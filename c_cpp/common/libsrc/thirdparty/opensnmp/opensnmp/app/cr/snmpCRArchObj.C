//
// snmpCRArchObj
//


#include "config.h"
#include <stdio.h>
#include <strings.h>
#include <typeinfo>
#include <iostream>
#include <map>
#include <set>
#include "snmpCRArchObj.H"
#include "snmpFIFOObj.H"
#include "snmpRegObj.H"
#include "mibs/system.H"
#include "mibs/control.H"
#include "mibs/sysOrTable.H"
#include "mibs/snmpTable.H"
#include "MIBRegTree.H"
#include "snmpDispArchRegObj.H"
#include "snmpDispRegisterEngIdASI.H"
#include "snmpCRRegisterOID.H"
#include "snmpCRRegisterTable.H"
#include "snmpCRRegisterScalarSet.H"
#include "mibs/mibEmpty.H"
#include "snmpVACMArchRegObj.H"
#include "scalarSet.H"
#include "debug.H"
#include "TransportAddress.H"

void
snmpCRArchObj::registerUs() {
  //
  // We need to register all the pdu types we wish to be responsible
  // for with the CR.
  //
  set<PDU_Type> *pduTypeSet = new set<PDU_Type>();
  snmpDispRegisterEngIdASI *regEngMsg;
  
  pduTypeSet->insert(PDU_Type(BER_TAG_PDU_GET));
  pduTypeSet->insert(PDU_Type(BER_TAG_PDU_GETNEXT));
  pduTypeSet->insert(PDU_Type(BER_TAG_PDU_GETBULK));
  pduTypeSet->insert(PDU_Type(BER_TAG_PDU_SET));
  
  regEngMsg =  new snmpDispRegisterEngIdASI(
      new OctetString(ourEngine->get_snmpEngineIdOS()), 
      pduTypeSet, fifoPtr, 
      new set<TransportAddress>(*(this->listenTAddresses)));
  DEBUG9(debugObj, "pushing RegisterEngId pduTypeSet to Disp:" 
         << regEngMsg << ", " << ourEngine->get_snmpEngineId()->toString() 
         << endl);
  dispFIFO->push(regEngMsg);
}

snmpCRArchObj::snmpCRArchObj(snmpEngine *ourEngine, snmpFIFOObj *fifoPtr,
                   std::set<TransportAddress> *listenTAddresses) //= NULL
{
  DEBUGCREATE(debugObj, "snmpCRArchObj");
  this->fifoPtr = fifoPtr;
  this->dispFIFO  = NULL;
  this->vacmFIFO  = NULL;
  this->ourEngine = ourEngine;
  this->listenTAddresses = listenTAddresses;

  // init the engine
  ourEngine->init();
  
  //
  // create the top level mib node that everything hangs underneath.
  //
  this->mibtop = 
    new MIBRegTree(new MIBRegistration(new mibEmpty(),
                                       new OID(".1.3"),
                                       new OctetString("none")));

  //
  // add in the systemMib()
  //
  this->mibtop->addNew( 
      new MIBRegTree(new MIBRegistration(new scalarSet(new systemMib()),
                                         new OID(".1.3.6.1.2.1.1"),
                                         new OctetString("none"))));

  //
  // create the definition of the sysORTable
  //
  list<berTag> lb;
  lb.push_back(BER_TAG_INTEGER32);
  snmpIndexes si(lb);
  snmpTable *st = new snmpTable(si, 3, 2);

  // add a row to the sysORTable
  si.set_index_number(1, new Integer32(1));
  snmpRow *sr = new snmpRow();
  sr->set_column(2, OID(".1.3.6.1.2.1.9"));
  sr->set_column(3, OctetString("sysOrTable"));
  st->add_row(si, sr);

  sr = new snmpRow();
  sr->set_column(2, OID(".1.3.6.1.6.3.16.2.2.1"));
  sr->set_column(3, OctetString("vacm"));
  si.set_index_number(1, new Integer32(2));
  st->add_row(si, sr);
  
  this->mibtop->addNew(
    new MIBRegTree(new MIBRegistration(st,
                                       new OID(".1.3.6.1.2.1.1.9"),
                                       new OctetString("none"))));
  //
  // A simple mib that controls this simple agent in simple ways.
  //
   this->mibtop->addNew( 
       new MIBRegTree(new MIBRegistration(new scalarSet(new controlMib(mibtop)),
                                          new OID(".1.3.6.1.4.1.2021.13.9089"),
                                          new OctetString("none"))));

  // 
  // create the mib registry table from the phaseI mib, as another example.
  //
  lb.clear();
  lb.push_back(BER_TAG_OBJECT_ID);
  snmpIndexes si2 = snmpIndexes(lb);
  st = new snmpTable(si2, 2);
  
  si2.set_index_number(1, new OID(".1.3.6.1.2.1"));
  sr = new snmpRow();
  sr->set_column(1, OID(".1.3.6.1.2.1"));
  sr->set_column(2, OctetString("systemMib"));
  st->add_row(si2, sr);

  si2.set_index_number(1, new OID(".1.3.6.1.2.1.9"));
  sr = new snmpRow();
  sr->set_column(1, OID(".1.3.6.1.2.1.9"));
  sr->set_column(2, OctetString("sysORTable"));
  st->add_row(si2, sr);

  si2.set_index_number(1, new OID(".1.3.6.1.4.1.2021.102"));
  sr = new snmpRow();
  sr->set_column(1, OID(".1.3.6.1.4.1.2021.102"));
  sr->set_column(2, OctetString("phaseI-mrTable"));
  st->add_row(si2, sr);

  this->mibtop->addNew(
    new MIBRegTree(new MIBRegistration(st,
                                       new OID(".1.3.6.1.4.1.2021.102"),
                                       new OctetString("none"))));

  // tell the engine that it can register it's stuff now.
  ourEngine->registerMibs();

  mibtop->dumpTree("tree: ");

  DEBUG9(debugObj,"snmpCRArchObj Contructor...\n");

}

snmpCRArchObj::~snmpCRArchObj()  {
  DEBUG9(debugObj,"snmpCRArchObj Destructor...\n");
  DEBUGDESTROY(debugObj);
}

void 
snmpCRArchObj::main_loop()  {
  string                   aString;
  snmpMessageObj          *newMsg, *outMsg;
  snmpRegObj               theReg;
  snmpAppProcessPduASI     *ppMsg;

  // Find the dispatcher
  while (NULL == this->dispFIFO) {
      if (NULL == (this->dispFIFO = theReg.findArchFIFO(SNMP_DISP_ARCH_NAME))) {
          DEBUG9(debugObj, "ERROR in dispArchObj: unable to find Disp fifo (Disp) in registry\n");
          // possibly raise exception.
          sleep(1);
      } else {
          DEBUG9(debugObj, "requested and found the Disp fifo\n");
      }
  }

  // find/create the VACM
  snmpVACMArchRegObj      *VACMArchRegPtr;
  string vacmName        = SNMP_VACM_ARCH_NAME; // VACM name in the registry
  VACMArchRegPtr         = new snmpVACMArchRegObj();  // and vacm reg obj.
  theReg.addNewArchInit(vacmName, VACMArchRegPtr);

  // Find the vacm
  if (NULL == this->vacmFIFO) {
      if (NULL == (this->vacmFIFO = theReg.findArchFIFO(SNMP_VACM_ARCH_NAME))) {
          DEBUG9(debugObj, "ERROR in vacmArchObj: unable to find Vacm fifo (Vacm) in registry\n");
          // possibly raise exception.
      } else {
          DEBUG9(debugObj, "requested and found the Vacm fifo\n");
      }
  }

  DEBUGN(99, debugObj, "hi there\n");
  DEBUGN(99, debugObj, "I'm your pal Eddie\n");
  DEBUGN(99, debugObj, "But, would Eddie be a pal if he was...\n");
  DEBUGN(99, debugObj, "On Fire!\n");

  // register our self.
  if (ourEngine != NULL && fifoPtr != NULL) {
      registerUs();
  } else {
      // XXX: big big error.  We can't go on.
      exit(77);
  }
  
  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  int mikescount = 0;

  while ( (NULL != (newMsg = fifoPtr->pop())) )  {

    outMsg = NULL;

    //    DEBUG9(debugObj, "-------msg: " << typeid(*newMsg).name() << "\n");

    // if incoming message is request message 
    if (typeid(*newMsg) == typeid(snmpAppProcessPduASI)) {
      // process it.
      ppMsg = dynamic_cast<snmpAppProcessPduASI *>(newMsg);
      
      DEBUG9(debugObj, "NewMsg is an snmpAppProcessPduASI\n");
      
      // Process stuff here.
      outMsg = handleMsg(ppMsg);

    } else if (typeid(*newMsg) == typeid(snmpCRRegisterOID)) {
        snmpCRRegisterOID *regmsg = dynamic_cast<snmpCRRegisterOID *>(newMsg);

        mibtop->addNew(new MIBRegTree(regmsg->get_MIBRegistration(true)));

        //XXX: return status of add

    } else if (typeid(*newMsg) == typeid(snmpCRRegisterTable)) {
        snmpCRRegisterTable *regmsg = 
            dynamic_cast<snmpCRRegisterTable *>(newMsg);
        DEBUG9(debugObj, "registering table at " 
               << *(regmsg->get_registerAt()) << endl);
        snmpTable *tbl;
        tbl = 
            new snmpTable(regmsg->get_table(true),
                          regmsg->get_maxcolumn(),
                          regmsg->get_mincolumn(),
                          regmsg->get_dSType(),
                          regmsg->get_rowManager(true));
	tbl->init( *regmsg->get_registerAt() );
        mibtop->addNew(
            new MIBRegTree(
                new MIBRegistration(tbl, regmsg->get_registerAt(true),
                                    regmsg->get_context(true))));

        mibtop->dumpTree("tree: ");

        //XXX: return status of add

    } else if (typeid(*newMsg) == typeid(snmpCRRegisterScalarSet)) {
        snmpCRRegisterScalarSet *regmsg = 
            dynamic_cast<snmpCRRegisterScalarSet *>(newMsg);
        DEBUG9(debugObj, "registering scalar set at " 
               << *(regmsg->get_registerAt()) << endl);
        scalarSet *sset;
        sset = new scalarSet(regmsg->get_scalarData(true));
        mibtop->addNew(
            new MIBRegTree(
                new MIBRegistration(sset, regmsg->get_registerAt(true),
                                    regmsg->get_context(true))));

        mibtop->dumpTree("tree: ");

        //XXX: return status of add

    } else if (typeid(*newMsg) == typeid(snmpVACMIsAccessAllowedASI)) {
        snmpVACMIsAccessAllowedASI *msg = 
            dynamic_cast<snmpVACMIsAccessAllowedASI *> (newMsg);
        
        handleACMResults(msg);
	delete msg;

    } else if (typeid(*newMsg) == typeid(snmpDispReturnResponsePduASI) ||
               typeid(*newMsg) == typeid(snmpDispRegisterEngIdASI)) {
        // nothing to do really.  We can't deal with the errors in any way.
        delete newMsg;
    } else if (typeid(*newMsg) == typeid(snmpExitMessage)) {
        // engine shutting down, do any neccessary exit clean up here
        delete newMsg;
	DEBUG9(debugObj, "crArchObj: received exit message, exiting\n");
	return;
    } else {
      DEBUG9(debugObj, "\ncr: Error, CR got wrong message type\n");
    }

    if (outMsg) {
        DEBUG9(debugObj, "sending to Disp\n");
        if (newMsg->get_returnFIFO())
            newMsg->get_returnFIFO()->push(outMsg);
        else if (dispFIFO)
            this->dispFIFO->push(outMsg);
        else {
            cerr << "no where to send the return message\n";
            exit(42); 
        }
    }
  }
  DEBUG9(debugObj, "crArchObj: Fin\n");
  sleep(2);
  return;
  //  exit(0);
} // main_loop

VarBindList *replaceNulls(VarBindList *thelist, berTag withTag) {
    VarBindList::iterator vbli;
    
    for(vbli = thelist->begin(); vbli != thelist->end(); vbli++) {
        if ((*vbli)->get_value()->get_tag() == BER_TAG_NULL) {
            (*vbli)->set_value(new BerError(withTag));
        }
    }
    return thelist;
}

snmpCRArchObj::crRef *
snmpCRArchObj::extractReference(PDU *pdu) {
    crRef *ref = new crRef();

//    RFC2573: section 3.2
//    (1)  The operation type is determined from the ASN.1 tag value
//         associated with the PDU parameter.  The operation type should
//         always be one of the types previously registered by the
//         application.

  //    Saved in the incoming message.
  //    XXX: Check that it's our registration type
  

//    (2)  The request-id is extracted from the PDU and saved.
    ref->reqID = pdu->get_requestID();

//    (3)  Any PDU type specific parameters are extracted from the PDU and
//         saved (for example, if the PDU type is an SNMPv2 GetBulk PDU,
//         the non-repeaters and max-repetitions values are extracted).

    // these two contain important data only for GetBulk PDUs
    // (non-repeaters and max-repetitions)
    ref->non_repeaters = pdu->get_errorStatus();
    ref->max_repetitions = pdu->get_errorIndex();
    // set the errorindex and error status to 0.
    pdu->set_errorIndex(0);
    pdu->set_errorStatus(0);

//    (4)  The variable-bindings are extracted from the PDU and saved.
    ref->varbinds = pdu->get_varBindList(false);
    ref->varbinds_copy = ref->varbinds->clone();

    ref->pdu = pdu;

    return ref;
}

void
snmpCRArchObj::sendIsAccessAllowed(crRef *ref, VarBindList *vbl) {
    VarBindList::iterator i;
    
    for(i = vbl->begin(); i != vbl->end(); i++) {
        sendIsAccessAllowed(ref, *i);
    }
    refStash.insert(pair<int, crRef *>(ref->reqID, ref));
}

void
snmpCRArchObj::sendIsAccessAllowed(crRef *ref, VarBind *vb) {
    snmpVACMIsAccessAllowedASI *msg;
    snmpAppProcessPduASI *message = ref->origMessage;
    
    msg = new snmpVACMIsAccessAllowedASI();
    msg->set_securityModel(message->get_securityModel()->clone());
    msg->set_securityName(message->get_securityName()->clone());
    msg->set_securityLevel(message->get_securityLevel()->clone());
    msg->set_contextName(message->get_contextName()->clone());
    msg->set_variableName(vb->get_OID()->clone());
    msg->set_stateReference(new stateRef(ref->reqID, vb));
    acmStash.insert(pair<int, VarBind *>(ref->reqID, vb));
    msg->set_returnFIFO(fifoPtr);
    msg->set_statusInformation(NULL);
    vacmFIFO->push(msg);
}

snmpDispReturnResponsePduASI *
snmpCRArchObj::handleMsg(snmpAppProcessPduASI *message) {

  PDU *pdu;
  snmpProtoErr *error;
  crRef *ref;
  
  DEBUG9(debugObj, "** handleMsg\n");
  pdu = message->get_pdu(true);

  ref = extractReference(pdu);
  ref->origMessage = message;

//    (5)  The management operation represented by the PDU type is
//         performed with respect to the relevant MIB view within the
//         context named by the contextName (for an SNMPv2 PDU type, the
//         operation is performed according to the procedures set forth in
//         [RFC1905]).  The relevant MIB view is determined by the
//         securityLevel, securityModel, contextName, securityName, and the
//         class of the PDU type.  To determine whether a particular object
//         instance is within the relevant MIB view, the following abstract
//         service interface is called:

//        statusInformation =      -- success or errorIndication
//          isAccessAllowed(
//          IN   securityModel     -- Security Model in use
//          IN   securityName      -- principal who wants to access
//          IN   securityLevel     -- Level of Security
//          IN   viewType          -- read, write, or notify view
//          IN   contextName       -- context containing variableName
//          IN   variableName      -- OID for the managed object
//               )

//    Where:

//      -  The securityModel is the value from the received message.

//      -  The securityName is the value from the received message.

//      -  The securityLevel is the value from the received message.

//      -  The viewType indicates whether the PDU type is a Read-Class or
//         Write-Class operation.

//      -  The contextName is the value from the received message.

//      -  The variableName is the object instance of the variable for
//         which access rights are to be checked.

//    Normally, the result of the management operation will be a new PDU
//    value, and processing will continue in step (6) below.  However, at
//    any time during the processing of the management operation:

//      -  If the isAccessAllowed ASI returns a noSuchView, noAccessEntry,
//         or noGroupName error, processing of the management operation is
//         halted, a PDU value is constructed using the values from the
//         originally received PDU, but replacing the error_status with an
//         authorizationError code, and error_index value of 0, and control
//         is passed to step (6) below.

//      -  If the isAccessAllowed ASI returns an otherError, processing of
//         the management operation is halted, a different PDU value is
//         constructed using the values from the originally received PDU,
//         but replacing the error_status with a genError code, and control
//         is passed to step (6) below.

//      -  If the isAccessAllowed ASI returns a noSuchContext error,
//         processing of the management operation is halted, no result PDU
//         is generated, the snmpUnknownContexts counter is incremented,
//         and control is passed to step (6) below.

//      -  If the context named by the contextName parameter is
//         unavailable, processing of the management operation is halted,
//         no result PDU is generated, the snmpUnavailableContexts counter
//         is incremented, and control is passed to step (6) below.

  DEBUG9(debugObj, "varbinds: " << ref->varbinds->toString() << "\n");

  switch (pdu->get_tag()) {
    case BER_TAG_PDU_GETBULK:
	// clear out the values in the varbind, just in case
	ref->varbinds->null_values();

        // The first pass for get bulk retrieves all of the data in
        // the varbinds, so just do it as a getnext set at first.
        // doing it via getnexts to the clients is the lazy way to do
        // it, but much less complex and for implementation speed
        // purposes is necessary at this point.   XXX: Maybe add later?
        ref->bulkpassnum = 0;

        // calculate number of repeaters: num_varbinds - num_non_repeaters || 0
        if ((unsigned int)ref->varbinds->size() > ref->non_repeaters) {
            ref->getbulknum = ref->varbinds->size() - ref->non_repeaters;
        } else {
            // if size < , its illegally encoded but we process anyway.
            ref->getbulknum = 0;
        }
        
        // no break: fall through to getnext handling

    case BER_TAG_PDU_GETNEXT:
	// clear out the values in the varbind, just in case
	ref->varbinds->null_values();

        // we need to handle these seperately, since we need to check
        // their access rights after retrieving possible return values and
        // then doing further retrevials if necessary till everything is
        // in view.
        error = processGetNextPDUs(pdu);
        if (!error)
            replaceNulls(ref->varbinds, BER_TAG_ENDOFMIBVIEW);
        sendIsAccessAllowed(ref, ref->varbinds);
        break;

    case BER_TAG_PDU_GET:
	// clear out the values in the varbind, just in case
	ref->varbinds->null_values();
	// NOTE: fall through to BER_TAG_PDU_SET
    case BER_TAG_PDU_SET:
      // the oids in the varbinds are exactly what is requested so that
      // we can check for access control *before* doing the requested
      // operation.
        sendIsAccessAllowed(ref, ref->varbinds);
        break;
      
    default:
      cerr << "********************** unknown PDU tag\n";
      // XXX: ASN parse error?  no...  um...
      break;
  }

  return NULL;
}

snmpDispReturnResponsePduASI *
snmpCRArchObj::handleACMResults(snmpVACMIsAccessAllowedASI *acmmsg) {
    snmpCRArchObj::stateRef *sref = (snmpCRArchObj::stateRef *) acmmsg->get_stateReference(true);
    int reqid = sref->reqID;
    VarBind *vb = sref->vb;
    VarBindList *vbl;
    map<int, VarBindList *>::iterator pti;
    map <int, crRef *>::iterator ri = refStash.find(reqid);
    
    multimap<int, VarBind *>::iterator i;
    for(i = acmStash.lower_bound(reqid); i != acmStash.upper_bound(reqid);
        i++) {
        if (i->second == vb) {
            acmStash.erase(i);
            break;
        }
    }

    if (ri == refStash.end()) {
        // XXX: ack ack ack ack ack.
        DEBUG9(debugObj, "********** snmpCRArchObj::handleACMResults: ACK ACK ACK ACK ACK\n");
    }
    if (!acmmsg->get_statusInformation()->get_success()) {
        VACMError *theError = dynamic_cast<VACMError *> 
            (acmmsg->get_statusInformation(false)->popErrorObj());
        if (theError && theError->resultCode == VACMError::NOTINVIEW) {
            // deal with it here
            switch (ri->second->pdu->get_tag()) {
                case BER_TAG_PDU_GET:
                case BER_TAG_PDU_SET:
                    vb->set_value(new BerError(BER_TAG_NOSUCHOBJECT));
                    break;
                    
                case BER_TAG_PDU_GETNEXT:
                case BER_TAG_PDU_GETBULK:
                    // get or create "redo" varbind list for this reqid.
                    pti = processThese.find(reqid);
                    if (pti == processThese.end()) {
                        vbl = new VarBindList();
                        processThese.insert(pair<int, VarBindList *>(reqid, vbl));
                    } else {
                        vbl = pti->second;
                    }

                    // save the bad varbind in the list
                    vbl->push_back(vb);
                    break;

                default:
                    // XXX: do something.
                    break;
            }
        } else {
            // save the (first) error inside the reference
            if (ri->second->acmResultCode == NULL) {
                ri->second->acmResultCode = theError;
            }
        }
    } else if (ri->second->pdu->get_tag() == BER_TAG_PDU_GET) {
        // successful ACM check.
        // Save the varbinds we want to process for use later.
        pti = processThese.find(reqid);
        if (pti == processThese.end()) {
            vbl = new VarBindList();
            processThese.insert(pair<int, VarBindList *>(reqid, vbl));
        } else {
            vbl = pti->second;
        }
        // save the bad varbind in the list
        vbl->push_back(vb);
    }

    if (acmStash.lower_bound(reqid) == acmStash.end()) {
        // we're finished waiting for the acm, so take the next step.
        refStash.erase(ri);
        crRef *ref = ri->second;
        snmpProtoErr *error = NULL;

        // handle ACM errors before all else.
        if (ref->acmResultCode) {
            // -  If the isAccessAllowed ASI returns a noSuchView,
            //    noAccessEntry, or noGroupName error, processing of
            //    the management operation is halted, a PDU value is
            //    constructed using the values from the originally
            //    received PDU, but replacing the error_status with an
            //    authorizationError code, and error_index value of 0,
            if (ref->acmResultCode->resultCode == VACMError::NOSUCHVIEW ||
                ref->acmResultCode->resultCode == VACMError::NOACCESSENTRY ||
                ref->acmResultCode->resultCode == VACMError::NOGROUPNAME)
                return sendResponse(new snmpProtoErr(PROTOERR_AUTHORIZATIONERROR), ref);

            // -  If the isAccessAllowed ASI returns an otherError,
            //    processing of the management operation is halted, a
            //    different PDU value is constructed using the values
            //    from the originally received PDU, but replacing the
            //    error_status with a genError code, and control is
            //    passed to step (6) below.
            if (ref->acmResultCode->resultCode == VACMError::OTHERERROR)
                return sendResponse(new snmpProtoErr(PROTOERR_GENERR), ref);
            

            // -  If the isAccessAllowed ASI returns a noSuchContext
            //    error, processing of the management operation is
            //    halted, no result PDU is generated, the
            //    snmpUnknownContexts counter is incremented, and
            //    control is passed to step (6) below.
            // XXX:  Ack!  send a report?  huh?

            // -  If the context named by the contextName parameter is
            //    unavailable, processing of the management operation
            //    is halted, no result PDU is generated, the
            //    snmpUnavailableContexts counter is incremented, and
            //    control is passed to step (6) below.
            // XXX:  Ack!  send a report?  huh?
        }
        
        map<int, VarBindList *>::iterator pti;
        switch (ref->pdu->get_tag()) {
            case BER_TAG_PDU_GET:
                pti = processThese.find(reqid);
                if (pti != processThese.end()) {
                    // need to keep searching for allowed access
                    VarBindList *vbl = pti->second;
                    processThese.erase(pti);
                    error = processGetPDUs(ref->pdu, vbl);
                    if (!error)
                        // technically speaking, the mib
                        // implementation should pick either
                        // noSuchObject or noSuchInstance as the error
                        // to return, but since they didn't we'll
                        // assume noSuchObject.
                        replaceNulls(ref->varbinds, BER_TAG_NOSUCHOBJECT);
                    return sendResponse(error, ref);
                }
                // no varbinds to process at all?  Odd.  Return success.
                // XXX: check RFCs to see if this is the proper thing to do.
                return sendResponse(NULL, ref);

            case BER_TAG_PDU_SET:
                error = processSetPDUs(ref->pdu);
                if (!error)
                    replaceNulls(ref->varbinds, BER_TAG_NOSUCHOBJECT);
                return sendResponse(error, ref);

            case BER_TAG_PDU_GETNEXT:
            case BER_TAG_PDU_GETBULK:
                pti = processThese.find(reqid);
                if (pti != processThese.end()) {
                    // need to keep searching for allowed access
                    VarBindList *vbl = pti->second;
                    processThese.erase(pti);
                    error = processGetNextPDUs(ref->pdu, vbl);
                    if (!error)
                        replaceNulls(ref->varbinds, BER_TAG_ENDOFMIBVIEW);
                    sendIsAccessAllowed(ref, vbl);
                    return NULL;
                } else if (ref->pdu->get_tag() == BER_TAG_PDU_GETBULK) {
                    // possibly loop more times to get more data.
                    
                    // check max-repeaters against the number of times
                    // we've looped already.
                    if (ref->bulkpassnum >= ref->max_repetitions) {
                        // We're done, return the results.
                        DEBUG9(debugObj, "GETBULK: DONE\n");
                        return sendResponse(NULL, ref);
                    } else {
                        // loop more.
                        ref->bulkpassnum++;
                        DEBUG9(debugObj, "GETBULK: looping again ("
                             << ref->bulkpassnum << "/" 
                             << ref->max_repetitions << ", " 
                             << ref->getbulknum << "more) ...\n");
                        
                        // clone and append the last getbulknum varbinds.
                        bool allendofview = true;
                        VarBindList *newvbl = new VarBindList();
                        VarBindList::reverse_iterator vblri;
                        unsigned int count;
                        for(vblri = ref->varbinds->rbegin(), count=0; 
                            vblri != ref->varbinds->rend() && 
                                count < ref->getbulknum;
                            vblri++, count++) {

                            // make sure there is at least one !endofview
                            if (allendofview && 
                                (*vblri)->get_value()->get_tag() != BER_TAG_ENDOFMIBVIEW &&
                                (*vblri)->get_value()->get_tag() != BER_TAG_NULL)
                                allendofview = false;

                            // we're going backwards, so reverse the
                            // list by pushing them to the front.
                            newvbl->push_front((*vblri)->clone());
                        }

                        if (allendofview) {
                            // quit if all results are "end of mib view"
                            // from last pass.
                            delete newvbl;
                            return sendResponse(NULL, ref);
                        }

                        // tack the list contents onto the original request.
                        VarBindList::iterator vbli;
                        for(vbli = newvbl->begin(); vbli != newvbl->end();
                            vbli++) {
                            DEBUG9(debugObj, "GETBULK: adding (" 
                                 << (int) ((*vbli)->get_value()->get_tag()) << ") "
                                 << (*vbli)->toString() << "\n");
                            // replace value with a null too.
                            (*vbli)->set_value(new Null());
                            ref->varbinds->push_back(*vbli);
                        }

                        // finally, process these new requests.
                        error = processGetNextPDUs(ref->pdu, newvbl);
                        if (!error)
                            replaceNulls(ref->varbinds, BER_TAG_ENDOFMIBVIEW);
                        sendIsAccessAllowed(ref, newvbl);
                        return NULL;
                    }
                } else {
                    // finished getNext.  Return results (or err) to requester.
                    return sendResponse(NULL, ref);
                }

            default:
                // XXX: do, uh, something.
                break;
        }
    }
    return NULL;
}  // handleACMResults

snmpDispReturnResponsePduASI *
snmpCRArchObj::sendResponse(snmpProtoErr *error, crRef *ref) {
  VarBindList::iterator vbli;
  VarBindList dummyList;
  VarBind *cause;
  PDU *pdu = ref->pdu;
  int i;

  // If an error is found, we need to reset the varbind list and
  // appropriately set the PDU's error flags.
  if (error) {
      DEBUG9(debugObj, "***** Error found! (" << error->get_error() << " == " << error->toString() << ")\n");

      if ((cause = error->get_cause()) == NULL) {
          // XXX: Please tell me this will never happen.
          DEBUG9(debugObj, "********** It happened\n");
	  vbli = dummyList.end();
      }

      // first figure out which varbind caused the problem in our
      // original varbind list.
      if (cause) {
          for (vbli = ref->varbinds->begin(), i = 1; 
               vbli != ref->varbinds->end();
               vbli++, i++) {
              if ((*vbli) == cause)
                  // We've found our cause.
                  break;
          }
      }
      
      // Ok, then we set the error code and index in the original PDU.
      pdu->set_errorStatus(error->get_error());
      if (vbli != ref->varbinds->end())
          // only set if we found a matching varbind, else error is
          // unknown position (which is legal for things like certain
          // ACM responses like NOSUCHVIEW, etc.
          pdu->set_errorIndex(i); 

      // Then, put the original varbind back in place (which is
      // actually now a clone of the real original).
      pdu->set_varBindList(ref->varbinds_copy);
  } else {
      delete ref->varbinds_copy;
  }

  // delete old ref info
  processThese.erase(pdu->get_requestID());

  // Change the tag type to a response
  pdu->set_tag(BER_TAG_PDU_RESPONSE);
      
//    (6)  The Dispatcher is called to generate a response or report
//         message.  The abstract service interface is:

//    returnResponsePdu(
//      IN   messageProcessingModel   -- typically, SNMP version
//      IN   securityModel            -- Security Model in use
//      IN   securityName             -- on behalf of this principal
//      IN   securityLevel            -- same as on incoming request
//      IN   contextEngineID          -- data from/at this SNMP entity
//      IN   contextName              -- data from/in this context
//      IN   pduVersion               -- the version of the PDU
//      IN   PDU                      -- SNMP Protocol Data Unit
//      IN   maxSizeResponseScopedPDU -- maximum size of the Response PDU
//      IN   stateReference           -- reference to state information
//                                    -- as presented with the request
//      IN   statusInformation        -- success or errorIndication
//           )                        -- error counter OID/value if error

  DEBUG9(debugObj, "Final PDU: " << pdu->toString() << "\n");
  
  snmpDispReturnResponsePduASI *msg = 
    new snmpDispReturnResponsePduASI();
  snmpAppProcessPduASI *xmsg = ref->origMessage;
  Integer32 *x = xmsg->get_messageProcessingModel(true);
  msg->set_messageProcessingModel(x);
  msg->set_securityModel(ref->origMessage->get_securityModel(true));
  msg->set_securityName(ref->origMessage->get_securityName(true));
  msg->set_securityLevel(ref->origMessage->get_securityLevel(true));
  msg->set_contextEngineID(ref->origMessage->get_contextEngineID(true));
  msg->set_contextName(ref->origMessage->get_contextName(true));
  msg->set_pduVersion(new PDU_Version(pdu->get_version()));
  msg->set_pdu(ref->pdu); // already extracted.
  msg->set_maxSizeResponsePDU(ref->origMessage->get_maxSizeResponsePDU(true));
  msg->set_statusInformation(new snmpStatusInfo(true));
  msg->set_stateReference(ref->origMessage->get_stateReference(true));
  msg->set_returnFIFO(fifoPtr);

  if (ref->origMessage->get_returnFIFO())
      ref->origMessage->get_returnFIFO()->push(msg);
  else if (dispFIFO)
      this->dispFIFO->push(msg);
  else {
      cerr << "no where to send the return message\n";
      exit(42); 
  }
  delete ref->origMessage;  // delete the old message.
  return NULL;
}  // sendResponse

VarBind *
snmpCRArchObj::assignVarBinds(PDU *pdu, map<OID, MIBRegTree *> *saved, 
                              VarBindList *vbl) {
  // assigns a list of varbinds to a set of mib registrations and
  // returns a list of registration tree pointers.

  if (vbl == NULL)
      vbl = pdu->get_varBindList(false);

  VarBindList::iterator i; // list<VarBind *>::
  OID *oid, *regoid;
  MIBRegTree *regt_start, *regt_end;
  MIBRegistration *reg;
  SearchRange *range;
  VarBind *unassigned = 0;
  
  DEBUG9(debugObj, "    * extracting varbinds\n");
  for(i = vbl->begin(); i != vbl->end(); i++) {

    VarBind *vb = (*i);

    DEBUG9(debugObj, "VACM assigning: " << vb->toString() << "\n");

    // get_ the varbind's oid we're searching for.
    oid = vb->get_OID(false);
    
    // Find the registration information.
    regt_start = mibtop->get_registered(oid);

    // Have we found one at all?  If not, skip it or assign it to the top.
    if (regt_start == NULL) {
      if ((pdu->get_tag() == BER_TAG_PDU_GETNEXT ||
           pdu->get_tag() == BER_TAG_PDU_GETBULK) &&
          *(mibtop->get_registration()->get_reg_oid()) > *oid) {
        // its less than, so assign it to the first node if a get_next.
        regt_start = mibtop;
      } else {
        DEBUG9(debugObj, "     didn't give varbind out" << oid->toString() << "\n");
        if (!unassigned)
            unassigned = (*i);
        continue;
      }
    }
    
    regt_end = NULL;
    reg = regt_start->get_registration();

    if (pdu->get_tag() == BER_TAG_PDU_GETNEXT ||
        pdu->get_tag() == BER_TAG_PDU_GETBULK) {
      // collect the ending oid to search for if doing a get_next op.
      regt_end = regt_start->get_children();

      // however, the above is just a start.
      while(regt_end && *regt_end->get_registration()->get_reg_oid() < *oid)
          regt_end = regt_end->get_next();

      // need to decend
    }
    range = new SearchRange(*i, regt_start, regt_end);
    
    reg->collectRanges(range);
    
    // save the search range.
    regoid = reg->get_reg_oid();
    if (saved->count(*regoid) == 0)
      (*saved)[*regoid] = regt_start;

      DEBUG9(debugObj,"      gave varbind out: " << oid->toString() 
             << " to " << regoid->toString() << ", " 
             << ((range->get_end_tree()) ? range->get_end_tree()->get_registration()->get_reg_oid()->toString() : "NULL")
             << "\n");
  }
  DEBUG9(debugObj, 
         "    * processed varbindlist: " << vbl->toString() << "\n");
  return unassigned;
}

snmpProtoErr *
snmpCRArchObj::processGetPDUs(PDU *pdu, VarBindList *vbl) {
  map<OID, MIBRegTree *> saved;
  map<OID, MIBRegTree *>::iterator s;
  snmpProtoErr *err = 0;

  DEBUG9(debugObj, "** processGetPDU\n");

  DEBUG9(debugObj, "******** processing PDU\n");

  assignVarBinds(pdu, &saved, vbl);

  DEBUG9(debugObj, "   ** calling requested action \n");
  for(s = saved.begin(); s != saved.end(); s++) {
    MIBRegistration *reg = s->second->get_registration();
    
    DEBUG9(debugObj, "    * calling an action \n");

    err = reg->get();
    if (err) {
        // exit immediately, as all results will be bad.
        break;
    }

    reg->SearchRangeListReset();
  }
  DEBUG9(debugObj, "   ** called all requested actions \n");
  DEBUG9(debugObj, "  *** Done creating return values:\n"
         << pdu->get_varBindList(false)->toPrettyString("      "));
  DEBUG9(debugObj, "***** finished processing pdu \n");
  return err;
}

snmpProtoErr *
snmpCRArchObj::processSetPDUs(PDU *pdu) {
  map<OID, MIBRegTree *> saved;
  map<OID, MIBRegTree *>::iterator s;
  snmpProtoErr *err = 0, *finalerr = 0;
  set_actions state = AGENTX_TESTSET;
  VarBind *unassigned;

  DEBUG9(debugObj, "** processSetPDU\n");
  DEBUG9(debugObj, "******** processing PDU\n");

  if ((unassigned = assignVarBinds(pdu, &saved)) != NULL) {
      // something wasn't assigned, so bail out here.
      return new snmpProtoErr(PROTOERR_NOSUCHOBJECT, unassigned);
  } else {
      DEBUG9(debugObj, "   ** calling requested action \n");

      while (state != WERE_FINISHED) {
          for(s = saved.begin(); s != saved.end(); s++) {
              MIBRegistration *reg = s->second->get_registration();
    
              DEBUG9(debugObj, "    * calling an action \n");

              //
              // State diagram:
              //
              // TESTSET -success-> COMMIT -success-> CLEANUP
              //    |                 |
              //    |                 \--failure-> UNDO
              //    |
              //    |
              //     --failure-> CLEANUP
              //

              DEBUG9(debugObj, "    * Setting: TESTSET \n");

              err = reg->set(state);
          
              if (err && (state == AGENTX_TESTSET || state == AGENTX_COMMIT)) {
                  // Error condition, stop here and go to the next state.
                  break;
              }
          }
          // decide the next state.
          switch (state) {
              case AGENTX_TESTSET:
                  if (err) {
                      DEBUG9(debugObj, "    *   ERROR \n");
                      DEBUG9(debugObj, "    * Setting: TESTSET -> CLEANUP \n");
                      state = AGENTX_CLEANUP;
                      finalerr = err;
                  } else {
                      DEBUG9(debugObj, "    * Setting: TESTSET -> COMMIT \n");
                      state = AGENTX_COMMIT;
                  }
                  break;
          
              case AGENTX_COMMIT:
                  if (err) {
                      DEBUG9(debugObj, "    *   ERROR \n");
                      DEBUG9(debugObj, "    * Setting: COMMIT -> UNDO \n");
                      state = AGENTX_UNDO;
                      finalerr = err;
                  } else {
                      DEBUG9(debugObj, "    * Setting: COMMIT -> CLEANUP \n");
                      state = AGENTX_CLEANUP;
                  }
                  break;
          
              case AGENTX_CLEANUP:
              case AGENTX_UNDO:
                  state = WERE_FINISHED;
                  break;
          
              default:
                  state = WERE_FINISHED;
                  cerr << "********************** Ouch.  bad state.\n";
                  break;
          }
          // XXX: reset varbinds, just in case something has touched them
          //      (bad something, bad something).
          // XXX: delete err
      }
  }
  for(s = saved.begin(); s != saved.end(); s++) {
      MIBRegistration *reg = s->second->get_registration();
      reg->SearchRangeListReset();
  }
  
  DEBUG9(debugObj, "   ** called all requested actions \n");
  DEBUG9(debugObj, "  *** Done creating return values:\n" \
       << pdu->get_varBindList(false)->toPrettyString("      "));
  DEBUG9(debugObj, "***** finished processing pdu \n");
  return finalerr;
}

snmpProtoErr *
snmpCRArchObj::processGetNextPDUs(PDU *pdu, VarBindList *vbl) {
  SearchRangeList *nextlist = 0;
  OID *regoid;
  SearchRangeList *srlp;
  SearchRangeList::iterator srli;
  VarBindList::iterator vbli;
  map<OID, MIBRegTree *> saved;
  bool notdone = true;
  int count = 0;
  snmpProtoErr *err;
  DEBUG9(debugObj, "** processGetNextPDU\n");

  DEBUG9(debugObj, "******** processing PDU\n");

  // divide up the varbinds to their appropriate sections.
  while(notdone) {
      DEBUG9(debugObj, "  *** starting loop (#" << ++count << ")\n");
      notdone = false;

      if (nextlist) {
          // erase the previous mapping set_
          saved.clear();
    
          // loop over all the search ranges we need to re-process
          for(srli = nextlist->begin(); srli != nextlist->end(); srli++) {

              // get_ the registration object, and tell it to collect the range.
              MIBRegistration *reg = 
                  (*srli)->get_start_tree()->get_registration();
              reg->collectRanges(*srli);

              regoid = reg->get_reg_oid();
              if (saved.count(*regoid) == 0)
                  saved[*regoid] = (*srli)->get_start_tree();

              DEBUG9(debugObj, "      gave varbind out: " 
                   << (*srli)->get_varbind()->get_OID()->toString()
                   << " to " << regoid->toString() << ", "
                   << (((*srli)->get_end_tree()) ? (*srli)->get_end_tree()->get_registration()->get_reg_oid()->toString() : "NULL")
                   << "\n");
          }
          delete nextlist;
          nextlist = 0;
      } else {
        assignVarBinds(pdu, &saved, vbl);
      }
  
      DEBUG9(debugObj, "   ** calling requested action \n");
      map<OID, MIBRegTree *>::iterator s;
      for(s = saved.begin(); s != saved.end(); s++) {
          MIBRegistration *reg = s->second->get_registration();
    
          DEBUG9(debugObj, "    * calling an action \n");

          err = reg->getNext();
          if (err) {
              // exit immediately, as all results will be bad.
              return err;
          }

          // find empty varbinds and set_up for another run
          srlp = reg->get_ranges(true);
          srli = srlp->begin();

          for(; srli != srlp->end(); srli++) {
            VarBind *vb = (*srli)->get_varbind();
            // if result is NULL or less than the valid end
            // of their search range, loop again.
            if (((*srli)->get_end_oid() != 0 &&
                 vb->get_OID()->compare(*(*srli)->get_end_oid()) == GREATER_THAN)
                || vb->get_OID()->compare(*(*srli)->get_start_oid()) ==
                LESS_THAN) {

              // they returned an an invalid result, outside their
              // search range.  Retrieve the previous OID, and store
              // it back in the varbind and delete the result they
              // returned.
              DEBUG9(debugObj, "  ** INVALID RESULT OUTSIDE RANGE\n");
              vb->set_OID((*srli)->get_start_oid()->clone());
              vb->set_value(new Null());

              goto nextrange;

            } else if (vb->get_value()->get_tag() == BER_TAG_NULL) {
              // XXX: if varbind is not empty, delete value.
              // XXX: delete range if == 0, mem leak

              nextrange:
              if ((*srli)->get_next() == 0)
                continue;
              // put into new list, and getNext in search range
              if (nextlist == 0) {
                notdone = true;
                nextlist = new SearchRangeList();
              }
              nextlist->push_back(*srli);
            }
          }
          // reset_ the list of search ranges for that entity.
          reg->SearchRangeListReset();
      }
      DEBUG9(debugObj, "   ** called all requested actions \n");
  }
  
  // find all of the objects in the request list that still have NULL
  // attached as the value, and change them to end of mib view
  // objects.
  if (!vbl)
      vbl = pdu->get_varBindList(false);
  for(vbli = vbl->begin(); vbli != vbl->end(); vbli++) {
    if ((*vbli)->get_value()->get_tag() == BER_TAG_NULL) {
      (*vbli)->set_value(new BerError(BER_TAG_ENDOFMIBVIEW));
    }
  }
  DEBUG9(debugObj, "  *** Done creating return values:\n" \
       << vbl->toPrettyString("      "));
  DEBUG9(debugObj, "***** finished processing pdu \n");
  return 0;
}

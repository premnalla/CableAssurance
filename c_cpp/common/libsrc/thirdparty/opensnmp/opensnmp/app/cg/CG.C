#include <config.h>
#include "CG.H"
#include "snmpDispSendPduASI.H"
#include "snmpCGArchRegObj.H"
#include "snmpCGtoCGArchMsg.H"


// STATIC ---
snmpEngine   *CG::theEngine = NULL;
snmpRegObj   *CG::archReg   = NULL;


CG::CG() {
    init_internals();

    if (NULL == CG::theEngine) {
      CG::theEngine = new snmpEngine();
      
      // this should be after any command line options instead of
      // before, in case the command line options changes any engine
      // vars. e.g. this engine's ID.
      CG::theEngine->init();  // registers the disp, mp, sm, etc.
      CG::archReg = theEngine->get_archRegistry(false); // get the registry

      // XXX Note: multi CG's: we may want to registor this CG object
      // under a unique name

      // Register the CG Arch
      snmpCGArchRegObj *cgRegObj = new snmpCGArchRegObj(CG::theEngine);
      string forwantofareference = string(SNMP_CG_ARCH_NAME);
      archReg->addNewArchInit( forwantofareference, cgRegObj);
      
    }
    else {
      // do nothing?? 
    }
    // get cg arch's fifo, first time will also couse the cg arch's
    // thread to be started.
    cgArchFIFO    = archReg->findArchFIFO(SNMP_CG_ARCH_NAME);
    if ( NULL == cgArchFIFO) {
      DEBUG0(CGDEBUG, "ERROR: CG init: can't find CGArch in the reg table!");
      DEBUG0(CGDEBUG, "ERROR: CG init: everything will be bad after this!");
    }

    this->ourFIFO = new snmpFIFOObj();

    // set synchronous timeout
    this->timeout = -1;
}


void
CG::init_internals() {
    DEBUGCREATE(CGDEBUG, "CG");
    DEBUG9(CGDEBUG, "initialized CG");
    cgArchFIFO = NULL;
}



// sends the pdu and waits for a reponse,
// null indicates failure, possibly timeout
PDU *
CG::send_and_wait(PDU *pdu, sendPduArgs & pArgs) {

  send_dont_wait(pdu, pArgs);
  
  PDU                 *newPDU = NULL;
  PDU                 *retPDU = NULL;
  snmpStatusInfo      *status = NULL;
  OctetString    *msgEngineID = NULL;
  // wait for the message
  messageTypeEnum  retType = get_message(newPDU,      status, 
					 msgEngineID, this->timeout);

  // since this only returns NULL on an error..., 
  // should add error return in the future
  if (internal_error == retType) {
    retPDU = NULL;
  }
  else if (   (report == retType) ||
	    (response == retType) )  {
    retPDU = newPDU;
    newPDU = NULL;

    if ( NULL != msgEngineID ) {
      if ( !(pArgs.get_contextEngId()) ||
	   (*(pArgs.get_contextEngId()) == OctetString("")) ) {
	pArgs.set_contextEngId(msgEngineID);
      } else {
	delete msgEngineID;
      }
    }
 
  }
  else {
    // noMessage or something unknown
    retPDU = NULL;
  }

  if (newPDU) delete newPDU;
  if (status) delete status;

  return retPDU;
}  // send_and_wait


// XXX these need to be updated to new system.
// VarBindList *
// CG::walk(const OID &oid) {
//     PDU *result = 
//       new PDU(BER_TAG_PDU_GETNEXT,0,0,0, new VarBindList, PDU_VERSION_2);
//     VarBind *vb;
//     VarBindList *ret = new VarBindList();
//     result->Add(vb = new VarBind(oid.clone()));
    
//     DEBUG5(CGDEBUG, "CG::walk -> beginning to retrieve " << (string) oid);
//     while(1) {
//         result = send_and_wait(result);
//         if (result) {
//             vb = *(result->get_varBindList()->begin());
//             if (vb->get_OID()->mincompare(oid) != EQUAL_TO)
//                 break;
//             DEBUG9(CGDEBUG, "  CG::walk -> retrieved " << (string) *vb);
//             ret->push_back(vb->clone());
//         } else {
//             break;
//         }
//         result->set_pduType(BER_TAG_PDU_GETNEXT);
//     }
//     DEBUG9(CGDEBUG, "  CG::walk -> finished ");
//     return ret;
// }


// // XXX needs to be updated to new system.
// snmpDataTable *
// CG::get_table(const OID &oid) {
//     PDU *result = 
//       new PDU(BER_TAG_PDU_GETNEXT,0,0,0, new VarBindList, PDU_VERSION_2);
//     snmpRow *row = NULL;
//     VarBind *vb;
//     result->Add(vb = new VarBind(oid.clone(),new Null()));

//     // set up return table
//     list<berTag> lb;
//     lb.push_back(BER_TAG_OBJECT_ID); // not correct, but doesn't matter
//     snmpIndexes si(lb, true);
//     snmpDataTable *table = new snmpDataTable(si);

//     DEBUG5(CGDEBUG,
//            "CG::get_table() -> beginning to retrieve " << (string) oid);
//     while(1) {
//        // where doing a get_next, the varbind value should be null,
//        // lets make sure...
//        	(*result->get_varBindList()->begin())->set_value(new Null());
//         result = send_and_wait(result);
//         if (result) {
//             // get result parts
//             vb = *(result->get_varBindList()->begin());
//             OID *retoid = vb->get_OID();
//             list<asnIndex *> indexlist;
//             OID *index = retoid->clone();
//             cout << "returned oid: " << *index << endl;

//             index->crop(oid.length()+3, index->length());
//             cout << "index: " << index->get_numericStr() << endl;
//             indexlist.push_back(index);

//             // out of scope?
//             if (retoid->mincompare(oid) != EQUAL_TO) {
//                 cout << "done\n";
//                 break;
//             }

//             // does a row with this index exist?
//             if ((row = table->find(indexlist)) == NULL) {
// 	      // no, create the row
// 	      row = new snmpRow();
// 	      // set up the index
// 	      si.set_fromOID(*retoid, oid.length()+3); // skip past column
// 	      cout << "created oid: " << *retoid << " / length = " 
// 		   << retoid->length() << " : " << si.toString() << endl;
// 	      table->add_row(si, row);
//             }
//             cout << "xxx\n";
//             cout << "set: [index=" << "blah" <<
//                 "/col=" << (*retoid)[oid.length()+1] << "] = " 
//                  << *vb->get_value() << endl;
//             row->set_column((*retoid)[oid.length()+1], *vb->get_value());
//         } else {
//             break;
//         }
//         result->set_pduType(BER_TAG_PDU_GETNEXT);
//     }
//     return table;
// }


// set's the engine's SNMP Engine ID.  In general, if you're doing
// this, you want to do it before you send any messages.
void
CG::set_engineID(string engID) {
  OctetString *newEngID = new OctetString();
  // allow for hexidecimal (i.e. '0x' prefix)
  newEngID->fromString(engID);
  if ( (newEngID->size() < 5) || (newEngID->size() >32) ) {
	cerr << "error: invalid new engine ID, failing\n";
	exit(1);
  }

  theEngine->set_snmpEngineId_noUpdate(newEngID);
}


// currently this means YOU DON'T WANT TO 
CG::~CG() {
  DEBUGDESTROY(CGDEBUG);
}


void
CG::shutdown() {
  // this sends and exit message to every thread registered.
  theEngine->shutdownAll();
}



//  Asynchronous Specific calls


// send_dont_wait
//
// returns true if the pdu is processed and sent across the network
// successfully.
// returns false (and prints out any error messages) if it fails
// to process and send the SNMP PDU.
bool
CG::send_dont_wait(PDU *pdu, sendPduArgs & pArgs) {
  // make sure the user exists

  snmpCGtoCGArchMsg *ctcMsg = 
    new snmpCGtoCGArchMsg(pdu,
			  new sendPduArgs(pArgs),
			  ourFIFO);

  DEBUG5(CGDEBUG, "send_dont_wait: pushing to CG Arch");
  cgArchFIFO->push(ctcMsg);  // send PDU to CG Arch
    
  return true;
}  // send_dont_wait


// get_message has four basic types of results.  The CALLER is
// responsible for freeeing any data assigned to *pdu or
// *status. They will be set to NULL if no data of that type is
// returned.

// maxwait: the maximum time the call should wait for a message
// before returning 'noMessage' (a message arriving will cause an
// immediate return).
// '0' indicates return immediately (the default). 
// '-1' indicates wait forever until a message arrives.

// The four types are: 

// noMessage: no messages are waiting.  *pdu = NULL, *status =
//            NULL.

// response: an SNMP response from the agent, this usually
//           indicates success. The *pdu will be the returned
//           PDU, *status = NULL.

// report: an SNMP report, this usually indicates an SNMP
//         error returned by the agent (command receiver). The
//         *pdu will be the returned PDU, *status = NULL.

// internal_error: error within this engine, usually indicates a
//                 problem sending the PDU.  *pdu will be the pdu
//                 information initially sent, *status will be
//                 snmpStatusInfo with any error information.

CG::messageTypeEnum 
CG::get_message(PDU            * &pdu, 
		snmpStatusInfo * &status, 
		OctetString    * &contextEngineID,
		int              maxwait)  {
  pdu = NULL;  status = NULL; contextEngineID = NULL;

  // pop messages off of queue until one is found that is returnable
  // or there is no messages left.
  while ( ourFIFO->messageWating() || (maxwait > 0)  || (maxwait == -1) )  {
    snmpMessageObj  *newMsg = NULL;

    // if we know there is a message or we are waiting for a
    // specified amount of time.
    if (0 <= maxwait) {
      newMsg = ourFIFO->pop(maxwait);
    }
    // wait for a message forever
    else {
      newMsg = ourFIFO->pop();
    }

    if (NULL == newMsg) {
      DEBUG5(CGDEBUG, "Warning: get_message: null returned from pop");
      return noMessage;
    }
    // if it's an incoming CG to CG Arch message,
    else if (snmpCGtoCGArchMsg *msg = 
	dynamic_cast<snmpCGtoCGArchMsg *>(newMsg)) {
      DEBUG5(CGDEBUG, "get_message: snmpCGtoCGArchMsg received");
      pdu             = msg->get_pdu(true);
      status          = msg->get_status(true); // may be null
      contextEngineID = msg->get_args()->get_contextEngId(true);
      // clean up
      delete msg;

      // sanity check
      if ( !(pdu) ) {
	DEBUG0(CGDEBUG, "Error: get_message: empty pdu from response mesasge, I'll drop and try continuing");
      }
      // check if it was unsuccessful
      else if ( status && !(status->get_success()) ) {
	DEBUG5(CGDEBUG, "get_message: message not successful");
	return internal_error;
      }
      // return type;
      else if ( pdu->get_pduType() ==  PDU_Type(BER_TAG_PDU_REPORT) ) {
	DEBUG5(CGDEBUG, "get_message: Report returned");
	return report;
      }
      else if ( pdu->get_pduType() ==  PDU_Type(BER_TAG_PDU_RESPONSE) ) {
	DEBUG5(CGDEBUG, "get_message: Response returned");
	return response;
      }
      else  {
	DEBUG5(CGDEBUG, "get_message: unhandable pdu type returned (i.e. not a response or a report).  I'll drop and continue.");
	delete pdu; pdu = NULL;
      }
    }
    // unknown/unrequested messagen
    else { 
      DEBUG0(CGDEBUG, "Error: get_message: received unknown message type, '" 
	     + string(typeid(*newMsg).name()) + 
	     "', from CG Arch, I'll drop and continue");
      delete msg;
    }
  }

  return noMessage;
} // get_message


void
CG::print_incoming_pdu(PDU *pdu) {
  VarBindList *varList = NULL;
  VarBindList::const_iterator v_it = NULL;

  if (!(pdu)) {
    DEBUG0(CGDEBUG, "Error: print_incoming_pdu: asked to print a NULL pdu!");
    return;
  }

  DEBUG9(CGDEBUG, "print_incoming_pdu: returned, " +
	 pdu->toString());

  if (pdu->get_pduType() ==  PDU_Type(BER_TAG_PDU_REPORT)) {
    cout << "Error, Report returned, OID(s):\n";
    varList = pdu->get_varBindList();
    v_it = varList->begin();
    while (v_it != varList->end()) {
      cout << "  " << string(*(*v_it)->get_OID()) << endl;
      v_it++;
    }
  }

  else if (pdu->get_pduType() ==  PDU_Type(BER_TAG_PDU_RESPONSE)) {
    if (0 == pdu->get_errorIndex()) {
      DEBUG5(CGDEBUG, "\n   *** COMMAND WAS SUCCESSFUL ***\n");
      varList = pdu->get_varBindList();
      v_it = varList->begin();
      while (v_it != varList->end()) {
	cout << string(*(*v_it)->get_OID()) << " = " 
	     << string(*(*v_it)->get_value()) << endl;
	v_it++;
      }
    }
    else  {
      DEBUG5(CGDEBUG, "\n   *** ERROR WITH COMMAND ***\n");
      cout << "Command Failed, returned: \n" << pdu->toString();    
    }
  }
  else {
    cerr << "Received a PDU from the dispatcher that I didn't register "
	 << "for :{, Failing!\n";
  }

} // handle_incoming_pdu

// retuns the a pointer to snmpEngine.
snmpEngine * &
CG::get_engine(void) {
  return (theEngine);
}

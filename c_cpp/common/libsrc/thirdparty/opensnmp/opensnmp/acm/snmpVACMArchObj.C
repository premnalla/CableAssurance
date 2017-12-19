//
// snmpVACMArchObj
//
#include "config.h"
#include "asnDataTypes.H"
#include "snmpFIFOObj.H"
#include "snmpRegObj.H"
#include "snmpMessageObj.H"
#include "snmpVACMArchObj.H"
#include "snmpVACMIsAccessAllowedASI.H"
#include "snmpCRRegisterTable.H"

#include "snmpDataTable.H"
#include "snmpRowStatus.H"
#include "snmpColumnCloneAllocator.H"
#include "snmpRowStatusColumnAllocator.H"
#include "snmpRowStorageColumnAllocator.H"
#include "snmpRowStatusCheckColsExist.H"
#include "snmpStorageType.H"
#include "debug.H"
#include "opensnmpUtilities.H"
#include "snmpPersistentRowManager.H"

snmpVACMArchObj::snmpVACMArchObj(snmpFIFOObj *thefifo)  {
  DEBUGCREATE(debugObj, "VACM");
  this->fifoPtr = thefifo;
  this->crFIFO  = NULL;

  DEBUG9(debugObj, "VACM:snmpVACMArchObj constructor" << endl);
}

snmpVACMArchObj::snmpVACMArchObj(const snmpVACMArchObj & ref) {
  DEBUGCREATE(debugObj, "VACM");
  this->fifoPtr = ref.fifoPtr;
  this->crFIFO  = ref.crFIFO;

  DEBUG9(debugObj, "VACM:snmpVACMArchObj constructor" << endl);
}

snmpVACMArchObj::~snmpVACMArchObj()  {
  DEBUG9(debugObj, "VACM:snmpVACMArchObj destructor" << endl);
  DEBUGDESTROY(debugObj);
}

void 
snmpVACMArchObj::main_loop()  {
  snmpMessageObj   *newMsg;
  int               count = 0;

  if (this->fifoPtr == NULL) {
    DEBUG9(debugObj, "VACM: main_loop: ERROR: our fifo is NULL???" << endl);
    exit(99);
  }

  createTables();

  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  while (NULL != (newMsg = this->fifoPtr->pop())) {
    DEBUG9(debugObj, "VACM:recvd a new message in fifo: number = " << count \
           << ": newMsg = " << newMsg << endl);
    if (snmpVACMIsAccessAllowedASI *msg 
	       = dynamic_cast<snmpVACMIsAccessAllowedASI *>(newMsg)) {

      msg = isAccessAllowed(msg);
      snmpFIFOObj *retFifo = msg->get_returnFIFO(true);
      if (retFifo)
          retFifo->push(msg);
      else
          exit(99);

    } else if (snmpExitMessage *msg 
	       = dynamic_cast<snmpExitMessage *>(newMsg)) {
      // do any neccessary exit processing here
      DEBUG9(debugObj, "VACM: received exit message, exiting");
      return;
    } else {
      DEBUG9(debugObj, "VACM:ERROR: unknown message type:" << newMsg << endl);
    }
    count++;
  }
  // XXX: unregister tables with CR
  DEBUG9(debugObj, "VACM:main_loop exitting" << endl);
} // main_loop

snmpVACMIsAccessAllowedASI *
snmpVACMArchObj::returnError(snmpVACMIsAccessAllowedASI *msg, 
                             VACMError::VACMResultCodes errorcode) {
    msg->set_statusInformation(new snmpStatusInfo(false, 
                                                  new VACMError(errorcode)));
    DEBUG9(debugObj, "VACM::  returning error: " << 
	     (int) errorcode << "\n");
    return msg;
}

snmpVACMIsAccessAllowedASI *
snmpVACMArchObj::isAccessAllowed(snmpVACMIsAccessAllowedASI *msg) {
    list<asnIndex *> lb;
    OctetString *groupName;
    OctetString *contextName;
    OctetString *viewName;

    DEBUG9(debugObj, "VACM: Deciding on " << msg->get_variableName()->toString() << "\n");

    // 1) The vacmContextTable is consulted for information about
    //    the SNMP context identified by the contextName.  If information
    //    about this SNMP context is absent from the table, then an
    //    errorIndication (noSuchContext) is returned to the calling module.

    contextName = msg->get_contextName();
    lb.push_back(contextName->clone());
    DEBUG9(debugObj, "VACM: searching for context " << contextName->toString() << "\n");
//    if (contextTable->find(lb, 1) == NULL)
//        return returnError(msg, VACMError::NOSUCHCONTEXT);

    DEBUG9(debugObj, "VACM: context " << contextName->toString() << "found\n");
    
    // 2) The vacmSecurityToGroupTable is consulted for mapping the
    //    securityModel and securityName to a groupName.  If the information
    //    about this combination is absent from the table, then an
    //    errorIndication (noGroupName) is returned to the calling module.

    lb.clear();
    lb.push_back(msg->get_securityModel()->clone());
    lb.push_back(msg->get_securityName()->clone());

    DEBUG9(debugObj, "VACM: searching for a group model="  \
         << (int) *(msg->get_securityModel()) << ", securityName=" \
         << (string) *(msg->get_securityName()) << "\n");
    groupName = dynamic_cast<OctetString *> 
        (securityToGroupTable->find(lb, VS_vacmGroupName_COL));
    if (groupName == NULL)
        return returnError(msg, VACMError::NOGROUPNAME);
    DEBUG9(debugObj, "VACM: group found: " << groupName->toString() << "\n");
    
    
    // 3) The vacmAccessTable is consulted for information about the
    //    groupName, contextName, securityModel and securityLevel.  If
    //    information about this combination is absent from the table, then
    //    an errorIndication (noAccessEntry) is returned to the calling
    //    module.

    // Begin text from SNMP-VIEW-BASED-ACM-MIB:
    // To select the proper entry, follow these steps:


    list<asnIndex *> accessIndexes;
    accessIndexes.push_back(groupName->clone());
    snmpDataTable::iterator accessIterator;
    list<pair<snmpIndexes, snmpRow *> > validRows;

    // 1) the set of possible matches is formed by the
    //    intersection of the following sets of entries:
    //      the set of entries with identical vacmGroupName
    for(accessIterator = accessTable->lower_bound(accessIndexes);
        accessIterator != accessTable->end() &&
        *(dynamic_cast<const OctetString *> (accessIterator->first.get_index_number(VA_GroupName_IDX))) == *groupName; accessIterator++) {

        DEBUG9(debugObj, "comparing: " << (string) *groupName << " to " << \
            (string) *(dynamic_cast<const OctetString *> (accessIterator->first.get_index_number(VA_GroupName_IDX))) << "\n");

        const OctetString *rowContextName = dynamic_cast<const OctetString *> 
            (accessIterator->first.get_index_number(VA_ContextPrefix_IDX));

        //      the union of these two sets:
        //       - the set with identical vacmAccessContextPrefix
        //       - the set of entries with vacmAccessContextMatch
        //         value of 'prefix' and matching
        //         vacmAccessContextPrefix
        Integer32 *rowContextMatch = 
            accessIterator->second->get_column_Integer32(VA_ContextMatch_COL);
	// XXXB backward compatibility:
	// The following .compare change is from gcc 2.x -> 3.x
	// If backwards compatibility is desired, this will probably have
	// to be ifdef'd ... sigh.
//         if ((rowContextName && (*rowContextName == *contextName)) ||
//             (rowContextMatch && (*(rowContextMatch) == VACM_prefix) && 
//              ((string) *rowContextName).compare((string) *contextName, 0, 
//                                                 rowContextName->size(),
        if ( (rowContextName && (*rowContextName == *contextName))  ||
	     ( rowContextMatch && (*(rowContextMatch) == VACM_prefix) && 
	       OPENSNMP_UTILITIES::comparePrefix( string(*rowContextName),
						  string(*contextName) )
	     )
	   )  {
            //      intersected with the union of these two sets:
            //       - the set of entries with identical
            //         vacmSecurityModel
            //       - the set of entries with vacmSecurityModel
            //         value of 'any'
            const Integer32 *rowSecurityModel = dynamic_cast<const Integer32 *> 
                (accessIterator->first.get_index_number(VA_SecurityModel_IDX));
            if (rowSecurityModel && 
                (*rowSecurityModel == *(msg->get_securityModel()) ||
                 *rowSecurityModel == VACM_any)) {

                //      intersected with the set of entries with
                //      vacmAccessSecurityLevel value less than or equal
                //      to the requested securityLevel

                const Integer32 *rowSecurityLevel = 
                    dynamic_cast<const Integer32 *> 
                    (accessIterator->first.get_index_number(VA_SecurityLevel_IDX));
                DEBUG9(debugObj, "is level " <<  \
                    (int) (*(rowSecurityLevel)) << " less than or equal to " << ((int) *(msg->get_securityLevel())) << "?\n");

                if (*rowSecurityLevel <= 
                    USMSecLevel_to_VACMSecLevel(*(msg->get_securityLevel()))) {
                    validRows.push_back(pair<snmpIndexes, snmpRow *>
                                        (accessIterator->first,
                                         accessIterator->second));
                }
            }
        }
    }


    list<pair<snmpIndexes, snmpRow *> >::iterator finalAnswer;
    //  2) if this set has only one member, we're done
    if (validRows.size() == 1) {
        finalAnswer = validRows.begin();
    } else if (validRows.size() == 0) {
        return returnError(msg, VACMError::NOACCESSENTRY);
    } else {
        // otherwise, it comes down to deciding how to weight
        // the preferences between ContextPrefixes,
        // SecurityModels, and SecurityLevels as follows:
        list<pair<snmpIndexes, snmpRow *> >::iterator rowsIterator, tmpi;
        for(rowsIterator = validRows.begin(); rowsIterator != validRows.end();
            rowsIterator++) {
            // a) if the subset of entries with securityModel
            //    matching the securityModel in the message is
            //    not empty, then discard the rest.
            Integer32 *rowModel = dynamic_cast<Integer32 *> 
                (rowsIterator->second->get_column(VA_SecurityModel_COL));
            Integer32 *msgModel = msg->get_securityModel();
        
            if (rowModel && msgModel && *(rowModel) == *(msgModel))
                for(tmpi = validRows.begin(); tmpi != validRows.end(); tmpi++) {
                    const Integer32 *rowSecurityModel = 
                        tmpi->second->get_column_Integer32(VA_SecurityModel_COL);
                    if (rowSecurityModel && msg->get_securityModel() &&
                        *rowSecurityModel != *msgModel)
                        validRows.erase(tmpi);
                }
            
            // b) if the subset of entries with
            //    vacmAccessContextPrefix matching the contextName
            //    in the message is not empty,
            //    then discard the rest
            const OctetString *rowContext = dynamic_cast<const OctetString *> 
                (rowsIterator->first.get_index_number(VA_ContextPrefix_IDX));
            if (rowContext && contextName && *(rowContext) == *contextName)
                for(tmpi = validRows.begin(); tmpi != validRows.end(); tmpi++) {
                    const OctetString *rowContextPrefix = 
                        dynamic_cast<const OctetString *> 
                        (tmpi->first.get_index_number(VA_ContextPrefix_IDX));
                    if (rowContextPrefix && *rowContextPrefix == *contextName)
                        validRows.erase(tmpi);
                }
        }
        
        // c) discard all entries with ContextPrefixes shorter
        //    than the longest one remaining in the set
        int longest=0;
        for(rowsIterator = validRows.begin(); rowsIterator != validRows.end();
            rowsIterator++) {
            if ((dynamic_cast<const OctetString *> (rowsIterator->first.get_index_number(VA_ContextPrefix_IDX)))->size() > longest)
                longest = (dynamic_cast<const OctetString *> (rowsIterator->first.get_index_number(VA_ContextPrefix_IDX)))->size();
        }

        for(rowsIterator = validRows.begin(); rowsIterator != validRows.end();
            rowsIterator++) {
            if (dynamic_cast<const OctetString *> ((rowsIterator->first.get_index_number(VA_ContextPrefix_IDX)))->size() < longest)
                validRows.erase(rowsIterator);
        }
            
        // d) select the entry with the highest securityLevel
        finalAnswer = validRows.begin();
        for(rowsIterator = (validRows.begin())++; 
            rowsIterator != validRows.end();
            rowsIterator++) {
            if (*(dynamic_cast<Integer32 *>(rowsIterator->second->get_column(VA_SecurityLevel_COL))) > *(dynamic_cast<Integer32 *> (finalAnswer->second->get_column(VA_SecurityLevel_COL))))
                finalAnswer = rowsIterator;
        }
    }
     
    // End text from SNMP-VIEW-BASED-ACM-MIB

    // 4) a) If the viewType is "read", then the read view is used for
    //       checking access rights.

    //    b) If the viewType is "write", then the write view is used for
    //       checking access rights.

    //    c) If the viewType is "notify", then the notify view is used
    //       for checking access rights.

    viewName = dynamic_cast<OctetString *> 
        (finalAnswer->second->get_column(msg->get_viewType()));
    if (viewName == NULL)
        return returnError(msg, VACMError::NOACCESSENTRY);

    DEBUG9(debugObj, "VACM: viewName found: " << (string) *viewName << "\n");

    //    If the view to be used is the empty view (zero length viewName)
    //    then an errorIndication (noSuchView) is returned to the calling
    //    module.

    if (viewName->size() == 0)
        return returnError(msg, VACMError::NOSUCHVIEW);

    // 5) a) If there is no view configured for the specified viewType,
    //       then an errorIndication (noSuchView) is returned to the calling
    //       module.

    snmpDataTable::iterator sti = MIBViewsTable->begin();
    // XXX: ick, linear search...
    // XXX: check Active rows only (all above tables too)
    // get to the start of the view tree with the same name
    while (sti != MIBViewsTable->end() &&
           *(dynamic_cast<const OctetString *> (sti->first.get_index_number(VVTF_vacmViewTreeFamilyViewName_COL))) != *viewName)
        sti++;

    // Begin text from SNMP-VIEW-BASED-ACM-MIB:

    //  To determine if a particular object instance is in
    //  a particular MIB view, compare the object instance's
    //  OBJECT IDENTIFIER with each of the MIB view's active
    //  entries in this table.  If none match, then the
    //  object instance is not in the MIB view.  If one or
    //  more match, then the object instance is included in,
    //  or excluded from, the MIB view according to the
    //  value of vacmViewTreeFamilyType in the entry whose
    //  value of vacmViewTreeFamilySubtree has the most
    //  sub-identifiers.  If multiple entries match and have
    //  the same number of sub-identifiers (when wildcarding
    //  is specified with the value of vacmViewTreeFamilyMask),
    //  then the lexicographically greatest instance of
    //  vacmViewTreeFamilyType determines the inclusion or
    //  exclusion.

    // XXX: saveoid will not multithread well...  need to clone it.
    // find the depest like OID
    const OID *saveoid = NULL;
    snmpRow *row = NULL;
    while (sti != MIBViewsTable->end() &&
           *(dynamic_cast<const OctetString *> 
             (sti->first.get_index_number(VVTF_vacmViewTreeFamilyViewName_COL)))
           == *viewName) {
        const OID *indexoid = dynamic_cast<const OID *> 
            (sti->first.get_index_number(VVTF_vacmViewTreeFamilySubtree_COL));
        if (indexoid->mincompare(*msg->get_variableName(),
                                 (const unsigned char *) ((string) *(dynamic_cast<OctetString *> (sti->second->get_column(VVTF_vacmViewTreeFamilyMask_COL)))).data()) == EQUAL_TO) {
            if (saveoid == NULL || saveoid->length() < indexoid->length() ||
                (saveoid->length() == indexoid->length() && 
                 *indexoid > *saveoid)) {
                row = sti->second;
                saveoid = indexoid;
            }
        }
        sti++;
    }
    
    if (!row)
        return returnError(msg, VACMError::NOSUCHVIEW);

    //    b) If the specified variableName (object instance) is not in the
    //       MIB view (see DESCRIPTION clause for vacmViewTreeFamilyTable in
    //       section 4), then an errorIndication (notInView) is returned to
    //       the calling module.

    Integer32 *type = dynamic_cast<Integer32 *> (row->get_column(4));
    if (((int) *type) == 2)
        return returnError(msg, VACMError::NOTINVIEW);

    //       Otherwise,

    //    c) The specified variableName is in the MIB view.
    //       A statusInformation of success (accessAllowed) is returned to
    //       the calling module.

    msg->set_statusInformation(new snmpStatusInfo(true, new VACMError(VACMError::ACCESSALLOWED)));
    DEBUG9(debugObj, "VACM: Access allowed\n");
    return msg;
} // isAccessAllowed DEBUG

// converts USM flags from the message to the VACM
int snmpVACMArchObj::USMSecLevel_to_VACMSecLevel(int lev) {
    switch(lev) {
        case 0:
            return VACM_noAuthNoPriv;
        case 1:
            return VACM_authNoPriv;
        case 3:
            return VACM_authPriv;
        default:
            return -1;
    }
}

void
snmpVACMArchObj::createTables() {
    snmpRegObj           theReg;
    if (crFIFO == NULL) {
        if ((crFIFO = theReg.findArchFIFO(SNMP_CR_ARCH_NAME)) == NULL) {
            DEBUG9(debugObj, "VACM-MIB: failed to find the CR FIFO\n");
            // do not exit, this is not an error.
        }
    }

    create_securityToGroupTable();
    create_vacmAccessTable();
    create_vacmViewTreeFamilyTable();
    create_vacmContextTable();
}

void
snmpVACMArchObj::create_securityToGroupTable() {
  snmpColumnAllocatorList *calist;
  snmpRow::defaultValueList *dvlist;
  columnStorageMap *csmap;
  snmpCRRegisterTable *regMsg;
  snmpRegObj           theReg;

  //
  // create the definition of the securityToGroupTable
  //
  //    +--vacmSecurityToGroupTable(2)
  //    |  |
  //    |  +--vacmSecurityToGroupEntry(1)
  //    |     |
  //    |     +-- ---- Integer   vacmSecurityModel(1)
  //    |     |        Textual Convention: SnmpSecurityModel
  //    |     |        Range: 1..2147483647
  //    |     +-- ---- String    vacmSecurityName(2)
  //    |     |        Textual Convention: SnmpAdminString
  //    |     |        Size: 1..32
  //    |     +-- CR-- String    vacmGroupName(3)
  //    |     |        Textual Convention: SnmpAdminString
  //    |     |        Size: 1..32
  //    |     +-- CR-- EnumVal   vacmSecurityToGroupStorageType(4)
  //    |     |        Textual Convention: StorageType
  //    |     |        Values: other(1), volatile(2), nonVolatile(3), 
  //                           permanent(4), readOnly(5)
  //    |     +-- CR-- EnumVal   vacmSecurityToGroupStatus(5)
  //    |              Textual Convention: RowStatus
  //    |              Values: active(1), notInService(2), notReady(3), 
  //                           createAndGo(4), createAndWait(5), destroy(6)
  list<berTag> lb;
  lb.push_back(BER_TAG_INTEGER32);
  lb.push_back(BER_TAG_OCTET_STRING);
  snmpIndexes si(lb);
  snmpVACMArchObj::securityToGroupTable = new snmpDataTable(si);
  
#ifdef NO_PERSISTENCE
  snmpRow *sr;

  // necessary info
    
  // add a row to the table
  sr = new snmpRow();
  si.set_index_number(snmpVACMArchObj::VS_vacmSecurityModel_COL, new Integer32(snmpVACMArchObj::VACM_USM));
  si.set_index_number(snmpVACMArchObj::VS_vacmSecurityName_COL, new OctetString("newUser"));
  sr->set_column(snmpVACMArchObj::VS_vacmGroupName_COL, OctetString("aGroup"));
  sr->set_column(snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL, 
		 snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_READONLY));
  // XXX: memory leak, the object is cloned
  sr->set_column(snmpVACMArchObj::VS_vacmSecurityToGroupStatus_COL, 
		 snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_ACTIVE,
			       snmpVACMArchObj::securityToGroupTable, sr,
			       new snmpRowStatusCheckColsExist(snmpVACMArchObj::VS_vacmGroupName_COL, snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL)));
  snmpVACMArchObj::securityToGroupTable->add_row(si, sr);
#endif

  DEBUG9(debugObj, "VACM: registering securityToGroupTable\n");
  
  // create a column allocator which knows which column is the
  // snmpRowStatus column.
  calist = new snmpColumnAllocatorList();
  (*calist)[snmpVACMArchObj::VS_vacmSecurityToGroupStatus_COL] =
    new snmpRowStatusColumnAllocator
    (new snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT, 
		       snmpVACMArchObj::securityToGroupTable, NULL, 
		       new snmpRowStatusCheckColsExist
		       (snmpVACMArchObj::VS_vacmGroupName_COL,
			snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL)));

  (*calist)[snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL] =
    new snmpRowStorageColumnAllocator();
  
    // assign default values for newly created rows
    dvlist = new snmpRow::defaultValueList();
    (*dvlist)[snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL] =
      new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);

    // assign default persistent columns
    csmap = new columnStorageMap();
    (*csmap)[snmpVACMArchObj::VS_vacmSecurityModel_COL] = true;
    (*csmap)[snmpVACMArchObj::VS_vacmSecurityName_COL] = true;
    (*csmap)[snmpVACMArchObj::VS_vacmGroupName_COL] = true;
    (*csmap)[snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL] = true;
    (*csmap)[snmpVACMArchObj::VS_vacmSecurityToGroupStatus_COL] = true;


    // register the table
    if (crFIFO) {
        regMsg = new snmpCRRegisterTable(securityToGroupTable,
                                         new OID(".1.3.6.1.6.3.16.1.2"),
                                         new OctetString("none"),
                                         5, 3, snmpDataTable::ALLWRITABLE,
                                         new snmpPersistentRowManager(
					   snmpVACMArchObj::VS_vacmSecurityToGroupStorageType_COL,
					   csmap, NULL, 
                                           new snmpRowAllocator(calist, dvlist)));
        crFIFO->push(regMsg);
    }
}

void
snmpVACMArchObj::create_vacmAccessTable() {
  snmpColumnAllocatorList *calist;
  snmpRow::defaultValueList *dvlist;
  columnStorageMap *csmap;
  snmpCRRegisterTable *regMsg;
  snmpRegObj           theReg;

  // create the definition for the vacmAccessTable
  //    |  +--vacmAccessTable(4)
  //    |  |  |
  //    |  |  +--vacmAccessEntry(1)
  //    |  |     |   ---- inherited index: vacmGroupName
  //    |  |     |
  //    |  |     +-- ---- String    vacmAccessContextPrefix(1)
  //    |  |     |        Textual Convention: SnmpAdminString
  //    |  |     |        Size: 0..32
  //    |  |     +-- ---- Integer   vacmAccessSecurityModel(2)
  //    |  |     |        Textual Convention: SnmpSecurityModel
  //    |  |     |        Range: 0..2147483647
  //    |  |     +-- ---- EnumVal   vacmAccessSecurityLevel(3)
  //    |  |     |        Textual Convention: SnmpSecurityLevel
  //    |  |     |        Values: noAuthNoPriv(1), authNoPriv(2), authPriv(3)
  //    |  |     +-- CR-- EnumVal   vacmAccessContextMatch(4)
  //    |  |     |        Values: exact(1), prefix(2)
  //    |  |     +-- CR-- String    vacmAccessReadViewName(5)
  //    |  |     |        Textual Convention: SnmpAdminString
  //    |  |     |        Size: 0..32
  //    |  |     +-- CR-- String    vacmAccessWriteViewName(6)
  //    |  |     |        Textual Convention: SnmpAdminString
  //    |  |     |        Size: 0..32
  //    |  |     +-- CR-- String    vacmAccessNotifyViewName(7)
  //    |  |     |        Textual Convention: SnmpAdminString
  //    |  |     |        Size: 0..32
  //    |  |     +-- CR-- EnumVal   vacmAccessStorageType(8)
  //    |  |     |        Textual Convention: StorageType
  //    |  |     |        Values: other(1), volatile(2), nonVolatile(3), permanent(4), readOnly(5)
  //    |  |     +-- CR-- EnumVal   vacmAccessStatus(9)
  //    |  |              Textual Convention: RowStatus
  //    |  |              Values: active(1), notInService(2), notReady(3), createAndGo(4), createAndWait(5), destroy(6)

  list<berTag> lb;
  lb.push_back(BER_TAG_OCTET_STRING);
  lb.push_back(BER_TAG_OCTET_STRING);
  lb.push_back(BER_TAG_INTEGER32);
  lb.push_back(BER_TAG_INTEGER32);
  snmpIndexes si(lb);
  snmpVACMArchObj::accessTable = new snmpDataTable(si);
    
#ifdef NO_PERSISTENCE
  // add a row to the table
  snmpRow *sr = new snmpRow();
  si.set_index_number(snmpVACMArchObj::VA_GroupName_IDX,
		      new OctetString("aGroup"));
  si.set_index_number(snmpVACMArchObj::VA_ContextPrefix_IDX,
		      new OctetString(""));
  si.set_index_number(snmpVACMArchObj::VA_SecurityModel_IDX,
		      new Integer32(snmpVACMArchObj::VACM_USM));
  si.set_index_number(snmpVACMArchObj::VA_SecurityLevel_IDX,
		      new Integer32(snmpVACMArchObj::VACM_noAuthNoPriv));
  sr->set_column(snmpVACMArchObj::VA_ContextMatch_COL,
		 Integer32(snmpVACMArchObj::VACM_exact));
  sr->set_column(snmpVACMArchObj::VA_ReadViewName_COL,
		 OctetString("testview"));
  sr->set_column(snmpVACMArchObj::VA_WriteViewName_COL,
		 OctetString("testview"));
  sr->set_column(snmpVACMArchObj::VA_NotifyViewName_COL,
		 OctetString("testview"));
  sr->set_column(snmpVACMArchObj::VA_StorageType_COL,
		 snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_READONLY));
  sr->set_column(snmpVACMArchObj::VA_Status_COL, 
		 snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_ACTIVE,
			       snmpVACMArchObj::accessTable, sr));
  snmpVACMArchObj::accessTable->add_row(si, sr);
#endif

  DEBUG9(debugObj, "VACM: registering accessTable\n");

  calist = new snmpColumnAllocatorList();
  (*calist)[snmpVACMArchObj::VA_Status_COL] =
    new snmpRowStatusColumnAllocator
    ( new snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT, 
			snmpVACMArchObj::accessTable));
  (*calist)[snmpVACMArchObj::VA_StorageType_COL] =
    new snmpRowStorageColumnAllocator();

  dvlist = new snmpRow::defaultValueList();
  (*dvlist)[snmpVACMArchObj::VA_ContextMatch_COL] = new Integer32(snmpVACMArchObj::VACM_exact);
  (*dvlist)[snmpVACMArchObj::VA_ReadViewName_COL] = new OctetString("");
  (*dvlist)[snmpVACMArchObj::VA_WriteViewName_COL] = new OctetString("");
  (*dvlist)[snmpVACMArchObj::VA_NotifyViewName_COL] = new OctetString("");
  (*dvlist)[snmpVACMArchObj::VA_StorageType_COL] =
    new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);

    // assign default persistent columns
    csmap = new columnStorageMap();
    (*csmap)[snmpVACMArchObj::VA_ContextPrefix_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_SecurityModel_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_SecurityLevel_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_ContextMatch_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_ReadViewName_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_WriteViewName_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_NotifyViewName_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_StorageType_COL] = true;
    (*csmap)[snmpVACMArchObj::VA_Status_COL] = true;

    // register the table
    if (crFIFO) {
        regMsg = new snmpCRRegisterTable(accessTable,
                                         new OID(".1.3.6.1.6.3.16.1.4"),
                                         new OctetString("none"),
                                         9, 4, snmpDataTable::ALLWRITABLE,
                                         new snmpPersistentRowManager(
					   snmpVACMArchObj::VA_StorageType_COL,
					   csmap, NULL, 
                                           new snmpRowAllocator(calist, dvlist)));
        crFIFO->push(regMsg);
    }
}

void
snmpVACMArchObj::create_vacmViewTreeFamilyTable() {
  snmpColumnAllocatorList *calist;
  snmpRow::defaultValueList *dvlist;
  columnStorageMap *csmap;
  snmpCRRegisterTable *regMsg;
  snmpRegObj           theReg;
  // XXX: vacmViewSpinLock

  // create the definition for the vacmViewTreeFamilyTable
  //
  // +--vacmViewTreeFamilyTable(2)
  //    |
  //    +--vacmViewTreeFamilyEntry(1)
  //       |
  //       +-- ---- String    vacmViewTreeFamilyViewName(1)
  //       |        Textual Convention: SnmpAdminString
  //       |        Size: 1..32
  //       +-- ---- ObjID     vacmViewTreeFamilySubtree(2)
  //       +-- CR-- String    vacmViewTreeFamilyMask(3)
  //       |        Size: 0..16
  //       +-- CR-- EnumVal   vacmViewTreeFamilyType(4)
  //       |        Values: included(1), excluded(2)
  //       +-- CR-- EnumVal   vacmViewTreeFamilyStorageType(5)
  //       |        Textual Convention: StorageType
  //       |        Values: other(1), volatile(2), nonVolatile(3), permanent(4), readOnly(5)
  //       +-- CR-- EnumVal   vacmViewTreeFamilyStatus(6)
  //                Textual Convention: RowStatus
  //                Values: active(1), notInService(2), notReady(3), createAndGo(4), createAndWait(5), destroy(6)

  list<berTag> lb;
  lb.push_back(BER_TAG_OCTET_STRING);
  lb.push_back(BER_TAG_OBJECT_ID);
  snmpIndexes si(lb);
  snmpVACMArchObj::MIBViewsTable = new snmpDataTable(si);
    
#ifdef NO_PERSISTENCE
  // add a row to the table
  snmpRow *sr = new snmpRow();
  si.set_index_number(snmpVACMArchObj::VVTF_vacmViewTreeFamilyViewName_COL, 
		      new OctetString("testview"));
  si.set_index_number(snmpVACMArchObj::VVTF_vacmViewTreeFamilySubtree_COL, 
		      new OID(".1.3.6.1")); // pretty much everything SNMP
  char val = 0xf0;
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyMask_COL,
		 OctetString(&val, 1));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyType_COL, 
		 Integer32(snmpVACMArchObj::VACM_included));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL,
		 snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_READONLY));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyStatus_COL, 
		 snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_ACTIVE,
			       snmpVACMArchObj::MIBViewsTable, sr));
  snmpVACMArchObj::MIBViewsTable->add_row(si, sr);

  // exclude something now:
  sr = new snmpRow();
  si.set_index_number(snmpVACMArchObj::VVTF_vacmViewTreeFamilySubtree_COL, 
		      new OID(".1.3.6.1.2.1.1.5")); // sysName
  val = 0xff;
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyMask_COL,
		 OctetString(&val, 1));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyType_COL, 
		 Integer32(snmpVACMArchObj::VACM_excluded));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL,
		 snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_READONLY));
  sr->set_column(snmpVACMArchObj::VVTF_vacmViewTreeFamilyStatus_COL, 
		 snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_ACTIVE,
			       snmpVACMArchObj::MIBViewsTable, sr));
  snmpVACMArchObj::MIBViewsTable->add_row(si, sr);
#endif

  DEBUG9(debugObj, "VACM: registering MIBViewsTable\n");

  calist = new snmpColumnAllocatorList();
  (*calist)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyStatus_COL] =
    new snmpRowStatusColumnAllocator
    (new snmpRowStatus(SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT, 
		       snmpVACMArchObj::MIBViewsTable));
  (*calist)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL] =
    new snmpRowStorageColumnAllocator();

  dvlist = new snmpRow::defaultValueList();
  (*dvlist)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyMask_COL] = new OctetString("");
  (*dvlist)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyType_COL] = new Integer32(snmpVACMArchObj::VACM_included);
  (*dvlist)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL] = 
    new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);

    // assign default persistent columns
    csmap = new columnStorageMap();
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyViewName_COL] = true;
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilySubtree_COL] = true;
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyMask_COL] = true;
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyType_COL] = true;
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL] = true;
    (*csmap)[snmpVACMArchObj::VVTF_vacmViewTreeFamilyStatus_COL] = true;

    // register the table
    if (crFIFO) {
        regMsg = new snmpCRRegisterTable(MIBViewsTable,
                                         new OID(".1.3.6.1.6.3.16.1.5.2"),
                                         new OctetString("none"),
                                         6, 3, snmpDataTable::ALLWRITABLE,
                                         new snmpPersistentRowManager(
                                           snmpVACMArchObj::VVTF_vacmViewTreeFamilyStorageType_COL,
					   csmap, NULL, 
                                           new snmpRowAllocator(calist, dvlist)));
        crFIFO->push(regMsg);
    }
}


void
snmpVACMArchObj::create_vacmContextTable() {
  // create the definition for the vacmContextTable
  //
  // +--vacmContextTable(1)
  //    |
  //    +--vacmContextEntry(1)
  //       |
  //       +-- -R-- String    vacmContextName(1)
  //                Textual Convention: SnmpAdminString
  //                Size: 0..32

  list<berTag> lb;
  lb.push_back(BER_TAG_OCTET_STRING);
  snmpIndexes si(lb);
  contextTable = new snmpDataTable(si);
  snmpCRRegisterTable *regMsg;
  snmpRegObj           theReg;

  // add a row to the table
  snmpRow *sr = new snmpRow();
  si.set_index_number(1, new OctetString(""));
  sr->set_column(1, OctetString(""));
  snmpVACMArchObj::contextTable->add_row(si, sr);

  DEBUG9(debugObj, "VACM: registering contextTable\n");
  if (crFIFO) {
      regMsg = new snmpCRRegisterTable(contextTable,
                                       new OID(".1.3.6.1.6.3.16.1.1"),
                                       new OctetString("none"),
                                       1, 1, snmpDataTable::READ_ONLY);
      crFIFO->push(regMsg);
  }
}

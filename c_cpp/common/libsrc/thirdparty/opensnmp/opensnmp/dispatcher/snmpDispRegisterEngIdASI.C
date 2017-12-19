//
// snmpDispHandleInMsg implememntation
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpDispRegisterEngIdASI.H"
#include "debug.H"

snmpDispRegisterEngIdASI::snmpDispRegisterEngIdASI()  {
  DEBUGCREATE(debugObj, "snmpDispRegisterEngIdASI");
  DEBUG9(debugObj, "Disp:snmpDispRegisterEngIdASI contructor1" << endl);
  this->contextEngineID    = 0;
  this->pduTypeSet         = 0;
  this->statusInformation  = 0;
  this->lAddressSet        = 0;
}

snmpDispRegisterEngIdASI::snmpDispRegisterEngIdASI(
			   OctetString      *contextEngineID, // = NULL, 
			   PDU_Type         *pduType,         // = NULL,
			   snmpFIFOObj      *theFIFO,         // = NULL,
			   TransportAddress *listenAddress,   // = NULL,
			   snmpSynchDataObj *newData,         // = NULL,
			   snmpMessageObj   *copyFromMsg,     // = NULL,
 			   int               newID) :         // = 0
  snmpMessageObj(copyFromMsg,newID,newData,theFIFO)
{
  DEBUGCREATE(debugObj, "snmpDispRegisterEngIdASI");
  DEBUG9(debugObj, "Disp:snmpDispRegisterEngIdASI contructor2" << endl);
  this->contextEngineID = contextEngineID;
  this->pduTypeSet = new set<PDU_Type>();
  this->pduTypeSet->insert(*pduType);
  if (listenAddress != NULL) {
    this->lAddressSet = new set<TransportAddress>;
    this->lAddressSet->insert(*listenAddress);
  }
  else {
    this->lAddressSet = create_tAdd_from_pduSet(this->pduTypeSet);
  }
  this->statusInformation  = 0;
}

snmpDispRegisterEngIdASI::snmpDispRegisterEngIdASI(
			   OctetString      *contextEngineID, //= NULL 
			   set<PDU_Type>    *pduTypeSet,      //= NULL
			   snmpFIFOObj      *theFIFO,         //= NULL
			   set<TransportAddress> *listenAddresses, //= NULL
			   snmpSynchDataObj *newData,         //= NULL
			   snmpMessageObj   *copyFromMsg,     //= NULL
 			   int               newID) :         // = 0
  snmpMessageObj(copyFromMsg,newID,newData,theFIFO)
{
  DEBUGCREATE(debugObj, "snmpDispRegisterEngIdASI");
  DEBUG9(debugObj, "Disp:snmpDispRegisterEngIdASI contructor3" << endl);
  this->contextEngineID = contextEngineID;
  this->pduTypeSet = pduTypeSet;
  if (listenAddresses != NULL) {
    this->lAddressSet = listenAddresses;
  }
  else {
    this->lAddressSet = create_tAdd_from_pduSet(this->pduTypeSet);
  }
  this->statusInformation  = 0;
}

snmpDispRegisterEngIdASI::~snmpDispRegisterEngIdASI()  {
  DEBUG9(debugObj, "Disp:~snmpDispRegisterEngIdASI destructor" << endl);

  if (this->contextEngineID == 0)   delete this->contextEngineID;
  if (this->pduTypeSet == 0)        delete this->pduTypeSet;
  if (this->lAddressSet == 0)       delete this->lAddressSet;
  if (this->statusInformation == 0) delete this->statusInformation;

  DEBUGDESTROY(debugObj);
}


set<TransportAddress> *
snmpDispRegisterEngIdASI::create_tAdd_from_pduSet(set<PDU_Type> *pSet) {
  set<TransportAddress> *ret = new set<TransportAddress>;
  set<PDU_Type>::iterator pi;
  bool cr_found = false, nr_found = false;

 for(pi = pSet->begin(); pi != pSet->end(); pi++) {
   if ( !cr_found && 
	( (*pi ==  BER_TAG_PDU_GET) || (*pi == BER_TAG_PDU_GETNEXT) ||
	  (*pi == BER_TAG_PDU_SET) || (*pi == BER_TAG_PDU_GETBULK) )
	) {
     ret->insert(TransportAddress(short(161), "127.0.0.1",
				  SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP));
     cr_found = true;
   }
   else if ( !nr_found &&
	     ( (*pi ==  BER_TAG_PDU_TRAP2) || (*pi == BER_TAG_PDU_INFORM) ||
	       (*pi == BER_TAG_PDU_TRAP) )
	     ) {
     ret->insert(TransportAddress(short(162), "127.0.0.1",
				  SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP));
      nr_found = true;
   }
 }
 return ret;
}


void
snmpDispRegisterEngIdASI::set_contextEngineID(OctetString  *contextEngineID) {
  if (this->contextEngineID)
    delete this->contextEngineID;
  this->contextEngineID = contextEngineID;
}

OctetString  *
snmpDispRegisterEngIdASI::get_contextEngineID(bool extract) {
  OctetString  *ret = contextEngineID;
  if (extract)
    contextEngineID = NULL;
  return ret;

}

void
snmpDispRegisterEngIdASI::set_pduTypeSet(set<PDU_Type>  *pduTypeSet) {
  if (this->pduTypeSet)
    delete this->pduTypeSet;
  this->pduTypeSet = pduTypeSet;
}

set<PDU_Type> *
snmpDispRegisterEngIdASI::get_pduTypeSet(bool extract) {
  set<PDU_Type> *ret = pduTypeSet;
  if (extract)
    pduTypeSet = NULL;
  return ret;
}


void
snmpDispRegisterEngIdASI::set_lAddressSet(set<TransportAddress>  *lAddressSet)
{
  if (this->lAddressSet)
    delete this->lAddressSet;
  this->lAddressSet = lAddressSet;
}

set<TransportAddress> *
snmpDispRegisterEngIdASI::get_lAddressSet(bool extract) {
  set<TransportAddress> *ret = lAddressSet;
  if (extract)
    lAddressSet = NULL;
  return ret;
}


void
snmpDispRegisterEngIdASI::set_statusInformation(snmpStatusInfo  *statusInformation) {
  if (this->statusInformation)
    delete this->statusInformation;
  this->statusInformation = statusInformation;
}

snmpStatusInfo  *
snmpDispRegisterEngIdASI::get_statusInformation(bool extract) {
  snmpStatusInfo  *ret = statusInformation;
  if (extract)
    statusInformation = NULL;
  return ret;
}


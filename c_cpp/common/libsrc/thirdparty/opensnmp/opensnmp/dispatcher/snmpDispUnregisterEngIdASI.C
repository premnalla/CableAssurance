//
// snmpDispHandleInMsg implememntation
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpDispUnregisterEngIdASI.H"
#include "debug.H"

snmpDispUnregisterEngIdASI::snmpDispUnregisterEngIdASI()  {
  DEBUGCREATE_L(debugObj, "snmpDispUnregisterEngIdASI");
  this->contextEngineID   = NULL;
  this->pduTypeSet        = NULL;
  this->statusInformation = NULL;
  DEBUG9_L(debugObj, "Disp:snmpDispUnregisterEngIdASI contructor1" << endl);
}

snmpDispUnregisterEngIdASI::snmpDispUnregisterEngIdASI(
			   OctetString      *contextEngineID, //= NULL 
			   PDU_Type         *pduType,         //= NULL
			   snmpFIFOObj      *theFIFO,         //= NULL
			   snmpSynchDataObj *newData,         //= NULL
			   snmpMessageObj   *copyFromMsg,     //= NULL
 			   int               newID) :         //= 0
  snmpMessageObj(copyFromMsg,newID,newData,theFIFO)
{
  DEBUGCREATE_L(debugObj, "snmpDispUnregisterEngIdASI");
  DEBUG9_L(debugObj, "Disp:snmpDispUnregisterEngIdASI() contructor2" << endl);
  this->contextEngineID = contextEngineID;
  this->pduTypeSet = new set<PDU_Type>();
  this->pduTypeSet->insert(*pduType);
  this->statusInformation = NULL;
}

snmpDispUnregisterEngIdASI::snmpDispUnregisterEngIdASI(
			   OctetString      *contextEngineID, //= NULL 
			   set<PDU_Type>   *pduTypeSet,       //= NULL
			   snmpFIFOObj      *theFIFO,         //= NULL
			   snmpSynchDataObj *newData,         //= NULL
			   snmpMessageObj   *copyFromMsg,     //= NULL
 			   int               newID) :         //=0
  snmpMessageObj(copyFromMsg,newID,newData,theFIFO)
{
  DEBUGCREATE_L(debugObj, "snmpDispUnregisterEngIdASI");
  DEBUG9_L(debugObj, "Disp:snmpDispUnregisterEngIdASI contructor3" << endl);
  this->contextEngineID = contextEngineID;
  this->pduTypeSet = pduTypeSet;
  this->statusInformation = NULL;
}


snmpDispUnregisterEngIdASI::~snmpDispUnregisterEngIdASI()  {
  DEBUGCREATE_L(debugObj, "snmpDispUnregisterEngIdASI");
  DEBUG9_L(debugObj, "Disp:~snmpDispUnregisterEngIdASI destructor" << endl);

  if (this->contextEngineID == 0)   delete this->contextEngineID;
  if (this->pduTypeSet == 0)        delete this->pduTypeSet;
  if (this->statusInformation == 0) delete this->statusInformation;
}

void
snmpDispUnregisterEngIdASI::set_contextEngineID(OctetString  *contextEngineID)
{
  if (this->contextEngineID)
    delete this->contextEngineID;
  this->contextEngineID = contextEngineID;
}

OctetString  *
snmpDispUnregisterEngIdASI::get_contextEngineID(bool extract) {
  OctetString  *ret = contextEngineID;
  if (extract)
    contextEngineID = NULL;
  return ret;
}

void
snmpDispUnregisterEngIdASI::set_pduTypeSet(set<PDU_Type>  *pduTypeSet) {
  if (this->pduTypeSet)
    delete this->pduTypeSet;
  this->pduTypeSet = pduTypeSet;
}

set<PDU_Type>  *
snmpDispUnregisterEngIdASI::get_pduTypeSet(bool extract) {
  set<PDU_Type>  *ret = pduTypeSet;
  if (extract)
    pduTypeSet = NULL;
  return ret;
}

void
snmpDispUnregisterEngIdASI::set_statusInformation(snmpStatusInfo  *statusInformation) {
  if (this->statusInformation)
    delete this->statusInformation;
  this->statusInformation = statusInformation;
}

snmpStatusInfo  *
snmpDispUnregisterEngIdASI::get_statusInformation(bool extract) {
  snmpStatusInfo  *ret = statusInformation;
  if (extract)
    statusInformation = NULL;
  return ret;
}


#ifndef SNMPDISPUNREGISTERENGIDASI_H
#define SNMPDISPUNREGISTERENGIDASI_H

#include <set>

#include "asnDataTypes.H"
#include "snmpFIFOObj.H"
#include "snmpSynchDataObj.H"
#include "snmpStatusInfo.H"
#include "snmpMessageObj.H"


class snmpDispUnregisterEngIdASI : public snmpMessageObj {

private:
  OctetString       *contextEngineID;
  set<PDU_Type>     *pduTypeSet;
  
  snmpStatusInfo    *statusInformation;

public:
   
  snmpDispUnregisterEngIdASI();

  snmpDispUnregisterEngIdASI(OctetString      *contextEngineID = NULL, 
			   PDU_Type         *pduType     = NULL,
			   snmpFIFOObj      *theFIFO     = NULL,
			   snmpSynchDataObj *newData     = NULL,
			   snmpMessageObj   *copyFromMsg = NULL,
 			   int               newID       = 0
			   );

  snmpDispUnregisterEngIdASI(OctetString    *contextEngineID = NULL, 
			   set<PDU_Type>   *pduTypeSet = NULL,
			   snmpFIFOObj      *theFIFO     = NULL,
			   snmpSynchDataObj *newData     = NULL,
			   snmpMessageObj   *copyFromMsg = NULL,
 			   int               newID       = 0
			   );

  virtual ~snmpDispUnregisterEngIdASI();

  void set_contextEngineID(OctetString  *contextEngineID);
  OctetString  *get_contextEngineID(bool extract=false);
  void set_pduTypeSet(set<PDU_Type>  *pduTypeSet);
  set<PDU_Type>  *get_pduTypeSet(bool extract=false);
  void set_statusInformation(snmpStatusInfo  *statusInformation);
  snmpStatusInfo  *get_statusInformation(bool extract=false);
};

#endif /*  SNMPDISPUNREGISTERENGIDASI_H  */

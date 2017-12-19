#ifndef SNMPDISPREGISTERENGIDASI_H
#define SNMPDISPREGISTERENGIDASI_H

#include <set>

#include "asnDataTypes.H"
#include "snmpFIFOObj.H"
#include "snmpSynchDataObj.H"
#include "snmpStatusInfo.H"
#include "snmpMessageObj.H"


class snmpDispRegisterEngIdASI : public snmpMessageObj {

private:
  OctetString           *contextEngineID;
  set<PDU_Type>         *pduTypeSet;
  set<TransportAddress> *lAddressSet;
  snmpStatusInfo        *statusInformation;
  DEBUGDECLARE(debugObj);

public:
   
  snmpDispRegisterEngIdASI();

  // register with the dispatcher for Eng ID, with PDU types.
  // a zero length engine ID (e.g. engID = new OctetString())
  // is a wildcard for all engine ID's. 
  // A listenAddress of Null will default to listening to a
  // TransportAddress depending on PDU type/combo thereof:
  // BER_TAG_PDU_GET, BER_TAG_PDU_GETNEXT, BER_TAG_PDU_SET, 
  // BER_TAG_PDU_GETBULK, : UDP port 161
  // BER_TAG_PDU_TRAP2, BER_TAG_PDU_INFORM, BER_TAG_PDU_TRAP
  // : UDP port 162
  //
  // for BER_TAG_PDU_REPORT BER_TAG_PDU_RESPONSE message types,
  // the randomly generated send port will be listened to automatically.
  // 
  // Any listenAdress(es) sent in otherwise will be the only ones listened
  // too except for the random send port.

  snmpDispRegisterEngIdASI(OctetString      *contextEngineID = NULL, 
			   PDU_Type         *pduType         = NULL,
			   snmpFIFOObj      *theFIFO         = NULL,
			   TransportAddress *listenAddress   = NULL,
			   snmpSynchDataObj *newData         = NULL,
			   snmpMessageObj   *copyFromMsg     = NULL,
 			   int               newID           = 0
			   );

  snmpDispRegisterEngIdASI(OctetString      *contextEngineID = NULL, 
			   set<PDU_Type>    *pduTypes        = NULL,
			   snmpFIFOObj      *theFIFO         = NULL,
			   set<TransportAddress> *listenAddresses = NULL,
			   snmpSynchDataObj *newData         = NULL,
			   snmpMessageObj   *copyFromMsg     = NULL,
 			   int               newID           = 0
			   );

  virtual ~snmpDispRegisterEngIdASI();

  set<TransportAddress> *create_tAdd_from_pduSet(set<PDU_Type> *pSet);

  void set_contextEngineID(OctetString  *contextEngineID);
  OctetString  *get_contextEngineID(bool extract=false);
  void set_pduTypeSet(set<PDU_Type>  *pduTypeSet);
  set<PDU_Type>  *get_pduTypeSet(bool extract=false);
  void set_lAddressSet(set<TransportAddress> *lAddressSet);
  set<TransportAddress> *get_lAddressSet(bool extract=false);
  void set_statusInformation(snmpStatusInfo  *statusInformation);
  snmpStatusInfo  *get_statusInformation(bool extract=false);
};

#endif /*  SNMPDISPREGISTERENGIDASI_H  */

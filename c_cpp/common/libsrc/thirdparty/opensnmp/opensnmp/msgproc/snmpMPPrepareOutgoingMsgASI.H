#ifndef SNMPMPPREPAREOUTGOINGMSGASI_H
#define SNMPMPPREPAREOUTGOINGMSGASI_H

#include "snmpStandardMessage.H"
#include "snmpStatusInfo.H"

#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"
#include "TransportAddress.H"
#include "snmpStatusInfo.H"
#include "BufferClass.H"
#include "snmpConstants.H"
using SNMP_CONSTANTS::TransportDomain;

class snmpMPPrepareOutgoingMsgASI : public snmpStandardMessage {
  
private:
  DEBUGDECLARE(debugObj);

public:
   
  snmpMPPrepareOutgoingMsgASI(snmpMessageObj   *copyFromMsg = NULL,
			      int               newID       = 0,
			      snmpSynchDataObj *newData     = NULL,
			      snmpFIFOObj      *theFIFO     = NULL  );


  snmpMPPrepareOutgoingMsgASI
  ( TransportDomain   *newTransportDomain,
    TransportAddress  *newTransportAddress,
    Integer32         *newMessageProcessingModel,
    Integer32         *newSecurityModel,
    OctetString       *newSecurityName,
    Integer32         *newSecurityLevel,

    // -----------------------------------------
    OctetString       *newContextEngineID,
    OctetString       *newContextName,
    PDU_Version       *newPDUVersion,

    PDU               *newMsgPDU,

    bool              *newExpectResponse,
    unsigned long     sendPduhandle,
    // -----------------------------------------

    BufferClass       *newOutMsg,
    Integer32         *newOutMsgLength,

    // -----------------------------------------

    snmpStatusInfo    *newStatusInformation,

    snmpSynchDataObj *newData     = NULL,
    snmpFIFOObj      *theFIFO     = NULL );

  snmpMPPrepareOutgoingMsgASI(snmpStandardMessage_internalData *intData,
			      snmpSynchDataObj          *newData = NULL,
			      snmpFIFOObj               *theFIFO = NULL );

  virtual ~snmpMPPrepareOutgoingMsgASI();

};

#endif /* SNMPMPPREPAREOUTGOINGMSGASI_H */

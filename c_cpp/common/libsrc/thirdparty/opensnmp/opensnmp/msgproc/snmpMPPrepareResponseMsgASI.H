#ifndef SNMPMPPREPARERESPONSEMSGASI_H
#define SNMPMPPREPARERESPONSEMSGASI_H

#include "snmpStandardMessage.H"
#include "OctetString.H"
#include "Integer32.H"
#include "snmpStatusInfo.H"
#include "PDU.H"
#include "BufferClass.H"
#include "TransportAddress.H"
#include "snmpConstants.H"
using SNMP_CONSTANTS::TransportDomain;

class snmpMPPrepareResponseMsgASI : public snmpStandardMessage {
  
private:
  DEBUGDECLARE(debugObj);
  
public:
   
  snmpMPPrepareResponseMsgASI(snmpMessageObj   *copyFromMsg = NULL,
			      int               newID       = 0,
			      snmpSynchDataObj *newData     = NULL,
			      snmpFIFOObj      *theFIFO     = NULL);

  snmpMPPrepareResponseMsgASI
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


    // -----------------------------------------
    Integer32         *newMaxSizeRespPDU,
    BufferClass       *newOutMsg,
    Integer32         *newOutMsgLength,

    // -----------------------------------------
    snmpObj           *newStateReference,


    snmpStatusInfo    *newStatusInformation,

    snmpSynchDataObj *newData     = NULL,
    snmpFIFOObj      *theFIFO     = NULL );

  snmpMPPrepareResponseMsgASI(snmpStandardMessage_internalData *intData,
			      snmpSynchDataObj          *newData = NULL,
			      snmpFIFOObj               *theFIFO = NULL );

  virtual ~snmpMPPrepareResponseMsgASI();

};

#endif /* SNMPMPPREPARERESPONSEMSGASI_H */

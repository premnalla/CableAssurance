#ifndef SNMPMPPREPAREDATAELEMENTSASI_H
#define SNMPMPPREPAREDATAELEMENTSASI_H

#include "snmpStandardMessage.H"
#include "snmpStatusInfo.H"
#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "TransportAddress.H"
#include "snmpConstants.H"
#include "BufferClass.H"

using  SNMP_CONSTANTS::TransportDomain;

class snmpMPPrepareDataElementsASI : public snmpStandardMessage {

public:
   
  snmpMPPrepareDataElementsASI(snmpMessageObj   *copyFromMsg = NULL,
			       int               newID       = 0,
			       snmpSynchDataObj *newData     = NULL,
			       snmpFIFOObj      *theFIFO     = NULL);

  snmpMPPrepareDataElementsASI
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
    PDU_Type          *newPDUType, 
    PDU               *newMsgPDU,


    // -----------------------------------------
    Integer32         *newMaxSizeRespPDU,
    BufferClass       *newOutMsg,
    Integer32         *newOutMsgLength,

    // -----------------------------------------
    snmpObj           *newStateReference,
    unsigned long      newSNMPPDUHandle,

    snmpStatusInfo    *newStatusInformation,

    snmpSynchDataObj *newData     = NULL,
    snmpFIFOObj      *theFIFO     = NULL );

  snmpMPPrepareDataElementsASI(snmpStandardMessage_internalData *intData,
			       snmpSynchDataObj          *newData = NULL,
			       snmpFIFOObj               *theFIFO = NULL );

  virtual ~snmpMPPrepareDataElementsASI();

};

#endif /* SNMPMPPREPAREDATAELEMENTSASI_H */

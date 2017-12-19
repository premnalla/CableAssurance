#ifndef SNMPMESSAGEPROCESSINGMSGOBJ_H
#define SNMPMESSAGEPROCESSINGMSGOBJ_H

#include "snmpMessageObj.H"
#include "snmpStatusInfo.H"
#include "snmpGlobalData.H"
#include "snmpConstants.H"
using SNMP_CONSTANTS::TransportDomain;

#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"

class snmpMessageProcessingMsgObj : public snmpMessageObj {
  
private:
  TransportDomain   *transportDomain;
  OctetString       *transportAddress;
  Integer32         *messageProcessingModel;
  Integer32         *securityModel;
  OctetString       *securityName;
  Integer32         *securityLevel;
  OctetString       *securityEngineID;
  // -----------------------------------------
  OctetString       *contextEngineID;
  OctetString       *contextName;
  Integer32         *pduVersion;
  PDU_Type          *pduType; 
  PDU               *msgPDU;
  ScopedPDU         *scopedPDU;
  bool              *expectedResponse;
  // -----------------------------------------
  Integer32         *maxSizeRespPDU;
  char              *outMsg;
  Integer32         *outMsgLength;
  OctetString       *securityParameters;
  // -----------------------------------------
  snmpObj           *stateReference;
  snmpObj           *securityStateReference;
  Integer32         *maxMsgSize;
  snmpStatusInfo    *statusInformation;
  snmpGlobalData    *globalData;

public:

  snmpMessageProcessingMsgObj(snmpMessageObj   *copyFromMsg = NULL,
			      int               newID       = 0,
			      snmpSynchDataObj *newData     = NULL,
			      snmpFIFOObj      *theFIFO     = NULL);

  snmpMessageProcessingMsgObj
  ( TransportDomain   *newTransportDomain,
    OctetString       *newTransportAddress,
    Integer32         *newMessageProcessingModel,
    Integer32         *newSecurityModel,
    OctetString       *newSecurityName,
    Integer32         *newSecurityLevel,
    OctetString       *newSecurityEngineID,
    // -----------------------------------------
    OctetString       *newContextEngineID,
    OctetString       *newContextName,
    Integer32         *newPDUVersion,
    PDU_Type          *newPDUType, 
    PDU               *newMsgPDU,
    ScopedPDU         *newScopedPDU,
    bool              *newExpectedResponse,
    // -----------------------------------------
    Integer32         *newMaxSizeRespPDU,
    char              *newOutMsg,
    Integer32         *newOutMsgLength,
    OctetString       *newSecurityParameters,
    // -----------------------------------------
    snmpObj           *newStateReference,
    snmpObj           *newSecurityStateReference,
    Integer32         *newMaxMsgSize,
    snmpStatusInfo    *newStatusInformation,
    snmpGlobalData    *newGlobalData,
    snmpSynchDataObj  *newData     = NULL,
    snmpFIFOObj       *theFIFO     = NULL );
  
  virtual ~snmpMessageProcessingMsgObj();

  virtual snmpMessageProcessingMsgObj *clone();

  // extract is passed as true for ownership of the variable.
  // That is, the variable is then no longer referenced by this object.
  // You own it, you delete it!
  TransportDomain *get_transportDomain(bool extract=false);
  bool            set_transportDomain(TransportDomain      *newTDomain);

  OctetString    *get_transportAddress(bool extract=false);
  bool            set_transportAddress(OctetString     *newTAddress);

  Integer32      *get_messageProcessingModel(bool extract=false);
  bool            set_messageProcessingModel(Integer32 *newProcModel);

  Integer32      *get_securityModel(bool extract=false);
  bool            set_securityModel(Integer32          *newSecModel);

  OctetString    *get_securityName(bool extract=false);
  bool            set_securityName(OctetString         *newSecName);

  Integer32      *get_securityLevel(bool extract=false);
  bool            set_securityLevel(Integer32          *newSecLevel);
  
  OctetString    *get_securityEngineID(bool extract=false);
  bool            set_securityEngineID(OctetString     *newSecEndID);

  // -----------------------------------------

  OctetString    *get_contextEngineID(bool extract=false);
  bool            set_contextEngineID(OctetString  *newID);

  OctetString    *get_contextName(bool extract=false);
  bool            set_contextName(OctetString      *newContextName);

  Integer32      *get_PDUVersion(bool extract=false);
  bool            set_PDUVersion(Integer32         *newPDUVersion);

  PDU_Type       *get_PDUType(bool extract=false);
  bool            set_PDUType(PDU_Type             *newPDUType); 
       
  PDU            *get_PDU(bool extract=false);
  bool            set_PDU(PDU                      *newPDU); 
      
  ScopedPDU      *get_scopedPDU(bool extract=false);
  bool            set_scopedPDU(ScopedPDU          *newScopedPDU); 
      
  bool           *get_expectedResponse(bool extract=false);
  bool            set_expectedResponse(bool        *expResponse);
      
  // -----------------------------------------

  Integer32      *get_maxSizeResponseScopedPDU(bool extract=false);
  bool            set_maxSizeResponseScopedPDU(Integer32  *newMaxRespSize);

  char           *get_wholeOutMsg(bool extract=false);
  bool            set_wholeOutMsg(char               *newOutMsg);

  Integer32      *get_wholeOutMsgLength(bool extract=false);
  bool            set_wholeOutMsgLength(Integer32    *newMsgLength);

  OctetString    *get_securityParameters(bool extract=false);
  bool            set_securityParameters(OctetString  *newSecParams);

  // -----------------------------------------

  snmpObj        *get_stateReference(bool extract=false);
  bool            set_stateReference(snmpObj  *newStateReference);

  snmpObj        *get_securityStateReference(bool extract=false);
  bool            set_securityStateReference(snmpObj  *newSecStateRef);

  Integer32      *get_maxMsgSize(bool extract=false);
  bool            set_maxMsgSize(Integer32  *newMsgLength);

  snmpStatusInfo *get_statusInformation(bool extract=false);
  bool            set_statusInformation(snmpStatusInfo *newStatus);

  snmpGlobalData *get_globalData(bool extract=false);
  bool            set_globalData(snmpGlobalData  *newGlobalData);

};


class snmpMPMsgObj         : virtual public snmpMessageProcessingMsgObj  {

public:

  snmpMPMsgObj();
  virtual ~snmpMPMsgObj();

  virtual void   *copy();
};

class snmpMPResponseMsgObj : virtual public snmpMessageProcessingMsgObj  {

public:

  snmpMPResponseMsgObj();
  virtual ~snmpMPResponseMsgObj();

  virtual void   *copy();
};


#endif /* SNMPMESSAGEPROCESSINGMSGOBJ_H */

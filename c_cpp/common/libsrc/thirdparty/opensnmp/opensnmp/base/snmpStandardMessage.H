#ifndef SNMPSTANDARDMESSAGE_H
#define SNMPSTANDARDMESSAGE_H

#include "snmpMessageObj.H"
#include "snmpStatusInfo.H"
#include "HeaderData.H"
#include "snmpConstants.H"
#include "asnDataTypes.H"
#include "PDU.H"
#include "TransportAddress.H"
#include "BufferClass.H"
#include "debug.H"
#include "snmpV3Message.H"

using  SNMP_CONSTANTS::TransportDomain;

class snmpStandardMessage_internalData {
public:
  snmpV3Message      v3Message;

  TransportDomain   *transportDomain;
  TransportAddress  *transportAddress;
  //  Integer32         *messageProcessingModel;  // msgVersion in v3message
  //  Integer32         *securityModel;  // header data
  OctetString       *securityName;
  Integer32         *securityLevel;
  OctetString       *securityEngineID;
  // -----------------------------------------
  //  OctetString       *contextEngineID; // Scoped PDU
  //  OctetString       *contextName; // Scoped PDU
  PDU_Version       *pduVersion;
  PDU_Type          *pduType; 
  //  PDU               *msgPDU; // Scoped PDU
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  ScopedPDU         *scopedPDU;
  bool              *expectResponse;
  // -----------------------------------------
  Integer32         *maxSizeResponsePDU;
  unsigned long      sendPduHandle;
  BufferClass       *outMsg;
  Integer32         *outMsgLength;
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  asnDataType       *securityParameters;
  // -----------------------------------------
  snmpObj           *stateReference;
  snmpObj           *securityStateReference;
  //  Integer32         *maxMsgSize;  // header data
  snmpStatusInfo    *statusInformation;
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  HeaderData        *globalData;

  snmpStandardMessage_internalData();
  virtual ~snmpStandardMessage_internalData();
  virtual snmpStandardMessage_internalData *clone();

  snmpV3Message &get_v3Message_ref();
  
};


#define DEFINE_SSM_PTR_ACCESSOR(TYPE, NAME)              \
   virtual TYPE *get_##NAME(bool extract = false) {      \
      TYPE *ret = NULL;                                  \
      if (intData != NULL)                               \
        ret = intData->NAME;                             \
      if ((extract) && (intData != NULL))                \
         intData->NAME = NULL;                           \
      return ret;                                        \
   };                                                    \
   virtual void set_##NAME(TYPE* NAME) {                 \
     if (intData == NULL)                                \
       intData = new snmpStandardMessage_internalData(); \
     if (intData->NAME)                                  \
         delete intData->NAME;                           \
     intData->NAME = NAME;                               \
   } 

#define DEFINE_SSM_ACCESSOR(TYPE, NAME)                  \
   virtual TYPE get_##NAME() {                           \
       return intData->NAME;                             \
   };                                                    \
   virtual void set_##NAME(const TYPE &NAME) {           \
     intData->NAME = NAME;                               \
   } 


// ***** DECLARE  snmpStandardMessage DECLARE *******

class snmpStandardMessage : public snmpMessageObj {

private:
  DEBUGDECLARE(mDebug);
  snmpStandardMessage_internalData *intData;

public:
   
  snmpStandardMessage(snmpMessageObj   *copyFromMsg = NULL,
		      int               newID       = 0,
		      snmpSynchDataObj *newData     = NULL,
		      snmpFIFOObj      *theFIFO     = NULL  );

  snmpStandardMessage
  (TransportDomain   *newTransportDomain,
   TransportAddress  *newTransportAddress,
   Integer32         *newMessageProcessingModel,
   Integer32         *newSecurityModel,
   OctetString       *newSecurityName,
   Integer32         *newSecurityLevel,
   OctetString       *newSecurityEngineID,
   // -----------------------------------------
   OctetString       *newContextEngineID,
   OctetString       *newContextName,
   PDU_Version       *newPDUVersion,
   PDU_Type          *newPDUType, 
   PDU               *newMsgPDU,
   ScopedPDU         *newScopedPDU,
   bool              *newExpectResponse,
   // -----------------------------------------
   Integer32         *newMaxSizeRespPDU,
   unsigned long      newSendPduHandle,
   BufferClass       *newOutMsg,
   Integer32         *newOutMsgLength,
   asnDataType       *newSecurityParameters,
   // -----------------------------------------
   snmpObj           *newStateReference,
   snmpObj           *newSecurityStateReference,
   Integer32         *newMaxMsgSize,
   snmpStatusInfo    *newStatusInformation,
   HeaderData        *newGlobalData,
   snmpMessageObj    *copyFromMsg               = NULL,
   int                newID                     = 0,
   snmpSynchDataObj  *newData                   = NULL,
   snmpFIFOObj       *theFIFO                   = NULL );

  snmpStandardMessage(snmpStandardMessage_internalData *intData,
		      snmpSynchDataObj                 *newData = NULL,
		      snmpFIFOObj                      *theFIFO = NULL );


  snmpStandardMessage(snmpStandardMessage &oldMsg);

  virtual ~snmpStandardMessage();

  virtual snmpStandardMessage *clone();

  // extract is passed as true for ownership of the variable.
  // That is, the var is then no longer referenced by this object.
  // You own it, you delete it!

  DEFINE_PTR_ACCESSOR(snmpStandardMessage_internalData, intData, );

  DEFINE_SSM_PTR_ACCESSOR(TransportDomain, transportDomain);
  DEFINE_SSM_PTR_ACCESSOR(TransportAddress, transportAddress);

  // message processing model is msgVersion in v3Message
  virtual Integer32 *get_messageProcessingModel(bool extract = false) {
    if (intData) return intData->v3Message.get_msgVersion(extract);
    else return NULL;
  };
  virtual void set_messageProcessingModel(Integer32 *newMessageProcessingModel)
  {
    if (!intData) intData = new snmpStandardMessage_internalData();
    intData->v3Message.set_msgVersion(newMessageProcessingModel);
  };
 // securityModel in HeaderData
  virtual Integer32 *get_securityModel(bool extract = false) {
      if ( intData && intData->v3Message.get_msgGlobalData() )
	return (intData->v3Message.get_msgGlobalData())->
	  get_msgSecurityModel(extract);
      else 
	return NULL;
  }
  virtual void set_securityModel(Integer32 *newSecurityModel) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    if ( !(intData->v3Message.get_msgGlobalData()) )
      intData->v3Message.set_msgGlobalData(new HeaderData());

    (intData->v3Message.get_msgGlobalData())->
      set_msgSecurityModel(newSecurityModel);
  }

  DEFINE_SSM_PTR_ACCESSOR(OctetString, securityName);
  DEFINE_SSM_PTR_ACCESSOR(Integer32, securityLevel);
  DEFINE_SSM_PTR_ACCESSOR(OctetString, securityEngineID);
  // -----------------------------------------
  // contextEngineID in Scoped PDU
  virtual OctetString *get_contextEngineID(bool extract = false) {
      if ( intData && intData->v3Message.get_scopedPDU() )
	return (intData->v3Message.get_scopedPDU())->
	  get_contextEngineID(extract);
      else 
	return NULL;
  }
  virtual void set_contextEngineID(OctetString *newContextEngineID) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    if ( !(intData->v3Message.get_scopedPDU()) )
      intData->v3Message.set_scopedPDU(new ScopedPDU());

    (intData->v3Message.get_scopedPDU())->
      set_contextEngineID(newContextEngineID);
  }
  // contextName in Scoped PDU
  virtual OctetString *get_contextName(bool extract = false) {
      if ( intData && intData->v3Message.get_scopedPDU() )
	return (intData->v3Message.get_scopedPDU())->
	  get_contextName(extract);
      else 
	return NULL;
  }
  virtual void set_contextName(OctetString *newContextName) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    if ( !(intData->v3Message.get_scopedPDU()) )
      intData->v3Message.set_scopedPDU(new ScopedPDU());

    (intData->v3Message.get_scopedPDU())->
      set_contextName(newContextName);
  }

  DEFINE_SSM_PTR_ACCESSOR(PDU_Version, pduVersion);
  DEFINE_SSM_PTR_ACCESSOR(PDU_Type, pduType);
  // pdu in Scoped PDU
  virtual PDU *get_pdu(bool extract = false) {
      if ( intData && intData->v3Message.get_scopedPDU() )
	return (intData->v3Message.get_scopedPDU())->
	  get_pdu(extract);
      else 
	return NULL;
  }
  virtual void set_pdu(PDU *newPDU) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    if ( !(intData->v3Message.get_scopedPDU()) )
      intData->v3Message.set_scopedPDU(new ScopedPDU());

    (intData->v3Message.get_scopedPDU())->
      set_pdu(newPDU);
  }
  // scopedPDU in v3Message
  virtual ScopedPDU *get_scopedPDU(bool extract = false) {
    if (intData) return intData->v3Message.get_scopedPDU(extract);
    else return NULL;
  };
  virtual void set_scopedPDU(ScopedPDU *newScopedPDU)  {
    if (!intData) intData = new snmpStandardMessage_internalData();
    intData->v3Message.set_scopedPDU(newScopedPDU);
  };
  // scopedPDU_stringin v3Message
  virtual string *get_scopedPDU_string(bool extract = false) {
    if (intData) return intData->v3Message.get_scopedPDU_string(extract);
    else return NULL;
  };
  virtual void set_scopedPDU_string(string *newScopedPDU_string)  {
    if (!intData) intData = new snmpStandardMessage_internalData();
    intData->v3Message.set_scopedPDU_string(newScopedPDU_string);
  };
  DEFINE_SSM_PTR_ACCESSOR(bool, expectResponse);
  // -----------------------------------------
  DEFINE_SSM_PTR_ACCESSOR(Integer32, maxSizeResponsePDU);
  DEFINE_SSM_ACCESSOR(unsigned long, sendPduHandle);
  DEFINE_SSM_PTR_ACCESSOR(BufferClass, outMsg);
  DEFINE_SSM_PTR_ACCESSOR(Integer32, outMsgLength);
  // securityParameters in v3Message
  virtual asnDataType *get_securityParameters(bool extract = false) {
    if (intData) return intData->v3Message.get_msgSecurityParameters(extract);
    else return NULL;
  };
  virtual void set_securityParameters(asnDataType *newSecurityParameters) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    intData->v3Message.set_msgSecurityParameters(newSecurityParameters);
  };
  // -----------------------------------------
  DEFINE_SSM_PTR_ACCESSOR(snmpObj, stateReference);
  DEFINE_SSM_PTR_ACCESSOR(snmpObj, securityStateReference);
  // maxMsgSize in Header Data
  virtual Integer32 *get_maxMsgSize(bool extract = false) {
      if ( intData && intData->v3Message.get_msgGlobalData() )
	return (intData->v3Message.get_msgGlobalData())->
	  get_maxMsgSize(extract);
      else 
	return NULL;
  }
  virtual void set_maxMsgSize(Integer32 *newMaxMsgSize) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    if ( !(intData->v3Message.get_msgGlobalData()) )
      intData->v3Message.set_msgGlobalData(new HeaderData());

    (intData->v3Message.get_msgGlobalData())->set_maxMsgSize(newMaxMsgSize);
  }

  DEFINE_SSM_PTR_ACCESSOR(snmpStatusInfo, statusInformation);
  // globaData in v3Message
  virtual HeaderData *get_globalData(bool extract = false) {
    if (intData) return intData->v3Message.get_msgGlobalData(extract);
    else return NULL;
  };
  virtual void set_globalData(HeaderData *newGlobalData) {
    if (!intData) intData = new snmpStandardMessage_internalData();
    intData->v3Message.set_msgGlobalData(newGlobalData);
  };

  // special for v3 message
  virtual snmpV3Message &get_v3Message_ref();
  
};

#endif /* SNMPSTANDARDMESSAGE_H */

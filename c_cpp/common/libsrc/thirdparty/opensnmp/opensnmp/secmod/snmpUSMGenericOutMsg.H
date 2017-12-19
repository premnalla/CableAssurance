#ifndef SNMPGENERICOUTMSG_H
#define SNMPGENERICOUTMSG_H

#include "snmpMessageObj.H"
#include "snmpStatusInfo.H"
#include "HeaderData.H"

#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"
#include "BufferClass.H"

class snmpUSMGenericOutMsg : public snmpMessageObj {

private:


  Integer32         *messageProcessingModel;
  Integer32         *securityModel;
  OctetString       *securityName;
  Integer32         *securityLevel;
  OctetString       *securityEngineID;
  // -----------------------------------------





  ScopedPDU         *scopedPDU;

  // -----------------------------------------

  BufferClass       *outMsg;
  Integer32         *outMsgLength;
  asnDataType       *securityParameters;
  // -----------------------------------------

  snmpObj           *securityStateReference;
  Integer32         *maxMsgSize;
  snmpStatusInfo    *statusInformation;
  HeaderData        *globalData;

public:
   
  snmpUSMGenericOutMsg(snmpMessageObj   *copyFromMsg = NULL,
		       int               newID       = 0,
		       snmpSynchDataObj *newData     = NULL,
		       snmpFIFOObj      *theFIFO     = NULL  );

  snmpUSMGenericOutMsg
  (

    Integer32         *newMessageProcessingModel,
    Integer32         *newSecurityModel,
    OctetString       *newSecurityName,
    Integer32         *newSecurityLevel,
    OctetString       *newSecurityEngineID,
    // -----------------------------------------





    ScopedPDU         *newScopedPDU,

    // -----------------------------------------

    BufferClass       *newOutMsg,
    Integer32         *newOutMsgLength,
    asnDataType       *newSecurityParameters,
    // -----------------------------------------

    snmpObj           *newSecurityStateReference,
    Integer32         *newMaxMsgSize,
    snmpStatusInfo    *newStatusInformation,
    HeaderData        *newGlobalData,
    snmpSynchDataObj  *newData     = NULL,
    snmpFIFOObj       *theFIFO     = NULL );

  virtual ~snmpUSMGenericOutMsg();

  virtual snmpUSMGenericOutMsg *clone();


  // extract is passed as true for ownership of the variable.
  // That is, the variable is then no longer referenced by this object.
  // You own it, you delete it!



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




       
      
  ScopedPDU      *get_scopedPDU(bool extract=false);
  bool            set_scopedPDU(ScopedPDU          *newScopedPDU); 
      
      
  // -----------------------------------------


  BufferClass    *get_wholeOutMsg(bool extract=false);
  bool            set_wholeOutMsg(BufferClass        *newOutMsg);

  Integer32      *get_wholeOutMsgLength(bool extract=false);
  bool            set_wholeOutMsgLength(Integer32    *newMsgLength);

  asnDataType    *get_securityParameters(bool extract=false);
  bool            set_securityParameters(asnDataType  *newSecParams);

  // -----------------------------------------


  snmpObj        *get_securityStateReference(bool extract=false);
  bool            set_securityStateReference(snmpObj  *newSecStateRef);

  Integer32      *get_maxMsgSize(bool extract=false);
  bool            set_maxMsgSize(Integer32  *newMsgLength);

  snmpStatusInfo *get_statusInformation(bool extract=false);
  bool            set_statusInformation(snmpStatusInfo *newStatus);

  HeaderData *get_globalData(bool extract=false);
  bool            set_globalData(HeaderData  *newGlobalData);

};

#endif /* SNMPGENERICOUTMSG_H */

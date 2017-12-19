#ifndef SNMPKSMARCHOBJ_H
#define SNMPKSMARCHOBJ_H

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "BufferClass.H"
#include "snmpEngine.H"
#include "snmpSNMPErrorObj.H"
#include "debug.H"

// #include "snmpKSMSecurityStateReference.H"
// #include "KSMSecurityParameters.H" // for the message
#include "snmpUSMGenerateRequestMsgASI.H"
#include "snmpUSMGenerateResponseMsgASI.H"
#include "snmpUSMProcessIncomingASI.H"

#include <map>

#include <krb5.h>
#include <com_err.h>

#define SNMP_KSM_ARCH_NAME "SM:2066432";
#define KSM_KEY_USAGE_ENCRYPTION	1030
#define KSM_KEY_USAGE_CHECKSUM		1031


class snmpKSMArchObj : public snmpArchObj {
public:

private:

  friend class snmpKSMArchRegObj; // to share fifoPtr

  virtual snmpKSMArchObj *clone() const { throw; }

  DEBUGDECLARE(ksmDebug);
  
  snmpEngine      *enginePtr;
  snmpFIFOObj     *crFIFO;
  snmpRegObj       theReg;

  // C import
  krb5_context kcontext;

  snmpKSMArchObj();
  snmpKSMArchObj(snmpEngine *engPtr);

  void return_message(snmpMessageObj *theMessage);
  snmpSNMPErrorObj* processError();

  void GenerateOutMessage(snmpSecurityStateReference *userData, 
			  snmpKSMGenericOutMsg *genOutMsg);
  void GenerateRequestMessage(snmpUSMGenerateRequestMsgASI   *genReqMsg);
  void GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg);
  void ProcessIncoming(snmpUSMProcessIncomingASI             *procIncoming);

protected:
  snmpFIFOObj   *fifoPtr, *mpFIFO;

public:
  static const int BUFFER_SIZE;
  static const int MESSAGE_OVERHEAD;

  virtual ~snmpKSMArchObj();

  // New Methods

  virtual void main_loop();

 };


#endif /* SNMPKSMARCHOBJ_H ifndef */

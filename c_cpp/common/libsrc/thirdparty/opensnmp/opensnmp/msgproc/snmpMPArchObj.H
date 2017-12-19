#ifndef SNMPMPARCHOBJ_H
#define SNMPMPARCHOBJ_H

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpRegObj.H"
#include "snmpFIFOObj.H"
#include "snmpStateReference.H"
#include "debug.H"
#include <map>

#include "snmpMPPrepareOutgoingMsgASI.H"
#include "snmpMPPrepareResponseMsgASI.H"
#include "snmpMPPrepareDataElementsASI.H"

#include "snmpUSMGenerateRequestMsgASI.H"
#include "snmpUSMGenerateResponseMsgASI.H"
#include "snmpUSMProcessIncomingASI.H"

#define SNMP_MP3_ARCH_NAME "MP3"

class snmpEngine;

class snmpMPArchObj : public snmpArchObj {

  friend class snmpMPArchRegObj; // to share fifoPtr

private:

  snmpEngine  *enginePtr;
  DEBUGDECLARE(mpDebug);

  static const string DEFAULT_CONTEXT_NAME;
  static const int    MAX_MESSAGE_SIZE;
  string              LOCAL_ENGINE_ID;
  static const string FAKE_USER;
  
  map<int, snmpStateReference *>  outgoingRequestMap;
  map<int, snmpStateReference *>  incomingRequestMap;
  typedef map<int, snmpStateReference *>::iterator  map_iterator_type;

  void return_message(snmpMessageObj *theMessage);
  void send_sm_message(Integer32 secModel, snmpMessageObj *theMessage);
  void send_usm_message(snmpMessageObj *theMessage);
  void send_disp_message(snmpMessageObj *theMessage);
  bool handle_reports(snmpStateReference *refData, 
 		      VarBindList        *theList,
 		      int                 oldMsgID,
 		      OctetString        *newEngID);

  void PrepareOutgoingMessage(snmpMPPrepareOutgoingMsgASI  *prepOutMsg);
  void PrepareResponseMessage(snmpMPPrepareResponseMsgASI  *prepRespMsg);
  void PrepareDataElements(snmpMPPrepareDataElementsASI    *prepDataElem);

  void GenerateRequestMessage(snmpUSMGenerateRequestMsgASI   *genReqMsg);
  void GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg);
  void ProcessIncoming(snmpUSMProcessIncomingASI             *procIncoming);

protected:

  snmpRegObj     theReg;
  snmpFIFOObj   *fifoPtr, *dispFIFO, *usmFIFO;

public:

  snmpMPArchObj(snmpEngine *theEngine);
  virtual ~snmpMPArchObj();

  // New Methods

  virtual void main_loop();

  virtual void handleInFIFO(snmpMessageObj *message);

 };


#endif /* SNMPMPARCHOBJ_H ifndef */

#ifndef SNMPUSMARCHOBJ_H
#define SNMPUSMARCHOBJ_H

#include "snmpArchObj.H"
#include "snmpMessageObj.H"
#include "snmpFIFOObj.H"
#include "BufferClass.H"
#include "snmpUSMData.H"
#include "snmpEngine.H"
#include "snmpCrypto.H"
#include "snmpSNMPErrorObj.H"
#include "debug.H"

#include "snmpSecurityStateReference.H"

#include "USMSecurityParameters.H"
#include "snmpUSMGenerateRequestMsgASI.H"
#include "snmpUSMGenerateResponseMsgASI.H"
#include "snmpUSMProcessIncomingASI.H"
#include "usmAddUserMsg.H"
#include "usmDelUserMsg.H"
#include "usmCheckUserMsg.H"

#include <map>

#define SNMP_USM_ARCH_NAME "SM:3"


class snmpUSMArchObj : public snmpArchObj {
public:
  enum usmStatsEnum {
    usmStatsUnsupportedSecLevels = 1,
    usmStatsNotInTimeWindows     = 2,
    usmStatsUnknownUserNames     = 3,
    usmStatsUnknownEngineIDs     = 4,
    usmStatsWrongDigests         = 5,
    usmStatsDecryptionErrors     = 6,
    usmStatsLast                 = 7
  };

private:

  friend class snmpUSMArchRegObj; // to share fifoPtr

  virtual snmpUSMArchObj *clone() const { throw; }

  DEBUGDECLARE(usmDebug);
  
  snmpUSMData      usmData;
  snmpEngine      *enginePtr;
  snmpFIFOObj     *crFIFO;
  snmpCrypto       crypto;
  long             pre_salt; // should be 4 bytes
  snmpRegObj       theReg;

  struct usmLCDEntry {
    int  boots;
    int  time;
    int  latestTime;
    bool is_auth;
  };

  map<OctetString, usmLCDEntry>  lcdMap;

  snmpUSMArchObj();
  snmpUSMArchObj(snmpEngine *engPtr);

  snmpCounterSet  usmStatistics;

  void return_message(snmpMessageObj *theMessage);
  snmpSNMPErrorObj* processStatsError(usmStatsEnum theError);

  bool encryptData(snmpSecurityStateReference *userData, ScopedPDU *sPDU, 
		   OctetString *privParams, unsigned int secEngineBoots,
                   unsigned int secEngineTime);
  bool decryptData(snmpSecurityStateReference *userData,
		   USMSecurityParameters      *secParams,
		   char *payload, int payloadLength,
		   string *sPDU_string);

  bool authenticateMessage(snmpSecurityStateReference *userData, 
			   USMSecurityParameters *secParams, 
			   char *theSerialMsg, int msgLength);
  bool checkAuthentication(snmpSecurityStateReference *userData,
			   USMSecurityParameters      *secparams,
			   snmpV3Message              &theMsg);

  void processBootsTime(USMSecurityParameters *secParams,
			snmpStatusInfo        *status,
			bool                   is_auth = true);
  void getBootsTime(OctetString *engID, int *boots, int *time);
  bool isValidEngineID(OctetString *engID);
  bool findUserData(OctetString *userName, OctetString *engID,
		    snmpSecurityStateReference *userData);

  void GenerateOutMessage(snmpSecurityStateReference *userData, 
			  snmpStandardMessage *genOutMsg);
  void GenerateRequestMessage(snmpUSMGenerateRequestMsgASI   *genReqMsg);
  void GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg);
  void ProcessIncoming(snmpUSMProcessIncomingASI             *procIncoming);

  void AddUserUSM(usmAddUserMsg *addUser);
  void CheckUserUSM(usmCheckUserMsg *checkUser);
  void createTables();
  void passTablesToCR();

protected:
  snmpFIFOObj   *fifoPtr, *mpFIFO;

public:
  static const int BUFFER_SIZE;
  static const int MESSAGE_OVERHEAD;

  static const OID  usmStatsOID;

  virtual ~snmpUSMArchObj();

  // New Methods

  virtual void main_loop();

  virtual void handleInFIFO(snmpMessageObj *message);

 };


#endif /* SNMPUSMARCHOBJ_H ifndef */

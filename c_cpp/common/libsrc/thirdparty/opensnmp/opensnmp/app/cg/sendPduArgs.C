
#include "sendPduArgs.H"

sendPduArgs::sendPduArgs(
     TransportDomain  *ntransportDomain,
     TransportAddress *ntransportAddr,
     OctetString      *ncontextEngId,
     OctetString      *ncontextName,
     OctetString      *nuserName,
     OID              *nauthOID,
     opensnmpKey      *nauthKey,
     OID              *nprivOID,
     opensnmpKey      *nprivKey,
     Integer32        *nsecLevel,
     PDU              *nmsgPDU,
     int               ntimeout,
     int               nretries,
     bool              naddToUSM,
     bool              nisWalk) {

     transportDomain = ntransportDomain;
     transportAddr   = ntransportAddr;
     contextEngId    = ncontextEngId;
     contextName     = ncontextName;
     userName        = nuserName;
     authOID         = nauthOID;
     authKey         = nauthKey;
     privOID         = nprivOID;
     privKey         = nprivKey;
     secLevel        = nsecLevel;
     msgPDU          = nmsgPDU;
     timeout         = ntimeout;
     retries         = nretries;
     addToUSM        = naddToUSM;
     isWalk          = nisWalk; 

     sendTime        = 0;
}


sendPduArgs::sendPduArgs(sendPduArgs & copyArgs)  {

  if (NULL != copyArgs.transportDomain) {
    this->transportDomain = new TransportDomain(*(copyArgs.transportDomain));
  }
  else this->transportDomain = 
	 new TransportDomain(SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP);

  if (NULL != copyArgs.transportAddr) {
    this->transportAddr = copyArgs.transportAddr->clone(); 
  }
  else this->transportAddr = new TransportAddress();

  if (NULL != copyArgs.contextEngId) {
    this->contextEngId = copyArgs.contextEngId->clone(); 
  }
  else this->contextEngId = new OctetString("");

  if (NULL != copyArgs.contextName) {
    this->contextName = copyArgs.contextName->clone(); 
  }
  else this->contextName = new OctetString("");

  if (NULL != copyArgs.userName) {
    this->userName = copyArgs.userName->clone(); 
  }
  else this->userName = new OctetString("initial");

  if (NULL != copyArgs.authOID) {
    this->authOID = copyArgs.authOID->clone(); 
  }
  else this->authOID = new OID(".1.3.6.1.6.3.10.1.1.2"); // MD5

  if (NULL != copyArgs.authKey) {
    this->authKey = copyArgs.authKey->clone(); 
  }
  else this->authKey = new opensnmpKey(""); // no auth key

  if (NULL != copyArgs.privOID) {
    this->privOID = copyArgs.privOID->clone(); 
  }
  else this->privOID = new  OID(".1.3.6.1.6.3.10.1.2.2"); // DES

  if (NULL != copyArgs.privKey) {
    this->privKey = copyArgs.privKey->clone(); 
  }
  else this->privKey = new opensnmpKey(""); // no priv key

  if (NULL != copyArgs.secLevel) {
    this->secLevel = copyArgs.secLevel->clone(); 
  }
  else this->secLevel = 
	 new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV);
  
  if (NULL != copyArgs.msgPDU) {
    this->msgPDU = copyArgs.msgPDU->clone(); 
  }
  else this->msgPDU = NULL;

  this->timeout  = copyArgs.timeout;
  this->retries  = copyArgs.retries;
  this->sendTime = copyArgs.sendTime;
  this->addToUSM = copyArgs.addToUSM;
  this->isWalk   = copyArgs.isWalk;

}


sendPduArgs::~sendPduArgs() {
  if(transportDomain)
    delete transportDomain;

  if(transportAddr)
    delete transportAddr;

  if(contextEngId)
    delete contextEngId;

  if(contextName)
    delete contextName;

  if(userName)
    delete userName;

  if(authOID)
    delete authOID;

  if(authKey)
    delete authKey;

  if(privOID)
    delete privOID;

  if(privKey)
    delete privKey;

  if(secLevel)
    delete secLevel;

  if(msgPDU)
    delete msgPDU;

}

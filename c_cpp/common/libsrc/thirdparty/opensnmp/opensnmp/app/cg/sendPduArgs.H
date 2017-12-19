#ifndef SENDPDUARGS_H
#define SENDPDUARGS_H

#include "asnDataTypes.H"
#include "snmpConstants.H"
#include "opensnmpKey.H"

using namespace SNMP_CONSTANTS;

class sendPduArgs {

  // These are all the values that can be associated with a pdu
  // (including, of course, the pdu.
  // 
  // 
private:
  TransportDomain  *transportDomain; 
  TransportAddress *transportAddr;   // default 0.0.0.0, needs to be set
  OctetString      *contextEngId;
  OctetString      *contextName;
  OctetString      *userName;
  OID              *authOID;
  opensnmpKey      *authKey; 
  OID              *privOID;
  opensnmpKey      *privKey; 
  Integer32        *secLevel;
  PDU              *msgPDU;
  int               timeout;
  int               retries;
  long              sendTime;
  bool              addToUSM;
  bool              isWalk;

public:
  DEFINE_PTR_ACCESSOR(TransportDomain,  transportDomain, );
  DEFINE_PTR_ACCESSOR(TransportAddress, transportAddr,   );
  DEFINE_PTR_ACCESSOR(OctetString,      contextEngId,    addToUSM = true;);
  DEFINE_PTR_ACCESSOR(OctetString,      contextName,     );
  DEFINE_PTR_ACCESSOR(OctetString,      userName,        addToUSM = true;);
  DEFINE_PTR_ACCESSOR(OID,              authOID,         addToUSM = true;);
  DEFINE_PTR_ACCESSOR(opensnmpKey,      authKey,         addToUSM = true;);
  DEFINE_PTR_ACCESSOR(OID,              privOID,         addToUSM = true;);
  DEFINE_PTR_ACCESSOR(opensnmpKey,      privKey,         addToUSM = true;);
  DEFINE_PTR_ACCESSOR(Integer32,        secLevel,        );
  DEFINE_PTR_ACCESSOR(PDU,              msgPDU,          );
  DEFINE_ACCESSOR(int,                  timeout, );
  DEFINE_ACCESSOR(int,                  retries, );
  DEFINE_ACCESSOR(long,                 sendTime, );
  DEFINE_ACCESSOR(bool,                 addToUSM, );
  DEFINE_ACCESSOR(bool,                 isWalk, );


  sendPduArgs
    (TransportDomain *ntransportDomain = 
     new TransportDomain(SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP),
     TransportAddress *ntransportAddr = new TransportAddress(),
     OctetString *ncontextEngId  = new OctetString(""),
     OctetString *ncontextName   = new OctetString(""),
     OctetString *nuserName      = new OctetString("initial"),
     OID         *nauthOID       = new OID(".1.3.6.1.6.3.10.1.1.2"),//MD5 def.
     opensnmpKey *nauthKey       = new opensnmpKey(""),      // no auth key
     OID         *nprivOID       = new OID(".1.3.6.1.6.3.10.1.2.2"),//DES def.
     opensnmpKey *nprivKey       = new opensnmpKey(""),      // no priv key
     Integer32   *nsecLevel      = 
        new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV),
     PDU *nmsgPDU        = NULL,
     int  ntimeout       = 5,
     int  nretries       = 5,
     bool naddToUSM      = true,
     bool nisWalk        = false); 

  sendPduArgs(sendPduArgs & copyArgs);

  virtual ~sendPduArgs();

  virtual sendPduArgs*  clone() { return new sendPduArgs(*this); };

};

#endif /* SENDPDUARGS_H */

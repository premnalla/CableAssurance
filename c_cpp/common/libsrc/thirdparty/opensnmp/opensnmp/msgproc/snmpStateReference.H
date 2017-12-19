#ifndef SNMPSTATEREFERENCE_H
#define SNMPSTATEREFERENCE_H

#include "snmpObj.H"

#include "OctetString.H"
#include "Integer32.H"
#include "PDU.H"
#include "ScopedPDU.H"
#include "TransportAddress.H"
#include "HeaderData.H"
#include "snmpConstants.H"
using SNMP_CONSTANTS::TransportDomain;

class snmpStateReference : public snmpObj {
  
  friend class snmpMPArchObj;

private:

  Integer32       *msgID;
  OctetString     *securityEngineID;
  OctetString     *contextEngineID;
  OctetString     *contextName;
  Integer32       *securityModel;
  OctetString     *securityName;
  Integer32       *securityLevel;
  
  // extras for resending messages

  ScopedPDU       *sPDU;
  PDU             *pdu;
  HeaderData      *globalData;

  bool             reportable;
  bool             eng_ID_Discovery;
  
  unsigned long    sendPDUHandle;

  TransportDomain  *transportDomain;
  TransportAddress *transportAddress;

  OctetString     *msgFlags;
  Integer32       *msgMaxSize;
  Integer32       *maxSizeResponsePDU;
  snmpObj         *securityStateReference;

  snmpStateReference();

public:

  virtual ~snmpStateReference();
  
  virtual snmpObj *clone() const;


};

#endif /* SNMPSTATEREFERENCE_H */

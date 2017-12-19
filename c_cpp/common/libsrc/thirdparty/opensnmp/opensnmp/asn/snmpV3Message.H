#ifndef SNMPV3MESSAGE_H
#define SNMPV3MESSAGE_H

#include "asnDataType.H"
#include "OctetString.H"
#include "Integer32.H"
#include "ScopedPDU.H"
#include "HeaderData.H"


class snmpV3Message : public asnDataType {
private:
  Integer32    *msgVersion;
  HeaderData   *msgGlobalData;
  asnDataType  *msgSecurityParameters;
  ScopedPDU    *scopedPDU;
  std::string       *scopedPDU_string;
  char         *serialMsg,
               *msgVersion_loc, *msgGlobalData_loc,
               *msgSecurityParameters_loc, *scopedPDU_loc;

  void EraseData();
  void ZeroLocations();

public:
  snmpV3Message(void);
  snmpV3Message(const snmpV3Message &ref);
  snmpV3Message(Integer32   *newMsgVersion, 
		HeaderData  *newMsgGlobalData, 
		asnDataType *newMsgSecurityParameters, 
		ScopedPDU   *newScopedPDU);
  
  virtual ~snmpV3Message();

  virtual char *EncodeData(char *cp) const;

  virtual void  DecodeData(char *cp, int len);

  // (asn)DecodeMsgHeader decodes the first three sequences of a message
  // i.e. msgVersion, msgGlobalData, msgSecurityParameters
  // and returns the char * to the next (& last) sequence which is
  // a ScopdePDU/scopedPDU.
  // This is used on an incoming message to parse the message
  // before possible message authentication or PDU decryption.
  virtual char *asnDecodeMsgHeader(char *cp, int length);
  virtual char *DecodeMsgHeader(char *cp, int len);
  
  DEFINE_PTR_ACCESSOR(Integer32, msgVersion, );
  DEFINE_PTR_ACCESSOR(HeaderData, msgGlobalData, );
  DEFINE_PTR_ACCESSOR(asnDataType, msgSecurityParameters, );
  DEFINE_PTR_ACCESSOR(ScopedPDU, scopedPDU, );
  DEFINE_PTR_ACCESSOR(string, scopedPDU_string, );
  
  virtual char    *get_serialLocation();
  virtual char    *get_scopedPDUSerialLocation();
  virtual char    *get_msgSecurityParametersSerialLocation();

  std::string toString() const;
  
  virtual snmpV3Message *clone() const { return new snmpV3Message(*this); };
  virtual snmpV3Message &change_value(const asnDataType &right);
};

#endif /* SNMPV3MESSAGE_H */

#ifndef SNMPHEADERDATA_H
#define SNMPHEADERDATA_H

#include "asnDataType.H"
#include "Integer32.H"
#include "OctetString.H"

class HeaderData : public asnDataType {

private:
  Integer32   *msgID;      // should be 0 <=
  Integer32   *maxMsgSize; // should be 484 <
  OctetString *msgFlags;   // should be length 1
  Integer32   *msgSecurityModel; // should be 1 <=

public:
  HeaderData();
  HeaderData(const HeaderData &ref);
  HeaderData(Integer32   *new_msgID, 
	     Integer32   *new_maxMsgSize,
	     OctetString *new_msgFlags,
	     Integer32   *new_msgSecurityModel);

  virtual ~HeaderData();
  
  void EraseData();

  virtual char *EncodeData(char *cp) const;
  virtual void  DecodeData(char *cp, int len);

  DEFINE_PTR_ACCESSOR(Integer32, msgID, );  
  DEFINE_PTR_ACCESSOR(Integer32, maxMsgSize, );
  DEFINE_PTR_ACCESSOR(OctetString, msgFlags, );
  DEFINE_PTR_ACCESSOR(Integer32, msgSecurityModel, );

  virtual std::string toString() const;
  virtual operator string() const;

  virtual HeaderData *clone() const { return new HeaderData(*this); };
  virtual HeaderData &change_value(const asnDataType &right);

};

#endif /* SNMPHEADERDATA_H */

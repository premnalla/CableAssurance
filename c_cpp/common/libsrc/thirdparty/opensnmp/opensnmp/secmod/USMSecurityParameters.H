#ifndef USNMSECURITYPARAMETERS_H
#define USNMSECURITYPARAMETERS_H

#include "asnDataType.H"
#include "OctetString.H"
#include "Integer32.H"

struct loc_t; // forward

class USMSecurityParameters : public asnDataType {
private:
  OctetString  *msgAuthoritativeEngineID;
  Integer32    *msgAuthoritativeEngineBoots;
  Integer32    *msgAuthoritativeEngineTime;
  OctetString  *msgUserName;
  OctetString  *msgAuthenticationParameters;
  OctetString  *msgPrivacyParameters;

  loc_t  *loc;

  void EraseData();
  void ZeroLocations();

public:
  USMSecurityParameters();
  USMSecurityParameters(const USMSecurityParameters &ref);
  USMSecurityParameters(OctetString  *new_msgAuthoritativeEngineID,
			Integer32    *new_msgAuthoritativeEngineBoots,
			Integer32    *new_msgAuthoritativeEngineTime,
			OctetString  *new_msgUserName,
			OctetString  *new_msgAuthenticationParameters,
			OctetString  *new_msgPrivacyParameters);

  
  virtual ~USMSecurityParameters();

  virtual char *EncodeData(char *cp) const;

  virtual void  DecodeData(char *cp, int len);

  // needed to handle the fact this is a sequence within an octet string.
  virtual char *asnEncode(char *) const;
  virtual char *asnDecode(char *, int length);


  virtual OctetString  *get_msgAuthoritativeEngineID(bool extract=false);
  virtual void          set_msgAuthoritativeEngineID(OctetString *newVal);
  
  virtual Integer32    *get_msgAuthoritativeEngineBoots(bool extract=false);
  virtual void          set_msgAuthoritativeEngineBoots(Integer32 *newVal);
  
  virtual Integer32    *get_msgAuthoritativeEngineTime(bool extract=false);
  virtual void          set_msgAuthoritativeEngineTime(Integer32 *newVal);
  
  virtual OctetString  *get_msgUserName(bool extract=false);
  virtual void          set_msgUserName(OctetString *newVal);
  
  virtual OctetString  *get_msgAuthenticationParameters(bool extract=false);
  virtual void          set_msgAuthenticationParameters(OctetString *newVal);
  
  virtual OctetString  *get_msgPrivacyParameters(bool extract=false);
  virtual void          set_msgPrivacyParameters(OctetString *newVal);
  

  // pass in the pointer to the serialized char you want..
  // e.g. get_serialLocation(SecParams->get_msgUserName()) gets
  // the serial location 'char *' of the msgUserName.
  virtual char    *get_serialLocation();
  virtual char    *get_serialLocation(Integer32    *findVal);
  virtual char    *get_serialLocation(OctetString  *findVal);
  
  virtual string toString() const;
  virtual operator string() const;
  
  virtual USMSecurityParameters *clone() const 
    { return new USMSecurityParameters(*this); };
};

#endif /* USNMSECURITYPARAMETERS_H */

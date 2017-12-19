#ifndef SNMPERROROBJ_H
#define SNMPERROROBJ_H

#include<string>
#include "snmpObj.H"

class snmpErrorObj : virtual public snmpObj {

public:

  enum internal_error_enums {
    NO_ERROR = 0,
    UNKNOWN_ERROR,
    BAD_MSGID,
    NO_STATE_REFERENCE,
    UNKNOWN_SECURITY_LEVEL,
    NO_PDU,
    BAD_MSG_DATA,
    ENCRYPTION_ERROR,
    AUTHENTICATION_ERROR,
    ASN_ENCODE_ERROR,
    ASN_PARSE_ERROR,
    TIMEOUT_ERROR,
    DISCOVERY_ERROR,
    ADD_USM_USER_ERROR,
    USM_USER_EXISTS_ERROR,
    DISP_INTERNAL_MESSAGE_ERROR,
    SNMP_ERROR,
    MAX_INTERNAL_ERROR
  };

  bool    success;
  int     errorValue;
  string  errorText;

  snmpErrorObj(int        newError = int(NO_ERROR), 
	       string newErrorText = string(""));
  snmpErrorObj(const snmpErrorObj &);

  virtual ~snmpErrorObj();
  
  virtual bool get_success();
  virtual void set_success(bool is_success = true);

  virtual int get_error();
  virtual void set_error(int newError = int(NO_ERROR));

  virtual string toString();
  virtual snmpObj *clone() const { return new snmpErrorObj(*this); };

};


#endif  // SNMPERROROBJ_H

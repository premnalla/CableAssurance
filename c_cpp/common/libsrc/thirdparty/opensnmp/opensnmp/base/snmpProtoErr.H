#ifndef SNMPPROTOERR_H
#define SNMPPROTOERR_H

#include "snmpErrorObj.H"
#include "VarBind.H"

// XXX: not complete
enum protocol_error_enums {
  PROTOERR_NO_ERROR		= 0,
  PROTOERR_TOOBIG               = 1,
  PROTOERR_NOSUCHNAME           = 2,  // v1 only (or proxy)
  PROTOERR_BADVALUE             = 3,  // v1 only (or proxy)
  PROTOERR_READONLY             = 4,  // v1 only (or proxy)
  PROTOERR_GENERR		= 5,
  PROTOERR_NOACCESS		= 6,
  PROTOERR_WRONGTYPE		= 7,
  PROTOERR_WRONGLENGTH		= 8,
  PROTOERR_WRONGENCODING	= 9,
  PROTOERR_WRONGVALUE		= 10,
  PROTOERR_NOCREATION		= 11,
  PROTOERR_INCONSISTENTVALUE	= 12,
  PROTOERR_RESOURCEUNAVAILABLE	= 13,
  PROTOERR_COMMITFAILED		= 14,
  PROTOERR_UNDOFAILED		= 15,
  PROTOERR_AUTHORIZATIONERROR	= 16,
  PROTOERR_NOTWRITABLE		= 17,
  PROTOERR_INCONSISTENTNAME	= 18,
  PROTOERR_NOSUCHOBJECT		= 128,
  PROTOERR_NOSUCHINSTANCE	= 129,
  PROTOERR_ENDOFMIBVIEW		= 130
};


class snmpProtoErr : public snmpErrorObj {
 private:
   VarBind *cause;
   
 public:
   snmpProtoErr(int newError = int(PROTOERR_NO_ERROR), VarBind *cause = NULL);
   virtual ~snmpProtoErr();
   virtual string toString();
   
   virtual void set_cause(VarBind *);
   virtual VarBind *get_cause();
};

#endif /* SNMPPROTOERR_H */


#include "config.h"
#include "snmpProtoErr.H"

snmpProtoErr::snmpProtoErr(int newError, VarBind *cause) 
    : snmpErrorObj(newError),
      cause(cause)
{
}

snmpProtoErr::~snmpProtoErr() 
{
    // do not delete the cause, its not ours
}

string
snmpProtoErr::toString() {
    switch (get_error()) {
      case PROTOERR_NO_ERROR:
          return "PROTOERR_NO_ERROR: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_GENERR:
          return "PROTOERR_GENERR: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_NOACCESS:
          return "PROTOERR_NOACCESS: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_WRONGTYPE:
          return "PROTOERR_WRONGTYPE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_WRONGLENGTH:
          return "PROTOERR_WRONGLENGTH: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_WRONGENCODING:
          return "PROTOERR_WRONGENCODING: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_WRONGVALUE:
          return "PROTOERR_WRONGVALUE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_NOCREATION:
          return "PROTOERR_NOCREATION: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_INCONSISTENTVALUE:
          return "PROTOERR_INCONSISTENTVALUE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_RESOURCEUNAVAILABLE:
          return "PROTOERR_RESOURCEUNAVAILABLE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_COMMITFAILED:
          return "PROTOERR_COMMITFAILED: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_UNDOFAILED:
          return "PROTOERR_UNDOFAILED: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_NOTWRITABLE:
          return "PROTOERR_NOTWRITABLE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_INCONSISTENTNAME:
          return "PROTOERR_INCONSISTENTNAME: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_NOSUCHOBJECT:
          return "PROTOERR_NOSUCHOBJECT: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_NOSUCHINSTANCE:
          return "PROTOERR_NOSUCHINSTANCE: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_ENDOFMIBVIEW:
          return "PROTOERR_ENDOFMIBVIEW: " + ((cause) ? cause->toString() : "no associated VarBind");
          
      case PROTOERR_AUTHORIZATIONERROR:
          return "PROTOERR_AUTHORIZATIONERROR: " + ((cause) ? cause->toString() : "no associated VarBind");

    }
    return "Unknown Protocol Error: " + ((cause) ? cause->toString() : "no associated VarBind");
}

void
snmpProtoErr::set_cause(VarBind *cause) {
    this->cause = cause;
}

VarBind *
snmpProtoErr::get_cause() {
    return cause;
}


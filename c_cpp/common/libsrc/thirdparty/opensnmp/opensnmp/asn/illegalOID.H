#ifndef ILLEGALOID_H
#define ILLEGALOID_H

#include "snmpStringException.H"

class illegalOID  : public snmpStringException {
 private:
   std::string failed_oid;

 public:
   illegalOID(const illegalOID & ref)
     : snmpStringException( ref ) { failed_oid = ref.failed_oid; };
   illegalOID(std::string failed = "")
     : snmpStringException("illegal OID: " + failed) {};
   virtual ~illegalOID() throw() {};
   virtual illegalOID *clone() const { return new illegalOID( *this ); };
};

#endif /* STRINGEXCEPTION_H */

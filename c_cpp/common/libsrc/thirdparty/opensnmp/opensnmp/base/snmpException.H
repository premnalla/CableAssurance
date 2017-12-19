#ifndef SNMPEXCEPTION_H
#define SNMPEXCEPTION_H

#include <exception>
#include <string>

#include "snmpErrorObj.H"

class snmpException : public std::exception {
 public:
   snmpException(const snmpException & ref);
   snmpException();
   virtual ~snmpException() throw () {};

   snmpErrorObj theErrorObj;
  
   virtual snmpException *clone() const { return new snmpException( *this ); };
   virtual string what() { return "ack, unknown"; };  // from exception
};

#endif /* STRINGEXCEPTION_H */

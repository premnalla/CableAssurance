#ifndef ASNPARSEERROR_H
#define ASNPARSEERROR_H

#include "snmpStringException.H"

class asnParseError : public snmpStringException {

 public:
   asnParseError();
   asnParseError(char *);
   virtual ~asnParseError() throw() {};
};

class asnUsageError : public snmpStringException {

 public:
   asnUsageError();
   asnUsageError(char *);
   virtual ~asnUsageError() throw() {};
};

#endif /* ASNPARSEERROR_H */

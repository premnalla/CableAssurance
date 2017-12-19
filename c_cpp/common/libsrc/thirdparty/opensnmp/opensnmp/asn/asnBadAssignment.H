#ifndef ASNBADASSIGNMENT_H
#define ASNBADASSIGNMENT_H

#include "snmpStringException.H"

class asnBadAssignment  : public snmpStringException {
 public:
  asnBadAssignment(const asnBadAssignment & ref)
    : snmpStringException( ref ) {};
  asnBadAssignment(char *string) : snmpStringException(string) {};
  asnBadAssignment() : snmpStringException("assignment failure") {};
  virtual ~asnBadAssignment() throw() {};
  virtual asnBadAssignment * clone() const {
    return new asnBadAssignment( *this );
  };
};

#endif /* STRINGEXCEPTION_H */

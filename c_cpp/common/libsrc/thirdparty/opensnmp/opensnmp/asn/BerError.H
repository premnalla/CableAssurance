#ifndef BERERROR_H
#define BERERROR_H

/* BerError is a SNMP result that is not really a value. */

#include "Null.H"

class BerError : public Null {

 public:
   BerError(berTag ber_tag);
   BerError(const BerError &theCopy);
   virtual ~BerError();

   virtual std::string toString() const;
   virtual operator string() const;
   virtual BerError *clone() { return new BerError(*this); };
};
   
#endif /* BERERROR_H */

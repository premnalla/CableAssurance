#ifndef ASNINDEX_H
#define ASNINDEX_H

class asnIndex;
class OID;

// #include "OID.H"
#include "asnDataType.H"

class asnIndex : public asnDataType {
 public:
   asnIndex();
   asnIndex(berTag tag);
   virtual ~asnIndex();
   virtual OID toOID(bool implied = false);
   virtual OID& appendOID(OID &to, bool implied = false) = 0;
   virtual asnIndex *clone() const = 0;
   virtual std::string toString() const;
};

#include "OID.H"

#endif /* ASNINDEX_H */

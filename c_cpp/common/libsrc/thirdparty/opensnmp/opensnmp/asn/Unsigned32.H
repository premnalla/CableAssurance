#ifndef UNSIGNED32_H
#define UNSIGNED32_H
#include "asnIndex.H"
#include "asnDataType.H"
#include "OID.H"

class Unsigned32   : public asnIndex {
 private:
   unsigned int value;
   
 public:
   Unsigned32();
   Unsigned32(unsigned int init_val, berTag asn_tag = BER_TAG_UNSIGNED32);
   Unsigned32(char *, berTag asn_tag  = BER_TAG_UNSIGNED32);
   virtual ~Unsigned32();

   virtual void  EraseData();
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int len);

   operator unsigned int() const;
   virtual int get_value() const { return value; }

   //   print it!

   virtual Unsigned32 &change_value(const asnDataType &fromNum);

  //  virtual functions from base class

   virtual std::string toString() const;
   virtual OID& appendOID(OID &to, bool implied=false);
   virtual Unsigned32 *clone() const { return new Unsigned32(*this); };


   // operators
   virtual Unsigned32 &operator+=(unsigned int addto) { value += addto; return *this; }
};

#endif

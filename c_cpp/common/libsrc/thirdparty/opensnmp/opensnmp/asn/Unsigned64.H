#ifndef UNSIGNED64_H
#define UNSIGNED64_H
#include "asnDataType.H"

class Unsigned64   : public asnDataType {
 private:
   unsigned long long value;
   
 public:
   Unsigned64();
   Unsigned64(unsigned long long init_val, berTag asn_tag);
   Unsigned64(char *, berTag asn_tag);
   virtual ~Unsigned64();

   virtual void  EraseData();
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int len);

   operator unsigned long long() const;

   //   print it!

   virtual void operator=(Unsigned64 fromNum);

  //  virtual functions from base class

  virtual std::string toString() const;
  
   virtual Unsigned64 *clone() const { return new Unsigned64(*this); };
   virtual Unsigned64 &change_value(const asnDataType &right);

};

#endif

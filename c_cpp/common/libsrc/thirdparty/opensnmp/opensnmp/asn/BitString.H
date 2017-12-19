#ifndef BITSTRING
#define BITSTRING

#include "OctetString.H"

class BitString : public OctetString {
 private:
   std::string bitString() const;
 public:
   BitString(void);
   BitString(const BitString &thecopy);
   BitString(char *init);
   virtual ~BitString();

   virtual operator string() const;

   virtual std::string toString() const;

   virtual BitString *clone() const { return new BitString(*this); };

};

#endif  /* ifndef BITSTRING */

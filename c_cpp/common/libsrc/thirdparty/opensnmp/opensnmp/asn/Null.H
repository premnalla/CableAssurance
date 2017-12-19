#ifndef NULL_H
#define NULL_H
#include "asnDataType.H"

class Null   : public asnDataType {
 private:
   
 public:
   Null();
   Null(const Null &theCopy);
   Null(berTag ber_tag);
   virtual ~Null();

   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int len);

   //  virtual functions from base class

   virtual std::string toString() const;
   virtual operator string() const;
   virtual Null *clone() const { return new Null(*this); };
   virtual Null &change_value(const asnDataType &right);
};

#endif /* NULL_H */

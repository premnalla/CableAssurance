#ifndef INTEGER32_H
#define INTEGER32_H
#include "asnIndex.H"
#include "asnDataType.H"
#include "OID.H"

class Integer32   : public asnIndex {
 protected:
   int value;
   
 public:
   Integer32();
   Integer32(const Integer32 &thecopy);
   Integer32(int);
   Integer32(char *);
   Integer32(const std::string &);
   virtual ~Integer32();

   virtual void  EraseData();
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int len);

   virtual Integer32 &change_value(const asnDataType &fromNum);
   virtual void set_value(int fromNum);
   virtual int get_value() const { return value; }

   Integer32 operator++(); // prefix ++ operator (useful for counter incr)

   operator int() const;

   Integer32 &operator =(int newval) { value = newval; return *this; }

  //  virtual functions from base class

   virtual std::string toString() const;
   virtual operator string() const;
   virtual OID& appendOID(OID &to, bool implied=false);
   virtual Integer32 *clone() const { return new Integer32(*this); };
};

#endif

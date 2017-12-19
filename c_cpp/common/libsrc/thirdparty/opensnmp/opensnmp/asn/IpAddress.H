#ifndef SNMPIPADDRESS
#define SNMPIPADDRESS

#include "RawData.H"

class IpAddress : public RawData {
 public:
   IpAddress();
   IpAddress(const IpAddress &thecopy);
   IpAddress(char *init);
   virtual ~IpAddress();

   //   print it!
   virtual std::string toString() const;

  //  virtual functions from base class

   virtual IpAddress *clone() const { return new IpAddress(*this); };
};

#endif  /* ifndef SNMPIPADDRESS */

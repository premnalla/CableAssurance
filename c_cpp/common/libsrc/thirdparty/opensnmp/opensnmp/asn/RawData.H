#ifndef SNMPASNRAWDATA
#define SNMPASNRAWDATA

#include "asnIndex.H"
#include "OID.H"

class RawData : public asnIndex {
 protected:
   std::string data_string;
 public:
   // constants
   static const std::string nullstr;

   RawData(berTag asn_tag = BER_TAG_OCTET_STRING);
   RawData(const std::string &init, berTag asn_tag = BER_TAG_OCTET_STRING);
   RawData(const RawData &ref);
   RawData(char *str, unsigned int len, berTag asn_tag);
   virtual ~RawData();

   virtual void  EraseData();
   virtual char *EncodeData(char *) const;
   virtual void  DecodeData(char *cp, int len);
   virtual int   size() const;
   
   char *get_string(char *buf, unsigned int maxLen, bool null_terminated = true);
   // like above but returns the length of the data copied, discounting end 
   // '\0', in maxLen value;
   char *get_string(char *buf, unsigned int *maxLen, bool null_terminated = true);
   // copies up to maxLen, starting from location start_location
   // doing the appropriate checks.
   char *get_string(char *buf,   unsigned int  start_location,
		    unsigned int *maxLen, bool null_terminated = true);
   virtual const std::string &get_value() const;

   virtual void assign(const char *str, int len);
   virtual void assign(int len, const char theChar);

   virtual operator string() const; // from base class
   void operator =(string newValue);
   virtual RawData &change_value(const asnDataType &newValue);
   void operator =(const RawData & fromData);
   bool operator ==(RawData fromData) const;
   bool operator !=(RawData fromData) const  
          { return(! (this->operator==( fromData)) ); };
   bool operator <(RawData fromData) const;

   // fromString intreprets a c_str (i.e. '\0' terminated), as a regular
   // std::string or as a hex std::string if prepended with 0x (e.g. 0xff07)
   void fromString(const char* Str);
   void fromString(const std::string & Str) {fromString(Str.c_str());};
   void fromHexString(const std::string & hexStr) {fromHexString(hexStr.c_str());}
   void fromHexString(const char* hexStr);
   std::string toHexString() const;

   //  virtual functions from base class

   virtual std::string toString() const;
   virtual void append(string str);
   virtual void append(char *cp, int len);
   virtual OID& appendOID(OID& to, bool implied);
   virtual RawData *clone() const { return new RawData(*this); };
};

#endif  /* ifndef SNMPASNRAWDATA */

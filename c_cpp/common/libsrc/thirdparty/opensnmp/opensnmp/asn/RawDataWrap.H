#ifndef RAWDATAWRAP
#define RAWDATAWRAP

#include "RawData.H"

template<berTag ber_tag>
class RawDataWrap : public RawData {
 public:
   RawDataWrap() : RawData(ber_tag)
   {
   }
       
   RawDataWrap(char *init, unsigned int len = 0) : RawData(init, len, ber_tag)
   {
   }

   RawDataWrap(const std::string &init) : RawData(init, ber_tag)
   {
   }

   RawDataWrap(const RawDataWrap &thecopy) : RawData(thecopy)
   {
   }

   //   void operator =(const RawDataWrap fromData);

   virtual RawDataWrap<ber_tag> *clone() const 
       { return new RawDataWrap<ber_tag>(*this); };

   //  virtual functions from base class
   virtual RawDataWrap<ber_tag> &change_value(const asnDataType &from) {
       return (RawDataWrap<ber_tag> &)RawData::change_value( from );
   }
   

   virtual operator string() const {
       return RawData::operator string();
   }

   virtual std::string toString() const {
       return RawData::toString();
   }
 };

#endif  /* ifndef RAWDATAWRAP */

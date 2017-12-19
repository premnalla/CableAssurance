#ifndef SCOPEDPDU_H
#define SCOPEDPDU_H

#include "asnDataType.H"
#include "OctetString.H"
#include "PDU.H"

#include "BufferClass.H"

class ScopedPDU      : public asnDataType {
 private:
   OctetString  *contextEngineID;
   OctetString  *contextName;
   PDU          *pdu;
   char         *serialScopedPDU;
   OctetString  *encryptedData;
   
 public:
   ScopedPDU(const ScopedPDU  &copyFrom);
   ScopedPDU(OctetString *contextEngineID = new OctetString(), 
	     OctetString     *contextname = new OctetString(), 
             PDU                     *pdu = new PDU());
   ScopedPDU(char *buffer);

   virtual ~ScopedPDU();
   void EraseData();

   void Add(VarBind *);

   int length();

   DEFINE_PTR_ACCESSOR(OctetString, contextEngineID, );
   DEFINE_PTR_ACCESSOR(OctetString, contextName, );
   DEFINE_PTR_ACCESSOR(PDU, pdu, );  

   char        *get_serialScopedPDU(bool extract=false);
   OctetString *get_encryptedData(bool extract=false);
   void         set_encryptedData(OctetString *newEncryptedData);

   void set_serialScopedPDU(char *);

   virtual char *asnEncode(char *) const;
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int length);

   //   print it!
   virtual std::string toString() const;
   virtual operator string() const;
   virtual ScopedPDU *clone() const { return new ScopedPDU(*this); };
   virtual ScopedPDU &change_value(const asnDataType &right);
};

#endif /* ScopedPDU_H */

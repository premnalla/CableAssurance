#ifndef VARBIND_H
#define VARBIND_H

#include "asnDataType.H"
#include "OID.H"
#include "Null.H"

class VarBind      : public asnDataType {
 private:
   OID         *oid;
   asnDataType *value;
   
  VarBind & operator =( const VarBind & ); // not implemented

 public:
   VarBind();
   VarBind(const VarBind &ref);
   virtual ~VarBind(); // will delete oid & value pointers
 
   // This CTOR creates a new VarBind object and assumes
   // responsibility for the OID and value pointers.
   VarBind(OID *, asnDataType * = new Null());

   // EraseData() deletes the current value and OID
   // pointers and assigns 0 (NULL) to both pointers. 
   void EraseData();

   // Encode the data into a buffer, but in reverse.
   virtual char *EncodeData(char *cp) const;

   // Decode the data from a char * buffer.
   virtual void  DecodeData(char *cp, int len);

   // get_OID() returns the pointer to the OID
   // for this VarBind. If extract is set to true,
   // the internal OID pointer is set to 0 (NULL) and the
   // caller assumes responsibility for the returned pointer.
   virtual OID *get_OID(bool extract=false);
   
   // get_OID() returns the pointer to the OID
   // for this VarBind. If extract is set to true,
   // the internal OID pointer is set to 0 (NULL) and the
   // caller assumes responsibility for the returned pointer.
   virtual const OID *get_OID(void) const { return oid; };
   
   // set_OID() assigns an OID pointer as the
   // OID for the VarBind. Any previous pointer is deleted.
   // The VarBind class assumes responsibility for the pointer.
   virtual void set_OID(OID *newval);

   // get_value() returns the pointer to the asnDataType
   // for this VarBind. If extract is set to true, the
   // internal value pointer is set to 0 (NULL) and the
   // caller assumes responsibility for the returned pointer.
   virtual asnDataType *get_value(bool extract=false);

   // get_value() returns the pointer to the asnDataType
   // for this VarBind. If extract is set to true, the
   // internal value pointer is set to 0 (NULL) and the
   // caller assumes responsibility for the returned pointer.
   virtual const asnDataType *get_value(void) const { return value; };

   // set_value() assigns an asnDataType pointer as the
   // value for the VarBind. Any previous pointer is deleted.
   // The VarBind class assumes responsibility for the pointer.
   virtual void set_value(asnDataType *newvalue);
   

   //   print it: turn our object into a textual representation...
   virtual std::string toString() const;
   virtual operator string() const;

   virtual VarBind &change_value(const asnDataType &right);

   virtual VarBind *clone() const { return new VarBind(*this); };
};

#endif /* VARBIND_H */

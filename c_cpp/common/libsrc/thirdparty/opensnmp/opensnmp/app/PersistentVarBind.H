#ifndef PERSISTENTVARBIND_H
#define PERSISTENTVARBIND_H

#include "VarBind.H"
#include "VarBindList.H"

class PersistentVarBind      : public VarBind {
 private:
  PersistentVarBind(); // not implemented
  PersistentVarBind(const PersistentVarBind &); // not implemented
  PersistentVarBind & operator=(const PersistentVarBind &); // not implemented

  friend class snmpDatabaseObj; // need set_value_no_update

   // update the database with the new information.
   void update();
   
 public:
   virtual ~PersistentVarBind(); // will save OID/value to database
 
   // This CTOR creates a new PersistentVarBind object. The specified
   // value pointer is used as default value if there is no value
   // currently in the database for the specified OID. Ownership of
   // both pointers is assumed by the class.
   PersistentVarBind(OID *, asnDataType *);

   // EraseData() deletes the current value and OID
   // pointers and assigns 0 (NULL) to both pointers.
   // The entry is removed from the persistent database.
   void EraseData();

   // get_OID() returns the pointer to the OID
   // for this VarBind.
   const OID *get_OID(void) const { return VarBind::get_OID(); };
   
   // get_tag() returns the tag type for the value
   virtual berTag get_tag() const { return VarBind::get_value()->get_tag(); };

   // get_value() returns the pointer to the asnDataType
   // for this VarBind.
   const asnDataType *get_value(void) const { return VarBind::get_value(); };
   
   // set_value() updates the value of this object. Ownership of
   // the asnDataType pointer is assumed by the class.
   void set_value( const asnDataType & new_value );

   // set_value() updates the value of this object. Ownership of
   // the asnDataType pointer is assumed by the class.
   void set_value_no_update( const asnDataType & new_value );

   // set_oid() does not exist because you shouldn't be changing the OID
   // of objects in the persistent storage. If you really need to change,
   // and OID, use EraseData() to delete the old one, and create a new one.

   //   print it: turn our object into a textual representation...
   string toString() const { return VarBind::toString(); };
   operator string() const { return VarBind::operator string(); };

   // since we're really just a wrapper around a data type, only
   // encode it as the datatype and not like a varbind:
   PersistentVarBind & operator =( const asnDataType & from );

   virtual PersistentVarBind &change_value(const asnDataType &from);

   virtual PersistentVarBind *clone() const { 
       return new PersistentVarBind(get_OID()->clone(), get_value()->clone()); 
   };
   virtual char *EncodeData(char *in) const;
   virtual void DecodeData(char *in, int len);
};

#endif /* PERSISTENTVARBIND_H */

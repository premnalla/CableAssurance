#ifndef USMUSERCLONEFROM_H
#define USMUSERCLONEFROM_H

#include <OctetString.H>
#include <snmpRowDependent.H>
#include "debug.H"

class usmUserCloneFrom : public OID, public snmpRowDependent {
private:
  bool have_cloned;
  bool parseOID(OID         theOID, 
		OctetString *userName, 
		OctetString *engID);
  DEBUGDECLARE(userDebug);

public:
  usmUserCloneFrom(snmpDataTable *table = NULL, snmpRow *row = NULL);
  usmUserCloneFrom(const usmUserCloneFrom &thecopy);
  virtual ~usmUserCloneFrom();
  
  static OID usmUserEntryOID;
  
  virtual usmUserCloneFrom *clone() const 
  {return new usmUserCloneFrom(*this);};

  virtual usmUserCloneFrom &change_value(const asnDataType &newValue);
  
  //   virtual const OID &get_value() const;
};

#endif /* USMUSERCLONEFROM_H */

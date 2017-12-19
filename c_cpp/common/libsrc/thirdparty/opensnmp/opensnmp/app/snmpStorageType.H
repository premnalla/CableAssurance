#ifndef SNMPSTORAGETYPE_H
#define SNMPSTORAGETYPE_H

#include "Integer32.H"
#include "snmpConstants.H"
#include "snmpProtoErr.H"
#include "snmpRowDependent.H"

class snmpStorageType : public Integer32 {
 private:
   
 public:
   snmpStorageType();
   snmpStorageType(const Integer32 &thecopy);
   snmpStorageType(const snmpStorageType &thecopy);
   snmpStorageType(int);
   snmpStorageType(char *);
   virtual ~snmpStorageType();

   // based on current value, should this row be stored to stable storage?
  bool should_store( void ) {
    register int value = Integer32::get_value();
    // xxx-rks: what about SNMP_CONSTANTS::SNMP_STORAGE_OTHER?
    return( value == SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE ||
	    value == SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT ||
	    value == SNMP_CONSTANTS::SNMP_STORAGE_READONLY );
  }


   virtual snmpStorageType &change_value(const asnDataType &fromNum);
   virtual snmpStorageType &override_value(const asnDataType &fromNum);
   virtual snmpStorageType *clone() const { return new snmpStorageType(*this);};
};

#endif /* SNMPSTORAGETYPE_H */

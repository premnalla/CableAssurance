#ifndef ROWSTORAGECOLUMNALLOCATOR_H
#define ROWSTORAGECOLUMNALLOCATOR_H

#include "snmpStorageType.H"

class snmpRowStorageColumnAllocator : public snmpColumnAllocator {

public:

   virtual snmpStorageType *allocate(snmpRow *row) {
     return new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);
   }

  virtual snmpStorageType *allocate(snmpRow *row,
			    const asnDataType & initialValue ) {
    snmpStorageType *ret = new snmpStorageType(SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE);
    ret->override_value( initialValue );
    return ret;
  }
  virtual snmpRowStorageColumnAllocator *clone() const {
    return new snmpRowStorageColumnAllocator();
  }

};

#endif /* ROWSTORAGECOLUMNALLOCATOR_H */

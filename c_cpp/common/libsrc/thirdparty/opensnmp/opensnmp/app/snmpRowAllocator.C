#include "config.h"
#include "snmpRowAllocator.H"

snmpRowAllocator::snmpRowAllocator(const snmpRowAllocator & ref ) {
  this->columnAllocators = ref.columnAllocators;
  this->defValues = ref.defValues;
}

snmpRowAllocator::snmpRowAllocator
(std::map<int, snmpColumnAllocator *> *columnAllocators, //= NULL
 std::map<int, asnDataType *> *defValues)  {             //= NULL
  this->columnAllocators = columnAllocators;
  this->defValues = defValues;
}
   
snmpRowAllocator::~snmpRowAllocator() {
}

snmpRow *snmpRowAllocator::allocate() {
  return new snmpRow(columnAllocators, defValues);
}

snmpRow *snmpRowAllocator::allocateWithoutDefaults() {
  return new snmpRow(columnAllocators, NULL);
}

void snmpRowAllocator::allocateMissingDefaults(snmpRow & row) {
  if( defValues != NULL )
    row.allocateMissingDefaults( *defValues );
}

snmpRowAllocator *snmpRowAllocator::clone() const {
  return new snmpRowAllocator( *this );
}

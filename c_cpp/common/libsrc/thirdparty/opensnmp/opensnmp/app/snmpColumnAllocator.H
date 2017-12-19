#ifndef COLUMNALLOCATOR_H
#define COLUMNALLOCATOR_H

#include "snmpRow.H"

class snmpRow;

class snmpColumnAllocator : public snmpObj {
 public:
   virtual asnDataType *allocate(snmpRow *row) = 0;
   virtual asnDataType *allocate(snmpRow *row,
				 const asnDataType & initialValue ) = 0;
};

#endif /* COLUMNALLOCATOR_H */

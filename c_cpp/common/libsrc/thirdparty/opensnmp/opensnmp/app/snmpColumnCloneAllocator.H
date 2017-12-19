#ifndef COLUMNCLONEALLOCATOR_H
#define COLUMNCLONEALLOCATOR_H

#include "snmpRow.H"
#include "snmpColumnAllocator.H"

class snmpRow;

class snmpColumnCloneAllocator : public snmpColumnAllocator {
 private:
   asnDataType *data;
   
 public:

   snmpColumnCloneAllocator(asnDataType *data) {
       this->data = data;
   }

   virtual ~snmpColumnCloneAllocator() {
       if (data)
           delete data;
   }
   
   virtual asnDataType *allocate(snmpRow *row) {
       if (data)
           return data->clone();
       else
           return NULL;
   }
};

#endif /* COLUMNCLONEALLOCATOR_H */

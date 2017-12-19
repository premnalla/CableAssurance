#ifndef ROWALLOCATOR_H
#define ROWALLOCATOR_H

#include "snmpRow.H"

class snmpRowAllocator : public snmpObj {
 protected:
   std::map<int, snmpColumnAllocator *> *columnAllocators;
   std::map<int, asnDataType *> *defValues;

 public:
   snmpRowAllocator(const snmpRowAllocator & ref );
   snmpRowAllocator(std::map<int, 
		    snmpColumnAllocator *> *columnAllocators = NULL,
                    std::map<int, asnDataType *> *defValues = NULL);
   
   virtual ~snmpRowAllocator();

   virtual snmpRow *allocate();
   virtual snmpRow *allocateWithoutDefaults();
   virtual void allocateMissingDefaults(snmpRow & row);

   virtual snmpRowAllocator *clone() const;
};

#endif /* ROWALLOCATOR_H */

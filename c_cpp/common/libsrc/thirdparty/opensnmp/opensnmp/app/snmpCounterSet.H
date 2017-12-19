#ifndef SNMPCOUNTERSET_H
#define SNMPCOUNTERSET_H

#include "asnDataTypes.H"
#include "snmpScalarSet.H"

class snmpCounterSet : public snmpScalarSet {
 private:
 public:
   // creates no initial counters for you.
   snmpCounterSet();

   // creates counter from column lowcolnum to highcolnum
   snmpCounterSet(unsigned int highcolnum, unsigned int lowcolnum = 1);

   // creates a counter at position num
   void addCounterAt(unsigned int num);

   // increments the value of a given counter.
   // creates a counter32 at that location if one didn't exist
   virtual Counter32 *increment(unsigned int column, unsigned int value = 1);
};

    
#endif /* SNMPCOUNTERSET_H */

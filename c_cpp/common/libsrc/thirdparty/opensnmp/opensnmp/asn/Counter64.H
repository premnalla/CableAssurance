#ifndef COUNTER64_H
#define COUNTER64_H

#include "Unsigned64.H"

class Counter64 : public Unsigned64 {

 public:
   Counter64();
   Counter64(unsigned long long init_val);
   Counter64(char *);
   virtual Counter64 *clone() const { return new Counter64(*this); };
};
   
#endif /* COUNTER64_H */

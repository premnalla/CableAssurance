#ifndef COUNTER32_H
#define COUNTER32_H

#include "Unsigned32.H"

class Counter32 : public Unsigned32 {

 public:
   Counter32();
   Counter32(unsigned int init_val);
   Counter32(char *);
   virtual Counter32 *clone() const { return new Counter32(*this); };
};
   
#endif /* COUNTER32_H */

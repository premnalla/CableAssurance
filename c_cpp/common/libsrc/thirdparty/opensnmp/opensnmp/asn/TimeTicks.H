#ifndef TIMETICKS_H
#define TIMETICKS_H

#include "Unsigned32.H"

class TimeTicks : public Unsigned32 {

 public:
   TimeTicks();
   TimeTicks(unsigned int init_val);
   TimeTicks(char *);
   virtual TimeTicks *clone() const { return new TimeTicks(*this); };
};
   
#endif /* TIMETICKS_H */

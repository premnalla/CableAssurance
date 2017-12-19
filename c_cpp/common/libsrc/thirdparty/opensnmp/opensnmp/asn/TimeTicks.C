/* TimeTicks.C */
#include "config.h"
#include "TimeTicks.H"

TimeTicks::TimeTicks() 
  : Unsigned32((unsigned int) 0, BER_TAG_TIMETICKS)
{
}

TimeTicks::TimeTicks(unsigned int init_val) 
  : Unsigned32(init_val, BER_TAG_TIMETICKS)
{
}

TimeTicks::TimeTicks(char *init_str) 
  : Unsigned32(init_str, BER_TAG_TIMETICKS)
{
}

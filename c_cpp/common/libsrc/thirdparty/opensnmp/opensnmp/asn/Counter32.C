/* Counter32.C */
#include "config.h"
#include "Counter32.H"

Counter32::Counter32() 
  : Unsigned32((unsigned int) 0, BER_TAG_COUNTER) 
{
}

Counter32::Counter32(unsigned int init_val) 
  : Unsigned32(init_val, BER_TAG_COUNTER)
{
}

Counter32::Counter32(char *init_str) 
  : Unsigned32(init_str, BER_TAG_COUNTER)
{
}

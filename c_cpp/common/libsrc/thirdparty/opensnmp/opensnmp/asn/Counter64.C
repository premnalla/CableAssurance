/* Counter64.C */
#include "config.h"
#include "Counter64.H"

Counter64::Counter64() 
  : Unsigned64((unsigned long long) 0, BER_TAG_COUNTER64)
{
}

Counter64::Counter64(unsigned long long init_val) 
  : Unsigned64(init_val, BER_TAG_COUNTER64)
{
}

Counter64::Counter64(char *init_str) 
  : Unsigned64(init_str, BER_TAG_COUNTER64)
{
}


#include <stdio.h>
#include "axDbXResource.hpp"

int main (int argc, char * argv[])
{
  axDbXResource xr;
  xr.m_resType = 0;
  if (!xr.insertRow()) {
    printf("Insert failed\n");
  } else {
    printf("Insert success. ResId=%u\n", xr.m_resId);
  }

  return (0);
}


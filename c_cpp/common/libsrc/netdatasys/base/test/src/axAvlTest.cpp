
#include <stdio.h>
#include "axListBase.hpp"
#include "axAvlTree.hpp"
// #include "axAvlCmMacEntry.hpp"
#include "ace/Log_Msg.h"

int main (int argc, char * argv[])
{
  ACE_LOG_MSG;
  ACE_DEBUG((LM_DEBUG, "hello\n"));

  axListBase list;
  // axAvlTree avlTree;

  // axAvlTree * t = new axAvlTree();

#if 0
  axAvlCmMacEntry * e;
  axAvlCmMacEntry * e1;
  e = new axAvlCmMacEntry("01:02:03:04:05:06");
  t->add(e);
  e1 = new axAvlCmMacEntry("01:02:03:04:05:07");
  t->add(e1);
  e = new axAvlCmMacEntry("01:02:03:04:05:08");
  t->add(e);

  e = (axAvlCmMacEntry *) t->find(e1);

  printf("mac address = %s\n", e->getCmMac());

  for (int i=1; i<1000000; i++) {
    AX_MAC_ADDRESS m;
    sprintf(m, "%d", i);
    axAvlCmMacEntry * ee = new axAvlCmMacEntry(m);
    t->add(ee);
  }

  sleep(30);
#endif

  return (0);
}

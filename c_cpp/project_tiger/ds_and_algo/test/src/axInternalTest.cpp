
#include <stdio.h>
#include <string>
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"
#include "axInternalEmta.hpp"
#include "axGlobalData.hpp"

typedef struct foo_t {
  void    * cmPtr;
  string    myStr;
  AX_INT8   docsisStatus;
  AX_INT8   pingStatus;
  AX_INT8   provStatus;
  AX_INT8   availability;
  bool operator () (const char * a, const char * b) const {
    return false;
  };
} foo_t;

int main (int argc, char * argv[])
{
  axInternalCm * cm = new axInternalCm("foo");
  // axInternalGenMta * m = new axInternalGenMta("foo");
  axInternalEmta * em = new axInternalEmta("foo", "bar");
  axGlobalData::getInstance();
  printf("sizeof(axIntEmtaNonkey_t)=%d\n", sizeof(axIntEmtaNonkey_t));
  printf("sizeof(foo_t)=%d\n", sizeof(foo_t));
  // foo_t foo;
  // memset(&foo, 0, sizeof(foo));
  foo_t foo = {0};
  // Person p={0};
  axIntEmtaNonkey_t bar;
  // memset(&foo, '\0', sizeof(foo));
  // memset(&bar, '\0', sizeof(bar));

  printf("%d,%d,%d,%d\n",
         foo.docsisStatus,foo.pingStatus,foo.provStatus,foo.availability);
  printf("%s\n", foo.myStr.c_str());

  return (0);
}


/* test2.C */
//
// asn/ber encoding and profiling example.
//
//   encodes things forwards, backwards, and using the original CMU
//   encodings.  Run with -h for usage information.
//
#include "config.h"
#include <iostream>
using namespace std;

#include <stdlib.h>
#include <stdio.h>
#include <sys/time.h>
#include <sys/types.h>

#include "asnDataTypes.H"

main(int argc, char *argv[]) {

  // Simple tests...

  // Integer32
  Integer32 i = -32;
  int normi = i;
  
  cout << "int conv: " << normi << "\n";

  // Unsigned32
  Unsigned32 ui = 32;
  unsigned int normui = ui;
  
  cout << "uint conv: " << normui << "\n";

  // Counter32
  Counter32 count = (unsigned int) ui;  // if this works, every other uInt type should as well.
  unsigned int normcount = count;
  
  cout << "count conv: " << normcount << "\n";

  // OctetString
  OctetString str = (string) "hi there";
  string normstr = str;
  
  cout << "string conv: " << normstr << "\n";
  str.append(str);
  cout << "string appended with itself: " << (string) str << "\n";

  // Counter64
  Counter64 count64 = (unsigned long long) count;  // if this works, every other uInt type should as well.
  count64 = count64 * 0xffffffff * 88;  /* shouldn't be possible with 32 bits */
  long long normcount64 = count64;
  
  cout << "count64 conv: " << hex << normcount64 << "\n";

  // Simple (ha) test.  Take a pointer to an integer as it's base
  // class and clone it and see if we get_ back an integer.
  asnDataType *ac = &i;
  cout << "precloned int: " << ac->toString();
  asnDataType *acnew = ac->clone();
  cout << "cloned int: " << acnew->toString();

  ac = &str;
  cout << "orig str: " << ac->toString() << "\n";
  cout << "precloned str: " << ac->toString()  << "\n";
  acnew = ac->clone();
  cout << "cloned str: " << acnew->toString()  << "\n";

  cout << dec << "******************************************************\n";
  cout << "usage tests...\n";

  cout << "UInt: " << ui << "\n";
  cout << "Int: " << i << "\n";
//  ui += 2;
//  cout << "UInt += 2: " << ui << "\n";
  ui = ui + 2;
  cout << "UInt = UInt + 2: " << ui << "\n";
  cout << "UInt + Int: " << (ui + i) << "\n";
//  ui++;
//  cout << "UInt++: " << ui << "\n";
  
  cout << "******************************************************\n";

  // randomize declairations 
  OID x(".1.3.6.1.4.1.2021");
  OID z(".1.3.6.1.4.1.2021.2");
  OID y(".1.3.6.1.4.1.2021.1");
  OID w(".1.3.6.1.4.1.2021");
  
  if (w == x)
    cout << "yep1\n";
  if (w < y)
    cout << "yep2\n";
  if (y > w)
    cout << "yep3\n";
  if (y > z)
    cout << "ack4\n";
  else
    cout << "yep4\n";
  if (z < y)
    cout << "ack5\n";
  else
    cout << "yep5\n";
  if (z != y)
    cout << "yep6\n";
  if (w != x)
    cout << "ack\n";
  else
    cout << "yep7\n";

  /* testing [] operator */
  cout << "testing OID:  " << w.toString() << "\n";
  cout << "oid's 3rd segment: " << z[2] << "\n";
  cout << "oid's 1st segment: " << z[0] << "\n";
  cout << "oid's 6th segment: " << z[6] << "\n";

  /* varbind clone test */
  VarBindList   *vtmp1 = new VarBindList();
  vtmp1->Add(new VarBind(new OID(".1.3"), new Integer32(42)));
  
  VarBindList   *vtmp2 = vtmp1->clone();
  
  cout << "vb2: " << vtmp2->toString() << "\n";

  (*(vtmp1->begin()))->set_value(new OctetString("hi there"));

  cout << "vb1: " << vtmp1->toString() << "\n";
  cout << "vb2: " << vtmp2->toString() << "\n";

  cout << "y+z: " << (y+z).toString() << "\n";
  Integer32 plusi = -i;
  cout << "y+(abs(i)): " << (y+plusi.toOID()).toString() << "\n";
  cout << "y+(abs(i)).crop(5,7): "
       << (y+plusi.toOID()).crop(5,7).toString() << "\n";

  asnDataType *pti = &i;
  Integer32 xx(9876);
  asnDataType *ptxx = &xx;
  cout << "i: " << (int) i << ", xx: " << (int) xx << "\n";
  i = xx;
  cout << "i: " << (int) i << ", xx: " << (int) xx << "\n";
  i = 42;
  cout << "i: " << (int) i << ", xx: " << (int) xx << "\n";
  ptxx->change_value(*pti);
  cout << "i: " << (int) i << ", xx: " << (int) xx << "\n";

  // init the smi library.
  LIBSMI ls;
  
  // set the load path
  ls.path("/usr/local/share/snmp/mibs");
  cout << "path: " << ls.path() << "\n";

  // load a mib into it.
  if (ls.loadModule("SNMP-VIEW-BASED-ACM-MIB").size() == 0)
      cout << "failed to load1\n";
  if (ls.loadModule("SNMPv2-MIB").size() == 0)
      cout << "failed to load2\n";

//   libsmiOid oid(argv[1]);
//   if (!oid.validate())
//       cout << "validation failed\n";
//   else
//       cout << "validation success\n";

  OID a;
  try {
      a.set(argv[1]);
  } catch (illegalOID &err) {
      cerr << err.what() << "\n";
      exit(1);
  }

  cout << "oid: " << (string) a << "\n";
  cout << "oid: " << a.toDisplayString(SINGLE_NODE) << "\n";
  a.set_displayType(FULLY_QUALIFIED);
  cout << "oid: " << (string) a << "\n";
}

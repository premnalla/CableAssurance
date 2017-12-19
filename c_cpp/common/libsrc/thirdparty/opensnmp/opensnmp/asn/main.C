/* main.C */
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

#include "snmpException.H"
#include "asnDataTypes.H"

#define BUFSIZE 8192

void
printbuf(char *buf, char *cp, int maxlinecount) {
  char *cp2;
  int i;

  int owidth = cout.width();
  cout.width(2);
  // XXX:HEX
  cout << "    results: " << (cp-buf) << " (" << hex << (cp-buf) << ") bytes\n      ";
  for(i=1, cp2 = buf; cp2 < cp && maxlinecount; i++, cp2++) {
    cout << (((int) (0xff & (*cp2)) < 0x10) ? "0" : "") <<
      (int) (0xff & (*cp2)) << " ";  // XXX:HEX
    if (i == 16) {
      cout << "\n      ";
      i = 0;
      maxlinecount--;
    }
  }
  cout << dec << "\n";
  cout.width(owidth);
}

void
usage(char *name) {
  cout << "Usage: " << name << " [-c NUM] [-l NUM]\n";
  cout << "\t-c NUM\tencode each object NUM times.\n";
  cout << "\t-l NUM\tNUM maximum lines of output per object breakdown\n";

  cout << "\n";
  cout << "\t-d    \tDefault test list\n";
  cout << "\t-i NUM\tTest on integer32 NUM\n";
  cout << "\t-s STR\tTest with STR as an octetstring\n";
  cout << "\t-o OID\tTest with OID as an object identifier\n";
  cout << "\t-v OID\tAdd a variable (referenced by OID) to a varBindList\n";
  cout << "\t      \tto encode.  The next value (-i, -s, ...) specified on\n";
  cout << "\t      \tthe command linewill be its value\n";
  cout << "\t-p    \tPut the previous defined varbind in a PDU.";
  cout << "\t-P    \tPut the previous defined varbind in a ScopedPDU.";
  cout << "\n";
  cout << "Examples:\n";
  cout << "\t" << name << " -i 42\n";
  cout << "\t" << name << " -o .1.3.6.1.42.42.4242\n";
  cout << "\t" << name << " -v .1.3.6.1.42.42.4242 -i 42 -v .1.3.6.1.4242 -s \"42\"\n";
}

void
mytimediff(struct timeval *t1, struct timeval *t2) {
  t1->tv_sec--;                                   \
  t1->tv_usec += 1000000L;                        \
  t1->tv_sec -= t2->tv_sec;
  t1->tv_usec -= t2->tv_usec;
  if (t1->tv_usec > 1000000L) {
    t1->tv_usec -= 1000000L;               \
    t1->tv_sec++;                          \
  }
}

main(int argc, char *argv[]) {
  try {
    char buf[BUFSIZE];
    char *cp;

    int maxlines = 100, countnum = 1000;

    int num=0;
    asnDataType   *all[num];
    OID           *otmp = 0;
    VarBindList   *vtmp = 0;
    PDU	          *ptmp = 0;
    ScopedPDU     *Ptmp = 0;
    asnDataType   *atmp = 0;

    int addtovarbind=0;

    for(int i=1; i < argc; i++) {
      if (argv[i][0] != '-') {
        usage(argv[0]);
        exit(1);
      }

      switch(argv[i][1]) {
        case 'd':
          if (vtmp == 0) {
            vtmp = new VarBindList();
            all[num++] = vtmp;
          }
          // create the default test list.
          otmp = new OID(".1.3.6.1.4.1.2021.12.99.1");
          atmp = new Integer32(-4242);
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.2");
          atmp = new Unsigned32(4242);
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.3");
          atmp = new OctetString("The meaning of");
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.4");
          atmp = new TimeTicks(424242);
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.5");
          atmp = new OID(".1.3.6.1.4.1.2021.12.42.42.42");
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.6");
          atmp = new Counter32(0x4242);
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.7");
          atmp = new Null();
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.8");
          atmp = new Counter64(0x42424242);
          vtmp->Add(new VarBind(otmp, atmp));

          otmp = new OID(".1.3.6.1.4.1.2021.12.99.10");
          atmp = new Opaque("The life the universe and everything.");
          addtovarbind = 1;
          break;
        
        case 'l':
          maxlines = atoi(argv[++i]);
          break;
        
        case 'c':
          countnum = atoi(argv[++i]);
          break;

        case 'i':
          atmp = new Integer32(argv[++i]);
          break;

        case 'u':
          atmp = new Unsigned32(argv[++i]);
          break;

        case 'a':
          atmp = new IpAddress(argv[++i]);
          break;
        
        case 's':
          atmp = new OctetString(argv[++i]);
          break;

        case 'b':
          atmp = new BitString(argv[++i]);
          break;

        case 'n':
          atmp = new Null();
          break;

        case 'v':
          if (vtmp == 0) {
            vtmp = new VarBindList();
            all[num++] = vtmp;
          }
          addtovarbind=1;
          otmp = new OID(argv[++i]);
          break;
          // fallover into next

        case 'p':
          if (ptmp == 0) {
            ptmp = new PDU(BER_TAG_PDU_GET, 1, 2, 3, new VarBindList(*vtmp));
            all[num++] = ptmp;
          }
          break;

        case 'P':
          if (Ptmp == 0) {
            Ptmp = new ScopedPDU("fake engineID", "a context name", 
                                 new PDU(BER_TAG_PDU_GET, 1, 2, 3, 
                                         new VarBindList(*vtmp)));
            all[num++] = Ptmp;
          }
          break;

        case 'o':
          atmp = new OID(argv[++i]);
          break;

        default:
          usage(argv[0]);
          exit(1);
      }

      if (addtovarbind && vtmp && otmp) {
        if (atmp) {
          vtmp->Add(new VarBind(otmp, atmp));
          addtovarbind = 0;
          otmp = 0;
          atmp = 0;
        }
      } else {
        all[num++] = atmp;
        atmp = 0;
      }

    }
      
    // data to encode:

    for(int i=0; i < num; i++) {
      cout << "\nTest: " << i << "\n";
      cout << "  " << all[i]->toString() << "\n";
  
      cout << "  Encode:\n";

      cp = all[i]->asnEncode(buf+BUFSIZE);
      printbuf(cp+1, buf+BUFSIZE+1, maxlines);

      // attempt to decode it now
      asnDataType *dt;

      if (all[i] == vtmp) {
        cout << "decoding VarBindList...\n";
        dt = new VarBindList();
        dt->asnDecode(cp+1);
      } else if (all[i] == ptmp) {
        cout << "decoding PDU...\n";
        dt = new PDU();
        dt->asnDecode(cp+1);
      } else if (all[i] == Ptmp) {
        cout << "decoding ScopedPDU...\n";
        dt = new ScopedPDU();
        dt->asnDecode(cp+1);
      } else {
        asnDataType::asnDecodeUnknown(cp+1,&dt);
      }

      cout << "\n  Decode:\n    " << dt->toString() << "\n";

      delete dt;
      delete all[i];
    }
    cout << "Done.\n";
  } catch (snmpException &e2) {
    cout << "caught:\n";
    cout << e2.what();
    cout << ".\n";
  } catch (exception &e) {
    cout << "caught generic:\n";
    cout << e.what();
    cout << ".\n";
  } catch (...) {
    cout << "caught something: unknown\n";
  }
}

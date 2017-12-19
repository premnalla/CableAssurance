#include "config.h"
#include <iostream>
using namespace std;
#include <map>

#include "asnDataTypes.H"
#include "snmpIndexes.H"

main(int argc, char *argv[]) {
  list<berTag> lb;
  lb.push_back(BER_TAG_INTEGER32);
  snmpIndexes si(lb), s2(lb), s3(lb);
  map <snmpIndexes, int>::iterator ri;
  map <snmpIndexes, int> data;

  si.set_index_number(1, new Integer32(1));
  s2.set_index_number(1, new Integer32(1));
  s3.set_index_number(1, new Integer32(101010));

  snmpIndexes s4(si);
  s4.set_index_number(1, new Integer32(1));

  data[si] = 1;
  if (data[si] == 1)
      cout << "data1\n";
  if (data[s2] == 1)
      cout << "data2\n";

  ri = data.find(s2);
  if (ri == data.end())
      cout << "data.no\n";
  else
      cout << "data.find(s2): " << ri->second << "\n";

  ri = data.find(s4);
  if (ri == data.end())
      cout << "data.no\n";
  else
      cout << "data.find(s4): " << ri->second << "\n";

//   if (si == s2)
//       cout << "yes1\n";
//   if (si != s3)
//       cout << "yes2\n";

//   s3 = s2;
//   if (si == s3)
//       cout << "yes3\n";
}

#include "config.h"
#include "asnDataTypes.H"
#include "smiNode.H"

int
main(int argc, char **argv) {
  // initialize mib engine
  LIBSMI ls;
    
  // set the load path
  ls.path("/usr/local/share/snmp/mibs");
  if (ls.loadModule("SNMP-VIEW-BASED-ACM-MIB").size() == 0)
      cout << "failed to load1\n";
  if (ls.loadModule("SNMPv2-MIB").size() == 0)
      cout << "failed to load2\n";

  try {
      smiNode node(argv[1]);
      cout << "label:  " << node.label() << "\n";
      cout << "desc:   " << node.description() << "\n";
      cout << "access: " << node.access() << "\n";
      cout << "status: " << node.status() << "\n";
      cout << "kind:   " << node.nodekind() << "\n";

      list<smiNode *> children = 
          node.get_children();
      list<smiNode *>::iterator i;
      cout << "children:" << endl ;
      for (i = children.begin(); i != children.end(); i++) {
          cout << "  " << (*i)->label() << endl ;
      }

  } catch (snmpStringException &e) {
      cout << "OID error: " << e.get_errorMessage() << "\n";
  }
}

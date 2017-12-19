#include "config.h"
#include <iostream>
#include "snmpVACMAccessListObj.H"
#include "snmpVACMGroupListObj.H"
#include "snmpVACMViewListObj.H"

void access_test(void);
void group_test(void);
void view_test(void);

main() {
  view_test();
  //  group_test();
  //  access_test();
}

void access_test(void) {
  snmpVACMAccessListObj l;
  snmpVACMAccessEntryObj *entry;
  OctetString group;
  OctetString context;
  string groupStr;
  string contextStr;

  cout << "Adding access list entries...\n\n";

  group = "group1";
  context = "context1";
  l.createEntry(&group,&context,ANY,NOAUTHNOPRIV);

  group = "group2";
  context = "context2";
  l.createEntry(&group,&context,ANY,NOAUTHNOPRIV);

  group = "group3";
  context = "context3";
  l.createEntry(&group,&context,ANY,NOAUTHNOPRIV);

  l.print();

  cout << "Searching for an access list entry...\n\n";

  group = "group2";
  context = "context2";
  entry = l.get_entry(&group,&context,ANY,NOAUTHNOPRIV);

  if(entry) {
    groupStr = *entry->get_groupName();
    contextStr = *entry->get_contextPrefix();
    
    cout << "Group: " << groupStr << "\n";
    cout << "Context: " << contextStr << "\n";
  } else {
    cout << "Failed to find the entry...\n";
  }

  cout << "\nDeleting an access list entry...\n\n";
  
  l.destroyEntry(&group,&context,ANY,NOAUTHNOPRIV);
  
  l.print();
  
  cout << "Adding an access list entry...\n\n";
  
  group = "group4";
  context = "context4";
  entry = l.createEntry(&group,&context,ANY,NOAUTHNOPRIV);
  
  l.print();
  
  cout << "Destroying all access list entries...\n\n";
  
  l.destroyAll();
  
  l.print();
}

void group_test(void) {
  snmpVACMGroupListObj l;
  snmpVACMGroupEntryObj *entry;
  OctetString security;
  OctetString group;
  string securityStr;
  string groupStr;

  cout << "Adding group list entries...\n\n";

  security = "context1";
  group = "group1";
  entry = l.createEntry(ANY,&security);
  entry->set_groupName(&group);

  security = "context2";
  group = "group2";
  entry = l.createEntry(ANY,&security);
  entry->set_groupName(&group);

  security = "context3";
  group = "group3";
  entry = l.createEntry(ANY,&security);
  entry->set_groupName(&group);

  l.print();

  cout << "Searching for a group list entry...\n\n";

  security = "context3";
  entry = l.get_entry(ANY,&security);

  if(entry) {
    securityStr = *entry->get_securityName();
    groupStr = *entry->get_groupName();
    
    cout << "Security: " << securityStr << "\n";
    cout << "Group: " << groupStr << "\n";
  } else {
    cout << "Failed to find the entry...\n";
  }

  cout << "\nDeleting a group list entry...\n\n";
  
  l.destroyEntry(ANY,&security);
  
  l.print();
  
  cout << "Adding a group list entry...\n\n";
  
  security = "context4";
  group = "group4";
  entry = l.createEntry(ANY,&security);
  entry->set_groupName(&group);

  l.print();
  
  cout << "Destroying all group list entries...\n\n";
  
  l.destroyAll();
  
  l.print();
}

void view_test(void) {
  snmpVACMViewListObj l;
  snmpVACMViewEntryObj *entry;
  OctetString viewName;
  OID oid;
  int subTreeLen;
  int maskLen;
  string viewStr;
  string maskStr;
  string oidStr;

  cout << "Adding view list entries...\n\n";

  viewName = "view1";
  oid = ".1.6.23.1.14.23.55";
  // subTreeLen = oid.length();            // broken;
  subTreeLen = 7;
  entry = l.createEntry(&viewName,&oid,subTreeLen);
  maskStr[0] = 223;
  entry->set_viewMask(&maskStr);
  maskLen = 7;
  entry->set_viewMaskLength(maskLen);
  cout << "Mask: " << maskStr << "\n";

  viewName = "view1";
  oid = ".1.6.22.1.14.23.55";
  // subTreeLen = oid.length();            // broken;
  subTreeLen = 7;
  entry = l.createEntry(&viewName,&oid,subTreeLen);

  viewName = "view3";
  oid = ".1.6.23.1.14.23.55.12.1.14";
  // subTreeLen = oid.length();            // broken;
  subTreeLen = 10;
  entry = l.createEntry(&viewName,&oid,subTreeLen);

  l.print();

  viewName = "view1";
  oid = ".1.6.22.1.14.23.55";
  subTreeLen = 7;
  entry = l.get_entry(&viewName,&oid,subTreeLen);

  if(entry) {
    viewStr = *entry->get_viewName();
    oid = *entry->get_viewSubTree();
    oidStr = oid.toString();
    
    cout << "View: " << viewStr << "\n";
    cout << "OID: " << oidStr << "\n";
  } else {
    cout << "Failed to find the entry...\n";
  }

  viewName = "view3";
  oid = ".1.6.23.1.14.23.55.12.1.14";
  subTreeLen = 10;
  l.destroyEntry(&viewName,&oid,subTreeLen);

  l.print();

  l.destroyAll();
  
  l.print();
}

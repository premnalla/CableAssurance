//
// example.C
//
// Example of existing MIB Class functionality.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>  Sun Feb 27 16:08:41 EST 2000
//

#include "config.h"
#include "snmp_error.H"
#include "mib.H"


int
main(void) {

	// -0-
	// Class LIBSMI.
	//
	cout	<< endl
		<< "==== USING Class LIBSMI ===="
		<< endl << endl;

	LIBSMI ls;

	cout	<< "LIBSMI search path:\n    "
		<< ls.path()
		<< endl << endl;

	cout	<< "Verbose setting while loading modules..." 
		<< endl << endl;

	ls.errorLevel(9);
	ls.loadModule("SNMP-VIEW-BASED-ACM-MIB");
	ls.errorLevel(0);

	cout	<< endl;



	// -0-
	// Class libsmiOid.
	//
	cout	<< endl
		<< "==== USING Class libsmiOid ===="
		<< endl << endl;

	libsmiOid lso(".1.3.6");

	cout	<< "Dump OID path:\t\t" << lso.str()
		<< endl;
	
	lso += "1.6.3.16";

	cout	<< "Add to OID path:\t" << lso.str()
		<< endl << endl;

	lso.validate();

	cout	<< "Validate OID path and convert to labels:\n    "
		<< lso.str_label()
		<< endl << endl;


	SmiNode *sn = ls.getNodeByOID(lso);

	cout	<< "Lookup an OID path in libsmi cache via LIBSMI..."
		<< endl
		<< "\tNode:\t"		<< sn->name	<< endl
		<< endl;

	// ls.freeNode(sn);	// XXX  What happened to this in v0.2?



	// -0-
	// Classes mibModule and mibNode.
	//
	cout	<< endl
		<< "==== USING Classes mibModule and mibNode ===="
		<< endl << endl;

	mibNode	 mn("SNMP-NOTIFICATION-MIB"),
		*mn2;


	cout 	<< "Select a node using strings or ints..."
		<< endl << endl;

	mn2 = &mn[".1.3.6.1.6.3.13"];
	mn2->dumpNode();

	cout	<< endl;

	mn2 = &mn[".iso.org.dod.internet.snmpV2"];
	mn2 = &(*mn2)["snmpModules.snmpNotificationMIB"];
	mn2->dumpNode();

	cout	<< endl;

	mn2 = &mn[1][3][6][1][6][3][13];
	mn2->dumpNode();


	cout	<< endl
		<< "Dump module information:" 
		<< endl << endl;

	mn2->dumpModule();


	cout	<< endl << endl
		<< "Add additional modules." 
		<< endl
		<< "Dump the contents of the MIB tree:"
		<< endl << endl;

	mn.addModule("SNMP-MPD-MIB");
	mn.dumpTree(MN_PT_LABELANDNUMBER);


	return 0;

}  // main (void)


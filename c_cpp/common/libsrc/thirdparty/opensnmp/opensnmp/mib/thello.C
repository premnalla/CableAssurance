//
// thello.C
//
// Tests of mibModule and mibNode.
//
// FIX  Individual tests should be more modular.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#include "config.h"
#include "snmp_error.H"
#include "mib.H"



// ------------------------------------ -o- 
// Prototypes & main.
//
static string	progname;

void basic_module_load(void);
void basic_node_info(int argc, char *argv[]);
void module_list_creation(int argc, char *argv[]);
void dump_mibroot(void);
void setFields(void);
void reference_n_create(int argc, char *argv[]);
void alt_paths(int argc, char *argv[]);
// void XXX(int argc, char *argv[]);


int
main(int argc, char *argv[]) {
	
	progname = argv[0];

	// basic_module_load();
	// basic_node_info(argc, argv);

	// dump_mibroot();
	// module_list_creation(argc, argv);
	// setFields();

	reference_n_create( argc, argv);
	// alt_paths(argc,argv);

	// XXX(argc,argv);

	return 0;

} /* main() */



/*
// ------------------------------------ -o- 
// XXX
//
void
XXX( int argc, char *argv[] )
{
EM0(1, "XXX");

}  // XXX
 */



// ------------------------------------ -o- 
// basic_module_load (void)
//
// NOTE: Lookup for SNMPv2-SMI (mn2) causes a seg fault.  FIX.
//
void
basic_module_load(void)
{
EM(1);

	mibNode	mn("SNMP-USER-BASED-SM-MIB"),
		// mn2("SNMPv2-SMI"),
		mn3("SNMP-FRAMEWORK-MIB");


	cout	<< "Module name is " << "\"" << mn.moduleName() << "\"." 
		<< endl
		<< "Organization:\t" << mn.organization() 
		// << endl
		// << "Contact Info:" << endl << mn.contactInfo() 
		<< endl;

// 	cout	<< endl
// 		<< "Module name is " << "\"" << mn2.moduleName() << "\"." 
// 		<< endl
// 		<< "Organization:\t" << mn2.organization() 
// 		<< endl;

	cout	<< endl
		<< "Module name is " << "\"" << mn3.moduleName() << "\"." 
		<< endl
		<< "Organization:\t" << mn3.organization() 
		<< endl;


	// cout << "=== module dump ===" << endl;
	// mm->module_dump();

	// cout << "=== node dump ===" << endl;
	// mm->dump();

	// cout << endl << "=== module list dump ===" << endl;
	// mm->module_dumpall();

	exit(0);

}  // basic_module_load (void)


// ------------------------------------ -o- 
// basic_node_info (int, char**)
//
// Parameters:
//	argc
//	argv
//
// NOTE usmStats for <node_name> causes a seg fault... FIX
//
void
basic_node_info(int argc, char* argv[])
{
EMA(1, "int, char**");

#define USAGE	"Usage: " << progname << " <module> <node_name>"

	if (argc < 3) { 
		cerr << USAGE << endl; 
		exit(1); 
	}

	mibNode	mn;

	mn.errorLevel(9);
	mn.loadModule(argv[1]);
	mn.moduleName(argv[1]);

	mn.name(argv[2]);

	cout << "Access:\t"	 << mn.access() << endl;
	cout << "Status:\t"	 << mn.status() << endl;
	cout << "Description:\n" << mn.description() << endl;

	// exit(0);

#undef USAGE
	
}  // basic_node_info (int, char**)



// ------------------------------------ -o- 
// dump_mibroot (void)
//
void
dump_mibroot(void)
{
EMA(1, "void");

	mibModule::mibRoot	mr("splat");


	cout << mr;

	// EM1(1, mr.dump());

	
	exit(0);

}  // dump_mibroot (void)



// ------------------------------------ -o- 
struct fields {
	string	rname,	    // FIX -- redundant?
		roid;

	bool	isfaux,	    // TRUE if module's a custom "node root."
		iscreated;  // TRUE if module OID has been created.
};

void 
setFields(void)
{
	//struct fields	*f = new struct fields;
	fields  *f = new fields;

	f->rname	 = "blort";
	f->roid		 = "blooper";
	f->isfaux	 = false;
	f->iscreated	 = true;

	cout << "root name: " << f->rname << endl;
}




// ------------------------------------ -o- 
// module_list_creation (int, char**)
//
// Parameters:
//	argc
//	argv
//
// Do the following:
//	+ initial load
//	+ dump list
//	+ dump from a separate invocation
//	+ add module
//	+ dump again.
//
//
// FIX -- note that mn3.moduleName() won't work because of returned by
//	reference... ?!
//
void
module_list_creation(int argc, char* argv[])
{
EMA(1, "int, char**");

	//mibNode	mn("SNMP-MPD-MIB");
	// mibNode mn2("SNMPv2-SMI");
	mibNode mn3("SNMP-FRAMEWORK-MIB");
	//mibNode mn3;

	// cout << "SMI path: " << mn3.getPath() << endl;

	//cout << "Module Name: " << mn3.moduleName << endl;


	//cout << "Dumping mn module list..." << endl;
	//mn.dumpModuleRoots();

	//cout << "Dumping mn3 module list..." << endl;
	//mn3.dumpModuleRoots();

	//mn3.addModule("SNMP-USER-BASED-SM-MIB");
	//cout << "Dumping mn3 module list again..." << endl;
	//mn3.dumpModuleRoots();


	exit(0);
	
}  // module_list_creation (int, char**)



// ------------------------------------ -o- 
// reference_n_create
//
// FIX treat mn like mn2.
//
void
reference_n_create( int argc, char *argv[] )
{
	string	framework_s	= ".1.3.6.1.6.3.10",
		usmstats_s	= ".1.3.6.1.6.3.15.1.1";

	mibNode	 mn("SNMP-FRAMEWORK-MIB"),
		*mn2;

	mn2 = &mn[framework_s];
	

	cout << endl;

	mn2->dumpModule();
	// mn.dumpModule();
	cout << endl;

	mn2->dumpNode();
	// mn.dumpNode();
	cout << endl;

	cout	<< "List of longest OID paths in MIB tree:" 
		<< endl << endl;

	// mn[".1"].dumpTree(MN_PT_LABELANDNUMBER);
	// mn2->dumpTree(MN_PT_LABELANDNUMBER);
	(*mn2)[""].dumpTree(MN_PT_LABELANDNUMBER);
	cout << endl;


	//
	// Add SNMP-USER-BASED-SM-MIB.
	// 
	mn.addModule("SNMP-USER-BASED-SM-MIB");
	mn2 = &mn[usmstats_s];

	cout << endl;
	mn2->dumpModule();
	cout << endl;
	mn2->dumpNode();
	cout << endl;

	mn.dumpTree(MN_PT_LABELANDNUMBER);
	cout << endl;


	//
	// Relative paths.
	//
	mn2 = &mn[".1.3.6.1.6.3"];

	cout << endl;
	mn2->dumpNode();
	cout << endl;

	mn2 = &(*mn2)["15"];

	mn2->dumpNode();
	cout << endl;


}  // reference_n_create



// ------------------------------------ -o- 
// alt_paths
//
// Tests the following:
//	paths using u_int oids
//	paths using labels
//	paths going UP.
//
void
alt_paths( int argc, char *argv[] )
{
EM0(1, "alt_paths");

        string  framework_s     = ".1.3.6.1.6.3.10",
                usmstats_s      = ".1.3.6.1.6.3.15.1.1";

	libsmiOid framework_lso(framework_s);

        mibNode  mn("SNMP-FRAMEWORK-MIB"),
                *mn2;

	mn.addModule("SNMP-USER-BASED-SM-MIB");


	//
	// Paths using u_int oids.
	//
	cout << endl << "STRING NUMERIC PATH..." << endl;
	mn[framework_s].dumpNode();
	cout << endl;


	cout << endl << "NUMBER PATH..." << endl;
	mn[1][3][6][1][6][3][10].dumpNode();
	cout << endl;

	
	//
	// Paths using labels.
	//
	cout << endl << "STRING LABEL PATH..." << endl;

	if ( ! framework_lso.validate() ) 
		cout << "=== validation failed === " << endl;

	mn[framework_lso.str_label()].dumpNode();
	cout << endl;


	cout << endl << "STRING LABEL PATH (part 2)..." << endl;
	framework_lso.validate();
	framework_lso.size(6);
	// cout	<< framework_lso.str_label() << endl
		// << framework_lso.str_numeric() << endl;
	mn2 = &mn[framework_lso.str_label()];

	mn2->dumpNode();
	cout << endl;

	(*mn2)["snmpFrameworkMIB"].dumpNode();
	cout << endl;


}  // alt_paths


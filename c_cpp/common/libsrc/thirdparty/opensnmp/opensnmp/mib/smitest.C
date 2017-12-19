//
// smitest.C
//
// Tests of LIBSMI and libsmiOid.
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
char	*progname;
void using_smiGetNode(int argc, char *argv[]);
void using_smiGetNode2(int argc, char *argv[]);
void getting_oids(int argc, char *argv[]);

void oid_test(int argc, char *argv[]);
void oid_test2(int argc, char *argv[]);
void subpath_test(int argc, char *argv[]);
void label_validity_test(int argc, char *argv[]);
void label_validity_test2(int argc, char *argv[]);
void lso_operations(int argc, char *argv[]);
// void XXX(int argc, char *argv[]);

#define ANNOUNCE(x)						\
	cout 	<< "======================="	<< endl		\
		<< #x				<< endl		\
		<< "======================="	<< endl;

int 
main(int argc, char *argv[])
{
	progname = argv[0];

	// using_smiGetNode(argc, argv);
	// using_smiGetNode2(argc, argv);
	// getting_oids( argc, argv);
	
	// oid_test(argc,argv);
	// oid_test2(argc,argv); 

	// subpath_test(argc,argv);
	// label_validity_test(argc,argv);
	label_validity_test2(argc,argv);
	// lso_operations(argc, argv);

	// XXX(argc,argv);

	return 0;

} // main(argc, argv)



/*
// ------------------------------------ -o- 
// XXX
//
void
XXX( int argc, char *argv[] )
{
EM(1);

}  // XXX
 */



// ------------------------------------ -o- 
// using_smiGetNode (int, char**)
//
// Dump basic SmiNode information if node is found.
//
// FIX did not figure out how to give arguments of: 
//	NULL, <full_oid>
//	NULL, <numeric_oid>
//	...
//
void
using_smiGetNode(int argc, char *argv[])
{
EMA(1, "int, char**");

	LIBSMI		ls;
	SmiNode	*	sn;

	if (argc < 3) {
		cerr	<< "Usage: smitest <module_name> <relative_oid>"
			<< endl;
		exit(1);
	}

	cout	<< endl
		<< "Previous error level / flags:\t"	
		<< ls.errorLevel(9) << " / " << hex << ls.flags() 
		<< endl;

	cout	<< "Current error level / flags:\t"	
		<< ls.errorLevel() << " / " << hex << ls.flags()
		<< endl;

	cout	<< "Previous MIB path:\t"	<< ls.path( "=../mibs" ) 
		<< endl;

	cout	<< "Current MIB path:\t"	<< ls.path()
		<< endl << endl;


	cout	<< endl
		<< "Loaded module:\t"	<< ls.loadModule(argv[1])
		<< endl
		<< "Looking up OID:\t"	<< argv[2]
		<< endl;


	// sn = ls.getNode(argv[1], argv[2]);
	sn = ls.getNode(argv[2]);  // FIX -- this now ignores module parameter 

	if (sn) {
		cout	<< endl
			<< "    name:\t" << sn->name		  << endl
			<< "    oid:\t"	 << sn->oid[sn->oidlen-1] << endl
			<< endl;
	} else {
		cout	<< "Got nothing..."
			<< endl;
	}

	// ls.freeNode(sn);	// XXX  Gone in v0.2.


	exit(0);

}  // using_smiGetNode (int, char**)



// ------------------------------------ -o- 
// using_smiGetNode2 (int, char**)
//
// Dump basic SmiNode information if node is found.
//
// FIX did not figure out how to give arguments of: 
//	NULL, <full_oid>
//	NULL, <numeric_oid>
//	...
//
void
using_smiGetNode2(int argc, char *argv[])
{
EMA(1, "int, char**");

	mibModule	mm("SNMP-FRAMEWORK-MIB");
	libsmiOid	lso(".1.3.6.1.6.3.10");
	LIBSMI		ls;
	SmiNode	*	sn;

	ls.errorLevel(9);
	// ls.loadModule("SNMP-FRAMEWORK-MIB");

	cout	<< ((ls.isLoaded("SNMP-FRAMEWORK-MIB")) ? "true" : "false")
		<< endl
		<< ls.path()
		<< endl << endl;


	for (	int i = 4;
		i < lso.maxsize();
		i++ )
	{
		lso.subpath(0, i);
		sn = ls.getNodeByOID(lso);
		// sn = ls.getNodeByOID(lso.str());
		// sn = smiGetNodeByOID( lso.size(), lso.array() );

		EM3(1, lso.size(), sn->name, sn->oid[ sn->oidlen ] );

		// ls.freeNode(sn);	// XXX  Gone in v0.2.
	}

	exit(0);

}  // using_smiGetNode2 (int, char**)



// ------------------------------------ -o- 
// getting_oids (int, char**)
//
void
getting_oids(int argc, char* argv[])
{
EMA(1, "int, char**");

#define USAGE	"Usage:"						\
	<< "\t" << progname << " -m <module_name> <node_name>" << endl	\
	<< "\t" << progname << " -n <fully_qualified_nodename>" << endl	\
	<< "\t" << progname << " -s <absolute_numerical_oid>" << endl	\
	;


	// mibNode mn3("SNMP-FRAMEWORK-MIB");
	LIBSMI	ls("SNMP-FRAMEWORK-MIB");
	SmiNode	*sn;


	// ------------------------------------ -o- 
	if ( ! argv[1] ) { cout << USAGE; exit(1); }

	switch ( *(argv[1]+1) ) {
	// case 'm':	sn = ls.getNode( argv[2], argv[3] );	break;
	case 'm':	sn = ls.getNode( argv[3] );	break;
		// FIX -- this now ignores module parameter.
	case 'n':	sn = ls.getNode( argv[2] );		break;
	case 's':	sn = ls.getNodeByOID( argv[2] );	break;
	default:
		cout << USAGE;
		exit (1);
	}  // switch



	// ------------------------------------ -o- 
	if ( !sn ) {
		cout << "sn == NULL" << endl;
		exit (0);
	}

	cout << "sn == " << sn << endl << endl;


	libsmiOid lso(sn->oidlen, sn->oid);

	EM3(1,	 sn->name,
		 sn->oidlen,
		 lso.str() );

	EM3(1,	 (int) sn->decl,
		 (int) sn->access,
		 (int) sn->status );

	EM4(1,	 sn->format,
				    // sn->value,  // FIX -- define operator<<
		 sn->units,
		 sn->description,
		 sn->reference );

	EM3(1,	 (int) sn->indexkind,
		 sn->implied,
		 sn->create );

	EM1(1,	 sn->nodekind );
		
	exit(0);
	

#undef USAGE

}  // getting_oids (int, char**)



// ------------------------------------ -o- 
// oid_test
//
// First libsmiOid test.
//
void
oid_test( int argc, char *argv[] )
{
EM0(1, "oid_test");


	u_int		 ia2[] = { 1, 2, 3, 4, 5 };
	string		 s2 = ".1.2.3.4.5";
	vector<u_int>	 uiv2;
	vector<string>	 sv2;

		uiv2.push_back(1); uiv2.push_back(2); uiv2.push_back(3);
			uiv2.push_back(4); uiv2.push_back(5);

		sv2.push_back("b"); sv2.push_back("l"); sv2.push_back("o");
			sv2.push_back("r"); sv2.push_back("t");

	u_int		*ia;
	string		 s;
	vector<u_int>	 uiv;
	vector<string>	 sv;

	libsmiOid	*lso;

	if ( argc < 2 ) {
		cout << "Usage: smitest [-savV][-u <oid_string>]" << endl;
		exit(1);
	}

	switch ( *(argv[1]+1) ) {
	case 's':	lso = new libsmiOid(s2);	break;
	case 'a':	lso = new libsmiOid(5, ia2);	break;
	case 'v':	lso = new libsmiOid(uiv2);	break;
	case 'V':	lso = new libsmiOid(sv2);	break;
	case 'u':	lso = new libsmiOid(argv[2]);	break;
	default:
		cout << "Usage: smitest [-savV][-u <oid_string>]" << endl;
		exit(1);
	}


	cout << "lso string:\t" << lso->str() << endl;

	cout << "lso array:\t";
	if ( lso->absolute() )  cout << "(absolute) ";
	ia = lso->array();
	for (	int i = 0;
		ia && (i < lso->size());
		i++ )	{ cout << ia[i] << " "; }
	cout << endl;

	cout << "lso vector:\t";
	if ( lso->type() == LSO_ET_LABEL ) {
		sv = lso->vect_label();
		for (	libsmiOid::oid_label_const_iterator  CI = sv.begin();
			(! sv.empty()) && (CI != sv.end());
			CI++ )	{ cout << *CI << " "; }
		cout << endl;

	} else {
		uiv = lso->vect_numeric();
		for (	libsmiOid::oid_numeric_const_iterator CI = uiv.begin();
			(! uiv.empty()) && (CI != uiv.end());
			CI++ )	{ cout << *CI << " "; }
		cout << endl;
	}


}  // oid_test



// ------------------------------------ -o- 
// oid_test2
//
/* Test the following libsmiOid functions...

    absolute()   Return TRUE if OID path begins at MIB root.
    maxsize()    Always returns full OID path length.
    size()       Set/return current OID path length.
    subpath()    (Re)set beginning/ending indeces for OID path.
    
   ...and their impact upon output functions.
 */
//
void
oid_test2( int argc, char *argv[] )
{
EM(1);

	libsmiOid	lso_l("a.b.c.d.e"),
			lso_n(".1.2.3.4.5");

	cout 	<< "l:\t" << lso_l.str() 
		<< setw(20) << "absolute = " << lso_l.absolute()
		<< endl
		<< "n:\t" << lso_n.str() 
		<< setw(20) << "absolute = " << lso_n.absolute()
		<< endl << endl;

	lso_l.subpath(1, 4);
	lso_n.subpath(3, 5, true);

	cout 	<< "l:\t" << lso_l.str() 
		<< setw(20) << "maxsize = " << lso_l.maxsize()
		<< endl
		<< "n:\t" << lso_n.str() 
		<< setw(20) << "maxsize = " << lso_n.maxsize()
		<< endl << endl;

	cout 	<< "l:\t" << lso_l.str() 
		<< setw(20) << "maxsize = " << lso_l.maxsize()
		<< endl
		<< "n:\t" << lso_n.str() 
		<< setw(20) << "maxsize = " << lso_n.maxsize()
		<< endl << endl;

	lso_n.subpath();
	cout	<< "n:\t" << lso_n.str() 
		<< setw(20) << "maxsize = " << lso_n.maxsize()
		<< endl;


}  // oid_test2


// ------------------------------------ -o- 
// subpath_test
//
// Test subpath() and size() in libsmiOid
//
void
subpath_test( int argc, char *argv[] )
{
EM(1);

#define USAGE	"Usage: smitest <begin> <end>"
// #define USAGE	"Usage: smitest <size>"

	// if (argc < 2) {
	if (argc < 3) {
		cout << USAGE << endl;
		exit (1);
	}


	libsmiOid	lso_n(".1.2.3.4.5");
	libsmiOid	lso_l("a.b.c.d.e");

	cout	<< "n (original):\t" << lso_l.str() 
		<< setw(20) << "absolute = " << lso_l.absolute()
		<< endl;

	lso_l.subpath(atoi(argv[1]), atoi(argv[2]));
	// lso_l.subpath(2, 4, true);
	// lso_l.size( atoi(argv[1]) );

	cout 	<< "n:\t" << lso_l.str() 
		<< setw(20) << "maxsize = " << lso_l.maxsize()
		<< endl;

}  // subpath_test



// ------------------------------------ -o- 
// label_validity_test
//
/* Tests for libsmiOid
	validate()
	type()
	label()
	oid()
	opeators...
 */
//
void
label_validity_test( int argc, char *argv[] )
{
EM(1);

	LIBSMI		ls("SNMP-FRAMEWORK-MIB");

	libsmiOid n(".1.3.6.1.6.3.10");
	libsmiOid l(".iso.org.dod.internet.snmpV2.snmpModules.snmpFrameworkMIB");
	// libsmiOid n(".1.3.6.1.6.3.999"),
	// libsmiOid l(".iso.org.dod.BLOOP.snmpV2.snmpModules.snmpFrameworkMIB");


	cout	<< "n type / size:\t"	
		<< (int) n.type() << " / " << n.size() << endl;
	cout	<< "l type / size:\t"	
		<< (int) l.type() << " / " << l.size() << endl;


	n.index(LIBSMIOID_END);
	l.index(LIBSMIOID_END);
	cout	<< "\tnumeric\tlabel" << endl;

	for (	int vsize = n.size()-1; 
		vsize >= 0;
		vsize-- )
	{
		cout	<< "\t" << n.oid() << "\t" << l.label() << endl;

		if (vsize > 0) { n.prev(); l.prev(); }
	}


	l.validate();
	n.validate();

	cout << "n as label:\t" << n.str_label() << endl;
	cout << "l as numeric:\t" << l.str_numeric() << endl;

	cout	<< endl << "ticker tape n:\t";
	n.rewind();
	for ( int i = 0; i < n.size(); i++ ) {
		if (i%2)	cout << "." << n.oid();
		else		cout << "." << n.label();
		n.next();
	}
	cout << endl;

	// cout << "(numeric l)  " << l.type(LSO_ET_NUMERIC).str() << endl;
	// cout << "(labelled l) " << l.type(LSO_ET_LABEL).str() << endl;

	cout	<< endl << "ticker tape l:\t";
	l.rewind();
	l.type(LSO_ET_LABEL);	
		// FIX -- NIT shouldn't be necessary to reset type to label...
	for ( int i = 0; i < l.size(); i++ ) {
		if (i%2)	cout << "." << l.label();
		else		cout << "." << l.oid();
		l.next();
	}
	cout << endl;


	cout	<< endl 
		<< "Comparison tests (set l smaller, then n XX l) --" 
		<< endl;

	// n.size(n.maxsize()-1);
	l.size(l.maxsize()-1);

	if ( n < l )	cout << "   less-than" << endl;
	else		cout << "   NOT less-than" << endl;
	
	if ( n > l )	cout << "   greater-than" << endl;
	else		cout << "   NOT greater-than" << endl;
	
	if ( n == l )	cout << "   equal" << endl;
	else		cout << "   NOT equal" << endl;

	if ( n != l )	cout << "   not-equal" << endl;
	else		cout << "   NOT not-equal" << endl;
	
}  // label_validity_test



// ------------------------------------ -o- 
// label_validity_test2
//
//
void
label_validity_test2( int argc, char *argv[] )
{
EM(1);

	LIBSMI		ls("SNMP-FRAMEWORK-MIB");

	libsmiOid n(".1.3.6.1.6.3.10");
	libsmiOid l(".iso.org.dod.internet.snmpV2.snmpModules.snmpFrameworkMIB");
	// libsmiOid n(".1.3.6.1.6.3.999"),
	// libsmiOid l(".iso.org.dod.BLOOP.snmpV2.snmpModules.snmpFrameworkMIB");


	n.validate();
	n.type(LSO_ET_LABEL);
	cout	<< "label 4: " << n.label(4) << endl;
	n.subpath(0, 5);
	cout	<< "label 4: " << n.label(4) << endl;
	n.subpath();
	cout	<< "label 4: " << n.label(4) << endl;
	
}  // label_validity_test2



// ------------------------------------ -o- 
// lso_operations
//
void
lso_operations( int argc, char *argv[] )
{
EM(1);

	libsmiOid	one("1.2.3.4"),
			two("10.11.12"),
			three(".20.21.22"),
			four("zero");


	cout	<< "one:  "	<< one.str()	<< endl;
	cout	<< "two:  "	<< two.str()	<< endl;
	cout	<< "three:  "	<< three.str()	<< endl;
	cout	<< "four:  "	<< four.str()	<< endl;

	one += two;
	one += three;
	one += four;

	cout	<< one.str()	<< " " << one.size() << endl;
	cout	<< two.str()	<< endl;
	cout	<< three.str()	<< endl;
	cout	<< four.str()	<< endl;

}  // lso_operations


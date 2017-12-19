//
// exampleofuse2.C
//
// Second draft of MIB Class interface...
//



#include "config.h"
#include "snmp_error.H"
#include "mib.H"



int
main(void) {

	mibNode mn("SNMP-USER-BASED-SM-MIB"),
		mn_alias, mn_alias2,
		mn2;

	varBind	vb, vb2,	// FIX -- need details on var-bind type.
		vb_list, vb_list2;



	// -0-
	// Direct reference and naming from a known root.
	// Aliasing.
	//
	mn["1.1.1"].description();
	mn[1][1][1].description();
	mn["1"]["1"]["1"].description();

	mn["usmMIBObjects.usmStats.usmStatsUnsupportedSecLevels"].description();
	mn["usmMIBObjects"]["usmStats"]
				["usmStatsUnsupportedSecLevels"].description();

	mn_alias = mn["1.1.1"].alias();
	// TBD -- 
	//	Should mn_alias2 or mn_alias take the other as an argument?
	//


	// -0-
	// Dump common fields.
	//
	mn_alias.max_access();
	mn_alias.status();
	mn_alias.description();

	mn_alias.dump();	// Includes value if one exists.
				// FIX -- what if the value is "complex"?


	// -0-
	// Indirect and absolute path references.
	//
	mn2 = mn_alias;

	mn2["+2"].description();
	mn2["+usmStatsNotInTimeWindows"].description();

	mn2["++1.2"].description();
	mn2["++usmStats.usmStatsNotInTimeWindows"].description();

	mn2[".iso.org.dod.internet.snmpV2.snmpModules.snmpUsmMIB.usmMIBObjects.usmStats.usmStatsUnsupportedSecLevels"].description();
	mn2[".iso.org.dod.internet.snmpV2.snmpModules.snmpUsmMIB"]
	    ["usmMIBObjects.usmStats.usmStatsUnsupportedSecLevels"].description();

	mn2[".1.3.6.1.6.3.15.1.1.1"].description();

	mn2[".1"].description();
	mn2[][1].description();	// FIX -- how to reference root w/o quotes?


	// -0-
	// Print or capture simple non-table values.
	// Capture non-table walk output.
	//
	// Set simple values singly.
	// (Set simple values in bulk.)
	//
	vb = mn[1][1][1][0].get();
	vb.dump();

	mn[1][1][1][0].value();

	vb_list = mn["usmMIBObjects.usmStats"].walk();
	vb_list.dump();


	// -0-
	// TBD -- is this what set should look like?
	//
	mn[1][1][1][0].set(10000);	
	mn[1][1][1][0].set(Counter32, 10000);

	vb2.set( mn["1.1.1.0"].oid(), Counter32, 10000 );

	mn2["1.1.1.0"] = vb2;	// FIX -- must the OIDs match exactly?

	vb_list2.set( mn["1.1.1.0"].oid(), Counter32, 10001 );
	vb_list2.setadd( mn["1.1.2.0"].oid(), Counter32, 10002 );
	vb_list2.setadd( mn["1.1.3.0"].oid(), Counter32, 10003 );
	vb_list2.setadd( mn["1.1.4.0"].oid(), Counter32, 10004 );
	vb_list2.setadd( mn["1.1.5.0"].oid(), Counter32, 10005 );
	vb_list2.setadd( mn["1.1.6.0"].oid(), Counter32, 10006 );

	mn2["1.1"] = vb_list2;

} // main()


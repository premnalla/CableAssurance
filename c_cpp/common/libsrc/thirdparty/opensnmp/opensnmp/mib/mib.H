//
// mib.H
//
// Master include file for mib sub-system.
//
// FIX  Use autoconf for constants defined here and in other *.H's.
// FIX  All header guards should be localized to PHASE2 codebase.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _MIB_H
#define _MIB_H



// ------------------------------------ -o- 
// Constants.
//

//#define PHASE2_HOME	"/usr/home/dreeder/snmp/UCDSTASH/p2/phaseII"
	// XXX



// ------------------------------------ -o- 
// Includes.
//
#include <iostream>
#include <iomanip>

#include <utility>

#include <string>
#include <vector>
#include <map>

#include <cstdlib>

// #include "snmpObj.H"		// FIX -- Need for integration with PHASE2.

// #include "snmp_error.H" // Ick.

#include "csmi"
#include "libsmiOid.H"
#include "LIBSMI.H"

#include "mibModule.H"
#include "mibNode.H"


#endif  // _MIB_H

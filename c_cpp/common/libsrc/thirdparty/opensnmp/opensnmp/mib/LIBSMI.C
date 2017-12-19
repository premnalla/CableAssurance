//
// LIBSMI.C
//
// Class wrapper for basic libsmi operations.
//
// FIX  Handle error diagnostics by using or copying libsmi.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#include "config.h"
#include "snmp_error.H"
#include "mib.H"



// ------------------------------------ -o- 
// Set class globals.
//
int LIBSMI::refcnt = 0;



// ------------------------------------ -o- 
// LIBSMI::init_LIBSMI (void)
//
// Initialize libsmi. 
// Create default path to MIB directories. 
// Set default error level.
//
// XXX  Does not incorporate "tag" feature of smiInit().
//
void
LIBSMI::init_LIBSMI(void)
{
EMA(-1, "void");
	
	if ( smiInit(NULL) != 0 ) {
		EM0(1, "ERROR: libsmi did not initialize properly.");
	}

	refcnt += 1;

#ifdef LIBSMI_PATH
	path( LIBSMI_PATH );
#endif

#ifdef PHASE2_HOME
	string s = PHASE2_HOME;
	path( s + "/mibs" );
#endif

	error_level = LIBSMI_ERRORLEVEL_DEFAULT;

}  // LIBSMI::init_LIBSMI (void)



// ------------------------------------ -o- 
// LIBSMI::errorLevel (int)
//
// Parameters:
//	level	Error level.
//
// Return:
//	Previous error level.
//      
// Automatically (re-)set error and statistics output related flags
// along with error level.
//
int
LIBSMI::errorLevel(int level)
{
EMA(-1, "int");

	int	flagset,
		previous_error_level = errorLevel();


	smiSetErrorLevel(level);
	error_level = level;

	if (level > 0) {
		if (level == 9) flagset |= SMI_FLAG_STATS;
		flagset |= (SMI_FLAG_ERRORS | flags());

	} else {
		flagset = ~(SMI_FLAG_ERRORS|SMI_FLAG_STATS);
		flagset &= flags();
	}
	
	flags(flagset);


	return previous_error_level;

}  // LIBSMI::errorLevel (int)



// ------------------------------------ -o- 
// LIBSMI::path (string)
//
// Parameters:
//	newpath		New or additional path defining loction of libsmi MIBs.
//      
// Returns:
//	Copy of previous path.
//
// Defining newpath to begin with "=" causes existing path to be replaced.
// Otherwise newpath is appended.
//
//
// NOTE  
// smiSetPath() has internal setting, and checks environment variable SMIPATH.
//
string
LIBSMI::path(string newpath)
{
EMA(-1, "string");

	string oldpath = path();


	if ( newpath[0] == '=' ) {
		newpath.erase(0, 1);

	} else if ( oldpath != "" ) {
		newpath = oldpath + ":" + newpath;
	}

	smiSetPath( newpath.c_str() );


	return oldpath;

}  // LIBSMI::path (string)


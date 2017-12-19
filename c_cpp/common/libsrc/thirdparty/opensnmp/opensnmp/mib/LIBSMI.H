//
// LIBSMI.H
//
// Class wrapper for libsmi operations.
//
// As a rule, class members are named after libsmi API functions with
//	the "smi" prefix removed.  In some cases, "get" and "set" have
//	also been removed, collapsing the API functions into a single 
//	overloaded member.
//
// FIX  Apply const properly and thoroughly.
// FIX  Not thread-safe.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _LIBSMI_H
#define _LIBSMI_H

#include <vector>

#include "csmi"
#include "libsmiOid.H"

using namespace libsmi;


// shouldn't be needed.
// #define LIBSMI_PATH			"/usr/local/lib/tnm/mibs:../mibs"
#define LIBSMI_ERRORLEVEL_DEFAULT	0



// ------------------------------------ -o- 
// class LIBSMI
//
class LIBSMI {

private:
	// -0- 
	// Data.
	//
	int		error_level;

	static int	refcnt;  // Reference count guarding destructor.


	// -0-
	// Functions.
	//
	void init_LIBSMI(void);


protected:
	/*EMPTY*/


public:
	// -0-
	// Constructors.
	//
	LIBSMI(void)		    { init_LIBSMI(); }
	LIBSMI(std::string module_name)  { init_LIBSMI(); loadModule(module_name); }
	~LIBSMI(void)		    { if (--refcnt <= 0) smiExit(); }


	// -0-
	// Wrappers for smi_config(3) libsmi API.
	//
	// errorLevel()	Set/return libsmi error level; set flags accordingly.
	// flags()	Set/return libsmi flags.
	// path()	Set/return path to libsmi MIBs.
	//
	// loadModule()	Load a named MIB module into libsmi cache.
	// isLoaded()	TRUE if named module exists in libsmi cache.
	//
	int	errorLevel(void)	{ return error_level; }
	int	errorLevel(int level);

	int	flags(void)		{ return smiGetFlags(); }
	int	flags(int userflags) { 
			int f = flags(); smiSetFlags(userflags); return f;
	}

	std::string	path(void)		{ 
	           char * s =smiGetPath(); return s ? s : ""; }

	std::string	path(std::string newpath);

	std::string	loadModule(std::string module_name) {
		   char * s = smiLoadModule( module_name.c_str() );
		   return s ? s : ""; 
	}

	bool	isLoaded(std::string module_name) {
			return 
			  (smiIsLoaded(module_name.c_str())) ? true : false;
	}

    /* TBD  Add these...
	void smiSetSeverity(char *pattern, int severity);
	int smiReadConfig(const char *filename, const char *tag);
    */



	// -0-
	// Wrappers for smi_macro(3) libsmi API.
	//
	// TBD  Add these...



	// -0-
	// Wrappers for smi_module(3) libsmi API.
	//
	// getModule()		   Acquire pointer to SmiModule.
	// getFirstModule()	   Acquire pointer to first SmiModule.
	// getNextModule()	   Acquire pointer to next SmiModule
	// getModuleIdentityNode() Acquire pointer to SmiNode for SmiModule.
	//
	SmiModule * getModule(std::string module_name) {
			return smiGetModule(module_name.c_str()); 
	}

	SmiModule * getFirstModule(void) {
			return smiGetFirstModule(); 
	}

	SmiModule * getNextModule(SmiModule * modulePtr) {
			return smiGetNextModule(modulePtr); 
	}

	SmiNode *   getModuleIdentityNode(SmiModule *modulePtr) {
			return smiGetModuleIdentityNode(modulePtr);
	}

    /* TBD  Add these...
	SmiImport *smiGetFirstImport(SmiModule *smiModulePtr);
	SmiImport *smiGetNextImport(SmiImport *smiImportPtr);
	int smiIsImported(SmiModule *smiModulePtr,
			     SmiModule *importedModulePtr, char *importedName);
	SmiRevision *smiGetFirstRevision(SmiModule *smiModulePtr);
	SmiRevision *smiGetNextRevision(SmiRevision *smiRevisionPtr);
    */



	// -0-
	// Wrappers for smi_node(3) libsmi API.
	//
	// getNode()	    Acquire SmiNode pointer.  Usage: [<module>.]<name>.
	// getNodebyOID()   Acquire SmiNode pointer with LONGEST MATCHING OID.
	// getNodeModule()  Convert SmiNode pointer into SmiModule.
	// getFirstNode()   Acquire first SmiNode in SmiModule of given kind.
	// getNextNode()    Acquire next SmiNode of same module as the first.
	//
	SmiNode *   getNode(std::string node_name) { 
			return smiGetNode( NULL, node_name.c_str() );
	}

	SmiNode *   getNodeByOID( std::vector<u_int> &oid ) { 
			libsmiOid lso(oid);
			return smiGetNodeByOID( lso.size(), lso.array() );
	}

	SmiNode *   getNodeByOID( std::string oid ) {
			libsmiOid lso(oid);
			return smiGetNodeByOID( lso.size(), lso.array() );
	}

	SmiNode *   getNodeByOID( libsmiOid &lso ) {
			return smiGetNodeByOID( lso.size(), lso.array() );
	}

	SmiModule * getNodeModule(SmiNode *nodePtr) {
			return smiGetNodeModule(nodePtr);
	}

	SmiNode *   getFirstNode(SmiModule *modulePtr, SmiNodekind kinds) {
			return smiGetFirstNode(modulePtr, kinds);
	}

	SmiNode *   getNextNode(SmiNode *nodePtr, SmiNodekind kinds) {
			return smiGetNextNode(nodePtr, kinds); 
	}

    /* TBD  Add these...
	SmiNode *smiGetNode(SmiModule *smiModulePtr, char *name);
		XXX  LIBSMI does not represent all variations.

	SmiNode *smiGetParentNode(SmiNode *smiNodePtr);
	SmiNode *smiGetRelatedNode(SmiNode *smiNodePtr);
	SmiNode *smiGetFirstChildNode(SmiNode *smiNodePtr);
	SmiNode *smiGetNextChildNode(SmiNode *smiNodePtr);
	SmiType *smiGetNodeType(SmiNode *smiNodePtr);

	SmiElement *smiGetFirstElement(SmiNode *smiNodePtr);
	SmiElement *smiGetNextElement(SmiElement *smiElementPtr);
	SmiNode *smiGetElementNode(SmiElement *smiElementPtr);

	SmiOption *smiGetFirstOption(SmiNode *smiComplianceNodePtr);
	SmiOption *smiGetNextOption(SmiOption *smiOptionPtr);
	SmiNode *smiGetOptionNode(SmiOption *smiOptionPtr);

	SmiRefinement *smiGetFirstRefinement(SmiNode *smiComplianceNodePtr);
	SmiRefinement *smiGetNextRefinement(SmiRefinement *smiRefinementPtr);
	SmiNode *smiGetRefinementNode(SmiRefinement *smiRefinementPtr);
	SmiType *smiGetRefinementType(SmiRefinement *smiRefinementPtr);
	SmiType *smiGetRefinementWriteType(SmiRefinement *smiRefinementPtr);
    */



	// -0-
	// Wrappers for smi_type(3) libsmi API.
	//
	// TBD  Add these...

}; // class LIBSMI

#endif  // _LIBSMI_H


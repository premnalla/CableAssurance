//
// mibModule.C
//
// Class to aggregate module related operations.  
//
// Lookup module characteristics in libsmi
// Manage a list of default module roots
// Prime libsmi cache by loading SNMPv2-MIB.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#include "config.h"
#include "snmp_error.H"
#include "mib.H"

using std::string;


// ------------------------------------ -o- 
// mibModule::init_module (void)
//
// Pre-load and register the minimal set of modules necessary to support
// use of the tree of mibNode's.
//
// ASSUMTIONS
//	. SNMPv2-MIB will import SNMPv2-TC, SNMPv2-SMI, and SNMPv2-CONF.
//	. Common MIB roots may be taken from list of OBJECT-IDENTIFIERS
//		in SNMPv2-SMI.
//
// Additional ("faux") roots added with arbitrary names, prefixed by "_".
//
//
// TBD  Use autoconf to pre-load additional "faux" roots?
//
void
mibModule::init_module(void)
{
EMA(-1, "void");

	static bool	 mibroots_are_initialized = false;
	SmiModule	*sm;

	
	if (mibroots_are_initialized)	return;
	mibroots_are_initialized = true;


	//
	// Configure and load a minimal set of modules.
	//
	addModule("SNMPv2-MIB");


	//
	// Configure set of common MIB roots.
	//
	if ( ! (sm = getModule("SNMPv2-SMI")) ) {
		EM0(1, "ERROR: Could not get module \"SNMPv2-SMI\".");
		return;
	}

        for (   SmiNode *sn = getFirstNode(sm, SMI_NODEKIND_NODE);
                sn;
                sn = getNextNode(sn, SMI_NODEKIND_NODE) )
	{
		if (sn->oid[0] != 1) continue;	    // Skips zeroDotZero.  XXX

		libsmiOid lso(sn->oidlen, sn->oid);
		mibroots.add(sn->name, lso.absolute(true).str_numeric());
	}


	//
	// Add "faux" roots by hand.
	//
	mibroots.add( "_ISO", ".1", true );
			// .iso

	mibroots.add( "_SYSTEM", ".1.3.6.1.2.1.1", true );
			// .iso.org.dod.internet.mgmt.mib-2.system     

}  // mibModule::init_module (void)



// ------------------------------------ -o- 
// mibModule::getModuleField (string, module_field)
//
// Parameters:
//	module_name	Name of MIB module to search.
//	ftype		Type of field to return from SmiModule structure.
//      
// Returns:
//	Copy of the string for the named field.
//
// FIX  This function may move or change.
//
string
mibModule::getModuleField(string module_name, module_field ftype)
{
EMA(-1, "string, module_field");

#define set_sfield(x)	if (module_p->x) sfield = module_p->x

	string		sfield = "";
	SmiModule *	module_p;

	if ( module_name == "" ) module_name = mname;

	if ( module_name != "" && (module_p = getModule(module_name)) ) {
		switch(ftype) {
		case MF_ORGANIZATION:	set_sfield(organization);	break;
		case MF_CONTACTINFO:	set_sfield(contactinfo);	break;
		case MF_DESCRIPTION:	set_sfield(description);	break;
		case MF_REFERENCE:	set_sfield(reference);		break;
		default:
			EMA0(1, "string, module_field", 
				"Should not happen.  FIX throw something?");
		}

		// XXX  How to free module?  WAS: freeModule(module_p);
	}

	return sfield;

#undef set_sfield

}  // mibModule::getModuleField (string, module_field)



// ------------------------------------ -o- 
// mibModule::moduleOid (string)
//
// Return a string representation of the numeric OID path for the given module.
//
string
mibModule::moduleOid(string module_name)
{
EMA(-1, "string");

	SmiModule	*sm;
	SmiNode		*sn;

	if ( module_name == "" )  module_name = mname;

	if ( ! (sm = getModule(module_name)) ) {
		string s = "ERROR: Could not lookup module name \"" 
						+ module_name + "\".";
		EM0(1, s.c_str());

		return "";
	}

	if ( ! (sn = getModuleIdentityNode(sm)) ) {
		string s = "ERROR: Could not lookup module node for \"";
		s += sm->name;
		s += "\".";
		EM0(1, s.c_str());

		// XXX  How to free module?  WAS: freeModule(sm);
		return "";
	}


	libsmiOid lso(sn->oidlen, sn->oid, true);

	// XXX  How to free node?  WAS: freeNode(sn);
	// XXX  How to free module?  WAS: freeModule(sm);

	return lso.str();
	
}  // mibModule::moduleOid (string)



// ------------------------------------ -o- 
// mibModule::addModule (string)
//
// Parameters:
//	module_name	Name of module to load and add to list of mibroots.
//      
// Returns:
//	Copy of string naming the loaded module.
//
//
// NOTES
// 	Because loading one module may import others, each addition
//		requires a scan of the current modules known to libsmi.
// 	The libsmi cache is shared between all threads, new threads will know
//		about the mibroots loaded by other threads when this 
//		function exits.
//
// ASSUMPTIONS
// 	getNode() will always return a node for a module's object.
//
mibModule &
mibModule::addModule(string module_name)
{
EMA(-1, "string");

	if ( (loadModule(module_name) == "") && (module_name != "") ) {
		string s = "ERROR: Failed to load module \"" 
						+ module_name + "\".";
		EM0(1, s.c_str());
		return *this;
	}


	for (	SmiModule *sm = getFirstModule();
		sm;
		sm = getNextModule(sm) )
	{
		libsmiOid	*lso = NULL;
		SmiNode		*sn;

		if ( sm->name && (sn = getNode(sm->name)) )
		{
			lso = new libsmiOid(sn->oidlen, sn->oid);
		}

		mibroots.add( sm->name, 
			(lso) ? lso->absolute(true).str_numeric() : "" );
		delete lso;
	}


	return *this;

}  // mibModule::addModule (string)



// ------------------------------------ -o- 
// mibModule::dumpModule (string)
//
// Output basic information about the given module.
//
// XXX  Could be fancier, and perhaps more useful.
//
void
mibModule::dumpModule(string module_name)
{
EMA(-1, "string");

 if ( module_name == "" )  module_name = mname;
 
   std::cout << "Module:\t\t"     << module_name      << std::endl
    << "OID:\t\t"        << moduleOid(module_name)    << std::endl
    << "Organization:\t" << organization(module_name) << std::endl;
	
}  // mibModule::dumpModule (string)



// ------------------------------------ -o- 
// std::operator<< (ostream &, mibModule::mibRoot &)
//
// Pretty print the list of mibroot entries.
//
std::ostream& 
operator<<(std::ostream& os, mibRoot & mr)
{
  using namespace std;

  mibRoot::mrlist_const_iterator	mrlist_CI;
  int				counter;

  for (	mrlist_CI = mr.mrlist.begin(), counter = 1;
	mrlist_CI != mr.mrlist.end();
	mrlist_CI++ )
    {
      if ( ! (mrlist_CI->second) )  continue;

      string  roid_in_parens = "(" + 
	(mrlist_CI->second)->roid + ")";

      os  << setw(3) << counter++ << " "
	  << setw(25 - (mrlist_CI->second)->rname.size()) 
	  << (mrlist_CI->second)->rname << " " // 
	  << setw(25 - roid_in_parens.size()) 
	  << roid_in_parens << " " << "< ";

      if ((mrlist_CI->second)->iscreated)	os << "created ";
      if ((mrlist_CI->second)->isfaux)	os << "faux ";
      os << ">" << std::endl;
    }

  return os;
}  // std::operator<< (ostream &, mibModule::mibRoot &)


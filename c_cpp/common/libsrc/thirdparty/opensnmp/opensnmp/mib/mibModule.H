//
// mibModule.H
//
// Class to aggregate module related operations.  
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _MIBMODULE_H
#define _MIBMODULE_H

#include "mibRoot.H"

enum module_field { 
	MF_ORGANIZATION, MF_CONTACTINFO, MF_DESCRIPTION, MF_REFERENCE 
};


// ------------------------------------ -o- 
// class mibModule
//
// XXX  Define mibRoot outside mibModule?
//

// std::ostream & std::operator<<(std::ostream & os, mibRoot & mr);

class mibModule : public LIBSMI {

public:

private:
	// -0-
	// Functions.
	//
	void	init_module(void);
	std::string	getModuleField(std::string module_name, 
				       module_field ftype);


protected:
	std::string	mname;		// Module name.
	mibRoot		mibroots;		// FIX -- make it "static"?
						// FIX store it in mibNode?

public:
	// -0-
	// Constructors.
	//
	mibModule(void)  { init_module(); } 
	mibModule(std::string module_name) : mname(module_name) { 
	  init_module(); addModule(module_name); 
	}

	~mibModule(void)  { /*EMPTY*/ }


	// -0-
	// Miscellanous helpers.
	//
	// moduleName()		Return/set module name.
	// moduleOid()		Return module OID path in numeric form.
	//
	// addModule()		Load a MIB module; add to the list of MIB roots.
	//
	// dumpModule()		Dump basic info about a module.
	// dumpModuleRoots()	Dump the list of MIB roots and creation status.
	//
	std::string &  moduleName(void)		{ return mname; };
	mibModule &    moduleName(std::string module_name) {
	  mname = module_name; return *this;
	}
	std::string    moduleOid(std::string module_name = "");

	mibModule & addModule(std::string module_name);

	void	    dumpModule(std::string module_name = "");
	void	    dumpModuleRoots(void)  { std::cout << mibroots; }


	// -0-
	// Pull information from LIBSMI.
	//
	// organization()
	// contactInfo()
	// description()
	// reference()
	//
	std::string  organization(std::string module_name = "") { 
	  return getModuleField(module_name, MF_ORGANIZATION); 
	}

	std::string  contactInfo(std::string module_name = "") { 
	  return getModuleField(module_name, MF_CONTACTINFO); 
	}

	std::string  description(std::string module_name = "") { 
	  return getModuleField(module_name, MF_DESCRIPTION); 
	}

	std::string  reference(std::string module_name = "") { 
	  return getModuleField(module_name, MF_REFERENCE); 
	}

	// FIX -- what else?
	// FIX  Special type (or TC) for lastUpdate and revision 
	//	  (eg, 9901200000Z).
	//
	// std::string	lastUpdated(std::string module_name = "");
	// std::string	revision(std::string module_name = "");

};  // class mibModule


#endif  // _MIBMODULE_H


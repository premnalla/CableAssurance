//
// mibRoot.H
//
// This file is included by class mibModule as a complex declaration.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _MIBROOT_H
#define _MIBROOT_H

#include <string>
#include <iostream>

// ------------------------------------ -o- 
// class mibRoot
//
// Each instance of mibRoot defines a list of (numeric) MIB root OID paths
// and "checklist" operations to manage their creation from the mibNode tree.
//
// Each MIB root entry is stored with:
//	. name 
//	. (numeric) OID path
//	. indication of whether it exists naturally in the libsmi cache
//	. indication of whether it has been created in mibNode tree.
//
//
// NOTE:  Defined in the class mibModule namespace.
//

class mibRoot;

class fields {
		
  friend class mibRoot;
  friend std::ostream& operator<<(std::ostream & os, mibRoot & mr);

protected:
  std::string	rname,	    // MIB root name.
		  roid;	    // MIB root numerical OID path.

  bool	isfaux,	    // TRUE if module's a custom "node root."
		  iscreated;  // TRUE if module OID has been created.

  fields(void)	{ /*EMPTY*/ }
  ~fields(void)	{ /*EMPTY*/ }

};  // class fields


class mibRoot {

#define V  (mrlist_I->second)

  friend std::ostream& operator<<(std::ostream & os, mibRoot & mr);


private:
	// -0-
	// Data.
	//

	typedef std::map<std::string, fields *>::const_iterator	
	                                             mrlist_const_iterator;
	typedef std::map<std::string, fields *>::iterator  mrlist_iterator;

	std::map<std::string, fields *>	mrlist;
	mrlist_iterator		mrlist_I;

	u_int			num_uncreated;


	void setFields(std::string root_name, 
		       std::string root_oid, bool faux_module) {
		//
		// root_oid=="" indicates that module exists in libsmi
		//	cache, but there is no MIB root associated with it.
		//
		fields *f = NULL;

		if ( root_name != "" ) {
			f = new fields;
			f->rname         = root_name;
			f->roid          = root_oid;
			f->isfaux        = faux_module;
			f->iscreated     = (root_oid == "") ? true : false;

		}
		if ( f && (! f->iscreated) )	num_uncreated += 1;
		mrlist[root_name] = f;
	}


protected:
	/*EMPTY*/


public:
	// -0-
	// Constructors.
	//
	mibRoot(std::string root_name = "", std::string root_oid = "",
						bool faux_module = false)
		: num_uncreated(0)
	{ 
		setFields(root_name, root_oid, faux_module); 
	}

	~mibRoot(void) {
		for (	mrlist_const_iterator mrlist_CI = mrlist.begin();
			mrlist_CI != mrlist.end();
			mrlist_CI++ )
		{
			if (mrlist_CI->second) delete mrlist_CI->second;
		}
	}


	// -0-
	// Helpers.
	//
	// add()		Add an entry to list of mibRoots.
	// exists_uncreated()	TRUE if any entry is marked "uncreated."
	// set_created()	Set entry as "created."
	// nextUncreated()	Return OID path of next uncreated root.
	//
	void add(std::string root_name = "", std::string root_oid = "", 
		 bool faux_module = false) {
		if ( (root_name != "") 
				&& (mrlist.find(root_name) == mrlist.end()) ) {
			setFields(root_name, root_oid, faux_module); 
		}
	}

	bool exists_uncreated(void)	{ return (num_uncreated > 0); }

	void set_created(bool ic = true) {
		if ( V && (! V->iscreated) ) { num_uncreated -= 1; }
		V->iscreated = ic;
	}

	std::string nextUncreated(void) {
		if ( ! exists_uncreated() )	return "";

		for (	mrlist_I = mrlist.begin();
			mrlist_I != mrlist.end();
			mrlist_I++ )
		{
			if ( V && (! V->iscreated) )  return V->roid;
		}

		return "";
	}

#undef V

};  // class mibRoot

#endif  // _MIBROOT_H


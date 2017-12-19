//
// mibNode.H
//
// Define data and operations for a node in a tree of MIB nodes.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _MIBNODE_H
#define _MIBNODE_H



enum pathtype {
	MN_PT_NUMERIC, MN_PT_LABEL, MN_PT_LABELANDNUMBER
};
	



// ------------------------------------ -o- 
// class mibNode
//
class mibNode : public mibModule {

private:
	// -0-
	// Data.
	//
	std::string	 nname;		// OID label for this element.
	u_int		 noid;		// OID number for this element.

	mibNode		*root_node;
	mibNode		*parent;

	typedef std::map<std::string, mibNode *>::iterator  
	               children_bylabel_iterator;
	typedef std::map<u_int,  mibNode *>::iterator  
                       children_bynumber_iterator;

	std::map<std::string, mibNode *>		  children_bylabel;
	std::map<u_int,  mibNode *>			  children_bynumber;


	// -0-
	// Functions.
	//
	// FIX  Generalize ui2s() across classes?  This steals from libsmiOid.H.
	//
	void	init_node(void);

	std::string  ui2s(u_int ui)  { 
	  std::string s; UI_TO_S(ui, s); return s; 
	}

	void	instantiate_roots(void) {
			std::string s;
			while ( (s = mibroots.nextUncreated()) != "" ) {
				libsmiOid lso(s);
				(*root_node)[ lso.str_numeric() ];
				mibroots.set_created();
			}
	}
		

protected:
	/*EMPTY*/


public:
	// -0-
	// Constructors.
	//
	mibNode(void) : mibModule()  { init_node(); }
	mibNode(std::string module_name) : mibModule(module_name)  { 
	  init_node(); 
	}
	mibNode(std::string	 node_name, 
		u_int	         node_oid, 
		mibNode	        *node_parent = NULL,
		std::string	 module_name = "" )
		: mibModule(module_name), nname(node_name), 
		  noid(node_oid), parent(node_parent)  { 
	  if ( (mname == "") && parent )  mname = parent->mname;
	  if (parent)  root_node = parent->root_node;
	  init_node();
	}

	~mibNode(void) { /*EMPTY*/ }


	// -0-
	// Miscellaneous helpers.
	//
	// name()	Return/set node name.
	// oid()	Return/set node OID.
	// abspath()	OID path from root in numeric, labelled or
	//		  "label(number)" format.
	// []		Reference a node with labelled or numeric format.
	// =		Return reference, NOT copy.
	// dumpNode()	Basic info about a node.  XXX
	//
	std::string name(void)		    { return nname; }
	mibNode &   name(std::string node_name)  { 
	  nname = node_name; return *this; 
	}

	u_int	    oid(void)		    { return noid; }
	mibNode &   oid(u_int oid_number)   { 
	  noid = oid_number; return *this; 
	}

	std::string  abspath(void)	    { return abspath(MN_PT_NUMERIC); }
	std::string  abspath(pathtype pt);

        mibNode &   operator[] (std::string oid_s);
        mibNode &   operator[] (u_int oid_ui) { return (*this)[ui2s(oid_ui)]; }

	void	    dumpNode(void);


	// -0-
	// Tree structure.
	//
	// addModule()	Load a MIB module and add its root to the MIB tree.
	// dumpTree()	Dump a simplified form of the MIB tree.
	//
	std::string	    addModule(std::string module_name);	

	void	    dumpTree(void)	{ dumpTree(MN_PT_NUMERIC); }
	void	    dumpTree(pathtype pt);


	// -0-
	// Pull information from LIBSMI.
	//
	// status()
	// access()
	// description()
	//
	// FIX -- SmiValue for syntax info; what other fields...
	//
	std::string	status(void);
	std::string	access(void);
	std::string	description(void);

};  // class mibNode

#endif  // _MIBNODE_H


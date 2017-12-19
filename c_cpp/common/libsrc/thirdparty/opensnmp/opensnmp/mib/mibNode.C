//
// mibNode.C
//
// Define data and operations for a node in a tree of MIB nodes.
//
// Provides indexing into MIB tree.
// Lookup of node characteristics in libsmi cache.
// Initialize MIB tree providing skeleton for further additions.
//
//
// TBD  Error handling.
// TBD  Means to create node "aliases."  Overload operator= ?
// TBD  Add semantics to perform relative search "up" the tree...
// TBD  Reference count and threading.
// TBD  mibModule parent class is too heavy, and contains unsharable but
//	  critical data object mibRoot.
// TBD  Need to interface with revised varBind class, asn class,
//	  table class, and some new mibValue class.   mibValue must
//	  handle textual conventions, or offload to yet another class.
// TBD  Need simple means of registering MIB internal functions via
//	  public naming scheme taken from MIB modules.
// TBD  Be able to invoke VACM through a member function that trades on
//	  local context.  Will require interface with USM User class.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//


#include "config.h"
#include "snmp_error.H"
#include "mib.H"

using std::cout;
using std::endl;

// ------------------------------------ -o- 
// mibNode::init_node (void)
//
// Initialization for mibNode -- executes after init_module() in mibModule.
//
// Create root mibNode.
// Create initial skeleton of mibNodes based on contents of mibroots.
//
// ASSUME
//	All OID paths in mibroots exist and are in libsmi cache, by definition.
//
void
mibNode::init_node(void)
{
EMA(-1, "void");

	static bool	root_is_initialized = false;


	if ( root_is_initialized )  return;
	root_is_initialized	= true;


	if ( ISDF(ROOTSBEFORE) ) {
		cout << "MIB roots BEFORE--" << endl; 
		dumpModuleRoots();
	}

	root_node	= this;

	nname		= "ROOT_NODE";
	noid		= 0;
	parent		= NULL;
	mname		= "(nil)";


	instantiate_roots();

	if ( ISDF(ROOTSAFTER) ) {
		cout << "MIB roots AFTER--" << endl; 
		dumpModuleRoots();
	}

}  // mibNode::init_node (void)



// ------------------------------------ -o- 
// mibNode::addModule (string)
//
// Parameters:
//	module_name	Name of module to load.
//      
// Returns:
//	Copy of string naming the loaded module.
//
// Check contents of mibroots and (possibly) add to skeleton of 
// modules roots contained by the tree of mibNode's.
//
string
mibNode::addModule(string module_name)
{
EMA(-1, "string");

	mibModule::addModule(module_name);
	
	instantiate_roots();

	if ( ISDF(ROOTSAFTER) ) {
		cout << "MIB roots AFTER (again)--" << endl; 
		dumpModuleRoots();
	}

	return module_name;

}  // mibNode::addModule (string)



// ------------------------------------ -o- 
// mibNode::abspath (pathtype)
//
// Returns:
//	String representation of OID path.
//	
// Recursively generate absolute (numeric) OID path up to root node.
//
string
mibNode::abspath(pathtype pt)
{
EMA(-1, "pathtype");

	string s = "";

	if (! parent)  return "";


	switch (pt) {
	case MN_PT_NUMERIC:
		s = parent->abspath(pt) + DOT_S + ui2s(noid);
		break;

	case MN_PT_LABEL:
		s = parent->abspath(pt) + DOT_S + nname;
		break;

	case MN_PT_LABELANDNUMBER:
		s = parent->abspath(pt) + " " + nname + "(" + ui2s(noid) + ")";
		break;

	default:
		EM0(1, "ERROR: (internal) Invalid pathtype value.");
	}


	return s;

}  // mibNode::abspath (pathtype)



// ------------------------------------ -o- 
// mibNode::operator[] (string)
//
// Parameters:
//	String representing a numeric or labelled OID path.
//      
// Returns:
//	Reference to indexed node upon success.
//	Reference to last legal node along the path upon failure.
//
//
// To lookup an OID --
//
//	1 Traverse relative (absolute) path from this node (*root_node)
//		until some node is found to be uncreated or last node is
//		reached.
//	  Return last node of OID path if whole path is traversed,
//		else set last existing node to current node.
//		
//	2 Create absolute OID path from root, including the current node.
//	  Validate the path.  
//	  Return current node if path is not valid.
//
//	3 Continue to walk through OID path, creating each node and
//		defining minimal characteristics for each.
//	  Return last node of path.
//
// ASSUME
//	getNodeByOID will not fail if validate() is successful.
//
mibNode &
mibNode::operator[](string oid_s)
{
EMA(-1, "string");

	//
	// Special cases return root_node.
	//
	if ( (oid_s == "") || (oid_s == ".") )  return *root_node;


	//
	// Declarations.
	//
	libsmiOid	 oidpath(oid_s),
			*oidpath_abs;

	mibNode		*curnode = oidpath.absolute() ? root_node : this;

	SmiNode		*sn	 = NULL;
	SmiModule	*sm	 = NULL;

	int		 opsize,
			 counter;

	bool		 missedit,
			 uselabel = 
				(oidpath.type() == LSO_ET_LABEL) ? true : false;

	children_bynumber_iterator	CBNI;
	children_bylabel_iterator	CBLI;



	//
	// Traverse as much of OID path as exists in mibTree.
	// Return the last node of the path if it exists.
	//
	if ( ISDF(ARRAYOP) && ! uselabel && oidpath.absolute() ) {	
		// FIX  oidpath.validate when uselabel segfaults in lso
		//	  deconstructor freeing array (oid_c_uia)?!
		if ( oidpath.validate() ) sn = getNodeByOID(oidpath);
		EM1(1, oidpath.str());
		if (sn) { EM1(1, sn->name); }
	}

	for (	counter = 0, missedit = false, opsize = oidpath.maxsize();
		counter < opsize;
		++counter, curnode = uselabel ? CBLI->second : CBNI->second )
	{
		oidpath.subpath(0, counter+1);

		if (uselabel) {
			CBLI = curnode->children_bylabel
				.find( oidpath	.type(LSO_ET_LABEL)
						.label(LIBSMIOID_END) );
		} else {
			CBNI = curnode->children_bynumber
				.find( oidpath.oid(LIBSMIOID_END) );
		}

		if ( (uselabel 
				&& (CBLI == curnode->children_bylabel.end()))
			|| (!uselabel 
				&& (CBNI == curnode->children_bynumber.end())) )
		{
			missedit = true;
		}

		if (missedit) {
			if ( ISDF(ARRAYOP) ) {
				EM4(1, oidpath.size(), counter, oidpath.str(), 
					oidpath.oid(LIBSMIOID_END) );
			}

			break;
		}
	}  // for

	if ( ISDF(ARRAYOP) ) {
		EM4(1, counter, oidpath.maxsize(), curnode, root_node );
	}

	if ( (counter == opsize)
		&& ( (uselabel 
			&& (CBLI != curnode->children_bylabel.end()))
		    || (!uselabel 
			&& (CBNI != curnode->children_bynumber.end())) ) )
	{
		return *curnode;
	}



	//
	// Generate absolute OID path, and check for validity.
	//
	if ( oidpath.absolute() ) {
		oidpath_abs = new libsmiOid( oidpath.subpath() );

	} else {
		oidpath_abs = new libsmiOid( curnode->abspath() );
		counter += (oidpath_abs->size() + 1);
		(*oidpath_abs) += oidpath.subpath().str_numeric();

		if ( ISDF(ARRAYOP) ) { 
			EM2(1, oidpath.str(), oidpath_abs->str()); 
		}
	}

	if ( ! oidpath_abs->validate() ) {
		EM0(1, "ERROR: "
			"Target OID path does not exist in libsmi cache.");
		return *curnode;	// XXX -- Alternatives?
	}

	if ( ISDF(ARRAYOP) ) {
		EM3(1, counter, oidpath_abs->str(), oidpath_abs->str_label());
	}



	//
	// Add to mibNode tree along OID path.
	//
	for (	opsize = oidpath_abs->maxsize(), oidpath_abs->size(counter);
		counter < opsize;
		++counter )
	{
		oidpath_abs->sizeinc();

		if ( ISDF(ARRAYOP) ) {
			EM4(1, oidpath_abs->str_numeric(), oidpath_abs->size(), 
			    oidpath_abs->oid(LIBSMIOID_END), 
				oidpath_abs->type(LSO_ET_LABEL)
				    .label(LIBSMIOID_END));
			EM2(1, counter, opsize);
		}

		oidpath_abs->array();
		sn = getNodeByOID( *oidpath_abs );

                if ( ISDF(ARRAYOP) ) {
			EM4(1, sn->name, sn->oidlen, 
				sn->oid[sn->oidlen-1], curnode);
		}

		if ( ! (sm = getNodeModule(sn)) ) {
			string s = "ERROR: Could not get module for node \"";
			s += sn->name;
			s += "\".";
			EM0(1, s.c_str());

			continue;
		}

		curnode = curnode->children_bynumber
					[ oidpath_abs->oid(LIBSMIOID_END) ]
			= curnode->children_bylabel
					[ oidpath_abs->type(LSO_ET_LABEL)
							.label(LIBSMIOID_END) ]
				= new mibNode(	sn->name,
						sn->oid[ sn->oidlen-1 ],
						curnode,
						sm->name );
		// XXX  How to free node?  WAS: freeNode(sn);
	}  // for


	return *curnode;

}  // mibNode::operator[] (string)



// ------------------------------------ -o- 
// mibNode::dumpNode (void)
//
// Output basic info about the current node.
//
// XXX  Could be fancier, and possibly more useful.
//
void
mibNode::dumpNode(void)
{
EMA(-1, "void");

	cout	<< "Node:\t\t"		<< nname		<< endl
		<< "OID:\t\t"		<< abspath()		<< endl
		<< "Access:\t\t"	<< access()		<< endl
		<< "Status:\t\t"	<< status()		<< endl
		<< "Description:\t"	<< description()	<< endl
		;
	
}  // mibNode::dumpNode (void)



// ------------------------------------ -o- 
// mibNode::dumpTree (pathtype)
//
// Dump all longest OID paths in MIB tree.
//
// XXX  This could be fancier and more useful...
//
void
mibNode::dumpTree(pathtype pt)
{
EMA(-1, "pathtype");

	children_bynumber_iterator	CNUM_I;

	if ( children_bynumber.empty() ) {
		cout << abspath(pt) << endl;
		return;
	}

	for (	CNUM_I = this->children_bynumber.begin();
		CNUM_I != this->children_bynumber.end();
		CNUM_I++ )  
	{ 
		CNUM_I->second->dumpTree(pt); 
	}
	
}  // mibNode::dumpTree (pathtype)



// ------------------------------------ -o- 
// mibNode::status (void)
//
// Returns:
//	String representing status type.
//
// FIX	Better to put all this in traditoinal strcutre?  Steal from
//	  libsmi's tools/dump-smi.c?  Standardize on this?  Put it in smi.h??
//
string
mibNode::status(void)
{
EMA(-1, "void");

	SmiNode *	sn;
	string		sstatus = "";

	if ( (nname != "") && (sn = this->getNode(nname)) )
	{
		switch (sn->status) {
    		case SMI_STATUS_CURRENT:
			sstatus = "current";		break;
    		case SMI_STATUS_DEPRECATED:
			sstatus = "depreciated";	break;
    		case SMI_STATUS_MANDATORY:
			sstatus = "mandatory";		break;
    		case SMI_STATUS_OPTIONAL:
			sstatus = "optional";		break;
    		case SMI_STATUS_OBSOLETE:
			sstatus = "obsolete";		break;
		default:
			sstatus = "(unknown)";
		}

		// XXX  How to free node?  WAS: this->freeNode(sn);
	}

	return sstatus;

}  // mibNode::status (void)




// ------------------------------------ -o- 
// mibNode::access (void)
//
// Returns:
//	String representing access type.
//
// FIX	Better to put all this in traditoinal strcutre?  Steal from
//	  libsmi's tools/dump-smi.c?  Standardize on this?  Put it in smi.h??
//
string
mibNode::access(void)
{
EMA(-1, "void");

	SmiNode *	sn;
	string		saccess = "";

	if ( (nname != "") && (sn = this->getNode(nname)) )
	{
		switch (sn->access) {
		case SMI_ACCESS_NOT_ACCESSIBLE:
			saccess = "not-accessible";		break;
		case SMI_ACCESS_NOTIFY:
			saccess = "accessible-for-notify";	break;
		case SMI_ACCESS_READ_ONLY:
			saccess = "read-only";			break;
		case SMI_ACCESS_READ_WRITE:
			saccess = "read-write";			break;
		default:
			saccess = "(unknown)";
		}

		// XXX  How to free node?  WAS: this->freeNode(sn);
	}

	return saccess;

}  // mibNode::access (void)



// ------------------------------------ -o- 
// mibNode::description (void)
//
// Returns:
//	Copy of the string for the description field.
//
// FIX	Better to put all this in traditoinal strcutre?  Steal from
//	  libsmi's tools/dump-smi.c?  Standardize on this?  Put it in smi.h??
//
string
mibNode::description(void)
{
EMA(-1, "void");

	SmiNode *	sn;
	string		sfield = "";


	if ( (nname != "") && (sn = this->getNode(nname)) )
	{
		if (sn->description) sfield = sn->description;
		// XXX  How to free node?  WAS: this->freeNode(sn);
	}

	return sfield;

}  // mibNode::description (void)


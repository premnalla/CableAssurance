//
// libsmiOid.C
//
// Store OID paths and convert between different represenations.
//
// Allows for absolute and relative paths of numeric or labelled OIDs.
// Allows designation of subpaths.
// Allows setting of index into OID path, and walking the path by label
//	or by number.
// References to alternative internal representations are consistent
//	and may be used in place of (re)copied objects.
//
// Will perform comparison of a given path against libsmi cache to
//	determine whether it exists under the current set of loaded MIB modules.
// Will convert numeric paths to labelled paths and vice versa.
//
//
// NOTE
//	Conversion functions will report an error if source vector
//	  is empty.  User must run validate() by hand to force the conversion.
//
//	str*() output functions discard non-permanent subpath changes
//	  after the first use.  vect_*()/array() functions do not alter
//	  subpath state.  Thus, str*() is a poor-person's "substring"
//	  function and the others may be used repeatedly in place of
//	  copied vectors/arrays.
//
//	subpath_end never points beyond the last {max,}size'd element.
//	  length_max is real length of the vector.
//	
// ASSUMES
//	SmiSubid == u_int.				XXX
//	length_max <= (LIBSMIOID_END == 2^31 - 2).	XXX
//
//
// FIX  Handle errors with exceptions; verbose flag.
// FIX  Check length against LIBSMIOID_OIDLENGTH_MAX.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#include "config.h"
#include "snmp_error.H"
#include "mib.H"



// ------------------------------------ -o- 
// libsmiOid::init_libsmiOid (void)
//
void
libsmiOid::init_libsmiOid(void)
{
EMA(-1, "void");
	
	is_absolute_path	 = false;
	is_permanent_subpath	 = false;

	element_type		 = LSO_ET_DEFAULT;

	oid_c_uia		 = NULL;
	
}  // libsmiOid::init_libsmiOid (void)



// ------------------------------------ -o- 
// libsmiOid::resetState (int, int)
//
// Parameters:
//	begin	Beginning index for subpath.
//	end	Index just beyond ending index for subpath.
//
//
// (Re)set subpath indeces, and (re)initialize internal representation
//	of OID path.
//
// index_current is reset when subpath_begin changes.
//
// Internal "copies" of the label and numeric OID path vectors need to
//	be emptied out whenever any boundary index or the lengthchanges.
//
// XXX  Could optimize calling environment to skip when input arguments
//	  match current state values.
//
void
libsmiOid::resetState(int begin, int end)
{
EMA(-1, "int, int");
		
	subpath_begin	 = begin;
	subpath_end	 = (end == LIBSMIOID_END) ? length_max : end;
	index_current	 = subpath_begin;
	length		 = (subpath_end - subpath_begin);

	subpath_state	 = LSO_SS_UNSET;
	if ( (subpath_begin != 0) || (subpath_end != length_max) ) {
		subpath_state = LSO_SS_SET;
	}

	if ( element_type == LSO_ET_DEFAULT ) {
		if ( ! oid_sv.empty() )		element_type = LSO_ET_LABEL;
		else if ( ! oid_uiv.empty() )	element_type = LSO_ET_NUMERIC;
		else {
			EM0(1, "ERROR: Cannot determine "
				"element type after initialization.");
		}
	}

	clear();

}  // libsmiOid::resetState (int, int)



// ------------------------------------ -o- 
// libsmiOid::clear (void)
//
// Zero internal "copies" of the label and numeric OID path vectors.
//
// XXX  When OIDs represent secrets, they need to be zeroed out first.
//
void
libsmiOid::clear(void)
{
EMA(-1, "void");

	oid_c_label.erase();
	oid_c_numeric.erase();

	oid_c_uiv.clear();
	oid_c_sv.clear();

	delete [] oid_c_uia;
	oid_c_uia = NULL;

}  // libsmiOid::clear (void)



// ------------------------------------ -o- 
// libsmiOid::checkSubpathState (void)
//
// Filter for output functions: one time use of subpath settings 
//	unless the setting is "permament."  Permanent settings may be
//	reset by another call to subpath().
//
void
libsmiOid::checkSubpathState(void)
{
EMA(-1, "void");

	switch (subpath_state) {
	case LSO_SS_UNSET:	/*EMPTY*/			break;
	case LSO_SS_SET:	subpath_state = LSO_SS_USED;	break;
	case LSO_SS_USED:
		if ( is_permanent_subpath )  return;
		resetState();
		break;
	default:
		EM0(1, "ERROR: (internal) Invalid subpath state value.");
	}

}  // libsmiOid::checkSubpathState (void)



// ------------------------------------ -o- 
// libsmiOid::libsmiOid (string)
//
// Parameters:
//	oid	String representation of a labelled or numeric OID.
//      
// Convert oid to internal represenation of vector<string> or vector<u_int>.
//
libsmiOid::libsmiOid(string oid)
{
EMA(-1, "string");
	
	string::size_type	sentinel = 0;
	string			numeric_chars;


	//
	// Setup.  Guess at element type.
	//
	init_libsmiOid();

	numeric_chars  = "0123456789";
	numeric_chars += DOT_S;

	if ( oid.find_first_not_of(numeric_chars) != string::npos ) {
		element_type = LSO_ET_LABEL;
	}


	//
	// Conversion.
	//
	if ( oid[0] == DOT_C ) {
		is_absolute_path = true;

	} else {
		oid.insert( oid.begin(), DOT_C );
	}

	do {
		oid.erase(0, sentinel+1);

		if ( oid[0] == DOT_C ) {
			EM0(1, "ERROR: Skipping doubled sentinel...");
			continue;	
		}

		if (element_type == LSO_ET_LABEL) {
			oid_sv.push_back
			    ( oid.substr(0, oid.find(DOT_S)) );
		} else {
			oid_uiv.push_back( atoi(oid.c_str()) );
		}

	} while ((sentinel = oid.find(DOT_S)) != string::npos);


	length = length_max = 
		(element_type == LSO_ET_LABEL) ? oid_sv.size() : oid_uiv.size();

	resetState();

}  // libsmiOid::libsmiOid (string)



// ------------------------------------ -o- 
// libsmiOid::libsmiOid (vector<string> &, bool)
//
// Parameters:
//	oid		OID path of label values.
//	absolute_path	TRUE if path is from the root of MIB tree.
//      
// Copy OID path to internal representation of vector<string>.
//
// NOTE  Vectors mixed with numeric values are not detected here.
//
libsmiOid::libsmiOid(std::vector<string> &oid, bool absolute_path)
{
EMA(-1, "std::vector<string> &, bool");

	init_libsmiOid();
	is_absolute_path = absolute_path;

	oid_sv = oid;

	length = length_max = oid.size();
	resetState();

}  // libsmiOid::libsmiOid (vector<string> &, bool)



// ------------------------------------ -o- 
// libsmiOid::libsmiOid (std::vector<u_int> &, bool)
//
// Parameters:
//	oid		OID path of numeric values.
//	absolute_path	TRUE if OID path begins at root of MIB tree.
//      
// Copy OID path to internal representation of vector<u_int>.
//
libsmiOid::libsmiOid(std::vector<u_int> &oid, bool absolute_path)
{
EMA(-1, "std::vector<u_int> &, bool");

	init_libsmiOid();
	is_absolute_path = absolute_path;

	oid_uiv = oid;

	length = length_max = oid.size();
	resetState();

}  // libsmiOid::libsmiOid (std::vector<u_int> &, bool)



// ------------------------------------ -o- 
// libsmiOid::libsmiOid (int, u_int[], bool)
//
// Parameters:
//	array_length	Length of OID path.
//	array		Numeric OID path.
//	absolute_path	TRUE if path begins at root of MIB tree.
// 
// Converts array to default internal representation of vector<u_int>.
//
libsmiOid::libsmiOid(int array_length, u_int array[], bool absolute_path)
{
EMA(-1, "int, u_int[], bool");

	init_libsmiOid();
	is_absolute_path = absolute_path;


	for (	int i = 0;
		i < array_length;
		i++ )
	{
		oid_uiv.push_back(array[i]);
	}

	length = length_max = array_length;
	resetState();
	
}  // libsmiOid::libsmiOid (int, u_int[], bool)



// ------------------------------------ -o- 
// libsmiOid::libsmiOid (libsmiOid &)
//
// Parameters:
//	Reference to libsmiOid object to copy from.
//      
// Returns:
//	Copy of input object and relevant state (size, subpath, absolute).
//
libsmiOid::libsmiOid(libsmiOid &lso)
{
EMA(-1, "libsmiOid &");

	init_libsmiOid();
	is_absolute_path = lso.absolute();

	length = length_max = lso.size();

	for (	u_int i = 0;
		i < length;
		i++ )
	{
		oid_uiv.push_back(lso.array()[i]);
	}

	resetState();
	
}  // libsmiOid::libsmiOid (libsmiOid &)



// ------------------------------------ -o- 
// libsmiOid::size (int)
//
// Parameters:
//	newsize		Suggested new length for OID path.
//      
// Returns:
//	Reference to current object. 
//
//
// Set output length of OID path.
// LIBSMIOID_END sets size to maximum length for the current subpath.
//
libsmiOid &
libsmiOid::size(u_int newsize)
{
EMA(-1, "int");

	if ( newsize == LIBSMIOID_END ) {
		newsize = (subpath_end - subpath_begin);
	}

	if ( newsize < 0 ) {
		EM0(1, "ERROR: newsize less than zero.");
		length = 0;

	} else if ( newsize > (subpath_end - subpath_begin) ) {
		EM0(1, "ERROR: newsize greater than OID (subpath) length.");
		EM3(1, newsize, subpath_end, subpath_begin);
		length = (subpath_end - subpath_begin);

	} else {
		length = newsize;
	}

	clear();


	return *this;

}  // libsmiOid::size (int)



// ------------------------------------ -o- 
// libsmiOid::index (int)
//
// Parameters:
//	newindex	Suggested new index into OID path (subpath).
//      
// Returns:
//	Reference to current object.
//
//
// First index is 0, last index is (length_max-1).
//
// LIBSMIOID_END sets index to end of OID (subpath).
//	XXX  But this symbolic value should indicate one *beyond* the end...
//
libsmiOid &
libsmiOid::index(u_int newindex)
{
EMA(-1, "int");

	if ( newindex == LIBSMIOID_END ) {
		newindex = subpath_begin + length - 1;
	}

	if ( newindex < subpath_begin ) {
		EM0(1, "ERROR: newindex less than subpath beginning.");
		index_current = subpath_begin;

	} else if ( newindex > (subpath_begin + length) ) {
		EM0(1, "ERROR: newindex greater "
				"than length beyond subpath beginning.");
		index_current = subpath_begin + length;

	} else {
		index_current = newindex;
	}
	

	return *this;

}  // libsmiOid::index (int)



// ------------------------------------ -o- 
// libsmiOid::subpath (int, int, bool)
//
// Parameters:
//	begin			Beginning index of subpath.
//	end			Index defining end of subpath.
//	permanent_subpath	TRUE if subpath remains in effect after a 
//				  single output conversion.
// Returns:
//	Reference to current object.
//
// Set boundaries for a subpath within the full OID path.
// end is set to the index *beyond* the last valid element.
// Indices are reset to extreme values after first use to output
//	the OID path unless permanent_subpath is TRUE.
//
// XXX  Inputs should be const.
//
libsmiOid &
libsmiOid::subpath(u_int begin, u_int end, bool permanent_subpath)
{
EMA(-1, "int, int, bool");

	if ( begin < 0 ) {
		EM0(1, "ERROR: Beginning index less than zero.");
		begin = 0;

	} else if ( begin > length_max ) {
		EM0(1, "ERROR: "
			"Beginning index greater than maximum OID length.");
		begin = length_max;
	}


	if ( end == LIBSMIOID_END )  end = length_max;

	if ( end < 0 ) {
		EM0(1, "ERROR: Ending index less than zero.");
		end = 0;

	} else if ( end > length_max ) {
		EM0(1, "ERROR: Ending index greater than maximum OID length.");
		end = length_max;
	}

	if ( begin > end ) {
		EM0(1, "ERROR: Beginning index greater than ending index.");
		begin = end;
	}


	is_permanent_subpath = permanent_subpath;

	resetState(begin, end);

	
	return *this;

}  // libsmiOid::subpath (int, int, bool)



// ------------------------------------ -o- 
// libsmiOid::validate (void)
//
// Returns:
//	TRUE if OID path is valid in current libsmi cache.
//
//
// Lookup OID path in libsmi cache.  During lookup, create counterpart
//	to labelled (or numeric) vector.
// Report error and remove partial counterpart vector upon failure.
//
// NOTE  
//	Automatically resets subpath setting to maximum size..
//	Trades on the fact that LIBSMI is not threaded.  Declared object
//		does not alter previously populated libsmi cache.
//	Equality comparison should disambiguate cases if/when libsmi cache
//		holds two nodes from diverse modules containing identical 
//		labels.
//
// XXX  Better to return *this?
//
bool
libsmiOid::validate(void)
{
EMA(-1, "void");
	
	LIBSMI	 	     ls;
	SmiNode		    *sn		= NULL;
	int	 	     vsize	= maxsize();
	lso_element_type     et_error	= LSO_ET_DEFAULT;  // Overloaded.  XXX


	if ( ! is_absolute_path ) {
		EM0(1, "ERROR: Only absolute OID paths may be validated.");
		return false;
	}

	//
	// Already validated -- ASSUME it's right... XXX
	//
	if ( ! (oid_sv.empty() || oid_uiv.empty()) ) {
		return true;
	}


	//
	// Validate OID path and create counterpart path.
	//
	subpath();

	if ( ! oid_sv.empty() ) {		// Validate labelled vector.
		size(0);

		while ( vsize-- ) {
			sizeinc().index(LIBSMIOID_END);
			type(LSO_ET_LABEL);

			if ( (sn = ls.getNode( label() )) != NULL ) {
				libsmiOid lso( sn->oidlen, sn->oid );
				oid_uiv.push_back( sn->oid[sn->oidlen-1] );
				// XXX  How to free node?  WAS: ls.freeNode(sn);
				sn = NULL;

				if ( *this != lso ) {
					et_error = LSO_ET_LABEL;
					break;
				}

			} else {
				et_error = LSO_ET_LABEL;
				break;
			}
		}

	} else {				// Validate numeric vector.
		size(0);

		while ( vsize-- ) {
			sizeinc();

			if ( ((sn = ls.getNodeByOID(*this)) != NULL) 
					&& (sn->oidlen == size()) )
			{
				oid_sv.push_back
					( (sn->name) ? sn->name : "(nil)" );
				// XXX  How to free node?  WAS: ls.freeNode(sn);
				sn = NULL;

			} else {
				et_error = LSO_ET_NUMERIC;
				break;
			}
		}
	}  // if


	//
	// Cleanup.  Handle errors.
	//
	if ( et_error == LSO_ET_LABEL ) {
		EM0(1, "ERROR: Could not validate labelled path.");
		oid_uiv.clear();

	} else if ( et_error == LSO_ET_NUMERIC ) {
		EM0(1, "ERROR: Could not validate numeric path.");
		oid_sv.clear();
	}

	size(LIBSMIOID_END);


	return (et_error == LSO_ET_DEFAULT) ? true : false;
	
}  // libsmiOid::validate (void)



// ------------------------------------ -o- 
// libsmiOid::type (lso_element_type)
//
// Parameters:
//	et	Suggested element type for this OID path object.
//      
// Returns:
//	Reference to current object.
//
libsmiOid &
libsmiOid::type(lso_element_type et)
{
EMA(-1, "lso_element_type");

	switch (et) {
	case LSO_ET_LABEL:	element_type = LSO_ET_LABEL;	break;
	case LSO_ET_NUMERIC:	element_type = LSO_ET_NUMERIC;	break;
	default:
		EM0(1, "ERROR: Unrecognized element type.");
	}
	
	return *this;

}  // libsmiOid::type (lso_element_type)



// ------------------------------------ -o- 
// libsmiOid::str (lso_element_type)
//
// Parameters:
//	et	Suggested element type for ouput string.
//
// Returns:
//	Reference to string representation of labelled or numeric OID (sub)path.
//
//
// Convert OID (sub)path to a labelled or numeric string.
//
// XXX  Automatically validate if the requested vector is empty?
//
string &
libsmiOid::str(lso_element_type et)
{
EMA(-1, "lso_element_type");

	string oid_c;


	//
	// Setup.  Return pre-computed string paths.
	//
	if (et != LSO_ET_DEFAULT)  type(et);

	checkSubpathState();

	if ( element_type == LSO_ET_LABEL ) {
		if ( oid_c_label != "" )	return oid_c_label;

		if ( oid_sv.empty() ) {
			EM0(1, "ERROR: Labelled vector is empty.");
			return oid_c_label;
		}

	} else {
		if ( oid_c_numeric != "" )	return oid_c_numeric;

		if ( oid_uiv.empty() ) {
			EM0(1, "ERROR: Numeric vector is empty.");
			return oid_c_numeric;
		}
	}


	//
	// Compute string path.
	//
	for (	u_int i = subpath_begin; 
		i < (subpath_begin + length); 
		i++ ) 
	{
		oid_c += DOT_S;

		oid_c += (element_type == LSO_ET_LABEL)
				? oid_sv[i]
				: ui2s(oid_uiv[i]) ;
	}

	if ( (! is_absolute_path) || (subpath_begin != 0) ) {
		oid_c.erase(0, 1);
	}

	
	//
	// Return the path with reference to proper element type.
	//
	if ( element_type == LSO_ET_LABEL )	oid_c_label   = oid_c;
	else					oid_c_numeric = oid_c;

	return (element_type == LSO_ET_LABEL) ? oid_c_label : oid_c_numeric ;

}  // libsmiOid::str (lso_element_type)



// ------------------------------------ -o- 
// libsmiOid::vect_label (void)
//
// Returns:
//	Reference to copy of labelled OID (sub)path.
//
std::vector<string> &
libsmiOid::vect_label(void)
{
EMA(-1, "void");

	if ( ! oid_c_sv.empty() )  return oid_c_sv;

	if ( oid_sv.empty() ) {
		EM0(1, "ERROR: Labelled vector is empty.");
		return oid_c_sv;
	}


	for (	u_int i = subpath_begin; 
		i < (subpath_begin + length); 
		i++ )		{ oid_c_sv.push_back( oid_sv[i] ); }

	
	return oid_c_sv;

}  // libsmiOid::vect_label (void)



// ------------------------------------ -o- 
// libsmiOid::vect_numeric (void)
//
// Returns:
//	Reference to copy of numeric OID (sub)path.
//
std::vector<u_int> &
libsmiOid::vect_numeric(void)
{
EMA(-1, "void");

	if ( ! oid_c_uiv.empty() )  return oid_c_uiv;

	if ( oid_uiv.empty() ) {
		EM0(1, "ERROR: Numeric vector is empty.");
		return oid_c_uiv;
	}


	for (	u_int i = subpath_begin; 
		i < (subpath_begin + length); 
		i++ )		{ oid_c_uiv.push_back( oid_uiv[i] ); }

	
	return oid_c_uiv;

}  // libsmiOid::vect_numeric (void)



// ------------------------------------ -o- 
// libsmiOid::array (void)
//
// Returns:
//	Reference to copy of int[] array representing OID (sub)path.
//	  -OR-
//	NULL if oid_uiv is empty.
//
// NOTE
// Use size() to get the length.
// Use validate() to convert labelled OID path to numeric.
//
u_int *
libsmiOid::array(void)
{
EMA(-1, "void");

	if (oid_c_uia)  return oid_c_uia;

	if ( oid_uiv.empty() ) {
		EM0(1, "ERROR: Numeric vector is empty.");
		return NULL;
	}

	oid_c_uia = new u_int[oid_uiv.size()];

	for (	u_int i = subpath_begin; 
		i < (subpath_begin + length); 
		i++ )		{ oid_c_uia[i] = oid_uiv[i]; }


	if ( ISDF(DUMPARRAY) ) {
		std::cout << "array: ";
		if ( oid_c_uia ) {
			for (	u_int i = subpath_begin; 
				i < (subpath_begin + length); 
				i++ )	{ std::cout << " " << oid_c_uia[i]; }

		} else {
			std::cout << "(not defined)";
		}
		std::cout << std::endl;
	}


	return oid_c_uia;

}  // libsmiOid::array (void)



// ------------------------------------ -o- 
// libsmiOid::label (int)
//
// Parameters:
//	path_index	Index into OID path vector.
//      
// Returns:
//	Copy of labelled or numeric value at path_index. 
//
string
libsmiOid::label(int path_index)
{
EMA(-1, "int");

	if ( (element_type == LSO_ET_LABEL) && oid_sv.empty() ) {
		EM0(1, "ERROR: Labelled vector is empty.");
		return "";
		// FIX -- throw something...

	} else if ( (element_type == LSO_ET_NUMERIC) && oid_uiv.empty() ) {
		EM0(1, "ERROR: Numeric vector is empty.");
		return "";
		// FIX -- throw something...
	}


	index(path_index);
	
	return (element_type == LSO_ET_LABEL) 
			? oid_sv[index_current]
			: ui2s( oid_uiv[index_current] ) ;

}  // libsmiOid::label (int)



// ------------------------------------ -o- 
// libsmiOid::oid (int)
//
// Parameters:
//	path_index	Index into OID path vector.
//      
// Returns:
//	Copy of numeric value at path_index. 
//
u_int
libsmiOid::oid(int path_index)
{
EMA(-1, "int");

	if ( oid_uiv.empty() ) {
		EM0(1, "ERROR: Numeric vector is empty.");
		return 0;
		// FIX -- MUST throw something...
	}

	index(path_index);
	
	return oid_uiv[index_current];

}  // libsmiOid::oid (int)



// ------------------------------------ -o- 
// libsmiOid::operator+= (libsmiOid &)
//
// Parameters:
//	Reference to libsmiOid to append to current object.
//      
// Returns:
//	Reference to re-created libsmiOid.
//
// NOTE
//	New object inherits absolute characteristic from previous.
//	Disregards the subpath settings in the added lso...  XXX
//
// FIX  Make it possible to += a labelled only object...
//
libsmiOid &
libsmiOid::operator+=(libsmiOid &lso)
{
EMA(-1, "libsmiOid &");

	bool	absolute_path = is_absolute_path;

	
	if ( lso.absolute() ) {
		EM0(1, "ERROR: Only non-absolute OID paths may be appended.");
		return *this;

	} else if ( lso.vect_numeric().empty() ) {
		EM0(1, "ERROR: Numeric path of right-hand object is empty.");
		return *this;
	}

	
	//
	// Re-initialize.  
	// FIX  Generalize this for other operator*'s.
	//
	oid_sv.clear();
	if (oid_c_uia)	delete [] oid_c_uia;

	init_libsmiOid();
	is_absolute_path = absolute_path;


	//
	// Append right-hand object to left-hand object.
	//
	for (	oid_numeric_const_iterator CI = lso.vect_numeric().begin();
		CI != lso.vect_numeric().end();
		CI++ )
	{
		oid_uiv.push_back( *CI );
	}

	length = length_max = oid_uiv.size();
	resetState();

	
	return *this;

}  // libsmiOid::operator+= (libsmiOid &)


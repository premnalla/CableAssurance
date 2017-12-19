//
// libsmiOid.H
//
// Store OID paths and convert between different represenations.
//
//
// XXX  Destructor does not zero out OID paths which represent secrets.
// XXX  Confusing that subpath changes with str*() and not with other
//	  output functions?
//
// TBD  Overload operator>>/operator<<.
// TBD  Default constructor + new add() function to allow full definition
//	  post-declaration, and to change contents.  operator=.
//	Improve existing operator+=.
// TBD	Inline changes to OID path, length, or individual element operations.
//
// FIX  Throwaway LIBSMIOID_END in favor of maxsize()?
// FIX  Make comparisons handle cases where oid_uiv is not defined.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _LIBSMIOID_H
#define _LIBSMIOID_H


using namespace libsmi;



// ------------------------------------ -o- 
// Constants, type definitions, macros.
//
#define LIBSMIOID_END			((2^31)-2)	// Large int.  XXX

#define LIBSMIOID_OIDLENGTH_MAX		255

#define DOT_C		'.'
#define DOT_S		"."

#define UPDOT_C		'+'
#define UPDOT_S		"+"


enum lso_element_type {	
	LSO_ET_LABEL, LSO_ET_NUMERIC, LSO_ET_DEFAULT
};

enum lso_subpath_state {
	LSO_SS_UNSET, LSO_SS_SET, LSO_SS_USED
};


#define UI_TO_S(ui, s)	for ( u_int ui_tmp = ui; ui_tmp; ui_tmp /= 10 )   \
				s.insert( s.begin(), (ui_tmp%10) + '0' );


// ------------------------------------ -o- 
// class libsmiOid
//
class libsmiOid {

private:
	// -0-
	// Data.
	//
	// Input to the constructors is translated into oid_sv or oid_uiv.
	// Output is copied into oid_c_* and retured by reference.
	//

	std::vector<std::string> oid_sv;	// Original OID label vector.
	std::vector<u_int>	 oid_uiv;	// Original OID numeric vector.

	std::string		 oid_c_label,	// Labelled OID path.
				 oid_c_numeric;	// Numeric OID path.
	std::vector<std::string> oid_c_sv;	// Vector of OID labels.
	std::vector<u_int>	 oid_c_uiv;	// Vector of OID numerals.
	u_int			*oid_c_uia;	// Array of OID numerals.


    unsigned int length,		// Current length.
		 length_max,		// Total length.
		 index_current,		// Current index (offset).
		 subpath_begin,		// Subpath beginning.
		 subpath_end;		// Subpath ending.

	bool	 is_absolute_path,	// TRUE if path begins at MIB root.
		 is_permanent_subpath;	// TRUE if subpath settings should
					//   persist beyond first-time use.
						
	lso_subpath_state	 subpath_state;
					// Controls whether subpath setting
					//   persists after first use.
	lso_element_type	 element_type;
					// Types initial input as labelled 
					//   or numeric.  Also used to control
					//   output after validation.

	// -0-
	// Functions.
	//
	void	init_libsmiOid(void);
	void	resetState(int begin = 0, int end = LIBSMIOID_END);
	void	clear(void);
	void	checkSubpathState(void);

	std::string  ui2s(u_int ui) 
          { std::string s; UI_TO_S(ui, s); return s; }


protected:
	/*EMPTY*/


public:
	// -0-
	// Typedefs.
	//
	typedef std::vector<std::string>::const_iterator 
	  oid_label_const_iterator;
	typedef std::vector<u_int>::const_iterator
	  oid_numeric_const_iterator;


	// -0-
	// Constructors.
	//
	// No default constructor.  Must always declare with input.
	//
	libsmiOid(std::string oid);
	libsmiOid(std::vector<std::string> &oid, bool absolute_path = false);
	libsmiOid(std::vector<u_int> &oid, bool absolute_path = false);
	libsmiOid(int array_length, u_int array[], bool absolute_path = false);
	libsmiOid(libsmiOid &lso);

	~libsmiOid(void)  { if (oid_c_uia)  delete [] oid_c_uia; }


	// -0-
	// Miscellaneous helpers.
	//
	// absolute()	Return TRUE if OID path begins at MIB root.
	// maxsize()	Always returns full OID path length.
	// size()	Set/return current OID path length.
	// sizeinc()	Shorthand for size(size()+1).
	// index()	Set/return index into current OID (sub)path.
	// rewind()	Shorthand for index(0).
	// next()	Increment index into OID path vector.
	// prev()	Decrement index into OID path vector.
	// subpath()	(Re)set beginning/ending indeces for OID path.
	// type()	Set/return element type (label or numerical).
	// validate()	Convert and validate labelled path to numeric
	//		  path (or vice versa).
	//
	bool	    absolute(void)	{ return is_absolute_path; }
	libsmiOid & absolute(bool ap)	{ is_absolute_path = ap; return *this; }

	u_int	    maxsize(void)	{ return length_max; }

	u_int	    size(void)		{ return length; }
	libsmiOid & size(u_int newsize);
	libsmiOid & sizeinc(void)	{ return size(length+1); }

	u_int	    index(void)		{ return index_current; }
	libsmiOid & index(u_int newindex);
	libsmiOid & rewind(void)	{ return index(subpath_begin); };
	libsmiOid & next(void)		{ return index(index_current + 1); }
	libsmiOid & prev(void)		{ return index(index_current - 1); }

	libsmiOid & subpath(	u_int  begin		= 0, 
				u_int  end		= LIBSMIOID_END, 
				bool permanent_subpath	= false);

	lso_element_type    type(void)	{ return element_type; }
	libsmiOid &	    type(lso_element_type et);

	bool	    validate(void);


	// -0-
	// Conversion and output functions.
	//
	// str()	  Return OID path as string (numeric or labelled).
	// str_label()	  Shorthand for str(LSO_ET_LABEL).
	// str_numeric()  Shorthand for str(LSO_ET_NUMERIC).
	// vect_label()	  Return OID path as a vector of labels.
	// vect_numeric() Return OID path as a vector of numeric values.
	// array()	  Return OID path as an array of u_int[].
	// label()	  Return current/indexed element of OID path.
	//		    type() chooses labelled vs. numeric output.
	// oid()	  Return numeric for current/indexed OID path element.
	//
	std::string &	    str(lso_element_type et = LSO_ET_DEFAULT);
	std::string &	    str_label(void)	{ return str(LSO_ET_LABEL); }
	std::string &	    str_numeric(void)	{ return str(LSO_ET_NUMERIC); }

	std::vector<std::string> &    vect_label(void);
	std::vector<u_int> &	    vect_numeric(void);

	u_int *		    array(void);

	std::string    label(void)		{ return label(index_current); }
	std::string    label(int path_index);

	u_int	    oid(void)		{ return oid(index_current); }
	u_int	    oid(int path_index);


	// -0-
	// Comparison operators.
	//
	// <, >, ==, !=
	// 	Comparison of (numeric) oid_uiv ONLY.  validate() uses 
	//	  not-equal (!=) to disambiguate labelled OID path.
	//
	bool	operator< (libsmiOid &lso) {
			libsmiOid left(*this), right(lso);
			return (left.vect_numeric() < right.vect_numeric()) 
				? true : false;
	}

	bool	operator> (libsmiOid &lso) { 
			return (lso < *this) ? true : false; 
	}

	bool	operator== (libsmiOid &lso) {
			return ( ! ((*this < lso) || (lso < *this)) )
				? true : false;
	}

	bool	operator!= (libsmiOid &lso) {
			return ( ! (*this == lso) ) ? true : false;
	}


	// -0-
	// Algebraic operators.
	//
	// +=	Append (non-absolute, numeric) OID path to this.   XXX
	//
	libsmiOid &	operator+= (libsmiOid &lso);

	libsmiOid &	operator+= (std::string &oid) {
				libsmiOid lso(oid); *this += lso; return *this;
	}
	libsmiOid &	operator+= (char *oid) {
				libsmiOid lso(oid); *this += lso; return *this;
	}

};  // class libsmiOid

#endif  // _LIBSMIOID_H


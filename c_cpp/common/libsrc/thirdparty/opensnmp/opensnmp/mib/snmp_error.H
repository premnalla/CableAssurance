//
// snmp_error.H
//
// Entrymark and debugging flag macros.
//
// FIX	Replace with an object?
// FIX  Get class scope into function name output.
//
//
//  David Reeder  <dreeder@cs.pdx.edu>   Mon Jan 31 23:42:11 EST 2000
//

#ifndef _SNMP_ERROR_H
#define _SNMP_ERROR_H

#include <string>

using std::string;


// ------------------------------------ -o- 
// Debugging flags.
//
// DF		Build a Debug Flag symbol.
// ISDF		Test whether a given Debug Flag IS defined.
// SETDF	Set a single Debug Flag.
// UNSETDF	Unset a single Debug Flag.
//
#define DF(l)		(DEBUG_ ## l)
#define ISDF(f)		( (snmp_debug & (DF(ON))) && (snmp_debug & DF(f)) )
#define SETDF(fs)	{ snmp_debug |= DF(fs); }
#define UNSETDF(fs)	{ snmp_debug &= ~DF(fs); }

#define DEBUG_ON		0x0001	// General ON/OFF switch.
#define DEBUG_EM		0x0002	// Print entry mark messages.
#define DEBUG_DUMPARRAY		0x0004	// Dump from libsmiOid::array().
#define DEBUG_ARRAYOP		0x0008	// Inline from mibNode:operator[].
#define DEBUG_ROOTSBEFORE	0x0010	// Dump mibroots before instantiated.
#define DEBUG_ROOTSAFTER	0x0020	// Dump mibroots after instantiation.

static unsigned int snmp_debug =	DF(ON) | DF(EM)	
				// | DF(DUMPARRAY)
				// | DF(ARRAYOP)
				// | DF(ROOTSBEFORE)
				// | DF(ROOTSAFTER)
				;


// ------------------------------------ -o-
// Entry mark macro.  
// Also EM + Arguments, EM + Destructor.
//
// Usage: EM(  <level>)					-OR-
//	  EM0( <level>, <message>)			-OR-
//	  EMn( <level>, <format_string> [ <n_variable(s)> ])
//
// Usage: EMA( <level>, <function_args>)		-OR-
//	  EMA0(<level>, <function_args>, <message>)	-OR-
//	  EMAn(<level>, <function_args>, <format_string> [ <n_variable(s)> ])
//
//	  EMD( <level>)					-OR-
//	  EMD0(<level>, <message>)
//
// Where output is given only if level >= _EM_LEVEL, and N = {0..5} is
// the number of arguments given to a printf-type format string.
//
#ifndef _EM_H
#define _EM_H

#define _EM_LEVEL	1
#define _EM_OSTR	std::cerr
#define _EM_PRINT(em)	if (ISDF(EM) && (_EM_LEVEL <= em))

#define _EM_FUNC(em, fargs, mesg)					 \
	_EM_PRINT(em) {							 \
		string args = fargs, tilde = "~";			 \
		if ( args != tilde ) tilde = "";			 \
		_EM_OSTR << "EM " << tilde << __FUNCTION__ 		 \
			 << "(" << fargs << "), l." << __LINE__ << ".  " \
			 << mesg << std::endl;				 \
	}

#define _EM_VAR(em, v) \
	_EM_PRINT(em) { _EM_OSTR << "    " << #v << " = " << v << std::endl; }

#define EM(  em)			_EM_FUNC(em, "", "");
#define EM0( em, mesg)			_EM_FUNC(em, "", mesg);
#define EM1( em, a1)			_EM_FUNC(em, "", "");	 _EM_VAR(em,a1);
#define EM2( em, a1, a2)		EM1(em, a1);		 _EM_VAR(em,a2);
#define EM3( em, a1, a2, a3)		EM2(em, a1, a2);	 _EM_VAR(em,a3);
#define EM4( em, a1, a2, a3, a4)	EM3(em, a1, a2, a3);	 _EM_VAR(em,a4);
#define EM5( em, a1, a2, a3, a4, a5)	EM4(em, a1, a2, a3, a4); _EM_VAR(em,a5);

#define EMA( em, fa)			_EM_FUNC(em, fa, "");
#define EMA0(em, fa, mesg)		_EM_FUNC(em, fa, mesg); 
#define EMA1(em, fa, a1)		_EM_FUNC(em, fa, "");	 _EM_VAR(em,a1);
#define EMA2(em, fa, a1, a2)		EMA1(em, fa, a1);	 _EM_VAR(em,a2);
#define EMA3(em, fa, a1, a2, a3)	EMA2(em, fa, a1, a2);	 _EM_VAR(em,a3);
#define EMA4(em, fa, a1, a2, a3, a4)	EMA3(em, fa, a1, a2, a3);_EM_VAR(em,a4);
#define EMA5(em, fa, a1, a2, a3, a4, a5) \
				    EMA4(em, fa, a1, a2, a3, a4);_EM_VAR(em,a5);

#define EMD(em)				_EM_FUNC(em, "~", "");
#define EMD0(em, mesg)			_EM_FUNC(em, "~", mesg);

#endif /* _EM_H	*/

#endif  // _SNMP_ERROR_H



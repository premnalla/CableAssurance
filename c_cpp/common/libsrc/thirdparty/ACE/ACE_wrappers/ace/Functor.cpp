// Functor.cpp,v 4.7 2005/10/28 16:14:52 ossama Exp

// ============================================================================
//
// = LIBRARY
//    ace
//
// = FILENAME
//    Functor.cpp
//
// = DESCRIPTION
//    Non-inlinable method definitions for non-templatized classes
//    and template specializations implementing the GOF Command Pattern,
//    and STL-style functors.
//
// = AUTHOR
//    Chris Gill           <cdgill@cs.wustl.edu>
//
//    Based on Command Pattern implementations originally done by
//
//    Carlos O'Ryan        <coryan@cs.wustl.edu>  and
//    Douglas C. Schmidt   <schmidt@cs.wustl.edu> and
//    Sergio Flores-Gaitan <sergio@cs.wustl.edu>
//
//    and on STL-style functor implementations originally done by
//
//    Irfan Pyarali  <irfan@cs.wustl.edu>
//
// ============================================================================

#include "ace/Functor_T.h"
#include "ace/Functor.h"

#if !defined (__ACE_INLINE__)
#include "ace/Functor.inl"
#endif /* __ACE_INLINE__ */

ACE_RCSID(ace, Functor, "Functor.cpp,v 4.7 2005/10/28 16:14:52 ossama Exp")

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

ACE_Command_Base::~ACE_Command_Base (void)
{
}

ACE_END_VERSIONED_NAMESPACE_DECL

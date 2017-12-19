// -*- C++ -*-

// ================================================================
/**
 * @file DLL_Test_Parent.h
 *
 * DLL_Test_Parent.h,v 4.1 2005/10/06 22:51:08 ossama Exp
 *
 * @author Kirthika Parameswaran  <kirthika@cs.wustl.edu>
 * @author Ossama Othman  <ossama@dre.vanderbilt.edu>
 */
// ================================================================

#ifndef ACE_TESTS_DLL_TEST_PARENT_H
#define ACE_TESTS_DLL_TEST_PARENT_H

#include "DLL_Test_Parent_Export.h"

#if !defined (ACE_LACKS_PRAGMA_ONCE)
# pragma once
#endif /* ACE_LACKS_PRAGMA_ONCE */


class DLL_Test_Parent_Export Parent
{
public:

  virtual ~Parent (void);

  virtual void test (void);

};

#endif /* ACE_TESTS_DLL_TEST_PARENT_H */

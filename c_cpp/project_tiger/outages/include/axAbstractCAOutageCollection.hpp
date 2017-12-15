
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCAOutageCollection_hpp_
#define _axAbstractCAOutageCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAvlTreeExtLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axAbstractCAOutageCollection.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class axAbstractCAOutageCollection : public axAvlTreeExtLock
{
public:

  /// destructor
  virtual ~axAbstractCAOutageCollection();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */

  ///
  // virtual axObject * add(axObject *);

protected:

  /**
   * Specify comparison function and locking scheme
   */
  axAbstractCAOutageCollection(axAbstractLock *);

  /// default constructor
  axAbstractCAOutageCollection();

private:

  axAbstractCAOutageCollection(const axAbstractCAOutageCollection &);

};

#endif // _axAbstractCAOutageCollection_hpp_

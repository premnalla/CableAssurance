
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAOutageCollection_hpp_
#define _axCAOutageCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAOutageCollection.hpp"

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
 * file/class: axCAOutageCollection.hpp
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

class axCAOutageCollection : public axAbstractCAOutageCollection
{
public:

  /**
   * Specify comparison function and locking scheme
   */
  axCAOutageCollection(axAbstractLock *);

  /// default constructor
  axCAOutageCollection();

  /// destructor
  virtual ~axCAOutageCollection();

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
  virtual axObject * add(axObject *);

protected:

private:

  axCAOutageCollection(const axCAOutageCollection &);

};

#endif // _axCAOutageCollection_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAAlarmCollection_hpp_
#define _axCAAlarmCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAAlarmCollection.hpp"

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
 * file/class: axCAAlarmCollection.hpp
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

class axCAAlarmCollection : public axAbstractCAAlarmCollection
{
public:

  /**
   * Specify comparison function and locking scheme
   */
  axCAAlarmCollection(axAbstractLock *);

  /// default constructor
  axCAAlarmCollection();

  /// destructor
  virtual ~axCAAlarmCollection();

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

  axCAAlarmCollection(const axCAAlarmCollection &);

};

#endif // _axCAAlarmCollection_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSimpleRunnableCollection_hpp_
#define _axSimpleRunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnableCollection.hpp"
#include "axListBase.hpp"

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
 * file/class: axSimpleRunnableCollection.hpp
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

class axSimpleRunnableCollection : 
               public axAbstractCARunnableCollection, public axListBase
{
public:

  /// data constructor
  axSimpleRunnableCollection();

  /// destructor
  virtual ~axSimpleRunnableCollection();

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

  /**
   *
   */
  virtual bool scheduleRunnables(void);

  /**
   *
   */
  virtual void addSubject(axObject *);
  virtual void addSubject(axListBase &);

protected:

  /**
   *
   */
  bool areAllRunnablesComplete(void);

  /**
   *
   */
  virtual AX_UINT32 getMaxDevicesPerRunnable(void);

  /**
   *
   */
  virtual axAbstractCARunnable * getNextRunnable(axAbstractCARunnable *);

private:

  axSimpleRunnableCollection(const axSimpleRunnableCollection &);

};

#endif // _axSimpleRunnableCollection_hpp_

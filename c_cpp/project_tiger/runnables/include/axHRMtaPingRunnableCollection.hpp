
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHRMtaPingRunnableCollection_hpp_
#define _axHRMtaPingRunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSimpleRunnableCollection.hpp"

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
 * file/class: axHRMtaPingRunnableCollection.hpp
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

class axHRMtaPingRunnableCollection : public axSimpleRunnableCollection
{
public:

  /// data constructor
  axHRMtaPingRunnableCollection(AX_UINT32);

  /// destructor
  virtual ~axHRMtaPingRunnableCollection();

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
  virtual AX_INT64  hashCode(void);
  virtual AX_UINT32 hashUInt32(void);

  /**
   *
   */
  virtual bool scheduleRunnables(void);

  /**
   *
   */
  virtual axAbstractCARunnable * createNewRunnable(void);

  /**
   *
   */
  virtual void processRunnableComplete(axAbstractRunnable *);

protected:

  /// default constructor
  axHRMtaPingRunnableCollection();

  /**
   *
   */
  virtual AX_UINT32 getMaxDevicesPerRunnable(void);

  /**
   *
   */
  virtual bool canScheduleRunnables(void);

  /**
   *
   */
  virtual void postponeTask(void);

private:

  axHRMtaPingRunnableCollection(const axHRMtaPingRunnableCollection &);

  AX_UINT32  m_cmtsResId;

};

#endif // _axHRMtaPingRunnableCollection_hpp_
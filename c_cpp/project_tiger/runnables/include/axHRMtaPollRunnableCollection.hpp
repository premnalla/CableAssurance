
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHRMtaPollRunnableCollection_hpp_
#define _axHRMtaPollRunnableCollection_hpp_

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
 * file/class: axHRMtaPollRunnableCollection.hpp
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

class axHRMtaPollRunnableCollection : public axSimpleRunnableCollection
{
public:

  /// data constructor
  axHRMtaPollRunnableCollection(AX_UINT32);

  /// destructor
  virtual ~axHRMtaPollRunnableCollection();

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
  axHRMtaPollRunnableCollection();

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

  axHRMtaPollRunnableCollection(const axHRMtaPollRunnableCollection &);

  AX_UINT32  m_cmtsResId;

};

#endif // _axHRMtaPollRunnableCollection_hpp_

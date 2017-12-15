
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDailySummaryRC_hpp_
#define _axDailySummaryRC_hpp_

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
 * file/class: axDailySummaryRC.hpp
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

class axDailySummaryRC : public axSimpleRunnableCollection
{
public:

  /// destructor
  virtual ~axDailySummaryRC();

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
  static axDailySummaryRC * getInstance(void);

  /**
   *
   */
  // virtual AX_INT64  hashCode(void);
  // virtual AX_UINT32 hashUInt32(void);
  // virtual bool isKeyEqual(axObject *);

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
  axDailySummaryRC();

  /**
   *
   */
  virtual bool canScheduleRunnables(void);

  /**
   *
   */
  virtual void postponeTask(void);

private:

  axDailySummaryRC(const axDailySummaryRC &);

  static axDailySummaryRC * m_instance;

};

#endif // _axDailySummaryRC_hpp_

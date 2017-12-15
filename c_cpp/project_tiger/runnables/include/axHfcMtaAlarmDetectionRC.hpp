
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcMtaAlarmDetectionRC_hpp_
#define _axHfcMtaAlarmDetectionRC_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSimpleRunnableCollection.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axHfcMtaAlarmDetectionRC.hpp
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

class axHfcMtaAlarmDetectionRC : public axSimpleRunnableCollection
{
public:

  /// data constructor
  axHfcMtaAlarmDetectionRC(INTDS_RESID_t);

  /// destructor
  virtual ~axHfcMtaAlarmDetectionRC();

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
  virtual void processRunnableComplete(axAbstractRunnable *);

  /**
   *
   */
  virtual axAbstractCARunnable * createNewRunnable(void);

protected:

  /// default constructor
  axHfcMtaAlarmDetectionRC();

  /**
   *
   */
  virtual bool canScheduleRunnables(void);

  /**
   *
   */
  virtual void postponeTask(void);

private:

  axHfcMtaAlarmDetectionRC(const axHfcMtaAlarmDetectionRC &);

  INTDS_RESID_t  m_cmtsResId;

};

#endif // _axHfcMtaAlarmDetectionRC_hpp_

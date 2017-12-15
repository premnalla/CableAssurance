
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCAOutage_hpp_
#define _axAbstractCAOutage_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axObject.hpp"
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
 * file/class: axAbstractCAOutage.hpp
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

class axAbstractCAOutage : public axObject
{
public:

  /// destructor
  virtual ~axAbstractCAOutage();

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
  virtual AX_UINT8 getAlarmType(void)=0;
  virtual AX_UINT8 getAlarmSubType(void)=0;

  ///
  time_t getDetectionTime(void);

  ///
  void      setOutageId(AX_UINT32);
  AX_UINT32 getOutageId(void);

  ///
  void           setAlarmId(AX_UINT32);
  AX_UINT32      getAlarmId(void);

  ///
  void           setResId(INTDS_RESID_t);
  INTDS_RESID_t  getResId(void);

protected:

  /// default constructor
  axAbstractCAOutage(time_t);

  /// default constructor
  axAbstractCAOutage();

private:

  axAbstractCAOutage(const axAbstractCAOutage &);

  // This is not the same as alarm_id field in the db table
  // This is the unique outage id generated valid only while
  // this application is up and running. When restarted the
  // the numbers get reassigned from 1
  AX_UINT32      m_outageId;

  // the following mimic the db fields. Replicated only for
  // speedy lookup
  AX_UINT32      m_alarmId;
  INTDS_RESID_t  m_alarmResId;
  time_t         m_detectionTime;
};

#endif // _axAbstractCAOutage_hpp_

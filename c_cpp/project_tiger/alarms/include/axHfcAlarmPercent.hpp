
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmPercent_hpp_
#define _axHfcAlarmPercent_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarm.hpp"

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
 * file/class: axHfcAlarmPercent.hpp
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

class axHfcAlarmPercent : public axHfcAlarm
{
public:

  /**
   * In: CMTS ResID, In-Memory HFC
   */
  axHfcAlarmPercent(INTDS_RESID_t, axInternalHfc *);

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcAlarmPercent(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axHfcAlarmPercent();

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
  virtual bool addAlarm(void);

  /**
   *
   */
  virtual bool postSoakCheckEscalate(void);

  /**
   *
   */
  virtual AX_UINT8 getAlarmSubType(void);

#if 0 // moved to axHfcAlarm
  /*
   *
   */
  void         setPreSoakTotalCm(AX_UINT16);
  AX_UINT16    getPreSoakTotalCm(void);
  void         setPreSoakOnlineChangeCm(AX_UINT16);
  AX_UINT16    getPreSoakOnlineChangeCm(void);
  void         setPostSoakTotalCm(AX_UINT16);
  AX_UINT16    getPostSoakTotalCm(void);
  void         setPostSoakOnlineChangeCm(AX_UINT16);
  AX_UINT16    getPostSoakOnlineChangeCm(void);
#endif

protected:

  /**
   * In: 
   * Out: 
   */

private:

  /// default constructor
  axHfcAlarmPercent();

  /// copy constructor
  axHfcAlarmPercent(const axHfcAlarmPercent &);

#if 0 // moved to axHfcAlarm
  /*
   * Pre Soak
   */
  AX_UINT16         m_preSoakCmTotal;
  AX_UINT16         m_preSoakCmOnlineChange;

  /*
   * Post Soak
   */
  AX_UINT16         m_postSoakCmTotal;
  AX_UINT16         m_postSoakCmOnlineChange;
#endif

};

#endif // _axHfcAlarmPercent_hpp_

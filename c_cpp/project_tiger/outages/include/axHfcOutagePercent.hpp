
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutagePercent_hpp_
#define _axHfcOutagePercent_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutage.hpp"

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
 * file/class: axHfcOutagePercent.hpp
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

class axHfcOutagePercent : public axHfcOutage
{
public:

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcOutagePercent(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axHfcOutagePercent();

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

  virtual AX_UINT8 getAlarmSubType(void);

protected:


private:

  /// default constructor
  axHfcOutagePercent();

  axHfcOutagePercent(const axHfcOutagePercent &);

};

#endif // _axHfcOutagePercent_hpp_

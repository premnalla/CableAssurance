
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmDetection_hpp_
#define _axHfcAlarmDetection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCorrelatedAlarmDetection.hpp"
#include "axInternalHfc.hpp"

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
 * file/class: axHfcAlarmDetection.hpp
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

class axHfcAlarmDetection : public axCorrelatedAlarmDetection
{
public:

  /// data constructor
  axHfcAlarmDetection(axInternalHfc *);

  /// destructor
  virtual ~axHfcAlarmDetection();

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


protected:

  /// default constructor
  axHfcAlarmDetection();

  /**
   * Overloaded from the base class
   */
  virtual bool hasData(void);
  virtual axAbstractIterator * getCableModems(void);
  virtual axAbstractIterator * getMTAs(void);

private:

  axHfcAlarmDetection(const axHfcAlarmDetection &);

  axInternalHfc * m_intHfc;
};

#endif // _axHfcAlarmDetection_hpp_

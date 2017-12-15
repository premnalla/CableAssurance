
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axChannelAlarmDetection_hpp_
#define _axChannelAlarmDetection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCorrelatedAlarmDetection.hpp"
#include "axInternalChannel.hpp"

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
 * file/class: axChannelAlarmDetection.hpp
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

class axChannelAlarmDetection : public axCorrelatedAlarmDetection
{
public:

  /// data constructor
  axChannelAlarmDetection(axInternalChannel *);

  /// destructor
  virtual ~axChannelAlarmDetection();

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
  axChannelAlarmDetection();

  /**
   * Overloaded from the base class
   */
  virtual bool hasData(void);
  virtual axAbstractIterator * getCableModems(void);
  virtual axAbstractIterator * getMTAs(void);

private:

  axChannelAlarmDetection(const axChannelAlarmDetection &);

  axInternalChannel * m_intChannel;
};

#endif // _axChannelAlarmDetection_hpp_


//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axOutageTimerEntry_hpp_
#define _axOutageTimerEntry_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerObject.hpp"
#include "axAbstractCAOutage.hpp"

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
 * file/class: axOutageTimerEntry.hpp
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

class axOutageTimerEntry : public axAbstractTimerObject
{
public:

  /// destructor
  virtual ~axOutageTimerEntry();

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
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual AX_INT64 hashCode(void);


  ///
  AX_UINT32 getOutageId(void);

protected:

  /// data constructor
  axOutageTimerEntry(axAbstractCAOutage *, time_t &);

  /// default constructor
  axOutageTimerEntry();

private:

  axOutageTimerEntry(const axOutageTimerEntry &);

  AX_UINT32 m_outageId;
};

#endif // _axOutageTimerEntry_hpp_

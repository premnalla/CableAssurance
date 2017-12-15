
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMtaCurrentAvailability_hpp_
#define _axDbMtaCurrentAvailability_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCurrentStatus.hpp"
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
 * file/class: axDbMtaCurrentAvailability.hpp
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

class axDbMtaCurrentAvailability : public axDbAbstractCurrentStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /// default constructor
  axDbMtaCurrentAvailability();

  /**
   * IN: (e)MTA Res ID
   */
  axDbMtaCurrentAvailability(DB_RESID_t);

  /**
   * IN: (e)MTA Res ID; Internal (e)MTA;
   */
  axDbMtaCurrentAvailability(DB_RESID_t, time_t, axIntGenMtaNonkey_t *);

  /// destructor
  virtual ~axDbMtaCurrentAvailability();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  // AX_UINT32   m_availTime;
  // AX_UINT32   m_unavailTime;
  // AX_UINT8    m_availability;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

private:

  // Copy disallowed
  axDbMtaCurrentAvailability(const axDbMtaCurrentAvailability &);

};

#endif // _axDbMtaCurrentAvailability_hpp_

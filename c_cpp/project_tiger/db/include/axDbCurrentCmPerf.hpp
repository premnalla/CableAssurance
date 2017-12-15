
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCurrentCmPerf_hpp_
#define _axDbCurrentCmPerf_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCmPerf.hpp"

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
 * file/class: axDbCurrentCmPerf.hpp
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

class axDbCurrentCmPerf : public axDbAbstractCmPerf
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * QueryCurrent;
  static AX_INT8 * UpdateCurrent;
  static AX_INT8 * InsertCurrent;

  /// default constructor
  axDbCurrentCmPerf();

  /**
   * IN: CM Res ID
   */
  axDbCurrentCmPerf(DB_RESID_t);

  /**
   * IN: CM Res ID;
   * IN: time_t (seconds since 01/1/70
   * IN: Internal CM;
   */
  axDbCurrentCmPerf(DB_RESID_t, time_t, axIntCmNonkey_t *);

  /// destructor
  virtual ~axDbCurrentCmPerf();

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

  AX_UINT32 m_sumTcaTime;
  AX_UINT32 m_sumNonTcaTime;

  /**
   * Time of state (tca->nonTCA || nonTCA->tca) change
   */
  AX_UINT32 m_lastTcaChangeTime;

  AX_UINT16 m_stateChanges;

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
  axDbCurrentCmPerf(const axDbCurrentCmPerf &);

};

#endif // _axDbCurrentCmPerf_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axGlobalData_hpp_
#define _axGlobalData_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axGlobalCmtsTable.hpp"
// #include "axCAOutageCollection.hpp"
#include "axInternalCmts.hpp"
// #include "axInternalHfc.hpp"
// #include "axInternalChannel.hpp"
// #include "axInternalCm.hpp"
// #include "axInternalGenMta.hpp"

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
 * file/class: axGlobalData.hpp
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

class axGlobalData 
{
public:

  /// destructor
  virtual ~axGlobalData();

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
  static axGlobalData * getInstance(void);

  ///
  axGlobalCmtsTable * getCmtsTable(void);
  axInternalCmts    * findCmts(axIntCmtsKey_t &);
  axInternalCmts    * findCmtsExtLock(axIntCmtsKey_t &);
  // axInternalHfc     * findHfc(axInternalCmts *, axIntHfcKey_t &);

  ///
  // moved to axOutageTrackingMgr class
  // axCAOutageCollection * getCurrentHfcOutageTable(void);
  // axCAOutageCollection * getCurrentMtaOutageTable(void);

protected:

  /// default constructor
  axGlobalData();

private:

  axGlobalData(const axGlobalData &);

  static axGlobalData * m_instance;

  axGlobalCmtsTable m_cmtsTable;

  // moved to axOutageTrackingMgr class
  // axCAOutageCollection m_currentHfcOutageTable;
  // axCAOutageCollection m_currentMtaOutageTable;

};

#endif // _axGlobalData_hpp_

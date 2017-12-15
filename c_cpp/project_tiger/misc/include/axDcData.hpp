
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDcData_hpp_
#define _axDcData_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"

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
 * file/class: axDcData.hpp
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

class axDcData : public axObject
{
public:

  /// default constructor
  axDcData();

  /// destructor
  virtual ~axDcData();

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

  /*
   * Data begin
   */

  std::string m_cmtsName;
  std::string m_cmtsCommAddr;
  std::string m_hfcName;
  std::string m_cmMac;
  std::string m_mtaMac;
  std::string m_cmFqdn;
  std::string m_mtaFqdn;

  /*
   * Data begin
   */

protected:


private:

  axDcData(const axDcData &);

};

#endif // _axDcData_hpp_

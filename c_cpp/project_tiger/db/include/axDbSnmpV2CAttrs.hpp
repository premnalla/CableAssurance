
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbSnmpV2CAttrs_hpp_
#define _axDbSnmpV2CAttrs_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbsDbSnmpVerAttrs.hpp"

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
 * file/class: axDbSnmpV2CAttrs.hpp
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

class axDbSnmpV2CAttrs : public axAbsDbSnmpVerAttrs
{
public:

  /**
   * default constructor
   */
  axDbSnmpV2CAttrs();

  /// destructor
  virtual ~axDbSnmpV2CAttrs();

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
  DB_SNMP_COMMUNITY_t  m_readCommunity;
  DB_SNMP_COMMUNITY_t  m_writeCommunity;

  /**
   * 
   */
  axDbSnmpV2CAttrs(const axDbSnmpV2CAttrs &);

protected:


private:

};

#endif // _axDbSnmpV2CAttrs_hpp_

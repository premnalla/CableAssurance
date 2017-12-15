
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMta_hpp_
#define _axDbMta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbGenMta.hpp"

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
 * file/class: axDbMta.hpp
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

class axDbMta : public axDbGenMta
{
public:

  /// default constructor
  axDbMta();

  /**
   * IN: MTA Res ID
   */
  axDbMta(DB_RESID_t);

  /**
   * IN: MTA mac, CMTS Res Id
   */
  axDbMta(DB_MAC_t, DB_RESID_t);

  /// destructor
  virtual ~axDbMta();

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
   * Begin data members...
   */

  DB_RESID_t m_cmtsResId;
  DB_RESID_t m_hfcResId;

  /* TODO add other members */

  /**
   * End data members...
   */

  /* TODO add other methods here */

  virtual bool queryObj(void);

  virtual AX_UINT8 getResourceType(void);

protected:

private:

  axDbMta(const axDbMta &);

  void instantiationInit(void);

};

#endif // _axDbMta_hpp_

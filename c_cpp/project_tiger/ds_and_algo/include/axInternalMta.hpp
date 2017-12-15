
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalMta_hpp_
#define _axInternalMta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalGenMta.hpp"

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
 * file/class: axInternalMta.hpp
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

class axInternalMta : public axInternalGenMta
{
public:

  /// data constructor
  axInternalMta(const char *);

  /// destructor
  virtual ~axInternalMta();

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

   // static int CompareFunction(const void *, const void *, void *);

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void);

protected:

  /// default constructor
  axInternalMta();

private:

  axInternalMta(const axInternalMta &);

  axInternalMtaData_t m_mtaData;
};

#endif // _axInternalMta_hpp_

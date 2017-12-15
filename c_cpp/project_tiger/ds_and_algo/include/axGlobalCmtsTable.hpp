
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axGlobalCmtsTable_hpp_
#define _axGlobalCmtsTable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axInternalDsTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// class axAbstractCollection;
// class axAbstractLock;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axGlobalCmtsTable.hpp
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

class axGlobalCmtsTable 
{
public:

  /// default constructor
  axGlobalCmtsTable();

  /// destructor
  virtual ~axGlobalCmtsTable();

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
   synchronizedTable_t * getTable(void);

protected:


private:

  axGlobalCmtsTable(const axGlobalCmtsTable &);

  synchronizedTable_t  m_data;

};

#endif // _axGlobalCmtsTable_hpp_

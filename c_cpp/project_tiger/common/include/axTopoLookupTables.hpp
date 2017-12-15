
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTopoLookupTables_hpp_
#define _axTopoLookupTables_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axTopoLookupIntDsTypes.hpp"
#include "axTopoLookupIntGenMta.hpp"
#include "axListExtLock.hpp"
#include "axAbstractTopoContainerObj.hpp"

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
 * file/class: axTopoLookupTables.hpp
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

class axTopoLookupTables
{
public:

  /// destructor
  virtual ~axTopoLookupTables();

  // Singleton Instance getter
  static axTopoLookupTables * getInstance(void);

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
   *
   */
  synchronizedTable_t * getMtaTable(void);
  axListExtLock       * getContainerList(void);

  /**
   *
   */
  axTopoLookupIntGenMta * findMtaExtLock(AX_INT8 *);
  axTopoLookupIntGenMta * findMta(AX_INT8 *);
  axTopoLookupIntGenMta * addMtaExtLock(axTopoLookupIntGenMta *);
  axTopoLookupIntGenMta * addMta(axTopoLookupIntGenMta *);

  /**
   *
   */
  axAbstractTopoContainerObj * findContainerExtLock(AX_UINT16);
  axAbstractTopoContainerObj * findContainerExtLock(AX_INT32);
  axAbstractTopoContainerObj * findContainer(AX_UINT16);
  axAbstractTopoContainerObj * findContainer(AX_INT32);
  axAbstractTopoContainerObj * addContainerExtLock(axAbstractTopoContainerObj *);
  axAbstractTopoContainerObj * addContainer(axAbstractTopoContainerObj *);
  void                         setFdSetExtLock(fd_set *);
  void                         setFdSet(fd_set *);
  void                         getFdSetExtLock(AX_INT32 * fdList, size_t len, fd_set *);
  void                         getFdSet(AX_INT32 * fdList, size_t len, fd_set *);

protected:

  /// default constructor
  axTopoLookupTables();

private:

  axTopoLookupTables(const axTopoLookupTables &);

  ///
  synchronizedTable_t m_mtaTable;
  axListExtLock       m_containerList;

  /**
   *
   */
  static axTopoLookupTables * m_instance;

};

#endif // _axTopoLookupTables_hpp_

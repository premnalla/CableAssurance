
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutageCollections_hpp_
#define _axHfcOutageCollections_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSubSystem.hpp"
#include "axListExtLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used in a similar fashion as the axCmtsPoller, 
 * axMtaPoller, etc., classes. It stores a collection of Runnable-
 * Collections.
 * 
 * 
 * file/class: axHfcOutageCollections.hpp
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

class axHfcOutageCollections : 
                       public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axHfcOutageCollections();

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
  static axHfcOutageCollections * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axHfcOutageCollections();

private:

  axHfcOutageCollections(const axHfcOutageCollections &);

  bool loadRunnableCollection(void);

  static axHfcOutageCollections * m_instance;

  bool m_initialized;
};

#endif // _axHfcOutageCollections_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axReaderWriterLock_hpp_
#define _axReaderWriterLock_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axAbstractLock.hpp"

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
 * file/class: axReaderWriterLock.hpp
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

class axReaderWriterLock : public axAbstractLock
{
public:

  /// default constructor
  axReaderWriterLock();

  /// destructor
  virtual ~axReaderWriterLock();

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
   virtual void readPreAccess(void);
   virtual void readPostAccess(void);

   ///
   virtual void writePreAccess(void);
   virtual void writePostAccess(void);

protected:


private:

  axReaderWriterLock(const axReaderWriterLock &);

  pthread_mutex_t m_readWriteLock;

};

#endif // _axReaderWriterLock_hpp_

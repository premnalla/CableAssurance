
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMultipleReaderLock_hpp_
#define _axMultipleReaderLock_hpp_

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
 * file/class: axMultipleReaderLock.hpp
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

class axMultipleReaderLock : public axAbstractLock
{
public:

  /// default constructor
  axMultipleReaderLock();

  /// destructor
  virtual ~axMultipleReaderLock();

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

  ///
  axMultipleReaderLock(const axMultipleReaderLock &);

  ///
  pthread_mutex_t m_readLock;
  pthread_mutex_t m_writeLock;

  ///
  int m_readCounter;

};

#endif // _axMultipleReaderLock_hpp_

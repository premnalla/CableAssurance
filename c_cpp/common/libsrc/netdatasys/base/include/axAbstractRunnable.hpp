
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractRunnable_hpp_
#define _axAbstractRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
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
 * file/class: axAbstractRunnable.hpp
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

class axAbstractRunnable : public axObject
{
public:

  /// destructor
  virtual ~axAbstractRunnable();

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

  virtual AX_INT32 preRun(void)=0;
  virtual AX_INT32 run(void)=0;
  virtual AX_INT32 postRun(void)=0;
  virtual void     nextAction(void)=0;

  /**
   *
   */
  AX_INT8 getPriority(void);

  /**
   *
   */
  virtual axObject * getId(void);
  // virtual AX_UINT32 getId(void);

  /**
   *
   */
  axObject * getNextUniqueId(void);
  // AX_UINT32 getNextUniqueId(void);

  /**
   *
   */
  virtual AX_INT64  hashCode(void);
  virtual AX_UINT32 hashUInt32(void);

  /**
   *
   */
  // virtual bool isKeyEqual(axObject *);


  bool isDeletable(void);

protected:

  /// default constructor
  axAbstractRunnable();

  void setPriority(AX_INT8);

  void setDeletable(bool);

private:

  axAbstractRunnable(const axAbstractRunnable &);

  axObject * m_id;
  AX_INT8    m_priority;
  AX_INT8    m_deletable;

  static AX_UINT32       m_uniqueId;
  static pthread_mutex_t m_lock;

};

#endif // _axAbstractRunnable_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsPollRunnableCollection_hpp_
#define _axCmtsPollRunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSimpleRunnableCollection.hpp"

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
 * file/class: axCmtsPollRunnableCollection.hpp
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

class axCmtsPollRunnableCollection : public axSimpleRunnableCollection
{
public:

  /// data constructor
  axCmtsPollRunnableCollection(AX_UINT32);

  /// destructor
  virtual ~axCmtsPollRunnableCollection();

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
  virtual AX_INT64  hashCode(void);
  virtual AX_UINT32 hashUInt32(void);
  // virtual bool isKeyEqual(axObject *);

  /**
   *
   */
  // static AX_UINT32 getNextRunnableId(void);

  /**
   *
   */
  virtual axAbstractCARunnable * createNewRunnable(void);

  /**
   *
   */
  virtual void processRunnableComplete(axAbstractRunnable *);

protected:

  /// default constructor
  axCmtsPollRunnableCollection();

  /**
   *
   */
  virtual bool canScheduleRunnables(void);

  /**
   *
   */
  virtual void postponeTask(void);

private:

  axCmtsPollRunnableCollection(const axCmtsPollRunnableCollection &);

  AX_UINT32  m_cmtsResId;

  // static AX_UINT32  m_nextRunnableId;
};

#endif // _axCmtsPollRunnableCollection_hpp_

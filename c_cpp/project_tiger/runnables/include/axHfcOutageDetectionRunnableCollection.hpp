
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutageDetectionRunnableCollection_hpp_
#define _axHfcOutageDetectionRunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSimpleRunnableCollection.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axHfcOutageDetectionRunnableCollection.hpp
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

class axHfcOutageDetectionRunnableCollection : public axSimpleRunnableCollection
{
public:

  /// data constructor
  axHfcOutageDetectionRunnableCollection(INTDS_RESID_t);

  /// destructor
  virtual ~axHfcOutageDetectionRunnableCollection();

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

  /**
   *
   */
  virtual void processRunnableComplete(axAbstractRunnable *);

protected:

  /// default constructor
  axHfcOutageDetectionRunnableCollection();

private:

  axHfcOutageDetectionRunnableCollection(const axHfcOutageDetectionRunnableCollection &);

  INTDS_RESID_t  m_cmtsResId;

};

#endif // _axHfcOutageDetectionRunnableCollection_hpp_

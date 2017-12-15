
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractWrappedStream_hpp_
#define _axAbstractWrappedStream_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axSocketFailInterface.hpp"

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
 * file/class: axAbstractWrappedStream.hpp
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

class axAbstractWrappedStream 
{
public:

  /// destructor
  virtual ~axAbstractWrappedStream();

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
  AX_INT32 getFd(void);

  /**
   *
   */
  bool isClosed(void);

protected:

  /**
   * default constructor
   */
  axAbstractWrappedStream();

  /**
   * data constructor
   */
  axAbstractWrappedStream(AX_INT32, axSocketFailInterface *);

  /**
   * Mark the stream as closed
   */
  void setClosed(bool);

  /**
   * 
   */
  axSocketFailInterface * getSocketFailHandler(void);

private:

  /// not allowed
  axAbstractWrappedStream(const axAbstractWrappedStream &);

  /**
   * Data input stream. This stream is used to input the block sizes
   * from the data stream that are written by the WrappedOutputStream.
   * <p>
   * <strong>Note:</strong> The data input stream is only used for
   * reading the byte count for performance reasons. We avoid the
   * method indirection for reading the byte data.
   */
  AX_INT32 m_fd;

  /** To mark that the stream is "closed". */
  bool m_fClosed;

  axSocketFailInterface * m_sockFailHandler;
};

#endif // _axAbstractWrappedStream_hpp_

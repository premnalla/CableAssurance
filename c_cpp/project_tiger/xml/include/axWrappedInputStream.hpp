
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axWrappedInputStream_hpp_
#define _axWrappedInputStream_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractWrappedStream.hpp"

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
 * file/class: axWrappedInputStream.hpp
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

class axWrappedInputStream : public axAbstractWrappedStream
{
public:

  /**
   * Data constructor
   * IN: FD
   */
  axWrappedInputStream(AX_INT32, axSocketFailInterface *);

  /// destructor
  virtual ~axWrappedInputStream();

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
   * Reads a single byte.
   */
  AX_INT32 streamRead(void);

  /**
   * Reads a block of bytes and returns the total number of bytes read.
   */
  AX_INT32 streamRead(AX_INT8 b[], AX_INT32 offset, AX_INT32 length);

  /**
   * Skips the specified number of bytes from the input stream.
   */
  AX_LONG streamSkip(AX_LONG);

  /**
   * Closes the input stream. This method will search for the end of
   * the wrapped input, positioning the stream at after the end packet.
   * <p>
   * <strong>Note:</strong> This method does not close the underlying
   * input stream.
   */
  void streamClose(void);

  /**
   *
   */
  bool isOpen(void);

protected:

  /**
   * default constructor
   */
  axWrappedInputStream();

  /** Bytes left on input stream for current packet. */
  AX_INT32 m_fPacketCount;

private:

  /// not allowed
  axWrappedInputStream(const axWrappedInputStream &);

  AX_INT32 readPacketCount(void);
  AX_INT32 getPacketCount(void);
};

#endif // _axWrappedInputStream_hpp_


//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axWrappedOutputStream_hpp_
#define _axWrappedOutputStream_hpp_

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
 * file/class: axWrappedOutputStream.hpp
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

class axWrappedOutputStream : public axAbstractWrappedStream
{
public:

  /**
   * Data constructor
   * IN: FD
   */
  axWrappedOutputStream(AX_INT32, axSocketFailInterface *);

  /// destructor
  virtual ~axWrappedOutputStream();

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
   * Writes a single byte.
   */
  AX_INT32 streamWrite(AX_INT8);

  /**
   * Writes a block of bytes and returns the total number of bytes read.
   */
  AX_INT32 streamWrite(AX_INT8 b[], AX_INT32 offset, AX_INT32 length);

  /**
   * Writes a block of bytes and returns the total number of bytes read.
   */
  AX_INT32 streamWrite(AX_INT8 b[], AX_INT32 length);

  /**
   * Flushes the output buffer, writing all bytes currently in
   * the buffer to the output.
   */
  void streamFlush(void);
  void streamFlush0(void);

  /**
   * Closes the output stream. This method <strong>must</strong> be
   * called when done writing all data to the output stream.
   * <p>
   * <strong>Note:</strong> This method does <em>not</em> close the
   * actual output stream, only makes the input stream see the stream
   * closed. Do not write bytes after closing the output stream.
   */
  void streamClose(void);

protected:

  /**
   * default constructor
   */
  axWrappedOutputStream();

  /** Bytes left on input stream for current packet. */
  AX_INT32 m_fPosition;

  AX_INT8 * m_fBuffer;

private:

  /// not allowed
  axWrappedOutputStream(const axWrappedOutputStream &);

  AX_INT32 streamWriteInt(AX_INT32);
  AX_INT32 streamWriteBytes(AX_INT8 *, AX_INT32, AX_INT32);

  static AX_INT32 m_maxBufferLen;
};

#endif // _axWrappedOutputStream_hpp_

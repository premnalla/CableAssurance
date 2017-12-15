
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include "axWrappedOutputStream.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT32 axWrappedOutputStream::m_maxBufferLen = 1024;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axWrappedOutputStream::axWrappedOutputStream() :
  m_fPosition(0),
  m_fd(0),
  m_fClosed(false),
  m_fBuffer(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axWrappedOutputStream::~axWrappedOutputStream()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axWrappedOutputStream::axWrappedOutputStream(AX_INT32 fd) :
  m_fPosition(0),
  m_fd(fd),
  m_fClosed(false),
  m_fBuffer(NULL)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedOutputStream::streamWriteInt(AX_INT32 v)
{
  // printf("streamWriteInt: val before htonl = %d\n", v);

  v = htonl(v);
  // printf("streamWriteInt: val after htonl = %d\n", v);

  AX_INT32 nWrite = write(m_fd, &v, sizeof(v));
  // printf("streamWriteInt: bytesWrote %d\n", nWrite);

  return (nWrite);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedOutputStream::streamWriteBytes(AX_INT8 b[], AX_INT32 offset, 
                                                          AX_INT32 len)
{
  AX_INT32 bytesToWrite = len;
  AX_INT32 bytesWrote = 0;
  AX_INT8 * bPtr = b;
  while(bytesToWrite > 0) {
    AX_INT32 nWrite = write(m_fd, bPtr, bytesToWrite);
    bPtr += nWrite;
    bytesToWrite -= nWrite;
    bytesWrote += nWrite;
  }
  // printf("streamWriteBytes: bytesWrote %d\n", bytesWrote);
  return (bytesWrote);
}


#if 0
//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedOutputStream::streamWrite(AX_INT8 b)
{
  AX_INT32 nWrite = 0;
  m_fBuffer[m_fPosition++] = (AX_INT8)b;
  if (m_fPosition == m_maxBufferLen) {
      m_fPosition = 0;
      streamWriteInt(m_maxBufferLen);
      nWrite = streamWriteBytes(m_fBuffer, 0, m_maxBufferLen);
  }
  return (nWrite);
}
#endif


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedOutputStream::streamWrite(AX_INT8 b[], AX_INT32 offset, AX_INT32 length)
{

#if 0
  // flush existing buffer
  if (m_fPosition > 0) {
      streamFlush0();
  }
#endif

  /*
   * write header followed by actual bytes
   */

  char * buffer = new char[4096];
  char * buffPtr = buffer;
  AX_INT32 * intPtr = (int *) buffPtr;
  AX_INT32 nboLen = htonl(length + sizeof(*intPtr));
  *intPtr = nboLen;
  buffPtr += sizeof(*intPtr);
  memcpy(buffPtr, b, length);
  buffPtr += length;
  intPtr = (int *) (buffPtr);
  *intPtr = 0;
  buffPtr += sizeof(*intPtr);
  AX_INT32 msgLen = length + 2*sizeof(*intPtr);
  AX_INT32 bytesWrote = streamWriteBytes(buffer, offset, msgLen);
  delete buffer;
  return (bytesWrote);
}


//********************************************************************
// method:
//********************************************************************
void
axWrappedOutputStream::streamFlush0(void)
{
  int length = m_fPosition;
  m_fPosition = 0;
  if (length > 0) {
      streamWriteInt(length);
      streamWriteBytes(m_fBuffer, 0, length);
  }
}


//********************************************************************
// method:
//********************************************************************
void
axWrappedOutputStream::streamFlush(void)
{
  streamFlush0();
}


//********************************************************************
// method:
//********************************************************************
void
axWrappedOutputStream::streamClose(void)
{
  streamFlush0();
  streamWriteInt(0);
}



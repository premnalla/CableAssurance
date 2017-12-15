
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include "axWrappedInputStream.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axWrappedInputStream::axWrappedInputStream() :
  m_fPacketCount(0),
  m_fd(0),
  m_fClosed(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axWrappedInputStream::~axWrappedInputStream()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axWrappedInputStream::axWrappedInputStream(AX_INT32 fd) :
  m_fPacketCount(0),
  m_fd(fd),
  m_fClosed(false)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedInputStream::readPacketCount(void)
{
  AX_INT32 ret = -1;

  if (m_fClosed) {
    goto EXIT_LABEL;
  }

  int intBuff;
  int nRead = read(m_fd, &intBuff, sizeof(intBuff));
  // printf("readPacketCount: BytesRead = %d\n", nRead);
  if (nRead <= 0) {
    m_fClosed = true;
    // goto EXIT_LABEL;
  } else {
    // printf("ValueRead before ntohl = %d\n", intBuff);
    intBuff = ntohl(intBuff);
    // printf("ValueRead after ntohl = %d\n", intBuff);
    int pc = intBuff & 0x7FFFFFFF;
    // printf("ValueRead = %d\n", pc);
    if (pc == 0) {
        m_fClosed = true;
        // goto EXIT_LABEL;
    } else {
      ret = pc;
    }
  }

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedInputStream::getPacketCount(void)
{
  AX_INT32 ret = -1;

  if (m_fClosed) {
    goto EXIT_LABEL;
  }

  if (m_fPacketCount == 0) {
    int pc = readPacketCount();
    if (pc > 0) {
      m_fPacketCount = pc;
    }
  }

  ret = m_fPacketCount;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method: Read 1 Byte
//********************************************************************
AX_INT32
axWrappedInputStream::streamRead(void)
{
  AX_INT32 ret = -1;

  // read packet header
  if (getPacketCount() <= 0) {
    goto EXIT_LABEL;
  }

  // read a byte from the packet
  AX_INT8 bBuf;
  m_fPacketCount--;
  ret = read(m_fd, &bBuf, sizeof(bBuf));

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedInputStream::streamRead(AX_INT8 b[], AX_INT32 offset, AX_INT32 length)
{
  AX_INT32 ret = -1;

  // printf("streamRead: entry\n");

  // read packet header
  if (getPacketCount() <= 0) {
    goto EXIT_LABEL;
  }

  // read bytes from packet
  if (length > m_fPacketCount) {
      length = m_fPacketCount;
  } else if (length < m_fPacketCount) {
    printf("streamRead: buffer insufficient to read data\n");
    exit(-1);
  }

  AX_INT32 bytesLeftToRead = m_fPacketCount;
  ret = 0;
  while (bytesLeftToRead > 0) {
    AX_INT32 nRead = read(m_fd, b, bytesLeftToRead);
    printf("streamRead: BytesRead = %d\n", nRead);

    bytesLeftToRead -= nRead;
    b += nRead;
    ret += nRead;

    if (nRead <= 0) {
      // NOTE: This condition should not happen. The end of
      //       the stream should always be designated by a
      //       byte count header of 0. -Ac
      m_fClosed = true;
      goto EXIT_LABEL;
    }
  }

  m_fPacketCount -= ret;

EXIT_LABEL:

  // printf("streamRead: exit\n");

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_LONG
axWrappedInputStream::streamSkip(AX_LONG n)
{
  AX_LONG ret = 0;

  if (!m_fClosed) {
      for (long i = 0; i < n; i++) {
          int b = streamRead();
          if (b == -1) {
              ret = i + 1;
              break;
          }
      }
      ret = n;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axWrappedInputStream::streamClose(void)
{
  if (!m_fClosed) {
      do {
          streamSkip(m_fPacketCount);
          int pc = getPacketCount();
          if (pc > 0) {
            m_fPacketCount = pc;
          } else {
            m_fPacketCount = 0;
          }
      } while (m_fPacketCount > 0);
      m_fClosed = true;
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axWrappedInputStream::isOpen(void)
{
  return (!m_fClosed);
}



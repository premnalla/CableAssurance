
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
  m_fPacketCount(0)
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
axWrappedInputStream::axWrappedInputStream(AX_INT32 fd,
                                        axSocketFailInterface * sfh) :
  axAbstractWrappedStream(fd, sfh),
  m_fPacketCount(0)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWrappedInputStream::readPacketCount(void)
{
  AX_INT32 ret = -1;

  int intBuff;
  int nRead;

  if (isClosed()) {
    goto EXIT_LABEL;
  }

  nRead = read(getFd(), &intBuff, sizeof(intBuff));
  // printf("readPacketCount: BytesRead = %d\n", nRead);
  if (nRead <= 0) {
    setClosed(true);
    axSocketFailInterface * failHdlr = getSocketFailHandler();
    if (failHdlr) {
      failHdlr->handleReadFailure(getFd());
    }
    // goto EXIT_LABEL;
  } else {
    // printf("ValueRead before ntohl = %d\n", intBuff);
    intBuff = ntohl(intBuff);
    // printf("ValueRead after ntohl = %d\n", intBuff);
    int pc = intBuff & 0x7FFFFFFF;
    // printf("ValueRead = %d\n", pc);
    if (pc == 0) {
        setClosed(true);
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

  if (isClosed()) {
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
  ret = read(getFd(), &bBuf, sizeof(bBuf));
  if (ret <= 0) {
    setClosed(true);
    axSocketFailInterface * failHdlr = getSocketFailHandler();
    if (failHdlr) {
      failHdlr->handleReadFailure(getFd());
    }
  }

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

  AX_INT32 bytesLeftToRead;

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

  bytesLeftToRead = m_fPacketCount;
  ret = 0;
  while (bytesLeftToRead > 0) {
    AX_INT32 nRead = read(getFd(), b, bytesLeftToRead);
    if (nRead <= 0) {
      // NOTE: This condition should not happen. The end of
      //       the stream should always be designated by a
      //       byte count header of 0. -Ac
      setClosed(true);
      axSocketFailInterface * failHdlr = getSocketFailHandler();
      if (failHdlr) {
        failHdlr->handleReadFailure(getFd());
      }
      goto EXIT_LABEL;
    }
    // printf("streamRead: BytesRead = %d\n", nRead);

    bytesLeftToRead -= nRead;
    b += nRead;
    ret += nRead;

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

  if (!isClosed()) {
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
  if (!isClosed()) {
      do {
          streamSkip(m_fPacketCount);
          int pc = getPacketCount();
          if (pc > 0) {
            m_fPacketCount = pc;
          } else {
            m_fPacketCount = 0;
          }
      } while (m_fPacketCount > 0);
      setClosed(true);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axWrappedInputStream::isOpen(void)
{
  return (!isClosed());
}



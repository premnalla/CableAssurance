/*
 * Copyright 2007 Prem Nallasivampillai
 * 
 */

/*
 * $Log: axTcpSocketInputSource.cpp,v $
 * Revision 1.3  2007/05/29 16:37:46  prem
 * Cleaned up code
 *
 * Revision 1.2  2007/05/28 18:38:06  prem
 * Check-point check-in
 *
 * Revision 1.1  2007/05/26 16:28:53  prem
 * Initial
 *
 */

// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/util/BinFileInputStream.hpp>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/util/XMLUniDefs.hpp>
#include "axTcpSocketInputSource.hpp"
#include "axBinSocketInputStream.hpp"
#include "axWrappedInputStream.hpp"

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  axTcpSocketInputSource: Constructors and Destructor
// ---------------------------------------------------------------------------
axTcpSocketInputSource::axTcpSocketInputSource(int fd
                                          , MemoryManager* const manager)
    : InputSource(manager)
      , m_fd(fd)
{
}

axTcpSocketInputSource::axTcpSocketInputSource() :
  m_fd(0)
{
}

axTcpSocketInputSource::~axTcpSocketInputSource()
{
}


// ---------------------------------------------------------------------------
//  axTcpSocketInputSource: InputSource interface implementation
// ---------------------------------------------------------------------------
BinInputStream* axTcpSocketInputSource::makeStream() const
{
  axWrappedInputStream * w = new axWrappedInputStream(m_fd);
  axBinSocketInputStream* retStrm = new (getMemoryManager()) axBinSocketInputStream(w, getMemoryManager());
  if (!retStrm->getIsOpen())
  {
    delete retStrm;
    return 0;
  }
  return retStrm;
}

XERCES_CPP_NAMESPACE_END


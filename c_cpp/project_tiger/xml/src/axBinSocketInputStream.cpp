/*
 * Copyright 2007 Prem Nallasivampillai
 * 
 */

/*
 * $Log: axBinSocketInputStream.cpp,v $
 * Revision 1.1.1.1  2007/06/01 20:44:20  prem
 * Initial
 *
 * Revision 1.3  2007/05/29 16:37:46  prem
 * Cleaned up code
 *
 * Revision 1.2  2007/05/28 18:38:06  prem
 * Check-point check-in
 *
 */


// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/util/Janitor.hpp>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/XMLExceptMsgs.hpp>
#include <xercesc/util/XMLString.hpp>
#include "axBinSocketInputStream.hpp"

XERCES_CPP_NAMESPACE_BEGIN

// ---------------------------------------------------------------------------
//  axBinSocketInputStream: Constructors and Destructor
// ---------------------------------------------------------------------------
axBinSocketInputStream::axBinSocketInputStream(axWrappedInputStream * source
                                       , MemoryManager* const manager) :

    fSource(source)
  , fMemoryManager(manager)
{
}

axBinSocketInputStream::~axBinSocketInputStream()
{
  if (fSource) {
    delete fSource;
    fSource = NULL;
  }
}


// ---------------------------------------------------------------------------
//  axBinSocketInputStream: Getter methods
// ---------------------------------------------------------------------------
unsigned int axBinSocketInputStream::getSize() const
{
  // return XMLPlatformUtils::fileSize(fSource, fMemoryManager);
  return 0;
}


// ---------------------------------------------------------------------------
//  axBinSocketInputStream: Stream management methods
// ---------------------------------------------------------------------------
void axBinSocketInputStream::reset()
{
  // XMLPlatformUtils::resetFile(fSource, fMemoryManager);
}


// ---------------------------------------------------------------------------
//  axBinSocketInputStream: Implementation of the input stream interface
// ---------------------------------------------------------------------------
unsigned int axBinSocketInputStream::curPos() const
{
  // return XMLPlatformUtils::curFilePos(fSource, fMemoryManager);
  return 0;
}

unsigned int
axBinSocketInputStream::readBytes(XMLByte * const       toFill
                                , const unsigned int    maxToRead)
{
  return (fSource->streamRead((AX_INT8 *) toFill, 0, maxToRead));
}

XERCES_CPP_NAMESPACE_END

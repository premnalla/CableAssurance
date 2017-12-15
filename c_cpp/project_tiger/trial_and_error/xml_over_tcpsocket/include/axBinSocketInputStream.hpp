/*
 * Copyright 2007 Prem Nallasivampillai
 * 
 */

/*
 * $Log: axBinSocketInputStream.hpp,v $
 * Revision 1.3  2007/05/29 16:37:46  prem
 * Cleaned up code
 *
 * Revision 1.2  2007/05/28 18:38:06  prem
 * Check-point check-in
 *
 * Revision 1.1  2007/05/26 16:22:13  prem
 * Initial
 *
 */

#if !defined(_axBinSocketInputStream_hpp_)
#define _axBinSocketInputStream_hpp_

#include <xercesc/util/BinInputStream.hpp>
#include <xercesc/util/PlatformUtils.hpp>
#include "axWrappedInputStream.hpp"

XERCES_CPP_NAMESPACE_BEGIN

class XMLUTIL_EXPORT axBinSocketInputStream : public BinInputStream
{
public :
    // -----------------------------------------------------------------------
    //  Constructors and Destructor
    // -----------------------------------------------------------------------
    axBinSocketInputStream
    (
        axWrappedInputStream * source
      , MemoryManager * const  manager = XMLPlatformUtils::fgMemoryManager
    );

    virtual ~axBinSocketInputStream();


    // -----------------------------------------------------------------------
    //  Getter methods
    // -----------------------------------------------------------------------
    bool getIsOpen() const;
    unsigned int getSize() const;
    void reset();


    // -----------------------------------------------------------------------
    //  Implementation of the input stream interface
    // -----------------------------------------------------------------------
    virtual unsigned int curPos() const;

    virtual unsigned int readBytes
    (
          XMLByte* const      toFill
        , const unsigned int  maxToRead
    );


private :
    // -----------------------------------------------------------------------
    //  Unimplemented constructors and operators
    // -----------------------------------------------------------------------
    axBinSocketInputStream(const axBinSocketInputStream&);
    axBinSocketInputStream& operator=(const axBinSocketInputStream&);   

    // -----------------------------------------------------------------------
    //  Private data members
    //
    //  fSource
    //      The source file that we represent. The FileHandle type is defined
    //      per platform.
    // -----------------------------------------------------------------------
    axWrappedInputStream *  fSource;
    MemoryManager * const   fMemoryManager;
};


// ---------------------------------------------------------------------------
//  axBinSocketInputStream: Getter methods
// ---------------------------------------------------------------------------
inline bool axBinSocketInputStream::getIsOpen() const
{
    return (fSource->isOpen());
}

XERCES_CPP_NAMESPACE_END

#endif // _axBinSocketInputStream_hpp_

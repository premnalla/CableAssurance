/*
 * Copyright 2007 Prem Nallasivampillai
 * 
 */

/*
 * $Log: axTcpSocketInputSource.hpp,v $
 * Revision 1.2  2007/06/03 20:29:24  prem
 * Added a bunch of failure handling code in the socket layer. Added more TRAPs code
 *
 * Revision 1.1.1.1  2007/06/01 20:44:20  prem
 * Initial
 *
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


#if !defined(_axTcpSocketInputSource_hpp_)
#define _axTcpSocketInputSource_hpp_

#include <xercesc/sax/InputSource.hpp>
#include "axSocketFailInterface.hpp"

XERCES_CPP_NAMESPACE_BEGIN

class BinInputStream;

/**
 *  This class is a derivative of the standard InputSource class. It provides
 *  for the parser access to data which is referenced via a stream socket.
 *
 *  As with all InputSource derivatives. The primary objective of an input
 *  source is to create an input stream via which the parser can spool in
 *  data from the referenced source.
 */
class XMLPARSER_EXPORT axTcpSocketInputSource : public InputSource
{
public :
    // -----------------------------------------------------------------------
    //  Constructors and Destructor
    // -----------------------------------------------------------------------

    /** @name Constructors */
    //@{

    /**
      * Constructor.
      *
      * @param  fd          The socket FD.
      *
      */
    axTcpSocketInputSource
    (
        int fd
        , axSocketFailInterface * sfh
        , MemoryManager* const manager = XMLPlatformUtils::fgMemoryManager
    );
    //@}

    /** @name Destructor */
    //@{
    ~axTcpSocketInputSource();
    //@}


    // -----------------------------------------------------------------------
    //  Virtual input source interface
    // -----------------------------------------------------------------------

    /** @name Virtual methods */
    //@{

    /**
    * This method will return a binary input stream derivative that will
    * parse from the local file indicatedby the system id.
    *
    * @return A dynamically allocated binary input stream derivative that
    *         can parse from the file indicated by the system id.
    */
    virtual BinInputStream* makeStream() const;

    //@}

protected:

    /** @name Virtual methods */
    //@{
    axTcpSocketInputSource();
    //@}
    
private:
    // -----------------------------------------------------------------------
    //  Unimplemented constructors and operators
    // -----------------------------------------------------------------------
    axTcpSocketInputSource(const axTcpSocketInputSource&);
    axTcpSocketInputSource& operator=(const axTcpSocketInputSource&);

    int                     m_fd;
    axSocketFailInterface * m_socketFailHandler;
};

XERCES_CPP_NAMESPACE_END

#endif // _axTcpSocketInputSource_hpp_

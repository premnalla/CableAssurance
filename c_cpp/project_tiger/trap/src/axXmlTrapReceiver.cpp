
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <string.h>
#include "axCALog.hpp"
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <xercesc/util/OutOfMemoryException.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMNodeList.hpp>
#include <xercesc/dom/DOMNamedNodeMap.hpp>
#include "axXmlTrapReceiver.hpp"
#include "axWrappedInputStream.hpp"
#include "axCASystemConfig.hpp"
#include "axXmlErrorHandler.hpp"
#include "axTcpSocketInputSource.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
/* XML */
static char*                    gXmlFile               = 0;
static bool                     gDoNamespaces          = false;
static bool                     gDoSchema              = false;
static bool                     gSchemaFullChecking    = false;
static bool                     gDoCreate              = false;
static char*                    goutputfile            = 0;
// options for DOMWriter's features
static XMLCh*                   gOutputEncoding        = 0;
static bool                     gSplitCdataSections    = true;
static bool                     gDiscardDefaultContent = true;
static bool                     gUseFilter             = false;
static bool                     gFormatPrettyPrint     = false;
static bool                     gWriteBOM              = false;
static XercesDOMParser::ValSchemes    gValScheme       = XercesDOMParser::Val_Auto;
static axXmlErrorHandler * errReporter = NULL;
static XercesDOMParser * m_parser = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axXmlTrapReceiver::axXmlTrapReceiver() :
  // m_fd(0), m_parser(NULL)
  m_fd(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axXmlTrapReceiver::~axXmlTrapReceiver()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axXmlTrapReceiver::axXmlTrapReceiver()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axXmlTrapReceiver::run(void)
{
  static const char * myName="axXmlTrapReceiver::run:";

  AX_INT32 ret = 0;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  /* XML Parser init */
  try {
     XMLPlatformUtils::Initialize();
  } catch (const XMLException& toCatch) {
     goto EXIT_LABEL;
  }
  m_parser = new XercesDOMParser;
  m_parser->setValidationScheme(gValScheme);
  m_parser->setDoNamespaces(gDoNamespaces);
  m_parser->setDoSchema(gDoSchema);
  m_parser->setValidationSchemaFullChecking(gSchemaFullChecking);
  m_parser->setCreateEntityReferenceNodes(gDoCreate);
  errReporter = new axXmlErrorHandler();
  m_parser->setErrorHandler(errReporter);

  m_fd = getConnection();

  fd_set readFdSet;
  struct timeval tm;

  while (1) {

    AX_INT32 fd = m_fd;

    FD_ZERO(&readFdSet);
    FD_SET(fd, &readFdSet);
    tm.tv_sec = 30;
    tm.tv_usec = 0;
    int selRc = select(fd+1, &readFdSet, NULL, NULL, &tm);
    if (selRc < 0)  {
      if (errno == EINTR) {
        continue;
      }
      ACE_DEBUG((LM_CRITICAL, "%s select() error\n", myName));
      exit(-1);
      // break;
    } else if (selRc == 0) {
      ACE_DEBUG((LM_DEBUG, "%s select() timeout\n", myName));
    } else {
      readFromFd(fd);
    }

    if (m_fd <= 0) {
      m_fd = getConnection();
    }

  } // while forever

EXIT_LABEL:

  ACE_DEBUG((LM_CRITICAL, "%s exiting worker %d\n", 
                                          myName, pthread_self()));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axXmlTrapReceiver::getConnection(void)
{
  static const char * myName="axXmlTrapReceiver::getConnection:";

  AX_INT32 fd = connectToServer();

  while(fd == 0) {
    sleep(10);
    fd = connectToServer();
  }

  return (fd);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axXmlTrapReceiver::connectToServer(void)
{
  static const char * myName="axXmlTrapReceiver::connect:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  // TODO: the following should come from sysCfg
  char * host = "linux";
  char * port = "9090";

  AX_INT32 fd = 0;
  struct addrinfo * res;
  struct addrinfo hints;
  memset(&hints, 0, sizeof(hints));

  // hints.ai_flags = AI_PASSIVE;
  // hints.ai_family = AF_INET6;
  hints.ai_socktype = SOCK_STREAM;
  int rc = getaddrinfo(host, port, &hints, &res);
  if (rc==0) {
    ACE_DEBUG((LM_DEBUG, "%s getaddrinfo(): success\n", myName));
    for (; res; res = res->ai_next) {
      // printf("family=%d\n", res->ai_family);
      // printf("sock-type=%d\n", res->ai_socktype);
      fd = socket(res->ai_family, res->ai_socktype, 0);
      if (fd <= 0) {
        // failed
      } else {
        // passed
        int conRc = connect(fd, res->ai_addr, res->ai_addrlen);
        if (conRc < 0) {
          // failed
          // printf("connect() - error\n");
          // goto EXIT_ERROR;
          close(fd);
          fd = 0;
        } else {
          // success
          break;
        }
      }
    }
    freeaddrinfo(res);
  } else {
    ACE_DEBUG((LM_CRITICAL, "%s ERROR:%s\n", myName, gai_strerror(rc)));
  }

  return (fd);
} 


//********************************************************************
// method:
//********************************************************************
void
axXmlTrapReceiver::readFromFd(AX_INT32 fd)
{
  static const char * myName="axXmlTrapReceiver::readFromFd:";

  axTcpSocketInputSource s(fd, this);

  try
  {
    errReporter->resetErrors();
    const unsigned long startMillis = XMLPlatformUtils::getCurrentMillis();
    m_parser->parse(s);
    const unsigned long endMillis = XMLPlatformUtils::getCurrentMillis();
    long duration = endMillis - startMillis;
    int errorCount = m_parser->getErrorCount();

    DOMNode * nd = m_parser->getDocument();
    printf("Node Name = %s\n", XMLString::transcode(nd->getNodeName()));
    // m_parser->getDocument();

    DOMNodeList * children = nd->getChildNodes();
    for (int i=0; i<children->getLength(); i++) {
      DOMNode * child = children->item(i);
      printf("Node Name = %s\n", XMLString::transcode(child->getNodeName()));
      DOMNodeList * grandChildren = child->getChildNodes();
      for (int j=0; j<grandChildren->getLength(); j++) {
        DOMNode * grandChild = grandChildren->item(j);
        printf("Node Name = %s\n", XMLString::transcode(grandChild->getNodeName()));
      }
    }

    delete nd;
  }
  catch (const OutOfMemoryException&)
  {
    printf("Out-of-memory exception in parse\n");
  }
  catch (const XMLException& e)
  {
    printf("XML exception in parse\n");
  }

  try {
    /* finally */
    m_parser->reset();
  } catch (...) {
  }

}


//********************************************************************
// method:
//********************************************************************
void
axXmlTrapReceiver::handleReadFailure(AX_INT32 fd)
{
  close(fd);
  m_fd = 0;
}


//********************************************************************
// method:
//********************************************************************
void
axXmlTrapReceiver::handleWriteFailure(AX_INT32 fd)
{
  handleReadFailure(fd);
}



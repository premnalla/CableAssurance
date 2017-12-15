#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <xercesc/util/OutOfMemoryException.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMNodeList.hpp>
#include <xercesc/dom/DOMNamedNodeMap.hpp>
// #include <xercesc/util/OutOfMemoryException.hpp>
// #include <xercesc/util/XMLString.hpp>
// #include <xercesc/framework/StdInInputSource.hpp>
// #include <xercesc/parsers/SAXParser.hpp>
#include "axWrappedInputStream.hpp"
#include "axXmlErrorHandler.hpp"
#include "axTcpSocketInputSource.hpp"

XERCES_CPP_NAMESPACE_USE

#define MAX_FDS 10
#define HOST "localhost"
#define PORT 9090

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
static XercesDOMParser * parser = NULL;
static axXmlErrorHandler * errReporter = NULL;

int myFd = 0;
struct sockaddr_in srvAddr;
struct sockaddr_in clntAddr;
int maxFd = 0;
int readFds[MAX_FDS];

void readClientData(int);
void readFromFds(fd_set *);
void addFd(int *, int);
void removeFd(int *, int);
void setFdSet(int *, fd_set *, fd_set *);
void closeErroredFds(fd_set *, fd_set *);
void printHost(struct sockaddr *, size_t);

int main(int argc, char * argv[])
{

  /* XML Parser init */
  try {
     XMLPlatformUtils::Initialize();
  } catch (const XMLException& toCatch) {
     return -1;
  }
  parser = new XercesDOMParser;
  parser->setValidationScheme(gValScheme);
  parser->setDoNamespaces(gDoNamespaces);
  parser->setDoSchema(gDoSchema);
  parser->setValidationSchemaFullChecking(gSchemaFullChecking);
  parser->setCreateEntityReferenceNodes(gDoCreate);
  errReporter = new axXmlErrorHandler();
  parser->setErrorHandler(errReporter);

  myFd = socket(AF_INET, SOCK_STREAM, 0);
  if (myFd < 0) {
    printf("socket() call failed\n");
    goto EXIT_ERROR;
  }

  memset(&srvAddr, 0, sizeof(srvAddr));
  srvAddr.sin_family = AF_INET;
  srvAddr.sin_addr.s_addr = htonl(INADDR_ANY);
  srvAddr.sin_port = htons(PORT);

  int bindRc = bind(myFd, (struct sockaddr *) &srvAddr, sizeof(srvAddr));
  if (bindRc < 0) {
    printf("socket() call failed\n");
    goto EXIT_ERROR;
  }

  listen(myFd, 5);

  fd_set readFdSet;
  fd_set writeFdSet;
  fd_set errorFdSet;
  struct timeval tm;
  memset(&readFds, 0, sizeof(readFds));
  maxFd = myFd;
  addFd(readFds, myFd);

  printHost((struct sockaddr *) &srvAddr, sizeof(srvAddr));

  /*
   * Forever
   */
  while (1) {
    FD_ZERO(&readFdSet);
    FD_ZERO(&writeFdSet);
    FD_ZERO(&errorFdSet);
    setFdSet(readFds, &readFdSet, &errorFdSet);
    tm.tv_sec = 5;
    tm.tv_usec = 0;
    int selRc = select(maxFd+1, &readFdSet, &writeFdSet, &errorFdSet, &tm);
    if (selRc < 0)  {
      printf("select() error\n");
    } else if (selRc == 0) {
      printf("select() timeout\n");
    } else {
      closeErroredFds(&readFdSet, &errorFdSet);
      readFromFds(&readFdSet);
    }
  }

  goto EXIT_LABEL;
  
EXIT_ERROR:
  printf("Error. exitting\n");

EXIT_LABEL:

  return (0);
}


void setFdSet(int * fds, fd_set * readFdSet, fd_set * errFdSet)
{
  for (int i=0; i<MAX_FDS; i++) {
    if (fds[i]) {
      FD_SET(fds[i], readFdSet);
      FD_SET(fds[i], errFdSet);
    }
  }
}


void addFd(int * fds, int fd)
{
  printf("adding %d\n", fd);

  for (int i=0; i<MAX_FDS; i++) {
    if (fds[i] == fd) {
      break;
    } else if (!fds[i]) {
      fds[i] = fd;
      break;
    }
  }
}

void removeFd(int * fds, int fd)
{
  printf("removing %d\n", fd);

  for (int i=0; i<MAX_FDS; i++) {
    if (fds[i] == fd) {
      close(fd);
      fds[i] = 0;
      break;
    }
  }
}

void readFromFds(fd_set * readFdSet)
{
  /*
   * New connection
   */
  if (FD_ISSET(myFd, readFdSet)) {
    int clientLen = sizeof(clntAddr);
    int acceptRc = accept(myFd, (sockaddr *) &clntAddr, (socklen_t *) &clientLen);
    if (acceptRc < 0) {
      printf("accept() error\n");
    } else {
      printf("Accepted new connection - %d\n", acceptRc);
#if 0
      unsigned int tmpAddr = clntAddr.sin_addr.s_addr;
      tmpAddr = ntohl(tmpAddr);
      struct in_addr pzTmpAddr;
      pzTmpAddr.s_addr = tmpAddr;
      printf("HOST %s\n", inet_ntoa(pzTmpAddr));
#else
      printf("HOST %s\n", inet_ntoa(clntAddr.sin_addr));
      printHost((struct sockaddr *) &clntAddr, sizeof(clntAddr));
#endif
      addFd(readFds, acceptRc);
      if (maxFd < acceptRc) {
        maxFd = acceptRc;
      }
    }
  }

  for (int i=1; i<MAX_FDS; i++) {
    if (readFds[i]) {
      if (FD_ISSET(readFds[i], readFdSet)) {
        readClientData(readFds[i]);
      }
    }
  }
}

void closeErroredFds(fd_set * readFdSet, fd_set * erroredFdSet)
{
  printf("closeErroredFds: entry\n");
  for (int i=1; i<MAX_FDS; i++) {
    if (readFds[i]) {
      if (FD_ISSET(readFds[i], erroredFdSet)) {
        printf("closeErroredFds: found match\n");
        FD_CLR(readFds[i], readFdSet);
        close(readFds[i]);
        readFds[i] = 0;
      }
    }
  }
  printf("closeErroredFds: exit\n");
}

#if 1
void readClientData(int fd) 
{
  char buffer[1024];
  printf("reading from %d\n", fd);
  int bytesRead = read(fd, buffer, sizeof(buffer));
  if (bytesRead <= 0) {
    removeFd(readFds, fd);
  } else {
    buffer[bytesRead] = '\0';
    printf("Bytes read = %d\n", bytesRead);
    printf("READ: %s\n", buffer);
  }
}
#endif

#if 0
void readClientData(int fd) 
{
  // axWrappedInputStream in(fd);
  axTcpSocketInputSource s(fd);

  try
  {
    errReporter->resetErrors();
    const unsigned long startMillis = XMLPlatformUtils::getCurrentMillis();
    parser->parse(s);
    const unsigned long endMillis = XMLPlatformUtils::getCurrentMillis();
    long duration = endMillis - startMillis;
    int errorCount = parser->getErrorCount();

#if 1
    DOMNode * nd = parser->getDocument();
    printf("Node Name = %s\n", XMLString::transcode(nd->getNodeName()));
    // parser->getDocument();

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
#endif

  }
  catch (const OutOfMemoryException&)
  {
    printf("Out-of-memory exception in parse\n");
  }
  catch (const XMLException& e)
  {
    printf("XML exception in parse\n");
  }

  if (errReporter->isErrored()) {
    removeFd(readFds, fd);
  }

  try {
    /* finally */
    parser->reset();
  } catch (...) {
  }

  // in.streamClose();
}
#endif


#if 0
void readClientData(int fd) 
{
  printf("Reading from fd %d\n", fd);
  char buffer[1024];
  buffer[0] = '\0';
  axWrappedInputStream in(fd);
  int nRead = in.streamRead(buffer, 0, sizeof(buffer));
  printf("readClientData: BytesRead = %d\n", nRead);
  buffer[nRead] = '\0';
  printf("READ: %s\n", buffer);
  in.streamClose();
  if (nRead <= 0) {
    removeFd(readFds, fd);
  }
}
#endif


void printHost(struct sockaddr * addr, size_t addrLen)
{
  char hostBuf[128];

  /*
   *
   */
  int flags = NI_NAMEREQD;

  int rc = getnameinfo(addr, addrLen, hostBuf, sizeof(hostBuf),
             NULL, 0, flags);

  if (!rc) {
    // success
    printf("HostName = %s\n", hostBuf);
  } else {
    /*
     * DNS lookup didn't succeed to get hostname. Try to get numeric
     * form of the IP address (i.e., 1.1.1.1 or ff:ff:...)
     */
    flags = NI_NUMERICHOST;
    rc = getnameinfo(addr, addrLen, hostBuf, sizeof(hostBuf),
             NULL, 0, flags);
    if (!rc) {
      // success
      printf("Numeric host = %s\n", hostBuf);
    } else {
      // error
      printf("Error resolving name\n");
      goto EXIT_LABEL;
    }
  }

EXIT_LABEL:

  return;
}




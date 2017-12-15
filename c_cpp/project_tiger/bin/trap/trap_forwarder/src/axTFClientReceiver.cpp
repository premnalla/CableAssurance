
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include "axCALog.hpp"
#include "axTFClientReceiver.hpp"
#include "axDbBlade.hpp"
#include "axTopoLookupTables.hpp"
#include "axTopoBladeContainer.hpp"
#include "axReadLockOperator.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

// TEMPORARY: needs to go in TrapFwdSystemConfig
#define PORT 9020

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
axTFClientReceiver::axTFClientReceiver() :
  m_exit(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axTFClientReceiver::~axTFClientReceiver()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTFClientReceiver::axTFClientReceiver()
// {
// }


//********************************************************************
// method: main loop
//********************************************************************
AX_INT32
axTFClientReceiver::run(void)
{
  static const char * myName="axTFClientReceiver::run:";

  axTopoLookupTables * clntTbl = axTopoLookupTables::getInstance();

  int bindRc;
  fd_set readFdSet;
  // fd_set writeFdSet;
  // fd_set exceptFdSet;
  struct timeval timeout;
  int count;

  pthread_t thrId = getWorkerId();

  if (thrId != pthread_self()) {
    ACE_ERROR((LM_ERROR, "ACE_thread_t != pthread_t\n"));
  }

  struct sockaddr_in srvAddr;
  struct sockaddr_in clntAddr;
  int    fdList[MAX_CLIENTS];
  int myFd = 0;
  int maxFd = myFd;

  myFd = socket(AF_INET, SOCK_STREAM, 0);
  if (myFd < 0) {
    goto EXIT_LABEL;
  }

  maxFd = myFd;

  memset(&srvAddr, 0, sizeof(srvAddr));
  srvAddr.sin_family = AF_INET;
  srvAddr.sin_addr.s_addr = htonl(INADDR_ANY);
  srvAddr.sin_port = htons(PORT);

  bindRc = bind(myFd, (struct sockaddr *) &srvAddr, sizeof(srvAddr));
  if (bindRc < 0) {
    goto EXIT_LABEL;
  }

  addClient(myFd, (struct sockaddr *) &srvAddr, sizeof(srvAddr));

  listen(myFd, 5);

  while(!m_exit) {

    FD_ZERO(&readFdSet);
    // FD_ZERO(&writeFdSet);
    // FD_ZERO(&exceptFdSet);
    memset(&timeout, 0, sizeof(timeout));

    setFdSet(&readFdSet);
    timeout.tv_sec = 5;

    // count = select(maxFd+1, &readFdSet, NULL, NULL, &timeout);
    count = select(maxFd+1, &readFdSet, NULL, NULL, NULL);

    if (count > 0) {
      if (FD_ISSET(myFd, &readFdSet)) {
        // accept new client
        int clientLen = sizeof(clntAddr);
        int clientFd = accept(myFd, (sockaddr *) &clntAddr, 
                                              (socklen_t *) &clientLen);
        if (clientFd < 0) {
          ACE_DEBUG((LM_ERROR, "%s accept() error\n", myName));
        } else {
          ACE_DEBUG((LM_DEBUG, "%s accepted new client\n", myName));
          addClient(clientFd, (struct sockaddr *) &clntAddr, 
                                                      sizeof(clntAddr));
          if (maxFd < clientFd) {
            maxFd = clientFd;
          }
        }
      }

      clntTbl->getFdSet(fdList, sizeof(fdList), &readFdSet);
      for (int i=1; i<MAX_CLIENTS; i++) {
        if (!fdList[i]) {
          break;
        }
        // DO SOMETHING with fdList
        // readClientData(readFds[i]);
      }

    } else {
        switch (count) {
        case 0:
            // timeout
            break;
        case -1:
            if (errno == EINTR) {
                continue;
            }
            ACE_DEBUG((LM_CRITICAL, "%s select returned %d\n", myName, count));
            m_exit = true;
            break;
        default:
            ACE_DEBUG((LM_CRITICAL, "%s select returned %d\n", myName, count));
            m_exit = true;
            break;
        }
    }

  } // while

EXIT_LABEL:

  ACE_DEBUG((LM_CRITICAL, "%s thread %d exitting!", myName, (int)thrId));

  return (0);
}


//********************************************************************
// method: 
//********************************************************************
void
axTFClientReceiver::addClient(int fd, struct sockaddr * addr, 
                                                       size_t addrLen)
{
  axTopoLookupTables * clntTbl = axTopoLookupTables::getInstance();

  axTopoBladeContainer * bldContainer;

  axDbBlade dbBld;
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
      goto EXIT_LABEL;
    }
  }

  dbBld.m_host = hostBuf;
  if (!dbBld.getRow()) {
    goto EXIT_LABEL;
  }

  bldContainer = (axTopoBladeContainer *) 
                                   clntTbl->findContainer(dbBld.m_id);

  if (!bldContainer) {
    bldContainer = new axTopoBladeContainer(fd, dbBld);
    clntTbl->addContainer(bldContainer);
  } else {
    bldContainer->setContainerFd(fd);
  }

EXIT_LABEL:

  return;
}


//********************************************************************
// method: 
//********************************************************************
void 
axTFClientReceiver::setFdSet(fd_set * readFdSet)
{
  axTopoLookupTables * clntTbl = axTopoLookupTables::getInstance();
  clntTbl->setFdSet(readFdSet);
}


//********************************************************************
// method:
//********************************************************************
void
axTFClientReceiver::handleReadFailure(AX_INT32 fd)
{
  axTopoLookupTables * clntTbl = axTopoLookupTables::getInstance();
  axReadLockOperator wLock(clntTbl->getContainerList()->getLock());
  axTopoBladeContainer * bldCont = (axTopoBladeContainer *)
     clntTbl->findContainerExtLock(fd);
  if (bldCont) {
    close(fd);
    bldCont->clearContainerFd();
  }
}


//********************************************************************
// method:
//********************************************************************
void
axTFClientReceiver::handleWriteFailure(AX_INT32 fd)
{
  handleReadFailure(fd);
}



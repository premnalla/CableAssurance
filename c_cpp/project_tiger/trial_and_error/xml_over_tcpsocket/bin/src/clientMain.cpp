#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include "axWrappedInputStream.hpp"
#include "axWrappedOutputStream.hpp"

#define MAX_FDS 1
#define HOST "192.168.1.104"
#define PORT 9090

int myFd = 0;
struct sockaddr_in srvAddr;
struct sockaddr_in clntAddr;
int maxFd = 0;
int readFds[MAX_FDS];

void removeFd(int * fds, int fd);
void readClientData(int);
void readFromFds(fd_set *);
void addFd(int *, int);
void setFdSet(int *, fd_set *, fd_set *);
void closeErroredFds(fd_set * readFdSet, fd_set * erroredFdSet);

int main(int argc, char * argv[])
{

  memset(&srvAddr, 0, sizeof(srvAddr));
  srvAddr.sin_family = AF_INET;
  // srvAddr.sin_addr.s_addr = inet_addr(HOST);
  inet_aton(HOST, &srvAddr.sin_addr);
  srvAddr.sin_port = htons(PORT);

  std::string out("<?xml version=\"1.0\" ?>");

  myFd = socket(AF_INET, SOCK_STREAM, 0);
  if (myFd < 0) {
    printf("socket() - error\n");
    goto EXIT_ERROR;
  }

  int connectRc = connect(myFd, (struct sockaddr *) &srvAddr, sizeof(srvAddr));
  if (connectRc < 0) {
    printf("connect() - error\n");
    goto EXIT_ERROR;
  }

  out.append("<foo>");
  out.append("<bar>foo</bar>");
  out.append("</foo>");

  fd_set readFdSet;
  fd_set writeFdSet;
  fd_set errorFdSet;
  struct timeval tm;
  memset(&readFds, 0, sizeof(readFds));
  maxFd = myFd;
  addFd(readFds, myFd);

  /*
   * Forever
   */
  while (1) {

    printf("About to write = %s - %d\n", out.c_str(), out.size());

#if 0
    axWrappedOutputStream o(myFd);
    int bytesWrote = o.streamWrite(const_cast<AX_INT8 *> (out.c_str()), 0, out.size());
    // printf("Bytes wrote = %d\n", bytesWrote);
    o.streamClose();
#else
    // int bytesWrote = write(myFd, out.c_str(), out.size());
    int writeFd = readFds[0];
    if (writeFd) {
      int bytesWrote = write(writeFd, out.c_str(), out.size());
      printf("Bytes wrote = %d\n", bytesWrote);
    }
#endif
    
    FD_ZERO(&readFdSet);
    FD_ZERO(&writeFdSet);
    FD_ZERO(&errorFdSet);
    setFdSet(readFds, &readFdSet, &errorFdSet);
    tm.tv_sec = 5;
    tm.tv_usec = 0;
    int selRc = select(maxFd+1, &readFdSet, NULL, &errorFdSet, &tm);
    if (selRc < 0)  {
      printf("select() error\n");
      exit(-1);
    } else if (selRc == 0) {
      printf("select() timeout\n");
    } else {
      closeErroredFds(&readFdSet, &errorFdSet);
      readFromFds(&readFdSet);
    }

    // break;
  }

  close(myFd);

  goto EXIT_LABEL;

EXIT_ERROR:
  printf("Error. exitting\n");

EXIT_LABEL:

  return (0);
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

void setFdSet(int * fds, fd_set * readFdSet, fd_set * errFdSet)
{
  for (int i=0; i<MAX_FDS; i++) {
    if (fds[i]) {
      FD_SET(fds[i], readFdSet);
      FD_SET(fds[i], errFdSet);
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

void readFromFds(fd_set * readFdSet)
{
  for (int i=0; i<MAX_FDS; i++) {
    if (readFds[i]) {
      if (FD_ISSET(readFds[i], readFdSet)) {
        readClientData(readFds[i]);
      }
    }
  }
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


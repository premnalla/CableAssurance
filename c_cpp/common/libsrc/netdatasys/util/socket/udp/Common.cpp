/* */

#include <unistd.h>
#include <stdio.h>
// #include <sys/types.h>
// #include <sys/socket.h>
// #include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <string.h>

#include "Common.h"

int
get_socket(void)
{
  int socket_fd = socket(AF_INET, SOCK_DGRAM, 0);

  if (socket_fd < 0) {
    perror("socket() failed");
    exit(-1);
  }

#if 0

  int val = 1;

  int rc = setsockopt(socket_fd, IPPROTO_TCP, TCP_NODELAY, (char *) &val, sizeof(val));
  if (rc < 0) {
    perror("setsockopt() failed for TCP_NODELAY");
  }

  rc = setsockopt(socket_fd, SOL_SOCKET, SO_KEEPALIVE, (char *) &val, sizeof(val));
  if (rc < 0) {
    perror("setsockopt() failed for SO_KEEPALIVE");
  }

  struct linger so_linger;
  memset(&so_linger, 0, sizeof(so_linger));
  so_linger.l_onoff = 1;
  so_linger.l_linger = 1;

  rc = setsockopt(socket_fd, SOL_SOCKET, SO_LINGER, (char *) &so_linger, sizeof(so_linger));
  if (rc < 0) {
    perror("setsockopt() failed for SO_LINGER");
  }

#endif

  return (socket_fd);
}

void
init_socket_addr(struct sockaddr_in & server_addr, const char * host_name, int server)
{
  memset(&server_addr, 0, sizeof(server_addr));
  server_addr.sin_family      = AF_INET;

#if 0

  if (!host_name) {
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
  } else {
    server_addr.sin_addr.s_addr = inet_addr(host_name);
  }

#else

  server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

#endif


#if 0

  server_addr.sin_port        = htons(SERVER_PORT);

#else

  if (server)  {
    server_addr.sin_port        = htons(SERVER_PORT);
  } else {
    server_addr.sin_port        = htons(0);
  }

#endif

}


int
bind_socket(int socket_fd, const char * host_name, int server)
{
  struct sockaddr_in server_addr;

  init_socket_addr(server_addr, host_name, server);

  int rc = bind(socket_fd, (struct sockaddr *) &server_addr, sizeof(server_addr));

  if (rc < 0) {
    perror("bind() failed");
    exit(-1);
  }

  return (rc);
}

int
listen_on_socket(int socket_fd)
{
  int rc = listen(socket_fd, 5);

  if (rc < 0) {
    perror("listen() failed");
    exit(-1);
  }

  return (rc);
}

int
accept_on_socket(int socket_fd)
{
  int clnt_len;

  struct sockaddr_in clnt_addr;
  memset(&clnt_addr, 0, (clnt_len=sizeof(clnt_addr)));

  int clnt_fd = accept(socket_fd, (struct sockaddr *) &clnt_addr, &clnt_len);

  if (clnt_fd < 0) {
    perror("accept() failed");
    exit(-1);
  }

  return (clnt_fd);
}

int
connect_to_socket(int socket_fd)
{
  struct sockaddr_in server_addr;
  init_socket_addr(server_addr, SERVER_HOST, 0);

  int rc = connect(socket_fd, (struct sockaddr *) &server_addr, sizeof(server_addr));

  if (rc < 0) {
    perror("accept() failed");
    exit(-1);
  }

  return (rc);
}

int
send_msg_to_socket(int socket_fd, const char * msg, struct sockaddr_in & dest_addr)
{
  int rc;
  int len;

  int dest_len = sizeof(dest_addr);

  // This is UDP remember
  // rc = write(socket_fd, msg, (len=strlen(msg)+1));
  rc = sendto(socket_fd, msg, (len=strlen(msg)+1), 0, (struct sockaddr *) &dest_addr, dest_len);

  if (rc < 0) {
    perror("write() failed");
  }

  return (rc);
}

char *
get_msg_from_socket(int socket_fd, struct sockaddr_in & src_addr)
{
  static const int max_len = 64;

  static char buffer[max_len];

  char * ret = NULL;

  int src_len = sizeof(src_addr);
  memset(&src_addr, 0, (src_len=sizeof(src_addr)));

  // This is UDP remember!
  // int rc = read(socket_fd, buffer, max_len);
  int rc = recvfrom(socket_fd, buffer, max_len, 0, (struct sockaddr *) &src_addr, &src_len);

  if (rc < 0) {
    perror("read() failed");
    exit(-1);
  }

  if (rc) {
    ret = buffer;
  }

  return (ret);
}



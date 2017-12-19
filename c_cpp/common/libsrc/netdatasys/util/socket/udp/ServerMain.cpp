/* */

#include <unistd.h>
#include <stdio.h>
// #include <sys/types.h>
// #include <sys/socket.h>
// #include <netinet/in.h>
#include <sys/time.h>
#include <arpa/inet.h>
#include <string.h>

#include "Common.h"


static const int MAX_FDS = 100;
int fd_array[MAX_FDS];

static void 
set_fd(int fd)
{
  for (int i=0; i<MAX_FDS; i++) {
    if (!fd_array[i]) {
      fd_array[i] = fd;
      break;
    }
  }
}

static void
clear_fd(int fd)
{
  for (int i=0; i<MAX_FDS; i++) {
    if (fd_array[i] == fd) {
      fd_array[i] = 0;
      break;
    }
  }
}

int
main(int argc, char * argv[])
{
  memset(fd_array, 0, sizeof(fd_array));

  // int client_len;
  struct sockaddr_in client_addr;
  // init_socket_addr(client_addr, SERVER_HOST, 0);
  // memset(&client_addr, 0, (client_len=sizeof(client_addr)));

  int my_fd = get_socket();

  int rc = bind_socket(my_fd);

  // This is UDP
  // rc = listen_on_socket(my_fd);

#if 0

  int client_fd = accept_on_socket(my_fd);

  while (1) {
    char * str = get_msg_from_socket(client_fd);
    if (str) {
      fprintf(stderr, "%s\n", str);
    }
    // close(my_fd);
    // close(client_fd);
    // break;
  }

#else

  fd_set my_read_fdset_in;
  fd_set my_error_fdset_in;

  fd_set my_read_fdset_out;
  fd_set my_error_fdset_out;

  FD_ZERO(&my_read_fdset_in);
  FD_SET(my_fd, &my_read_fdset_in);

  FD_ZERO(&my_error_fdset_in);
  FD_SET(my_fd, &my_error_fdset_in);

  struct timeval my_timeout;
  memset(&my_timeout, 0, sizeof(my_timeout));
  my_timeout.tv_sec = 1;

  int num_set_fds = my_fd + 1;

  fprintf(stderr, "my_fd = %d\n", my_fd);

  while (1) {

    my_read_fdset_out = my_read_fdset_in;
    my_error_fdset_out = my_error_fdset_in;

    int num_ready_fds = 
      select(num_set_fds, &my_read_fdset_out, NULL, &my_error_fdset_out, &my_timeout);

    if (num_ready_fds < 0) {
      perror("select() failed\n");
      exit(-1);
    }

    if (!num_ready_fds) {
      continue;
    }

    fprintf(stderr, "Num FD's to read = %d\n", num_ready_fds);

    if (FD_ISSET(my_fd, &my_read_fdset_out)) {

      fprintf(stderr, "data on my_fd\n");

#if 0

      int client_fd = accept_on_socket(my_fd);
      FD_SET(client_fd, &my_read_fdset_in);
      set_fd(client_fd);

      if (client_fd > my_fd) {
        num_set_fds = client_fd + 1;
      }

      fprintf(stderr, "new client_fd = %d\n", client_fd);

#else

        char * str = get_msg_from_socket(my_fd, client_addr);

        if (str) {
          // fprintf(stderr, "%s\n", str);
          send_msg_to_socket(my_fd, str, client_addr);
        } else {
          // FD_CLR(fd_array[i], &my_read_fdset_in);
          // close(fd_array[i]);
          // clear_fd(fd_array[i]);
        }

#endif

    }

#if 0

    for (int i=0; i<MAX_FDS; i++) {
      if (fd_array[i] &&
          FD_ISSET(fd_array[i], &my_read_fdset_out)) {
        char * str = get_msg_from_socket(fd_array[i]);
        if (str) {
          fprintf(stderr, "%s\n", str);
        } else {
          FD_CLR(fd_array[i], &my_read_fdset_in);
          close(fd_array[i]);
          clear_fd(fd_array[i]);
        }
      }
    }

    for (int i=0; i<MAX_FDS; i++) {
      if (fd_array[i] &&
          FD_ISSET(fd_array[i], &my_error_fdset_out)) {
        FD_CLR(fd_array[i], &my_error_fdset_in);
        close(fd_array[i]);
        clear_fd(fd_array[i]);
        // --num_set_fds;
      }
    }

#endif

  } // while (1)

#endif

  return (0);
}



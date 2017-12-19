/* */

#include <unistd.h>
#include <stdio.h>
#include <signal.h>
#include <string.h>

#include "Common.h"


int my_fd = 0;

void
signal_handler(int sig_no)
{
  fprintf(stderr, "\nsignal_handler() called for signal %d\n", sig_no);
  close(my_fd);
  my_fd = 0;
  exit(0);
}

void
setup_signal_handlers(void)
{
  struct sigaction act;
  memset(&act, 0, sizeof(act));

  sigemptyset(&act.sa_mask);
  act.sa_handler = signal_handler;
  sigaction(SIGINT, &act, NULL);
}

int
main(int argc, char * argv[])
{
  setup_signal_handlers();

  struct sockaddr_in server_addr;
  init_socket_addr(server_addr, NULL, 1);

  my_fd = get_socket();

  int rc = bind_socket(my_fd, SERVER_HOST, 0);

  // This is UDP
  // int rc = connect_to_socket(my_fd);

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
  my_timeout.tv_sec = 10;

  int num_set_fds = my_fd + 1;

  // fprintf(stderr, "my_fd = %d\n", my_fd);

  char buffer[64];

  int loop = 1;

  while (1) {

    my_read_fdset_out = my_read_fdset_in;
    my_error_fdset_out = my_error_fdset_in;

    sprintf(buffer, "%d", loop++);
    
    int rc = send_msg_to_socket(my_fd, buffer, server_addr);

    // fprintf(stderr, "send msg: %s\n", buffer);

    int num_ready_fds =
      select(num_set_fds, &my_read_fdset_out, NULL, &my_error_fdset_out, &my_timeout);

    if (num_ready_fds < 0) {
      perror("select() failed\n");
      exit(-1);
    }

    if (!num_ready_fds) {
      continue;
    }

    // fprintf(stderr, "Num FD's to read = %d\n", num_ready_fds);

    if (FD_ISSET(my_fd, &my_read_fdset_out)) {

      // fprintf(stderr, "data on my_fd\n");

      char * str = get_msg_from_socket(my_fd, server_addr);

      if (str) {
        fprintf(stderr, "%s\n", str);
      } else {
        // FD_CLR(fd_array[i], &my_read_fdset_in);
        // close(fd_array[i]);
        // clear_fd(fd_array[i]);
      }

    }

    sleep(2);

  } // while (1)

  return (0);
}



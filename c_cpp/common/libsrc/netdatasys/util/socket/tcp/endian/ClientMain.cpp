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

  my_fd = get_socket();

  int rc = connect_to_socket(my_fd);

  char buffer[64];

  int loop = 90;

  fd_set my_read_fdset_in;
  FD_ZERO(&my_read_fdset_in);
  FD_SET(my_fd, &my_read_fdset_in);

  fd_set my_error_fdset_in;
  FD_ZERO(&my_error_fdset_in);
  FD_SET(my_fd, &my_error_fdset_in);

  struct timeval my_timeout;
  memset(&my_timeout, 0, sizeof(my_timeout));
  my_timeout.tv_sec = 1;

  while (1) {

    sprintf(buffer, "%d", loop++);
    
    fprintf(stderr, "Sending msg: %s\n", buffer);
    int rc = send_msg_to_socket(my_fd, buffer);

    int num_ready_fds =
      select(my_fd+1, &my_read_fdset_in, NULL, &my_error_fdset_in, &my_timeout);

    if (num_ready_fds < 0) {
      perror("select() failed\n");
      exit(-1);
    }

    if (!num_ready_fds) {
      continue;
    }

    fprintf(stderr, "Num FD's to read = %d\n", num_ready_fds);

    if (FD_ISSET(my_fd, &my_read_fdset_in)) {
      char * str = get_msg_from_socket(my_fd);
      if (str) {
        fprintf(stderr, "Got mesg: %s\n", str);
      } else {
        fprintf(stderr, "Closing socket\n");
        FD_CLR(my_fd, &my_read_fdset_in);
        close(my_fd);
        // clear_fd(my_fd);
      }
    }

    if (FD_ISSET(my_fd, &my_error_fdset_in)) {
      FD_CLR(my_fd, &my_error_fdset_in);
      close(my_fd);
      // clear_fd(my_fd);
      // --num_set_fds;
    }

    // sleep(2);
    sleep(1);

    // close(my_fd);

    // break;
  }

  return (0);
}



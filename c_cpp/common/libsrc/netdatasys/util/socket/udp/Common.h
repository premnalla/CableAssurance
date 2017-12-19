/* */

#if !defined(_Common_h_)
#define _Common_h_

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

// #define SERVER_PORT 1000
#define SERVER_PORT 10000
#define SERVER_HOST "192.168.8.26"

extern int get_socket(void);
extern void init_socket_addr(struct sockaddr_in & server_addr, const char * host_name, int server);
extern int bind_socket(int socket_fd, const char * host_name=NULL, int server=1);
extern int listen_on_socket(int socket_fd);
extern int accept_on_socket(int socket_fd);
extern int connect_to_socket(int socket_fd);
extern int send_msg_to_socket(int socket_fd, const char * msg, struct sockaddr_in &);
extern char * get_msg_from_socket(int socket_fd, struct sockaddr_in &);

#endif /* _Common_h_ */

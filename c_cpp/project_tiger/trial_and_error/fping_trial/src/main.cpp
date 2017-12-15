
#include <string.h>
#include <stdio.h>
#include <pthread.h>
#include "myfping3.hpp"

#define MAX_THREADS 5

void *
thread_start_function(void * arg)
{
  // pthread_t workerId = *((pthread_t *) arg);
  pthread_t workerId = pthread_self();
  // int workerId = *((int *) arg);
  // fprintf(stderr, "In thread %d\n", workerId);
  myfping3 m;
  m.setThreadId(workerId);
  m.run();

  return (NULL);
}


int
main(int argc, char * argv[])
{
  pthread_t       workerId;
  pthread_attr_t  workerProperties;
  int intThrId[MAX_THREADS];

  memset(&workerId, 0, sizeof(workerId));
  pthread_attr_init(&workerProperties);

  for (int i=0; i<MAX_THREADS; i++) {
    intThrId[i]=i;
    int ret = pthread_create(&workerId, &workerProperties,
                             thread_start_function, &intThrId[i]);

    if (ret != 0) {
      goto EXIT_LABEL;
    }

    fprintf(stderr, "created thread %d\n", workerId);
  }

  while (1) {
  }

EXIT_LABEL:

  return (0);
}

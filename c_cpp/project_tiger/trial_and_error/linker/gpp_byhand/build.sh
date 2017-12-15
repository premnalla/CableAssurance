#!/bin/sh

/bin/rm foo foo.o main.o libfoo.so* > /dev/null

#The following works!!!
#g++ -c -fPIC foo.cpp -o foo.o

#However, the following doesn't work!!!
#The -fvisibility=hidden is the KILLER
g++ -fvisibility=hidden -W -Wall -Wpointer-arith -DLINUX -O3 -g -pipe    -D_REENTRANT -Wno-missing-field-initializers -c -fPIC foo.cpp -o foo.o

g++ -c main.cpp -o main.o
g++ -shared -Wl,-soname,libfoo.so.1 -o libfoo.so.1 foo.o
ln -s libfoo.so.1 libfoo.so
g++ -o foo -L. main.o -lfoo


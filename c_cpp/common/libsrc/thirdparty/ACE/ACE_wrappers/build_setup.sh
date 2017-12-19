#!/bin/sh
autoreconf -I m4 --install --force
mkdir build
cd build
#../configure --enable-ipv4-ipv6 --enable-prof
#../configure --enable-ipv4-ipv6 --enable-static=yes --enable-shared=no
../configure --enable-ipv4-ipv6 --enable-shared=yes --enable-static=no
#../configure --enable-ipv4-ipv6 --enable-shared=yes
# edit ace/SSL/Makefile
# change DEFAULT_INCLUDES to also include -I$(top_builddir)/../../../openssl/openssl/include

#!/bin/sh
./configure
# edit soapcpp2/Makefile add -DWITH_NONAMESPACES to CXXFLAGS and CFLAGS
cd soapcpp2
gmake

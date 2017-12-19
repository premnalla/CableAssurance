###############################################################################
##
##   Copyright (C) 2000 by Thomas M. Hazel, txObject ATK (www.tobject.org)
##
##                           All Rights Reserved
##
##   This program is free software; you can redistribute it and/or modify it
##   under the terms of the GNU General Public License as published by the
##   Free Software Foundation; either version 2, or (at your option) any
##   later version.
##
##   This program is distributed in the hope that it will be useful,
##   but WITHOUT ANY WARRANTY; without even the implied warranty of
##   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
##   GNU General Public License for more details.
##
##   You should have received a copy of the GNU General Public License
##   along with this program; if not, write to the Free Software
##   Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
##
###############################################################################

include ../../build.defs

OBJS = \
	txtest_tcp1.cxx \
	$(ENDOFLIST)

all:
	$(CCC) $(CFLAGS) -I../inc -I../../layer1/inc \
	-I../../layer2/inc -I../../layer3/inc $(OBJS) \
	../lib/liblayer4.a  ../../layer3/lib/liblayer3.a \
	../../layer2/lib/liblayer2.a ../../layer1/lib/liblayer1.a $(SYS_LIBS)

clean:
	rm -f *.o
	rm -f a.out
	rm -rf $(OS_TEMPORARY)

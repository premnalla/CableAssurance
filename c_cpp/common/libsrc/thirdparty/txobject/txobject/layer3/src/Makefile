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
	event.cxx \
	txevent.cxx \
	txeventq.cxx \
	txlock.cxx \
	txthrdbase.cxx \
	txthrdmgr.cxx \
	txthrdss.cxx \
	txthread.cxx \
	thrdmgr.cxx \
	thrdplist.cxx \
	thread.cxx \
	thrdstats.cxx \
	$(ENDOFLIST)

all:
	$(CCC) $(CFLAGS) -I../inc -I../src \
	-I../../layer1/inc -I../../layer2/inc $(OBJS) -c

	$(ARCHIVE) ../lib/liblayer3.a *.o

	$(RANLIB) ../lib/liblayer3.a

clean:
	rm -f *.o
	rm -f ../lib/*.a
	rm -rf $(OS_TEMPORARY)

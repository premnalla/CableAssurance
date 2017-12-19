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
	txboolean.cxx \
	txbtree.cxx \
	txchar16.cxx \
	txchar8.cxx \
	txdouble.cxx \
	txfactory.cxx \
	txfloat.cxx \
	txgobject.cxx \
	txhashmap.cxx \
	txhashtable.cxx \
	txin.cxx \
	txint16.cxx \
	txint32.cxx \
	txint64.cxx \
	txint8.cxx \
	txlist.cxx \
	txobject.cxx \
	txobjref.cxx \
	txostring.cxx \
	txout.cxx \
	txrefobj.cxx \
	txsmrtbuf.cxx \
	txstream.cxx \
	txstring.cxx \
	txsyslist.cxx \
	txtypecheckss.cxx \
	txuchar16.cxx \
	txuchar8.cxx \
	txuint16.cxx \
	txuint32.cxx \
	txuint64.cxx \
	txuint8.cxx \
	$(ENDOFLIST)

all:
	$(CCC) $(CFLAGS) -I../inc $(OBJS) -c

	$(ARCHIVE) ../lib/liblayer1.a *.o

	$(RANLIB) ../lib/liblayer1.a

clean:
	rm -f *.o
	rm -f ../lib/*.a
	rm -rf $(OS_TEMPORARY)

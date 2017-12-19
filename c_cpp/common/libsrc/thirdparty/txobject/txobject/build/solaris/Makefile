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

all:
	cp -f build.defs ../..

	cd ../../layer1/src; make; \
	cd ../../layer2/src; make; \
	cd ../../layer3/src; make; \
	cd ../../layer4/src; make; \
	cd ../../layer5/src; make; \

install:
	rm -f *.h
	rm -f sys/*.h
	rm -f txObject.lib

	ar -rv txObject.lib ../../layer5/src/*.o ../../layer4/src/*.o \
	../../layer3/src/*.o ../../layer2/src/*.o ../../layer1/src/*.o

	cp ../../layer*/inc/*.h .
	cp ../../layer*/inc/sys/*.h sys

clean:
	rm -f *.h
	rm -f sys/*.h
	rm -f txObject.lib

	cp -f build.defs ../..

	cd ../../layer1/src; make clean; \
	cd ../../layer2/src; make clean; \
	cd ../../layer3/src; make clean; \
	cd ../../layer4/src; make clean; \
	cd ../../layer5/src; make clean; \

	cd ../../layer1/test; make clean; \
	cd ../../layer2/test; make clean; \
	cd ../../layer3/test; make clean; \
	cd ../../layer4/test; make clean; \
	cd ../../layer5/test; make clean; \

	rm -f ../../build.defs


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

###############################################################################
#
# Abstract
#
###############################################################################

txObject Application Tool Kit (ATK) is a package of powerful C++ libraries that
helps decrease project development time and increase software reliability.
txObject ATK provides both embedded and application level projects a five-layer
framework. Each layer offers a unified set of tools that allows developers to
focus their development efforts.

txObject ATK's five-layer framework contains the following: Object Library, IO
& Timers, Threads, Inter-Process Communication, and Distributed Object
Communication. All libraries are currently supported on Windows(98/NT/2000),
Linux, and most variants of the Unix operating systems. Excelon Corp. 
ObjectStore Database support is also featured for those platforms the
ObjectStore application runs on [ObjectStore Support 2.8].

A correlation can be made between txObject ATK and Sun Microsystems Inc. Java
Language. txObject ATK has many of the advantages Java has, but without some of
the performance issues commonly associated with the Java Language. Java's
initial advantages are its platform independent built-in and publicly available
support libraries. Due to the basic nature of Java, its libraries have a common
object-oriented feel and data flow model. txObject ATK has accomplished a
similar Java-like environment for C++.

Another correlation can be made between txObject ATK and the CORBA Standards.
As demand increases for applications to run in distributed environments and on
multiple platforms, projects have had a new added complexity to their
traditional development cycle. txObject ATK greatly decreases such complexity
in a similar way CORBA does but with two fundamental differences. The first
distinction is that txObject ATK is a tightly coupled peer-to-peer distributed
system, where CORBA is a loosely coupled client-server model. The second
division is that txObject ATK distributed objects are state and transaction
consistent. CORBA provides distributed object interfaces without state.

txObject ATK is ideal for developing embedded applications. It has been
optimized for memory and CPU constrained systems. The entire software package
is comprised of 5,000 lines of class declaration code and 10,000 lines of
implementation code.

The tutorial (in directory ./doc) introduces the reader informally to the
basic concepts and features of the txObject ATK libraries and system. It just
scratches the surface of how to use txObject ATK and what it can do. The
tutorial is designed to wet your appetite. For further information see txObject
ATK Application Programming Interface documentation for all class and macro
definitions.

Each layer of the txObject ATK is a foundation for every successive layer.  In
order for a particular layer to operate, it requires the layers below it but
not the ones above.  Since layer 1 is the base for all other layers, close
attention should be paid. Each component in layer 1 will be used intimately
throughout layers 2-5.

###############################################################################
#
# To build Unix base platforms:
#
###############################################################################

	1) cd build/$(PLATFORM)
	2) make or gmake

###############################################################################
#
# To build Windows base platforms:
#
###############################################################################

	1) Visual C++ launch build/winx/txObject.dsw

        !) (WARNING) - Window 2000 has a UDP socket bug. Add WIN2K_UDP_FIX
                       symbol to your compile flags. Also get 
                       q263823_w2k_sp2_x86_en.exe Q249149 patch from MicroSoft
                       or http://txobject.sourceforge.net

###############################################################################
#
# Compile flag definitions:
#
###############################################################################

	1) TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	   - unix base compilation (default : TX_BIG_ENDIAN)
	   - linux base compilation (default : TX_LITTLE_ENDIAN)

	2) TX_WIN
	   - windows base compilation (default : TX_LITTLE_ENDIAN)

        3) WIN2K_UDP_FIX
           - workaround for Window 2000 UDP socket bug (always use for Win2K)

	4) GNU_SUPPORT
	   - used in correlation with a UNIX base compilation
	   - only with gnu / green hill compilers (g++/gcx)

	5) OSTORE_SUPPORT
	   - allow layer1 to allocate in both heap and persisted memory
	   - only with Excelon's ObjectStore application
           - (off by default)

	6) FIBER_SUPPORT
	   - use windows fibers under txObject ATK's threading package

	7) NATIVE_SUPPORT
	   - support native threading package as well as TX_OOT
           - (off by default)

	8) TX_OOT_SUPPORT
	   - use txObject ATK's threading package

	9) TX_OOT_DEBUG
	   - enable txObject ATK's threading debug package
           - (off by default)

	10) TX_OOT_STAT
	   - enable txObject ATK's threading performance monitoring package
           - (off by default)
          
	11) TX_STREAM_DEBUG
	   - force crash when errors are found in a txObject ATK object stream
           - (off by default)

	12) TX_UDP_AGING
	   - age out UDP peers after 10 minutes of not saying hello
           - (off by default)

	13) TX_AGREE_UPON_AGREEING
	   - this is a work around for layer5 when peer1 can talk
	     with peer2, but peer3 cannot talk with peer2 ... etc.

	14) TX_BIG_ENDIAN
	   - compile code for big endianess (override platform defaults)
           - (off by default)
	
	15) TX_LITTLE_ENDIAN
	   - compile code for little endianess (override platform defaults)
           - (off by default)

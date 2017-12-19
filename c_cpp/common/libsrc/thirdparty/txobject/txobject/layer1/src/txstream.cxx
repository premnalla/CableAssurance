///////////////////////////////////////////////////////////////////////////////
//
//   Copyright (C) 2000 by Thomas M. Hazel, txObject ATK (www.tobject.org)
//
//                           All Rights Reserved
//
//   This program is free software; you can redistribute it and/or modify it
//   under the terms of the GNU General Public License as published by the
//   Free Software Foundation; either version 2, or (at your option) any
//   later version.
//
//   This program is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU General Public License for more details.
//
//   You should have received a copy of the GNU General Public License
//   along with this program; if not, write to the Free Software
//   Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
//
///////////////////////////////////////////////////////////////////////////////

#include "txstring.h"
#include "txstream.h"

TX_DEFINE_PARENT_TYPE(txStream,txObject)

txStream::txStream (void)
{
}
 
txStream::~txStream (void)
{
}

void txStream::stream (txOut& out) const
{ 
	unsigned int h_loc = out.length();

	out.putNull();

	out.writeUInt(classType().id());

	storeInners(out);

	out.writeHeader(h_loc, out.length()-(h_loc+4), TX_GTYPE_INT32);
} 

void txStream::destream (txIn& in)
{ 
	restoreInners(in);
} 

void txStream::storeInners (txOut&) const
{
}

void txStream::restoreInners (txIn&)
{
}


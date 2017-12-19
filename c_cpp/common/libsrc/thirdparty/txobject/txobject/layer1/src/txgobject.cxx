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
#include "txgobject.h"

TX_DEFINE_STREAM_TYPE(txGObject,txStream)

txGObject::txGObject (void) :
	_length_(0)
{
}

txGObject::txGObject (const txUInt32& type, unsigned long length) :
	_type_(type), _length_(length)
{
}

txGObject::~txGObject (void)
{
	_attrs_.clearAndDestroy();
}


void txGObject::stream (txOut& out) const
{
	unsigned int h_loc = out.length();

	out.putNull();

	out.writeUInt(_type_.value());

	storeInners(out);

	out.writeHeader(h_loc, out.length()-(h_loc+4), TX_GTYPE_INT32);
}

void txGObject::storeInners (txOut& out) const
{
	txObject* o;
	txListIterator iter((txList&) _attrs_);

	while (o = (txObject*) iter.next())
	{
		out << o; 
	}
}

void txGObject::restoreInners (txIn& in)
{
	txObject* o;
	const char* end = in.cursor() + _length_;

	while (in.cursor() < end)
	{
		in >> o;

		_attrs_.append(o);
	}
}


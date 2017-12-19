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

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/types.h>
	#include <netinet/in.h>
#else
	#include <winsock.h>
#endif

#include "txint8.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txInt8,txObject)

txInt8::txInt8 (void) :
	_int_(0)
{
}

txInt8::txInt8 (signed char v) :
	_int_(v)
{
}

txInt8::txInt8 (const txInt8& obj)
{
	_int_ = obj._int_;
}

txInt8& txInt8::operator= (txInt8& obj)
{
	if (this != &obj)
	{
		obj._int_ = obj._int_;
	}

	return *this;
}

txInt8::~txInt8 (void)
{
}

void txInt8::stream (txOut& out) const
{
	out.writeRawHeader(htonl(TX_SIGNED_INT8));
	out.append("\0\0\0", 3);
	out.append((char*) &_int_, 1);
}

void txInt8::destream (txIn& in)
{
	_int_ = *(in.cursor() + 3);
}

int txInt8::equals (const txObject* obj) const
{
	return obj->isClass(txInt8::Type) ?
		(_int_ == ((txInt8*) obj)->_int_) : 0;
}

int txInt8::compare (const txObject* obj) const
{
	if (_int_ == ((txInt8*) obj)->_int_)
	{
		return 0;
	}
	else if (_int_ > ((txInt8*) obj)->_int_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


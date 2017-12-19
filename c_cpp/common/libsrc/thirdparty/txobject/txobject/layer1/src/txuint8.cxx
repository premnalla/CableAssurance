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

#include "txuint8.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txUInt8,txObject)

txUInt8::txUInt8 (void) :
	_int_(0)
{
}

txUInt8::txUInt8 (unsigned char v) :
	_int_(v)
{
}

txUInt8::txUInt8 (const txUInt8& obj)
{
	_int_ = obj._int_;
}

txUInt8& txUInt8::operator= (txUInt8& obj)
{
	if (this != &obj)
	{
		obj._int_ = obj._int_;
	}

	return *this;
}

txUInt8::~txUInt8 (void)
{
}

void txUInt8::stream (txOut& out) const
{
	out.writeRawHeader(htonl(TX_UNSIGNED_INT8));
	out.append("\0\0\0", 3);
	out.append((char*) &_int_, 1);
}

void txUInt8::destream (txIn& in)
{
	_int_ = *(in.cursor() + 3);
}

int txUInt8::equals (const txObject* obj) const
{
	return obj->isClass(txUInt8::Type) ?
		(_int_ == ((txUInt8*) obj)->_int_) : 0;
}

int txUInt8::compare (const txObject* obj) const
{
	if (_int_ == ((txUInt8*) obj)->_int_)
	{
		return 0;
	}
	else if (_int_ > ((txUInt8*) obj)->_int_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


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

#include "txchar8.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txChar8,txObject)

txChar8::txChar8 (void) :
	_char_(0)
{
}

txChar8::txChar8 (signed char v) :
	_char_(v)
{
}

txChar8::txChar8 (const txChar8& obj)
{
	_char_ = obj._char_;
}

txChar8& txChar8::operator= (txChar8& obj)
{
	if (this != &obj)
	{
		obj._char_ = obj._char_;
	}

	return *this;
}

txChar8::~txChar8 (void)
{
}

void txChar8::stream (txOut& out) const
{
	out.writeRawHeader(htonl(TX_SIGNED_CHAR8));
	out.append("\0\0\0", 3);
	out.append((char*) &_char_, 1);
}

void txChar8::destream (txIn& in)
{
	memcpy(&_char_, in.cursor() + 3, 1);
}

int txChar8::equals (const txObject* obj) const
{
	return obj->isClass(txChar8::Type) ?
		(_char_ == ((txChar8*) obj)->_char_) : 0;
}

int txChar8::compare (const txObject* obj) const
{
	if (_char_ == ((txChar8*) obj)->_char_)
	{
		return 0;
	}
	else if (_char_ > ((txChar8*) obj)->_char_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


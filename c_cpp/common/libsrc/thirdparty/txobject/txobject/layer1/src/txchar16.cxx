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

#include "txchar16.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txChar16,txObject)

txChar16::txChar16 (void) :
	_char_(0)
{
}

txChar16::txChar16 (signed short v) :
	_char_(v)
{
}

txChar16::txChar16 (const txChar16& obj)
{
	_char_ = obj._char_;
}

txChar16& txChar16::operator= (txChar16& obj)
{
	if (this != &obj)
	{
		_char_ = obj._char_;
	}

	return *this;
}

txChar16::~txChar16 (void)
{
}

void txChar16::stream (txOut& out) const
{
	out.writeRawHeader(htonl(TX_SIGNED_CHAR16));
	out.append("\0\0", 2);
	out.append((char*) &_char_, 2);
}

void txChar16::destream (txIn& in)
{
	memcpy(&_char_, in.cursor() + 2, 2);
}

int txChar16::equals (const txObject* obj) const
{
	return obj->isClass(txChar16::Type) ?
		(_char_ == ((txChar16*) obj)->_char_) : 0;
}

int txChar16::compare (const txObject* obj) const
{
	if (_char_ == ((txChar16*) obj)->_char_)
	{
		return 0;
	}
	else if (_char_ > ((txChar16*) obj)->_char_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


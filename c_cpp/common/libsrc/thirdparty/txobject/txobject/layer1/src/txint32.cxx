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

#include "txint32.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txInt32,txObject)

txInt32::txInt32 (void) :
	_int_(0)
{
}

txInt32::txInt32 (signed long v) :
	_int_(v)
{
}

txInt32::txInt32 (const txInt32& obj)
{
	_int_ = obj._int_;
}

txInt32& txInt32::operator= (txInt32& obj)
{
	if (this != &obj)
	{
		obj._int_ = obj._int_;
	}

	return *this;
}

txInt32::~txInt32 (void)
{
}

void txInt32::stream (txOut& out) const
{
	out << _int_;
}

void txInt32::destream (txIn& in)
{
	_int_ = ntohl(*(unsigned long*) (in.cursor()));
}

int txInt32::equals (const txObject* obj) const
{
	return obj->isClass(txInt32::Type) ?
		(_int_ == ((txInt32*) obj)->_int_) : 0;
}

int txInt32::compare (const txObject* obj) const
{
	if (_int_ == ((txInt32*) obj)->_int_)
	{
		return 0;
	}
	else if (_int_ > ((txInt32*) obj)->_int_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


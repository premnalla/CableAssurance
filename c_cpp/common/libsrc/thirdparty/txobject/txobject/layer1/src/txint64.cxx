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

#include "txint64.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txInt64,txObject)

txInt64::txInt64 (void) :
	_hi_(0), _lo_(0)
{
}

txInt64::txInt64 (signed long hi, signed long lo) :
	_hi_(hi), _lo_(lo)
{
}

txInt64::txInt64 (const txInt64& obj)
{
	_hi_ = obj._hi_;
	_lo_ = obj._lo_;
}

txInt64& txInt64::operator= (txInt64& obj)
{
	if (this != &obj)
	{
		obj._hi_ = obj._hi_;
		obj._lo_ = obj._lo_;
	}

	return *this;
}

txInt64::~txInt64 (void)
{
}

void txInt64::stream (txOut& out) const
{
	out.writeRawHeader(htonl(TX_SIGNED_INT64));
	out.writeUInt(_hi_); out.writeUInt(_lo_);
}

void txInt64::destream (txIn& in)
{
	_hi_ = ntohl(*(unsigned long*) (in.cursor()));
	_lo_ = ntohl(*(unsigned long*) (in.cursor() + 4));
}

int txInt64::equals (const txObject* obj) const
{
	return obj->isClass(txInt64::Type) ?
		(_hi_ == ((txInt64*) obj)->_hi_) &&
		(_lo_ == ((txInt64*) obj)->_lo_) : 0;
}

int txInt64::compare (const txObject* obj) const
{
	if (_hi_ == ((txInt64*) obj)->_hi_)
	{
		if (_lo_ == ((txInt64*) obj)->_lo_)
		{
			return 0;
		}
		else if (_lo_ == ((txInt64*) obj)->_lo_)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	else if (_hi_ > ((txInt64*) obj)->_hi_)
	{
		return -1;
	}
	else
	{
		return 1;
	}
}


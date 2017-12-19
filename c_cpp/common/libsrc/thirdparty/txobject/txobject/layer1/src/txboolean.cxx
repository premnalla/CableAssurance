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

#include "txstring.h"
#include "txboolean.h"

TX_DEFINE_PARENT_TYPE(txBoolean,txObject)

txBoolean::txBoolean (void) :
	_bool_(0)
{
}

txBoolean::txBoolean (txObjectType::Boolean v) :
	_bool_(v)
{
}

txBoolean::txBoolean (const txBoolean& obj)
{
	_bool_ = obj._bool_;
}

txBoolean& txBoolean::operator= (txBoolean& obj)
{
	if (this != &obj)
	{
		obj._bool_ = obj._bool_;
	}

	return *this;
}

txBoolean::~txBoolean (void)
{
}

void txBoolean::stream (txOut& out) const
{
	if (_bool_)
	{
		out.writeRawHeader(htonl(TX_BOOLEAN_TRUE));
	}
	else
	{
		out.writeRawHeader(htonl(TX_BOOLEAN_FALSE));
	}
}

void txBoolean::destream (txIn& in)
{
	// done in class txIn
}

int txBoolean::equals (const txObject* obj) const
{
	return obj->isClass(txBoolean::Type) ?
		(_bool_ == ((txBoolean*) obj)->_bool_) : 0;
}

int txBoolean::compare (const txObject* obj) const
{
	if (_bool_ == ((txBoolean*) obj)->_bool_)
	{
		return 0;
	}
	else if (_bool_ > ((txBoolean*) obj)->_bool_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


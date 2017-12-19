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

#include "txfloat.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txFloat,txObject)

txFloat::txFloat (void) :
	_float_(0)
{
}

txFloat::txFloat (float v) :
	_float_(v)
{
}

txFloat::txFloat (const txFloat& obj)
{
	_float_ = obj._float_;
}

txFloat& txFloat::operator= (txFloat& obj)
{
	if (this != &obj)
	{
		obj._float_ = obj._float_;
	}

	return *this;
}

txFloat::~txFloat (void)
{
}

void txFloat::stream (txOut& out) const
{
	out << _float_;
}

void txFloat::destream (txIn& in)
{
	memcpy((char*) &_float_, in.cursor(), 4);
}

int txFloat::equals (const txObject* obj) const
{
	return obj->isClass(txFloat::Type) ?
		(_float_ == ((txFloat*) obj)->_float_) : 0;
}

int txFloat::compare (const txObject* obj) const
{
	if (_float_ == ((txFloat*) obj)->_float_)
	{
		return 0;
	}
	else if (_float_ > ((txFloat*) obj)->_float_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


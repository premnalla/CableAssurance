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

#include "txdouble.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txDouble,txObject)

txDouble::txDouble (void) :
	_double_(0)
{
}

txDouble::txDouble (double v) :
	_double_(v)
{
}

txDouble::txDouble (const txDouble& obj)
{
	_double_ = obj._double_;
}

txDouble& txDouble::operator= (txDouble& obj)
{
	if (this != &obj)
	{
		obj._double_ = obj._double_;
	}

	return *this;
}

txDouble::~txDouble (void)
{
}

void txDouble::stream (txOut& out) const
{
	out << _double_;
}

void txDouble::destream (txIn& in)
{
	memcpy((char*) &_double_, in.cursor(), 8);
}

int txDouble::equals (const txObject* obj) const
{
	return obj->isClass(txDouble::Type) ?
		(_double_ == ((txDouble*) obj)->_double_) : 0;
}

int txDouble::compare (const txObject* obj) const
{
	if (_double_ == ((txDouble*) obj)->_double_)
	{
		return 0;
	}
	else if (_double_ > ((txDouble*) obj)->_double_)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


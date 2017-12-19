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

#include "txclassa.h"
#include "txstring.h"

TX_DEFINE_STREAM_TYPE(txClassA,txStream)

txClassA::txClassA (void)
{
}

txClassA::txClassA (const char* data, int value) :
	_integer_(value), _string_(data)
{
}

txClassA::~txClassA (void)
{
}

const char* txClassA::data (void) const
{
	return _string_.data();
}

int txClassA::value (void) const
{
	return _integer_.value();
}

void txClassA::storeInners (txOut& out) const
{
	out << _string_;
	out << _integer_;
}

void txClassA::restoreInners (txIn& in)
{
	in >> _string_;
	in >> _integer_;
}


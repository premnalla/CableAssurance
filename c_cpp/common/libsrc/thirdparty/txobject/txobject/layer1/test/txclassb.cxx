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

#include "txclassb.h"
#include "txstring.h"

TX_DEFINE_STREAM_TYPE(txClassB,txStream)

txClassB::txClassB (void)
{
}

txClassB::txClassB (const char* data, int value) :
	_generic_(data, value)
{
}

txClassB::~txClassB (void)
{
}

const char* txClassB::data (void) const
{
	return _generic_.data();
}

int txClassB::value (void) const
{
	return _generic_.value();
}

void txClassB::storeInners (txOut& out) const
{
	out << _generic_;
}

void txClassB::restoreInners (txIn& in)
{
	in >> _generic_;
}


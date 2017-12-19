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

#include "txclassc.h"
#include "txstring.h"

TX_DEFINE_STREAM_TYPE(txClassC,txClassB)

txClassC::txClassC (void)
{
}

txClassC::txClassC (const char* data, int value) :
	txClassB(data, value)
{
}

txClassC::~txClassC (void)
{
}

const txList* txClassC::list (void) const
{
	return &_list_;
}

void txClassC::storeInners (txOut& out) const
{
	txClassB::storeInners(out);

	out << _list_;
}

void txClassC::restoreInners (txIn& in)
{
	txClassB::restoreInners(in);

	in >> _list_;
}


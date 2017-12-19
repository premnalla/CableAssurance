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

#include "txstring.h"
#include "sys/txstateobj.h"
 
TX_DEFINE_STREAM_TYPE(txStateObject,txState)

txStateObject::txStateObject (signed short int state) : _state_(state)
{
}

txStateObject::~txStateObject (void)
{
}

txStateObject& txStateObject::operator= (const txStateObject& obj)
{
	if (this != &obj)
	{
		_state_ = obj._state_;

		txState::operator=(obj);
	}

	return *this;
}

void txStateObject::storeInners (txOut& out) const
{
	out << _state_;
}
 
void txStateObject::restoreInners (txIn& in)
{
	in >> _state_;
}


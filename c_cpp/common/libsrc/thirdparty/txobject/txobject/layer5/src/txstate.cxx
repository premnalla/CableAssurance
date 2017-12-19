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
#include "sys/txstate.h"

TX_DEFINE_PARENT_TYPE(txState,txStream)

txState::txState (void) : _refcnt_(new int(1))
{
}

txState::txState (const txState &obj)
{
	_refcnt_ = obj._refcnt_;
	incref();
}

txState::~txState (void)
{
	if (!decref())
	{
		delete _refcnt_; _refcnt_ = 0;
	}
}

txState& txState::operator= (const txState& obj)
{
	((txState*) &obj)->incref();

	if (decref() == 0)
	{
		delete _refcnt_; _refcnt_ = 0;
	}

	_refcnt_ = obj._refcnt_;

	return *this;
}


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
 
#include "thread.h"
#include "thrdplist.h"

ThrdPList::ThrdPList (void) :
	_max_pri_(0)
{
}

ThrdPList::~ThrdPList (void)
{
}

void ThrdPList::remove (Thread* t)
{
	_list_[t->priority()].removeReference((txObject*) t);
}

void ThrdPList::put (Thread* t)
{
	int pri = t->priority();

	_list_[pri].append((txObject*) t);

	if (pri > _max_pri_)
	{
		_max_pri_ = pri;
	}
}

Thread* ThrdPList::get (void)
{
	register Thread* t = 0;
	register int i = _max_pri_;

	for (; i >= 0 ; i--)
	{
		if (_list_[i].entries() && (t = (Thread*) _list_[i].get()))
		{
			break;
		}
	}

	_max_pri_ = i;
	return t;
}


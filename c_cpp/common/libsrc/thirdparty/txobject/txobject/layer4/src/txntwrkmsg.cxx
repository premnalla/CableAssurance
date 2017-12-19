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

#include "txabspeer.h"
#include "txntwrkmsg.h"

static txNetworkMsgs THE_PACKET_REUSE_LIST;

void* txNetworkMsg::operator new (size_t size)
{
	void* p = 0;

	if ( !(p = THE_PACKET_REUSE_LIST.get()) )
	{
		p = (void*) malloc(size);
	}

	return p;
}

void txNetworkMsg::operator delete (void* p)
{
	THE_PACKET_REUSE_LIST.append((txNetworkMsg*) p);
}


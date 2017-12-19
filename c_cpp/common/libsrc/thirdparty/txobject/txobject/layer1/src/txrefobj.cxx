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
#include "sys/txrefobj.h"

TX_DEFINE_PARENT_TYPE(txRefObj,txObject)

txRefObj::txRefObj (unsigned long int ref, const txObject* obj) :
	_ref_(ref), _object_(obj)
{
}

txRefObj::~txRefObj (void)
{
}

void txRefObj::stream (txOut& out) const
{ 
	unsigned int h_loc = out.length(); 

	out.putNull(); 

	out.writeUInt(_ref_);

	((txObject*) _object_)->stream(out); 

	out.writeHeader(h_loc, out.length()-(h_loc+4), TX_REFOBJ_INT32);
} 

void txRefObj::destream (txIn&)
{ 
} 


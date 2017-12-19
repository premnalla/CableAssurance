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
#include "sys/txobjref.h"

TX_DEFINE_PARENT_TYPE(txObjRef,txObject)

txObjRef::txObjRef (unsigned long int ref) :
	_ref_(ref)
{
}

txObjRef::~txObjRef (void)
{
}

int txObjRef::equals (const txObject* obj) const
{
	return obj->isClass(txObjRef::Type) ?
		(_ref_ == ((txObjRef*) obj)->_ref_) : 0;
}

void txObjRef::stream (txOut& out) const
{ 
	unsigned int h_loc = out.length(); 

	out.putNull(); 

	out.writeUInt(_ref_); 

	out.writeHeader(h_loc, out.length()-(h_loc+4), TX_OBJREF_INT32);
}

void txObjRef::destream (txIn&)
{ 
}


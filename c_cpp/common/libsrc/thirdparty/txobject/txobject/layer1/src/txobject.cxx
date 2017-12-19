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

#include <stdio.h>

#include "txobject.h"
#include "txstring.h"

TX_DEFINE_PARENT_TYPE(txObject,txRoot)

txObject::txObject (void)
{
}

txObject::~txObject (void)
{
}

int txObject::compare (const txObject* obj) const
{
	fprintf(stderr, "TXOBJECT[error] : compare must be defined if used\n");
	fflush(stderr); TX_CRASH; return 0;
}

const txObject* txObjectSequence::insert (txObject*)
{
	return 0;
}

const txObject* txObjectSequence::find (const txObject*) const
{
	return 0;
}

txObject* txObjectSequence::remove (const txObject*)
{
	return 0;
}

void txObjectSequence::removeAndDestroy (const txObject*)
{
}

void txObjectSequence::clearAndDestroy (void)
{
}

void txObjectSequence::clear (void)
{
}

int txObjectSequence::entries (void) const
{
	return 0;
}

txObject* txObjectIterator::remove (void)
{
	return 0;
}

const txObject* txObjectIterator::next (void)
{
	return 0;
}

void txObjectIterator::reset (void)
{
}


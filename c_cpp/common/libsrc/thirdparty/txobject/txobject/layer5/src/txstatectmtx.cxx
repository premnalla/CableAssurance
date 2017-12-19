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
#include "txhashmap.h"
#include "txpeerlist.h"
#include "sys/txstateobj.h"
#include "sys/txstatectmtx.h"

txStateCtMtx::txStateCtMtx (const txAbsPeer& peer)
{
	setPrimaryKey(peer);
	txStateObject the_state(ESTABLISHED);
	txStateMatrix::set(peer, peer, the_state);
}

void txStateCtMtx::set (const txAbsPeer& row, const txAbsPeer& col, STATE state)
{
	txStateObject the_state(state);
	txStateMatrix::set(row, col, the_state);
}

txStateCtMtx::STATE txStateCtMtx::get (const txAbsPeer& row,
	const txAbsPeer& col) const
{
	txStateCtMtx::STATE state = UNKNOWN;

	txStateObject* the_state =
		(txStateObject*) txStateMatrix::get(row, col);

	if (the_state)
	{
		state = (txStateCtMtx::STATE) the_state->state();
	}

	return state;
}

int txStateCtMtx::determinate (void) const
{
	int result = 1;
	const txAbsPeer* key;
	txPeerList exclude_keys;

	txStateVector* vec = (txStateVector*) getRow(*getPrimaryKey());
	txHashMapIterator iter1((txHashMap&) *vec->getTable());

	while (key = (txAbsPeer*) iter1.next())
	{
		int state = ((txStateObject*) vec->get(*key))->state();

		if (state == LOST)
		{
			exclude_keys.append((txAbsPeer*) key);
		}			
	}

	txHashMapIterator iter2((txHashMap&) *vec->getTable());

	while (key = (txAbsPeer*) iter2.next())
	{
		int col_state = colState(*key, exclude_keys);

		if ((LOST != col_state) && (ESTABLISHED != col_state))
		{
			result = 0;
			break;
		}
	}

	return result;
}


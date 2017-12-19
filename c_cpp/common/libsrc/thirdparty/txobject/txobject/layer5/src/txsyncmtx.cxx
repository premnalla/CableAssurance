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
#include "txpeerlist.h"
#include "sys/txsyncmtx.h"
#include "sys/txstateobj.h"

txSyncMtx::txSyncMtx (const txAbsPeer& local_peer)
{
	setPrimaryKey(local_peer);

	txStateObject the_state(SYNC_COMPLETE);

	txStateMatrix::set(local_peer, local_peer, the_state);
}

void txSyncMtx::set (const txAbsPeer& row, const txAbsPeer& col, STATE state)
{
	txStateObject the_state(state);

	txStateMatrix::set(row, col, the_state);
}

txSyncMtx::STATE txSyncMtx::get (const txAbsPeer& row,
	const txAbsPeer& col) const
{
	txSyncMtx::STATE state = UNKNOW;

	txStateObject* the_state =
		(txStateObject*) txStateMatrix::get(row, col);

	if (the_state)
	{
		state = (txSyncMtx::STATE) the_state->state();
	}

	return state;
}

const txStateVector* txSyncMtx::getRow (const txAbsPeer& row) const
{
	return (txStateVector*) txStateMatrix::getRow(row);
}

int txSyncMtx::synced (void) const
{
	return (txStateMatrix::state() == SYNC_COMPLETE);
}

int txSyncMtx::syncedLocally (void) const
{
	return (outgoingSynced() && incomingSynced());
}

int txSyncMtx::outgoingSynced (void) const
{
	return (rowState(*getPrimaryKey(), *getPrimaryKey()) == SYNC_COMPLETE);
}

int txSyncMtx::incomingSynced (void) const
{
	return (colState(*getPrimaryKey(), *getPrimaryKey()) == SYNC_COMPLETE);
}


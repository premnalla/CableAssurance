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
 
#if !defined ( __TXSYNCMTX_H__ )
#define __TXSYNCMTX_H__

#include "txabspeer.h"
#include "sys/txstatemtx.h"

class txSyncMtx : public txStateMatrix
{
	public:
		enum STATE
		{
			UNKNOW = 0,
			SYNC_INITIAL = 10,
			SYNC = 11,
			SYNC_COMPLETE = 12,
			SYNC_ERROR = 13
		};

	public:
		txSyncMtx (const txAbsPeer& local_peer);

		~txSyncMtx (void)
		{
		}

		void set (const txAbsPeer& r, const txAbsPeer& c, STATE s);

		STATE get (const txAbsPeer& r, const txAbsPeer& c) const;

		const txStateVector* getRow (const txAbsPeer& r) const;

		int synced (void) const;

		int syncedLocally (void) const;

		int outgoingSynced (void) const;

		int incomingSynced (void) const;
};

#endif // __TXSYNCMTX_H__

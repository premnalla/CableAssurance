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
 
#if !defined ( __TXSTATEMGR_H__ )
#define __TXSTATEMGR_H__

#include "txevent.h"
#include "txhashmap.h"
#include "txpeerlist.h"
#include "sys/txdobjmsg.h"
#include "sys/txstatevec.h"
#include "sys/txstatectmtx.h"

class txStateMgr: public txDObjMsg
{
	TX_DEFINE_TYPE(txStateMgr)

	private:
		static int _registerFlag_;
		unsigned long _start_time_;
		unsigned long _contact_count_;

		txPeerList _static_peers_;
		txPeerList _active_peers_;
		txPeerList _all_known_peers_;

		txStateCtMtx _status_matrix_;

		txHashMap _contact_table_;
		txHashMap _no_determinate_peers_;
		txEvent _contact_pending_event_;
		txEvent _contact_determinate_event_;

	private:
		void _contact_ (const txAbsPeer&);
		void _disconnect_ (const txAbsPeer&);

		void _performNotifcations_ (void);
		void _sendVectorTo_ (const txAbsPeer&);
		void _processNotify_ (const txAbsPeer*, txAbsPeer*, int, int);

		void _addActivePeer_ (const txAbsPeer&);
		void _addAllKnownPeer_ (const txAbsPeer&);

		void _restoreVector_ (const txAbsPeer&, txIn&);
		void _streamVector_ (const txAbsPeer&, txOut&);

		void _goToState_ (const txAbsPeer&, txStateCtMtx::STATE);
		void _sendUpdate_ (const txAbsPeer&, txStateCtMtx::STATE);

		static void _contactLost_ (void*, const txAbsPeer*);
		static void _contactEstablished_ (void*, const txAbsPeer*);

		static void _notifyLost_ (void* obj);
		static void _notifyEstablish_ (void* obj);

		static txObject* _restoreVec_ (txStateMgr*, txIn&,
			const txAbsPeer&);

		static txObject* _updateEntry_ (txStateMgr*, txIn&,
			const txAbsPeer&);

	public:
		txStateMgr (txDObjMgr* d_mgr);

		~txStateMgr (void);

		int determinate (void);

		void initialize (const txPeerList& list);

		void waitForDeterminateContact (void);

		void checkForDeterminateContact (void);

		void addPeer (const txAbsPeer&);

		void removePeer (const txAbsPeer&);

		const txPeerList* allKnownPeers (void) const
		{
			return &_all_known_peers_;
		}

		const txPeerList* activePeers (void) const
		{
			return &_active_peers_;
		}

		const txPeerList* staticPeers (void) const
		{
			return &_static_peers_;
		}

		txStateCtMtx::STATE getState (const txAbsPeer& r,
			const txAbsPeer& c) const
		{
			return _status_matrix_.get(r, c);
		}

		const txStateCtMtx* getStatusMatrix (void) const
		{
			return &_status_matrix_;
		}
};

#endif // __TXSTATEMGR_H__

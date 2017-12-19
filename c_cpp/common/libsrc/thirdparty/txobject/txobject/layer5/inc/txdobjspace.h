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

#if !defined ( __TXDOBJSPACE_H__)
#define __TXDOBJSPACE_H__

#include "txlist.h"
#include "txlock.h"
#include "txevent.h"
#include "txthread.h"
#include "txdobject.h"
#include "txpeerlist.h"
#include "sys/txdobjmsg.h"
#include "sys/txsyncmtx.h"
#include "sys/txdobjcbmgr.h"

class txDObjMgr;
class txMemSpace;

class txDObjSpace : public txDObjMsg
{
	TX_DEFINE_TYPE(txDObjSpace)

	private:
		int _ref_count_;
		int _sync_count_;
		int _is_space_active_;
		static int _registerFlag_;

		txLock _space_arb_lock_;
		txEvent _space_sync_done_;

		txSyncMtx* _sync_matrix_;
		txMemSpace* _space_space_;
		txMemSpace* _local_space_;

		txPeerList _active_peers_;
		txPeerList _interested_peers_;

		txList _notify_list_;
		txDObjCBMgr _sync_notify_;

	private:
		txDObjSpace (txDObjMgr* mgr, txMemSpace* ms, const char* name);

		void incref (void);
		void decref (void);
		int refcount (void);

		int _spaceState_ (void);
		int _waitForSync_ (void);
		int _isSpaceActive_ (void) const;

		void _allocateObject_ (txDObject*);
		void _registerGlobalObject_ (txDObject*);
		void _unregisterObject_ (txDObject*);
		void _deallocateObject_ (txDObject*);
		void _registerObject_ (txDObject*, int notify = 0);

		txDObject* _arbitrateObject_ (txDObject*);

		static void _synchronizeTo_ (void* parms);

		void _objectDeleteMessage_ (txDObject*);
		void _objectTransferMessage_ (txDObject*);

		void _addInterestedPeer_ (const txAbsPeer&);
		int _removeInterestedPeer_ (const txAbsPeer&);

		void _notifyObjectsOfCreation_ (void);
		void _notifyObjectOfDeletion_ (txDObject*);

		void _notifyObjectsPeerUp_ (const txAbsPeer&);
		void _notifyObjectsPeerDown_ (const txAbsPeer&);

		void _updateLocalStatus_ (const txAbsPeer& ,
			txSyncMtx::STATE state);

		void _startOutGoingSync_ (const txAbsPeer&);
		void _beginSyncMessage_ (const txAbsPeer&);
		void _syncMessage_ (const txAbsPeer&);
		void _endSyncMessage_ (const txAbsPeer&);

		void _sendMessage_ (txOut& out, const txAbsPeer&);

		void _processSync_ (txIn&);
		void _processObjectTransfer_ (txIn&);
		void _processObjectDelete_ (txIn&);
		void _processUpdatePeerStatus_ (txIn& in, const txAbsPeer&);

		void _checkForLocalSynchronization_ (void);

		void _flushObjectRpcs_ (void);

		void _addToSortedPeers_ (txPeerList&, const txAbsPeer&);
		void _removeSortedPeer_ (txPeerList&, const txAbsPeer&);

		int _isPeerActive_ (const txAbsPeer&);

		static txObject* _beginSync_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _sync_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _endSync_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _objTransfer_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _objDelete_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _updPeerStatus_ (txDObjSpace*, txIn&,
			const txAbsPeer&);
		static txObject* _updStatus_ (txDObjSpace*, txIn&,
			const txAbsPeer&);

	public:
		~txDObjSpace (void);

		int synced (void) const;
		void objects (txList& list) const;
		const txDObject* accessObject (const char* oid) const;

		const txPeerList* activePeers (void) const;
		const txPeerList* interestedPeers (void) const;

		void registerForSyncNotify (const TX_CBK_FUNC, void*);
		void unregisterForSyncNotify (const TX_CBK_FUNC);

	friend class txDObjMgr;
	friend class txDObject;
};

#endif // __TXDOBJSPACE_H__


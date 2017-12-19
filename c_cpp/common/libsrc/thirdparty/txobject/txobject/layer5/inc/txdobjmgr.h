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
 
#if !defined ( __TXDOBJMGR_H__ )
#define __TXDOBJMGR_H__

#include "txlist.h"
#include "txdobject.h"
#include "txdobjspace.h"
#include "txdobjglbls.h"
#include "sys/txmemspace.h"
#include "sys/txdobjsrvr.h"
#include "sys/txdobjrpcbc.h"

class txStateMgr;
class txStateCtMtx;

class txDObjMgr : public txDObjRpcBc
{
	TX_DEFINE_TYPE(txDObjMgr)

	private:
		int _thread_pri_;
		int _static_peers_;
		static int _registerFlag_;

		txMemSpace _mem_mgr_;
		txDObjServer _comm_layer_;

		txList _list_of_spaces_;

		txStateMgr* _state_mgr_;
		txMemSpace* _space_space_;

		txDObjCBMgr _error_callback_;
		txDObjCBMgr _conn_up_notifer_;
		txDObjCBMgr _conn_down_notifer_;

	private:
		void _joinSpace_ (const char* name) ;
		void _broadcastJoinSpace_ (const char* name);
		void _createDObjSpace_ (const char* name);
		void _broadcastLeaveSpace_ (const char* name);

		void _waitForSpaceSynchronization_ (void);
		void _waitForSpaceSynchronization_ (const char* name);

		void _sendJoinSpaceMsgTo_ (const txAbsPeer& peer);
		void _sendJoinSpaceMsgTo_ (const txAbsPeer&, const char*);

		void _contactEstablished_ (const txAbsPeer& peer);
		void _contactLost_ (const txAbsPeer& peer);

		void _deallocate_ (txDObject* object);
		const txMemObj* _accessObject_ (const char* oid);
		void _allocate_ (const char* space, txDObject* object);
		void _registerObject_ (const char* space, txDObject* object);

		static txObject* _joinSpaceBCast_ (
			txDObjMgr* self,
			txIn& in,
			const txAbsPeer& peer);
		static txObject* _leaveSpaceBCast_ (
			txDObjMgr* self,
			txIn& in,
			const txAbsPeer& peer);

	public:
		txDObjMgr (const txAbsPeer& local_peer,
			const txPeerList& peers_of_interest,
			const txList& spaces_of_interest,
			const TX_CBK_FUNC up_notification,
			const TX_CBK_FUNC down_notification,
			const TX_CBK_FUNC error_notification,
			int static_peers = txDObjGlobals::STATIC_PEERS_FLAG,
			int thread_prior = txDObjGlobals::BASE_THREAD_PRIORITY,
			int peer_time = txDObjGlobals::PEER_TIMEOUT_INTERVAL);

		~txDObjMgr (void);

		int synced (void) const;
		int determinate (void) const;
		int isOidRegistered (const char*) const;
		int staticPeers (void) const { return _static_peers_; };
		int baseThreadPriority (void) const { return _thread_pri_; };

		int addPeer (const txAbsPeer&);
		int removePeer (const txAbsPeer&, int stop_ping = 0);

		void joinSpace (const char*);
		void leaveSpace (const char*);

		txDObject* accessObject (const char*);
		const txDObjSpace* accessSpace (const char*);

		const txList* spacesOfInterest (void) const;
		const txPeerList* activePeers (void) const;
		const txPeerList* allKnownPeers (void) const;
		const txStateCtMtx* getStatusMatrix (void) const;

		const txDObjServer* getObjSrvr (void) const
		{
			return &_comm_layer_;
		}

		const txMemSpace* getMemMgr (void) const
		{
			return &_mem_mgr_;
		}

		txDObject* registerObject (txDObject*, const char*);

		txDObject* registerGlobalObject (
			txDObject*, const char*,
			const char* space = txDObjGlobals::SYSTEM_SPACE);

		txDObject* unregisterObject (const char*);

		void registerNotifyUp (
			const TX_CBK_FUNC call_back_func,
			void* args = 0);
		void registerNotifyDown (
			const TX_CBK_FUNC call_back_func,
			void* args = 0);
		void registerErrorCallback (
			const TX_CBK_FUNC call_back_func,
			void* args = 0);

		void unregisterNotifyUp (
			const TX_CBK_FUNC call_back_func);
		void unregisterNotifyDown (
			const TX_CBK_FUNC call_back_func);
		void unregisterErrorCallback (
			const TX_CBK_FUNC call_back_func);

		void triggerErrorCallback (void* error_info = 0);

		void shutDown (void);

	friend class txStateMgr;
};

#endif // __TXDOBJMGR_H__

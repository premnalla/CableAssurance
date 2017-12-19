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
 
#if !defined ( __TXDOBJSRVR_H__ )
#define __TXDOBJSRVR_H__

#include "txipcsrvr.h"
#include "txthrdmgr.h"
#include "txntwrkmsg.h"
#include "txhashtable.h"
#include "txdobjglbls.h"

class txPeerList;
class txMemSpace;

class txDObjServer : public txObject
{
	public:
		enum STATUS
		{
			INITIAL,
			SHUTDOWN
		};

		static const int TXDOBJSERVER_THRD_PRIORITY;

	private:
		STATUS _status_;

		int _thread_pri_;

		txHashTable _peers_;
		txNetworkMsgs _packets_;

		txMemSpace* _tx_;
		txMemSpace* _memory_mgr_;

		txIpPeer* _local_;
		txIPCServer* _ipc_comm_layer_;
		txThreadManager _thread_manager_;

		void* _contact_lost_context_;
		void* _contact_estab_context_;

		TX_COMM_FUNC _contact_lost_func_;
		TX_COMM_FUNC _contact_estab_func_;

	private:
		static void _contactLost_ (void* self, txAbsPeer* peer);
		static void _contactEstablished_ (void* self, txAbsPeer* peer);

		static void _processMessages_ (void* obj);
		static void _receiveThreadFunc_ (void* self);

	public:
		txDObjServer (
			txMemSpace& mem_mgr,
			const txAbsPeer& peer,
			int priority = TXDOBJSERVER_THRD_PRIORITY,
			int peer_t_out = txDObjGlobals::PEER_TIMEOUT_INTERVAL);

		~txDObjServer (void);

		const char* getId (void) const
		{
			return _local_->getId();
		}

		int getPort (void) const
		{
			return _local_->getPort();
		}

		unsigned long getNetAddr (void) const
		{
			return _local_->getNetAddr();
		}

		const txAbsPeer* getPeer (void) const
		{
			return _local_;
		}

		int isEstablished (const txAbsPeer& peer);

		int contact (const txAbsPeer& peer);

		int sendTo (txOut& out, const txAbsPeer& peer);

		int multicast (txOut& out, const txPeerList& list);

		int broadcast (txOut& out);

		void registerContactEstablished (const TX_COMM_FUNC, void*);
		void registerContactLost (const TX_COMM_FUNC, void*);

		void unregisterContactEstablished (void);
		void unregisterContactLost (void);

		void close (const txAbsPeer& peer);
		void close (void);
};

#endif // __TXDOBJSRVR_H__

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
 
#if !defined ( __TXDOBJRPCBC_H__ )
#define __TXDOBJRPCBC_H__ 

#include "txevent.h"
#include "txhashmap.h"
#include "sys/txdobjrpc.h"

class txAbsPeer;
class txPeerList;
class txRpcEntryBc;

class txDObjRpcBc : public txDObjRpc
{
	TX_DECLARE_STREAM_TYPE(txDObjRpcBc)

	private:
		unsigned long _rpc_id_count_;
		static txHashTable _rpc_bc_entries_;

	private:
		txRpcEntryBc* _setupTable_ (void);

		static void _callSingleParallelRpc_ (void*);

		static void _checkForAllResultsReceived_ (txRpcEntryBc&);

		static void _put_ (unsigned long, const txAbsPeer&, txObject*);

	protected:
		void _flushBroadcastRpcs (void);

	public:
		txDObjRpcBc (txDObjMgr* d_mgr = 0);

		~txDObjRpcBc (void);	

		unsigned long getNextRpcId (void);

		void addBroadcastPeer (const txAbsPeer&);

		void removeBroadcastPeer (const txAbsPeer&);

		const txPeerList* interestedPeers (void) const;

		txRpcEntryBc* broadcastTwoWayMethod (
			const char*, const txObject* a = 0);
};

class txRpcEntryBc : public txObject
{
	friend class txDObjRpcBc;

	public:
		enum STATUS
		{
			UNKNOWN   = -1,
			TERMINATE = 0,
			COMPLETE  = 1
		};

	private:
		txEvent _event_;
		txDObjRpcBc* _self_;
		char* _method_name_;
		STATUS _rpc_status_;
		unsigned long _rpc_id_;
		txObject* _method_args_;
		txHashMap _rpc_result_vector_;

	private:
		txRpcEntryBc (const txRpcEntry&);
		txRpcEntryBc& operator= (const txRpcEntry&);

	public:
		txRpcEntryBc (unsigned long rpc_id, txDObjRpcBc* self);
		~txRpcEntryBc (void);

		txDObjRpcBc* self (void)
		{
			return _self_;
		}

		unsigned hash (void) const
		{
			return _rpc_id_;
		}

		int equals (const txObject* obj) const
		{
			return _rpc_id_ == ((txUInt32*) obj)->value();
		}

		int isRpcComplete (void);

		void setStatus (STATUS);

		void setMethodName (const char*);

		void setMethodArgs (const txObject*);

		void addPeerToVector (const txAbsPeer&);

		void removePeerFromVector (const txAbsPeer&);

		void initializeRpcVector (const txPeerList&);

		void updateRpcVectorWithResults (const txAbsPeer&, txObject*);

		STATUS getPeerStatus (const txAbsPeer&);

		txObject* getPeerResult (const txAbsPeer&);

		unsigned long getRpcId (void);

		const txEvent* getEvent (void);

		const char* getMethodName (void);

		const txObject* getMethodArgs (void);

		const txHashMap* getResponseVector (void);
};

class txRpcResultBc : public txObject
{
	public:
		txObject* result;
		txRpcEntryBc::STATUS status;

		~txRpcResultBc (void)
		{
			delete result; result = 0;
		}
};

#endif // __TXDOBJRPCBC_H__ 

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
 
#if !defined ( __TXDOBJECT_H__ )
#define __TXDOBJECT_H__

#include "sys/txdobjrpc.h"
#include "sys/txdobjutil.h"

class txDObjSpace;

class txDObject : public txDObjRpc
{
	TX_DECLARE_DOBJECT(txDObject)

	public:
		enum ARBITRATE
		{
			RENAME, REMOVE, REPLACE
		};

		enum TRANSACTIONS
		{
			NOOP, CHANGE_OWNER, ATTR_WRITE,
			ATTR_WRITE_INDEX, ATTR_DELETE, ATTR_DELETE_INDEX
		};

	private:
		txOut _trans_log_;
		txList* _peer_refs_;
		txAbsPeer* _owner_peer_;
		txDObjSpace* _object_space_;
		unsigned long _rpc_is_pending_;
		unsigned long _local_ref_count_;

	private:
		void incref (void);
		void decref (void);
		void _chown_ (void);
		int refcount (void);

		static txObject* _addRef_ (txDObject*, txIn&,
			const txAbsPeer&);
		static txObject* _removeRef_ (txDObject*, txIn&,
			const txAbsPeer&);
		static txObject* _executeMethod_ (txDObject*, txIn&,
			const txAbsPeer&);
		static txObject* _remoteUpdate_ (txDObject*, txIn&,
			const txAbsPeer&);
		static txObject* _receiveCommit_ (txDObject*, txIn&,
			const txAbsPeer&);

		txObject* _execRpc_ (const char*, const txOut&,
			const txAbsPeer*, signed long& timeout);

		void _destroy_ (void);
		void _notifyUp_ (const txAbsPeer&);
		void _notifyDown_ (const txAbsPeer&);
		void _setSpaceAndManager_ (txDObjSpace*, txDObjMgr*);
	
		void _commit_ (txOut& out);
		void _sendCommit_ (txOut& out);
		void _applyTransactionLog_ (txIn& in, unsigned long);

	public:
		txDObject (void);
		~txDObject (void);
		
		int ownedLocally (void) const;

		const txAbsPeer* owner (void) const;
		const txDObjSpace* space (void) const;
		const txPeerList* activePeers (void) const;
		const txPeerList* interestedPeers (void) const;

		void broadcastStaticOneWayMethod (
			const char*, const txObject* a = 0);
		void broadcastStaticOneWayMethod (
			const char*, const txOut&);

		void invokeStaticOneWayMethod (
			const char*, const txObject* a = 0,
			const txAbsPeer* p = 0);
		void invokeStaticOneWayMethod (
			const char*, const txOut&,
			const txAbsPeer* p = 0);

		txObject* invokeTwoWayMethod (
			const char*, const txObject* a = 0,
			const txAbsPeer* p = 0);
		txObject* invokeTwoWayMethod (
			signed long& timeout,
			const char*, const txObject* a = 0,
			const txAbsPeer* p = 0);
		txObject* invokeTwoWayMethod (
			const char*, const txOut&,
			const txAbsPeer* p = 0);
		txObject* invokeTwoWayMethod (
			signed long& timeout,
			const char*, const txOut&,
			const txAbsPeer* p = 0);

		void attrWrite (
			const char*, const txObject*);
		void attrWriteIndex (
			const char*, const txObject*, const txObject* v = 0);

		void attrDelete (
			const char*);
		void attrDeleteIndex (
			const char*, const txObject*);

		void changeOwner (const txAbsPeer&);

		virtual void attrWriteNotify (
			const char*, const txObject*, const txObject*) {}
		virtual void attrDeleteNotify (
			const char*, const txObject*) {}
		virtual void attrWriteIndexNotify (
			const char*, const txObject*, const txObject*,
			const txObject*) {}
		virtual void attrDeleteIndexNotify (
			const char*, const txObject*, const txObject*) {}

		virtual void changeOwnerNotify (
			const txAbsPeer&, const txAbsPeer&) {}

		virtual void createNotify (void) {}
		virtual void destroyNotify (void) {}

		virtual void contactLostNotify (const txAbsPeer&) {};
		virtual void contactEstablishedNotify (const txAbsPeer&) {};

		virtual ARBITRATE arbitrateObject (txDObject&);
		virtual const txAbsPeer* arbitrateOwnership (void);

		int commit (void);
		int abort (void);

	friend class txDObjSpace;
};

TX_DECLARE_STREAM_TYPE_OPER(txDObject)

#endif // __TXDOBJECT_H__

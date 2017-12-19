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

#include "txstring.h"
#include "txdobject.h"
#include "txdobjmgr.h"
#include "txdobjspace.h"
#include "sys/txdobjptr.h"
#include "sys/txmemspace.h"
 
struct txSyncParm
{
	txAbsPeer* peer;
	txDObjSpace* space;

	txSyncParm (txAbsPeer* dest, txDObjSpace* self) :
		peer(dest), space(self)
	{
	}

	~txSyncParm (void)
	{
		delete peer; peer = 0;
	}
};

int txDObjSpace::_registerFlag_ = 0;

TX_DEFINE_PARENT_TYPE(txDObjSpace,txDObjMsg)

txDObjSpace::txDObjSpace (txDObjMgr* d_mgr, txMemSpace* m_mgr,
	const char* name) :

	txDObjMsg(d_mgr),

	_space_arb_lock_("Space Arb Lock"),
	_space_sync_done_("Space Sync"),
	_is_space_active_(1),
	_sync_count_(0),
	_ref_count_(1)
{
	oid(name);
	term(name);

	_sync_matrix_ = new txSyncMtx(*(getPeer()));

	if (_registerFlag_ == 0)
	{
		registerStaticOneWayMethod("txl",
			(TX_METHOD_PTR) txDObjSpace::_beginSync_);
		registerStaticOneWayMethod("txm",
			(TX_METHOD_PTR) txDObjSpace::_sync_);
		registerStaticOneWayMethod("txn",
			(TX_METHOD_PTR) txDObjSpace::_endSync_);
		registerStaticOneWayMethod("txo",
			(TX_METHOD_PTR) txDObjSpace::_objTransfer_);
		registerStaticOneWayMethod("txp",
			(TX_METHOD_PTR) txDObjSpace::_objDelete_);
		registerStaticOneWayMethod("txq",
			(TX_METHOD_PTR) txDObjSpace::_updStatus_);
		registerStaticOneWayMethod("txr",
			(TX_METHOD_PTR) txDObjSpace::_updPeerStatus_);

		_registerFlag_ = 1;
	}	

	_addToSortedPeers_(_active_peers_, *getPeer());

	txMemSpace* space = (txMemSpace*) m_mgr->accessSpace("TX");
	space->registerObject(this);

	_space_space_= (txMemSpace*) m_mgr->allocateSpace(name);
	_local_space_= (txMemSpace*) _space_space_->allocateSpace(getId());

	_updateLocalStatus_(*getPeer(), txSyncMtx::SYNC_COMPLETE);
}

txDObjSpace::~txDObjSpace (void)
{
	_is_space_active_ = 0;

	_waitForSync_();

	_active_peers_.clearAndDestroy();
	_interested_peers_.clearAndDestroy();

	_local_space_ = 0;
	delete _space_space_; _space_space_ = 0;
	delete _sync_matrix_; _sync_matrix_ = 0;
}

void txDObjSpace::incref (void)
{
	_ref_count_++;
}

void txDObjSpace::decref (void)
{
	_ref_count_--;
}

int txDObjSpace::refcount (void)
{
	return _ref_count_;
}

int txDObjSpace::_spaceState_ (void)
{
	if (!_isSpaceActive_())
	{
		return txSyncMtx::UNKNOW;
	}
	else
	{
		return _sync_matrix_->get(*getPeer(), *getPeer());
	}
}

int txDObjSpace::_waitForSync_ (void)
{
	while (_isSpaceActive_() && !_sync_matrix_->synced())
	{
		txThread::yield(_space_sync_done_);
	}

	return _isSpaceActive_();
}

int txDObjSpace::_isSpaceActive_ (void) const
{
	return _is_space_active_;
}

void txDObjSpace::_allocateObject_ (txDObject* object)
{
	_local_space_->allocateObject(object);

	object->_setSpaceAndManager_(this, _d_mgr);

	object->_local_ref_count_++;

	_objectTransferMessage_(object);

	object->createNotify();

	object->_local_ref_count_--;
}

void txDObjSpace::_unregisterObject_ (txDObject* object)
{
	_space_space_->unregisterObject(object);

	object->_setSpaceAndManager_(0, 0);
}

void txDObjSpace::_deallocateObject_ (txDObject* object)
{
	object->accessible(0);

	_space_space_->unregisterObject(object);

	_notifyObjectOfDeletion_(object);

	_objectDeleteMessage_(object);

	object->_setSpaceAndManager_(0, 0);

	delete object; object = 0;
}

void txDObjSpace::_registerGlobalObject_ (txDObject* object)
{
	object->_setSpaceAndManager_(this, _d_mgr);

	if (_space_space_->registerObject(object))
	{
		object->_local_ref_count_++;

		_objectTransferMessage_(object);

		object->createNotify();

		object->_local_ref_count_--;
	}
	else
	{
		_arbitrateObject_(object);
	}
}

void txDObjSpace::_registerObject_ (txDObject* object, int notify_flag)
{
	object->_setSpaceAndManager_(this, _d_mgr);

	if (_space_space_->registerObject(object))
	{
		if (notify_flag)
		{
			_notify_list_.append(object);
		}
		else
		{
			object->createNotify();
		}
	}
	else
	{
		_arbitrateObject_(object);
	}
}

txDObject* txDObjSpace::_arbitrateObject_ (txDObject* object)
{
	txDObjPtr* oid;
	txAbsPeer* peer;
	txDObject* current;
	txDObject::ARBITRATE arbitrate;

	_space_arb_lock_.acquire();

	if (current = (txDObject*) _space_space_->accessObject(object->oid()))
	{
		arbitrate = current->arbitrateObject(*object);

		if (arbitrate == txDObject::RENAME)
		{
			object->oid(0);
			object->term(0);

			_space_space_->registerObject(object);
		}
		else if (arbitrate == txDObject::REMOVE)
		{
			delete object; object = 0;
		}
		else if (arbitrate == txDObject::REPLACE)
		{
			if (!object->_peer_refs_)
			{
				object->_peer_refs_ =
					new txList(TX_AUTODEL_ON);
			}

			object->_local_ref_count_ = current->_local_ref_count_;

			while (peer = (txAbsPeer*) current->_peer_refs_->get())
			{
				if (!object->_peer_refs_->find(peer))
				{
					object->_peer_refs_->append(peer);
				}
				else
				{
					delete peer; peer = 0;
				}
			}

			_space_space_->unregisterObject(current);
			_notify_list_.removeReference(current);
			_space_space_->registerObject(object);

			object->_chown_();

			if (oid = txDObjPtr::getPtr(current))
			{
				oid->reRef(object); // may context switch

				delete current; current = 0;
			}
			else
			{
				delete current; current = 0;
			}

			current = object;
		}
	}
	else
	{
		delete object; object = 0;
	}

	_space_arb_lock_.release();

	return current;
}

void txDObjSpace::_synchronizeTo_ (void* obj)
{
	txSyncParm* parm = (txSyncParm*) obj;
	txDObjSpace* self = (txDObjSpace*) parm->space;

	self->_updateLocalStatus_(*parm->peer, txSyncMtx::SYNC);

	self->_beginSyncMessage_(*parm->peer);

	self->_syncMessage_(*parm->peer);

	self->_endSyncMessage_(*parm->peer);

	self->_updateLocalStatus_(*parm->peer, txSyncMtx::SYNC_COMPLETE);

	self->_checkForLocalSynchronization_();

	delete parm; parm = 0;
}

void txDObjSpace::_objectDeleteMessage_ (txDObject* object)
{
	if (_interested_peers_.entries())
	{
		txOut out;

		out << oidObject();
		out.put("txp", 3, TX_ASCII_STRING);
		out << object->oidObject();

		broadcast(out);
	}
}

void txDObjSpace::_objectTransferMessage_ (txDObject* object)
{
	if (_interested_peers_.entries())
	{
		txOut out;

		out << oidObject();
		out.put("txo", 3, TX_ASCII_STRING);
		out << object;

		broadcast(out);
	}
}

void txDObjSpace::_addInterestedPeer_ (const txAbsPeer& peer)
{
	if (_isSpaceActive_() && !_interested_peers_.find(&peer))
	{
		_addToSortedPeers_(_active_peers_, peer);

		_addToSortedPeers_(_interested_peers_, peer);

		_space_space_->allocateSpace(peer.getId());

		_notifyObjectsPeerUp_(peer);
	}
}

int txDObjSpace::_removeInterestedPeer_ (const txAbsPeer& peer)
{
	txMemSpace* space;
	int incomplete = 0;
	txSyncMtx::STATE state = txSyncMtx::SYNC_COMPLETE;

	if (_isSpaceActive_() && _interested_peers_.find(&peer))
	{
		if (state != _sync_matrix_->get(peer, *getPeer()))
		{
			incomplete = 1;
		}

		_sync_matrix_->del(peer);

		_removeSortedPeer_(_active_peers_, peer);
		_removeSortedPeer_(_interested_peers_, peer);

		_checkForLocalSynchronization_();

		_notifyObjectsPeerDown_(peer);

		txDObjRpc::flushAffectedRpcs(peer);

		if (_space_space_) // if 0, space is being deleted
		{
			space = (txMemSpace*) _space_space_->accessSpace(
				peer.getId());

			if (space->entries() == 0)
			{
				_space_space_->deallocateObject(space);
			}
		}
	}

	return incomplete;
}

void txDObjSpace::_notifyObjectsOfCreation_ (void)
{
	txDObject* object;

	while (object = (txDObject*) _notify_list_.get())
	{
		object->createNotify();
	}
}

void txDObjSpace::_notifyObjectOfDeletion_ (txDObject* object)
{
	if (_notify_list_.entries())
	{
		_notify_list_.removeReference(object);
	}

	object->destroyNotify();
}

void txDObjSpace::_notifyObjectsPeerUp_ (const txAbsPeer& peer)
{
	txDObject* object;
	txMemIterator iter(*_space_space_);

	while (object = (txDObject*) iter.next())
	{
		if (object->isSubClass(txDObject::Type))
		{
			object->_notifyUp_(peer);
		}
	}
}

void txDObjSpace::_notifyObjectsPeerDown_ (const txAbsPeer& peer)
{
	txDObject* object;
	txMemIterator iter(*_space_space_);

	while (object = (txDObject*) iter.next())
	{
		if (object->isSubClass(txDObject::Type))
		{
			object->_notifyDown_(peer);
		}
	}
}

void txDObjSpace::_checkForLocalSynchronization_ (void)
{
	txSyncMtx::STATE state = txSyncMtx::SYNC_COMPLETE;

	if (0 == _interested_peers_.entries())
	{
		_sync_matrix_->set(*getPeer(), *getPeer(), state);

	}
	else if ((state != _sync_matrix_->get(*getPeer(), *getPeer())) &&
		_sync_matrix_->syncedLocally())
	{
		_sync_matrix_->set(*getPeer(), *getPeer(), state);

		txOut out;

		out << oidObject();
		out.put("txr", 3, TX_ASCII_STRING);
		out << _sync_matrix_->getRow(*getPeer());
	 
		broadcast(out);
	}

	if (!_sync_count_ && _sync_matrix_->synced())
	{
		_space_sync_done_.trigger();
		_notifyObjectsOfCreation_();
		_sync_notify_.trigger();
	}
}

void txDObjSpace::_flushObjectRpcs_ (void)
{
	txDObject* object;
	txMemIterator iter(*_space_space_);

	while (object = (txDObject*) iter.next())
	{
		if (object->isSubClass(txDObject::Type))
		{
			object->_setSpaceAndManager_(0, 0);
		}
	}
}

void txDObjSpace::_addToSortedPeers_ (txPeerList& list, const txAbsPeer& peer)
{
	list.append(peer.clone());

	txSortPeerList(&list, 0, list.entries() - 1);
}

void txDObjSpace::_removeSortedPeer_ (txPeerList& list, const txAbsPeer& peer)
{
	list.removeAndDestroy(&peer);
}

void txDObjSpace::_startOutGoingSync_ (const txAbsPeer& peer)
{
	_updateLocalStatus_(peer, txSyncMtx::SYNC_INITIAL);
 
	_updateLocalStatus_(*getPeer(), txSyncMtx::SYNC);

	txThread::start(
		txDObjSpace::_synchronizeTo_, new txSyncParm(peer.clone(),
		this), "SyncTo", _d_mgr->baseThreadPriority() + 2);
}

txObject* txDObjSpace::_beginSync_ (txDObjSpace* self, txIn& in,
	const txAbsPeer& caller)
{
	if (self->_isSpaceActive_())
	{
		self->_updateLocalStatus_(caller, txSyncMtx::SYNC);

		self->_addInterestedPeer_(caller);
	}

	return 0;
}

txObject* txDObjSpace::_sync_ (txDObjSpace* self, txIn& in,
	const txAbsPeer& caller)
{
	if (self->_isSpaceActive_())
	{
		self->_processSync_(in);
	}

	return 0;
}
	
void txDObjSpace::_processSync_ (txIn& in)
{
	txDObject* object;

	_sync_count_++;

	in >> object;

	while (object)
	{
		_registerObject_(object, 1);

		in >> object;
	}

	_sync_count_--;

	if (!_sync_count_)
	{
		_checkForLocalSynchronization_();
	}
}

txObject* txDObjSpace::_endSync_ (txDObjSpace* p, txIn& in,
	const txAbsPeer& caller)
{
	if (p->_isSpaceActive_())
	{
		txSyncMtx::STATE state = txSyncMtx::SYNC_COMPLETE;
		p->_sync_matrix_->set(*p->getPeer(), caller, state);

		txOut out;

		out << p->oidObject();
		out.put("txr", 3, TX_ASCII_STRING);
		out << p->_sync_matrix_->getRow(*p->getPeer());

		p->broadcast(out);
	}	

	return 0;
}

txObject* txDObjSpace::_objTransfer_ (txDObjSpace* self, txIn& in,
	const txAbsPeer& caller)
{
	if (self->_isSpaceActive_())
	{
		self->_processObjectTransfer_(in);
	}

	return 0;
}

void txDObjSpace::_processObjectTransfer_ (txIn& in)
{
	txDObject* object;

	_sync_count_++;

	in >> object;

	if (object)
	{
		_registerObject_(object);
	}

	_sync_count_--;

	if (!_sync_count_ && _isSpaceActive_())
	{
		_checkForLocalSynchronization_();
	}
}

txObject* txDObjSpace::_objDelete_ (txDObjSpace* self, txIn& in,
	const txAbsPeer&)
{
	if (self->_isSpaceActive_())
	{
		self->_processObjectDelete_(in);
	}

	return 0;
}

void txDObjSpace::_processObjectDelete_ (txIn& in)
{
	char* oid;
	txDObject* obj;

	in >> oid;

	if (oid)
	{
		if ((obj = (txDObject*) accessObject(oid)) &&
			obj->refcount() < 1)
		{ 
			obj->accessible(0);

			_space_space_->unregisterObject(obj);

			_notifyObjectOfDeletion_(obj);

			obj->_setSpaceAndManager_(0, 0);

			delete obj; obj = 0;
		}

		delete oid; oid = 0;
	}
}

txObject* txDObjSpace::_updStatus_ (txDObjSpace* self, txIn& in,
	const txAbsPeer& row)
{
	txAbsPeer* col;
	signed short int s;
	txSyncMtx::STATE state;
	txPeerList* active_peers;

	in >> col; in >> s; state = (txSyncMtx::STATE) s;

	active_peers = (txPeerList*) self->_d_mgr->activePeers();

	if (self->_isSpaceActive_() && active_peers->find(&row))
	{
		self->_sync_matrix_->set(row, *col, state);
		self->_checkForLocalSynchronization_();
	}
	
	delete col; col = 0;
	return 0;
}
	
txObject* txDObjSpace::_updPeerStatus_ (txDObjSpace* self, txIn& in,
	const txAbsPeer& caller)
{
	txPeerList* list = (txPeerList*) self->_d_mgr->activePeers();

	if (self->_isSpaceActive_() && list->find(&caller))
	{
		self->_processUpdatePeerStatus_(in, caller);
		self->_checkForLocalSynchronization_();
	}

	return 0;
}
 
void txDObjSpace::_processUpdatePeerStatus_ (txIn& in,
	const txAbsPeer& in_peer)
{
	txAbsPeer* peer;
	txPeerList* servers;
	txPeerList inactive;
	txStateVector* vector;

	servers = (txPeerList*) _d_mgr->activePeers();

	in >> vector;

	if (vector)
	{
		txHashMapIterator iter((txHashMap&) *vector->getTable());

		while (peer = (txAbsPeer*) iter.next())
		{
			if (getPeer()->equals(peer))
			{
				continue;
			}

			if (servers->find(peer) && !_active_peers_.find(peer))
			{
				_addInterestedPeer_(*peer);

				if (!_isSpaceActive_())
				{
					return;
				}
			}

			if (!servers->find(peer))
			{
				inactive.append(peer);
			}
		}

		while (peer = (txAbsPeer*) inactive.get())
		{
			vector->del(*peer);
		}

		_sync_matrix_->setRow(in_peer, *vector);

		delete vector; vector = 0;
	}
}

void txDObjSpace::_beginSyncMessage_ (const txAbsPeer& peer)
{
	txOut out;

	out << oidObject();
	out.put("txl", 3, TX_ASCII_STRING);

	_sendMessage_(out, peer);
}

int txDObjSpace::_isPeerActive_ (const txAbsPeer& peer)
{
	return _interested_peers_.find(&peer) ? 1 : 0;
}

void txDObjSpace::_syncMessage_ (const txAbsPeer& peer)
{
	txOut out;
	int packets = 0;
	txDObject* obj = 0;
	const char* m = "txm";
	const txString* o = oidObject();

	if (!_isPeerActive_(peer))
	{
		return;
	}

	out << o; out.put(m, 3, TX_ASCII_STRING);

	txMemIterator iter(*_space_space_);

	while (obj = (txDObject*) iter.next())
	{
		if (obj->isSubClass(txDObject::Type) && obj->ownedLocally())
		{
			out << obj; packets++;

			if (out.length() >= 4096)
			{
				packets = 0;

				_sendMessage_(out, peer);

				out.flush();

				if (!_isPeerActive_(peer))
				{
					return;
				}

				out << o; out.put(m, 3, TX_ASCII_STRING);
			}
		}
	}

	if (packets)
	{
		_sendMessage_(out, peer);
	}
}

void txDObjSpace::_endSyncMessage_ (const txAbsPeer& peer)
{
	txOut out;

	out << oidObject(); out.put("txn", 3, TX_ASCII_STRING);

	_sendMessage_(out, peer);
}

void txDObjSpace::_updateLocalStatus_ (const txAbsPeer& peer,
	txSyncMtx::STATE state)
{
	_sync_matrix_->set(*getPeer(), peer, state);

	if (_interested_peers_.entries())
	{
		txOut out;

		out << oidObject();
		out.put("txq", 3, TX_ASCII_STRING);
		out << peer;
		out << (signed short int) state;

		broadcast(out);
	}
}

void txDObjSpace::_sendMessage_ (txOut& out, const txAbsPeer& peer)
{
	if (!peer.equals(getPeer()))
	{
		sendTo(out, peer);
	}
}

int txDObjSpace::synced (void) const
{
	if (!_isSpaceActive_() || !_sync_matrix_->synced())
	{
		return 0;
	}

	return 1;
}

const txPeerList* txDObjSpace::activePeers (void) const
{
	return &_active_peers_;
}

const txPeerList* txDObjSpace::interestedPeers (void) const
{
	return &_interested_peers_;
}

const txDObject* txDObjSpace::accessObject (const char* oid) const
{
	return (txDObject*) _space_space_->accessObject(oid);
}

void txDObjSpace::objects (txList& list) const
{
	txDObject* object;
	txMemIterator iter(*_space_space_);

	while (object = (txDObject*) iter.next())
	{
		if (object->isSubClass(txDObject::Type) &&
			(object = (txDObject*) object->getReference()))
		{
			list.append(object);
		}
	}
}

void txDObjSpace::registerForSyncNotify (const TX_CBK_FUNC f, void* a)
{
	_sync_notify_.registerCb(f, a);
}

void txDObjSpace::unregisterForSyncNotify (const TX_CBK_FUNC f)
{
	_sync_notify_.unregisterCb(f);
}


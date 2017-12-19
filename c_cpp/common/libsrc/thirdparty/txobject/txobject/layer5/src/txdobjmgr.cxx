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

#include "txuint32.h"
#include "txstring.h"
#include "txdobject.h"
#include "txdobjmgr.h"
#include "txabspeer.h"
#include "txdobjmgr.h"
#include "txpeerlist.h"
#include "txdobjspace.h"
#include "sys/txstatemgr.h"
#include "sys/txstateobj.h"

int txDObjMgr::_registerFlag_ = 0;

TX_DEFINE_PARENT_TYPE(txDObjMgr,txDObjRpcBc)

txDObjMgr::txDObjMgr (const txAbsPeer& local, const txPeerList& peers,
	const txList& spaces, const TX_CBK_FUNC up_notification,
	const TX_CBK_FUNC down_notification,
	const TX_CBK_FUNC error_notification,
	int static_peers, int pri, int timeout) :

	_space_space_(0),
	_thread_pri_(pri),
	_mem_mgr_("ROOT"),
	_static_peers_(static_peers),
	_list_of_spaces_(TX_AUTODEL_ON),
	_comm_layer_(_mem_mgr_, local, pri, timeout)
{
	_d_mgr = this;
	_state_mgr_ = new txStateMgr(this);

	if (_registerFlag_ == 0)
	{
		registerStaticOneWayMethod("txe",
			(TX_METHOD_PTR) txDObjMgr::_joinSpaceBCast_);
		registerStaticOneWayMethod("txf",
			(TX_METHOD_PTR) txDObjMgr::_leaveSpaceBCast_);

		_registerFlag_ = 1;
	}

	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	//
	// 

	_space_space_ = (txMemSpace*) _mem_mgr_.allocateSpace("TX");

	oid("OM");
	term("OM");
	_mem_mgr_.registerObject(this);

	_state_mgr_->oid("SM");
	_state_mgr_->term("SM");
	_mem_mgr_.registerObject(_state_mgr_);

	registerNotifyUp(up_notification, this);
	registerNotifyDown(down_notification, this);
	registerErrorCallback(error_notification, this);

	//
	//
	//////////////////////////////////////////////////

	_state_mgr_->initialize(peers);

	txString* name;
	txListIterator iter((txList&) spaces);

	while (name = (txString*) iter.next())
	{
		_joinSpace_(name->data());
	}

	_waitForSpaceSynchronization_();

	txDObjGlobals::txDObjMgrs.insertKeyAndValue(local.clone(), this);
}

txDObjMgr::~txDObjMgr (void)
{
	txObject* key = txDObjGlobals::txDObjMgrs.remove(getPeer());
	delete key; key = 0;

	shutDown();

	txString* space;
	txListIterator iter(_list_of_spaces_);
 
	while (space = (txString*) iter.next())
	{
		leaveSpace(space->data());

		iter.reset();
	}
 
	txThread::yield();

	_mem_mgr_.deallocateObject(_space_space_);

	_mem_mgr_.unregisterObject(this);
	_mem_mgr_.unregisterObject(_state_mgr_);

	delete _state_mgr_; _state_mgr_ = 0;
}

void txDObjMgr::_joinSpace_ (const char* space_name)
{
	_createDObjSpace_(space_name);

	_broadcastJoinSpace_(space_name);

	_waitForSpaceSynchronization_(space_name);
}
 
void txDObjMgr::_waitForSpaceSynchronization_ (void)
{
	txDObjSpace* space = 0;
	txMemIterator iter(*_space_space_);

	while (space = (txDObjSpace*) iter.next())
	{
		_waitForSpaceSynchronization_(space->oid());
	}
}

void txDObjMgr::_waitForSpaceSynchronization_ (const char* space_name)
{
	txDObjSpace* space = (txDObjSpace*) accessSpace(space_name);

	if (space)
	{
		space->_waitForSync_();
	}
}

void txDObjMgr::_createDObjSpace_ (const char* space_name)
{
	_space_space_->registerObject(
		new txDObjSpace(this, &_mem_mgr_, space_name));
}
 
void txDObjMgr::_broadcastJoinSpace_ (const char* space_name)
{
	txUInt32* result;
	txAbsPeer* peer;
	txHashMap* vector;
	txDObjSpace* space;
	txRpcEntryBc* entry;
	txRpcResultBc* tuple;
	txString name(space_name);

	entry = broadcastTwoWayMethod("txe", &name);

	if (entry)
	{
		vector = (txHashMap*) entry->getResponseVector();

		txHashMapIterator iter(*vector);

		while (peer = (txAbsPeer*) iter.next())
		{
			tuple = (txRpcResultBc*) iter.value();
			result = (txUInt32*) tuple->result;

			if (result && result->value() == 1)
			{
				if (space =
					(txDObjSpace*) accessSpace(space_name))
				{	
					space->_addInterestedPeer_(*peer);
				}
			}
		}

		delete entry; entry = 0;
	}
}

void txDObjMgr::_sendJoinSpaceMsgTo_ (const txAbsPeer& peer)
{
	txDObjSpace* space;

	txMemIterator iter(*_space_space_);

	while (space = (txDObjSpace*) iter.next())
	{
		_sendJoinSpaceMsgTo_(peer, space->oid());
	}
}

void txDObjMgr::_sendJoinSpaceMsgTo_ (const txAbsPeer& peer,
	const char* space_name)
{
	txUInt32* result;
	txDObjSpace* space;
	txRpcResult rpc_result;
	txOut out; out.put("txe", 3, TX_ASCII_STRING); out << space_name;

	sendRpcMessage(rpc_result, out, peer);
 
	result = (txUInt32*) rpc_result.result();

	if (result && result->value())
	{
		if (space = (txDObjSpace*) accessSpace(space_name))
		{
			space->_addInterestedPeer_(peer);
		}
	}

	delete result; result = 0;
}

void txDObjMgr::_contactEstablished_ (const txAbsPeer& peer)
{
	_sendJoinSpaceMsgTo_(peer);

	_conn_up_notifer_.trigger(&(txAbsPeer&) peer);
}

void txDObjMgr::_contactLost_ (const txAbsPeer& peer)
{
	txDObjSpace* space = 0;

	removeBroadcastPeer(peer);

	txMemIterator iter(*_space_space_);

	while (space = (txDObjSpace*) iter.next())
	{
		if (space->_removeInterestedPeer_(peer) && !staticPeers())
		{
			txDObjError* error = new txDObjError(
				txDObjError::INCOMPLETE_SYNC,
				peer.clone());

			triggerErrorCallback(error);
		}
	}

	_conn_down_notifer_.trigger(&(txAbsPeer&) peer);
}

const txMemObj* txDObjMgr::_accessObject_ (const char* oid)
{
	return _mem_mgr_.accessObject(oid);
}

void txDObjMgr::_deallocate_ (txDObject* object)
{
	txDObjSpace* space = (txDObjSpace*) object->space();

	space->_deallocateObject_(object);
}

void txDObjMgr::_allocate_ (const char* space_name, txDObject* object)
{
	txDObjSpace* space = (txDObjSpace*) accessSpace(space_name);

	if (!space)
	{
		joinSpace(space_name);

		space = (txDObjSpace*) accessSpace(space_name);
	}

	if (space)
	{
		object->changeOwner(*getPeer());

		space->_allocateObject_(object);
	}
}

void txDObjMgr::_registerObject_ (const char* space_name, txDObject* object)
{
	txDObjSpace* space = (txDObjSpace*) accessSpace(space_name);

	if (!space)
	{
		joinSpace(space_name);

		space = (txDObjSpace*) accessSpace(space_name);
	}

	if (space)
	{
		object->changeOwner(*getPeer());

		space->_registerGlobalObject_(object);
	}
}

txObject* txDObjMgr::_joinSpaceBCast_ (txDObjMgr* self, txIn& in,
	const txAbsPeer& peer)
{
	txDObjSpace* space;
	txString space_name;
	txUInt32* space_response;

	in >> space_name;

	space = (txDObjSpace*) self->accessSpace(space_name.data());

	if (space)
	{
		space_response = new txUInt32(1);
		space->_addInterestedPeer_(peer);
		space->_startOutGoingSync_(peer);
	}
	else
	{
		space_response = new txUInt32(0);
	}

	return space_response;
}

txObject* txDObjMgr::_leaveSpaceBCast_ (txDObjMgr* self, txIn& in,
	const txAbsPeer& peer)
{
	txDObjSpace* space;
	txString space_name;

	in >> space_name;

	space = (txDObjSpace*) self->accessSpace(space_name.data());

	if (space && space->_removeInterestedPeer_(peer))
	{
		txDObjError* error = new txDObjError(
				txDObjError::INCOMPLETE_SYNC,
				peer.clone());

		self->triggerErrorCallback(error);
	}

	return 0;
}

void txDObjMgr::_broadcastLeaveSpace_ (const char* space_name)
{
	const txPeerList* list;

	list = _state_mgr_->activePeers();

	if (list && list->entries() > 0)
	{
		txOut out;
		out << oidObject();
		out.put("txf", 3, TX_ASCII_STRING);
		out << space_name;

		broadcast(out);
	}
}

int txDObjMgr::synced (void) const
{
	int s = 1;
	txDObjSpace* space = 0;

	txMemIterator iter(*_space_space_);

	while (s && (space = (txDObjSpace*) iter.next()))
	{
		s = space->synced();
	}

	return s;
}

int txDObjMgr::determinate (void) const
{
	return _state_mgr_->determinate();
}

int txDObjMgr::isOidRegistered (const char* oid) const
{
	return 0;
}

int txDObjMgr::addPeer (const txAbsPeer& peer)
{
	int status = 0;

	if (staticPeers())
	{
		txPeerList* peers = (txPeerList*) _state_mgr_->staticPeers();

		if (!peers->find(&peer))
		{
			peers->append(peer.clone());
		}

		txAbsPeer* p;
		txPeerListIterator iter(*peers);

		while (p = (txAbsPeer*) iter.next())
		{
			_comm_layer_.close(*p);
		}

		if (!getPeer()->equals(&peer))
		{
			_state_mgr_->addPeer(peer);
		}
	}

	if (!getPeer()->equals(&peer))
	{
		status = _comm_layer_.contact(peer);
	}

	return status;
}

int txDObjMgr::removePeer (const txAbsPeer& peer, int stop_ping)
{
	int status = 0;

	if (staticPeers())
	{
		txPeerList* peers = (txPeerList*) _state_mgr_->staticPeers();
		peers->removeAndDestroy(&peer);

		txAbsPeer* p;
		txPeerListIterator iter(*peers);

		if (!getPeer()->equals(&peer))
		{
			_state_mgr_->removePeer(peer);
			_comm_layer_.close(peer);
		}

		while (p = (txAbsPeer*) iter.next())
		{
			_comm_layer_.close(*p);
		}

		status =  1;
	}
	else if (!getPeer()->equals(&peer))
	{
		_state_mgr_->removePeer(peer);

		if (stop_ping)
		{
			txIpPeer* udp = (txIpPeer*) peer.clone();

			udp->setType(txAbsPeer::UDP);

			_comm_layer_.close(*udp);

			delete udp; udp = 0;
		}

		status =  1;
	}

	return status;
}

void txDObjMgr::joinSpace (const char* space_name)
{
	txDObjSpace* space = (txDObjSpace*) accessSpace(space_name);

	if (space)
	{
		space->incref();
	}
	else
	{
		_joinSpace_(space_name);
	}

	txString name(space_name);

	if (!_list_of_spaces_.find(&name))
	{
		_list_of_spaces_.append(new txString(space_name));
	}
}

void txDObjMgr::leaveSpace (const char* space_name)
{
	txDObjSpace* space = (txDObjSpace*) accessSpace(space_name);

	if (space)
	{
		space->decref();

		if (space->refcount() == 0)
		{
			space->_flushObjectRpcs_();

			_broadcastLeaveSpace_(space_name);

			_space_space_->deallocateObject(space);
		}
	}

	txString name(space_name);

	_list_of_spaces_.removeAndDestroy(&name);
}

const txList* txDObjMgr::spacesOfInterest (void) const
{
	return &_list_of_spaces_;
}

const txPeerList* txDObjMgr::activePeers (void) const
{
	return _state_mgr_->activePeers();
}

const txPeerList* txDObjMgr::allKnownPeers (void) const
{
	return _state_mgr_->allKnownPeers();
}

const txStateCtMtx* txDObjMgr::getStatusMatrix (void) const
{
	return _state_mgr_->getStatusMatrix();
}

txDObject* txDObjMgr::accessObject (const char* oid)
{
	txDObject* object;

	if (object = (txDObject*) _accessObject_(oid))
	{
		object = (txDObject*) object->getReference();
	}

	return object;
}

const txDObjSpace* txDObjMgr::accessSpace (const char* name)
{
	return (txDObjSpace*) _space_space_->accessObject(name);
}

txDObject* txDObjMgr::registerObject (txDObject* obj, const char* space)
{
	_allocate_(space, obj);

	return (txDObject*) obj->getReference();
}

txDObject* txDObjMgr::registerGlobalObject (txDObject* obj,
	const char* global_name, const char* space_name)
{
	char* name;
	txDObject* global_obj = 0;
	char separator = getMemMgr()->getSpaceSeparator();

	int length_space = strlen(space_name);
	int length_global = strlen(global_name);

	name = new char[length_space+length_global+2];

	memcpy(name, space_name, length_space);
	memcpy(name+length_space, &separator, 1);
	memcpy(name+length_space+1, global_name, length_global);

	name[length_space+length_global+1] = '\0';

	if (!accessSpace(space_name))
	{
		joinSpace(space_name);
	}

	if (!(global_obj = accessObject(name)))
	{
		if (!(global_obj = accessObject(name)))
		{
			obj->oid(name);
			obj->term(global_name);
			_registerObject_(space_name, obj);
			global_obj = accessObject(name);
		}
		else
		{
			delete obj; obj = 0;
		}
	}
	else
	{
		delete obj; obj = 0;
	}

	delete name; name = 0;

	return global_obj;
}

txDObject* txDObjMgr::unregisterObject (const char* oid)
{
	return 0;
}

void txDObjMgr::triggerErrorCallback (void* error_info)
{
	_error_callback_.trigger(error_info);
}

void txDObjMgr::registerErrorCallback (const TX_CBK_FUNC call_back_func,
	void* args)
{
	_error_callback_.registerCb(call_back_func, args);
}

void txDObjMgr::unregisterErrorCallback (const TX_CBK_FUNC call_back_func)
{
	_error_callback_.unregisterCb(call_back_func);
}

void txDObjMgr::registerNotifyUp (const TX_CBK_FUNC call_back_func,
	void* args)
{
	_conn_up_notifer_.registerCb(call_back_func, args);
}

void txDObjMgr::registerNotifyDown (const TX_CBK_FUNC call_back_func,
	void* args)
{
	_conn_down_notifer_.registerCb(call_back_func, args);
}

void txDObjMgr::unregisterNotifyUp (const TX_CBK_FUNC call_back_func)
{
	_conn_up_notifer_.unregisterCb(call_back_func);
}

void txDObjMgr::unregisterNotifyDown (const TX_CBK_FUNC call_back_func)
{
	_conn_down_notifer_.unregisterCb(call_back_func);
}

void txDObjMgr::shutDown (void)
{
	_comm_layer_.close();
}


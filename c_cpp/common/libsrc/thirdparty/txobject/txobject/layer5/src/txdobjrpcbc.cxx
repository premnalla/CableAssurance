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
#include "txthread.h"
#include "txstring.h"
#include "txabspeer.h"
#include "txdobjmgr.h"
#include "txpeerlist.h"
#include "sys/txdobjrpcbc.h"
 
struct txDObjRpcBcTokens
{
	txAbsPeer* peer;
	txDObjRpcBc* self;
	txRpcEntryBc* entry;
};

txHashTable txDObjRpcBc::_rpc_bc_entries_;

TX_DEFINE_PARENT_TYPE(txDObjRpcBc,txDObjRpc)

txDObjRpcBc::txDObjRpcBc (txDObjMgr* d_mgr) :
	txDObjRpc(d_mgr), _rpc_id_count_(0)
{
}

txDObjRpcBc::~txDObjRpcBc (void)
{
}

txRpcEntryBc* txDObjRpcBc::_setupTable_ (void)
{
	txPeerList* peers;
	txRpcEntryBc* entry;

	_rpc_id_count_++;

	entry = new txRpcEntryBc(_rpc_id_count_, this);
	
	peers = (txPeerList*) interestedPeers();

	entry->initializeRpcVector(*peers);

	_rpc_bc_entries_.insert(entry);

	return entry;
}

void txDObjRpcBc::_put_ (unsigned long rpc_id, const txAbsPeer& peer,
	txObject* rpc_result)
{
	txUInt32 id(rpc_id);
	txRpcEntryBc* entry;

	if (entry = (txRpcEntryBc*) _rpc_bc_entries_.find(&id))
	{
		entry->updateRpcVectorWithResults(peer, rpc_result);

		txDObjRpcBc::_checkForAllResultsReceived_(*entry);
	}
}

void txDObjRpcBc::_checkForAllResultsReceived_ (txRpcEntryBc& entry)
{
	if (entry.isRpcComplete())
	{
		((txEvent*) entry.getEvent())->trigger();
	}
}

void txDObjRpcBc::_callSingleParallelRpc_ (void* obj)
{
	txOut out;
	txRpcResult rpc;
	txDObjRpcBcTokens* toks = (txDObjRpcBcTokens*) obj;

	txAbsPeer* peer = toks->peer;
	txDObjRpcBc* self = toks->self;
	txRpcEntryBc* entry = toks->entry;

	delete toks; toks = 0;

	txUInt32 id(entry->getRpcId());

	if (!_rpc_bc_entries_.find(&id))
	{
		delete peer; peer = 0; return;
	}

	out << entry->getMethodName();

	if (entry->getMethodArgs())
	{
		out << entry->getMethodArgs();
	}

	self->sendRpcMessage(rpc, out, *peer);

	_put_(id.value(), *peer, rpc.result());

	delete peer; peer = 0;
}

void txDObjRpcBc::_flushBroadcastRpcs (void)
{
	txRpcEntryBc* entry;
	txHashTableIterator iter(_rpc_bc_entries_);

	while (entry = (txRpcEntryBc*) iter.next())
	{
		if (entry->self() == this)
		{
			iter.remove();

			delete entry; entry = 0;

			txThread::yield();
		}
	}
}

const txPeerList* txDObjRpcBc::interestedPeers (void) const
{
	return _d_mgr->activePeers();
}

txRpcEntryBc* txDObjRpcBc::broadcastTwoWayMethod (const char* method,
	const txObject* args)
{
	txEvent* event;
	txAbsPeer* peer;
	txRpcEntryBc* entry;
	const txPeerList* peers;

	entry = _setupTable_();
	entry->setMethodName(method);
	entry->setMethodArgs(args);

	peers = interestedPeers();

	txPeerListIterator iter((txPeerList&) *peers);

	while (peer = (txAbsPeer*) iter.next())
	{
		txDObjRpcBcTokens* toks = new txDObjRpcBcTokens();
		toks->peer = peer->clone();
		toks->entry = entry;
		toks->self = this;

		txThread::start(
			txDObjRpcBc::_callSingleParallelRpc_,
			toks, "_callSingleParallelRpc_",
			_d_mgr->baseThreadPriority() + 2);
	}

	txUInt32 id(entry->getRpcId());

	event = (txEvent*) entry->getEvent();

	if (peers->entries())
	{
		txThread::yield(*event);
	}

	return (txRpcEntryBc*) _rpc_bc_entries_.remove(&id);
}

void txDObjRpcBc::addBroadcastPeer (const txAbsPeer& peer)
{
	txRpcEntryBc* entry;
	txHashTableIterator iter(_rpc_bc_entries_);

	while (entry = (txRpcEntryBc*) iter.next())
	{
		if ((entry->self() == this) && !entry->isRpcComplete())
		{
			entry->addPeerToVector(peer);

			txDObjRpcBcTokens* toks = new txDObjRpcBcTokens();
			toks->peer = peer.clone();
			toks->entry = entry;
			toks->self = this;

			txThread::start(
				txDObjRpcBc::_callSingleParallelRpc_,
				toks, "_callSingleParallelRpc_",
				_d_mgr->baseThreadPriority() + 2);
		}
	}
}

void txDObjRpcBc::removeBroadcastPeer (const txAbsPeer& peer)
{
	txRpcEntryBc* entry;
	txHashTableIterator iter(_rpc_bc_entries_);

	while (entry = (txRpcEntryBc*) iter.next())
	{
		if (entry->self() == this)
		{
			entry->removePeerFromVector(peer);

			txDObjRpcBc::_checkForAllResultsReceived_(*entry);
		}
	}
}

txRpcEntryBc::txRpcEntryBc (unsigned long rpc_id, txDObjRpcBc* self) :
	_self_(self),
	_rpc_id_(rpc_id),
	_method_name_(0),
	_method_args_(0),
	_rpc_status_(UNKNOWN),
	_rpc_result_vector_(0, TX_AUTODEL_ON)
{
}

txRpcEntryBc::~txRpcEntryBc (void)
{
}

int txRpcEntryBc::isRpcComplete (void)
{
	int flag = 1;
	txRpcResultBc* tuple;
	txHashMapIterator iter(_rpc_result_vector_);

	while (iter.next())
	{
		tuple = (txRpcResultBc*) iter.value();

		if (tuple->status != COMPLETE)
		{
			flag = 0;
			break;
		}
	}

	return flag;
}

void txRpcEntryBc::setStatus (STATUS rpc_status)
{
	_rpc_status_ = rpc_status;
}

void txRpcEntryBc::setMethodName (const char* method_name)
{
	_method_name_ = (char*) method_name; 
}

void txRpcEntryBc::setMethodArgs (const txObject* method_args)
{
	_method_args_ = (txObject*) method_args;
}

void txRpcEntryBc::addPeerToVector (const txAbsPeer& peer)
{
	txRpcResultBc* tuple = new txRpcResultBc();

	tuple->status = UNKNOWN;
	tuple->result = 0;

	_rpc_result_vector_.insertKeyAndValue(peer.clone(), tuple);
}

void txRpcEntryBc::removePeerFromVector (const txAbsPeer& peer)
{
	_rpc_result_vector_.removeAndDestroy(&peer);
}

void txRpcEntryBc::initializeRpcVector (const txPeerList& peer_list)
{
	txAbsPeer* peer;
	txRpcResultBc* tuple;
	txPeerListIterator iter((txPeerList&) peer_list);

	while (peer = (txAbsPeer*) iter.next())
	{
		tuple = new txRpcResultBc();
		tuple->status = UNKNOWN;
		tuple->result = 0;

		_rpc_result_vector_.insertKeyAndValue(peer->clone(), tuple);
	}
}

void txRpcEntryBc::updateRpcVectorWithResults (const txAbsPeer& peer,
	txObject* rpc_result)
{
	txRpcResultBc* tuple;

	if (tuple = (txRpcResultBc*) _rpc_result_vector_.findValue(&peer))
	{
		tuple->status = COMPLETE;
		tuple->result = rpc_result;
	}
}

txRpcEntryBc::STATUS txRpcEntryBc::getPeerStatus (const txAbsPeer& peer)
{
	STATUS status = UNKNOWN;
	txRpcResultBc* tuple;

	if (tuple = (txRpcResultBc*) _rpc_result_vector_.findValue(&peer))
	{
		status = tuple->status;
	}

	return status;
}

txObject* txRpcEntryBc::getPeerResult (const txAbsPeer& peer)
{
	txObject* v = 0;
	txRpcResultBc* tuple;

	if (tuple = (txRpcResultBc*) _rpc_result_vector_.findValue(&peer))
	{
		v = tuple->result;
	}

	return v;
}

unsigned long txRpcEntryBc::getRpcId (void)
{
	return _rpc_id_;
}

const txEvent* txRpcEntryBc::getEvent (void)
{
	return &_event_;
}

const char* txRpcEntryBc::getMethodName (void)
{
	return _method_name_;
}

const txObject* txRpcEntryBc::getMethodArgs (void)
{
	return _method_args_;
}

const txHashMap* txRpcEntryBc::getResponseVector (void)
{
	return &_rpc_result_vector_;
}


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
#include "txdobjmgr.h"
#include "txdobjspace.h"
#include "sys/txdobjrpc.h"
#include "sys/txdobjutil.h"
#include "sys/txdobjsrvr.h"

int txDObjRpc::_registerFlag_ = 0;
txHashTable txDObjRpc::_rpc_entries_;
unsigned long txRpcEntry::_rpc_count_ = 0;

TX_DEFINE_PARENT_TYPE(txDObjRpc,txDObjMsg)

txDObjRpc::txDObjRpc (txDObjMgr* d_mgr) : txDObjMsg(d_mgr)
{
	if (_registerFlag_ == 0)
	{
		registerStaticOneWayMethod("txc",
			(TX_METHOD_PTR) txDObjRpc::_rpcReqRcvd_);
		registerStaticOneWayMethod("txd",
			(TX_METHOD_PTR) txDObjRpc::_rpcRespRcvd_);

		_registerFlag_ = 1;
	}
}

txDObjRpc::~txDObjRpc (void)
{
}

txObject* txDObjRpc::_rpcReqRcvd_ (txDObjRpc* self, txIn& in,
	const txAbsPeer& peer)
{
	txOut out;
	txString method;
	txObject* reply;
	unsigned long int id;

 	in >> id;
	in >> method;

	out << self->oidObject();
	out.put("txd", 3, TX_ASCII_STRING);
	out << id;

	txDObjServer* obj_srvr = (txDObjServer*) self->_d_mgr->getObjSrvr();

	if (reply = self->apply(method, in, peer))
	{
		out << reply;

		delete reply; reply = 0;
	}
	
	obj_srvr->sendTo(out, peer);

	return 0;
}

txObject* txDObjRpc::_rpcRespRcvd_ (txDObjRpc* self, txIn& in, const txAbsPeer&)
{
	txUInt32 id;
	txObject* reply;
	txRpcEntry* rpc_entry;

	if (self->isClass(txDObjSpace::Type)) return 0;

	in >> id;
	in >> reply;

	if (reply && reply->isClass(txNull::Type))
	{
		delete reply; reply = 0;
	}

	if (rpc_entry = (txRpcEntry*) self->_rpc_entries_.remove(&id))
	{
		rpc_entry->setResponse(txRpcResult::SUCCEEDED, reply);
	}
	else
	{
		delete reply; reply = 0;
	}

	return 0;
}

void txDObjRpc::sendRpcMessage (txRpcResult& result, txOut& out,
	const txAbsPeer& peer, unsigned long timeout)
{
	if (!_d_mgr) return;

	txRpcEntry rpc_entry(result, peer, this);

	_rpc_entries_.insert(&rpc_entry);

	txOut request;
	request << oidObject();
	request.put("txc", 3, TX_ASCII_STRING);
	request << rpc_entry.id();
	request.append(out);

	((txDObjServer*) _d_mgr->getObjSrvr())->sendTo(request, peer);

	rpc_entry.waitForResult(timeout);

	txUInt32 id(rpc_entry.id());

	_rpc_entries_.remove(&id);
}

void txDObjRpc::flushAffectedRpcs (void)
{
	txRpcEntry* re;
	txHashTableIterator iter(_rpc_entries_);

	while (re = (txRpcEntry*) iter.next())
	{
		if (re->self() == this)
		{
			iter.remove();

			re->setResponse(txRpcResult::DESTROYED, 0);

			txThread::yield();
		}
	}
}

void txDObjRpc::flushAffectedRpcs (const txAbsPeer& peer)
{
	txRpcEntry* re;
	txHashTableIterator iter(_rpc_entries_);

	while (re = (txRpcEntry*) iter.next())
	{
		if (re->peer()->equals(&peer))
		{
			iter.remove();

			re->setResponse(txRpcResult::FAIL, 0);

			txThread::yield();
		}
	}
}

void txRpcEntry::waitForResult (unsigned long time)
{
	if (txRpcResult::INIT == _result_->status())
	{
		if (time)
		{
			if (!txThread::yield(_event_, time))
			{
				_result_->status(txRpcResult::TIMEOUT);
			}
		}
		else
		{
			txThread::yield(_event_);
		}
	}
}

void txRpcEntry::setResponse (txRpcResult::RESPONSE response, txObject* result)
{
	_result_->status(response);
	_result_->result(result);
	_event_.trigger();
}


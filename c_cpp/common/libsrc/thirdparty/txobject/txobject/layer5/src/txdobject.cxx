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

#include "txint16.h"
#include "txstring.h"
#include "txthread.h"
#include "txabspeer.h"
#include "txpeerlist.h"

#include "txdobject.h"
#include "txdobjmgr.h"
#include "txdobjspace.h"

TX_DEFINE_STREAM_TYPE(txDObject,txDObjRpc)

int txDObject::_TX_R_ = 0;
txHashMap txDObject::_TX_M_(10, TX_AUTODEL_ON);
txHashTable txDObject::_TX_A_(10, TX_AUTODEL_ON);

txDObject::txDObject (void) : 
	_peer_refs_(0),
	_owner_peer_(0),
	_object_space_(0),
	_rpc_is_pending_(0),
	_local_ref_count_(0)
{
}

txDObject::~txDObject (void)
{
	flushAffectedRpcs();

	delete _peer_refs_; _peer_refs_ = 0;

	delete _owner_peer_; _owner_peer_ = 0;

	while (_rpc_is_pending_)
	{
		txThread::yield(100);
	}
}

void txDObject::incref (void)
{
	_local_ref_count_++;

	if (_d_mgr && (_local_ref_count_ == 1))
	{
		static txIn in((char*) 0, 0);

		_addRef_(this, in,  *getPeer());

		txOut out;

		out << oidObject(); out.put("txi", 3, TX_ASCII_STRING);

		broadcast(out);
	}
}

void txDObject::decref (void)
{
	_local_ref_count_--;

	if (_d_mgr && (_local_ref_count_ == 0))
	{
		txOut out;

		out << oidObject(); out.put("txj", 3, TX_ASCII_STRING);

		broadcast(out);

		static txIn in((char*) 0, 0);

		_removeRef_(this, in, *getPeer());
	}
	else if (_local_ref_count_ == 0)
	{
		delete this;
	}
}

void txDObject::_chown_ (void)
{
	if (_d_mgr)
	{
		changeOwner(*arbitrateOwnership());
	}
}

int txDObject::refcount (void)
{
	return _local_ref_count_;
}

txObject* txDObject::_addRef_ (txDObject* self, txIn& in, const txAbsPeer& p)
{
	if (!self->_peer_refs_)
	{
		self->_peer_refs_ = new txList(TX_AUTODEL_ON);
	}

	if (!self->_peer_refs_->find(&p))
	{
		self->_peer_refs_->append(p.clone());
	}

	return 0;
}

txObject* txDObject::_removeRef_ (txDObject* self, txIn& in,
	const txAbsPeer& p)
{
	int entries = 0;

	if (self->_peer_refs_)
	{
		self->_peer_refs_->removeAndDestroy(&p);

		entries = self->_peer_refs_->entries();
	}

	if (!entries && !self->refcount() && self->ownedLocally())
	{
		self->_destroy_();
	}

	return 0;
}

txObject* txDObject::_executeMethod_ (txDObject* self, txIn& in,
	const txAbsPeer& p)
{
	txString method;
	txObject* retval = 0;

	if (self->_d_mgr)
	{
		in >> method;

		retval = self->_apply_(method, in, p);
	}

	return retval;
}

txObject* txDObject::_remoteUpdate_ (txDObject* self, txIn& in,
	const txAbsPeer&) 
{
	signed short int length; in >> length;

	self->_applyTransactionLog_(in, length);

	return 0;
}

txObject* txDObject::_receiveCommit_ (txDObject* self, txIn& in,
	const txAbsPeer&)
{
	if (self->ownedLocally())
	{
		signed short int length; in >> length;

		self->_applyTransactionLog_(in, length);
	}
	else
	{
		txOut out;

		out << self->oidObject(); out.put("txg", 3, TX_ASCII_STRING);

		out.append(in.data(), in.length());

		self->sendTo(out, *self->owner());
	}

	return 0;
}

txObject* txDObject::_execRpc_ (const char* method, const txOut& args,
	const txAbsPeer* p, signed long& tm_out)
{
	txOut out; int i = 0;
	txString tmp(method);
	txObject* object = 0;
	txAbsPeer* clone = 0;
	txRpcResult rpc_result;
	out.put("txk", 3, TX_ASCII_STRING); out << method; out.append(args);

	_rpc_is_pending_++;

	if (p)
	{
		clone = p->clone();
	}

	do
	{
		if (!_d_mgr)
		{
			break;
		}
		else if (!clone && ownedLocally())
		{
			txIn in((char*) out.data(), out.length());

			txString x; in >> x; in >> x;

			object = _apply_(tmp, in, *owner());

			break;
		}
		else if (clone && clone->equals(getPeer()))
		{
			txIn in((char*) out.data(), out.length());

			txString x; in >> x; in >> x;

			object = _apply_(tmp, in, *clone);

			break;
		}
		else if (clone && !_object_space_->activePeers()->find(clone))
		{
			break;
		}

		if (clone)
		{
			sendRpcMessage(
				rpc_result, out,
				*clone, (unsigned long) tm_out);
		}
		else
		{
			sendRpcMessage(
				rpc_result, out,
				*_owner_peer_, (unsigned long) tm_out);
		}

		i++;
	}
	while (!clone && (i < 25) &&
		(txRpcResult::FAIL == rpc_result.status()));

	_rpc_is_pending_--;

	delete clone; clone = 0;

	if (txRpcResult::TIMEOUT == rpc_result.status())
	{
		tm_out = -1;
	}

	return object ? object : rpc_result.result();
}

void txDObject::_destroy_ (void)
{
	_object_space_->_deallocateObject_(this);
}

void txDObject::_notifyUp_ (const txAbsPeer& p)
{
	if (_d_mgr)
	{
		contactEstablishedNotify(p);
	}
}

void txDObject::_notifyDown_ (const txAbsPeer& p)
{
	if (_d_mgr)
	{
		if (p.equals(owner()))
		{
			_chown_();
		}

		contactLostNotify(p);

		static txIn in((char*) 0, 0);

		_removeRef_(this, in, p);
	}
}

void txDObject::_setSpaceAndManager_ (txDObjSpace* space, txDObjMgr* d_mgr)
{
	_object_space_ = space; _d_mgr = d_mgr;
}

void txDObject::_commit_ (txOut& trans_log)
{
	if (_d_mgr)
	{
		txIn in(trans_log);

		_applyTransactionLog_(in, trans_log.length());
	}

	if (_d_mgr && _object_space_->activePeers()->entries() > 1)
	{
		txOut out;

		out << oidObject(); out.put("txh", 3, TX_ASCII_STRING);
		out << (signed short int) trans_log.length();
		out.append(trans_log);
	
		broadcast(out);
	}
}

void txDObject::_sendCommit_ (txOut& trans_log)
{
	txOut out;

	out << oidObject();
	out.put("txg", 3, TX_ASCII_STRING); out.append(trans_log);

	sendTo(out, *owner());
}

void txDObject::_applyTransactionLog_ (txIn& in, unsigned long length)
{
	txInt16 type;
	txString attr;
	const char* end = in.cursor() + length;

	while (in.cursor() < end)
	{
		in >> type; in >> attr;

		_updateAttribute_(type, attr, in);
	}
}

txObject* txDObject::_apply_ (const txString& name, txIn& in,
	const txAbsPeer& p)
{
	txObject* v = 0;
	txDObjMethod* method;

	TX_INITIALIZE_DOBJECT()

	if ((method = (txDObjMethod*) _TX_M_.findValue(&name)))
	{
		v = method->apply(this, in, p);
	}

	return v;
}

void txDObject::_updateAttribute_ (txInt16& type, txString& attr, txIn& in)
{
	txOffSet* offset;

	TX_INITIALIZE_DOBJECT()

	if(!(offset = (txOffSet*) _TX_A_.find(&attr)))
	{
		return;
	}

	txDObjUpdate::attribute(this, type, attr, offset->get(this), in);
}

void txDObject::registerAttribute (const char* name, const void* attr) const
{
	txString lu(name);

	if (!_TX_A_.find(&lu))
	{
	 _TX_A_.insert(new txOffSet(name, (txObject**) attr, (txObject*) this));
	}
	else
	{
	 fprintf(stderr, "TXOBJECT[error] : dup. registerAttribute\n");
	 fflush(stderr); TX_CRASH;
	}
}

void txDObject::registerTwoWayMethod (const char* name,
	TX_METHOD_PTR meth) const
{
	txString lu(name);

	if (!_TX_M_.find(&lu))
	{
	 _TX_M_.insertKeyAndValue(new txString(name), new txDObjMethod(meth));
	}
	else
	{
	 fprintf(stderr, "TXOBJECT[error] : dup. registerTwoWayMethod.\n");
	 fflush(stderr); TX_CRASH;
	}
}

void txDObject::storeInners (txOut& out) const
{
	txMemObj::storeInners(out);

	txOffSet* offset;

	TX_INITIALIZE_DOBJECT()

	out << (signed short int) txDObject::_TX_A_.entries();

	txHashTableIterator iter(txDObject::_TX_A_);

	while (offset = (txOffSet*) iter.next())
	{
		out.put(offset);
		out.put(*offset->get((txObject*) this));
	}
}

void txDObject::restoreInners (txIn& in)
{
	txMemObj::restoreInners(in);

	txObject* v;
	txObject* o;
	txOffSet offset;
	txObject** attr;

	TX_INITIALIZE_DOBJECT()

	signed short int entries; in >> entries;

	for (int i = 0; i < entries; i++)
	{
		in >> offset; in >> v;

		if (o = (txObject*) _TX_A_.find(&offset))
		{
			attr = ((txOffSet*) o)->get(this); *attr = v;
		}
		else
		{
			delete v; v = 0;
		}
	}

	txDObject::restoreComplete();
}

txDObject* txDObject::operator-> (void)
{
	txDObject* self = (txDObject*) ((txDObjPtr*) this)->_getObject_();

	if (!self)
	{
		fprintf(stderr, "TXOBJECT[error] : invalid smart ptr ");
		fprintf(stderr, "[txDObject]\n");
		fflush(stdout); TX_CRASH;
	}

	return self;
}

void txDObject::restoreComplete (void)
{
}

void txDObject::registerInners (void) const
{
	registerAttribute("txa",
		&_peer_refs_);
	registerAttribute("txb",
		&_owner_peer_);

	registerStaticOneWayMethod("txg",
		(TX_METHOD_PTR) txDObject::_receiveCommit_);
	registerStaticOneWayMethod("txh",
		(TX_METHOD_PTR) txDObject::_remoteUpdate_);

	registerStaticOneWayMethod("txi",
		(TX_METHOD_PTR) txDObject::_addRef_);
	registerStaticOneWayMethod("txj",
		(TX_METHOD_PTR) txDObject::_removeRef_);

	registerStaticOneWayMethod("txk",
		(TX_METHOD_PTR) txDObject::_executeMethod_);
}

void txDObject::attrWrite (const char* name, const txObject* value)
{
	if (_d_mgr)
	{
		_trans_log_ << (signed short int) ATTR_WRITE;
		_trans_log_ << name;
		_trans_log_ << value;
	}
}

void txDObject::attrWriteIndex (const char* name, const txObject* index,
	const txObject* value)
{
	if (_d_mgr)
	{
		_trans_log_ << (signed short int) ATTR_WRITE_INDEX;
		_trans_log_ << name;
		_trans_log_ << index;
		_trans_log_ << value;
	}
}

void txDObject::attrDelete (const char* name)
{
	if (_d_mgr)
	{
		_trans_log_ << (signed short int) ATTR_DELETE;
		_trans_log_ << name;
	}
}

void txDObject::attrDeleteIndex (const char* name, const txObject* index)
{
	if (_d_mgr)
	{
		_trans_log_ << (signed short int) ATTR_DELETE_INDEX;
		_trans_log_ << name;
		_trans_log_ << index;
	}
}

void txDObject::changeOwner (const txAbsPeer& new_peer)
{
	txAbsPeer* old_owner = _owner_peer_;

	_owner_peer_ = new_peer.clone();

	if (old_owner)
	{
		changeOwnerNotify(*old_owner, *_owner_peer_);

		delete old_owner; old_owner = 0; 
	}
}

int txDObject::ownedLocally (void) const
{
	return _d_mgr ? getPeer()->equals(_owner_peer_) : 0;
}

const txDObjSpace* txDObject::space (void) const
{
	return _object_space_;
}

txDObject::ARBITRATE txDObject::arbitrateObject (txDObject&)
{
	if (_d_mgr)
	{
		const txPeerList* peer_list = _object_space_->activePeers();

		if (peer_list->entries())
		{
			int id = atoi(term()) % peer_list->entries();

			if (getPeer()->equals(peer_list->at(id)))
			{
				return REMOVE;
			}
		}
	}

	return REPLACE;
}

const txAbsPeer* txDObject::arbitrateOwnership (void)
{
	const txAbsPeer* new_owner = 0;

	if (_d_mgr)
	{
		const txPeerList* peer_list = _object_space_->activePeers();

		if (peer_list->entries())
		{
			int id = atoi(term()) % peer_list->entries();
			new_owner = peer_list->at(id);
		}
	}

	return new_owner;
}

const txAbsPeer* txDObject::owner (void) const
{
	return _owner_peer_;
}

void txDObject::broadcastStaticOneWayMethod (const char* method,
	const txObject* args)
{
	txOut out;
	out << oidObject(); out << method; out << args; broadcast(out);
}

void txDObject::broadcastStaticOneWayMethod (const char* method,
	const txOut& args)
{
	txOut out;
	out << oidObject(); out << method; out.append(args); broadcast(out);
}

void txDObject::invokeStaticOneWayMethod (const char* method,
	const txObject* args, const txAbsPeer* p)
{
	txOut out;
	out << oidObject(); out << method; out << args;

	if (p) { sendTo(out, *p); } else { sendTo(out, *owner()); }
}

void txDObject::invokeStaticOneWayMethod (const char* method,
	const txOut& args, const txAbsPeer* p)
{
	txOut out; out << oidObject(); out << method; out.append(args);

	if (p) { sendTo(out, *p); } else { sendTo(out, *owner()); }
}

txObject* txDObject::invokeTwoWayMethod (const char* method,
	const txObject* args, const txAbsPeer* p)
{
	signed long timeout = 0;

	return invokeTwoWayMethod(timeout, method, args, p);
}

txObject* txDObject::invokeTwoWayMethod (signed long& timeout,
	const char* method, const txObject* args, const txAbsPeer* p)
{
	txOut out; out << args;

	return _d_mgr ? _execRpc_(method, out, p, timeout) : 0;
}

txObject* txDObject::invokeTwoWayMethod (const char* method, const txOut& args,
	const txAbsPeer* p)
{
	signed long timeout = 0;

	return invokeTwoWayMethod(timeout, method, args, p);
}

txObject* txDObject::invokeTwoWayMethod (signed long& timeout,
	const char* method, const txOut& args, const txAbsPeer* p)
{
	return _d_mgr ? _execRpc_(method, args, p, timeout) : 0;
}

const txPeerList* txDObject::activePeers (void) const
{
	return _d_mgr ? _object_space_->activePeers() : 0;
}

const txPeerList* txDObject::interestedPeers (void) const
{
	return _d_mgr ? _object_space_->interestedPeers() : 0;
}

int txDObject::commit (void)
{
	if (_d_mgr && _trans_log_.length())
	{
		txOut out;

		out.append(_trans_log_);

		_trans_log_.flush();

		if (ownedLocally())
		{
			_commit_(out);
		}
		else
		{
			_sendCommit_(out);
		}
	}

	return 1;
}

int txDObject::abort (void)
{
	if (_d_mgr)
	{
		_trans_log_.flush();
	}

	return 1;
}


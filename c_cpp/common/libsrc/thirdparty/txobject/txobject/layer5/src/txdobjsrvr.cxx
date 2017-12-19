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

#include "txthread.h"
#include "txstring.h"
#include "txippeer.h"
#include "txipcsrvr.h"
#include "txpeerlist.h"
#include "sys/txdobjintf.h"
#include "sys/txdobjutil.h"
#include "sys/txdobjsrvr.h"
#include "sys/txmemspace.h"
 
class txPeerStatus : public txObject
{
	public:
		enum STATUS
		{
			INIT = -1,
			LOST = 0,
			ESTAB = 1
		};

	private:
		int _port_;
		STATUS _status_;
		unsigned long _netaddr_;

	public:
		txPeerStatus (const txAbsPeer& peer, STATUS s = INIT) :
			_netaddr_(((txIpPeer&) peer).getNetAddr()),
			_port_(((txIpPeer&) peer).getPort()),
			_status_(s)
		{
		}

		~txPeerStatus (void)
		{
		}

		void set (STATUS status)
		{
			_status_ = status;
		}

		STATUS get (void)
		{
			return _status_;
		}

		unsigned hash (void) const
		{
			return _netaddr_ + _port_;
		}

		int equals (const txObject* obj) const
		{
			int retval = 0;
			txPeerStatus* status = (txPeerStatus*) obj;

			if (_netaddr_ == status->_netaddr_)
			{
				if (_port_ == status->_port_)
				{
					retval = 1;
				}
			}

			return retval;
		}
};

const int txDObjServer::TXDOBJSERVER_THRD_PRIORITY = 1;

txDObjServer::txDObjServer (txMemSpace& mem_mgr, const txAbsPeer& peer,
	int priority, int peer_timeout) :
	_ipc_comm_layer_(new txIPCServer(peer, priority + 1, peer_timeout)),
	_thread_manager_(priority, "txDObjServer RecvQ", 15, 50),
	_peers_(25, TX_AUTODEL_ON),
	_thread_pri_(priority),
	_memory_mgr_(&mem_mgr),
	_status_(INITIAL),
	_tx_(0)
{
	_local_ = (txIpPeer*) peer.clone();

	_ipc_comm_layer_->registerContactLost(
		txDObjServer::_contactLost_, this);
	_ipc_comm_layer_->registerContactEstablished(
		txDObjServer::_contactEstablished_, this);

	txThread::start(
		txDObjServer::_receiveThreadFunc_, this,
		"txDObjServer Receiver", _thread_pri_);
}

txDObjServer::~txDObjServer (void)
{
	close();

	txThread::yield();

	delete _local_; _local_ = 0;
	delete _ipc_comm_layer_; _ipc_comm_layer_ = 0;

	txNetworkMsg* p;

	while (p = _packets_.get())
	{
		delete p; p = 0;
	}
}

void txDObjServer::_contactLost_ (void* obj, txAbsPeer* peer)
{
	txPeerStatus* s_peer;
	txDObjServer* self = (txDObjServer*) obj;
	txPeerStatus* tmp = new txPeerStatus(*peer, txPeerStatus::ESTAB);

	if (!(s_peer = (txPeerStatus*) self->_peers_.find(tmp)))
	{
		if (peer->getType() == txAbsPeer::TCP)
		{
			txIpPeer* copy = (txIpPeer*) peer->clone();
			copy->setType(txAbsPeer::UDP);
			self->contact(*copy);
			delete copy; copy = 0;
		}

		s_peer = tmp; self->_peers_.insert(tmp);
	}
	else
	{
		delete tmp; tmp = 0;
	}

	if (s_peer->get() == txPeerStatus::ESTAB)
	{
		if (peer->getType() == txAbsPeer::TCP)
		{
			s_peer->set(txPeerStatus::LOST);

			if (self->_contact_lost_func_)
			{
				self->_contact_lost_func_(
					self->_contact_lost_context_, peer);
			}
		}
	}
}

void txDObjServer::_contactEstablished_ (void* obj, txAbsPeer* peer)
{
	txPeerStatus* s_peer;
	txDObjServer* self = (txDObjServer*) obj;
	txPeerStatus* tmp = new txPeerStatus(*peer, txPeerStatus::LOST);

	if (!(s_peer = (txPeerStatus*) self->_peers_.find(tmp)))
	{
		if (peer->getType() == txAbsPeer::TCP)
		{
			txIpPeer* copy = (txIpPeer*) peer->clone();
			copy->setType(txAbsPeer::UDP);
			self->contact(*copy);
			delete copy; copy = 0;
		}

		s_peer = tmp; self->_peers_.insert(s_peer);
	}
	else
	{
		delete tmp; tmp = 0;
	}

	if (s_peer->get() == txPeerStatus::LOST)
	{
		if (peer->getType() == txAbsPeer::TCP)
		{
			s_peer->set(txPeerStatus::ESTAB);

			if (self->_contact_estab_func_)
			{
				self->_contact_estab_func_(
					self->_contact_estab_context_, peer);
			}
		}
		else if (peer->getType() == txAbsPeer::UDP)
		{
			txIpPeer* copy = (txIpPeer*) peer->clone();
			copy->setType(txAbsPeer::TCP);
			self->contact(*copy);
			delete copy; copy = 0;
		}
	}
}

void txDObjServer::_processMessages_ (void* obj)
{
	char* oid;
	txDObjIntf* dobj;
	txNetworkMsg* p;
	txDObjServer* self = (txDObjServer*) obj;

	if ((self->_status_ != SHUTDOWN) && (p = self->_packets_.get()))
	{
		if (p->peer()->getType() == txAbsPeer::TCP)
		{
			txMemSpace* root = self->_memory_mgr_;
			txIn in((char*) p->data(), p->length());

			in >> oid;

			if (dobj = (txDObjIntf*) self->_tx_->accessObject(oid))
			{
				dobj->recvFrom(in, *p->peer());
			}
			else if (dobj = (txDObjIntf*) root->accessObject(oid))
			{
				dobj->recvFrom(in, *p->peer());
			}
			else // object not found, respond back to sender
			{
				txString rpc_method("txc");
				txString method; in >> method;

				if (method.equals(&rpc_method))
				{
					unsigned long int id;
					txNull* n; in >> id; in >> n;

					if (!n || !n->isClass(txNull::Type))
					{
					   txOut out;
					   txNull dummy;
					   out << oid;
					   out.put("txd", 3, TX_ASCII_STRING);
					   out << id; out << dummy;
					   self->sendTo(out, *p->peer());
					}

					delete n; n = 0;
				}
			}

			delete oid; oid = 0;
		}
	
		delete p; p = 0;
	}
}

void txDObjServer::_receiveThreadFunc_ (void* obj)
{
	txDObjServer* self = (txDObjServer*) obj;

	self->_tx_ = (txMemSpace*) self->_memory_mgr_->accessSpace("TX");

	while ((self->_status_ != SHUTDOWN) &&
		(self->_ipc_comm_layer_->recvFrom(self->_packets_, 1) != -1))
	{
		self->_thread_manager_.start(
			txDObjServer::_processMessages_, self,
			"txDObjServer::_receiveThreadFunc_",
			self->_thread_pri_, 12288);
	}
}

int txDObjServer::isEstablished (const txAbsPeer& peer)
{
	int status = 0;
	txPeerStatus* s_peer;
	txPeerStatus tmp(peer, txPeerStatus::ESTAB);

	if (s_peer = (txPeerStatus*) _peers_.find(&tmp))
	{
		status = s_peer->get();
	}

	return status;
}

int txDObjServer::contact (const txAbsPeer& a)
{
	return _ipc_comm_layer_ ? _ipc_comm_layer_->contact(a) : -1;
}

int txDObjServer::sendTo (txOut& o, const txAbsPeer& a)
{
	return _ipc_comm_layer_ ?
		_ipc_comm_layer_->sendTo(
			o.data(), o.length(), a) : -1;
}

int txDObjServer::multicast (txOut& o, const txPeerList& list)
{
	if (list.entries())
	{
		return _ipc_comm_layer_ ?
			_ipc_comm_layer_->multicast(
				o.data(), o.length(), list) : -1;
	}
	else
	{
		return -1;
	}
}

int txDObjServer::broadcast (txOut& out)
{
	return _ipc_comm_layer_ ?
		_ipc_comm_layer_->broadcast(
			out.data(), out.length()) : -1;
}

void txDObjServer::registerContactEstablished (const TX_COMM_FUNC func,
	void* context)
{
	_contact_estab_func_ = func;
	_contact_estab_context_ = context;
}

void txDObjServer::registerContactLost (const TX_COMM_FUNC func,
	void* context)
{
	_contact_lost_func_ = func;
	_contact_lost_context_ = context;
}

void txDObjServer::unregisterContactEstablished (void)
{
	_contact_estab_func_ = 0;
	_contact_estab_context_ = 0;
}

void txDObjServer::unregisterContactLost (void)
{
	_contact_lost_func_ = 0;
	_contact_lost_context_ = 0;
}

void txDObjServer::close (const txAbsPeer& peer)
{
	_ipc_comm_layer_->close(peer);
}

void txDObjServer::close (void)
{
	_status_ = SHUTDOWN;

	_ipc_comm_layer_->close();
}


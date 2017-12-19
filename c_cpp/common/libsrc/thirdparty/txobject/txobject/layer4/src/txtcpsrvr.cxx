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

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/types.h>
	#include <sys/socket.h>
	#include <netinet/in.h>
#else
	#include <winsock.h>
#endif

#include "txthread.h"
#include "txostring.h"
#include "txtcpsrvr.h"

struct txTCPTokens
{
	txTCPServer* self;
	txIpPeer* peer;
	txSocket* socket;
};

const int txTCPServer::TCPSERVER_THRD_PRIORITY = 2;

txTCPServer::txTCPServer (const txIpPeer& peer, txAbsPacker* packer, int pri) :
	txAbsServer(pri),
	_local_(0),
	_contact_count_(0),
	_contact_pending_event_("TCP : Pending Contact Event"),
	_packer_(packer ? packer->clone() : new txAbsPacker()),
	_wks_(SOCK_STREAM, packer ? packer : new txAbsPacker(), pri + 2)
{
	_wks_.registerContactEstablished(txTCPServer::_listenCallBack_, this);
	_wks_.bind(peer.getNetAddr(), peer.getPort()); _wks_.listen(14);

	if (!peer.accessible())
	{
		((txIpPeer&) peer).setPort(_wks_.port());
	}

	_local_ = (txIpPeer*) peer.clone();

	txThread::start(
		txTCPServer::_sendThreadFunc_, this,
		"txTCPServer Sender", _thread_pri);
}

txTCPServer::~txTCPServer (void)
{
	close();

	_connects_.clearAndDestroy();
	_stale_cs_.clearAndDestroy();

	while (_contact_count_)
	{
		txThread::yield(_contact_pending_event_);
	}

	delete _local_; _local_ = 0;
	delete _packer_; _packer_ = 0;
}

void txTCPServer::_registerSocket_ (const txAbsPeer& peer, txSocket& socket)
{
	txTCPTokens* tokens = new txTCPTokens();
	tokens->self = this;
	tokens->socket = &socket;
	tokens->peer = (txIpPeer*) peer.clone();
		
	socket.registerContactLost(
		txTCPServer::_contactLostCallBack_, this);

	txThread::start(
		txTCPServer::_receiveThreadFunc_, tokens,
		"txTCPServer Receiver", _thread_pri);
}

int txTCPServer::_addConnection_ (txAbsPeer& peer, txSocket* socket)
{
	int flag = -1;

	socket->port(((txIpPeer&) peer).getPort());

	if (!_connects_.find(&peer))
	{
		_connects_.insertKeyAndValue(peer.clone(), socket);

		flag = 1;
	}
	else if (!_stale_cs_.find(&peer))
	{
		_stale_cs_.insertKeyAndValue(peer.clone(), socket);
		socket->unregisterContactEstablished();
		socket->unregisterContactLost();

		flag = 0;
	}

	if (flag != -1)
	{
		_registerSocket_(peer, *socket);

		if (flag == 1)
		{
			_contactEstablished_(peer);
		}
	}

	return flag;
}

int txTCPServer::_removeConnection_ (const txAbsPeer& peer)
{
	_connects_.removeAndDestroy(&peer);
	_stale_cs_.removeAndDestroy(&peer);

	return 1;
}

txAbsPeer* txTCPServer::_getHelloPacket_ (txSocket& socket)
{
	txOctetString mc; 
	txIpPeer* peer = 0;
	unsigned long lhn = 0; 
	txNetworkMsg* packet = 0;
	signed long int wkp = 0; 

	txNetworkMsgs packets;

	if ((socket.recv(packets, 1, 5000) == 1) && (packet = packets.get()))
	{
		txIn in((char*) packet->data(), packet->length());

		in >> mc;

		if (_local_->getEncryption()->equals(&mc))
		{
			in >> wkp;
			in >> lhn;

			peer = new txIpPeer(lhn, wkp, txAbsPeer::TCP);
		}

		delete packet; packet = 0;
	}

	return (txAbsPeer*) peer;
}

void txTCPServer::_sendHelloPacket_ (txSocket& s)
{
	txOut out;

	out << _local_->getEncryption();
	out << (signed long int) _local_->getPort();
	out << (unsigned long int) _local_->getNetAddr();

	s.send(out.data(), out.length());
}

void txTCPServer::_listenCallBack_ (void* obj, txSocket* socket)
{
	txTCPServer* self = (txTCPServer*) obj;
	txTCPTokens* t = new txTCPTokens();
	t->socket = socket;
	t->self = self;

	txThread::start(
		txTCPServer::_initializeContact_,
		t, "txTCPServer::_init_", self->_thread_pri);
}

void txTCPServer::_initializeContact_ (void* obj)
{
	txTCPTokens* tokens = (txTCPTokens*) obj;
	txTCPServer* self = tokens->self;
	txSocket* socket = tokens->socket;
	txAbsPeer* peer;
	int flag = -1;

	peer = self->_getHelloPacket_(*socket);

	if (self->_status != SHUTDOWN && peer)
	{
		self->_sendHelloPacket_(*socket);

		flag = self->_addConnection_(*peer, socket);
	}

	if (flag == -1)
	{
		delete socket; socket = 0;
	}

	delete tokens; tokens = 0;
	delete peer; peer = 0;
}

void txTCPServer::_sendThreadFunc_ (void* obj)
{
	void* p;
	txTCPServer* self = (txTCPServer*) obj;

	while ((self->_status != SHUTDOWN) && (self->_out_packets.get(p)!=-1))
	{
		self->_sendSafe_((txNetworkMsg*) p);
	}
}

void txTCPServer::_receiveThreadFunc_ (void* obj)
{
	txTCPTokens* tokens = (txTCPTokens*) obj;
	txTCPServer* self = tokens->self;
	txSocket* s = tokens->socket;
	txIpPeer* p = tokens->peer;

	delete tokens; tokens = 0;

	txNetworkMsg* packet;
	txNetworkMsgs packets;

	while ((self->_status != SHUTDOWN) && (s->recv(packets) != -1))
	{
		while (packet = packets.get())
		{
			packet->peer(p->clone());

			if (self->_in_packets.put(packet) == -1)
			{
				delete packet; packet = 0;
			}
		}
	}

	delete p; p = 0;
}

void txTCPServer::_contactLostCallBack_ (void* obj, txSocket* socket)
{
	txTCPServer* self = (txTCPServer*) obj;
	txAbsPeer* peer;

	if (self && (self->_status != SHUTDOWN))
	{
		txIpPeer lu(socket->netaddr(), socket->port(), txAbsPeer::TCP);

		if (peer = (txAbsPeer*) self->_connects_.find(&lu))
		{
			self->_contactLost_(*peer);
		}
	}
}

void txTCPServer::_contactLost_ (txAbsPeer& peer)
{
	txAbsPeer* holder;

	if (_status != SHUTDOWN)
	{ 
		peer.deactivate();

		holder = peer.clone();
		txTCPTokens* token = new txTCPTokens();
		token->peer = (txIpPeer*) peer.clone();
		token->self = this;

		_removeConnection_(*holder);

		_flushOutGoingPackets(*holder);

		_broadcast_list.removeAndDestroy(holder);

		txThread::start(
			txTCPServer::_threadedContactLost_,
			token, "txTCPServer::_lost_", _thread_pri + 2, 12288);	

		delete holder; holder = 0;
	}
}

void txTCPServer::_contactEstablished_ (txAbsPeer& peer)
{
	if (_status != SHUTDOWN)
	{ 
		peer.activate();

		_broadcast_list.append(peer.clone());

		txTCPTokens* token = new txTCPTokens();
		token->peer = (txIpPeer*) peer.clone();
		token->self = this;

		txThread::start(
			txTCPServer::_threadedContactEstablished_,
			token, "txTCPServer::_estab_", _thread_pri + 1, 12288);
	}
}

void txTCPServer::_threadedContactLost_ (void* obj)
{
	txTCPTokens* token = (txTCPTokens*) obj;
	txAbsPeer* peer = token->peer;
	txTCPServer* self = token->self;

	if (self->_contact_lost_func)
	{
		self->_contact_lost_func(self->_contact_lost_context, peer);
	}

	delete token; token = 0;
	delete peer; peer = 0;
}

void txTCPServer::_threadedContactEstablished_ (void* obj)
{
	txTCPTokens* token = (txTCPTokens*) obj;
	txAbsPeer* peer = token->peer;
	txTCPServer* self = token->self;

	if (self->_contact_estab_func)
	{
		self->_contact_estab_func(self->_contact_estab_context, peer);
	}

	delete token; token = 0;
	delete peer; peer = 0;
}

void txTCPServer::_sendSafe_ (txNetworkMsg* packet)
{
	int status = 1;
	txSocket* socket;
	const txAbsPeer* peer = packet->peer();

	if (socket = (txSocket*) _connects_.findValue(peer))
	{
		status = socket->send(packet);
	}
	else
	{
		contact(*peer);

		if (socket = (txSocket*) _connects_.findValue(peer))
		{
			status = socket->send(packet);
		}
		else
		{
			status = -1;
		}
	}

	if (status == -1)
	{
		delete packet; packet = 0;
	}
}

void txTCPServer::_enqueueMulticast_ (const char* message,
	unsigned long message_length, const txPeerList& peers)
{
	if (peers.entries())
	{
		txQueue queue;
		txAbsPeer* peer;
		txNetworkMsg* packet;
		txPeerListIterator iter((txPeerList&) peers);
 
		int length = htonl(message_length);
		packet = new txNetworkMsg(0, 0, (txAbsPeer*) iter.next());
		packet->append((char*) &length, sizeof(int));
		packet->append(message, message_length);
		queue.append(packet);

		while (peer = (txAbsPeer*) iter.next())
		{
			queue.append((void*) packet->clone(peer));
		}
 
		_out_packets.put(&queue);
	}
}

int txTCPServer::contact (const txAbsPeer& peer)
{
	int flag = -1;
	txSocket* socket = 0;
	txIpPeer* p = (txIpPeer*) &peer;
	static txEvent S_pend_event("contact pend");
	static txHashTable S_list_of_pending_peers;

	_contact_count_++;

	while (S_list_of_pending_peers.find(&((txAbsPeer&) peer)))
	{
		txThread::yield(S_pend_event);
	}

	S_list_of_pending_peers.insert(p);

	if (p->accessible() && !_connects_.find(p))
	{
		socket = new txSocket(
			SOCK_STREAM, _packer_->clone(), _thread_pri + 1);

		if (socket->connect(p->getNetAddr(), p->getPort()) != -1)
		{
			_sendHelloPacket_(*socket);

			if (p = (txIpPeer*) _getHelloPacket_(*socket))
			{
				flag = _addConnection_(*p, socket);

				delete p; p = 0;
			}
			else
			{
				_contactLost_((txIpPeer&) peer);
			}
		}
		else
		{
			_contactLost_((txIpPeer&) peer);
		}
	}

	if (flag == -1)
	{
		delete socket; socket = 0;
	}

	S_list_of_pending_peers.remove(&peer);

	S_pend_event.trigger();

	_contact_count_--;

	_contact_pending_event_.trigger();

	return flag;
}

int txTCPServer::sendTo (txNetworkMsg* packet)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		status = _out_packets.put((void*) packet);
	}

	return status;
}

int txTCPServer::sendTo (const char* message, unsigned long message_length,
	const txAbsPeer& peer)
{
	int status = -1;
	txNetworkMsg* packet;
	txIpPeer* p = (txIpPeer*) &peer;

	if (_status != SHUTDOWN)
	{
		packet = new txNetworkMsg(0, 0);
		packet->peer(p->clone());

		int length = htonl(message_length);
		packet->append((char*) &length, sizeof(int));
		packet->append(message, message_length);

		status = _out_packets.put((void*) packet);
	}

	return status;
}

int txTCPServer::recvFrom (txNetworkMsgs& packets, int amount)
{
	int status = -1;
	txNetworkMsg* packet;

	if (_status != SHUTDOWN)
	{
		if (!amount && !(amount = _in_packets.entries()))
		{
			amount = 1;
		}

		for (int i = 0; i < amount; i++)
		{
			if (_in_packets.get((void*&) packet) != -1)
			{
				packets.append(packet);
			}
			else
			{
				return -1;
			}
		}

		status = 1;
	}

	return status;
}

int txTCPServer::broadcast (const char* message, unsigned long message_length)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		_enqueueMulticast_(message, message_length, _broadcast_list);

		status = 1;
	}

	return status;
}

int txTCPServer::multicast (const char* message, unsigned long message_length,
	const txPeerList& peers)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		_enqueueMulticast_(message, message_length, peers);

		status = 1;
	}

	return status;
}

void txTCPServer::close (const txAbsPeer& peer)
{
	if ((_status != SHUTDOWN) && _connects_.find(&peer))
	{
		_contactLost_((txAbsPeer&) peer);
	}
}

void txTCPServer::close (void)
{
	_status = SHUTDOWN;

	unregisterContactEstablished();
	unregisterContactLost();

	_wks_.close();
}


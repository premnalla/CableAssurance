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

#include <string.h>

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/types.h>
	#include <netinet/in.h>
#else
	#include <winsock.h>
#endif

#include "txthread.h"
#include "txstring.h"
#include "txipcsrvr.h"
#include "txtcpsrvr.h"
#include "txudpsrvr.h"
#include "txabspacker.h"

struct txIPCTokens
{
        txIPCServer* self;
        txAbsServer* server;
};

const int txIPCServer::IPCSERVER_THRD_PRIORITY = 3;

txIPCServer::txIPCServer (const txAbsPeer& peer, int priority, int timeout) :
	txAbsServer(priority),
	_self_((txIpPeer*) &peer),
	_tcp_server_(0), _udp_server_(0),
	_udpserver_timeout_interval_(timeout)
{
	_initCommLayer_(_self_->getType());

	_self_ = (txIpPeer*) peer.clone();

	txThread::start(
		txIPCServer::_sendThreadFunc_, this,
		"txIPCServer Sender", _thread_pri);
}

txIPCServer::~txIPCServer (void)
{
	close();

	delete _tcp_server_; _tcp_server_ = 0;
	delete _udp_server_; _udp_server_ = 0;

	txThread::yield();

	delete _self_; _self_ = 0;
}

void txIPCServer::_initCommLayer_ (txAbsPeer::TYPE type)
{
	txIpPeer* s = _self_;
	int p = _thread_pri+1;
	txAbsPeer::TYPE orig = _self_->getType();

	_self_->setType(type);

	switch (_self_->getType())
	{
		case txAbsPeer::TCP :
		{
			_tcp_server_ = new txTCPServer(*s,new txAbsPacker,p);
			_registerServerThread_(*_tcp_server_);
		}
		case txAbsPeer::UDP :
		{
			int si = txUDPServer::UDPSERVER_SEND_INTERVAL;
			int ti = _udpserver_timeout_interval_;

			_udp_server_ = new txUDPServer(*s, p, si, ti);
			_registerServerThread_(*_udp_server_);

			break;
		}
	}

	_self_->setType(orig);
}

void txIPCServer::_registerServerThread_ (txAbsServer& server)
{
	txIPCTokens* tokens = new txIPCTokens();
	tokens->self = this;
	tokens->server = &server;

	server.registerContactLost(
		txIPCServer::_contactLost_, this);
	server.registerContactEstablished(
		txIPCServer::_contactEstablished_, this);

	txThread::start(
		txIPCServer::_receiveThreadFunc_, tokens,
		"txIPCServer Receiver", _thread_pri);
}

txAbsServer* txIPCServer::_getCommLayer_ (const txAbsPeer& remote)
{
	txAbsServer* server = 0;

	switch (remote.getType())
	{
		case txAbsPeer::TCP :
		{
			if (!_tcp_server_)
			{
				_initCommLayer_(txAbsPeer::TCP);
				_tcp_server_->contact(remote);
			}

			server = _tcp_server_;

			break;
		}
		case txAbsPeer::UDP :
		{
			if (!_udp_server_)
			{
				_initCommLayer_(txAbsPeer::UDP);
				_udp_server_->contact(remote);
			}

			server = _udp_server_;

			break;
		}
	}

	return server;
}


void txIPCServer::_setUpPacket_ (txNetworkMsg& packet, const char* message,
	unsigned long message_length)
{
	switch (packet.peer()->getType())
	{
		case txAbsPeer::TCP:
		{
			int length = htonl(message_length);
			packet.append((char*) &length, sizeof(int));
			packet.append(message, message_length);

			break;
		}
		case txAbsPeer::UDP:
		{
			packet.append(message, message_length);

			break;
		}
		case txAbsPeer::NONE:
		{
			break;
		}
	}
}

void txIPCServer::_enqueueMulticast_ (const char* message,
	unsigned long message_length, const txPeerList& peers)
{
	if (peers.entries())
	{
		txQueue queue;
		txAbsPeer* peer;
		txNetworkMsg* packet;
		txPeerListIterator iter((txPeerList&) peers);

		packet = new txNetworkMsg(0, 0, (txAbsPeer*) iter.next());
		_setUpPacket_(*packet, message, message_length);
		queue.append(packet);

		while (peer = (txAbsPeer*) iter.next())
		{
			queue.append(packet->clone(peer));
		}

		_out_packets.put(&queue);
	}
}

void txIPCServer::_contactLost_ (void* obj, txAbsPeer* peer)
{
	txIPCServer* self = (txIPCServer*) obj;
	txAbsPeer* tmp = peer->clone();

	//
	// disconnect tcp if udp goes down
	//
	if (self->_tcp_server_ && peer->getType() != txAbsPeer::TCP)
	{
		tmp->setType(txAbsPeer::TCP);
		self->_tcp_server_->close(*tmp);
	}

	//
	// change udp state to lost, but keep sending helltx
	//
	if (self->_udp_server_ && peer->getType() != txAbsPeer::UDP)
	{
		tmp->setType(txAbsPeer::UDP);
	 	self->_udp_server_->changeState(*tmp, txUDPServer::LOST);
	}

	self->_flushOutGoingPackets(*peer);

	self->_broadcast_list.removeAndDestroy(peer);

	if (self->_contact_lost_func)
	{
		self->_contact_lost_func(self->_contact_lost_context, peer);
	}

	delete tmp; tmp = 0;
}

void txIPCServer::_contactEstablished_ (void* obj, txAbsPeer* peer)
{
	txIPCServer* self = (txIPCServer*) obj;

	self->_broadcast_list.append(peer->clone());

	if (self->_contact_estab_func)
	{
		self->_contact_estab_func(self->_contact_estab_context, peer);
	}
}

void txIPCServer::_sendThreadFunc_ (void* obj)
{
	txIPCServer* self = (txIPCServer*) obj;
	txNetworkMsg* packet;
	txAbsServer* service;

	while ((self->_status != SHUTDOWN) &&
		(self->_out_packets.get((void*&) packet) != -1))
	{
		if (service = self->_getCommLayer_(*packet->peer()))
		{
			if (service->sendTo(packet) == -1)
			{
				delete packet; packet = 0;
			}
		}
		else
		{
			delete packet; packet = 0;
		}
	}
}

void txIPCServer::_receiveThreadFunc_ (void* obj)
{
	txIPCTokens* tokens = (txIPCTokens*) obj;
	txIPCServer* self = tokens->self;
	txAbsServer* s = tokens->server; 

	delete tokens; tokens = 0;

	txNetworkMsgs packets;
	txNetworkMsg* packet;

	while ((self->_status != SHUTDOWN) && (s->recvFrom(packets) != -1))
	{
		while (packet = packets.get())
		{
			if (self->_in_packets.put(packet) == -1)
			{
				delete packet; packet = 0;
			}
		}
	}
}

int txIPCServer::contact (const txAbsPeer& peer)
{
	int status = -1;
	txAbsServer* server;

	if (server = _getCommLayer_(peer))
	{
		status = server->contact(peer);
	}

	return status;
}

int txIPCServer::sendTo (txNetworkMsg* packet)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		status = _out_packets.put((void*) packet);
	}

	return status;
}

int txIPCServer::sendTo (const char* message, unsigned long message_length,
	const txAbsPeer& peer)
{
	int status = -1;
	txNetworkMsg* packet;
	txIpPeer* t_peer = (txIpPeer*) &peer;
 
	if (_status != SHUTDOWN)
	{
		packet = new txNetworkMsg(0, 0);
		packet->peer(t_peer->clone());

		_setUpPacket_(*packet, message, message_length);
 
		status = _out_packets.put((void*) packet);
	}

	return status;
}

int txIPCServer::recvFrom (txNetworkMsgs& packets, int amount)
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

int txIPCServer::broadcast (const char* message, unsigned long message_length)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		_enqueueMulticast_(message, message_length, _broadcast_list);

		status = 1;
	}

	return status;
}

int txIPCServer::multicast (const char* message, unsigned long message_length,
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

void txIPCServer::close (const txAbsPeer& peer)
{
	if (_status != SHUTDOWN)
	{
		if (_tcp_server_ && peer.getType() == txAbsPeer::TCP)
		{
			_tcp_server_->close(peer);
		}

		if (_udp_server_ && peer.getType() == txAbsPeer::UDP)
		{
			_udp_server_->close(peer);
		}
	}
}

void txIPCServer::close (void)
{
	_status = SHUTDOWN;

	unregisterContactEstablished();
	unregisterContactLost();

	if (_tcp_server_)
	{
		_tcp_server_->close();
	}

	if (_udp_server_)
	{
		_udp_server_->close();
	}
}


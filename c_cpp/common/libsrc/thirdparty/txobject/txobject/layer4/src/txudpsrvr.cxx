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
	#include <sys/socket.h>
	#include <netinet/in.h>
#else
	#include <winsock.h>
#endif

#include "txthread.h"
#include "txippeer.h"
#include "txudpsrvr.h"

struct txUDPTokens
{
        txIpPeer* peer;
        txUDPServer* self;
};

class txUDPState : public txObject
{
	public:
		txTimer alarm;
		double last_hello;
		txUDPTokens* tokens;
		txUDPServer::STATUS status;

	public:
		txUDPState (void) :
			last_hello(txTimer::currentTime()),
			tokens(0)
		{
		}

		~txUDPState (void)
		{
			delete tokens; tokens = 0;
		}
};


const int txUDPServer::UDPSERVER_THRD_PRIORITY = 3;
const int txUDPServer::UDPSERVER_SEND_INTERVAL = 5000;
const int txUDPServer::UDPSERVER_TIMEOUT_INTERVAL = 30000;

txUDPServer::txUDPServer (const txIpPeer& peer, int priority,
	int send_interval, int timeout_interval) :
	txAbsServer(priority),
	_exit_("EXIT EVENT"),
	_send_(send_interval),
	_timeout_(timeout_interval),
	_wks_(SOCK_DGRAM, priority + 1)
{
	_constructHelloMsg_();
	_constructShutdownMsg_();

	_wks_.bind(peer.getNetAddr(), peer.getPort());

	if (!peer.accessible())
	{
		((txIpPeer&) peer).setPort(_wks_.port());
	}

	txThread::start(
		txUDPServer::_sendThreadFunc_,
		this, "txUDPServer Sender", _thread_pri);

	txThread::start(
		txUDPServer::_receiveThreadFunc_,
		this, "txUDPServer Receiver", _thread_pri);
}

txUDPServer::~txUDPServer (void)
{
	close();

	_exit_.trigger();

	txThread::yield();

	_peers_of_interest_.clearAndDestroy();
}

void txUDPServer::_contactLost_ (const txIpPeer& peer)
{
	if (_status != SHUTDOWN)
	{
		((txIpPeer&) peer).deactivate();

		_broadcast_list.removeAndDestroy(&peer);

		txUDPTokens* t = new txUDPTokens();
		t->peer = (txIpPeer*) peer.clone();
		t->self = this;

		txThread::start(
			txUDPServer::_threadedContactLost_,
			t, "txUDPServer::_lost_", _thread_pri + 2, 12288);	
	}
}

void txUDPServer::_contactEstablished_ (const txIpPeer& peer)
{
	if (_status != SHUTDOWN)
	{
		((txIpPeer&) peer).activate();

		_broadcast_list.append(peer.clone());

		txUDPTokens* t = new txUDPTokens();
		t->peer = (txIpPeer*) peer.clone();
		t->self = this;

		txThread::start(
			txUDPServer::_threadedContactEstablished_,
			t, "txUDPServer::_estab_", _thread_pri + 1, 16384);
	}
}

void txUDPServer::_threadedContactLost_ (void* obj)
{
	txUDPTokens* t = (txUDPTokens*) obj;
	txAbsPeer* peer = t->peer;
	txUDPServer* self = t->self;

	if (self->_contact_lost_func)
	{
		self->_contact_lost_func(self->_contact_lost_context, peer);
	}

	delete peer; peer = 0;
	delete t; t = 0;
}

void txUDPServer::_threadedContactEstablished_ (void* obj)
{
	txUDPTokens* t = (txUDPTokens*) obj;
	txAbsPeer* peer = t->peer;
	txUDPServer* self = t->self;

	if (self->_contact_estab_func)
	{
		self->_contact_estab_func(self->_contact_estab_context, peer);
	}

	delete peer; peer = 0;
	delete t; t = 0;
}

void txUDPServer::_processIncomingMsgs_ (txIpPeer& peer, const char* msg,
	unsigned long length)
{
	if (sizeof(int)*2 == length)
	{
		if (!memcmp(_hello_msg_, msg, sizeof(int)))
		{
			_processHelloMsg_(peer, msg, length);
		}
		else if (!memcmp(_shutdown_msg_, msg, sizeof(int)))
		{
			_processGoodbyeMsg_(peer, msg, length);
		}
		else
		{
			_processUserMsg_(peer, msg, length);
		}
	}
	else
	{
		_processUserMsg_(peer, msg, length);
	}
}

void txUDPServer::_processUserMsg_ (txIpPeer& peer, const char* msg,
	unsigned long length)
{
	txNetworkMsg* packet = new txNetworkMsg(msg, length, &peer);

	if (_in_packets.put((void*) packet) == -1)
	{
		delete packet; packet = 0;
	}
}

void txUDPServer::_processHelloMsg_ (const txIpPeer& peer, const char* msg,
	unsigned long length)
{
	txUDPState* s_peer;

	if (s_peer = (txUDPState*) _peers_of_interest_.findValue(&peer))
	{
		s_peer->alarm.reset();

		if (s_peer->status == LOST)
		{
			_contactEstablished_(peer);
		}
	}
	else
	{
		s_peer = (txUDPState*) _addPeer_(peer);

		_contactEstablished_(peer);
	}

	s_peer->status = ESTABLISHED;

	s_peer->last_hello = txTimer::currentTime();

	if (length == sizeof(int)*2)
	{
		unsigned long expiry_interval;

		memcpy(&expiry_interval, msg+4, 4);

		s_peer->alarm.reset(0, 0, htonl(expiry_interval));
	}
}

void txUDPServer::_processGoodbyeMsg_ (const txIpPeer& peer, const char*,
	unsigned long)
{
	txUDPState* s_peer;

	if (s_peer = (txUDPState*) _peers_of_interest_.findValue(&peer))
	{
		s_peer->status = LOST;
	}

	_contactLost_(peer);
}

void* txUDPServer::_addPeer_ (const txIpPeer& peer)
{
	txUDPTokens* t;
	txUDPState* s_peer;

	t = new txUDPTokens();
	t->self = this;
	t->peer = (txIpPeer*) peer.clone();

	s_peer = new txUDPState();

	s_peer->alarm.reset(
		txUDPServer::_timerCheckCallBack_,
		s_peer,
		t->self->_timeout_);

	s_peer->status = LOST;
	s_peer->tokens = t;

	_peers_of_interest_.insertKeyAndValue(t->peer, s_peer);

	return (txUDPState*) s_peer;
}

void txUDPServer::_constructHelloMsg_ (void)
{
	_hello_msg_[0] = (char) 161;
	_hello_msg_[1] = (char) 162;
	_hello_msg_[2] = (char) 163;
	_hello_msg_[3] = (char) 164;
	int length = htonl(_timeout_);
	memcpy(_hello_msg_+4, (char*) &length, 4);
}

void txUDPServer::_constructShutdownMsg_ (void)
{
	_shutdown_msg_[0] = (char) 164;
	_shutdown_msg_[1] = (char) 163;
	_shutdown_msg_[2] = (char) 162;
	_shutdown_msg_[3] = (char) 161;
	int length = htonl(_timeout_);
	memcpy(_shutdown_msg_+4, (char*) &length, 4);
}

void txUDPServer::_sendThreadFunc_ (void* obj)
{
	txUDPServer* self = (txUDPServer*) obj;
	txIpPeer* peer;

	txHashMapIterator iter(self->_peers_of_interest_);

	while (self->_status != SHUTDOWN)
	{
		iter.reset();

		for (int i = 0; peer = (txIpPeer*) iter.next(); i++)
		{
			self->_wks_.sendTo(
				self->_hello_msg_,
				sizeof(int)*2,
				peer->getNetAddr(),
				peer->getPort());

#if defined TX_UDP_AGING // remove peer after 10 minutes;
			txUDPState* ps = (txUDPState*) iter.value();

			if ((txTimer::currentTime() - ps->last_hello) > 600000)
			{
				iter.removetxObject();
				delete ps; ps = 0;
				delete peer; peer = 0;
			}
#endif
			if (i == 10)
			{
				i = 0; txThread::yield(self->_exit_, 5);

				if (self->_status == SHUTDOWN)
				{
					return;
				}
			}
		}

		txThread::yield(self->_exit_, self->_send_);
	}
}

void txUDPServer::_receiveThreadFunc_ (void* obj)
{
	txUDPServer* self = (txUDPServer*) obj;
	unsigned long netaddr = 0;
	char* data = 0;
	int length = 0;
	int port = 0;

	while (self->_status != SHUTDOWN)
	{
		data = self->_wks_.recvFrom(4096, length, netaddr, port);

		if (length > 0)
		{
			txIpPeer peer(netaddr, port, txAbsPeer::UDP);
			self->_processIncomingMsgs_(peer, data, length);
			delete data; data = 0;
		}
		else if (length < 0)
		{
			break;
		}
	}
}

txTimerEnum::RETURN_STATUS txUDPServer::_timerCheckCallBack_ (void* obj)
{
	txUDPState* s_peer = (txUDPState*) obj;
	txUDPTokens* tokens = s_peer->tokens;
	txIpPeer* peer = tokens->peer;
	txUDPServer* self = tokens->self;

	if (s_peer->status == ESTABLISHED)
	{
		s_peer->status = LOST;

		self->_contactLost_(*peer);
	}

	return txTimerEnum::STOP;
}

int txUDPServer::changeState (const txAbsPeer& peer, STATUS status)
{
	txUDPState* s_peer;

	if (s_peer = (txUDPState*) _peers_of_interest_.findValue(&peer))
	{
		s_peer->status = status;
		return 1;
	}
	else
	{
		return 0;
	}
}

int txUDPServer::contact (const txAbsPeer& peer)
{
	int status = -1;

	if ((_status != SHUTDOWN) && !_peers_of_interest_.find(&peer))
	{
		_addPeer_((txIpPeer&) peer);

		status = _wks_.sendTo(_hello_msg_, sizeof(int)*2,
			((txIpPeer&) peer).getNetAddr(),
			((txIpPeer&) peer).getPort());
	}

	return status;
}

int txUDPServer::sendTo (txNetworkMsg* packet)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		status = _wks_.sendTo(
			packet->data(),
			(int) packet->length(),
			((txIpPeer*) packet->peer())->getNetAddr(),
			((txIpPeer*) packet->peer())->getPort());
	}

	return status;
}

int txUDPServer::sendTo (const char* message, unsigned long message_length,
	const txAbsPeer& peer)
{
	int status = -1;

	if (_status != SHUTDOWN)
	{
		status = _wks_.sendTo(
			message,
			message_length,
			((txIpPeer&) peer).getNetAddr(),
			((txIpPeer&) peer).getPort());
	}

	return status;
}

int txUDPServer::recvFrom (txNetworkMsgs& packets, int amount)
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

int txUDPServer::broadcast (const char* message, unsigned long message_length)
{
	int status = -1;
	txAbsPeer* peer;

	if (_status != SHUTDOWN)
	{
		txPeerListIterator iter(_broadcast_list);

		while (peer = (txAbsPeer*) iter.next())
		{
			sendTo(message, message_length, *peer);
		}

		status = 1;
	}

	return status;
}

int txUDPServer::multicast (const char* message, unsigned long message_length,
	const txPeerList& peers)
{
	int status = -1;
	txAbsPeer* peer;

	if (_status != SHUTDOWN)
	{
		txPeerListIterator iter((txPeerList&) peers);

		while (peer = (txAbsPeer*) iter.next())
		{
			sendTo(message, message_length, *peer);
		}

		status = 1;
	}

	return status;
}

void txUDPServer::close (const txAbsPeer& peer)
{
	_wks_.sendTo(
		_shutdown_msg_,
		sizeof(int)*2,
		((txIpPeer&) peer).getNetAddr(),
		((txIpPeer&) peer).getPort());

	_peers_of_interest_.removeAndDestroy(&peer);
}

void txUDPServer::close (void)
{
	txIpPeer* peer;
	txPeerListIterator iter(_broadcast_list);

	while (peer = (txIpPeer*) iter.next())
	{
		_wks_.sendTo(
			_shutdown_msg_,
			sizeof(int)*2,
			peer->getNetAddr(),
			peer->getPort());
	}

	_status = SHUTDOWN;

	unregisterContactEstablished();
	unregisterContactLost();

	_wks_.close();
}


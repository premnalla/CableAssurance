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

#include "txippeer.h"
#include "txabssrvr.h"

const int THE_MAX_RECV_DEPTH = 100;
const int THE_MAX_SEND_DEPTH = 100;

txAbsServer::txAbsServer (int priority) :
	_status(INITIAL),
	_thread_pri(priority),
	_in_packets(THE_MAX_RECV_DEPTH),
	_out_packets(THE_MAX_SEND_DEPTH),
	_contact_lost_func(0), _contact_estab_func(0),
	_contact_lost_context(0), _contact_estab_context(0)
{
}

txAbsServer::~txAbsServer (void)
{
	_flushQueue(_in_packets);
	_flushQueue(_out_packets);

	_broadcast_list.clearAndDestroy();
}

void txAbsServer::_flushQueue (txEventQueue& queue_to_flush)
{
	txQueue* queue;
	txNetworkMsg* packet;
 
	queue_to_flush.unregisterQueue(queue);
 
	if (queue)
	{
		while (packet = (txNetworkMsg*) queue->get())
		{
			delete packet; packet = 0;
		}

		delete queue; queue = 0;
	}
}

void txAbsServer::_flushOutGoingPackets (const txAbsPeer& peer)
{
	txQueue* queue1;
	txQueue* queue2;
	txIpPeer* t_peer;
	txNetworkMsg* packet;

	_out_packets.unregisterQueue(queue1);

	if (queue1)
	{
		queue2 = new txQueue();

		while (packet = (txNetworkMsg*) queue1->get())
		{
			t_peer = (txIpPeer*) packet->peer();

			if (!peer.equals(t_peer))
			{
				queue2->append(packet);
			}
			else
			{
				delete packet; packet = 0;
			}
		}

		_out_packets.registerQueue(queue2);

		delete queue1; queue1 = 0;
	}
}

void txAbsServer::registerContactEstablished (const TX_COMM_FUNC func,
	void* context)
{
	_contact_estab_func = func;
	_contact_estab_context = context;
}

void txAbsServer::registerContactLost (const TX_COMM_FUNC func,
	void* context)
{
	_contact_lost_func = func;
	_contact_lost_context = context;
}

void txAbsServer::unregisterContactEstablished (void)
{
	_contact_estab_func = 0;
	_contact_estab_context = 0;
}

void txAbsServer::unregisterContactLost (void)
{
	_contact_lost_func = 0;
	_contact_lost_context = 0;
}

void txAbsServer::close (void)
{
	_status = SHUTDOWN;

	unregisterContactEstablished();
	unregisterContactLost();
}


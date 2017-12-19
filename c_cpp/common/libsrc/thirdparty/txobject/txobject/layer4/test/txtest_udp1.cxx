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

#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <iostream.h>

#include "txevent.h"
#include "txthread.h"
#include "txippeer.h"
#include "txudpsrvr.h"
#include "txntwrkmsg.h"
#include "txpeerlist.h"

txUDPServer* G_UDPSERVER;

static int G_EXIT_FLAG = 0;
static txPeerList G_LIST_OF_PEERS;
static txEvent G_EXIT_EVENT("EXIT");

static void shutDown (int);

void receiveData (void*);
void printPacket (const char*, int);
void printPackets (txNetworkMsgs*);

void contactLost (void*, txAbsPeer*);
void contactEstablished (void*, txAbsPeer*);

void initialize(int argc, char** argv)
{
	#if !defined WIN32
		struct sigaction action;
		sigemptyset (&action.sa_mask);
		action.sa_flags = 0;
		action.sa_handler = shutDown;
		sigaction (SIGINT, &action, 0);
	#else
		signal(SIGINT, shutDown);
	#endif

	if (argc < 5)
	{
		cout << "usage : udpserver host port remote_host ";
		cout << "remote_port" << endl;
		exit(0);
	}

	cout << "UdpServer set on Port : " << atoi(argv[2]) << endl;
}

void udpTest (void* obj)
{
	int len;
	char* msg;
	txAbsPeer* peer;
	char** argv = (char**) obj;

	txIpPeer self(argv[1], atoi(argv[2]), txAbsPeer::UDP);
	txIpPeer peer1(argv[3], atoi(argv[4]), txAbsPeer::UDP);

	G_UDPSERVER = new txUDPServer(self);
	G_UDPSERVER->registerContactEstablished(contactEstablished, 0);
	G_UDPSERVER->registerContactLost(contactLost, 0);
	G_UDPSERVER->contact(peer1);

	msg = new char[strlen("HELLO FROM ") + strlen(argv[2]) + 1];
	strcpy(msg, "HELLO FROM ");
	strcat(msg, argv[2]);
	len = strlen(msg);

	txThread::start(receiveData, 0, "Receive Data");

	while (!G_EXIT_FLAG)
	{
		int entries = G_LIST_OF_PEERS.entries();

		for (int i = 0; i < entries; i++)
		{
			peer = (txAbsPeer*) G_LIST_OF_PEERS.at(i);

			G_UDPSERVER->sendTo(msg, len, *peer);
		}

		txThread::yield(G_EXIT_EVENT, 1000);
	}

	delete G_UDPSERVER;
	delete msg;
}

int main(int argc, char** argv)
{
	initialize(argc, argv);

	// starting the new thread to run the function 'udpTest'
	txThread::start(udpTest, argv, "udpTest", 50);

	while (!G_EXIT_FLAG)
	{
		txThread::yield(G_EXIT_EVENT, 1000);
	}

	G_LIST_OF_PEERS.clearAndDestroy();

	cout << "\n\n PROCESS TERMINATED \n" << flush;

	return 1;
}


static void shutDown( int )
{
	cout << "\n<CONTROL-C> SIGNAL." << endl;
	G_EXIT_FLAG = 1;
	G_EXIT_EVENT.trigger();
}

void printPackets (txNetworkMsgs* packets)
{
	txNetworkMsg* packet;

	while (packet = packets->get())
	{
		printPacket(packet->data(), packet->length());
		delete packet; packet = 0;
	}
}

void printPacket (const char* packet, int length)
{
	char* str = new char[length + 1];
	memcpy(str, packet, length);
	str[length] = '\0';

	cout << "packet : " << str << endl;

	delete str;
}

void receiveData (void*)
{
	txNetworkMsgs packets;

	while (!G_EXIT_FLAG)
	{
		// this call will block until there is data there
		// Notice: this function is on its own thread
		G_UDPSERVER->recvFrom(packets);

		printPackets(&packets);
	}
}

void contactEstablished  (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactEstablished : " << peer->getId() << endl;

		G_LIST_OF_PEERS.append(peer->clone());
	}
}

void contactLost  (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactLost : " << peer->getId() << endl;

		G_LIST_OF_PEERS.removeAndDestroy(peer);
	}
}

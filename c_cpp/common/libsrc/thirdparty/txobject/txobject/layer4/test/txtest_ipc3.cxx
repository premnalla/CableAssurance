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
#include "txostring.h"
#include "txipcsrvr.h"
#include "txntwrkmsg.h"

txIPCServer* G_IPCSERVER;

static int G_EXIT_FLAG = 0;
static txPeerList G_LIST_OF_PEERS;
static txEvent G_EXIT_EVENT("EXIT");

static void shutDown (int);

void receiveData (void*);
void printPacket (const char*, int);
void printPackets (txNetworkMsgs*);

void contactEstablished  (void*, txAbsPeer*);
void contactLost  (void*, txAbsPeer*);

void initialize(int argc, char** argv)
{
	#if !defined WIN32
		struct sigaction action;
		sigemptyset(&action.sa_mask);
		action.sa_flags = 0;
		action.sa_handler = shutDown;
		sigaction(SIGINT, &action, 0);
	#else
		signal(SIGINT, shutDown);
	#endif

	if (argc < 5)
	{
		cout << "usage : G_IPCSERVER host port remote_host ";
		cout << "remote_port" << endl;
		exit(0);
	}

	cout << "IpcServer set on Port : " << atoi(argv[2]) << endl;
}

void ipcTest (void* obj)
{
	int len1;
	int len2;
	char* msg1;
	char* msg2;
	txAbsPeer* peer;
	char** argv = (char**) obj;

	txIpPeer self(argv[1], atoi(argv[2]), txAbsPeer::TCP);
	txIpPeer peer1(argv[3], atoi(argv[4]), txAbsPeer::TCP);
	txIpPeer peer2(argv[3], atoi(argv[4]), txAbsPeer::UDP);

	self.setEncryption(txOctetString("COOKIE", strlen("COOKIE")));

	G_IPCSERVER = new txIPCServer(self);
	G_IPCSERVER->registerContactEstablished(contactEstablished, 0);
	G_IPCSERVER->registerContactLost(contactLost, 0);

	msg1 = new char[strlen("TCP FROM ") + strlen(argv[2]) + 1];
	strcpy(msg1, "TCP FROM ");
	strcat(msg1, argv[2]);
	len1 = strlen(msg1);

	msg2 = new char[strlen("UDP FROM ") + strlen(argv[2]) + 1];
	strcpy(msg2, "UDP FROM ");
	strcat(msg2, argv[2]);
	len2 = strlen(msg2);

	txThread::start(receiveData, 0, "Receive Data");

	G_IPCSERVER->sendTo(msg1, len1, peer1);
	G_IPCSERVER->sendTo(msg2, len2, peer2);

	while (!G_EXIT_FLAG)
	{
		int entries = G_LIST_OF_PEERS.entries();

		for (int i = 0; i < entries; i++)
		{
			peer = (txAbsPeer*) G_LIST_OF_PEERS.at(i);

			peer->setType(txAbsPeer::TCP);
			G_IPCSERVER->sendTo(msg1, len1, *peer);

			peer->setType(txAbsPeer::UDP);
			G_IPCSERVER->sendTo(msg2, len2, *peer);
		}

		txThread::yield(G_EXIT_EVENT, 1000);
	}

	delete msg1;
	delete msg2;
	delete G_IPCSERVER;
}

int main(int argc, char** argv)
{
	initialize(argc, argv);

	txThread::start(ipcTest, argv, "ipcTest", 50);

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
		G_IPCSERVER->recvFrom(packets);

		printPackets(&packets);
	}
}

void contactEstablished  (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactEstablished : " << peer->getId();
		cout << " type : " << peer->getType() << endl;


		G_LIST_OF_PEERS.append(peer->clone());
	}
}

void contactLost  (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactLost : " << peer->getId();
		cout << " type : " << peer->getType() << endl;

		G_LIST_OF_PEERS.removeAndDestroy(peer);
	}
}


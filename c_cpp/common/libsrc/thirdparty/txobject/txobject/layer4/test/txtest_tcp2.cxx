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

#include "txtimer.h"
#include "txevent.h"
#include "txthread.h"
#include "txippeer.h"
#include "txtcpsrvr.h"
#include "txntwrkmsg.h"

txTCPServer* G_TCPSERVER;

static int G_EXIT_FLAG = 0;
static int G_RECV_COUNT = 0;
static int G_SEND_COUNT = 0;
static double G_STOP_TIME = 0;
static double G_START_TIME = 0;
static int G_THREAD_PRIORITY = 3;
static txPeerList G_LIST_OF_PEERS;
static txEvent G_EXIT_EVENT("EXIT");

static char* input = "XXXXXXXXXXXXXXXXXXXX 50 CHARS XXXXXXXXXXXXXXXXXXXX";

static void shutDown (int);

void receiveData (void*);
void printPackets (txNetworkMsgs*);

void contactEstablished (void*, txAbsPeer*);
void contactLost (void*, txAbsPeer*);

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
		cout << "usage : tcpserver host port remote_host ";
		cout << "remote_port" << endl;
		exit(0);
	}

	cout << "TcpServer set on Port : " << atoi(argv[2]) << endl;
}

void tcpTest (void* obj)
{
	char* msg;
	txIpPeer* peer;
	char** argv = (char**) obj;

	txIpPeer self(argv[1], atoi(argv[2]), txAbsPeer::TCP);
	txIpPeer peer1(argv[3], atoi(argv[4]), txAbsPeer::TCP);

	G_TCPSERVER = new txTCPServer(self);
	G_TCPSERVER->registerContactEstablished(contactEstablished, 0);
	G_TCPSERVER->registerContactLost(contactLost, 0);
	G_TCPSERVER->contact(peer1);

	msg = new char[strlen(input) + 1];
	strcpy(msg, input);

	int str_length = strlen(msg);
	  
	txThread::start(receiveData, 0, "recv", G_THREAD_PRIORITY);

	while (!G_EXIT_FLAG)
	{
		int entries = G_LIST_OF_PEERS.entries();

		for (int i = 0; i < entries; i++)
		{
			peer = (txIpPeer*) G_LIST_OF_PEERS.at(i);
			G_TCPSERVER->sendTo(msg, str_length, *peer);

			G_SEND_COUNT++;

			if (G_EXIT_FLAG)
			{
				break;
			}
		}

		if (!entries)
		{
			txThread::yield(G_EXIT_EVENT, 1000);
		}
	}

	delete msg; msg = 0;
	delete G_TCPSERVER; G_TCPSERVER = 0;
}

int main(int argc, char** argv)
{
	initialize(argc, argv);

	txThread::start(tcpTest, argv, "tcpTest", G_THREAD_PRIORITY);

	while (!G_EXIT_FLAG)
	{
		txThread::yield(G_EXIT_EVENT, 1000);
	}

	cout << "Send Count : " << G_SEND_COUNT << endl << flush;
	cout << "Receive Count : " << G_RECV_COUNT << endl << flush;

	if (G_STOP_TIME)
	{
		cout << "Total Time : " << G_STOP_TIME-G_START_TIME;
		cout << endl << flush;
	}

	G_LIST_OF_PEERS.clearAndDestroy();

	return 1;
}


static void shutDown( int )
{
	G_STOP_TIME = txTimer::currentTime() * 0.001;

        cout << "\n<CONTROL-C> SIGNAL." << endl;
        G_EXIT_FLAG = 1;
	G_EXIT_EVENT.trigger();
}

void printPackets (txNetworkMsgs* packets)
{
	txNetworkMsg* packet;

	while (packet = packets->get())
	{
		delete packet;

		G_RECV_COUNT++;
	}
}

void receiveData (void*)
{
	txNetworkMsgs packets;

	while (!G_EXIT_FLAG)
	{
		if (G_TCPSERVER && G_LIST_OF_PEERS.entries())
		{
			G_TCPSERVER->recvFrom(packets);
			printPackets(&packets);
		}
		else
		{
			txThread::yield(G_EXIT_EVENT, 1000);
		}
	}
}

void contactEstablished (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactEstablished : " << peer->getId() << endl;

		G_LIST_OF_PEERS.append(peer->clone());

		G_START_TIME = txTimer::currentTime() * 0.001;
	}
}

void contactLost (void*, txAbsPeer* peer)
{
	if (peer)
	{
		cout << "contactLost : " << peer->getId() << endl;

		G_LIST_OF_PEERS.removeAndDestroy(peer);
	}
}


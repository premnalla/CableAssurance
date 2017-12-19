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

#include <sys/types.h>
#include <fcntl.h>
#include <signal.h>
#include <unistd.h>

#include <fstream.h>
#include <iostream.h>

#include "txlist.h"
#include "txevent.h"
#include "txtimer.h"
#include "txthread.h"
#include "txsocket.h"
#include "txippeer.h"
#include "txdobjmgr.h"
#include "txpeerlist.h"
#include "txdobjspace.h"
#include "txdobjglbls.h"

#include "txperf.h"

txDObjMgr* G_MGR_PTR = 0;
static int G_EXIT_FLAG = 0;
static txEvent G_EXIT_EVENT("EXIT");
static int G_STATIC_PEER_CONFIG = 1;

class PeerObject : public txPerf
{
	public :

	PeerObject (txUInt32* i, txString* s) : txPerf(i, s)
	{
	}

	void created (void)
	{
		cout << "===>> PeerObject::created called" << endl;
	}

	ARBITRATE arbitrateObject (txDObject& object)
	{
		cout << "===>> PeerObject::arbitrateObject called" << endl;
		ARBITRATE value = txDObject::arbitrateObject( object );
		if ( value == RENAME ) cout << "RENAME this object." << endl;
		if ( value == REMOVE ) cout << "REMOVE this object." << endl;
		if ( value == REPLACE ) cout << "REPLACE this object." << endl;
		return value;
	}
};

void contactEstablished (void*, void* obj)
{
	txIpPeer* peer = (txIpPeer*) obj;
 
	cout << "-----------> CONTACT ESTABLISH : " << peer->getId() << endl;
}
 
void contactLost (void*, void* obj)
{
	txIpPeer* peer = (txIpPeer*) obj;
 
	cout << "-----------> CONTACT LOST : " << peer->getId() << endl;
}
 
void systemError (void*, void* obj)
{
	txDObjError* error = (txDObjError*) obj;

	cout << "-----------> ERROR - Code : " << error->code() << endl;

	delete error; error = 0;
}

static void shutDown (int)
{
	cout << "\n<CONTROL-C> SIGNAL." << endl;

	G_EXIT_FLAG = 1;
}

void getInput (char* in_char)
{
	int i = 0;
	int fd = 1;
	in_char[0] = '\0';
	int fcntl_flags = fcntl( fd, F_GETFL, 0 );
	fcntl( fd, F_SETFL, O_NONBLOCK | fcntl_flags );

	while (in_char[0] == '\0' && !G_EXIT_FLAG)
	{
		read(fd, in_char, 80);
		txThread::yield(100);
	}

	while (in_char[i] != '\n') i++;
	in_char[i]='\0';
}

void printMenu (char* in_char)
{
	cout << "\n\nPolicy Engine Test Operations :" << endl;
	cout << "    0 --- Exit" << endl;
	cout << "    1 --- Add a peer" << endl;
	cout << "    2 --- Remove a peer" << endl;
	cout << "    3 --- Get all-known peers" << endl;
	cout << "    4 --- Get active peers" << endl;
	cout << "\nSelect: " ; cout.flush();
	getInput(in_char);
}

void add_peer (void)
{
	char peer_name[80], peer_port[20];
	cout << "    Peer name : " ; cout.flush();
	getInput( peer_name );
	cout << "    Peer port : " ; cout.flush();
	getInput( peer_port );
	int port = atoi( peer_port );
	txIpPeer peer( peer_name, port, txAbsPeer::TCP );
	G_MGR_PTR->addPeer( peer );
}

void rmv_peer (void)
{
	char peer_name[80], peer_port[20];
	cout << "    Peer name : " ; cout.flush();
	getInput( peer_name );
	cout << "    Peer port : " ; cout.flush();
	getInput( peer_port );
	int port = atoi( peer_port );
	txIpPeer peer( peer_name, port, txAbsPeer::TCP );
	G_MGR_PTR->removePeer( peer );
}

void get_known_peers (void)
{
	txIpPeer* peer = 0;
	const txPeerList* list = G_MGR_PTR->allKnownPeers();

	for (int i = 0; list && i < list->entries(); i++)
	{
		peer = (txIpPeer*)list->at(i);
		cout << "    Peer : " << peer->getId() << endl;
	}
}

void get_active_peers (void)
{
	txIpPeer* peer = 0;
	const txPeerList* list = G_MGR_PTR->activePeers();

	for (int i = 0; list && i < list->entries(); i++)
	{
		peer = (txIpPeer*)list->at(i);
		cout << "    Peer : " << peer->getId() << endl;
	}
}

void initialize (int, char** argv)
{
	char IP[16]; txSocket::generateLocalIp(txSocket::OUT_OF_BAND, IP);
	
	cout << "Host[" << IP << "] and port[" << atoi(argv[1]) << "]";
	cout << endl;

	txIpPeer local(IP, atoi(argv[1]), txAbsPeer::TCP);

	G_MGR_PTR = new txDObjMgr(local, txPeerList(), txList(),
			contactEstablished,
			contactLost,
			systemError, G_STATIC_PEER_CONFIG, 1, 0);
}

void PeerTest (void*)
{
	// Real Time Performace Times
	double start_time = 0;
	double stop_time = 0;

	// CPU Performance Timer
	PeerObject* peer;

	start_time = txTimer::currentTime() * 0.001;

	G_MGR_PTR->joinSpace("THE_PEER_SPACE");

	stop_time = txTimer::currentTime() * 0.001;

	cout << "\n\nTIME TO JOIN SPACE REAL TIME : "<< stop_time-start_time;
	cout << endl;

	start_time = txTimer::currentTime() * 0.001;

	peer = new PeerObject(new txUInt32(1), new txString("string"));

	TX_REGISTER_NAMED_DOBJECT_IN_SPACE(
		PeerObject,
		peer,
		"THE_PEER_SPACE",
		"PeerObject",
		G_MGR_PTR->getPeer());

	stop_time = txTimer::currentTime() * 0.001;

	cout << endl;
	cout << "TIME TO CREATE : 1 Global OBJECT" <<  endl;
	cout << "REAL TIME : " << stop_time-start_time << endl; 
	cout << endl;

	char in_char[3] = "5";
	do
	{
		printMenu(in_char);

		switch(in_char[0])
		{
			case '0': G_EXIT_FLAG = 1; break;
			case '1': add_peer(); break;
			case '2': rmv_peer(); break;
			case '3': get_known_peers(); break;
			case '4': get_active_peers(); break;
			default : break;
		}

	} while (!G_EXIT_FLAG);

	start_time = txTimer::currentTime() * 0.001;

	delete peer; peer = 0;

	stop_time = txTimer::currentTime() * 0.001;

	cout << endl;
	cout << "TIME TO DELETE : 1 Global OBJECT" <<  endl;
	cout << "REAL TIME : " << stop_time-start_time << endl;
	cout << endl;

	G_EXIT_EVENT.trigger();
}

int main(int argc, char** argv)
{
	#if !defined WIN32
		struct sigaction action;
		sigemptyset (&action.sa_mask );
		action.sa_flags = 0 ;
		action.sa_handler = shutDown;
		sigaction (SIGINT, &action, 0);
	#else
		signal(SIGINT, shutDown);
	#endif

	if (argc != 2)
	{
		cout << "Usage : program port" << endl;
		exit(-1);
	}

	initialize(argc, argv);

	txThread::start(PeerTest, 0, "PeerTest");

	txThread::yield(G_EXIT_EVENT);

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages
	
	return 1;
}


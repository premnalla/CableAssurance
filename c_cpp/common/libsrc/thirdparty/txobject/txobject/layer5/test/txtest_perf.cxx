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
#include "txdobjglbls.h"
#include "txdobjspace.h"

#include "txperf.h"

txList G_OBJECT_LIST;
txDObjMgr* G_MGR_PTR = 0;

static int G_EXIT_FLAG = 0;
static int G_NUMBER_OF_OBJECT = 0;
static txEvent G_EXIT_EVENT1("EXIT1");
static txEvent G_EXIT_EVENT2("EXIT2");

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
	G_EXIT_EVENT1.trigger();
}

void initialize (int, char** argv)
{
	char IP[16]; txSocket::generateLocalIp(txSocket::OUT_OF_BAND, IP);

	cout << "Host[" << IP << "] and port[" << atoi(argv[1]) << "]";
	cout << endl;

	txIpPeer local(IP, atoi(argv[1]), txAbsPeer::TCP);

	int port = 0;
	ifstream infile;
	infile.open(argv[2]);
	char peer_name[1024];

	txIpPeer* peer;
	txPeerList peers(TX_AUTODEL_ON);

	while (infile >> peer_name && infile >> port)
	{
		peer = new txIpPeer(peer_name, port, txAbsPeer::TCP);
		peers.append(peer);
	}

	G_MGR_PTR = new txDObjMgr(local, peers, txList(),
			contactEstablished,
			contactLost,
			systemError);
}

void PerformanceTest (void*)
{
	// Real Time Performace Times
	double start_time = 0;
	double stop_time = 0;

	// CPU Performance Timer
	txPerf* PERF;

	start_time = txTimer::currentTime() * 0.001;

	G_MGR_PTR->joinSpace("THE_PERF_SPACE");

	stop_time = txTimer::currentTime() * 0.001;

	cout << "\n\nTIME TO JOIN SPACE REAL TIME : "<< stop_time-start_time;
	cout << endl;

	start_time = txTimer::currentTime() * 0.001;

	for (int i = 0; i < G_NUMBER_OF_OBJECT; i++)
	{
		PERF = new txPerf(new txUInt32(1), new txString("string"));

		TX_REGISTER_DOBJECT(
			txPerf,
			PERF,
			"THE_PERF_SPACE",
			G_MGR_PTR->getPeer());

		G_OBJECT_LIST.append(PERF);

		if (!i % 10)
		{
			txThread::yield();
		}
	}

	stop_time = txTimer::currentTime() * 0.001;

	cout << endl;
	cout << "TIME TO CREATE : ";
	cout << G_NUMBER_OF_OBJECT <<" OBJECTS" <<  endl;
	cout << "REAL TIME : " << stop_time-start_time << endl; 
	cout << endl;

	while (!G_EXIT_FLAG)
	{
		txThread::yield(G_EXIT_EVENT1, 2000);
	}

	start_time = txTimer::currentTime() * 0.001;

	while (PERF = (txPerf*) G_OBJECT_LIST.get())
	{
		delete PERF; PERF = 0;

		txThread::yield();
	}

	stop_time = txTimer::currentTime() * 0.001;

	cout << endl;
	cout << "TIME TO DELETE : ";
	cout << G_NUMBER_OF_OBJECT << " OBJECTS " << endl;
	cout << "REAL TIME : " << stop_time-start_time << endl;
	cout << endl;

	G_EXIT_EVENT2.trigger();
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

	if (argc != 4)
	{
		cout << "Usage : program HOST_PORT_NAME_FILE";
		cout << " NumberOfObjects" << endl;
		exit(-1);
	}

	G_NUMBER_OF_OBJECT = atoi(argv[3]);

	initialize(argc, argv);

	txThread::start(PerformanceTest, 0, "PerformanceTest");

	txThread::yield(G_EXIT_EVENT2);

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages
	
	return 1;
}


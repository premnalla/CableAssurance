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
#include "txthread.h"
#include "txippeer.h"
#include "txostring.h"
#include "txdobjmgr.h"
#include "txpeerlist.h"
#include "txdobjspace.h"

#include "txcount.h"
#include "txfastcount.h"

static int G_EXIT_FLAG = 0;
static txDObjMgr* G_MGR_PTR = 0;
static txEvent G_EXIT_EVENT1("EXIT1");
static txEvent G_EXIT_EVENT2("EXIT2");

unsigned long G_COUNT = 100000000;

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
	cout << "DEMO HOW TO GET THE LOCAL IP : " << IP << endl;
 
	txIpPeer local(IP, atoi(argv[1]), txAbsPeer::TCP);

	cout << "Local id : " << local.getId() << endl;

	local.setEncryption(txOctetString("COOKIE", strlen("COOKIE")));

	// local peer variabls
	int port = 0;
	char name[1024];
	ifstream infile;
	infile.open(argv[2]);

	txIpPeer* peer;
	txPeerList peers(TX_AUTODEL_ON);

	// local peers from file
	while (infile >> name && infile >> port)
	{
		txSocket::generateIp(name, IP);
		cout << "DEMO HOW TO GET YOUR PEER IP : " << IP << endl;

		peer = new txIpPeer(name, port, txAbsPeer::TCP);
		peers.append(peer);
	}

	G_MGR_PTR = new txDObjMgr(local, peers, txList(),
		contactEstablished,
		contactLost,
		systemError, 0, 2, 10000);
}

void txCountTest (void*)
{
	txCount* count;
	txFastCount* fastcount;

	while (!G_EXIT_FLAG && (G_COUNT > 0))
	{
		count = new txCount(new txUInt32(1), new txUInt32(20));
		fastcount = new txFastCount(new txUInt32(1), new txUInt32(20));	

		TX_REGISTER_DOBJECT(
			txCount,
			count,
			"COUNT_SPACE",
			G_MGR_PTR->getPeer());

		TX_REGISTER_DOBJECT(
			txFastCount,
			fastcount,
			"COUNT_SPACE",
			G_MGR_PTR->getPeer());

		(*count)->incrementCount();
		(*fastcount)->incrementCount();
		(*fastcount)->incrementFastCount();

		txThread::yield(G_EXIT_EVENT1, 1000);

		delete count; count = 0;
		delete fastcount; fastcount = 0;

		G_COUNT--;
	}

	G_EXIT_FLAG = 1;
	G_EXIT_EVENT2.trigger();
}

int main (int argc, char** argv)
{
	#if !defined WIN32
		struct sigaction action;
		sigemptyset (&action.sa_mask );
		action.sa_flags = 0 ;
		action.sa_handler = shutDown;
		sigaction (SIGINT, &action, 0);
	#else // NT
		signal(SIGINT, shutDown);
	#endif

	if (argc < 3) 
	{
 		cout << "Usage : program LocalPort HOSTFILE ";
		cout << "[Iterations]" << endl;
		exit(-1);
	}
	else if (argc == 4)
	{
		G_COUNT = atoi(argv[3]);
	}

	initialize(argc, argv);

	txThread::start(txCountTest, 0, "test");

	txThread::yield(G_EXIT_EVENT2);

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages

	return 1;
}


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

#include "txcount.h"

txDObjMgr* G_MGR_PTR;
int G_ITERATIONS = 10;
static int G_EXIT_FLAG = 0;
static int G_FINISHED_FLAG = 0;
static txList G_SPACES(TX_AUTODEL_ON);

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

void initialize (int argc, char** argv)
{
	unsigned long netaddr;
	netaddr = txSocket::generateLocalNetAddr(txSocket::OUT_OF_BAND);

	txIpPeer local(netaddr, atoi(argv[1]), txAbsPeer::TCP);

	int port = 0;
	char name[1024];
	ifstream infile;
	infile.open(argv[2]);

	txIpPeer* peer;
	txPeerList peers(TX_AUTODEL_ON);

	while (infile >> name && infile >> port)
	{
		peer = new txIpPeer(name, port, txAbsPeer::TCP);
		peers.append(peer);
	}

	if (argc >= 4)
	{
		G_ITERATIONS = atoi(argv[3]);
	}

	G_SPACES.append(new txString("INITIAL_SPACE1"));
	G_SPACES.append(new txString("INITIAL_SPACE2"));
	G_SPACES.append(new txString("INTIIAL_SPACE3"));
	G_SPACES.append(new txString("INITIAL_SPACE4"));
	G_SPACES.append(new txString("INITIAL_SPACE5"));
	G_SPACES.append(new txString("INITIAL_SPACE6"));
	G_SPACES.append(new txString("INITIAL_SPACE7"));
	G_SPACES.append(new txString("INITIAL_SPACE8"));
	G_SPACES.append(new txString("INITIAL_SPACE9"));
	G_SPACES.append(new txString("INITIAL_SPACE10"));

	G_MGR_PTR = new txDObjMgr(local, peers, G_SPACES,
			contactEstablished,
			contactLost,
			systemError);
}

void init_space (void* obj)
{
	char* name = (char*) obj;
	txCount* count = new txCount(new txUInt32(1), new txUInt32(10));

	TX_REGISTER_NAMED_DOBJECT_IN_SPACE(
		txCount, count, name, "txCountObject",
		G_MGR_PTR->getPeer());

	for (int i = 0; i < G_ITERATIONS; i++)
	{
		(*count)->incrementCount();

		txThread::yield(500);
	}

	delete count; count = 0;

	--G_FINISHED_FLAG;
}

void txCount_test (void* obj)
{
	char* name = (char*) obj;
	txCount* count = new txCount(new txUInt32(1), new txUInt32(10));

	TX_REGISTER_NAMED_DOBJECT_IN_SPACE(
		txCount, count, name, "txCountObject",
		G_MGR_PTR->getPeer());

	for (int i = 0; i < G_ITERATIONS; i++)
	{
		(*count)->incrementCount();
		txThread::yield(500);
	}

	delete count; count = 0;

	--G_FINISHED_FLAG;
}

int main (int argc, char** argv)
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

	if (argc < 3)
	{
		cout << "Usage : program LocalPort HOSTFILE";
		cout << " [iterations]" << endl;
		exit(-1);
	}

	initialize(argc, argv);

	for (int i = 0; i < G_SPACES.entries(); i++)
	{
		G_FINISHED_FLAG++;
		const char* name = ((txString*) G_SPACES.at(i))->data();
		txThread::start(init_space, (void*) name, "init_space");
	}

	txThread::start(txCount_test, (void*) "JOIN_SPACE1" , "txCount_test");

	G_FINISHED_FLAG++;

	txThread::start(txCount_test, (void*) "JOIN_SPACE2" , "txCount_test");

	G_FINISHED_FLAG++;

	txThread::start(txCount_test, (void*) "JOIN_SPACE3" , "txCount_test");

	G_FINISHED_FLAG++;

	txThread::start(txCount_test, (void*) "JOIN_SPACE4" , "txCount_test");

	G_FINISHED_FLAG++;

	txThread::start(txCount_test, (void*) "JOIN_SPACE5" , "txCount_test");

	G_FINISHED_FLAG++;

	txThread::start(txCount_test, (void*) "JOIN_SPACE6" , "txCount_test");

	G_FINISHED_FLAG++;

	while (G_FINISHED_FLAG && !G_EXIT_FLAG)
	{
		txThread::yield(1000);
	}

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages

	return 1;
}


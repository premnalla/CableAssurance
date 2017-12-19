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

#include "txcount.h"

txList G_OBJ_LIST;
int G_EXIT_FLAG = 0;
txDObjMgr* G_MGR_PTR = 0;
const int G_MAX_TIME_REQ = 6000;

void contactEstablished (void*, void* obj)
{
	txIpPeer* peer = (txIpPeer*) obj;
	cout << "\n\n\n-----------> MGR get called." << endl;
	cout << "             PEER UP: " << peer->getId() << "\n\n" << endl;
}

void contactLost (void* , void* obj)
{
	txIpPeer* peer = (txIpPeer*) obj;
	cout << "\n\n\n- - - - - -> MGR get called." << endl;
	cout << "             PEER DOWN: " << peer->getId() << "\n\n" << endl;
}

void errorCallback (void* , void* obj)
{
	txDObjError* error = (txDObjError*) obj;
	cout << endl;
	cout << " TX ERROR - Code : " << error->code();
	cout << "\n\n\n" << flush;
	delete error; error = 0;
}

static void shutDown (int)
{
	G_EXIT_FLAG = 1;
}

void create_objs_in_space (const char* space_name)
{
	txCount* obj = 0;
	int do_not_create = 0;
	const txDObjSpace* d_space = G_MGR_PTR->accessSpace(space_name);

	if (d_space)
	{
		txList list;

		d_space->objects(list);

		if (list.entries() > 5)
		{
			do_not_create = 1;
		}

		list.clearAndDestroy();

		if (do_not_create) return;
	}

	for ( int i = 0; i < 10; i++ )
	{
		obj = new txCount(new txUInt32(1), new txUInt32(10));

		TX_REGISTER_DOBJECT(
			txCount,
			obj,
			space_name,
			G_MGR_PTR->getPeer());

		G_OBJ_LIST.append(obj);

		(*obj)->incrementCount();

		txThread::yield();
	}
}

void delete_objs_in_space (const char* space_name)
{
	txCount* obj = 0;
	txCount* t_obj = 0;
	const txDObjSpace* d_space = G_MGR_PTR->accessSpace(space_name);

	if (d_space)
	{
		txList list;

		d_space->objects(list);

		while (obj = (txCount*) list.get())
		{
			for (int i = 0; i < G_OBJ_LIST.entries(); i++)
			{
				t_obj = (txCount*) G_OBJ_LIST.at(i);

				if (!t_obj) break;
			
				if (strcmp((*obj)->oid(),(*t_obj)->oid())==0)
				{
					cout << "Trying to delete : ";
					cout << (*t_obj)->oid() << endl;

					G_OBJ_LIST.removeReference(t_obj);
					delete t_obj; t_obj = 0;
					break;
				}
			}

			delete obj; obj = 0;

			txThread::yield();
		}
	}
}

static void update_object (void* an_obj)
{
	txCount* obj = (txCount*) an_obj;
	const char* my_oid = (*obj)->oid();
	const char* tmp_id = (*obj)->owner()->getId();
	txUInt32* tmp_int = (txUInt32*) (*obj)->getCount();

	if (tmp_int)
	{
		cout << "**** : " << (*obj)->oid() << " " << tmp_int->value();
		cout << endl;
		delete tmp_int; tmp_int = 0;
	}

	(*obj)->incrementCount();

	delete obj; obj = 0;
}

void update_of_objs_on_all_spaces (void)
{
	txCount* obj = 0;
	txCount* t_obj = 0;
	txList* spaces = (txList*) G_MGR_PTR->spacesOfInterest();

	for ( int i = 0; i < spaces->entries(); i++ )
	{
		if (!spaces->at(i)) return;

		const char* space_name = ((txString*) spaces->at(i))->data();
		const txDObjSpace* d_space = G_MGR_PTR->accessSpace(space_name);
		cout << "UPDATE ON SPACE : " << space_name << endl;

		if (d_space)
		{
			txList list;
			d_space->objects(list);

			while (obj = (txCount*) list.get())
			{
				t_obj = (txCount*) (*obj)->getReference();

				txThread::start(update_object, t_obj, "Up");

				delete obj; obj = 0;
			}
		}
	}
}

static void start_test (void*)
{
	char data[100];
	txList* spaces = 0;
	int time_delay = 0;
	const char* name = 0;

	for (int i = 0; i < 6; i++)
	{
		sprintf(data, "TEST_SPACE_%d", i);
		G_MGR_PTR->joinSpace(data);
	}

	for(int count = 0; !G_EXIT_FLAG; count++)
	{
		srand((unsigned int) txTimer::currentTime());

		sprintf(data, "TEST_SPACE_%d", count);
		create_objs_in_space(data);

		update_of_objs_on_all_spaces();

		spaces = (txList*) G_MGR_PTR->spacesOfInterest();

		for ( int i = 0; i < spaces->entries(); i++ )
		{
			if (!spaces->at(i)) return;

			name = ((txString*) spaces->at(i))->data();

			delete_objs_in_space(name);
		}

		if (count > 5)
		{
			count = 0;
		
			spaces = (txList*) G_MGR_PTR->spacesOfInterest();

			for ( int i = 0; i < spaces->entries(); i++ )
			{
				if (!spaces->at(i)) return;

				name = ((txString*) spaces->at(i))->data();

				G_MGR_PTR->leaveSpace(name);
			}
		}

		time_delay = abs(rand() % G_MAX_TIME_REQ);

		if (time_delay > 4000) time_delay /= 3;

		cout << "\n**** DELAY TIME : " << time_delay << endl << endl;

		txThread::yield(time_delay); // Random waiting duration.
	}

	txThread::yield(1000); // Let other threads complete space operations.

	G_OBJ_LIST.clearAndDestroy();

	cout << " ... shutting down ... " << endl;

	G_EXIT_FLAG = 2;

	txThread::yield(1000);
}

void initialize (int argc, char** argv)
{
	unsigned long netaddr;
	netaddr = txSocket::generateLocalNetAddr(txSocket::OUT_OF_BAND);

	cout << "Host[" << netaddr << "] and port[";
	cout << atoi(argv[1]) << "]" << endl;

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

	G_MGR_PTR = new txDObjMgr(local, peers, txList(),
		contactEstablished,
		contactLost,
		errorCallback);
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
		cout << "Usage : program local_port HOSTFILE";
		cout << endl;
		exit(-1);
	}

	initialize(argc, argv);

	txThread::start(start_test, 0, "main test thread");

	while (!G_EXIT_FLAG)
	{
		txThread::yield(1000);
	}

	cout << endl << "Waiting to clean up....." << endl;

	G_MGR_PTR->shutDown();

	while (G_EXIT_FLAG != 2)
	{
		txThread::yield(1000);
	}

	cout << "DONE." << endl;

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages

	return 0;
}


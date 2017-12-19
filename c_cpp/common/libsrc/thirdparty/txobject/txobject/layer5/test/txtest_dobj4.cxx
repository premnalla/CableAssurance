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

#include "txattrobj.h"

int G_ITERATIONS = 50;
txDObjMgr* G_MGR_PTR = 0;
txEvent G_EXIT_EVENT("EXIT");

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

	G_MGR_PTR = new txDObjMgr(local, peers, txList(),
			contactEstablished,
			contactLost,
			systemError);
}

void reference_test (void* obj)
{
	char* name = (char*) obj;

	txAttrObj* object = new txAttrObj();

	TX_REGISTER_NAMED_DOBJECT_IN_SPACE(
		txAttrObj, object, name, "AttrObj",
		G_MGR_PTR->getPeer());
	
	cout << "Starting Dictionary Tests " << endl;
	cout << "Dictionary Should Be Empty " << (*object)->getDictEntries();
	cout << endl;

	int i = 0;
	int index = 0;
	while (i <  100)
	{
		cout << "Add Value " << i << " at index " << index << endl;
		txUInt32 tmp_int(index);
		txUInt32 tmp_value(i);
		(*object)->addDictEntry(&tmp_int, &tmp_value);
		i++;
		index = i%10;
	}

	i = 0;
	while (i < 10)
	{
		cout << "Delete Index " << i << endl;
		txUInt32 tmp_int(i);
		(*object)->removeDictEntry(&tmp_int);
		i++;
	}

	cout << "Ending Dictionary Test, I Should have 0 entries "<<endl;
	cout << "Dictionary Entries: " << (*object)->getDictEntries() <<endl;

	cout << "Starting List Tests " << endl;
	cout << "The List Should Be Empty " << (*object)->getListEntries();
	cout << endl;

	i = 0;
	index = 0;
	while (i <  20)
	{
		cout << "List Before Adding " << endl;
		cout << "Add Value " << i << " at index " << index << endl;
		txUInt32 tmp_int(index);
		txUInt32 tmp_value(i);
		(*object)->addListEntry(&tmp_int, &tmp_value);
		i++;
		index = i%10;
	}
	
	i = 0;
	while (i < 10)
	{
		txUInt32 tmp_int(0);
		(*object)->removeListEntry(&tmp_int);
		i++;
	}

	cout << "Ending List Test, List Should have 0 entries "<<endl;
	cout << "List Entries :" << (*object)->getListEntries() << endl;
	delete object; object = 0;

	G_EXIT_EVENT.trigger();
}

int main (int argc, char** argv)
{
	if (argc < 3)
	{
		cout << "Usage : program LocalPort HOSTFILE";
		cout << " [iterations]" << endl;
		exit(-1);
	}

	initialize(argc, argv);

	txThread::start(reference_test, (void*) "ATTRIBUTE" , "test");

	txThread::yield(G_EXIT_EVENT);

	delete G_MGR_PTR; G_MGR_PTR = 0; // Stop processing messages

	return 1;
}


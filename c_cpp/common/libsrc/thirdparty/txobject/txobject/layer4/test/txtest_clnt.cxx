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
#include <stdlib.h>
#include <iostream.h>

#include "txevent.h"
#include "txthread.h"
#include "txsocket.h"
#include "txntwrkmsg.h"

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX
	#include <sys/types.h>
	#include <sys/socket.h>
#endif

#if defined TX_WIN
	#include <winsock.h>
#endif

txSocket* G_CLIENT;
static int G_EXIT_FLAG = 0;

void receiveData (void*);
void printPacket (const char*, int);
void printPackets (txNetworkMsgs*);
void contactLost (void*, txSocket*);
void contactEstablished (void*, txSocket*);

int main(int argc, char** argv)
{
	if (argc != 3)
	{
		cout << "usage : client hostname port" << endl;
		exit(0);
	}

	G_CLIENT = new txSocket(SOCK_STREAM);
	G_CLIENT->registerContactEstablished(contactEstablished, 0);
	G_CLIENT->registerContactLost(contactLost, 0);

	unsigned long netaddr = txSocket::generateNetAddr(argv[1]);

	G_CLIENT->connect(netaddr, atoi(argv[2]));

	txThread::start(receiveData, 0, "Receive Data");

	while (!G_EXIT_FLAG)
	{
		txThread::yield(1000);
	}

	delete G_CLIENT;

	return 1;
}

void contactLost (void*, txSocket* sock)
{
	cout << "Lost Server : " << sock->netaddr();
	cout << "@" << sock->port() << endl;

	G_EXIT_FLAG = 1;
}

void contactEstablished (void*, txSocket* sock) 
{
	cout << "Established Server : " << sock->netaddr();
	cout << "@" << sock->port() << endl;
}

void printPackets (txNetworkMsgs* packets)
{
	int flag = 0;
	txNetworkMsg* packet = 0;

	while (packet = packets->get())
	{
		printPacket(packet->data(), packet->length());

		delete packet; packet = 0;

		flag = 1;
	}

	if (flag)
	{
		cout << "************* END OF STREAM *************" << endl;

		G_EXIT_FLAG = 1;
	}
}

void printPacket (const char* packet, int length)
{
	char* str = new char[length + 1];
	memcpy(str, packet, length);
	str[length] = '\0';

	cout << "packet : " << str << endl;

	delete str; str = 0;
}

void receiveData (void*)
{
	txNetworkMsgs packets;

	cout << "Receiving Data" << endl;

	G_CLIENT->recv(packets);

	printPackets(&packets);
}


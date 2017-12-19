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

#include <stdlib.h>
#include <string.h>
#include <iostream.h>

#include "txevent.h"
#include "txthread.h"
#include "txsocket.h"

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX
	#include <sys/types.h>
	#include <sys/socket.h>
#endif

#if defined TX_WIN
	#include <winsock.h>
#endif

txSocket* G_SERVER;
txSocket* G_CLIENT;
static int G_EXIT_FLAG = 0;

void sendData (void*, txSocket*);
void contactLost (void*, txSocket*);
void contactEstablished (void*, txSocket*);

int main(int argc, char** argv)
{
	if (argc != 2)
	{
		cout << "usage : server port" << endl;
		exit(0);
	}

	cout << "Server set on Port : " << atoi(argv[1]) << endl;
	
	G_SERVER = new txSocket(SOCK_STREAM);
	G_SERVER->registerContactEstablished(contactEstablished, 0);

	G_SERVER->bind(atoi(argv[1]));
	G_SERVER->listen(1);

	while (!G_EXIT_FLAG)
	{
		txThread::yield(1000);
	}

	delete G_CLIENT;
	delete G_SERVER;

	return 1;
}

void contactLost (void*, txSocket* G_CLIENT)
{
	cout << "Lost Client : " << G_CLIENT->netaddr();
	cout << "@" << G_CLIENT->port() << endl;

	G_EXIT_FLAG = 1;
}

void contactEstablished (void*, txSocket* G_CLIENT)
{
	cout << "Established Client : " << G_CLIENT->netaddr();
	cout << "@" << G_CLIENT->port() << endl;

	G_CLIENT->registerContactLost(contactLost, 0);

	sendData(0, G_CLIENT);
}

void sendData (void*, txSocket* sock)
{
	cout << "Sending Data" << endl;

	char data[100] = "HELLO CLIENT";

	sock->send(data, strlen(data));

	G_EXIT_FLAG = 1;
}


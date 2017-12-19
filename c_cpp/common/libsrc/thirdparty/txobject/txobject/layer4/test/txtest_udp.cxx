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
#include <stdlib.h>
#include <string.h>
#include <iostream.h>

#include "txevent.h"
#include "txthread.h"
#include "txsocket.h"

txSocket* G_UDPSOCKET;
static int G_EXIT_FLAG = 0;
static txEvent G_EXIT_EVENT("EXIT");

void receiveData (void*);
static void shutDown (int);

int main(int argc, char** argv)
{
	#if !defined WIN32
		struct sigaction action;
		sigemptyset (&action.sa_mask);
		action.sa_flags = 0;
		action.sa_handler = shutDown;
		sigaction (SIGINT, &action, 0);
	#else
		signal(SIGINT, shutDown);
	#endif

	char* msg_to_send;

	if (argc != 4)
	{
		cout << "usage : program port machine port" << endl;
		exit(0);

	}

	G_UDPSOCKET = new txSocket(SOCK_DGRAM);
	G_UDPSOCKET->bind(atoi(argv[1]));

	txThread::start(receiveData, 0, "Receive Data");

	char host[80];

	txSocket::generateLocalIp(txSocket::OUT_OF_BAND, host);

	msg_to_send = new char[strlen("Message from -> ")
		+ strlen(host) + strlen(" ") + strlen(argv[1]) + 1];

	strcpy(msg_to_send, "Message from -> ");
	strcat(msg_to_send, host);
	strcat(msg_to_send, " ");
	strcat(msg_to_send, argv[1]);

	unsigned long netaddr = txSocket::generateNetAddr(argv[2]);

	while (!G_EXIT_FLAG)
	{
		G_UDPSOCKET->sendTo(
			msg_to_send,
			strlen(msg_to_send),
			netaddr,
			atoi(argv[3]));

		txThread::yield(G_EXIT_EVENT, 500);
	}

	delete msg_to_send;
	delete G_UDPSOCKET;

	return 1;
}

void receiveData (void*)
{
	int port;
	int length;
	int msg_size = 4096;
	unsigned long netaddr;

	char* msg;
	char* data;

	while (!G_EXIT_FLAG)
	{
		data = G_UDPSOCKET->recvFrom(msg_size, length, netaddr, port);

		msg = new char[length + 1];
		memcpy(msg, data, length);
		msg[length] = '\0';

		cout << "Actual Messgae : " << msg << endl;
		cout << "  From : " << netaddr << "@" << port << endl;

		delete data; data = 0;
		delete msg; msg = 0;
	}
}

static void shutDown (int)
{
	cout << "\n<CONTROL-C> SIGNAL." << endl;
	G_EXIT_FLAG = 1;
	G_EXIT_EVENT.trigger();
}


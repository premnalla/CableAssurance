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

#if !defined ( __TXSOCKET_H__ )
#define __TXSOCKET_H__

#include "txevent.h"
#include "txeventq.h"
#include "txntwrkmsg.h"

class txSocket;
class txAbsPacker;

typedef void (*TX_SOCKET_FUNC) (void*, txSocket*);

class txSocket : public txObject
{
	public:
		enum SECURITY
		{
			ON,
			OFF
		};

		enum TYPE
		{
			IN_BAND,
			OUT_OF_BAND
		};

	private:
		enum STATUS
		{
			SOCKET_READY,
			SOCKET_BOUND,
			SOCKET_CLOSED,
			SOCKET_INITIAL,
			SOCKET_LISTENING,
			SOCKET_CONNECTED
		};
			
	private:
		int _fd_INET_;
		STATUS _status_;

		int _type_;
		int _port_;
		int _priority_;
		long _netaddr_;
		long _snd_inet_id_;
		long _rcv_inet_id_;

		txNetworkMsg _snd_;
		txNetworkMsg _rcv_;
		txAbsPacker* _packer_;

		txEventQueue _in_q_;
		txEventQueue _out_q_;

		txEvent _send_event_;
		txEvent _receive_event_;

		void* _contact_lost_args_;
		void* _contact_estab_args_;

		TX_SOCKET_FUNC _contact_lost_func_;
		TX_SOCKET_FUNC _contact_estab_func_;

	private:
		void _initialize_ (void);
		void _uninitialize_ (void);

		void _setNonBlocking_ (int fd);
		void _socketError_ (const char* func);

		static void _sendThreadFunc_ (txSocket* self);
		static void _receiveThreadFunc_ (txSocket* self);

		static void _sendDataCbFunc_ (txSocket* self, int fd);
		static void _receiveDataCbFunc_ (txSocket* self, int fd);

		txSocket* _accept_ (int fd); 
		int _send_ (int fd, const char* stream, int streamlength);

		txSocket (const txSocket& obj);
		txSocket& operator= (const txSocket& obj);
		txSocket (int Inet, int type, int port, int priority);

	private:
		void _registerSendCallBack_ (void);
		void _registerReceiveCallBack_ (void);

		void _unregisterSendCallBack_ (void);
		void _unregisterReceiveCallBack_ (void);
		void _unregisterFileDescriptors_ (void);

		static void _contactLost_ (void* obj);
		static void _contactEstablished_ (void* obj);

	public:
		static const int SOCKET_THRD_PRIORITY;

	public:
		txSocket (
			int type,
			int priority = SOCKET_THRD_PRIORITY);

		txSocket (
			int type,
			txAbsPacker* packer,
			int priority = SOCKET_THRD_PRIORITY);

		~txSocket (void);

		int fd (void);
		int port (void);
		int close (void);
		unsigned long netaddr (void);
		void port (int port) { _port_ = port; };

		int listen (int connections = 1);
		int bind (int port);
		int bind (unsigned long netaddr, int port);
		int connect (unsigned long netaddr, int port);

		int send (txNetworkMsg* packet);
		int send (const char* stream, int streamlength);
		int recv (txNetworkMsgs&, int num = 0, unsigned long time = 0);

		int sendTo (txNetworkMsg* packet);
		int sendTo (const char* data, int len, unsigned long, int);
		char* recvFrom (int s, int& l, unsigned long& net, int& port);

		void registerContactEstablished (const TX_SOCKET_FUNC, void*);
		void registerContactLost (const TX_SOCKET_FUNC, void*);

		void unregisterContactEstablished (void);
		void unregisterContactLost (void);

	public:
		static unsigned long generateLocalNetAddr (txSocket::TYPE);
		static int generateIp (unsigned long netaddr, char* ip);
		static int generateIp (const char* machine, char* ip);

		static unsigned long generateNetAddr (const char* machine);
		static int generateLocalIp (txSocket::TYPE, char* ip);
};

#endif // __TXSOCKET_H__


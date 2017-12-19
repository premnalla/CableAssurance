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

#if !defined ( __TXIPCSRVR_H__ )
#define __TXIPCSRVR_H__

#include "txippeer.h"
#include "txabssrvr.h"
#include "txtcpsrvr.h"
#include "txudpsrvr.h"
#include "txhashtable.h"

class txIPCServer : public txAbsServer
{
	private:
		txIpPeer* _self_;
		txTCPServer* _tcp_server_;
		txUDPServer* _udp_server_;
		int _udpserver_timeout_interval_;

	private:
		void _initCommLayer_ (txAbsPeer::TYPE);

		void _registerServerThread_ (txAbsServer&);

		txAbsServer* _getCommLayer_ (const txAbsPeer&);

		void _setUpPacket_ (txNetworkMsg&, const char*, unsigned long);

		void _enqueueMulticast_ (const char*, unsigned long,
			const txPeerList&);

		static void _contactLost_ (void*, txAbsPeer*);
		static void _contactEstablished_ (void*, txAbsPeer*);

		static void _sendThreadFunc_ (void*);
		static void _receiveThreadFunc_ (void*);

		txIPCServer (const txIPCServer&);
		txIPCServer& operator= (const txIPCServer&);

	public:
		static const int IPCSERVER_THRD_PRIORITY;

	public:
		txIPCServer (
			const txAbsPeer& peer,
			int priority = IPCSERVER_THRD_PRIORITY,
			int time = txUDPServer::UDPSERVER_TIMEOUT_INTERVAL);

		~txIPCServer (void);

		int contact (const txAbsPeer&);

		int sendTo (txNetworkMsg*);
		int sendTo (const char*, unsigned long, const txAbsPeer&);

		int recvFrom (txNetworkMsgs&, int num_of_p = 0);

		int broadcast (const char*, unsigned long);

		int multicast (const char*, unsigned long, const txPeerList&);

		void close (const txAbsPeer&);
		void close (void);
};

#endif // __TXIPCSRVR_H__

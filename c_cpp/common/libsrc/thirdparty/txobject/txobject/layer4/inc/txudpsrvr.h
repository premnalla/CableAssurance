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

#if !defined ( __TXUDPSRVR_H__ )
#define __TXUDPSRVR_H__

#include "txtimer.h"
#include "txippeer.h"
#include "txsocket.h"
#include "txabssrvr.h"
#include "txhashmap.h"

class txUDPServer : public txAbsServer
{
	public:
		enum STATUS
		{
			LOST,
			ESTABLISHED
		};
 
	private:
		int _send_;
		int _timeout_;

		txSocket _wks_;
		txEvent _exit_;

		txHashMap _peers_of_interest_;

		char _hello_msg_[sizeof(int)*2];
		char _shutdown_msg_[sizeof(int)*2];

	private:
		void _processIncomingMsgs_ (txIpPeer&, const char*,
			unsigned long);
		void _processUserMsg_ (txIpPeer&, const char*,
			unsigned long);
		void _processHelloMsg_ (const txIpPeer&, const char*,
			unsigned long);
		void _processGoodbyeMsg_ (const txIpPeer&, const char*,
			unsigned long);

		void* _addPeer_ (const txIpPeer&);
		void _contactLost_ (const txIpPeer&);
		void _contactEstablished_ (const txIpPeer&);

		void _constructHelloMsg_ (void);
		void _constructShutdownMsg_ (void);

		static void _sendThreadFunc_ (void*);
		static void _receiveThreadFunc_ (void*);
		static void _threadedContactLost_ (void*);
		static void _threadedContactEstablished_ (void*);
		static txTimerEnum::RETURN_STATUS _timerCheckCallBack_ (void*);

		txUDPServer (const txUDPServer&);
		txUDPServer& operator= (const txUDPServer&);

	public:
		static const int UDPSERVER_THRD_PRIORITY;
		static const int UDPSERVER_SEND_INTERVAL;
		static const int UDPSERVER_TIMEOUT_INTERVAL;

		int changeState (const txAbsPeer&, STATUS);

	public:
		txUDPServer (
			const txIpPeer& peer,
			int priority = UDPSERVER_THRD_PRIORITY,
			int send_interval = UDPSERVER_SEND_INTERVAL,
			int timeout_interval = UDPSERVER_TIMEOUT_INTERVAL);

		~txUDPServer (void);

		int contact (const txAbsPeer&);

		int sendTo (txNetworkMsg*);
		int sendTo (const char*, unsigned long, const txAbsPeer&);

		int recvFrom (txNetworkMsgs&, int num_of_p = 0);

		int broadcast (const char*, unsigned long);

		int multicast (const char*, unsigned long, const txPeerList&);

		void close (const txAbsPeer&);
		void close (void);
};

#endif // __TXUDPSRVR_H__

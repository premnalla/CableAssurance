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

#if !defined ( __TXTCPSRVR_H__ )
#define __TXTCPSRVR_H__

#include "txevent.h"
#include "txippeer.h"
#include "txsocket.h"
#include "txabssrvr.h"
#include "txhashmap.h"
#include "txabspacker.h"

class txTCPServer : public txAbsServer
{
	private:
		txSocket _wks_;
		txIpPeer* _local_;
		txAbsPacker* _packer_;

		txHashMap _connects_;
		txHashMap _stale_cs_;
		unsigned long _contact_count_;
		txEvent _contact_pending_event_;

	private:
		void _registerSocket_ (const txAbsPeer&, txSocket&);
		int _addConnection_ (txAbsPeer&, txSocket*);
		int _removeConnection_ (const txAbsPeer&);
 
		txAbsPeer* _getHelloPacket_ (txSocket&);
		void _sendHelloPacket_ (txSocket&);
		void _sendSafe_ (txNetworkMsg*);

		void _enqueueMulticast_ (
			const char*,
			unsigned long,
			const txPeerList&);

		void _contactLost_ (txAbsPeer&);
		void _contactEstablished_ (txAbsPeer&);

		static void _sendThreadFunc_ (void*);
		static void _receiveThreadFunc_ (void*);
		static void _initializeContact_ (void*);
		static void _threadedContactLost_ (void*);
		static void _threadedContactEstablished_ (void*);

		static void _listenCallBack_ (void*, txSocket*);
		static void _contactLostCallBack_ (void*, txSocket*);

		txTCPServer (const txTCPServer&);
		txTCPServer& operator= (const txTCPServer&);

	public:
		static const int TCPSERVER_THRD_PRIORITY;

	public:
		txTCPServer (
			const txIpPeer& peer,
			txAbsPacker* packer = 0, // use internal packer
			int priority = TCPSERVER_THRD_PRIORITY);

		~txTCPServer (void);

		int contact (const txAbsPeer&);

		int sendTo (txNetworkMsg*);
		int sendTo (const char*, unsigned long, const txAbsPeer&);

		int recvFrom (txNetworkMsgs&, int num_of_p = 0);

		int broadcast (const char*, unsigned long);

		int multicast (const char*, unsigned long, const txPeerList&);

		void close (const txAbsPeer&);
		void close (void);
};

#endif // __TXTCPSRVR_H__

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

#if !defined ( __TXABSSRVR_H__ )
#define __TXABSSRVR_H__

#include "txeventq.h"
#include "txabspeer.h"
#include "txpeerlist.h"
#include "txntwrkmsg.h"

typedef void (*TX_COMM_FUNC) (void*, txAbsPeer*);
 
class txAbsServer
{
	public:
		enum STATUS
		{
			INITIAL,
			SHUTDOWN
		};

	private:
                txAbsServer (const txAbsServer&);
		txAbsServer& operator= (const txAbsServer&);

	protected:
		STATUS _status;	
		int _thread_pri;

		txEventQueue _in_packets;
		txEventQueue _out_packets;
		txPeerList _broadcast_list;

		void* _contact_lost_context;
		void* _contact_estab_context;

		TX_COMM_FUNC _contact_lost_func;
		TX_COMM_FUNC _contact_estab_func;

	protected:
		void _flushQueue (txEventQueue& queue);
		void _flushOutGoingPackets (const txAbsPeer& peer);

	public:
		txAbsServer (int priority = 0);
		virtual ~txAbsServer (void);

		virtual int contact (const txAbsPeer&) = 0;

		virtual int sendTo (txNetworkMsg*) = 0;
		virtual int sendTo (const char*, unsigned long,
			const txAbsPeer&) = 0;

		virtual int recvFrom (txNetworkMsgs&, int num_of_p = 0) = 0;

		virtual int broadcast (const char*, unsigned long) = 0;

		virtual int multicast (const char*, unsigned long,
			const txPeerList&) = 0;

		virtual void registerContactEstablished (const TX_COMM_FUNC,
			void*);
		virtual void registerContactLost (const TX_COMM_FUNC, void*);

		virtual void unregisterContactEstablished (void);
		virtual void unregisterContactLost (void);

		virtual void close (const txAbsPeer& peer) = 0;
		virtual void close (void) = 0;
};

#endif // __TXABSSRVR_H__

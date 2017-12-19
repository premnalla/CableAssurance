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
 
#if !defined ( __TXDOBJMSG_H__ )
#define __TXDOBJMSG_H__

#include "txobject.h"
#include "txhashmap.h"
#include "sys/txdobjintf.h"
#include "sys/txdobjutil.h"

class txDObjMgr;
class txDObjServer;

class txDObjMsg : public txDObjIntf
{
	TX_DECLARE_STREAM_TYPE(txDObjMsg)

	private:
		static txHashMap _g_method_table_;

	protected:
		txDObjMgr* _d_mgr;

	public:
		txDObjMsg (txDObjMgr* d_mgr = 0) :
			_d_mgr(d_mgr)
		{
		}

		~txDObjMsg (void)
		{
			_d_mgr = 0;
		}

		int sendTo (txOut&, const txAbsPeer&);

		void multicast (txOut&, const txPeerList&);

		void broadcast (txOut&);

		void recvFrom (txIn& in, const txAbsPeer&);

		int getPort (void) const;

		const char* getId (void) const;

		unsigned long getNetAddr (void) const;

		const txAbsPeer* getPeer (void) const;

		virtual const txDObjServer* getObjSrvr (void) const;

		virtual const txPeerList* interestedPeers (void) const;

		txObject* apply (const txString&, txIn&, const txAbsPeer&);

		void registerStaticOneWayMethod (
			const char*, TX_METHOD_PTR) const;
};

#endif // __TXDOBJMSG_H__

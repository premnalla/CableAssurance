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

#if !defined ( __TXDOBJINTF_H__ )
#define __TXDOBJINTF_H__

#include "sys/txmemobj.h"

class txAbsPeer;
class txPeerList;

class txDObjIntf : public txMemObj
{
	TX_DECLARE_STREAM_TYPE(txDObjIntf)

	public:
		txDObjIntf (void)
		{
		}

		~txDObjIntf (void)
		{
		}

		virtual int sendTo (txOut& out, const txAbsPeer& peer) = 0;

		virtual void multicast (txOut& out, const txPeerList& list) = 0;

		virtual void broadcast (txOut& out) = 0;

		virtual void recvFrom (txIn& in, const txAbsPeer& peer) = 0;
};

#endif // __TXDOBJINTF_H__


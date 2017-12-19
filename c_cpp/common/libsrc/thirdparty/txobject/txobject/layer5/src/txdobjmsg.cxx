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

#include "txstring.h"
#include "txdobjmgr.h"
#include "sys/txdobjmsg.h"
#include "sys/txdobjsrvr.h"

TX_DEFINE_PARENT_TYPE(txDObjMsg,TIDObj)

txHashMap txDObjMsg::_g_method_table_(25, TX_AUTODEL_ON);

void txDObjMsg::registerStaticOneWayMethod (const char* name,
	TX_METHOD_PTR method) const
{
	txString attr(name);

	if (!_g_method_table_.find(&attr))
	{
	 txDObjMethod* value = new txDObjMethod(method);

	 _g_method_table_.insertKeyAndValue(new txString(name), value);
	}
	else
	{
	 fprintf(stderr, "TXOBJECT[error] : dup. registerStaticOneWayMethod\n");
	 fflush(stderr); TX_CRASH;
	}
}
    
txObject* txDObjMsg::apply (const txString& f, txIn& in,
	const txAbsPeer& caller)
{
	txObject* result = 0;
	txDObjMethod* method;

	if (_d_mgr && (method = (txDObjMethod*) _g_method_table_.findValue(&f)))
	{
		result = method->apply(this, in, caller);
	}

	return result;
}

int txDObjMsg::sendTo (txOut& out, const txAbsPeer& peer)
{
	return _d_mgr ? ((txDObjServer*) _d_mgr->getObjSrvr())->sendTo(
		out, peer) : -1;
}

void txDObjMsg::multicast (txOut& out, const txPeerList& list)
{
	if (_d_mgr)
	{
		((txDObjServer*) _d_mgr->getObjSrvr())->multicast(out, list);
	}
}

void txDObjMsg::broadcast (txOut& out)
{
	txPeerList* peers = (txPeerList*) interestedPeers();

	if (_d_mgr && peers)
	{
		((txDObjServer*) _d_mgr->getObjSrvr())->multicast(out, *peers);
	}
}

void txDObjMsg::recvFrom (txIn& in, const txAbsPeer& peer)
{
	txObject* reply;
	txString method;

	in >> method;

	reply = apply(method, in, peer);

	delete reply; reply = 0;
}

int txDObjMsg::getPort (void) const
{
	return _d_mgr ? _d_mgr->getObjSrvr()->getPort() : 0;
}

const char* txDObjMsg::getId (void) const
{
	return _d_mgr ? _d_mgr->getObjSrvr()->getId() : 0;
}

unsigned long txDObjMsg::getNetAddr (void) const
{
	return _d_mgr ? _d_mgr->getObjSrvr()->getNetAddr() : 0;
}

const txAbsPeer* txDObjMsg::getPeer (void) const
{
	return _d_mgr ? _d_mgr->getObjSrvr()->getPeer() : 0;
}

const txDObjServer* txDObjMsg::getObjSrvr (void) const
{
	return _d_mgr ? _d_mgr->getObjSrvr() : 0;
}

const txPeerList* txDObjMsg::interestedPeers (void) const
{
	return 0;
}


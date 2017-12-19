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

#include "txtimer.h"
#include "txint16.h"
#include "txuint32.h"
#include "txstring.h"
#include "txippeer.h"
#include "txdobjmgr.h"
#include "txdobjglbls.h"
#include "sys/txstateobj.h"
#include "sys/txstatemgr.h"
#include "sys/txdobjsrvr.h"
 
struct txStateMgrTokens
{
	txStateMgr* self;
	txAbsPeer* peer;

	txStateMgrTokens (txStateMgr* s, const txAbsPeer* p) :
		self(s), peer(p->clone())
	{
	} 

	~txStateMgrTokens (void)
	{
		delete peer; peer = 0;
	}
};


int txStateMgr::_registerFlag_ = 0;

TX_DEFINE_PARENT_TYPE(txStateMgr,txDObjMsg)

txStateMgr::txStateMgr (txDObjMgr* d_mgr) :

	txDObjMsg(d_mgr),

	_contact_count_(0),
	_contact_table_(25, TX_AUTODEL_ON),
	_no_determinate_peers_(25, TX_AUTODEL_ON),
	_contact_pending_event_("STMGR : Pending Contact Event"),
	_contact_determinate_event_("Determinate Contact Event")
{
	_status_matrix_.setPrimaryKey(*getPeer());

	if (_registerFlag_ == 0)
	{
		registerStaticOneWayMethod("txs",
			(TX_METHOD_PTR) txStateMgr::_restoreVec_ );
		registerStaticOneWayMethod("txt",
			(TX_METHOD_PTR) txStateMgr::_updateEntry_ );

		_registerFlag_ = 1;
	}

	((txDObjServer*) _d_mgr->getObjSrvr())->registerContactEstablished(
		(TX_COMM_FUNC) _contactEstablished_, this);
	((txDObjServer*) _d_mgr->getObjSrvr())->registerContactLost(
		(TX_COMM_FUNC) _contactLost_, this);
}

txStateMgr::~txStateMgr (void)
{
	((txDObjServer*) _d_mgr->getObjSrvr())->unregisterContactEstablished();
	((txDObjServer*) _d_mgr->getObjSrvr())->unregisterContactLost();

	_static_peers_.clearAndDestroy();
	_active_peers_.clearAndDestroy();
	_all_known_peers_.clearAndDestroy();

	while (_contact_count_)
	{
		txThread::yield(_contact_pending_event_);
	}
}

void txStateMgr::_contact_ (const txAbsPeer& peer)
{
	// the txTCPServer also does this type of contact count : join them ?

	_contact_count_++;

	((txDObjServer*) _d_mgr->getObjSrvr())->contact(peer);

	_contact_count_--;

	_contact_pending_event_.trigger();
}

void txStateMgr::_disconnect_ (const txAbsPeer& peer)
{
	((txDObjServer*) _d_mgr->getObjSrvr())->close(peer);
}

void txStateMgr::_performNotifcations_ (void)
{
	txAbsPeer* peer;
	const txAbsPeer* self = getPeer();
	txHashMapIterator iter(_no_determinate_peers_);

	while (peer = (txAbsPeer*) iter.next())
	{
		int current_state = _status_matrix_.get(*self, *peer);

		const txInt16* old_state = (const txInt16*) iter.value();

		_processNotify_(self, peer, current_state, old_state->value());
	}

	_no_determinate_peers_.clearAndDestroy();
}

void txStateMgr::_sendVectorTo_ (const txAbsPeer& peer)
{
	txOut out;

	_streamVector_(*getPeer(), out);

	sendTo(out, peer);
}

void txStateMgr::_processNotify_ (const txAbsPeer* self,
	txAbsPeer* peer, int current_state, int old_state)
{
	if (old_state != current_state)
	{
		if ((txStateCtMtx::LOST == current_state) ||
			(txStateCtMtx::STATIC == old_state))
		{
			if (!_d_mgr->staticPeers() ||
				(staticPeers()->find(peer) &&
				staticPeers()->find(getPeer())))
			{
				txStateMgrTokens* toks =
					new txStateMgrTokens(this, peer);

				txThread::start(
					txStateMgr::_notifyLost_,
					toks, "txStateMgr::_notifyLost_",
					_d_mgr->baseThreadPriority() + 3);
			}
		}
		else if (txStateCtMtx::ESTABLISHED == current_state)
		{
			txStateMgrTokens* toks =
				new txStateMgrTokens(this, peer);

			txThread::start(
				txStateMgr::_notifyEstablish_,
				toks, "txStateMgr::_notifyEstablish_",
				_d_mgr->baseThreadPriority() + 3);
		}
	}
}

void txStateMgr::_addActivePeer_ (const txAbsPeer& peer)
{
	if (!_active_peers_.find(&peer))
	{
		_active_peers_.append(peer.clone());

		txSortPeerList(&_active_peers_, 0, _active_peers_.entries()-1);
	}
}

void txStateMgr::_addAllKnownPeer_ (const txAbsPeer& peer)
{
	if (!_all_known_peers_.find(&peer))
	{
		_all_known_peers_.append(peer.clone());

		txSortPeerList(&_all_known_peers_,
			0, _all_known_peers_.entries()-1);
	}

	if (_d_mgr->staticPeers() && !_static_peers_.find(&peer))
	{
		_static_peers_.append(peer.clone());

		txSortPeerList(&_static_peers_,
			0, _static_peers_.entries()-1);
	}
}

void txStateMgr::_restoreVector_ (const txAbsPeer& from_peer, txIn& in)
{
	txAbsPeer* peer;
	int entries = 0;
	txStateVector* self_vec;
	txStateVector* peer_vec;
	txPeerList peers_to_contact;
	int static_peers = _d_mgr->staticPeers();

	in >> peer_vec;

	self_vec = (txStateVector*) _status_matrix_.getRow(*getPeer());

	txHashMapIterator iter1((txHashMap&) *peer_vec->getTable());

	for (; peer = (txAbsPeer*) iter1.next(); entries++)
	{
		if (static_peers && !staticPeers()->find(peer))
		{
			_disconnect_(from_peer);
			delete peer_vec; peer_vec = 0;
			return;
		}

		if (!((txIpPeer*) peer)->accessible())
		{
			peer = (txAbsPeer*) iter1.remove();
			delete peer; peer = 0;

			txDObjError* error = new txDObjError(
				txDObjError::UNRESOLVED_PEER);

			_d_mgr->triggerErrorCallback(error);
		}
		else if (!self_vec->get(*peer))
		{
			peers_to_contact.append(peer);

			_addAllKnownPeer_(*peer);
		}
	}
   
	_status_matrix_.setRow(from_peer, *peer_vec);

	_goToState_(from_peer, txStateCtMtx::ESTABLISHED);
	_sendUpdate_(from_peer, txStateCtMtx::ESTABLISHED);

	txPeerListIterator iter2(peers_to_contact);

	while (peer = (txAbsPeer*) iter2.next())
	{
		_goToState_(*peer, txStateCtMtx::INITIAL);
		_sendUpdate_(*peer, txStateCtMtx::INITIAL);
	}

	iter2.reset(peers_to_contact);

	while (peer = (txAbsPeer*) iter2.next())
	{
		_contact_(*peer);
	}

	delete peer_vec; peer_vec = 0;
}

void txStateMgr::_contactEstablished_ (void* obj, const txAbsPeer* peer)
{
	txStateMgr* self = (txStateMgr*) obj;

	if (self->_d_mgr->staticPeers() && !self->staticPeers()->find(peer))
	{
		self->_disconnect_(*peer);
		return;
	}
	
	self->_addActivePeer_(*peer);
	self->_addAllKnownPeer_(*peer);

	self->_goToState_(*peer, txStateCtMtx::INITIAL);
	self->_sendVectorTo_(*peer);
}

void txStateMgr::_contactLost_ (void* obj, const txAbsPeer* peer)
{
	txStateMgr* self = (txStateMgr*) obj;

	self->_active_peers_.removeAndDestroy(peer);

	txSortPeerList(&self->_active_peers_,
		0, self->_active_peers_.entries()-1);

	if (self->_d_mgr->staticPeers())
	{
		self->_no_determinate_peers_.removeAndDestroy(peer);
 
		self->_no_determinate_peers_.insertKeyAndValue(
			peer->clone(), new txInt16(txStateCtMtx::STATIC));

		self->checkForDeterminateContact();
	}
	else
	{
		self->_goToState_(*peer, txStateCtMtx::LOST);
		self->_sendUpdate_(*peer, txStateCtMtx::LOST);
	}
}

void txStateMgr::_notifyLost_ (void *obj)
{
	txStateMgrTokens* toks = (txStateMgrTokens*) obj;

	toks->self->_d_mgr->_contactLost_(*toks->peer);

	delete toks; toks = 0;
}

void txStateMgr::_notifyEstablish_ (void *obj)
{
	txStateMgrTokens* toks = (txStateMgrTokens*) obj;

	toks->self->_d_mgr->_contactEstablished_(*toks->peer);

	delete toks; toks = 0;
}

void txStateMgr::_goToState_ (const txAbsPeer& peer, 
	txStateCtMtx::STATE new_state)
{
	txStateCtMtx::STATE old_state;

	old_state = _status_matrix_.get(*getPeer(), peer);

	if ( ((new_state == txStateCtMtx::INITIAL) && 
		(old_state == txStateCtMtx::ESTABLISHED)) ||
		(new_state == txStateCtMtx::UNKNOWN) )
	{
		if (!_d_mgr->staticPeers())
		{
			// invalid state transition
			return;
		}
	}

	if (old_state != new_state)
	{
		_no_determinate_peers_.removeAndDestroy(&peer);
 
		_no_determinate_peers_.insertKeyAndValue(peer.clone(),
			new txInt16(old_state));

		_status_matrix_.set(*getPeer(), peer, new_state);

		checkForDeterminateContact();
	}
}

void txStateMgr::_streamVector_ (const txAbsPeer& row, txOut& out)
{
	txStateVector* vec = (txStateVector*) _status_matrix_.getRow(row);

	if (_d_mgr->staticPeers())
	{
		txAbsPeer* peer;
		txHashMapIterator iter((txHashMap&) *vec->getTable());

		while (peer = (txAbsPeer*) iter.next())
		{
			if (!getPeer()->equals(peer) &&
				!staticPeers()->find(peer))
			{
				iter.remove();
				_status_matrix_.del(*peer);
				delete peer; peer = 0;
			}
		}
	}

	out << oidObject();
	out.put("txs", 3, TX_ASCII_STRING);
	out << vec;
}

void txStateMgr::_sendUpdate_ (const txAbsPeer& peer,
	txStateCtMtx::STATE state)
{
	txOut out;

	out << oidObject();
	out.put("txt", 3, TX_ASCII_STRING);
	out << peer;
	out << (signed short int) state;

	multicast(out, _active_peers_);
}

txObject* txStateMgr::_restoreVec_ (txStateMgr* self, txIn& in,
	const txAbsPeer& peer)
{
	self->_restoreVector_(peer, in);

	return 0;
}

txObject* txStateMgr::_updateEntry_ (txStateMgr* self, txIn& in,
	const txAbsPeer& row)
{
	txAbsPeer* col;
	signed short int s;
	txStateCtMtx::STATE l_state;
	txStateCtMtx::STATE r_state;

 	in >> col;

	if (self->_d_mgr->staticPeers() && !self->staticPeers()->find(col))
	{
		delete col; col = 0;
		self->_disconnect_(row);
		return 0;
	}

	in >> s; r_state = (txStateCtMtx::STATE) s;

	l_state = self->_status_matrix_.get(*self->getPeer(), *col);

	if (l_state == txStateCtMtx::UNKNOWN)
	{
		if (r_state != txStateCtMtx::LOST)
		{
			self->_status_matrix_.set(row, *col, r_state);
			self->_goToState_(*col, txStateCtMtx::INITIAL);
			self->_sendUpdate_(*col, txStateCtMtx::INITIAL);
		}

		self->_contact_(*col);
	}
	else
	{
		self->_status_matrix_.set(row, *col, r_state);
	}

	self->checkForDeterminateContact();

	delete col; col = 0;
	return 0;
}

int txStateMgr::determinate (void)
{
	return _status_matrix_.determinate();
}

void txStateMgr::initialize (const txPeerList& peers)
{
	txAbsPeer* peer;
	txPeerList peers_to_contact;
	const txAbsPeer* self = getPeer();
	txPeerListIterator iter((txPeerList&) peers);

	_addAllKnownPeer_(*self);
	_status_matrix_.set(*self, *self, txStateCtMtx::ESTABLISHED);

	while (peer = (txAbsPeer*) iter.next())
	{
		_addAllKnownPeer_(*peer);

		if (!getPeer()->equals(peer))
		{
			_status_matrix_.set(
				*self, *peer, txStateCtMtx::INITIAL);

			peers_to_contact.append(peer);
		}
	}

	iter.reset(peers_to_contact);

	while (peer = (txAbsPeer*) iter.next())
	{
		_contact_(*peer);
	}

	waitForDeterminateContact();
}

void txStateMgr::addPeer (const txAbsPeer& peer)
{
	_goToState_(peer, txStateCtMtx::INITIAL);
}

void txStateMgr::removePeer (const txAbsPeer& peer)
{
	if (_status_matrix_.getRow(peer))
	{
		_all_known_peers_.removeAndDestroy(&peer);

		if (_status_matrix_.get(*getPeer(),peer)!=txStateCtMtx::LOST)
		{
			_status_matrix_.del(peer);
			_d_mgr->_contactLost_((txAbsPeer&) peer);
		}
		else
		{
			_status_matrix_.del(peer);
		}
	}
}

void txStateMgr::checkForDeterminateContact (void)
{
	if (_status_matrix_.determinate() || _d_mgr->staticPeers())
	{
		_contact_determinate_event_.trigger();
	}

	if (_no_determinate_peers_.entries())
	{
		_performNotifcations_();
	}
}

void txStateMgr::waitForDeterminateContact(void)
{
	while (!_status_matrix_.determinate())
	{
		txThread::yield(_contact_determinate_event_);
	}
}


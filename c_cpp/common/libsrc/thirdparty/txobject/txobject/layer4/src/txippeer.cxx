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

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "txippeer.h"
#include "txstring.h"
#include "txostring.h"
#include "txhashtable.h"

static txHashTable THE_PEER_ADDR_TABLE;
static int THE_PEER_ADDR_TABLE_HAS_BEEN_DELETED = 0;

static void removePeer (txPeerId* peer);
static txPeerId* findPeer (unsigned long netaddr, int port);
static txPeerId* createPeer (unsigned long netaddr, int port);

class txPeerId : public txObject
{
	private:
		int _port_;
		char _id_[15];
		txOctetString _key_;
		unsigned long _netaddr_;
		unsigned long* _ref_count_;

		txAbsPeer::TYPE _tcp_srvr_;
		txAbsPeer::TYPE _udp_srvr_;

	public:
		txPeerId (unsigned long n, int p, int force_another_construct) :
			_tcp_srvr_(txAbsPeer::NONE),
			_udp_srvr_(txAbsPeer::NONE),
			_ref_count_(0),
			_netaddr_(n),
			_port_(p)
		{
		}

		txPeerId (unsigned long netaddr, int port) :
			_ref_count_(new unsigned long(1)),
			_tcp_srvr_(txAbsPeer::NONE),
			_udp_srvr_(txAbsPeer::NONE),
			_key_("#$%^!@!!", 8),
			_netaddr_(netaddr),
			_port_(port)
		{
			sprintf(_id_, "%x@%d", netaddr, port);
		}

		~txPeerId (void)
		{
			delete _ref_count_; _ref_count_ = 0;
		}

		void activate (txAbsPeer::TYPE t)
		{
			switch (t)
			{
				case txAbsPeer::TCP:
				{
					_tcp_srvr_ = txAbsPeer::TCP;
				}
				case txAbsPeer::UDP:
				{
					_udp_srvr_ = txAbsPeer::UDP;
				}
				default:
				{
				}
			}
		}

		void deactivate (txAbsPeer::TYPE t)
		{
			switch (t)
			{
				case txAbsPeer::TCP:
				{
					_tcp_srvr_ = txAbsPeer::NONE;
				}
				case txAbsPeer::UDP:
				{
					_udp_srvr_ = txAbsPeer::NONE;
				}
				default:
				{
				}
			}
		}

		txAbsPeer::TYPE isActive (txAbsPeer::TYPE t) const
		{
			switch (t)
			{
				case txAbsPeer::TCP:
				{
					return _tcp_srvr_;
				}
				case txAbsPeer::UDP:
				{
					return _udp_srvr_;
				}
				default:
				{
					return txAbsPeer::NONE;
				}
			} 
		}

		const char* getId (void) const
		{
			return _id_;
		}

		unsigned long getNetAddr (void)
		{
			return _netaddr_;
		}

		int getPort (void) const
		{
			return _port_;
		}

		int refCount (void) const
		{
			return *_ref_count_;
		}

		int incref (void)
		{
			return (*_ref_count_)++;
		}

		int decref (void)
		{
			return (*_ref_count_)--;
		}

		unsigned hash (void) const
		{
			return _netaddr_ + _port_;
		}

		int equals (const txObject* obj) const
		{
			int retval = 0;
			txPeerId* identifer = (txPeerId*) obj;

			if (_port_ == identifer->_port_)
			{
				if (_netaddr_ == identifer->_netaddr_)
				{
					retval = 1;
				}
			}

			return retval;
		}

		void setEncryption (const txOctetString& key)
		{
			_key_.data(key.data(), key.length());
		}

		const txOctetString* getEncryption (void) const
		{
			return &_key_;
		}
};

static txPeerId* createPeer (unsigned long na, int p)
{
	return (txPeerId*) THE_PEER_ADDR_TABLE.insert(new txPeerId(na, p));
}

static txPeerId* findPeer (unsigned long netaddr, int port)
{
	txPeerId* id;
	txPeerId tmp(netaddr, port, 1);

	if (id = (txPeerId*) THE_PEER_ADDR_TABLE.find(&tmp))
	{
		id->incref(); return id;
	}

	return createPeer(netaddr, port);
}

static void removePeer (txPeerId* peer)
{
	if (!THE_PEER_ADDR_TABLE_HAS_BEEN_DELETED && !peer->decref())
	{
		THE_PEER_ADDR_TABLE.removeAndDestroy(peer);
	}
}

static void cleanUpPeerMemory (void)
{
	THE_PEER_ADDR_TABLE_HAS_BEEN_DELETED = 1;
	THE_PEER_ADDR_TABLE.clearAndDestroy();
}

TX_STATIC_DEALLOC_NOTIFY(cleanUpPeerMemory,(),cleanUpPeerMemory)


TX_DEFINE_STREAM_TYPE(txIpPeer,txAbsPeer)

txIpPeer::txIpPeer (void) :
	_type_(txAbsPeer::NONE),
	_self_(0)
{
}

txIpPeer::txIpPeer (txPeerId* self, txAbsPeer::TYPE type) :
	_type_(type),
	_self_(self)
{
	_self_->incref();
}

txIpPeer::txIpPeer (txAbsPeer::TYPE type, txSocket::TYPE band) :
	_type_(type),
	_self_(findPeer(txSocket::generateLocalNetAddr(band), 0))
{
}

txIpPeer::txIpPeer (const char* peer, int port, txAbsPeer::TYPE type) :
	_type_(type),
	_self_(findPeer(txSocket::generateNetAddr(peer), port))
{
}

txIpPeer::txIpPeer (unsigned long netaddr, int port, txAbsPeer::TYPE type) :
	_type_(type),
	_self_(findPeer(netaddr, port))
{
}

txIpPeer::~txIpPeer (void)
{
	removePeer(_self_);
}

void txIpPeer::setType (txAbsPeer::TYPE type)
{
	_type_ = type;
}

txAbsPeer::TYPE txIpPeer::getType (void) const
{
	return _type_;
}

void txIpPeer::setPort (int port)
{
	if (_self_->getPort() == 0)
	{
		txOctetString copy(*_self_->getEncryption());

		unsigned long netaddr = _self_->getNetAddr();

		removePeer(_self_);

		_self_ = findPeer(netaddr, port);

		_self_->setEncryption(copy);
	}
	else
	{
		fprintf(stderr, "error : peer already bound\n");
		fflush(stderr);
	}
}

int txIpPeer::getPort (void) const
{
	return _self_->getPort();
}

int txIpPeer::refCount (void) const
{
	return _self_->refCount();
}

int txIpPeer::accessible (void) const
{
	return (_self_->getPort() != 0) ? 1 : 0;
}

void txIpPeer::activate (void)
{
	_self_->activate(_type_);
}

void txIpPeer::deactivate (void)
{
	_self_->deactivate(_type_);
}

int txIpPeer::isActive (void) const
{
	return _self_->isActive(_type_) ? 1 : 0;
}

const char* txIpPeer::getId (void) const
{
	return _self_->getId();
}

unsigned long txIpPeer::getNetAddr (void) const
{
	return _self_->getNetAddr();
}

txAbsPeer* txIpPeer::clone (void) const
{
	return new txIpPeer(_self_, (TYPE) _type_);
}

unsigned txIpPeer::hash (void) const
{
	return _self_->hash() + _type_;
}

int txIpPeer::equals (const txObject* obj) const
{
	txIpPeer* peer = (txIpPeer*) obj;

	if (obj->isClass(txIpPeer::Type))
	{
		return _self_->equals(peer->_self_);
	}
	else
	{
		return 0;
	}
}

void txIpPeer::storeInners (txOut& out) const
{
	out << (signed short int) _type_;
	out << (signed long int) _self_->getPort();
	out << (unsigned long int) _self_->getNetAddr();
}

void txIpPeer::restoreInners (txIn& in)
{
	signed short int type; in >> type;
	signed long int port; in >> port;
	unsigned long int netaddr; in >> netaddr;

	_type_ = (TYPE) type; _self_ = findPeer(netaddr, port);
}

void txIpPeer::setEncryption (const txOctetString& key)
{
	_self_->setEncryption(key);
}

const txOctetString* txIpPeer::getEncryption (void) const
{
	return _self_->getEncryption();
}


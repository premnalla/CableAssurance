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

#if !defined ( __TXIPPEER_H__ )
#define __TXIPPEER_H__

#include "txsocket.h"
#include "txabspeer.h"

class txPeerId;
class txOctetString;

class txIpPeer : public txAbsPeer
{
	TX_DECLARE_STREAM_TYPE(txIpPeer)

	private:
		TYPE _type_;
		txPeerId* _self_;

		txIpPeer (void);
		txIpPeer (txPeerId* self, TYPE type);
		txIpPeer (const txIpPeer& obj);
		txIpPeer& operator= (const txIpPeer& obj);

	public:
		txIpPeer (const char* ip, int port, TYPE type);
		txIpPeer (unsigned long netaddr, int port, TYPE type);
		txIpPeer (TYPE type, txSocket::TYPE band=txSocket::OUT_OF_BAND);

		~txIpPeer (void);

		void setType (TYPE);
		TYPE getType (void) const;

		void setPort (int);
		int getPort (void) const;

		int refCount (void) const;
		int accessible (void) const;

		void activate (void);
		void deactivate (void);
		int isActive (void) const;

		const char* getId (void) const;
		unsigned long getNetAddr (void) const;

		txAbsPeer* clone (void) const;

		unsigned hash (void) const;
		int equals (const txObject*) const;

		void storeInners (txOut&) const;
		void restoreInners (txIn&);

		void setEncryption (const txOctetString&);
		const txOctetString* getEncryption (void) const;
};

TX_DECLARE_STREAM_TYPE_OPER(txIpPeer)

#endif // __TXIPPEER_H__

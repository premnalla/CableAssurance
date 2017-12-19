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

#if !defined ( __TXABSPACKER_H__ )
#define __TXABSPACKER_H__

#include "txobject.h"
#include "txntwrkmsg.h"

class txAbsPacker : public txObject
{
	private:
		txAbsPacker (const txAbsPacker&);
		txAbsPacker& operator= (const txAbsPacker&);

	protected:
		txNetworkMsg _residual;

	public:
		txAbsPacker (void);
		virtual ~txAbsPacker (void);

		virtual void packetize (txNetworkMsg&);

		virtual int depacketize (txNetworkMsg&, txNetworkMsgs&);

		virtual txAbsPacker* clone (void);

		virtual void flush (void);
};

class txRawPacker : public txAbsPacker
{
	private:
		txRawPacker (const txRawPacker&);
		txRawPacker& operator = (const txRawPacker&);

	public:
		txRawPacker (void)
		{
		}

		~txRawPacker (void)
		{
		}

		void packetize (txNetworkMsg& packet)
		{
		}

		int depacketize (txNetworkMsg& pckt, txNetworkMsgs& packets)
		{
			txNetworkMsg* p;

			p = new txNetworkMsg(pckt.data(), pckt.length());

			packets.append(p);

			return 1;
		}

		txAbsPacker* clone (void)
		{
			return new txRawPacker();
		}

		void flush (void)
		{
		}
};

#endif // __TXABSPACKER_H__

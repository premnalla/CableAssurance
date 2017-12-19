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

#if !defined ( __TXPEERLIST_H__ )
#define __TXPEERLIST_H__

#include "txglist.h"
#include "txabspeer.h"

TX_DECLARE_GENERIC_LIST(txAbsPeer,txGListOftxAbsPeer)

class txPeerList : public txGListOftxAbsPeer
{
	private:
		TX_AUTODEL_FLAG _autodel_;

	private:
		static int _compare_ (const txAbsPeer* v1, const txAbsPeer* v2)
		{
			return (v1->equals(v2));
		}

	public:
		txPeerList (TX_AUTODEL_FLAG autodel = TX_AUTODEL_OFF) :
			_autodel_(autodel)
		{
		}

		~txPeerList (void)
		{
			if (_autodel_)
			{
				clearAndDestroy();
			}
		}

		const txAbsPeer* find (const txAbsPeer* x) const
		{
			return txPeerList::findReference(_compare_, x);
		}

		txAbsPeer* remove (const txAbsPeer* x)
		{
			return txPeerList::removeReference(_compare_, x);
		}

		void removeAndDestroy (const txAbsPeer* x)
		{
			txAbsPeer* peer;

			peer = txPeerList::remove(x);

			delete peer; peer = 0;
		}
};

class txPeerListIterator : public txGListOftxAbsPeerIterator
{
	public:
		txPeerListIterator (txPeerList& list) :
			txGListOftxAbsPeerIterator((txGListOftxAbsPeer&) list)
		{
		}
};

void txSortPeerList (txPeerList* peers, int left, int right);

#endif // __TXPEERLIST_H__

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

#include <string.h>

#include "txabspeer.h"
#include "txpeerlist.h"

static int compare (const txAbsPeer* peer1, const txAbsPeer* peer2)
{
	return strcmp(peer1->getId(), peer2->getId());
}

static void txSwapPeer (txPeerList* list, int i, int j)
{
	const txAbsPeer* tmp1;
	const txAbsPeer* tmp2;

	if (i != j)
	{
		tmp1 = list->at(i);
		tmp2 = list->at(j);

		list->removeReference(tmp1);
		list->removeReference(tmp2);

		if (i < j)
		{
			list->insertAt(i, (txAbsPeer*) tmp2);
			list->insertAt(j, (txAbsPeer*) tmp1);
		}
		else
		{
			list->insertAt(j, (txAbsPeer*) tmp1);
			list->insertAt(i, (txAbsPeer*) tmp2);
		}
	}
}

void txSortPeerList (txPeerList* list, int left, int right)
{
	int i = 0;
	int last = 0;

	const txAbsPeer* tmpi = 0;
	const txAbsPeer* tmpleft = 0;

	if (left >= right)
	{
		return;
	}

	txSwapPeer(list, left, (left + right)/2);

	last = left;
	for (i = left+1; i <= right; i++)
	{
		tmpi = list->at(i);
		tmpleft = list->at(left);

		if (compare(tmpleft, tmpi) > 0)
		{
			txSwapPeer(list, ++last, i);
		}
	}

	txSwapPeer(list, left, last);
	txSortPeerList(list, left, last-1);
	txSortPeerList(list, last+1, right);
}


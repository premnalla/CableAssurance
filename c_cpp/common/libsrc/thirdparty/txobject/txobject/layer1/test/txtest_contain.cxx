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
#include <iostream.h>

#include "txin.h"
#include "txout.h"

#include "txstring.h"

#include "txlist.h"
#include "txbtree.h"
#include "txhashmap.h"
#include "txhashtable.h"

int main (void)
{
	txList list;
	txBTree tree;
	txHashMap dict;
	txHashTable table;
	txString* str = 0;

	for (int i = 0; i < 10; i++)
	{
		char buf[100];
		sprintf(buf, "string info : %d", i);
		str = new txString(buf);

		// INSERT

		list.insert(str);
		tree.insert(str);
		dict.insert(str);
		table.insert(str); // this container will delete the string

		txString lookup(buf);

		// FIND

		if (!list.find(&lookup))
		{
			cout << "failed to find : " << str->data() << endl;
		}

		if (!tree.find(&lookup))
		{
			cout << "failed to find : " << str->data() << endl;
		}

		if (!dict.find(&lookup))
		{
			cout << "failed to find : " << str->data() << endl;
		}

		if (!table.find(&lookup))
		{
			cout << "failed to find : " << str->data() << endl;
		}

#if 0
		// REMOVE

		if (!list.remove(&lookup))
		{
			cout << "failed to remove : " << str->data() << endl;
		}

		if (!tree.remove(&lookup))
		{
			cout << "failed to remove : " << str->data() << endl;
		}

		if (!dict.remove(&lookup))
		{
			cout << "failed to remove : " << str->data() << endl;
		}

		// REMOVE AND DESTROY

		table.removeAndDestroy(&lookup);
#endif
	}

	// TRY OUT ITERATORS

	txListIterator listIter(list);
	txBTreeIterator treeIter(tree);
	txHashMapIterator dictIter(dict);
	txHashTableIterator tableIter(table);

	while (str = (txString*) listIter.next())
	{
		cout << "LIST : " << str->data() << endl;
	}

	while (str = (txString*) treeIter.next())
	{
		cout << "TREE : " << str->data() << endl;
	}

	while (str = (txString*) dictIter.next())
	{
		cout << "DICT : " << str->data() << endl;
	}

	while (str = (txString*) tableIter.next())
	{
		cout << "TABLE : " << str->data() << endl;
	}

	// free up the strings

	table.clearAndDestroy();

	return 1;
}


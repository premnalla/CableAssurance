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
#include "txhashmap.h"

TX_DEFINE_PARENT_TYPE(txHashMap,txHashTable)

txAssociate::txAssociate (txObject* key, txObject* value) :
	_key_(key),
	_value_(value)
{
}

txAssociate::~txAssociate (void)
{
}


const txObject* txHashMap::find (const txObject* key) const
{
	txAssociate* x;

	x = (txAssociate*) txHashTable::find(key);

	return (x ? x->key() : 0);
}

const txObject* txHashMap::findKeyAndValue (const txObject* key,
	txObject*& value) const
{
	txObject* k;
	txAssociate* x;

	if (x = (txAssociate*) txHashTable::find(key))
	{
		k = x->key();
		value = x->value();
	}
	else
	{
		k = 0;
		value = 0;
	}

	return k;
}

const txObject* txHashMap::findValue (const txObject* key) const
{
	txAssociate* x;

	x = (txAssociate*) txHashTable::find(key);

	return (x ? x->value() : 0);
}

txObject* txHashMap::findValue (const txObject* key, txObject* value) const
{
	txAssociate* x;

	x = (txAssociate*) txHashTable::find(key);

	return (x ? x->value(value) : 0);
}

const txObject* txHashMap::insertKeyAndValue (txObject* key, txObject* value)
{
	if (!_table) resize(64);

	txObject* x = 0;
	int index = _index(key);

	if (!_table[index] || (_table[index] == ((txObject*) &_fill)))
	{
		#if !defined OSTORE_SUPPORT
			x = _insert(new txAssociate(key, value), index);
		#else
			if (TX_IS_OBJECT_PERSISTENT(this))
			{
				x = _insert(new (TX_GET_OBJECT_CLUSTER(this),
					txAssociate::get_os_typespec())
						txAssociate(key, value),
							index);
			}
			else
			{
				x = _insert(new txAssociate(key, value),
					index);
			}
		#endif
	}

	return x;
}

txObject* txHashMap::remove (const txObject* key)
{
	txAssociate* x;
	txObject* k = 0;

	if (x = (txAssociate*) txHashTable::remove(key))
	{
		k = x->key();
		delete x; x = 0;
	}

	return k;
}

txObject* txHashMap::removeKeyAndValue (const txObject* key, txObject*& v)
{
	txAssociate* x;
	txObject* k = 0;

	if (x = (txAssociate*) txHashTable::remove(key))
	{
		k = x->key();
		v = x->value();
		delete x; x = 0;
	}
	else
	{
		v = 0;
	}

	return k;
}

void txHashMap::removeAndDestroy (const txObject* key)
{
	txAssociate* x;

	if (x = (txAssociate*) txHashTable::remove(key))
	{
		delete x->key();
		delete x->value();
		delete x; x = 0;
	}
}

void txHashMap::clear (void)
{
	txHashTable::clearAndDestroy();
}

void txHashMap::clearAndDestroy (void)
{
	if (!_table) return;

	register int i, n = _size;
	register txObject** table = _table;

	_table = 0; _size = _entries = _fill = 0;

	for (i = 0; i < n; i++)
	{
		txAssociate* p = (txAssociate*) table[i];

		if (p != 0 && (p != ((txObject*) &_fill)))
		{
			delete p->value();
			delete p->key();
			delete p;
		}
	}

	delete [] table; table = 0;
}

void txHashMap::stream (txOut& out) const
{
	txObject* node;
	unsigned int hdr = out.length();

	out.putNull();

	out << (signed short int) TX_GET_AUTODEL_FLAG(_flags_);

	txHashMapIterator iter((txHashMap&) *this);

	while (node = (txObject*) iter.next())
	{
		out.put(node);
		node = (txObject*) iter.value();
		out.put(node);
	}

	out.writeHeader(hdr, out.length() - (hdr + 4), TX_MAP);
}

void txHashMap::destream (txIn& in)
{
	const char* end = in.cursor() + in.objectLength();

	signed short int flag; in >> flag;

	TX_SET_AUTODEL_FLAG(_flags_, (TX_AUTODEL_FLAG) flag);

	txObject* k;
	txObject* v;

	while (in.cursor() < end)
	{
		in >> k;
		in >> v;

		insertKeyAndValue(k, v);
	}
}

txObject* txHashMapIterator::remove (void)
{
	txAssociate* x;
	txObject* k = 0;

	if (x = (txAssociate*) txHashTableIterator::remove())
	{
		k = x->key();
		delete x; x = 0;
	}

	return k;
}

const txObject* txHashMapIterator::next (void)
{
	txAssociate* x;

	x = (txAssociate*) txHashTableIterator::next();

	return (x ? x->key() : 0);
}

const txObject* txHashMapIterator::key (void) const
{
	txAssociate* x;

	x = (txAssociate*) txHashTableIterator::key();

	return (x ? x->key() : 0);
}

const txObject* txHashMapIterator::value (void) const
{
	txAssociate* x;

	x = (txAssociate*) txHashTableIterator::key();

	return (x ? x->value() : 0);
}

void txHashMapIterator::reset (txHashMap& hm)
{
	txHashTableIterator::reset(hm);
}

void txHashMapIterator::reset (void)
{
	txHashTableIterator::reset();
}


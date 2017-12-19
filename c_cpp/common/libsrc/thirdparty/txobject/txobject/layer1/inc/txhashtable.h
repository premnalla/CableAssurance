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

#if !defined ( __TXHASHTABLE_H__ )
#define __TXHASHTABLE_H__

#include "txobject.h"

class txHashTable : public txObjectSequence
{
	TX_DEFINE_TYPE(txHashTable)

	private:
		unsigned long _flags_;

	protected:
		int _fill; // _fill's address is also used as the dummy value
		int _poly;
		int _size;
		int _entries;
		txObject** _table;

	protected:
		int _index (const txObject* x, int find = 1) const;

		txObject* _insert (txObject* x, int index = 0);

	public:
		txHashTable (
			int n = 64,
			TX_AUTODEL_FLAG autodel = TX_AUTODEL_OFF,
			TX_COMPARE_FLAG compare = TX_COMPARE_V2K) :

			_fill(0), _poly(0), _size(0), _entries(0), _table(0)
		{
			TX_SET_AUTODEL_FLAG(_flags_, autodel);
			TX_SET_COMPARE_FLAG(_flags_, compare);

			if (n) resize(n);
		}

		~txHashTable (void)
		{
			if (TX_GET_AUTODEL_FLAG(_flags_))
			{
				clearAndDestroy();
			}
			else
			{
				clear();
			}
		}

		virtual const txObject* find (const txObject* x) const
		{
			return _table ? _table[_index(x)] : 0;
		}

		virtual const txObject* insert (txObject* x);

		virtual txObject* remove (const txObject* x);

		virtual void removeAndDestroy (const txObject* x);

		int resize (int n = 0);

		virtual void clear (void);

		virtual void clearAndDestroy (void);

		int entries (void) const
		{
			return _entries;
		}

		int buckets (void) const
		{
			return _size;
		}

		void stream (txOut& out) const;

		void destream (txIn& in);

	friend class txHashTableIterator;
};

TX_DECLARE_STREAM_TYPE_OPER(txHashTable)

class txHashTableIterator : public txObjectIterator
{
	TX_PERSIST

	private:
		int _i_;
		txHashTable* _t_;

	public:
		txHashTableIterator (txHashTable& h) :
			_t_(&h), _i_(-1)
		{
		}

		~txHashTableIterator (void)
		{
		}

		virtual txObject* remove (void);

		virtual const txObject* next (void);

		virtual const txObject* key (void) const;

		void reset (txHashTable& h)
		{
			_t_ = &h;

			reset();
		}

		virtual void reset (void)
		{
			_i_ = -1;
		}
};

#endif // __TXHASHTABLE_H__

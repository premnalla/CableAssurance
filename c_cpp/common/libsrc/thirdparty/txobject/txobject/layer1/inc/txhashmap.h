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
 
#if !defined ( __TXHASHMAP_H__ )
#define __TXHASHMAP_H__
 
#include "txobject.h"
#include "txhashtable.h"

class txAssociate : public txObject
{
	TX_PERSIST

	private:
		txObject* _key_;
		txObject* _value_;

	public:
		txAssociate (txObject* key, txObject* value);

		~txAssociate (void);

		txObject* key (txObject* key)
		{
			register txObject* t = _key_;
			_key_ = key;
			return t;
		}

		txObject* key (void) const
		{
			return _key_;
		}

		txObject* value (txObject* value)
		{
			register txObject* t = _value_;
			_value_ = value;
			return t;
		}

		txObject* value (void) const
		{
			return _value_;
		}

		unsigned hash (void) const
		{
			return _key_->hash();
		}

		int equals (const txObject* obj) const
		{
			return obj->equals(_key_);
		}
};

class txHashMap : public txHashTable
{
	TX_DEFINE_TYPE(txHashMap)

	private:
		unsigned long _flags_;

	public:
		txHashMap (
			int n = 64,
			TX_AUTODEL_FLAG autodel = TX_AUTODEL_OFF,
			TX_COMPARE_FLAG compare = TX_COMPARE_V2K) :

			/*
                        ** NOTE: Auto-Delete DOES NOT WORK for derived class
                        */
			txHashTable(n, TX_AUTODEL_OFF, compare)
		{
			TX_SET_AUTODEL_FLAG(_flags_, autodel);

			/*
                        ** NOTE: Compare IS CURRENTLY DEFINED BY parent class
			TX_SET_COMPARE_FLAG(_flags_, compare);
                        */
		}

		~txHashMap (void)
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

		virtual const txObject* insert (txObject* k)
		{
			return insertKeyAndValue(k, 0);
		}

		virtual const txObject* find (const txObject* key) const;

		const txObject* findKeyAndValue (const txObject* key,
			txObject*& value) const;

		const txObject* findValue (const txObject* k) const;

		txObject* findValue (const txObject* k, txObject* v) const;

		const txObject* insertKeyAndValue (txObject* k, txObject* v);

		virtual txObject* remove (const txObject* k);

		txObject* removeKeyAndValue (const txObject* k, txObject*& v);

		virtual void removeAndDestroy (const txObject* k);

		virtual void clear (void);

		virtual void clearAndDestroy (void);

		void stream (txOut& out) const;

		void destream (txIn& in);

	friend class txHashMapIterator;
};    

TX_DECLARE_STREAM_TYPE_OPER(txHashMap)

class txHashMapIterator : private txHashTableIterator
{
	TX_PERSIST

	public:
		txHashMapIterator (txHashMap& hm) :
			txHashTableIterator(hm)
		{
		}

		~txHashMapIterator (void)
		{
		}

		virtual txObject* remove (void);

		virtual const txObject* next (void);

		const txObject* value (void) const;

		const txObject* key (void) const;

		void reset (txHashMap& hm);

		virtual void reset (void);
};

#endif // __TXHASHMAP_H__

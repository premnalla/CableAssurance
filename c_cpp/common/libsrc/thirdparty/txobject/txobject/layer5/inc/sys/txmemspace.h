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

#if !defined ( __TXMEMSPACE_H__ )
#define __TXMEMSPACE_H__

#include "txobject.h"
#include "txhashmap.h"
#include "sys/txmemobj.h"

class txMemSpace;

class txMemIterator : public txObject
{
	private:
		int _Internal_;
		txMemObj* _current_object_;

		txMemSpace* _space_;
		txMemSpace* _current_space_;

	private:
		int _internal_ (int flag = 0);
		void _removeObject_ (txMemObj* object);

	public:
		txMemIterator (const txMemSpace& space);
		~txMemIterator (void);

		txMemObj* next (void);

		void reset (const txMemSpace&);
		void reset (void);

	friend class txMemSpace;
};

class txMemSpace : public txMemObj
{
	TX_DEFINE_TYPE(txMemSpace)

	private:
		char* _prefix_;
		char _separator_;
		int _prefix_len_;
		txMemObj* _head_;
		txMemObj* _tail_;
		txHashMap _data_;
		txHashMap _iters_;
		txMemSpace* _parent_;
		unsigned long _instance_;


	private:
		int _registerObject_ (txMemObj*);
		txMemObj* _findObject_ (const char*);
		txMemSpace* _findObjectSpace_ (const char*);
		txMemSpace* _createNewMemSpace_ (const char*, int);

		txMemObj* _getNext_ (txMemIterator*);

		void _removeChild_ (txMemObj*);
		void _registerIterator_ (txMemIterator*);
		void _unregisterIterator_ (txMemIterator*);
		void _removeObjectFromIterators_ (txMemObj*);

	public:
		txMemSpace (const char* oid = 0, char sp = '/');
		~txMemSpace (void);

		const txMemSpace* parent (void) { return _parent_; }
		const txMemSpace* allocateSpace (const char* prefix);
		const txMemSpace* accessSpace (const char* prefix);
		const txMemObj* accessObject (const char* oid);

		int allocateObject (txMemObj* object);
		int registerObject (txMemObj* object);

		void deallocateObject (txMemObj* object);
		void unregisterObject (txMemObj* object);

		char getSpaceSeparator (void) const
		{
			return _separator_;
		}

		int entries (void)
		{
			return _data_.entries();
		}

	friend class txMemIterator;
};

#endif // __TXMEMSPACE_H__


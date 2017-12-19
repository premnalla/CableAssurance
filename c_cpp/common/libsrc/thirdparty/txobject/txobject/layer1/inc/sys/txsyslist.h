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
 
#if !defined ( __TXSYSLIST_H__ )
#define __TXSYSLIST_H__

#include "txutil.h"

typedef int (*TX_TEST_NODE) (const void*, const void*);

class txSysNode
{
	TX_PERSIST

	public:
		void* _info;
		txSysNode* _next;

	public:
		txSysNode (void) :
			_info(0),
			_next(0)
		{
		}

		txSysNode (void* x) :
			_info(x),
			_next(0)
		{
		}

		txSysNode* next (void) const
		{
			return _next;
		}

		txSysNode* clear (void)
		{
			txSysNode* node = _next;
			_next = 0;
			return node;
		}
};

class txSysList
{
	TX_PERSIST

	private:
		int _entries_;
		txSysNode _head_;
		txSysNode _tail_;
		txSysNode* _last_;

	private:
		void _init_ (void);

		const txSysNode* _at_ (int i) const;

		void _insertAfterNode_ (txSysNode* l, txSysNode* x);

		txSysNode* _findLeft_ (const txSysNode* x) const;

		txSysNode* _removeRight_ (txSysNode* x);

		txSysNode* _removeFirst_ (void)
		{
			return (!_entries() ? 0 : _removeRight_(&_head_));
		}

		static void* _stripNode_ (txSysNode* node);

	public:
		txSysList (void)
		{
			_init_();
		}

		~txSysList (void)
		{
			_clear();
		}

		const void* _at (int n) const
		{
			return _at_(n)->_info;
		}

		const void* _first (void) const
		{	
			return (!_entries() ? 0 : _head_._next->_info);
		}

		const void* _last (void) const
		{
			return (!_entries() ? 0 : _last_->_info);
		}

		const void* _append (void* x);

		const void* _prepend (void* x);

		int _index (const void* x) const;

		const void* _insertAt (int i, void* x);

		const void* _insertAfter (int i, void* x);

		const void* _findReference (const void* x) const;

		const void* _findReference (TX_TEST_NODE, const void* x) const;

		void* _removeReference (const void* x);

		void* _removeReference (TX_TEST_NODE, const void* x);

		void* _get (void)
		{
			return _entries() ? _stripNode_(_removeFirst_()) : 0;
		}

		int _entries (void) const
		{
			return _entries_;
		}

		void _clear (void);

	friend class txSysListIterator;
};

class txSysListIterator
{
	TX_PERSIST

	private:
		txSysList* _l_;
		txSysNode* _c_;

	private:
		void _forward_ (void)
		{
			_c_ = _c_->_next;
		}

		int _active_ (void) const
		{
			return _c_ != &_l_->_head_ && _c_ != &_l_->_tail_;
		}

	public:
		txSysListIterator (void) :
			_l_(0), _c_(0)
		{
		}

		txSysListIterator (txSysList &s) :
			_l_(&s), _c_(&s._head_)
		{
		}

		const void* _key (void) const
		{
			return (_active_() ? _c_->_info : 0);
		}

		const void* _next (void)
		{
			_forward_();

			return (_c_ == &_l_->_tail_ ? 0 : _c_->_info);
		}

		void* _remove (void);

		void _reset (void)
		{
			_c_ = &_l_->_head_;
		}

		void _reset (txSysList &l)
		{
			_l_ = &l;
			_reset();
		}
};

#endif // __TXSYSLIST_H__

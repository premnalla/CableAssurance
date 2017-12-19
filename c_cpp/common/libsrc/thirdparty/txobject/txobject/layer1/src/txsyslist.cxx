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

#include "sys/txsyslist.h"

void txSysList::_init_ (void)
{
	_head_._next = _tail_._next = &_tail_;
	_last_ = &_head_;
	_entries_ = 0;
}

const txSysNode* txSysList::_at_ (int i) const
{
	if (i >= _entries())
	{
		return 0;
	}

	register txSysNode* node = _head_._next;

	while (i--)
	{
		node = node->_next;
	}

	return node;
}

void txSysList::_insertAfterNode_ (txSysNode* l, txSysNode* x)
{
	x->_next = l->_next;
	l->_next = x;

	if (l == _last_)
	{
		_last_ = x;
	}

	_entries_++;
}

txSysNode* txSysList::_findLeft_ (const txSysNode* x) const
{
	register txSysNode* node = (txSysNode*) &_head_;

	while (node->_next != &this->_tail_)
	{
		if (node->_next == x)
		{
			return node;
		}

		node = node->_next;
	}

	return 0;
}

txSysNode* txSysList::_removeRight_ (txSysNode* x)
{
	txSysNode* node = x->_next;

	x->_next = node->_next;

	if (node == _last_)
	{
		_last_ = x;
	}

	_entries_--;

	return node;
}

void* txSysList::_stripNode_ (txSysNode* node)
{
	if (!node) return 0;

	void* p = node->_info;
	delete node;
	return p;
}

const void* txSysList::_append (void* x)
{
	_insertAfterNode_(_last_, new txSysNode(x));

	return x;
}

const void* txSysList::_prepend (void* x)
{
	_insertAfterNode_(&_head_, new txSysNode(x));

	return x;
}

int txSysList::_index (const void* x) const
{
	txSysNode* node = _head_._next;

	for (int i = 0; node != &_tail_; i++)
	{
		if (node->_info == x)
		{
			return i;
		}

		node = node->next();
	}

	return -1;
}

const void* txSysList::_insertAt (int i, void* x)
{
	if (i > _entries())
	{
		return 0;
	}

	txSysNode* prev = i ? (txSysNode*) _at_(i-1) : &_head_;

	_insertAfterNode_(prev, new txSysNode(x));

	return x;
}

const void* txSysList::_insertAfter (int i, void* x)
{
	if (i > _entries())
	{
		return 0;
	}

	_insertAfterNode_((txSysNode*)_at_(i), new txSysNode(x));

	return x;
}

const void* txSysList::_findReference (const void* x) const
{
	register txSysNode* node = _head_._next;

	while (node != &_tail_)
	{
		if (node->_info == x)
		{
			return node->_info;
		}

		node = node->next();
	}

	return 0;
}

const void* txSysList::_findReference (TX_TEST_NODE func, const void* x) const
{
	register txSysNode* node = _head_._next;

	while (node != &_tail_)
	{
		if (func(node->_info, x) == 1)
		{
			return node->_info;
		}

		node = node->next();
	}

	return 0;
}

void* txSysList::_removeReference (const void* x)
{
	register txSysNode* prev = &_head_;

	while (prev != _last_)
	{
		if (prev->next()->_info == x)
		{
			return _stripNode_(_removeRight_(prev));
		}

		prev = prev->next();
	}

	return 0;
}

void* txSysList::_removeReference (TX_TEST_NODE func, const void* x)
{
	register txSysNode* prev = &_head_;

	while (prev != _last_)
	{
		if (func(prev->next()->_info, x) == 1)
		{
			return _stripNode_(_removeRight_(prev));
		}

		prev = prev->next();
	}

	return 0;
}

void txSysList::_clear (void)
{
	register txSysNode* n;
	register txSysNode* node = _head_._next;

	while (node != &_tail_)
	{
		n = node->next();
		delete node;
		node = n;
	}

	_init_();
}

void* txSysListIterator::_remove (void)
{
	if (!_active_()) return 0;

	_c_ = _l_->_findLeft_(_c_);

	return txSysList::_stripNode_(_l_->_removeRight_(_c_));
}


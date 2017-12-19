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
#include <string.h>

#include "txstring.h"
#include "sys/txmemspace.h"

class txMemLookUp : public txObject
{
	private:
		const char* _data_;
		unsigned long _hash_;
		unsigned long _length_;

	public:
		txMemLookUp (void) :
			_data_(0),
			_hash_(0),
			_length_(0)
		{
		}

		const char* data (void) const
		{
			return _data_;
		}

		unsigned long length (void) const
		{
			return _length_;
		}

		inline unsigned hash (void) const;
		inline int equals (const txObject* obj) const;
		inline void set (const char* data, unsigned long length);
};

inline void txMemLookUp::set (const char* data, unsigned long length)
{
	_data_ = data;
	_length_ = length;

	_hash_ = txHash(_data_, _length_);
}

unsigned txMemLookUp::hash (void) const
{
	return _hash_;
}

int txMemLookUp::equals (const txObject* rhs) const
{
	txString* o = (txString*) rhs;

	if (length() == o->length())
	{
		return memcmp(data(), o->data(), length()) == 0;
	}

	return 0;
}

txMemIterator::txMemIterator (const txMemSpace& space) :
	_space_(&(txMemSpace&) space),
	_current_object_(0),
	_current_space_(0),
	_Internal_(0)
{
	_current_object_ = _space_->_head_;
	_space_->_registerIterator_(this);
}

txMemIterator::~txMemIterator (void)
{
	if (_space_)
	{
		_space_->_unregisterIterator_(this);
	}
}

int txMemIterator::_internal_ (int flag)
{
	if (flag)
	{
		_Internal_ = flag;
	}

	return _Internal_;
}

void txMemIterator::_removeObject_ (txMemObj* object)
{
	if (object == _space_)
	{
		_space_ = 0;
	}

	if (object == _current_space_)
	{
		_current_space_ = 0;
	}

	if (object == _current_object_)
	{
		_current_object_ = _current_object_->_front_;
	}
}

txMemObj* txMemIterator::next (void)
{
	txMemObj* obj = 0;

	if (!_current_space_)
	{
		obj = _current_object_;

		if (!obj)
		{
			return obj;
		}

		_current_object_ = _current_object_->_front_;

		if (obj->isClass(txMemSpace::Type))
		{
			_current_space_ = (txMemSpace*) obj;

			if (!(obj = _current_space_->_getNext_(this)))
			{
				_current_space_ = 0;
				obj = next();
			}
		}
	}
	else if (!(obj = _current_space_->_getNext_(this)))
	{
		_current_space_ = 0;
		obj = next();
	}

	return obj;
}

void txMemIterator::reset (const txMemSpace& space)
{
	if (_space_)
	{
		_space_->_unregisterIterator_(this);
	}

	_space_ = &(txMemSpace&) space;

	_current_object_ = _space_->_head_;

	_space_->_registerIterator_(this);
}

void txMemIterator::reset (void)
{
	if (_space_)
	{
		_current_object_ = _space_->_head_;
	}
}

TX_DEFINE_PARENT_TYPE(txMemSpace,txMemObj)

txMemSpace::txMemSpace (const char* oid, char separator) :
	_separator_(separator),
	_prefix_len_(0),
	_instance_(0),
	_prefix_(0),
	_parent_(0),
	_data_(4),
	_head_(0),
	_tail_(0)
{
	if (oid)
	{
		txMemObj::oid(oid);
		txMemObj::term(oid);
	}
}

txMemSpace::~txMemSpace (void)
{
	txMemIterator* iter;

	if (_parent_)
	{
		_parent_->_removeChild_(this);
	}

	while (_head_)
	{
		deallocateObject(_head_);
	}

	if (_iters_.entries())
	{
		txHashMapIterator search(_iters_);

		while (iter = (txMemIterator*) search.next())
		{
			iter->_removeObject_(this);
			iter = (txMemIterator*) search.value();
			iter->_removeObject_(this);

			search.remove();

			if (iter->_internal_())
			{
				delete iter; iter = 0;
			}
		}
	}

	delete _prefix_; _prefix_ = 0;
}

int txMemSpace::_registerObject_ (txMemObj* object)
{
	int flag = 0;

	if (_data_.insertKeyAndValue((txObject*) object->termObject(), object))
	{
		if (!_head_)
		{
			_head_ = object;
			_tail_ = _head_;
		}
		else
		{
			_tail_->_front_ = object;
			object->_back_ = _tail_;
			object->_front_ = 0;

			_tail_ = object;
		}

		if (object->isClass(txMemSpace::Type))
		{
			((txMemSpace*) object)->_parent_ = this;
		}

		flag = 1;
	}

	return flag;
}

txMemObj* txMemSpace::_findObject_ (const char* oid)
{
	int exit = 0;
	txMemObj* obj;
	static txMemLookUp key;
	const char* to = oid;
	const char* from = oid;
	txMemSpace* space = (txMemSpace*) this;

	do
	{
		for (; (*to != _separator_) && (*to != 0); to++);

		if (!*to) exit = 1;

		key.set(from, to - from);

		if (obj = (txMemObj*) space->_data_.findValue(&key))
		{
			if (obj->isClass(txMemSpace::Type))
			{
				space = (txMemSpace*) obj;
			}
		}

		from = (++to);
	}
	while (!exit);

	if (obj && obj->isClass(txMemSpace::Type))
	{
		return (txMemObj*)((txMemSpace*)obj)->_data_.findValue(&key);
	}
	else
	{
		return obj;
	}
}

txMemSpace* txMemSpace::_findObjectSpace_ (const char* oid)
{
	int exit = 0;
	txMemObj* obj;
	static txMemLookUp key;
	const char* to = oid;
	const char* from = oid;
	txMemSpace* space = (txMemSpace*) this;

	do
	{
		for (;(*to != _separator_) && (*to != 0); to++);

		if (!*to) exit = 1;

		key.set(from, to - from);

		if (obj = (txMemObj*) space->_data_.findValue(&key))
		{
			if (obj->isClass(txMemSpace::Type))
			{
				space = (txMemSpace*) obj;
			}
		}

		from = (++to);
	}
	while (!exit);

	return space;
}

txMemSpace* txMemSpace::_createNewMemSpace_ (const char* oid, int length)
{
	txMemSpace* space = new txMemSpace(0, _separator_);

	if (_parent_)
	{
		space->_prefix_len_ = _prefix_len_ + length + 1;
		space->_prefix_ = new char[space->_prefix_len_ + 1];
		memcpy(space->_prefix_, _prefix_, _prefix_len_);
		memcpy(space->_prefix_ + _prefix_len_, &_separator_, 1);
		memcpy(space->_prefix_ + _prefix_len_ + 1, oid, length);
		space->_prefix_[space->_prefix_len_] = '\0';
	}
	else
	{
		space->_prefix_len_ = length;
		space->_prefix_ = new char[space->_prefix_len_ + 1];
		memcpy(space->_prefix_, oid, space->_prefix_len_);
		space->_prefix_[space->_prefix_len_] = '\0';
	}

	space->_oid_ = new txString(space->_prefix_,
		(unsigned long) space->_prefix_len_);
	space->_term_ = new txString(oid,
		(unsigned long) length);

	_registerObject_(space);

	return space;
}

void txMemSpace::_removeChild_ (txMemObj* object)
{
	_data_.remove(object->termObject());

	if (object->_back_)
	{
		object->_back_->_front_ = object->_front_;
	}

	if (object->_front_)
	{
		object->_front_->_back_ = object->_back_;
	}

	if (object == _tail_)
	{
		_tail_ = object->_back_;
	}

	if (object == _head_)
	{
		_head_ = object->_front_;
	}

	object->_front_ = 0;
	object->_back_ = 0;
}

txMemObj* txMemSpace::_getNext_ (txMemIterator* current_iter)
{
	txMemObj* object;
	txMemIterator* iter;

	if (!(iter = (txMemIterator*) _iters_.findValue(current_iter)))
	{
		iter = new txMemIterator(*this);
		iter->_internal_(1);

		_iters_.insertKeyAndValue(current_iter, iter);
	}

	if (!(object = iter->next()))
	{
		_iters_.remove(current_iter);
		delete iter; iter = 0;
	}

	return object;
}

void txMemSpace::_registerIterator_ (txMemIterator* iter)
{
	_iters_.insertKeyAndValue(iter, iter);
}

void txMemSpace::_unregisterIterator_ (txMemIterator* iter)
{
	_iters_.remove(iter);
}

void txMemSpace::_removeObjectFromIterators_ (txMemObj* object)
{
	txMemIterator* iter;

	if (_iters_.entries())
	{
		txHashMapIterator search(_iters_);

		while (iter = (txMemIterator*) search.next())
		{
			iter->_removeObject_(object);
			iter = (txMemIterator*) search.value();
			iter->_removeObject_(object);
		}
	}
}

const txMemObj* txMemSpace::accessObject (const char* oid)
{
	return _findObject_(oid);
}

const txMemSpace* txMemSpace::accessSpace (const char* prefix)
{
	return _findObjectSpace_(prefix);
}

const txMemSpace* txMemSpace::allocateSpace (const char* prefix)
{
	int exit = 0;
	txMemSpace* obj;
	static txMemLookUp key;
	txMemSpace* space = this;
	const char* to = prefix;
	const char* from = prefix;

	do
	{
		for (;(*to != _separator_) && (*to != 0); to++);

		if (!*to) exit = 1;

		key.set(from, to - from);

		if (obj = (txMemSpace*) space->_data_.findValue(&key))
		{
			space = obj;
		}
		else
		{
			space = space->_createNewMemSpace_(from, to - from);
		}

		from = (++to);
	}
	while (!exit);

	return space;
}

int txMemSpace::allocateObject (txMemObj* object)
{
	char id[12];
	char oid[256];
	static txMemLookUp key;
	unsigned long count = _instance_;

	do
	{
		count++;

		sprintf(id, "%lu", count);

		key.set(id, strlen(id));
	}
	while (_data_.find(&key));

	_instance_ = count;

	if (_prefix_)
	{
		memcpy(oid, _prefix_, _prefix_len_);
		memcpy(oid + _prefix_len_, &_separator_, 1);
		memcpy(oid + _prefix_len_ + 1, key.data(), key.length());
		oid[_prefix_len_ + 1 + key.length()] = '\0';

		object->oid(oid);
	}
	else
	{
		object->oid(key.data());
	}

	object->term(id);

	return _registerObject_(object);
}

int txMemSpace::registerObject (txMemObj* object)
{
	int flag = 0;
	txMemSpace* space;

	if (space = _findObjectSpace_(object->oid()))
	{
		flag = space->_registerObject_(object);
	}

	return flag;
}

void txMemSpace::deallocateObject (txMemObj* object)
{
	unregisterObject(object);

	if (object->refcount() < 1)
	{
		delete object; object = 0;
	}
}

void txMemSpace::unregisterObject (txMemObj* object)
{
	txMemSpace* space = 0;

	if (object->isClass(txMemSpace::Type))
	{
		space = ((txMemSpace*) object)->_parent_;

		((txMemSpace*) object)->_parent_ = 0;
	}
	else
	{
		space = _findObjectSpace_(object->oid());
	}

	if (space)
	{
		space->_removeObjectFromIterators_(object);
		space->_removeChild_(object);
	}
}


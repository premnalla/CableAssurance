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

TX_DEFINE_PARENT_TYPE(txString,txObject)

txString::txString (void) :
	_length_(0),
	_hash_(0),
	_data_(0)
{
}

txString::txString (const char* data) :
	_hash_(0)
{
	_resize_(data, strlen(data));
}

txString::txString (const char* data, unsigned long length) :
	_hash_(0)
{
	_resize_(data, length);
}

void txString::_resize_ (const char* data, unsigned long length)
{
	_hash_ = 0;
	_length_ = length;

	#if !defined OSTORE_SUPPORT
		_data_ = new char[_length_ + 1];
	#else
		if (TX_IS_OBJECT_PERSISTENT(this))
		{
			_data_ = new(TX_GET_OBJECT_CLUSTER(this),
				os_typespec::get_char(), _length_ + 1)
					char[_length_ + 1];
		}
		else
		{
			_data_ = new char[_length_ + 1];
		}
	#endif

	memcpy(_data_, data, _length_);
	_data_[_length_] = '\0';
}

unsigned txString::hash (void) const
{
	if (_hash_)
	{
		return _hash_;
	}

	if (!length())
	{
		return 0;
	}

	((txString*) this)->_hash_ = txHash(data(), length());

	return _hash_;
}

int txString::equals (const txObject* rhs) const
{
	if (memcmp(className(), rhs->className(), 7) == 0)
	{
		txString* o = (txString*) rhs;

		if (_length_ == o->_length_)
		{
			return memcmp(data(), o->data(), _length_) == 0;
		}
	}

	return 0;
}

void txString::stream (txOut& out) const
{
	if (_length_)
	{
		out.put(_data_, _length_, TX_ASCII_STRING);
	}
	else if (_data_)
	{
		out.writeUInt(TX_EMPTY_STRING);
	}
	else
	{
		out.writeHeader(out.length(), 0, TX_ASCII_STRING);
	}
}

void txString::destream (txIn& in)
{
	delete [] _data_; _data_ = 0;

	if (in.objectLength() || in._typeMatchCurrentHeader_(TX_EMPTY_STRING))
	{
		_resize_(in.cursor(), in.objectLength());
	}
	else
	{
		_hash_ = 0; _length_ = 0;
	}
}

int txString::compare (const txObject* obj) const
{
	int v = memcmp(_data_, ((txString*) obj)->_data_, _length_);

	if (v == 0)
	{
		if (_length_ == ((txString*) obj)->_length_)
		{
			return 0;
		}
		else if (_length_ > ((txString*) obj)->_length_)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	else if (v > 0)
	{
		return 1;
	}
	else
	{
		return -1;
	}
}


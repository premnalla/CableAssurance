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
#include "txostring.h"

TX_DEFINE_PARENT_TYPE(txOctetString,txObject)

txOctetString::txOctetString (void) :
	_length_(0),
	_hash_(0),
	_data_(0)
{
}

txOctetString::txOctetString (const char* data, unsigned long length) :
	_hash_(0)
{
	_resize_(data, length);
}

void txOctetString::_resize_ (const char* data, unsigned long length)
{
	_hash_ = 0;
	_length_ = length;

	#if !defined OSTORE_SUPPORT
		_data_ = new char[_length_];
	#else
		if (TX_IS_OBJECT_PERSISTENT(this))
		{
			_data_ = new(TX_GET_OBJECT_CLUSTER(this),
				os_typespec::get_char(), _length_)
					char[_length_];
		}
		else
		{
			_data_ = new char[_length_];
		}
	#endif

	memcpy(_data_, data, _length_);
}

unsigned txOctetString::hash (void) const
{
	if (_hash_)
	{
		return _hash_;
	}

	if (!length())
	{
		return 0;
	}

	((txOctetString*) this)->_hash_ = txHash(data(), length());

	return _hash_;
}

int txOctetString::equals (const txObject* rhs) const
{
	if (rhs->isClass(txOctetString::Type))
	{
		txOctetString* o = (txOctetString*) rhs;

		if (_length_ == o->_length_)
		{
			return memcmp(data(), o->data(), _length_) == 0;
		}
	}

	return 0;
}

void txOctetString::stream (txOut& out) const
{
	if (_length_)
	{
		out.put(_data_, _length_, TX_OCTET_STRING);
	}
	else
	{
		out.writeHeader(out.length(), 0, TX_OCTET_STRING);
	}
}

void txOctetString::destream (txIn& in)
{
	delete [] _data_; _data_ = 0;

	if (in.objectLength())
	{
		_resize_(in.cursor(), in.objectLength());
	}
	else
	{
		_hash_ = 0; _length_ = 0;
	}
}

int txOctetString::compare (const txObject* obj) const
{
	int v = memcmp(_data_, ((txOctetString*) obj)->_data_, _length_);

	if (v == 0)
	{
		if (_length_ == ((txOctetString*) obj)->_length_)
		{
			return 0;
		}
		else if (_length_ > ((txOctetString*) obj)->_length_)
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


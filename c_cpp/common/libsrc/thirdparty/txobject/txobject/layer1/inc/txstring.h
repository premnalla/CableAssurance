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

#if !defined ( __TXSTRING_H__ )
#define __TXSTRING_H__

#include <string.h>

#include "txobject.h"

class txString : public txObject
{
	TX_DEFINE_TYPE(txString)

	private:
		char* _data_;
		unsigned _hash_;
		unsigned long _length_;

	private:
		void _resize_ (const char* data, unsigned long length);

	public:
		txString (void);

		txString (
			const char*);

		txString (
			const char*,
			unsigned long);

		txString (const txString& obj)
		{
			_resize_(obj._data_, obj._length_);
		}

		txString& operator= (txString& obj)
		{
			if (this != &obj)
			{
				_resize_(obj._data_, obj._length_);
			}

			return *this;
		}

		~txString (void)
		{
			delete [] _data_; _data_ = 0;
		}

		unsigned long length (void) const
		{
			return _length_;
		}

		const char* data (void) const
		{
			return _data_;
		}

		void data (const char* data)
		{
			delete [] _data_; _data_ = 0;

			_resize_(data, strlen(data));
		}

		void data (const char* data, unsigned long length)
		{
			delete [] _data_; _data_ = 0;

			_resize_(data, length);
		}

		unsigned hash (void) const;

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txString)

#endif // __TXSTRING_H__

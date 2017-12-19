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

#if !defined ( __TXUINT64_H__ )
#define __TXUINT64_H__

#include "txobject.h"

class txUInt64 : public txObject
{
	TX_DEFINE_TYPE(txUInt64)

	private:
		unsigned long _hi_;
		unsigned long _lo_;

	public:
		txUInt64 (void);
		txUInt64 (unsigned long hi, unsigned long lo);

		txUInt64 (const txUInt64& obj);

		txUInt64& operator= (txUInt64& obj);

		~txUInt64 (void);

		unsigned long hiValue (void) const
		{
			return _hi_;
		}

		unsigned long loValue (void) const
		{
			return _lo_;
		}

		void hiValue (unsigned long hi)
		{
			_hi_ = hi;
		}

		void loValue (unsigned long lo)
		{
			_lo_ = lo;
		}

		unsigned hash (void) const
		{
			return (unsigned) _hi_ + _lo_;
		}

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txUInt64)

#endif // __TXUINT64_H__

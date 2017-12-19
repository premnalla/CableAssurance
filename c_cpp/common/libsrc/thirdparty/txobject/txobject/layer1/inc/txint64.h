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

#if !defined ( __TXINT64_H__ )
#define __TXINT64_H__

#include "txobject.h"

class txInt64 : public txObject
{
	TX_DEFINE_TYPE(txInt64)

	private:
		signed long _hi_;
		signed long _lo_;

	public:
		txInt64 (void);
		txInt64 (signed long hi, signed long lo);

		txInt64 (const txInt64& obj);

		txInt64& operator= (txInt64& obj);

		~txInt64 (void);

		signed long hiValue (void) const
		{
			return _hi_;
		}

		signed long loValue (void) const
		{
			return _lo_;
		}

		void hiValue (signed long hi)
		{
			_hi_ = hi;
		}

		void loValue (signed long lo)
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

TX_DECLARE_STREAM_TYPE_OPER(txInt64)

#endif // __TXINT64_H__

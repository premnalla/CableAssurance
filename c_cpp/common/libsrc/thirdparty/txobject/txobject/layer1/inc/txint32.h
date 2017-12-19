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

#if !defined ( __TXINT32_H__ )
#define __TXINT32_H__

#include "txobject.h"

class txInt32 : public txObject
{
	TX_DEFINE_TYPE(txInt32)

	private:
		signed long _int_;

	public:
		txInt32 (void);
		txInt32 (signed long v);

		txInt32 (const txInt32& obj);

		txInt32& operator= (txInt32& obj);

		~txInt32 (void);

		signed long value (void) const
		{
			return _int_;
		}

		void value (signed long v)
		{
			_int_ = v;
		}

		unsigned hash (void) const
		{
			return (unsigned) _int_;
		}

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txInt32)

#endif // __TXINT32_H__

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

#if !defined ( __TXUINT8_H__ )
#define __TXUINT8_H__

#include "txobject.h"

class txUInt8 : public txObject
{
	TX_DEFINE_TYPE(txUInt8)

	private:
		unsigned char _int_;

	public:
		txUInt8 (void);
		txUInt8 (unsigned char v);

		txUInt8 (const txUInt8& obj);

		txUInt8& operator= (txUInt8& obj);

		~txUInt8 (void);

		unsigned char value (void) const
		{
			return _int_;
		}

		void value (unsigned char v)
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

TX_DECLARE_STREAM_TYPE_OPER(txUInt8)

#endif // __TXUINT8_H__

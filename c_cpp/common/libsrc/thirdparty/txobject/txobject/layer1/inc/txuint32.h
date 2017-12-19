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

#if !defined ( __TXUINT32_H__ )
#define __TXUINT32_H__

#include "txobject.h"

class txUInt32 : public txObject
{
	TX_DEFINE_TYPE(txUInt32)

	private:
		unsigned long _int_;

	public:
		txUInt32 (void);
		txUInt32 (unsigned long v);

		txUInt32 (const txUInt32& obj);

		txUInt32& operator= (txUInt32& obj);

		~txUInt32 (void);

		unsigned long value (void) const
		{
			return _int_;
		}

		void value (unsigned long v)
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

TX_DECLARE_STREAM_TYPE_OPER(txUInt32)

#endif // __TXUINT32_H__

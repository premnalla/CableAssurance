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

#if !defined ( __TXINT16_H__ )
#define __TXINT16_H__

#include "txobject.h"

class txInt16 : public txObject
{
	TX_DEFINE_TYPE(txInt16)

	private:
		signed short _int_;

	public:
		txInt16 (void);
		txInt16 (signed short v);

		txInt16 (const txInt16& obj);

		txInt16& operator= (txInt16& obj);

		~txInt16 (void);

		signed short value (void) const
		{
			return _int_;
		}

		void value (signed short v)
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

TX_DECLARE_STREAM_TYPE_OPER(txInt16)

#endif // __TXINT16_H__

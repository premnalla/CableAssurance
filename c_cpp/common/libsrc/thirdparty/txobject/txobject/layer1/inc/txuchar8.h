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

#if !defined ( __TXUCHAR8_H__ )
#define __TXUCHAR8_H__

#include "txobject.h"

class txUChar8 : public txObject
{
	TX_DEFINE_TYPE(txUChar8)

	private:
		unsigned char _char_;

	public:
		txUChar8 (void);
		txUChar8 (unsigned char v);

		txUChar8 (const txUChar8& obj);

		txUChar8& operator= (txUChar8& obj);

		~txUChar8 (void);

		unsigned char value (void) const
		{
			return _char_;
		}

		void value (unsigned char v)
		{
			_char_ = v;
		}

		unsigned hash (void) const
		{
			return (unsigned) _char_;
		}

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txUChar8)

#endif // __TXUCHAR8_H__

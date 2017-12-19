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

#if !defined ( __TXCHAR8_H__ )
#define __TXCHAR8_H__

#include "txobject.h"

class txChar8 : public txObject
{
	TX_DEFINE_TYPE(txChar8)

	private:
		signed char _char_;

	public:
		txChar8 (void);
		txChar8 (signed char v);

		txChar8 (const txChar8& obj);

		txChar8& operator= (txChar8& obj);

		~txChar8 (void);

		signed char value (void) const
		{
			return _char_;
		}

		void value (signed char v)
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

TX_DECLARE_STREAM_TYPE_OPER(txChar8)

#endif // __TXCHAR8_H__

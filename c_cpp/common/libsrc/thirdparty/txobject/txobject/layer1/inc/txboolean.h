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

#if !defined ( __TXBOOLEAN_H__ )
#define __TXBOOLEAN_H__

#include "txobject.h"

class txBoolean : public txObject
{
	TX_DEFINE_TYPE(txBoolean)

	private:
		txObjectType::Boolean _bool_;

	public:
		txBoolean (void);
		txBoolean (txObjectType::Boolean v);

		txBoolean (const txBoolean& obj);

		txBoolean& operator= (txBoolean& obj);

		~txBoolean (void);

		txObjectType::Boolean value (void) const
		{
			return _bool_;
		}

		void value (txObjectType::Boolean v)
		{
			_bool_ = v;
		}

		unsigned hash (void) const
		{
			return (unsigned) _bool_;
		}

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txBoolean)

#endif // __TXBOOLEAN_H__

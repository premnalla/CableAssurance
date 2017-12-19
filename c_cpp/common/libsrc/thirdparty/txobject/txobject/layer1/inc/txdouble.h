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

#if !defined ( __TXDOUBLE_H__ )
#define __TXDOUBLE_H__

#include "txobject.h"

class txDouble : public txObject
{
	TX_DEFINE_TYPE(txDouble)

	private:
		double _double_;

	public:
		txDouble (void);
		txDouble (double v);

		txDouble (const txDouble& obj);

		txDouble& operator= (txDouble& obj);

		~txDouble (void);

		double value (void) const
		{
			return _double_;
		}

		void value (double v)
		{
			_double_ = v;
		}

		unsigned hash (void) const
		{
			return (unsigned) _double_;
		}

		int equals (const txObject* obj) const;

		int compare (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txDouble)

#endif // __TXDOUBLE_H__

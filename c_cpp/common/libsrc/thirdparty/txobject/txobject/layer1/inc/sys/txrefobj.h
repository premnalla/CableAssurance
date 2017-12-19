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

#if !defined ( __TXREFOBJ_H__ )
#define __TXREFOBJ_H__

#include "txobject.h"

class txRefObj: public txObject 
{ 
	TX_DEFINE_TYPE(txRefObj)

	private:
		unsigned long int _ref_;
		const txObject* _object_;

	public: 
		txRefObj (unsigned long int ref, const txObject* obj);

		~txRefObj (void);

		unsigned long int ref (void) const
		{
			return _ref_;
		}

		const txObject* object (void) const
		{
			return _object_;
		}

		void stream (txOut& out) const;

		void destream (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txRefObj)

#endif // __TXREFOBJ_H__

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

#if !defined ( __TXOBJREF_H__ )
#define __TXOBJREF_H__

#include "txobject.h"

class txObjRef : public txObject 
{ 
	TX_DEFINE_TYPE(txObjRef)

	private:
		unsigned long int _ref_;

	public: 
		txObjRef (unsigned long int ref);

		~txObjRef (void);

		unsigned long int ref (void) const
		{
			return _ref_;
		}

		unsigned hash (void) const
		{
			return (unsigned) _ref_;
		}

		int equals (const txObject* obj) const;

		void stream (txOut& out) const;

		void destream (txIn& in);
}; 

TX_DECLARE_STREAM_TYPE_OPER(txObjRef)

#endif // __TXOBJREF_H__

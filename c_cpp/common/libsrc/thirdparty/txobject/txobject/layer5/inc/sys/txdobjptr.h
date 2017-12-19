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

#if !defined ( __TXDOBJPTR_H__ )
#define __TXDOBJPTR_H__

#include "txstream.h"

class txMemObj;
class txString;

class txDObjPtr : public txStream
{
	TX_DECLARE_STREAM_TYPE(txDObjPtr)

	private:
		txDObjPtr* _front_;
		txDObjPtr* _back_;

		txMemObj* _r_;
		txString* _oid_;
		
	private:
		void _add_ (void);
		void _remove_ (void);

		txDObjPtr (void) : _front_(0), _back_(0), _r_(0), _oid_(0) {}

	public:
		txMemObj* _getObject_ (void); // PRIVATE //

		static txDObjPtr* getPtr (txMemObj* object);

	public:
		txDObjPtr (txMemObj* object);
		~txDObjPtr (void);

		void reRef (txMemObj*);

		unsigned hash (void) const;

		int equals (const txObject*) const;

		void storeInners (txOut& out) const;

		void restoreInners (txIn& in);
};

#endif // __TXDOBJPTR_H__


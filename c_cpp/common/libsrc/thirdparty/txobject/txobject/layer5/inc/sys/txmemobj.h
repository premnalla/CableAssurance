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

#if !defined ( __TXMEMOBJ_H__ )
#define __TXMEMOBJ_H__

#include "txstream.h"

class txString;

class txMemObj : public txStream
{
	TX_DECLARE_STREAM_TYPE(txMemObj)

	private:
		txMemObj* _front_;
		txMemObj* _back_;

	private:
		txString* _oid_;
		txString* _term_;
		int _accessible_flag_;

	public:
		txMemObj (void);
		~txMemObj (void);

		int accessible (void);
		void accessible (int flag);

		virtual void incref (void);
		virtual void decref (void);
		virtual int refcount (void);

		txMemObj* getReference (void);

		const txString* oidObject (void) const;
		const char* oid (void) const;
		void oid (const char* oid);

		const txString* termObject (void) const;
		const char* term (void) const;
		void term (const char* term); 

		unsigned hash (void) const;
		int equals (const txObject* obj) const;

		void storeInners (txOut& out) const;
		void restoreInners (txIn& in);

	friend class txMemIterator;
	friend class txMemSpace;
};

#endif // __TXMEMOBJ_H__

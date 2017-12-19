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

#if !defined ( __TXGOBJECT_H__ )
#define __TXGOBJECT_H__

#include "txlist.h"
#include "txstream.h"
#include "txuint32.h"

class txGObject : public txStream 
{ 
	TX_DECLARE_STREAM_TYPE(txGObject)

	private:
		txList _attrs_;
		txUInt32 _type_;
		unsigned long _length_;

	public: 
		txGObject (void);

		txGObject (const txUInt32& type, unsigned long length);

		~txGObject (void);

		const txList* attrs (void) const
		{
			return &_attrs_;
		}

		unsigned long type (void) const
		{
			return _type_.value();
		}

		const txObject* get (const txObject& k) const
		{
			return _attrs_.find(&((txObject&) k));
		}

		void stream (txOut& out) const;

		void storeInners (txOut& out) const;

		void restoreInners (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txGObject)

#endif // __TXGOBJECT_H__

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
 
#if !defined ( __TXOBJECT_H__ )
#define __TXOBJECT_H__

#include "txin.h"
#include "txout.h"
#include "txutil.h"
#include "txtypecodes.h"
#include "sys/txtypecheckss.h"

class txObject
{
	TX_DEFINE_TYPE(txObject)

	public:
		txObject (void);

		virtual ~txObject (void);

		virtual unsigned hash (void) const
		{
			return (unsigned) this ^ ((unsigned) this >> 3);
		}

		virtual unsigned long id (void) const
		{
			return (unsigned long) hash();
		}

		virtual int equals (const txObject* obj) const
		{
			return (this == obj);
		}

		virtual int compare (const txObject* obj) const;

		int isClass (const txString& type) const
		{
			return txTypeCheckSS::isClass(classType(), type);
		}

		int isSubClass (const txString& type) const
		{
			return txTypeCheckSS::isSubClass(classType(), type);
		}

		int isSuperClass (const txString& type) const
		{
			return txTypeCheckSS::isSuperClass(type, classType());
		}

		virtual void stream (txOut&) const
		{
		}

		virtual void destream (txIn&)
		{
		}
};

TX_DECLARE_STREAM_TYPE_OPER(txObject)

class txObjectSequence : public txObject
{
	TX_PERSIST

	public:
		virtual const txObject* insert (txObject*);

		virtual const txObject* find (const txObject*) const;

		virtual txObject* remove (const txObject*);

		virtual void removeAndDestroy (const txObject*);

		virtual void clearAndDestroy (void);

		virtual void clear (void);

		virtual int entries (void) const;
};

class txObjectIterator : public txObject
{
	TX_PERSIST

	public:
		virtual txObject* remove (void);

		virtual const txObject* next (void);

		virtual void reset (void);
};

#endif // __TXOBJECT_H__

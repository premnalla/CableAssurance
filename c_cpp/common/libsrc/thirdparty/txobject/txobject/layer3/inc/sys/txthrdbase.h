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

#if !defined ( __TXTHRDBASE_H__ )
#define __TXTHRDBASE_H__

#include "txobject.h"

class txThrdBase : public txObject
{
	protected:
		const char* _name;
		void* _native_obj;

		txThrdBase (const txThrdBase&);
		txThrdBase& operator= (const txThrdBase&);

	public:
		txThrdBase (const char* name = "default");
		~txThrdBase (void);

		unsigned long id (void) const
		{
			return (unsigned long) this;
		}

		const char* name (void) const
		{
			return _name;
		}

		void name (const char* name)
		{
			_name = name;
		}
};

#endif // __TXTHRDBASE_H__

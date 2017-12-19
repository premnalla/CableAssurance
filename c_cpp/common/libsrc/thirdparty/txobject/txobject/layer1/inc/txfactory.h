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

#if !defined ( __TXFACTORY_H__ )
#define __TXFACTORY_H__

#include "txobject.h"

class txIn;
class txString;
class txStream;
class txHashMap;

typedef txStream* (*TX_STREAM_FUNC) (void);
typedef txStream* (*TX_EXCEPT_FUNC) (const txObject&, txIn&);

class txFactory
{
	private:
		static txHashMap* _factory_;
		static txHashMap* _excepts_;

	public:
		static void registerType (const txObject&, TX_STREAM_FUNC);

		static void unregisterType (const txObject&);

		static void registerException (const txObject&, TX_EXCEPT_FUNC);

		static void unregisterException (const txObject&);

		static txStream* create (const txObject&, int except = 0);

		static txStream* create (const txObject&, txIn&, int except=0);

		static const txString* findTypeInFactory (const txObject&);

		static const txString* findTypeInException (const txObject&);

		static void clear (void);
};

class txFactoryBinder
{
	public:
		txFactoryBinder (const txObject& type, TX_STREAM_FUNC func);
};

#endif // __TXFACTORY_H__

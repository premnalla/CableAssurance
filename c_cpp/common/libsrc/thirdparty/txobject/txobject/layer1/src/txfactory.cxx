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

#include <stdio.h>
#include <stdlib.h>

#include "txstring.h"
#include "txstream.h"
#include "txfactory.h"
#include "txhashmap.h"

class txTypeLookUp : public txObject
{
	private:
		unsigned _id_;

	public:
		txTypeLookUp (unsigned id) :
			_id_(id)
		{
		}

		~txTypeLookUp (void)
		{
		}

		unsigned hash (void) const
		{
			return _id_;
		}

		int equals (const txObject* obj) const
		{
			return id() == obj->id();
		}
};

TX_STATIC_DEALLOC_NOTIFY(txFactory::clear,(),clear)

txHashMap* txFactory::_factory_ = 0;
txHashMap* txFactory::_excepts_ = 0;

void txFactory::registerType (const txObject& type, TX_STREAM_FUNC func)
{
	txString* type_v;
	txTypeLookUp lookup(type.id());

	if (!_factory_)
	{
		_factory_ = new txHashMap();
	}

	if (type_v = (txString*) _factory_->find(&lookup))
	{
		fprintf(stderr, "TXOBJECT[error] : dup. registerType ");
		fprintf(stderr, "[ %s, ", ((txString&) type).data());
		fprintf(stderr, "%s ]\n", type_v->data());
		fflush(stderr); TX_CRASH;
	}

	_factory_->insertKeyAndValue((txObject*) &type, (txObject*) func);
}

void txFactory::unregisterType (const txObject& type)
{
	if (_factory_)
	{
		_factory_->remove(&type);
	}
}

void txFactory::registerException (const txObject& type, TX_EXCEPT_FUNC func)
{
	txString* type_v;
	txTypeLookUp lookup(type.id());

	if (!_excepts_)
	{
		_excepts_ = new txHashMap();
	}

	if (type_v = (txString*) _excepts_->find(&lookup))
	{
		fprintf(stderr, "TXOBJECT[error] : dup. registerException ");
		fprintf(stderr, "[ %s, ", ((txString&) type).data());
		fprintf(stderr, "%s ]\n", type_v->data());
		fflush(stderr); TX_CRASH;
	}

	_excepts_->insertKeyAndValue((txObject*) &type, (txObject*) func);
}

void txFactory::unregisterException (const txObject& type)
{
	if (_excepts_)
	{
		_excepts_->remove(&type);
	}
}

txStream* txFactory::create (const txObject& type, int x)
{
	txStream* obj = 0;
	TX_STREAM_FUNC g_f;
	TX_EXCEPT_FUNC e_f;
	txTypeLookUp t(type.id());

	if (!_factory_)
	{
		return 0;
	}
	
	if (x && _excepts_ && (e_f=(TX_EXCEPT_FUNC) _excepts_->findValue(&t)))
	{
		txIn in((char*) 0, 0); obj = e_f(type, in);
	}
	else if (g_f = (TX_STREAM_FUNC) _factory_->findValue(&t))
	{
		obj = g_f();
	}

	return obj;
}

txStream* txFactory::create (const txObject& type, txIn& in, int x)
{
	txStream* obj = 0;
	TX_EXCEPT_FUNC e_f;
	TX_STREAM_FUNC g_f;
	txTypeLookUp t(type.id());

	if (!_factory_)
	{
		return 0;
	}
	
	if (x && _excepts_ && (e_f=(TX_EXCEPT_FUNC) _excepts_->findValue(&t)))
	{
		obj = e_f(type, in);
	}
	else if (g_f = (TX_STREAM_FUNC) _factory_->findValue(&t))
	{
		obj = g_f();
		obj->destream(in);
	}

	return obj;
}

const txString* txFactory::findTypeInFactory (const txObject& type)
{
	if (_factory_)
	{
		txTypeLookUp lookup(type.id());

		return (txString*) _factory_->find(&lookup);
	}
	else
	{
		return 0;
	}
}

const txString* txFactory::findTypeInException (const txObject& type)
{
	if (_excepts_)
	{
		txTypeLookUp lookup(type.id());

		return (txString*) _excepts_->find(&lookup);
	}
	else
	{
		return 0;
	}
}

void txFactory::clear (void)
{
	delete _factory_; _factory_ = 0;
	delete _excepts_; _excepts_ = 0;
}

txFactoryBinder::txFactoryBinder (const txObject& t, TX_STREAM_FUNC f)
{
	txFactory::registerType(t, f);
}


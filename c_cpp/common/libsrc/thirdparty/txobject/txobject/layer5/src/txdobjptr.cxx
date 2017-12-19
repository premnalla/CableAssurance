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

#include "txstring.h"
#include "txdobjmgr.h"
#include "txhashmap.h"
#include "txdobjmgr.h"
#include "sys/txmemobj.h"
#include "sys/txdobjptr.h"

static txHashMap THE_OIDS;

TX_DEFINE_STREAM_TYPE(txDObjPtr,txStream)

txDObjPtr::txDObjPtr (txMemObj* object) :
	_r_(object),
	_front_(0),
	_back_(0),
	_oid_(0)
{
	_add_();

	_r_->incref();
}

txDObjPtr::~txDObjPtr (void)
{
	if (_r_)
	{
		_remove_();

		_r_->decref();
	}

	delete _oid_; _oid_ = 0;

	_r_ = 0;
}

void txDObjPtr::_add_ (void)
{
	txDObjPtr* oid;
	txString* id = _oid_ ? _oid_ : (txString*) _r_->oidObject();

	if (!THE_OIDS.insertKeyAndValue(id, this))
	{
		oid = (txDObjPtr*) THE_OIDS.findValue(id, this);

		oid->_front_ = this;
		this->_back_ = oid;
	}
}

void txDObjPtr::_remove_ (void)
{
	txDObjPtr* oid;
	txString* id = _oid_ ? _oid_ : (txString*) _r_->oidObject();

	if (id && (oid = (txDObjPtr*) THE_OIDS.findValue(id)))
	{
		do
		{
			if (oid == this)
			{
				if (this->_back_)
				{
					this->_back_->_front_ = this->_front_;
				}

				if (this->_front_)
				{
					this->_front_->_back_ = this->_back_;
				}

				break;
			}
		}
		while (oid = oid->_back_);

		if (!this->_front_ && !this->_back_)
		{
			THE_OIDS.remove(id);
		}
		else if (!this->_front_)
		{
			THE_OIDS.findValue(id, this->_back_);
		}
	}

	this->_front_ = 0; this->_back_ = 0;
}

txMemObj* txDObjPtr::_getObject_ (void)
{
	if (!_r_)
	{
		txDObjMgr* t;
		txHashMapIterator iter(txDObjGlobals::txDObjMgrs);

		while (!_r_ && iter.next())
		{
			t = (txDObjMgr*) iter.value();
			_r_ = (txMemObj*) t->accessObject(_oid_->data());
		}

		if (_r_)
		{
			_r_->incref();
		}
	}

	return _r_;
}

txDObjPtr* txDObjPtr::getPtr (txMemObj* object)
{
	return (txDObjPtr*) THE_OIDS.findValue(object->oidObject());
}

void txDObjPtr::reRef (txMemObj* object)
{
	int referenced;
	txDObjPtr* oid = this;

	if (THE_OIDS.remove(object->oidObject()))
	{
		THE_OIDS.insertKeyAndValue(
			(txObject*) object->oidObject(), object);
	}

	do
	{
		referenced = (int) oid->_r_;

		oid->_r_ = object;

		if (!referenced)
		{
			object->incref();
		}
	}
	while (oid = oid->_back_);
}

unsigned txDObjPtr::hash (void) const
{
	if (_r_)
	{
		return _r_->hash();
	}
	else
	{
		return 0;
	}
}

int txDObjPtr::equals (const txObject* obj) const
{
	if (_r_)
	{
		return _r_->equals(obj);
	}
	else
	{
		return 0;
	}
}

void txDObjPtr::storeInners (txOut& out) const
{
	out << _r_->oidObject();
}

void txDObjPtr::restoreInners (txIn& in)
{
	in >> _oid_;

	_getObject_();

	_add_();
}


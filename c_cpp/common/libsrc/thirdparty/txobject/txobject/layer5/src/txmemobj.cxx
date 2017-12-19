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
#include "sys/txmemobj.h"
#include "sys/txdobjptr.h"

TX_DEFINE_STREAM_TYPE(txMemObj,txStream)

txMemObj::txMemObj (void) :
	_accessible_flag_(1),
	_front_(0),
	_back_(0),
	_term_(0),
	_oid_(0)
{
} 

txMemObj::~txMemObj (void)
{
	_back_ = 0;
	_front_ = 0;
	_accessible_flag_ = 0;

	delete _oid_; _oid_ = 0;
	delete _term_; _term_ = 0;
} 

txMemObj* txMemObj::getReference (void)
{
	if (_accessible_flag_)
	{
		return (txMemObj*) new txDObjPtr(this);
	}
	else
	{
		return 0;
	}	
}

int txMemObj::accessible (void)
{
	return _accessible_flag_;
}

void txMemObj::accessible (int flag)
{
	_accessible_flag_ = flag;
}

void txMemObj::incref (void)
{
}

void txMemObj::decref (void)
{
}

int txMemObj::refcount (void)
{
	return 0;
}

const txString* txMemObj::oidObject (void) const
{
	return _oid_;
}

const char* txMemObj::oid (void) const
{
	if (_oid_)
	{
		return _oid_->data();
	}
	else
	{
		return 0;
	}
}

void txMemObj::oid (const char* oid)
{
	if (oid)
	{
		_oid_ = new txString(oid);
	}
	else
	{
		delete _oid_; _oid_ = 0;
	}
}

const txString* txMemObj::termObject (void) const
{
	return _term_;
}

const char* txMemObj::term (void) const
{
	if (_term_)
	{
		return _term_->data();
	}
	else
	{
		return 0;
	}
}

void txMemObj::term (const char* term)
{
	if (term)
	{
		_term_ = new txString(term);
	}
	else
	{
		delete _term_; _term_ = 0;
	}
}

unsigned txMemObj::hash (void) const
{
	return _oid_->hash();
}

int txMemObj::equals (const txObject* obj) const
{
	if (obj->isSubClass(txMemObj::Type))
	{
		return _oid_->equals(obj);
	}

	return 0;
}

void txMemObj::storeInners (txOut& out) const
{
	out << _oid_;
	out << _term_;
}

void txMemObj::restoreInners (txIn& in)
{
	in >> _oid_;
	in >> _term_;
}


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

#include <iostream.h>

#include "txcount.h"

TX_DEFINE_DOBJECT(txCount,txDObject)

txCount::txCount (void)
{
	_Count_ = 0;
	_MaxCount_ = 0;
}

txCount::txCount (txUInt32* count, txUInt32* max)
{
	_Count_ = count; 
	_MaxCount_ = max;
}

txCount::~txCount (void)
{
	delete _Count_; _Count_ = 0;
	delete _MaxCount_; _MaxCount_ = 0;
}

void txCount::restoreComplete (void)
{
}

void txCount::registerInners (void) const
{
	registerAttribute("_Count_",
		&_Count_);
	registerAttribute("_MaxCount_",
		&_MaxCount_);

	registerTwoWayMethod("_getCount_",
		(TX_METHOD_PTR) txCount::_getCount_);
	registerTwoWayMethod("_incCount_",
		(TX_METHOD_PTR) txCount::_incrementCount_);
	registerTwoWayMethod("_setCount_",
		(TX_METHOD_PTR) txCount::_setCount_);
	registerTwoWayMethod("_setMaxCount_",
		(TX_METHOD_PTR) txCount::_setMaxCount_);
}

txObject* txCount::_getCount_ (txCount* self)
{
	txObject* retval = 0;

	if (self->ownedLocally())
	{
		retval = new txUInt32(self->_Count_->value());
	}
	else
	{
		retval = self->invokeTwoWayMethod("_getCount_");
	}

	return retval;
}

txObject* txCount::_setCount_ (txCount* self, txIn& in)
{
	txUInt32 count; in >> count;

	if (self->ownedLocally())
	{
		self->_Count_->value(count.value());

		self->attrWrite("_Count_", self->_Count_);
		self->commit();
	}
	else
	{
		self->invokeTwoWayMethod("_setCount_", &count);
	}
	
	return 0;
}

txObject* txCount::_setMaxCount_ (txCount* self, txIn& in)
{
	txUInt32 max_count; in >> max_count;

	if (self->ownedLocally())
	{
		self->_MaxCount_->value(max_count.value());

		self->attrWrite("_MaxCount_", self->_MaxCount_);
		self->commit();
	}
	else
	{
		self->invokeTwoWayMethod("_setMaxCount_", &max_count);
	}

	return 0;
}

txObject* txCount::_incrementCount_ (txCount* self)
{
	if (self->ownedLocally())
	{
		self->_Count_->value(self->_Count_->value() + 1);

		self->attrWrite("_Count_", self->_Count_);
		self->commit();
	}
	else
	{
		self->invokeTwoWayMethod("_incCount_");
	}

	return 0;
}

void txCount::createNotify (void)
{
	cout << "***** AFTER CREATION **** : " << oid() << endl;

	if(!ownedLocally())
	{
		txUInt32* count = 0;

		count = (txUInt32*) invokeTwoWayMethod("_getCount_");

		if (count)
		{
			cout << "***** GET COUNT **** : " << count->value();
			cout << endl;

			delete count; count = 0;
		}
	}
}

void txCount::destroyNotify (void)
{
	cout << "***** BEFORE DELETION **** : " << oid() << endl;
}

void txCount::attrWriteNotify (const char* attr_name,
	const txObject* old_value, const txObject* new_value)
{
	if ((strcmp(attr_name, "_Count_")==0))
	{
		cout << "Attribute Changed From ";

		if (old_value)
		{
			cout << ((txUInt32*)old_value)->value() << flush ;
		}

		cout << " To ";
		cout << ((txUInt32*)new_value)->value() << endl;
	}
}

void txCount::changeOwnerNotify (const txAbsPeer& peer1,
	const txAbsPeer& peer2)
{
	cout << "THE OWNER HAS CHANGED FROM " << peer1.getId();
	cout << " TO " << peer2.getId() << endl;
}


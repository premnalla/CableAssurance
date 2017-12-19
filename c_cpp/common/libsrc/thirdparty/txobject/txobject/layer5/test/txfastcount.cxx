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

#include "txstring.h"
#include "txfastcount.h"

TX_DEFINE_DOBJECT(txFastCount,txCount)

txFastCount::txFastCount (void)
{
	_FastCount_ = 0;
}

txFastCount::txFastCount (txUInt32* count, txUInt32* max) : txCount(count, max)
{
	_FastCount_ = new txUInt32(count->value() + 200);
}

txFastCount::~txFastCount (void)
{
	delete _FastCount_; _FastCount_ = 0;
}

void txFastCount::restoreComplete (void)
{
}

void txFastCount::registerInners (void) const
{
	registerAttribute("_FastCount_",
		&_FastCount_);

	registerTwoWayMethod("_incFast_",
		(TX_METHOD_PTR) txFastCount::_incFastCount_);
}

txObject* txFastCount::_incFastCount_ (txFastCount* self)
{
	if (self->ownedLocally())
	{
		self->_FastCount_->value(self->_FastCount_->value() + 200);

		self->attrWrite("_FastCount_", self->_FastCount_);
		self->commit();
	}
	else
	{
		self->invokeTwoWayMethod("_incFast_");
	}

	return 0;
}

void txFastCount::createNotify (void)
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

void txFastCount::destroyNotify (void)
{
	cout << "***** BEFORE DELETION **** : " << oid() << endl;
}

void txFastCount::attrWriteNotify (const char* attr_name,
	const txObject*, const txObject* new_object)
{
	cout << "txFastCount Object's oid : " << oid() << endl;; 
	cout << "  Attribute : " << attr_name << endl;

	if ((strcmp(attr_name, "_Count_")==0))
	{
		cout << "  New Value : ";
		cout <<	((txUInt32*) new_object)->value() << endl;
	}
	else if ((strcmp(attr_name, "_FastCount_")==0))
	{
		cout << "  Data Member Value : " << _FastCount_->value();
		cout << endl;
		cout << "  New Value : ";
		cout <<	((txUInt32*) new_object)->value() << endl;
	}
}


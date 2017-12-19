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

#include "txattrobj.h"

TX_DEFINE_DOBJECT(txAttrObj,txDObject)

txAttrObj::txAttrObj (void)
{
	_list_ = new txList();
	_dict_ = new txHashMap();
	_Count_ = new txUInt32(100);
}

txAttrObj::~txAttrObj (void)
{
	delete _list_; _list_ = 0;
	delete _dict_; _dict_ = 0;
	delete _Count_; _Count_ = 0;
}

void txAttrObj::restoreComplete (void)
{
}

void txAttrObj::registerInners (void) const
{
	registerAttribute("_Count_", &_Count_);
	registerAttribute("_dict_", &_dict_);
	registerAttribute("_list_", &_list_);
}

void txAttrObj::addDictEntry (txObject* index, txObject* value)
{
	attrWriteIndex("_dict_", index, value); commit();
}

void txAttrObj::removeDictEntry (txObject* index)
{
	attrDeleteIndex("_dict_", index); commit();
}

void txAttrObj::addListEntry (txObject* index, txObject* value)
{
	attrWriteIndex("_list_", index, value); commit();
}

void txAttrObj::removeListEntry (txObject* index)
{
	attrDeleteIndex("_list_", index); commit();
}

int txAttrObj::getDictEntries (void)
{
	return _dict_->entries();
}

int txAttrObj::getListEntries (void)
{
	return _list_->entries();
}

void txAttrObj::printDict (void)
{
	cout << "printDict" << endl;
}

void txAttrObj::printList (void)
{
	int entries = _list_->entries();

	for (int i= 0; i < entries; i++)
	{
		cout << ((txUInt32*)_list_->at(i))->value() << endl;
	}
}

void txAttrObj::createNotify (void)
{
	cout << "***** AFTER CREATION **** : " << oid() << endl;
}

void txAttrObj::destroyNotify (void)
{
	cout << "***** BEFORE DELETION **** : " << oid() << endl;
}

void txAttrObj::attrWriteNotify (const char* attr_name,
	const txObject* old_value, const txObject* new_value)
{
	if ((strcmp(attr_name, "_Count_")==0))
	{
		cout << "Attribute Changed From "  ;

		if (old_value)
		{
			cout << ((txUInt32*)old_value)->value() << flush ;
		}

		cout << " To ";
		cout << ((txUInt32*)new_value)->value() << endl;
	}
}

void txAttrObj::attrWriteIndexNotify (const char* attr_name,
	const txObject* index, const txObject* old_value,
	const txObject* new_value)
{
	// Note: This Function Expects All Values (Index, Value)
	// to be txUInt32s

	cout << "The refer attribute " << attr_name ;
	cout << " changed at the Index ";
	cout  << ((txUInt32*)index)->value() ;
	cout << " changed From " << endl;

	if (old_value && old_value->isClass(txUInt32::Type))
	{
		cout << ((txUInt32*) old_value)->value() << flush ;
	}

	cout << " To ";
	cout << ((txUInt32*) new_value)->value() << endl;

	printList();

}

void txAttrObj::attrDeleteIndexNotify (const char* attr_name,
	const txObject* index, const txObject* value)
{
	cout << "The refer attribute " << attr_name ;
	cout << " deleted an attribute at index ";
	cout  << ((txUInt32*) index)->value() << " Deleting Value " ;

	if (value)
	{
		cout << ((txUInt32*) value)->value() << flush ;
	}

	cout << endl;
}

void txAttrObj::changeOwnerNotify (const txAbsPeer& peer1,
	const txAbsPeer& peer2)
{
	cout << "THE OWNER HAS CHANGED FROM " << peer1.getId();
	cout << " TO " << peer2.getId() << endl;
}


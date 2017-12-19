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

#include "txin.h"
#include "txout.h"
#include "txlist.h"
#include "txint32.h"
#include "txhashmap.h"

#include "txclassc.h"

void showListofStrings (txList* list)
{
	for (int i = 0; i < list->entries(); i++)
	{
		txString* t = (txString*) list->at(i);
		cout << "   type : " << t->data() << endl;
	}
}

int main (void)
{
	txOut out;
	txList list;
	txInt32 integer(123);
	txString string("A to Z");

	/******************************************************************
	* Test RTTI.
	******************************************************************/

	cout << endl << "SUB AND SUPER TYPE CHECKS" << endl;
	cout << "-------------------------\n" << endl;

	if (integer.isClass(txInt32::Type))
	{
		cout << "RTTI IS WORKING 1" << endl;
	}
	else
	{
		cout << "RTTI FAILED 1" << endl;
	}

	if (integer.isSubClass(txObject::Type))
	{
		cout << "RTTI IS WORKING 2" << endl;
	}
	else
	{
		cout << "RTTI FAILED 2" << endl;
	}

	if (!integer.isSubClass(txString::Type))
	{
		cout << "RTTI IS WORKING 3" << endl;
	}
	else
	{
		cout << "RTTI FAILED 3" << endl;
	}

	if (integer.isSubClass(txObject::Type))
	{
		cout << "RTTI IS WORKING 4" << endl;
	}
	else
	{
		cout << "RTTI FAILED 4" << endl;
	}

	if (txTypeCheckSS::isSuperClass(txObject::Type, txString::Type))
	{
		cout << "RTTI IS WORKING 5" << endl;
	}
	else
	{
		cout << "RTTI FAILED 5" << endl;
	}

	if (!txTypeCheckSS::isSuperClass(txString::Type, txObject::Type))
	{
		cout << "RTTI IS WORKING 6" << endl;
	}
	else
	{
		cout << "RTTI FAILED 6" << endl;
	}

	if (txTypeCheckSS::isSuperClass(txObject::Type, txClassC::Type))
	{
		cout << "RTTI IS WORKING 7" << endl;
	}
	else
	{
		cout << "RTTI FAILED 7" << endl;
	}

	if (!txTypeCheckSS::isSuperClass(txClassC::Type, txObject::Type))
	{
		cout << "RTTI IS WORKING 8" << endl;
	}
	else
	{
		cout << "RTTI FAILED 8" << endl;
	}

	cout << endl << "SUB AND SUPER TYPE REQUEST" << endl;
	cout << "--------------------------\n" << endl;

	txList CLASSES;

	txTypeCheckSS::superClasses(txString::Type, CLASSES);
	cout << "SuperClasses of txString::Type : " << endl;

	showListofStrings(&CLASSES);
	CLASSES.clear();

	txTypeCheckSS::subClasses(txObject::Type, CLASSES);
	cout << "SubClasses of txObject::Type : " << endl;

	showListofStrings(&CLASSES);
	CLASSES.clear();

	txTypeCheckSS::superClasses(txObject::Type, CLASSES);
	cout << "SuperClasses of txObject::Type : " << endl;

	showListofStrings(&CLASSES);
	CLASSES.clear();

	/******************************************************************
	* Test Lists.
	******************************************************************/

	cout << endl << "STREAM AND DE-STREAM CHECKS" << endl;
	cout << "----------------------------\n" << endl;

	list.append(&string);
	list.append(&integer);

	txList streamList;
	streamList.append(&list);
	out << streamList;

	list.clear();
	streamList.clear();

	txIn in1(out);

	txObject* obj1;
	txObject* obj2;
	txObject* obj3;

	in1 >> obj3;

	if (obj3->isClass(txList::Type))
	{
		cout << "We Have a List Object within a List Object" << endl;

		obj1 = ((txList*) obj3)->get();
		((txList*) obj3)->clear();
		delete obj3; obj3 = 0;

		obj2 = ((txList*) obj1)->get();

		if (obj2->isClass(txString::Type))
		{
			cout << "Item One Is a String" << endl;
			cout << "  DATA : " << ((txString*) obj2)->data();
			cout << endl;
		}

		delete obj2; obj2 = 0;

		obj2 = ((txList*) obj1)->get();

		if (obj2->isClass(txInt32::Type))
		{
			cout << "Item Two Is an Integer" << endl;
			cout << "VALUE : " << ((txInt32*) obj2)->value();
			cout << endl;
		}

		delete obj2; obj2 = 0;
		((txList*) obj1)->clear();
		delete obj1; obj1 = 0;
	}

	/******************************************************************
	* Test Dictionary.
	******************************************************************/

	out.flush();

	txHashMap map;
	txObject* obj4;
	txObject* obj5;

	map.insertKeyAndValue(&integer, &string);

	out << map;

	map.clear();

	txIn in2(out);

	in2 >> obj4;

	txHashMapIterator iter((txHashMap&) *obj4);

	while (obj5 = (txObject*) iter.next())
	{
		cout << "KEY : " << ((txInt32*) obj5)->value() << endl;

		delete obj5; obj5 = 0;

		obj5 = (txObject*) iter.value();

		cout << "VALUE : " << ((txString*) obj5)->data() << endl;

		delete obj5; obj5 = 0;
	}

	delete obj4; obj4 = 0;

	cout << endl;

	return 1;
}


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
#include "txclassc.h"

void checkUser (txClassC& obj)
{
	cout << "NAME : " << obj.className() << endl;
	cout << " DATA : " << obj.data() << endl;
	cout << " VALUE : " << obj.value() << endl;

	const txList* list = obj.list();

	if (list)
	{
		cout << " LIST ENTRIES : " << list->entries() << endl;
	}
	else
	{
		cout << " LIST IS NULL" << endl;
	}
}

int main (void)
{
	txOut out;
	txClassC testObj("ABC", 123);

	out << testObj;

	txIn in(out);

	txClassC* testNew; in >> testNew;

	if (testNew)
	{
		checkUser(*testNew);
		delete testNew; testNew = 0;
	}

	in.cursor(0); // move index to the beginning of stream

	txClassC testOverlay("DUMMY", 0);

	in >> testOverlay;

	checkUser(testOverlay);

	return 1;
}


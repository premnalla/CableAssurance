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

#include <stdlib.h>
#include <iostream.h>

#include "txtimer.h"
#include "sys/txmemspace.h"

int main (int argc, char** argv)
{
	if (argc != 2)
	{
		cout << "usgae : program number_of_object\n" << flush;
		exit(1);
	}

	int objects = atoi(argv[1]);

	txMemSpace* MM = new txMemSpace("ROOT");
	txMemSpace* MS = (txMemSpace*) MM->allocateSpace("SPACE");
	txMemObj* MO1 = 0;

	double start = txTimer::currentTime();

	for (int i = 0; i < objects; i++)
	{
		MO1 = new txMemObj();
		MS->allocateObject(MO1);
	}

	double stop = txTimer::currentTime();

	cout << "\n Time to create and allocate txMemObj's in MemSpace : ";
	cout << stop-start << endl << endl;

	start = txTimer::currentTime();

	for (int j = 0; j < objects; j++)
	{
		if (!MM->accessObject("SPACE/1"))
		{
			cout << "OBJECT NOT FOUND" << endl;
		}
	}

	stop = txTimer::currentTime();

	cout << " Time to find txMemObj's in MemSpace : ";
	cout << stop-start << endl << endl;

	txMemIterator iter(*MM);

	start = txTimer::currentTime();

	while (iter.next());

	stop = txTimer::currentTime();

	cout << " Time to iterate over all memory spaces : ";
	cout << stop-start << endl << endl;

	delete MM; MM = 0;

	return 1;
}


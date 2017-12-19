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
#include "sys/txmemspace.h"

int main (void)
{
	txMemObj* MO;
	txMemSpace* MS;
	txMemSpace* MM = new txMemSpace("ROOT");

	cout << "ROOT MM : " << MM->oid() << endl;

	MS = (txMemSpace*) MM->allocateSpace("SPACE/HOST_A");
	MO = new txMemObj(); MS->allocateObject(MO);
	MO = new txMemObj(); MS->allocateObject(MO);
	MO = new txMemObj(); MS->allocateObject(MO);

	MS = (txMemSpace*) MM->allocateSpace("SPACE/HOST_B");
	MO = new txMemObj(); MS->allocateObject(MO);
	MO = new txMemObj(); MS->allocateObject(MO);
	MO = new txMemObj(); MS->allocateObject(MO);

	MS = (txMemSpace*) MM->accessSpace("SPACE");
	MO = (txMemObj*) MS->accessObject("HOST_A/1");
	cout << "txMemObj : " << MO->oid() << endl;
	MO = (txMemObj*) MS->accessObject("SPACE/HOST_A/1");
	cout << "txMemObj : " << MO->oid() << endl;

	MS = (txMemSpace*) MM->accessSpace("SPACE/HOST_A");
	MO = (txMemObj*) MS->accessObject("HOST_A/1");
	cout << "txMemObj : " << MO->oid() << endl;
	txMemObj* MO1 = (txMemObj*) MS->accessObject("SPACE/HOST_A/2");
	cout << "txMemObj : " << MO->oid() << endl;

	cout << "*******************" << endl;

	cout << "MS : " << MS->oid() << endl;
	cout << "MO1 : " << MO1->oid() << endl;

	txMemIterator iter(*MS);

	cout << "********* STARTING ITERATIONS **********" << endl;

	MO = iter.next();
	if (MO) cout << "MO : " << MO->oid() << endl;
	MS->deallocateObject(MO1);
	MO = iter.next();
	if (MO) cout << "MO : " << MO->oid() << endl;
	MO = iter.next();
	if (MO) cout << "MO : " << MO->oid() << endl;
	MO = iter.next();
	if (MO) cout << "MO : " << MO->oid() << endl;

	delete MM; MM = 0;

	return 1;
}


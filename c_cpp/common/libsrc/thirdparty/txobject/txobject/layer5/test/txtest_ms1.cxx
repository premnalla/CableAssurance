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

#include <string.h> 
#include <iostream.h> 
#include "sys/txmemspace.h"

int main (void)
{
	txMemSpace* MM = new txMemSpace("ROOT");
	txMemSpace* MS = (txMemSpace*) MM->allocateSpace("SPACE");

	txMemObj* MO1 = new txMemObj(); MS->allocateObject(MO1);
	txMemObj* MO2 = new txMemObj(); MS->allocateObject(MO2);

	txMemObj* MO3 = new txMemObj();
	MO3->oid("SPACE/3");
	MO3->term("3");
	MM->registerObject(MO3);

	//////////////////////////////////////////////////////////
	// TEST 1
	//////////////////////////////////////////////////////////

	cout << "check if MO1 was created : ";
	if (strcmp(MO1->oid(), "SPACE/1") == 0)
	{
		cout << "SUCCESS " << endl;
	}
	else
	{
		cout << "FALIED " << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 2
	//////////////////////////////////////////////////////////

	cout << "check if MO2 was created : ";
	if (strcmp(MO2->oid(), "SPACE/2") == 0)
	{
		cout << "SUCCESS " << endl;
	}
	else
	{
		cout << "FALIED " << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 3
	//////////////////////////////////////////////////////////

	if (MO1 = (txMemObj*) MM->accessObject("SPACE/1"))
	{
		if (strcmp(MO2->oid(), "SPACE/2") == 0)
		{
			cout << "access of MO1 : SUCCESS " << endl;
		}
		else
		{
			cout << "access of MO1 : FALIED " << endl;
		}
	}
	else
	{
		cout << "access of MO1 : FALIED " << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 4
	//////////////////////////////////////////////////////////

	if (MO3 = (txMemObj*) MM->accessObject("SPACE/3"))
	{
		if (strcmp(MO3->oid(), "SPACE/3") == 0)
		{
			cout << "access of MO3 : SUCCESS " << endl;
		}
		else
		{
			cout << "access of MO3 : FALIED " << endl;
		}
	}
	else
	{
		cout << "access of MO3 : FALIED " << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 6
	//////////////////////////////////////////////////////////

	MS->deallocateObject(MO3);
	if (!(MO3 = (txMemObj*) MM->accessObject("SPACE/3")))
	{
		cout << "SUCCESS in deallocating MO3 " << endl;
	}
	else
	{
		cout << "FAILURE in deallocating MO3 : " << MO3->oid() << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 7
	//////////////////////////////////////////////////////////

	MS->unregisterObject(MO2);
	delete MO2; MO2 = 0;

	if (!(MO2 = (txMemObj*) MM->accessObject("SPACE/2")))
	{
		cout << "SUCCESS in unregistering MO2 " << endl;
	}
	else
	{
		cout << "FAILURE in unregistering MO2 : " << MO2->oid();
		cout << endl;
	}

	//////////////////////////////////////////////////////////
	// TEST 8
	//////////////////////////////////////////////////////////

	delete MM; MM = 0;

	cout << "If We Have Not Cored Memory Manage Is a SUCCESS " << endl;

	return 1;
}


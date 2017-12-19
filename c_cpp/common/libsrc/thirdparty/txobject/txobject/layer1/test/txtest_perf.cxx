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

#include "txin.h"
#include "txout.h"
#include "txlist.h"
#include "txuint32.h"
#include "txstring.h"

//#include "txtimer.h"

int main (int argc, char** argv)
{
	if (argc != 2)
	{
		cout << "usgae : program number_of_object\n" << flush;
		exit(1);
	}

	int num_of_objs = atoi(argv[1]);

	txOut out;
	txList listobj;
	txUInt32* intobj;

	for (int j = 0; j < num_of_objs; j++)
	{
		intobj = new txUInt32(j);
		listobj.append(intobj);
	}

	double start = 0; // txTimer::currentTime();

	out << listobj;
	out.flush();
	out << listobj;
	out.flush();
	out << listobj;
	out.flush();
	out << listobj;

	double stop = 0; // txTimer::currentTime();

	cout << "STREAMING" << endl;
	cout << "  Length of Stream : " << out.length() << endl;
	cout << "  Time to stream : " << num_of_objs << " objects -> ";
	cout << stop-start;
	cout << endl;

	txIn in(out);

	start = 0; // txTimer::currentTime();

	txList tmpList; in >> tmpList;

	stop = 0; // txTimer::currentTime();

	cout << "STREAMING" << endl;
	cout << "  Length of Stream : " << out.length() << endl;
	cout << "  Time to destream : " << num_of_objs << " objects -> ";
	cout << stop-start;
	cout << endl;

	if (tmpList.entries() != num_of_objs)
	{
		cerr << "error : STREAM TEST FAILED" << endl;
	}

	tmpList.clearAndDestroy();
	listobj.clearAndDestroy();

	return 1;
}


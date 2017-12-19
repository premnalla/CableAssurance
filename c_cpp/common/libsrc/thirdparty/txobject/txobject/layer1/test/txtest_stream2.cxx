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

#include "txin.h"
#include "txout.h"
#include "txlist.h"

int main (void)
{
	txOut out;
	float fa = 3.45;
	double da = 3.455567;
	double db = 90848.342234;
	signed char c; char* s; float f; double d; unsigned long int i;

	cout << endl;
	cout << "If there was an error, a FAILED message will displayed.";
	cout << endl;

	out.putNull(); // put null
	out << (signed char) 't';
	out << (unsigned long int) 12;
	out << (unsigned long int) 1972;
	out << (signed char) 'c';
	out << (unsigned long int) 2048;
	out << (unsigned long int) 9991992;
	out << fa;
	out << da;
	out << db;
	out << "afdasfsd987";
	out.put("abcdef", 6, TX_OCTET_STRING);

	txIn in(out);

	if (in.getNull() != 0) cout << "    FAILED NULL TEST" << endl;

	in >> c;

	if ('t' != c) cout << "    FAILED UCHAR TEST" << endl;

	in >> i;

	if (12 != i) cout << "    FAILED UINT16 TEST" << endl;

	in >> i;

	if (1972 != i) cout << "    FAILED UINT TEST" << endl;

	in >> i;

	if ('c' == i) cout << "    FAILED CHAR8 TEST" << endl;

	in >> i;

	if (2048 != i) cout << "    FAILED INT16 TEST" << endl;

	in >> i;

	if (9991992 != i) cout << "    FAILED INT TEST" << endl;

	in >> f;

	if (fa != f) cout << "    FAILED FLOAT TEST" << endl;

	in >> d;

	if (da != d) cout << "    FAILED FLOAT64 TEST" << endl;

	in >> d;
	
	if (db != d) cout << "    FAILED FLOAT64 TEST" << endl;

	in >> s;

	if (s)
	{
		if (strcmp("afdasfsd987", s))
		{
			cout << "    FAILED ASCII STRING TEST" << endl;
		}

		delete s; s = 0;
	}
	else
	{
		cout << "    FAILED ASCII STRING TEST" << endl;
	}

	unsigned long length = in.get(s, TX_OCTET_STRING);

	if (s)
	{
		if (memcmp("abcdef", s, length))
		{
			cout << "    FAILED OCTETSTRING TEST" << endl;
		}

		delete s; s = 0;
	}
	else
	{
		cout << "    FAILED OCTETSTRING TEST" << endl;
	}

	cout << endl;

	return 1;
}


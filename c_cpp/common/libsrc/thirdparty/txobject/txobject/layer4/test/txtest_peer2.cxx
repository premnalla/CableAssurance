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
#include "txtimer.h"
#include "txippeer.h"

int main (int argc, char** argv)
{
	if (argc != 2)
	{
		cout << "usgae : program number_of_object\n" << flush;
		exit(1);
	}

	txOut out;
	txList list;
	int objects = atoi(argv[1]);
	txIpPeer peer("127.0.0.1", 5000, txAbsPeer::TCP);

	for (int i = 0; i < objects; i++)
	{
		list.append(&peer);
	}

	double start = txTimer::currentTime() * 0.001;

	out << list;

	double stop = txTimer::currentTime() * 0.001;

	cout << "\n Time to stream : " << objects << " objects" << endl;
	cout << " Time : " << stop-start << endl << endl;

	cout << " Stream Length : " << out.length() << endl;

	txIn in(out);

	start = txTimer::currentTime() * 0.001;

	txList tmp(TX_AUTODEL_ON); in >> tmp;

	stop = txTimer::currentTime() * 0.001;

	cout << "\n Time to destream : " << objects << " objects" << endl;
	cout << " Time : " << stop-start << endl << endl;

	list.clear();

	return 1;
}


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
#include "txippeer.h"

int main (void)
{
	txOut out;

	txIpPeer peer("127.0.0.1", 5000, txAbsPeer::TCP);

	out << peer;

	txIn in(out.data(), out.length());

	txIpPeer* peer2;

	in >> peer2;

	cout << "id : " << peer2->getId() << endl;

	delete peer2; peer2 = 0;

	return 1;
}


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

#include "txsync.h"
#include "txtimer.h"

txTimerEnum::RETURN_STATUS timer1 (void* x)
{
	int* i = (int*) x;

	cout << "Call Timer of 1000 (ms) : " << *i << endl;

	if (*i != 100)
	{
		return txTimerEnum::CONTINUE;
	}
	else
	{
		return txTimerEnum::STOP;
	}
}

int main (void)
{
	int* i = new int(0);

	txTimer timer(timer1, i, 1000);

	for (; *i < 500; (*i)++)
	{
		txSync::IoAndTime(txTimer::processAndGetMinWait());
		
		cout << "." << flush;
	}

	delete i; i = 0;

	return 1;
}


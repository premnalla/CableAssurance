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

unsigned long id = 0;

void fd_func (void*, int, unsigned long)
{
	cout << "write to screen" << endl;

	txSync::unregisterIO(id);

	id = 0;
}

txTimerEnum::RETURN_STATUS timer1 (void* x)
{
	cout << "Call Timer of 1000 (ms)" << endl;

	if (!id)
	{
		id = txSync::registerIO(fd_func, 0, 0, txSync::IOWrite);
	}

	return txTimerEnum::CONTINUE;
}

int main (void)
{
	int fd = 0; // file descriptor for screen output;

	txTimer timer(timer1, 0, 1000);

	id = txSync::registerIO(fd_func, 0, fd, txSync::IOWrite);

	for (int i = 0; i < 500; i++)
	{
		txSync::IoAndTime(txTimer::processAndGetMinWait());
		
		cout << "." << flush;
	}

	return 1;
}


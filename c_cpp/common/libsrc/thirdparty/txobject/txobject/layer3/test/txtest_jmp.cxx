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

#include <stdio.h>
#include <stdlib.h>
#include <setjmp.h>
#include <iostream.h>

//
// This program help determines what jump index to use for a particular os
//

!!! XXX - define the right variable for the platform
//#define TX_JB_SIZE _SIGJBLEN
//#define TX_JB_SIZE JB_SIZE
//#define TX_JB_SIZE _JBLEN
!!! XXX - define the right variable for the platform

#if defined TX_HP || TX_SOL || TX_MAC
	#define JB_SP_INDEX 1
#endif

#if defined TX_DEC
	#define JB_SP_INDEX 32
#endif

#if defined TX_SUNOS || TX_SGI
	#define JB_SP_INDEX 2
#endif

#if defined TX_WIN || TX_LINUX
	#define JB_SP_INDEX 4
#endif

jmp_buf BUF1, BUF2;

void callDummyFunction (void)
{
	setjmp(BUF1);
}

unsigned long getJmpBufElement (jmp_buf* buf, int element)
{
	return (unsigned long) (*buf)[element];
}

unsigned long* getJmpBufStackPointer (jmp_buf* buf)
{
	unsigned long* ptr = (unsigned long*) *buf;
	return (unsigned long*) ptr[JB_SP_INDEX];
}

void runStackTest (int arg)
{
	int i;
	int tstbuf[100];
	int tstvar1 = 0x01010101;
	int tstvar2 = 0x01010101;

	tstbuf[0] = 0;

	setjmp(BUF1);

	cout << "\n##### Stack Location Test #####n" << endl;
	cout << "parameter address = " << hex << (unsigned long) &arg << endl;
	cout << "var1 address = " << hex << (unsigned long) &tstvar1 << endl;
	cout << "var2 address = " << hex << (unsigned long) &tstvar2 << endl;
	cout << endl;

	int last_found = 0;
	unsigned long stack_addr_found[TX_JB_SIZE];

	for (i = 0; i < TX_JB_SIZE; i++)
	{
		unsigned long val = getJmpBufElement(&BUF1, i);

		cout << "sigjmp_buf[ " << dec << i << " ] = " << hex << val;

		if (( abs( ( int ) ( (unsigned long) &arg - val )))  <= 2048)
		{
			cout << " <--- Ptxsible Stack Address in jmpbuf";
		}

		stack_addr_found[ last_found++ ] = val;
		cout << endl;
	}

	cout << endl;

	cout << "jmpbuf stack pointer is " << hex;
	cout << getJmpBufStackPointer(&BUF1) << endl;

	int found = 0 ;
	for (i = 0; i < last_found; i++)
	{
		if (((unsigned long) getJmpBufStackPointer( &BUF1 )) ==
			stack_addr_found[ i ])
		{
			cout << "Stack Location successfully found in jmpbuf[ ";
			cout << dec << i << " ]" << endl;
			found = 1;
		}
	}

	if (!found)
	{
		cout << "ERROR: Stack Location NOT successfully found" << endl;
	}

	cout << "\n#####  Function Call Frame Size Test #####\n" << endl;

	callDummyFunction();

	if (getJmpBufStackPointer(&BUF1) > getJmpBufStackPointer(&BUF2))
	{
		cout << "The stack grows down with each function call" << endl;
	}
	else
	{
		cout << "The stack grows up with each function call" << endl;
	}

	int stack_size = abs((int)(
		( unsigned long ) getJmpBufStackPointer( &BUF1 ) -
		( unsigned long ) getJmpBufStackPointer( &BUF2 ) ));

	cout << "Stack Minimum Size is 0x"
		<< hex << stack_size << " [ "
		<< dec << stack_size << " decimal ] "
		<< "bytes" << endl;

	cout << hex << "&tstbuf[ 0 ] at 0x" << (unsigned long) &tstbuf[ 0 ];
	cout << ", " << "&tstbuf[ 99 ] at 0x";
	cout << (unsigned long) &tstbuf[ 99 ] << endl;

	if (&tstbuf[0] > &tstbuf[99])
	{
		cout << "Variables grow down the stack" << endl ;
	}
	else
	{
		cout << "Variables grow up the stack" << endl;
	}

	cout << endl;
}

int main (void)
{
	runStackTest(1);

	return 1;
}


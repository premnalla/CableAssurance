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

#include "txlock.h"
#include "txthread.h"
#include "txthrdmgr.h"

static int G_EXIT_FLAG = 0;
static txNativeLock G_LOCK;
static txNativeEvent G_EXIT_EVENT;
static unsigned long G_TIMERS = 0;

void timerThread (void* time_out)
{
	int y_time = *(int*) time_out;

	while (!G_EXIT_FLAG)
	{
		txNativeThread::yield(y_time);
		G_TIMERS++;

		G_LOCK.acquire();
		printf("TIMEOUT : %d\n", y_time);
		printf("   ID   : %d\n", txNativeThread::id());
		printf("   NAME : %s\n", txNativeThread::name());
		G_LOCK.release();
	}
}

void timerTest (void*)
{
	txNativeThread::yield(10000);

	G_EXIT_FLAG = 1;

	G_LOCK.acquire();
	printf(" NUM OF WAITS PER 10 SECONDS = %d\n", G_TIMERS);
	G_LOCK.release();
}

int main (void)
{
	G_LOCK.acquire();
	printf("BEGIN MAIN LOOP\n");
	G_LOCK.release();

	// this thread will end the test
	txNativeThread::start(timerTest, 0, "timerTest", 255);

	// creating the txNativeThread on the stack
	//     priority:    100
	//     name:       "THRD_MGR"
	//     min_threads: 5
	//     max_threads: 10
	txNativeThreadManager threadMgr(100, "THRD_MGR", 5, 10, 5, 10);

	// integers used for timer values
	int* a = new int(1000);
	int* b = new int(5000);
	int* c = new int(10000);

	// having the txNativeThreadManager start the threads
	threadMgr.start(timerThread, a, "TIMER 1000");
	threadMgr.start(timerThread, b, "TIMER 5000");
	threadMgr.start(timerThread, c, "TIMER 10000");

	////////////////////////////////////
	//
	////////////////////////////////////

	char namebuf[30];

	int i = 101;

	while (!G_EXIT_FLAG)
	{
		sprintf(namebuf, "thread name : %d", i);	

		int* d = new int(i);   // this demo does not free this up
		int* e = new int(511); // this demo does not free this up

		// having the txNativeThreadManager start more threads
		threadMgr.start(timerThread, d, namebuf, i-90);
		threadMgr.start(timerThread, e, "TIMER 511");

		i++;

		// waiting for a second
		txNativeThread::yield(G_EXIT_EVENT, 1000);
	}

	G_LOCK.acquire();
	printf("END MAIN LOOP\n");
	G_LOCK.release();

	delete a; delete b; delete c;

	return 1; 
}


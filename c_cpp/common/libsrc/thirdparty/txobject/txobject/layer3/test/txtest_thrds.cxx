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

// -------- PLEASE READ, BEGIN ---------
//
// THIS EXAMPLE IS IDENTICAL TO THE NATIVE THREADING EXAMPLE txtest_nthrds.cxx
//   - NATIVE THREADING REQUIRES LOCKS AROUND STDOUT.
//   - TXTOOT THREADS DO NOT BUT WERE PUT IN TO DEMONSTRATE THAT
//     BOTH INTERFACES ARE IDENTICAL
//
// -------- PLEASE READ, END ---------

#include <stdio.h>

#include "thread.h"

#include "txlock.h"
#include "txevent.h"
#include "txthread.h"

static txLock G_LOCK;
static txEvent G_EXIT_EVENT;
static int G_EXIT_FLAG = 0;
static unsigned long G_TIMERS = 0;

void timer_thread (void* time_out)
{
	int y_time = *(int*) time_out; delete (int*) time_out;

	while (!G_EXIT_FLAG)
	{
		txThread::yield(y_time);

		G_TIMERS++;

		G_LOCK.acquire();
		printf("TIMEOUT : %d\n", y_time);
		printf("   ID   : %d\n", txThread::id());
		printf("   NAME : %s\n", txThread::name());
		G_LOCK.release();

		#if defined TX_OOT_STAT
		G_LOCK.acquire();
		Thread* t = (Thread*) txThread::id();
		printf("   numYields : ", t->stats.getNumYields());
		printf("   runCPU : ", t->stats.getRunCPU());
		printf("   runTime : ", t->stats.getRunTime());
		printf("   waitTime : ", t->stats.getWaitTime());
		printf("   aliveTime : ", t->stats.getAliveTime());
		printf("   activeTime : ", t->stats.getActiveTime());
		G_LOCK.release();
		#endif
	}
}

void timerTest (void*)
{
	txThread::yield(10000);

	G_EXIT_FLAG = 1;
	G_EXIT_EVENT.trigger();

	G_LOCK.acquire();
	printf(" NUM OF WAITS PER 10 SECONDS = %d\n", G_TIMERS);
	G_LOCK.release();
}

int main (void)
{
	G_LOCK.acquire();
	printf("BEGIN MAIN LOOP\n");
	G_LOCK.release();

	txThread::start(timerTest);

	G_LOCK.acquire();

	//txThread::start(timer_thread, new int(0), "timer_thread");
	//txThread::start(timer_thread, new int(10), "timer_thread");
	txThread::start(timer_thread, new int(100), "timer_thread");
	txThread::start(timer_thread, new int(1000), "timer_thread");
	txThread::start(timer_thread, new int(5000), "timer_thread");
	txThread::start(timer_thread, new int(10000), "timer_thread");
	txThread::start(timer_thread, new int(20000), "timer_thread");

	G_LOCK.release();

	while (!G_EXIT_FLAG)
	{
		txThread::yield(G_EXIT_EVENT, 1000);
	}

	G_LOCK.acquire();
	printf("END MAIN LOOP\n");
	G_LOCK.release();

	return 1;
}


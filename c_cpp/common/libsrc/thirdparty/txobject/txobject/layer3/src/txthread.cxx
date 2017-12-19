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

#include "txlist.h"
#include "txevent.h"
#include "txtimer.h"
#include "txthread.h"
#include "sys/txthrdss.h"

#if defined TX_OOT_SUPPORT
	#include <stdio.h>
	#include "thread.h"
#endif

const int txThread::DEFAULT_PRIORITY = 2;
const int txThread::DEFAULT_STACK_SIZE = 8192;

void txThread::start (TX_THREAD_FUNC func, void* context,
	const char* name, int priority, int stack_size)
{
	if (txThreadSS::isShutDown())
	{
		return;
	}

	if (priority < DEFAULT_PRIORITY)
	{
		priority = DEFAULT_PRIORITY;
	}

	#if defined TX_OOT_SUPPORT
		new Thread (func, context, name, priority, stack_size);
	#endif
}

int txThread::priority (void)
{
	int retval = 0;

	#if defined TX_OOT_SUPPORT
		retval = gCurrentThread->priority();
	#endif

	return retval;
}

int txThread::priority (int priority)
{
	int retval = 0;

	#if defined TX_OOT_SUPPORT
		retval = gCurrentThread->priority();

		gCurrentThread->priority(priority);	
	#endif

	return retval;
}

unsigned long txThread::id (void)
{
	unsigned long retval = 0;

	#if defined TX_OOT_SUPPORT
		retval = (unsigned long) gCurrentThread;
	#endif

	return retval;
}

const char* txThread::name (void)
{
	const char* retval = 0; 

	#if defined TX_OOT_SUPPORT
		retval = gCurrentThread->name();
	#endif

	return retval;
}

const char* txThread::name (const char* name)
{
	const char* retval = 0; 

	#if defined TX_OOT_SUPPORT
		retval = gCurrentThread->name();

		gCurrentThread->name(name);	
	#endif

	return retval;
}

static txList THE_EVENT_REUSE_LIST(TX_AUTODEL_ON);

inline txTimerEnum::RETURN_STATUS timerCallBackFunction (void* self)
{
	txEvent* event = (txEvent*) self;
 
	event->trigger();
 
	return txTimerEnum::STOP;
}

inline int waitForTimeout (txEvent* event, unsigned long time_out)
{
	int reuse_flag = 0;

	if (!event)
	{
		if (!(event = (txEvent*) THE_EVENT_REUSE_LIST.get()))
		{
			event = new txEvent();
		}

		reuse_flag = 1;
	}

	txTimer timer(timerCallBackFunction, event, time_out);

	event->wait();

	if (reuse_flag)
	{
		THE_EVENT_REUSE_LIST.append(event);
	}

	return timer.status();
}

void txThread::yield (void)
{
	#if defined TX_OOT_SUPPORT
        	if (gIoThread == gCurrentThread)
        	{
			fprintf(stderr, "TXOBJECT[error] : yielding in an IO");
			fprintf(stderr, " or TIMER callback is not allowed\n");
			fflush(stderr); TX_CRASH;
        	}
	#endif

	waitForTimeout(0, 0);
}

void txThread::yield (unsigned long time_out)
{
	#if defined TX_OOT_SUPPORT
        	if (gIoThread == gCurrentThread)
        	{
			fprintf(stderr, "TXOBJECT[error] : yielding in an IO");
			fprintf(stderr, " or TIMER callback is not allowed\n");
			fflush(stderr); TX_CRASH;
        	}
	#endif

	waitForTimeout(0, time_out);
}

void txThread::yield (txEvent& event)
{
	#if defined TX_OOT_SUPPORT
        	if (gIoThread == gCurrentThread)
        	{
			fprintf(stderr, "TXOBJECT[error] : yielding in an IO");
			fprintf(stderr, " or TIMER callback is not allowed\n");
			fflush(stderr); TX_CRASH;
        	}
	#endif

	event.wait();
}

int txThread::yield (txEvent& event, unsigned long time_out)
{
	#if defined TX_OOT_SUPPORT
        	if (gIoThread == gCurrentThread)
        	{
			fprintf(stderr, "TXOBJECT[error] : yielding in an IO");
			fprintf(stderr, " or TIMER callback is not allowed\n");
			fflush(stderr); TX_CRASH;
        	}
	#endif

	return waitForTimeout(&event, time_out);
}

void txThread::schedule (void)
{
	#if defined TX_OOT_SUPPORT
		if (gCurrentThread)
		{
			gCurrentThread->schedule();
		}
	#endif
}

#if defined NATIVE_SUPPORT

#include <stdio.h>

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sched.h>
	#include <stdlib.h>
	#include <signal.h>
	#include <unistd.h>
	#include <pthread.h>
#else
	#include <windows.h>
#endif


#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
class txThreadArgs // Used with pthreads to sim TXOOT threads
{
	public:
		TX_THREAD_FUNC func;
		void* context;

	public:
		txThreadArgs (TX_THREAD_FUNC f, void* c) : func(f), context(c)
		{
		}
};
#endif

const int txNativeThread::DEFAULT_PRIORITY = 2;
const int txNativeThread::DEFAULT_STACK_SIZE = 8192;

int txNativeThread::priority (void)
{
	// need to finish
	return 0;
}

int txNativeThread::priority (int pri)
{
	// need to finish
	return 0;
}

unsigned long txNativeThread::id (void)
{
#if defined TX_WIN
	return GetCurrentThreadId();
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	return (unsigned long) pthread_self();
#endif
}

const char* txNativeThread::name (void)
{
	static char buf[100] = "null";

#if defined TX_WIN
	sprintf(buf, "Windows Native thread : %d", txNativeThread::id());
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	sprintf(buf, "Posix Native thread : %d", txNativeThread::id());
#endif

	return buf;
}

const char* txNativeThread::name (const char* name)
{
	// not applicable
	return name;
}

void txNativeThread::yield (void)
{
#if defined TX_WIN
	Sleep(1);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC

	#if TX_SOL
		static int check = 0;

		if (!check)
		{
			sigignore(SIGALRM); check = 1;
		}
	#endif

	::usleep(1000);
#endif
}

void txNativeThread::yield (unsigned long time)
{
#if defined TX_WIN
	Sleep(time);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC

	#if TX_SOL
		static int check = 0;

		if (!check)
		{
			sigignore(SIGALRM); check = 1;
		}
	#endif

	::usleep(time * 1000);
#endif
}

void txNativeThread::yield (txNativeEvent& event)
{
	event.wait();
}

int txNativeThread::yield (txNativeEvent& event, unsigned long time)
{
	return event.wait(time);
}

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
static void* txCb (txThreadArgs* args) // Used with pthreads to sim TXOOT threads
{
	args->func(args->context);

	delete args; args = 0;

	pthread_t t = (pthread_t) txNativeThread::id();

	pthread_exit(&t);

	return 0;
}
#endif

void txNativeThread::start (TX_THREAD_FUNC func, void* context,
	const char*, int, int)
{
	unsigned long id = 0;

#if defined TX_WIN
	id = (unsigned long)
	CreateThread(0, 0, (LPTHREAD_START_ROUTINE) func, context, 0, 0);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	txThreadArgs* args = new txThreadArgs(func, context); // delete in txCb
	pthread_create((pthread_t*) &id, 0, (void* (*)(void*)) txCb, args);
#endif
} 

#endif // NATIVE_SUPPORT


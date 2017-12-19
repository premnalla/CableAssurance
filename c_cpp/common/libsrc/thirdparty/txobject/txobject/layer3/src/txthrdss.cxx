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

#include "txutil.h"
#include "txsync.h"
#include "txtimer.h"
#include "txthread.h"
#include "sys/txthrdss.h"

#if defined TX_OOT_SUPPORT
	#include "thread.h"
#endif

int txThreadSS::_shut_down_ = 0;

static void initializeTHRDMemory (void)
{
	txThreadSS::startUp();	
}

static void cleanUpTHRDMemory (void)
{
	txThreadSS::shutDown();
}

TX_STATIC_ALLOC_NOTIFY(initializeTHRDMemory,(),initializeTHRDMemory)
TX_STATIC_DEALLOC_NOTIFY(cleanUpTHRDMemory,(),cleanUpTHRDMemory)

#if defined TX_OOT_SUPPORT
	static void _processIOFunc_ (void*)
	{
		while (!txThreadSS::isShutDown())
		{
			txTimer::processExpiredTimers();

			gCurrentThread->schedule();

			txSync::IoAndTime(txTimer::getMinWaitTime());

			gCurrentThread->schedule();
		}
	}

	static void _inittxootThreading_ (void)
	{
		gCurrentThread = new Thread(
			0, 0, "TX_MAIN", 2, 0, RUNNING);

		gIoThread = new Thread(
			_processIOFunc_, 0, "TX_IO", 1, 8192, ACTIVE);
	}
#endif // TX_OOT_SUPPORT

/*
**
** TO JUST USE NATIVE AND NOT TXOOT THREADS, TURN THIS ON AND TURN OFF
** TXOOT THREADS
*/
#ifndef TX_OOT_SUPPORT
#ifdef NATIVE_SUPPORT
	static void _processIOFunc_ (void*)
	{
		while (!txThreadSS::isShutDown())
		{
			txTimer::processExpiredTimers();

			txSync::IoAndTime(txTimer::getMinWaitTime());
		}
	}

	static void _initNativeThreading_ (void)
	{
		txNativeThread::start(_processIOFunc_, 0, "TX_IO");
	}
#endif // NATIVE_SUPPORT
#endif // !TX_OOT_SUPPORT

void txThreadSS::startUp (void)
{
	#if defined TX_OOT_SUPPORT
		_inittxootThreading_();
	#endif

	#ifndef TX_OOT_SUPPORT
	#ifdef NATIVE_SUPPORT
		_initNativeThreading_();
	#endif
	#endif
}

void txThreadSS::shutDown (void)
{
	#if defined TX_OOT_SUPPORT
		gCurrentThread = 0;
	#endif

	_shut_down_ = 1;
}

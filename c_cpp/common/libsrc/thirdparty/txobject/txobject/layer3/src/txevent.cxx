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

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sched.h>
	#include <stdlib.h>
	#include <signal.h>
	#include <unistd.h>
	#include <sys/time.h>
#endif

#include "txevent.h"
#include "txthread.h"

#if defined TX_OOT_SUPPORT
	#include "event.h"
#endif

txEvent::txEvent (const char* name) :
	txThrdBase(name)
{
	#if defined TX_OOT_SUPPORT
		_native_obj = new Event(_name);
	#endif
}

txEvent::~txEvent (void)
{
	if (!_native_obj) return;

	trigger();

	#if defined TX_OOT_SUPPORT
		delete ((Event*) _native_obj); _native_obj = 0;
	#endif
}

void txEvent::trigger (void)
{
	if (!_native_obj) return;

	#if defined TX_OOT_SUPPORT
		((Event*) _native_obj)->trigger();
	#endif
}

int txEvent::wait (unsigned long timeout)
{
	if (!_native_obj) return -1;

	return txThread::yield(*this, timeout);
}

void txEvent::wait (void)
{
	if (!_native_obj) return;

	#if defined TX_OOT_SUPPORT
		((Event*) _native_obj)->wait();
	#endif
}

#if defined NATIVE_SUPPORT

txNativeEvent::txNativeEvent (const char* name) :
	txThrdBase(name)
{
#if defined TX_WIN
	_cond_ = CreateEvent(0, FALSE, FALSE, 0);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_cond_init(&_cond_, 0);
	pthread_mutex_init(&_mutex_, 0);
#endif
}

txNativeEvent::~txNativeEvent (void)
{
#if defined TX_WIN
	CloseHandle(_cond_);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_cond_destroy(&_cond_);
	pthread_mutex_destroy(&_mutex_);
#endif
}

int txNativeEvent::wait (unsigned long time)
{
	int flag = 0;

#if defined TX_WIN
	flag = (int) WaitForSingleObject(_cond_, time);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_mutex_lock(&_mutex_);
	timeval nw;
	timespec ts;

	gettimeofday(&nw, (struct timezone*) 0);

	ts.tv_sec = nw.tv_sec + (time_t) (time * 0.001);
	ts.tv_nsec = (nw.tv_usec * 1000) + (long) (time % 1000) * 1000000;

	flag = (int) pthread_cond_timedwait(&_cond_, &_mutex_, &ts);
	pthread_mutex_unlock(&_mutex_);
#endif

	if (flag) flag = 0; else flag = 1; // mimic TX threads

	return flag;
}

void txNativeEvent::wait (void)
{
#if defined TX_WIN
	WaitForSingleObject(_cond_, INFINITE);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_mutex_lock(&_mutex_);
	pthread_cond_wait(&_cond_, &_mutex_);
	pthread_mutex_unlock(&_mutex_);
#endif
}

void txNativeEvent::trigger (void)
{
#if defined TX_WIN
	SetEvent(_cond_);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_mutex_lock(&_mutex_);
	pthread_cond_signal(&_cond_);
	pthread_mutex_unlock(&_mutex_);
#endif
}

#endif // NATIVE_SUPPORT


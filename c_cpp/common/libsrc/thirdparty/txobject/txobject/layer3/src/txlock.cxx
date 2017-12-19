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
	#include <pthread.h>
#endif

#include "txlock.h"
#include "txevent.h"
#include "txthread.h"

txLock::txLock (const char* name) :
	txThrdBase(name),
	_lock_id_(0),
	_lock_count_(0)
{
	_native_obj = (void*) new txEvent((char*) _name);
}

txLock::~txLock (void)
{
	delete ((txEvent*) _native_obj); _native_obj = 0;

	_lock_id_ = 0;
	_lock_count_ = 0;
}

void txLock::acquire (void)
{
	acquire(txThread::id());
}

void txLock::acquire (unsigned long id)
{
	if (id != _lock_id_)
	{
		while (_lock_id_)
		{
			txThread::yield(*((txEvent*)_native_obj));
		}

		_lock_id_ = id;
	}

	_lock_count_++;
}

void txLock::release (void)
{
	release(txThread::id());
}

void txLock::release (unsigned long id)
{
	if ((_lock_id_ == id) && _lock_count_)
	{
		if (--_lock_count_ == 0)
		{
			_lock_id_ = 0;
			((txEvent*) _native_obj)->trigger();
		}
	}
}

#if defined NATIVE_SUPPORT

txNativeLock::txNativeLock (const char* name) :
	txThrdBase(name),
	_lock_id_(0),
	_lock_count_(0)
{
#if defined TX_WIN
	_mutex_ = CreateMutex(0, FALSE, 0);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_mutex_init(&_mutex_, 0);
#endif
}

txNativeLock::~txNativeLock (void)
{
#if defined TX_WIN
	CloseHandle(_mutex_);
#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	pthread_mutex_destroy(&_mutex_);
#endif
}

void txNativeLock::acquire (void)
{
	acquire(txNativeThread::id());
}

void txNativeLock::acquire (unsigned long id)
{
	if (id != _lock_id_)
	{
		#if defined TX_WIN
			WaitForSingleObject(_mutex_, INFINITE);
		#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			pthread_mutex_lock(&_mutex_);
		#endif

		_lock_id_ = id;
	}

	_lock_count_++;
}

void txNativeLock::release (void)
{
	release(txNativeThread::id());
}

void txNativeLock::release (unsigned long id)
{
	if ((_lock_id_ == id) && _lock_count_)
	{
		if (--_lock_count_ == 0)
		{
			_lock_id_ = 0;
		#if defined TX_WIN
			ReleaseMutex(_mutex_);
		#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			pthread_mutex_unlock(&_mutex_);
		#endif
		}
	}
}

#endif // NATIVE_SUPPORT


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

#if !defined ( __TXLOCK_H__ )
#define __TXLOCK_H__

#include "sys/txthrdbase.h"

class txLock : public txThrdBase
{
	private:
		unsigned long _lock_id_;
		unsigned long _lock_count_;

		txLock (const txLock&);
		txLock& operator= (const txLock&);

	public:
		txLock (const char* name = "default");
		~txLock (void);

		void acquire (void);
		void release (void);

		void acquire (unsigned long id);
		void release (unsigned long id);

		unsigned long locked (void) { return _lock_id_; }
};

#if defined NATIVE_SUPPORT

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <pthread.h>
#else
	#include <windows.h>
#endif

class txNativeLock : public txThrdBase
{
	private:
		unsigned long _lock_id_;
		unsigned long _lock_count_;

		#if defined TX_WIN
			HANDLE _mutex_;
		#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			pthread_mutex_t _mutex_;
		#endif

	public:
		txNativeLock (const char* name = "default");

		~txNativeLock (void);

		void acquire (void);
		void release (void);

		void acquire (unsigned long id);
		void release (unsigned long id);

		unsigned long locked (void) { return _lock_id_; }
};

#endif // NATIVE_SUPPORT

#endif // __TXLOCK_H__

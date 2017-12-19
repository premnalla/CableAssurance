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

#if !defined ( __TXEVENT_H__ )
#define __TXEVENT_H__

#include "sys/txthrdbase.h"

class txEvent : public txThrdBase
{
	private:
		txEvent (const txEvent&);
		txEvent& operator= (const txEvent&);

	public:
		txEvent (const char* name = "default");
		~txEvent (void);

		int wait (unsigned long timeout);
		void wait (void);

		void trigger (void);
};

#if defined NATIVE_SUPPORT

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <pthread.h>
#else
	#include <windows.h>
#endif

class txNativeEvent : public txThrdBase
{
	private:
		#if defined TX_WIN
			HANDLE _cond_;
		#elif defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			pthread_cond_t _cond_;
			pthread_mutex_t _mutex_;
		#endif

		txNativeEvent (const txNativeEvent&);
		txNativeEvent& operator= (const txNativeEvent&);

	public:
		txNativeEvent (const char* name = "default");

		~txNativeEvent (void);

		int wait (unsigned long time);
		void wait (void);

		void trigger (void);
};

#endif // NATIVE_SUPPORT

#endif // __TXEVENT_H__

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

#if !defined ( __TXEVENTQ_H__ )
#define __TXEVENTQ_H__

#include "txqueue.h"
#include "txevent.h"
#include "sys/txthrdbase.h"

class txEventQueue : public txThrdBase
{
	private:
		int _low_;
		int _high_;
		txQueue* _queue_;
		txEvent _put_event_;
		txEvent _get_event_;

	public:
		txEventQueue (int high = 0, int low = 0);
		txEventQueue (txQueue* queue, int high = 0, int low = 0);

		~txEventQueue (void);

		int put (void* message, unsigned long time = 0);
		int put (txQueue* queue);

		int get (void*& message, unsigned long time = 0);
		int get (txQueue* queue, int num = 0);

		int registerQueue (txQueue* queue);
		int unregisterQueue (txQueue*& queue);

		int entries (void);

		void flush (void);
};

#if defined NATIVE_SUPPORT

class txNativeEventQueue : public txThrdBase
{
	private:
		int _low_;
		int _high_;
		txQueue* _queue_;
		txNativeEvent _put_event_;
		txNativeEvent _get_event_;

	public:
		txNativeEventQueue (int high = 0, int low = 0);
		txNativeEventQueue (txQueue* queue, int high = 0, int low = 0);

		~txNativeEventQueue (void);

		int put (void* message, unsigned long time = 0);
		int put (txQueue* queue);

		int get (void*& message, unsigned long time = 0);
		int get (txQueue* queue, int num = 0);

		int registerQueue (txQueue* queue);
		int unregisterQueue (txQueue*& queue);

		int entries (void);

		void flush (void);
};

#endif // NATIVE_SUPPORT

#endif // __TXEVENTQ_H__

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

#if !defined ( __TXTHRDMGR_H__ )
#define __TXTHRDMGR_H__

#include "txevent.h"
#include "txeventq.h"
#include "txthread.h"
#include "sys/txthrdbase.h"

class txWorkNode;

class txThreadManager : public txThrdBase
{
	private:
		int _stack_;
		int _priority_;
		int _work_nodes_;
		int _min_threads_;
		int _max_threads_;
		int _current_threads_;
		int _do_task_exit_flag_;

		txQueue _eventQueue_;
		txEventQueue _workQueue_;

	private:
		inline void _addWork_ (txWorkNode* worknode);
		inline txWorkNode* _getWork_ (void);
		static void _doWorkThread_ (void*);

		txThreadManager (const txThreadManager&);
		txThreadManager& operator= (const txThreadManager&);

	public:
		txThreadManager (
			int priority,
			const char* name = "default",
			int min_threads = 2,
			int max_threads = 5,
			int low_water_mark = 0,
			int high_water_mark = 0);

		~txThreadManager (void);

		int priority (void);
		int numberOfThreads (void);
		int numberOfWorkNodes (void);
		int numberOfActiveWorkNodes (void);

		void start (
			TX_THREAD_FUNC func,
			void* context = 0,
			const char* name = "default",
			int priority = txThread::DEFAULT_PRIORITY,
			int stack = txThread::DEFAULT_STACK_SIZE);
};

#if defined NATIVE_SUPPORT

class txNativeThreadManager : public txThrdBase
{
	private:
		int _stack_;
		int _priority_;
		int _work_nodes_;
		int _min_threads_;
		int _max_threads_;
		int _current_threads_;
		int _do_task_exit_flag_;

		txQueue _eventQueue_;
		txNativeEventQueue _workQueue_;

	private:
		inline void _addWork_ (txWorkNode* worknode);
		inline txWorkNode* _getWork_ (void);
		static void _doWorkThread_ (void*);

		txNativeThreadManager (const txThreadManager&);
		txNativeThreadManager&operator=(const txNativeThreadManager&);

	public:
		txNativeThreadManager (
			int priority,
			const char* name = "default",
			int min_threads = 2,
			int max_threads = 5,
			int low_water_mark = 0,
			int high_water_mark = 0);

		~txNativeThreadManager (void);

		int priority (void);
		int numberOfThreads (void);
		int numberOfWorkNodes (void);
		int numberOfActiveWorkNodes (void);

		void start (
			TX_THREAD_FUNC func,
			void* context = 0,
			const char* name = "default",
			int priority = txNativeThread::DEFAULT_PRIORITY,
			int stack = txNativeThread::DEFAULT_STACK_SIZE);
};

#endif // NATIVE_SUPPORT

#endif // __TXTHRDMGR_H__

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
 
#if !defined ( __THREAD_H__ )
#define __THREAD_H__

#include <setjmp.h>
#include <stdlib.h>

#include "txobject.h"

#if defined TX_OOT_STAT
	#include "thrdstats.h"
#endif

class Event;
class ThrdPList;

typedef void (*THRD_FUNC_PTR) (void*);

#define THRD_PRIORITY     5        // default thread priority
#define THRD_STACK_SIZE   2048L    // 2048 words per stack frame

enum ThrdState
{
	ZOMBIE   = 0,	// In the process of being killed.
	RUNNING  = 1,	// Currently running.
	ACTIVE   = 2,	// Can be scheduled to run.
	WAITING  = 3,	// Waiting on an event or a timer.
	SLEEPING = 4,	// Waiting for an unspecified activation.
	DEAD     = 5	// STATIC threads end up DEAD.
};

enum ThrdMode
{
	DYNAMIC,	// Thread was created using new.
	STATIC		// Thread was created in the data section.
};

class Thread : public txObject
{
	private:
		int _priority_;
		ThrdMode _mode_;
		ThrdState _state_;
		const char* _name_;

		#if defined THREAD_DEBUG
			unsigned long* _end_stack_;
		#endif

		void* _cntxt_;
		THRD_FUNC_PTR _func_;
		unsigned long _size_;

		#if !defined FIBER_SUPPORT
			jmp_buf* _current_cntxt_;
			unsigned long* _alloc_point_;

			static unsigned long _fp_;
		#endif

		Event* _wait_event_;

		static void* _new_thread_;
		static txList* _inact_threads_;
		static ThrdPList* _act_threads_;

	private:
		inline void _cleanUp_ (void);
		inline void _removeContext_ (void);
		inline ThrdState _changeStateTo_ (ThrdState state);

		static void _deleteInactiveThreads_ (void);
		int _allocContext_ (unsigned long stack_size);

	public:
		Thread (
			THRD_FUNC_PTR func,
			void* context = 0,
			const char* name = 0,
			int priority = THRD_PRIORITY,
			unsigned long size = THRD_STACK_SIZE,
			ThrdState state = ACTIVE);

		~Thread (void);

		void *operator new (size_t size);
		void operator delete (void* t);

		const char* name (void) const { return _name_; };
		void name (const char* name) { _name_ = name; };

		ThrdState state (void) const { return _state_; };
		ThrdMode mode (void) const { return _mode_; };

		int priority (void) const { return _priority_; };
		void priority (int pri) { _priority_ = pri; };

		void activate (void);
		void schedule (void);
		void sleep (void);
		void wait (void);
		void kill (void);

		#if defined TX_OOT_STAT
			ThreadStats stats;
		#endif

	friend class ThrdPList;
	friend class ThrdMgr;
	friend class Event;
};

extern Thread* gCurrentThread;
extern Thread* gIoThread;

#endif // __THREAD_H__

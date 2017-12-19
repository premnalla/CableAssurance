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
 
#include <stdio.h>
#include <setjmp.h>
#include <string.h>

#include "event.h"
#include "thread.h"
#include "thrdmgr.h"
#include "thrdplist.h"

#if defined TX_MAC
	#define JB_SP_INDEX 0
#endif

#if defined TX_HP || TX_SOL
	#define JB_SP_INDEX 1
#endif

#if defined TX_DEC
	#define JB_SP_INDEX 32
#endif

#if defined TX_SUNOS || TX_SGI
	#define JB_SP_INDEX 2
#endif

#if defined TX_WIN || TX_LINUX
	#define JB_SP_INDEX 4
#endif

static void initializeThreadStack (unsigned long* thread_stack_end, 
	unsigned long *current_stack_end, int stack_frame_size)
{
	#if defined TX_HP
		memset(thread_stack_end, 0, stack_frame_size);
	#else
		memcpy(thread_stack_end, current_stack_end, stack_frame_size);
	#endif
}

static unsigned long* getThreadStackEnd (unsigned long* stack_base,
	unsigned long stack_size, int frame_size)
{
	#if defined TX_HP
		return (unsigned long*) (stack_base + frame_size);
	#else
		return (unsigned long*) (stack_base + stack_size - frame_size);
	#endif
}

static int getStackFrameSize (unsigned long stack_ptr, jmp_buf* thread_ptr)
{
	unsigned long* ptr = (unsigned long*) thread_ptr;
	return abs((int) (stack_ptr - ptr[JB_SP_INDEX]));
}

static unsigned long* getJmpBufStackPointer (jmp_buf* buf) // also in TEST_JMP
{
	unsigned long* ptr = (unsigned long*) *buf;
	return (unsigned long*) ptr[JB_SP_INDEX];
} 

static void setJmpBufStackPointer (jmp_buf* buf, unsigned long* new_stack_ptr)
{
	unsigned long* ptr = (unsigned long*) *buf;
	ptr[JB_SP_INDEX] = (int) new_stack_ptr;
}

// BEGIN : NtFiber Support

#if defined FIBER_SUPPORT

#define STRICT
#define _WIN32_WINNT 0x0400

#include <windows.h>

static void executeFiberFunction (void* fiber);

class NtFiber
{
	private:
		void* _id_;
		void* _args_;
		long _size_;
		Thread* _thrd_;
		void (*_func_)(void*);
		static void* _main_id_;

	public:
		NtFiber (Thread* t, long s, void (*f)(void*), void* a) :
			_thrd_(t), _size_(s), _func_(f), _args_(a), _id_(0)
		{
		}

		~NtFiber (void)
		{
			if (_id_)
			{
				DeleteFiber(_id_);
				_id_ = 0;
			}
		}

		void executeFunction (void)
		{
			if (_func_)
			{
				_func_(_args_);
				_thrd_->kill();
			}
		}

		void switchFiber (NtFiber* fiber)
		{
			if (!_main_id_)
			{
				_main_id_ = ConvertThreadToFiber(0);
				fiber->_id_ = _main_id_;
			}

			if (!_id_)
			{
				_id_ = CreateFiber(_size_,
					(LPFIBER_START_ROUTINE)
						executeFiberFunction, this);
			}

			if (!fiber || fiber->_id_ != _id_)
			{
				SwitchToFiber(_id_);
			}
		}
};

void* NtFiber::_main_id_ = 0;

static void executeFiberFunction (void* fiber)
{
	((NtFiber*) fiber)->executeFunction();
}

#endif // FIBER_SUPPORT

// END : NtFiber Support

#define THRD_X_CODE 2

//
// GLOBAL at file scope
//
Thread* gCurrentThread = 0;
Thread* gIoThread = 0;

void* Thread::_new_thread_ = 0;

#if !defined FIBER_SUPPORT
	unsigned long Thread::_fp_ = 0;
#endif

txList* Thread::_inact_threads_ = 0;
ThrdPList* Thread::_act_threads_ = 0;

void* Thread::operator new (size_t size)
{
	_new_thread_ = new unsigned char[size];
	return _new_thread_;
}

void Thread::operator delete (void* p)
{
	Thread* t = (Thread*) p;

	t->_cleanUp_();
	t->_changeStateTo_(ZOMBIE);

	_inact_threads_->append((txObject*) t);

	if (t == gCurrentThread)
	{
		t->schedule();
	}
}

Thread::Thread (THRD_FUNC_PTR func, void* context, const char* name,
	int priority, unsigned long stack_size, ThrdState state) :
	_name_(name),
	_func_(func),
	_wait_event_(0),
	_state_(ZOMBIE),
	_cntxt_(context),
#if !defined FIBER_SUPPORT
	_alloc_point_(0),
	_current_cntxt_(0),
#endif
	_size_(stack_size),
	_priority_(priority)
{
	if (this == _new_thread_)
	{
		_mode_ = DYNAMIC;
	}
	else
	{
		_mode_ = STATIC;
	}

	if (_act_threads_ == 0)
	{
		_inact_threads_ = new txList();
		_act_threads_ = new ThrdPList();
	}

	#if !defined FIBER_SUPPORT
		if (_fp_ == 0)
		{
			jmp_buf tmp; setjmp(tmp);
			_fp_ = (unsigned long) getJmpBufStackPointer(&tmp);
		}
	#endif

	if (!this) return; // system shutting down.

	if (_allocContext_(_size_))
	{
		_state_ = state;
	}

	if (_state_ == RUNNING)
	{
		gCurrentThread = this;
	}

	if (_priority_ > 256)
	{
		_priority_ = 256;
	}

	if (_state_ == ACTIVE)
	{
		_act_threads_->put(this);
	}

#if defined TX_OOT_DEBUG
	ThrdMgr::add(this);
#endif
}

Thread::~Thread (void)
{
#if defined TX_OOT_DEBUG
	ThrdMgr::remove(this);
#endif
}

ThrdState Thread::_changeStateTo_ (ThrdState state)
{
	#if defined TX_OOT_STAT
		stats._startIntervalTime_();

		if (state == RUNNING)
		{
			stats._startIntervalCPU_();
		}	
	#endif

	ThrdState old_state = _state_;

	_state_ = state;

	return old_state;
}

void Thread::_removeContext_ (void)
{
	_cleanUp_();

	#if !defined FIBER_SUPPORT
		delete [] _alloc_point_; _alloc_point_ = 0;
		delete [] _current_cntxt_; _current_cntxt_ = 0;
	#else
		delete (NtFiber*) _cntxt_; _cntxt_ = 0;
	#endif
}

void Thread::_cleanUp_ (void)
{
	if (_wait_event_)
	{
		_wait_event_->_threads_waiting_.removeReference(this);
		_wait_event_ = 0;
	}
}

void Thread::_deleteInactiveThreads_ (void)
{
	Thread* t;

	while (t = (Thread*) _inact_threads_->get())
	{
		t->_removeContext_();

		if (t->_mode_ == DYNAMIC)
		{
			delete (Thread*) t;
		}
	}
}

int Thread::_allocContext_ (unsigned long stack_size)
{
	#if !defined FIBER_SUPPORT
		_alloc_point_ = 0;
		_current_cntxt_ = 0;

		if (!(_current_cntxt_ = (jmp_buf*) new jmp_buf))
		{
			return 0;
		}

		memset(_current_cntxt_, 0, sizeof(jmp_buf));

		if (!stack_size)
		{
			return 1;
		}

		if (!(_alloc_point_ = new unsigned long [stack_size]))
		{
			delete [] _current_cntxt_; _current_cntxt_ = 0;
			return 0;
		}

		if (setjmp(*(_current_cntxt_)))
		{
			_deleteInactiveThreads_();

			gCurrentThread->_func_(gCurrentThread->_cntxt_);
			gCurrentThread->kill();
		}

		if (!_current_cntxt_) return 0;

		static int fp = getStackFrameSize(_fp_, _current_cntxt_);

		unsigned long* s = getThreadStackEnd(_alloc_point_, _size_, fp);
		unsigned long* e = getJmpBufStackPointer(_current_cntxt_);

		initializeThreadStack(s, e, fp);

		setJmpBufStackPointer(_current_cntxt_, s);

		#if defined THREAD_DEBUG
			#if defined TX_HP
				int dir = 1;
			#else
				int dir = -1;
			#endif

			this->_end_stack_ = s - (fp * dir) + (_size_ * dir);

			unsigned long* walk_stack = s;

			do
			{
				*walk_stack = 0xfadedbed;
				walk_stack += dir;
			}
			while (walk_stack != this->_end_stack_ + dir);

			*(this->_end_stack_) = 0xabaddeed;
		#endif
	#else
		_cntxt_ = new NtFiber(this, _size_, _func_, _cntxt_);
	#endif

	return 1;
}

void Thread::activate (void)
{
	if (_state_ == WAITING || _state_ == SLEEPING)
	{
		#if defined TX_OOT_STAT
			stats._waitTime_ += stats._getIntervalTime_();
		#endif

		_changeStateTo_(ACTIVE);

		_act_threads_->put(this);
	}
}

void Thread::schedule (void)
{
	if (gCurrentThread == this)
	{
		#if defined THREAD_DEBUG
			#if defined TX_HP
				int dir = -1;
			#else
				int dir = 1;
			#endif

			char* p;
			float value = 1;
			double percentage = 0;
			unsigned long* walk_stack;

			if (this->_end_stack_)
			{
				int i = 0;
				walk_stack = this->_end_stack_; walk_stack += 1;

				for (; *walk_stack==0xfadedbed; i++)
				{
					walk_stack += dir;
				}

				if (i && _size_)
				{
					i = _size_ - i;
					percentage = (float) i / (float) _size_;
				}
			}

			if (p = getenv("T_STACK_PERCENT"))
			{
				value = atof(p);
			}

			if (percentage > (value * 0.01))
			{
				printf("\nTOBJECT THREAD STACK INFO. : \n");
				printf("      name : %s\n", name());
				printf("      this : 0x%x\n", (void*) this);
				printf("      func : 0x%x\n", (void*) _func_);
				printf("      stack : 0x%x\n", (void*) _size_);
				printf("      event : ");

				if (_wait_event_)
				{
					printf("%s\n", _wait_event_->name());
				}
				else
				{
					printf("NULL\n");
				}

				printf("      percentage : %f\n\n", percentage);

				fflush(stdout); abort();
			}
		#endif

		if (_state_ == RUNNING)
		{
			#if defined TX_OOT_STAT
				stats._runTime_ += stats._getIntervalTime_();
				stats._runCPU_ += stats._getIntervalCPU_();
			#endif

			gCurrentThread->_changeStateTo_(ACTIVE);
			_act_threads_->put(gCurrentThread);
		}

		if (_state_ != ZOMBIE)
		{
			#if !defined FIBER_SUPPORT
				if (setjmp(*_current_cntxt_))
				{
					_deleteInactiveThreads_();
					return;
				}
			#else
				_deleteInactiveThreads_();
			#endif
		}

		#if !defined FIBER_SUPPORT
			while (gCurrentThread = _act_threads_->get())
			{
				if (gCurrentThread->_current_cntxt_)
				{
					break;
				}
			}
		#else
			Thread* prev = gCurrentThread;
			gCurrentThread = _act_threads_->get();
		#endif

		#if defined TX_OOT_STAT
			stats._activeTime_ += stats._getIntervalTime_();
		#endif

		gCurrentThread->_changeStateTo_(RUNNING);

		#if !defined FIBER_SUPPORT
			longjmp(*(gCurrentThread->_current_cntxt_),THRD_X_CODE);
		#else
			((NtFiber*) gCurrentThread->_cntxt_)->switchFiber(
				(NtFiber*) prev->_cntxt_);
		#endif
	}
}

void Thread::sleep (void)
{
	if (_state_ == RUNNING || _state_ == ACTIVE)
	{
		#if defined TX_OOT_STAT
			if (_state_ == ACTIVE)
			{
				stats._activeTime_ += stats._getIntervalTime_();
			}
			else
			{
				stats._runTime_ += stats._getIntervalTime_();
				stats._runCPU_ += stats._getIntervalCPU_();
			}
		#endif

		_changeStateTo_(SLEEPING);

		if (gCurrentThread == this)
		{
			schedule();
		}
	}			  
}

void Thread::wait (void)
{
	if (_state_ == RUNNING)
	{
		#if defined TX_OOT_STAT
			stats._runTime_ += stats._getIntervalTime_();
			stats._runCPU_ += stats._getIntervalCPU_();

			stats._numYields_++;
		#endif

		_changeStateTo_(WAITING);

		schedule();
	}
}

void Thread::kill (void)
{
	delete this;
}


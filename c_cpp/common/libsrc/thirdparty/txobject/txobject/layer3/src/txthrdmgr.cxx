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
#include "txthrdmgr.h"

class txWorkNode : public txObject
{
	public:
		TX_THREAD_FUNC func;
		const char* name;
		void* context;
		int priority;

		txWorkNode (TX_THREAD_FUNC f, void* c, const char* n, int p) :
			func(f), context(c), name(n), priority(p)
		{
		}

		~txWorkNode (void)
		{
		}

		void reset (TX_THREAD_FUNC f, void* c, const char* n, int p)
		{
			func = f; context = c; name = n; priority = p;
		}
};

static txList THE_WORKNODE_REUSE_LIST(TX_AUTODEL_ON);

txThreadManager::txThreadManager (int priority, const char* name,
	int min, int max, int low, int high) :

	txThrdBase(name),
	_stack_(0),
	_work_nodes_(0),
	_min_threads_(min),
	_max_threads_(max),
	_current_threads_(0),
	_priority_(priority),
	_do_task_exit_flag_(0),
	_workQueue_(low, high)
{
}

void txThreadManager::_addWork_ (txWorkNode* worknode)
{
	txEvent* e;

	_workQueue_.put(worknode);

	if (e = (txEvent*) _eventQueue_.get())
	{
		e->trigger();
	}
	else if ((_current_threads_ < _max_threads_) || !_max_threads_)
	{
		_current_threads_++;

		txThread::start(
			txThreadManager::_doWorkThread_,
			this, _name, _priority_, _stack_);
	}
}

txWorkNode* txThreadManager::_getWork_ (void)
{
	txWorkNode* work = 0;

	if (_workQueue_.entries())
	{
		_workQueue_.get((void*&) work);
	}

	return work;
}

txThreadManager::~txThreadManager (void)
{
	txEvent* e;
	txWorkNode* node;
	txQueue* queue;

	_do_task_exit_flag_ = 1;

	_workQueue_.unregisterQueue(queue);

	if (queue)
	{
		while (node = (txWorkNode*) queue->get())
		{
			delete node; node = 0;
		}

		delete queue; queue = 0;
	}

	while (e = (txEvent*) _eventQueue_.get())
	{
		e->trigger();
	}
}

void txThreadManager::_doWorkThread_ (void* obj)
{
	int nt;
	int nw;
	txWorkNode* work;
	txEvent e("_doWorkThread_");
	txThreadManager* self = (txThreadManager*) obj; 

	self->_work_nodes_++;

	while (!self->_do_task_exit_flag_)
	{
		if (work = self->_getWork_())
		{
			txThread::name(work->name);
			txThread::priority(work->priority);

			work->func(work->context);

			THE_WORKNODE_REUSE_LIST.append(work);

			txThread::name(self->_name);
			txThread::priority(self->_priority_);
			txThread::yield();
		}
		else
		{
			nt = self->numberOfThreads();
			nw = self->numberOfWorkNodes();

			if ((nt > self->_min_threads_) && (nw < nt))
			{
				break;
			}

			self->_eventQueue_.append(&e);

			txThread::yield(e);
		}
	}

	self->_current_threads_--;
	self->_work_nodes_--;
}

int txThreadManager::priority (void)
{
	return _priority_;
}

int txThreadManager::numberOfThreads (void)
{
	return _current_threads_;
}

int txThreadManager::numberOfWorkNodes (void)
{
	return _workQueue_.entries();
}

int txThreadManager::numberOfActiveWorkNodes (void)
{
	return _work_nodes_;
}

void txThreadManager::start (TX_THREAD_FUNC func,
	void* context, const char* name, int priority, int stack)
{
	if (!_do_task_exit_flag_)
	{
		if (stack > _stack_)
		{
			_stack_ = stack;
		}

		txWorkNode* work;

		if (!(work = (txWorkNode*) THE_WORKNODE_REUSE_LIST.get()))
		{
			work = new txWorkNode(func, context, name, priority);
		}
		else
		{
			work->reset(func, context, name, priority);
		}

		_addWork_(work);
	}
}

#if defined NATIVE_SUPPORT

txNativeThreadManager::txNativeThreadManager (int priority, const char* name,
	int min, int max, int low, int high) :

	txThrdBase(name),
	_stack_(0),
	_work_nodes_(0),
	_min_threads_(min),
	_max_threads_(max),
	_current_threads_(0),
	_priority_(priority),
	_do_task_exit_flag_(0),
	_workQueue_(low, high)
{
}

void txNativeThreadManager::_addWork_ (txWorkNode* worknode)
{
	txNativeEvent* e;

	_workQueue_.put(worknode);

	if (e = (txNativeEvent*) _eventQueue_.get())
	{
		e->trigger();
	}
	else if ((_current_threads_ < _max_threads_) || !_max_threads_)
	{
		_current_threads_++;

		txNativeThread::start(
			txNativeThreadManager::_doWorkThread_,
			this, _name, _priority_, _stack_);
	}
}

txWorkNode* txNativeThreadManager::_getWork_ (void)
{
	txWorkNode* work = 0;

	if (_workQueue_.entries())
	{
		_workQueue_.get((void*&) work);
	}

	return work;
}

txNativeThreadManager::~txNativeThreadManager (void)
{
	txNativeEvent* e;
	txWorkNode* node;
	txQueue* queue;

	_do_task_exit_flag_ = 1;

	_workQueue_.unregisterQueue(queue);

	if (queue)
	{
		while (node = (txWorkNode*) queue->get())
		{
			delete node; node = 0;
		}

		delete queue; queue = 0;
	}

	while (e = (txNativeEvent*) _eventQueue_.get())
	{
		e->trigger();
	}
}

void txNativeThreadManager::_doWorkThread_ (void* obj)
{
	int nt;
	int nw;
	txWorkNode* work;
	txNativeEvent e("_doWorkThread_");
	txNativeThreadManager* self = (txNativeThreadManager*) obj; 

	self->_work_nodes_++;

	while (!self->_do_task_exit_flag_)
	{
		if (work = self->_getWork_())
		{
			txNativeThread::name(work->name);
			txNativeThread::priority(work->priority);

			work->func(work->context);

			THE_WORKNODE_REUSE_LIST.append(work);

			txNativeThread::name(self->_name);
			txNativeThread::priority(self->_priority_);
			txNativeThread::yield();
		}
		else
		{
			nt = self->numberOfThreads();
			nw = self->numberOfWorkNodes();

			if ((nt > self->_min_threads_) && (nw < nt))
			{
				break;
			}

			self->_eventQueue_.append(&e);

			txNativeThread::yield(e);
		}
	}

	self->_current_threads_--;
	self->_work_nodes_--;
}

int txNativeThreadManager::priority (void)
{
	return _priority_;
}

int txNativeThreadManager::numberOfThreads (void)
{
	return _current_threads_;
}

int txNativeThreadManager::numberOfWorkNodes (void)
{
	return _workQueue_.entries();
}

int txNativeThreadManager::numberOfActiveWorkNodes (void)
{
	return _work_nodes_;
}

void txNativeThreadManager::start (TX_THREAD_FUNC func,
	void* context, const char* name, int priority, int stack)
{
	if (!_do_task_exit_flag_)
	{
		if (stack > _stack_)
		{
			_stack_ = stack;
		}

		txWorkNode* work;

		if (!(work = (txWorkNode*) THE_WORKNODE_REUSE_LIST.get()))
		{
			work = new txWorkNode(func, context, name, priority);
		}
		else
		{
			work->reset(func, context, name, priority);
		}

		_addWork_(work);
	}
}

#endif // NATIVE_SUPPORT


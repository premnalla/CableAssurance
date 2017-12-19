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
 
#if defined TX_OOT_DEBUG
#include <fstream.h>

#include "event.h"
#include "thread.h"
#include "thrdMgr.h"
#include "txhashtable.h"

txHashTable* ThrdMgr::_threads_ = 0;

void ThrdMgr::_startup_ (void)
{
	_threads_ = new txHashTable();
}

void ThrdMgr::_shutdown_ (void)
{
	delete _threads_; _threads_ = 0;
}

void ThrdMgr::add (Thread* thread)
{
	if (!_threads_)
	{
		_initialize_();
	}

	_threads_->insert(thread);
}

void ThrdMgr::remove (Thread* thread)
{
	if (_threads_)
	{
		_threads_->remove(thread);

		if (!_threads_.entries())
		{
			_shutdown_();
		}
	}
}

void ThrdMgr::log (void)
{
	Thread* thread;
	static ofstream f(".thread.dump", itx::app);

	if (!_threads_) return;

	f << "ThreadId State Priority EventId ThreadName EventName" << endl;
	f << "-------- ----- -------- ------- ---------- ---------" << endl;

	txHashTableIterator iter(*_threads_);

	while (thread = (Thread*) iter.next())
	{
		f << (void*) thread << " ";

		switch (thread->state())
		{
			case ZOMBIE:
			{
				f << "ZOMBIE ";
				break;
			}
			case RUNNING:
			{
				f << "RUNNING ";
				break;
			}
			case ACTIVE:
			{
				f << "ACTIVE ";
				break;
			}
			case WAITING:
			{
				f << "WAITING ";
				break;
			}
			case SLEEPING:
			{
				f << "SLEEPING ";
				break;
			}
			case DEAD:
			{
				f << "DEAD ";
				break;
			}
		}

		f << thread->priority() << " ";

		if (thread->_wait_event_)
		{
			f << (void*) thread->_wait_event_ << "  ";
		}
		else
		{
			f << (void*) 0 << " ";
		}

		f << thread->name() << " ";

		if (thread->_wait_event_)
		{
			f << thread->_wait_event_->name() << " ";
		}
		else
		{
			f << (void*) 0 << " ";
		}

		f << endl;
	}

	f << endl;
}
#endif /* TX_OOT_DEBUG */


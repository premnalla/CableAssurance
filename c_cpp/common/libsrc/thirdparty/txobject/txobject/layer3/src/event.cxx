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
 
#include "event.h"
#include "thread.h"
 
Event::Event (const char* name) :
	_name_(name)
{
}
 
Event::~Event (void)
{
}

void Event::trigger (void)
{
	Thread* thread;

	while (thread = (Thread*) _threads_waiting_.get())
	{
		thread->_wait_event_ = 0;
		thread->activate();
	}
}

void Event::wait (void)
{
	_threads_waiting_.append((txObject*) gCurrentThread);
	gCurrentThread->_wait_event_ = this;
	gCurrentThread->wait();

}


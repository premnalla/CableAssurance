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

#include "txeventq.h"
#include "txthread.h"

txEventQueue::txEventQueue (int high, int low) :
	_put_event_("txQueue Put"),
	_get_event_("txQueue Get"),
	_high_(high),
	_low_(low)
{
	_queue_ = new txQueue();
}

txEventQueue::txEventQueue (txQueue* queue, int high, int low) :
	_put_event_("txQueue Put"),
	_get_event_("txQueue Get"),
	_queue_(queue),
	_high_(high),
	_low_(low)
{
}

txEventQueue::~txEventQueue (void)
{
	delete _queue_; _queue_ = 0;

	_put_event_.trigger();
	_get_event_.trigger();

	txThread::yield();
}

int txEventQueue::put (void* message, unsigned long time)
{
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	status = 1;

	_queue_->append(message);

	_get_event_.trigger();

	if ( (_queue_ && _high_) && (_queue_->entries() >= _high_) )
	{
		if (time)
		{
			if (txThread::yield(_put_event_, time))
			{
				status = 0;
			}
		}
		else
		{
			txThread::yield(_put_event_);
		}
	}

	return status;
}

int txEventQueue::put (txQueue* queue)
{
	void* msg;
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	status = 1;

	while (msg = queue->get())
	{
		_queue_->append(msg);
	}

	_get_event_.trigger();

	if ( (_queue_ && _high_) && (_queue_->entries() >= _high_) )
	{
		txThread::yield(_put_event_);
	}

	return status;
}

int txEventQueue::get (void*& message, unsigned long time)
{
	int status = -1;

	while (_queue_ && status == -1)
	{
		if (!(message = _queue_->get()))
		{
			if (time)
			{
				if (!txThread::yield(_get_event_, time))
				{
					return 0;
				}
			}
			else
			{
				txThread::yield(_get_event_);
			}

			if (!_queue_)
			{
				return status;
			}
		}
		else
		{
			status = 1;
		}
	}

	if ( (_queue_ && _high_) && (_queue_->entries() <= _low_) )
	{
		_put_event_.trigger();
	}

	return status;
}

int txEventQueue::get (txQueue* queue, int num)
{
	void* msg;
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	if (!num)
	{
		num = _queue_->entries();
	}

	for (int i = 0; i < num; i++)
	{
		while ((msg = _queue_->get()) == 0)
		{
			if (_high_ && _queue_->entries() <= _low_)
			{
				_put_event_.trigger();
			}

			txThread::yield(_get_event_);

			if (!_queue_)
			{
				return status;
			}
		}

		queue->append(msg);

		status = 1;
	}

	if ( (_queue_ && _high_) && (_queue_->entries() <= _low_) )
	{
		_put_event_.trigger();
	}

	return status;
}

int txEventQueue::registerQueue (txQueue* queue)
{
	int status = -1;

	if (!_queue_)
	{
		_queue_ = queue;
		status = 1;
	}

	return status;
}

int txEventQueue::unregisterQueue (txQueue*& queue)
{
	int status = 1;

	queue = _queue_;

	if (!_queue_)
	{
		status = -1;
	}
	else
	{
		_queue_ = 0;
	}

	_put_event_.trigger();
	_get_event_.trigger();

	return status;
}

int txEventQueue::entries (void)
{
	int entries = 0;

	if (!_queue_)
	{
		return entries;
	}

	entries = _queue_->entries();

	return entries; 
}

void txEventQueue::flush (void)
{
	if (!_queue_)
	{
		return;
	}

	_queue_->clear();

	_put_event_.trigger();
	_get_event_.trigger();
}

#if NATIVE_SUPPORT

txNativeEventQueue::txNativeEventQueue (int high, int low) :
	_put_event_("txQueue Put"),
	_get_event_("txQueue Get"),
	_high_(high),
	_low_(low)
{
	_queue_ = new txQueue();
}

txNativeEventQueue::txNativeEventQueue (txQueue* queue, int high, int low) :
	_put_event_("txQueue Put"),
	_get_event_("txQueue Get"),
	_queue_(queue),
	_high_(high),
	_low_(low)
{
}

txNativeEventQueue::~txNativeEventQueue (void)
{
	delete _queue_; _queue_ = 0;

	_put_event_.trigger();
	_get_event_.trigger();

	txNativeThread::yield();
}

int txNativeEventQueue::put (void* message, unsigned long time)
{
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	status = 1;

	_queue_->append(message);

	_get_event_.trigger();

	if ( (_queue_ && _high_) && (_queue_->entries() >= _high_) )
	{
		if (time)
		{
			if (txNativeThread::yield(_put_event_, time))
			{
				status = 0;
			}
		}
		else
		{
			txNativeThread::yield(_put_event_);
		}
	}

	return status;
}

int txNativeEventQueue::put (txQueue* queue)
{
	void* msg;
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	status = 1;

	while (msg = queue->get())
	{
		_queue_->append(msg);
	}

	_get_event_.trigger();

	if ( (_queue_ && _high_) && (_queue_->entries() >= _high_) )
	{
		txNativeThread::yield(_put_event_);
	}

	return status;
}

int txNativeEventQueue::get (void*& message, unsigned long time)
{
	int status = -1;

	while (_queue_ && status == -1)
	{
		if (!(message = _queue_->get()))
		{
			if (time)
			{
				if (!txNativeThread::yield(_get_event_, time))
				{
					return 0;
				}
			}
			else
			{
				txNativeThread::yield(_get_event_);
			}

			if (!_queue_)
			{
				return status;
			}
		}
		else
		{
			status = 1;
		}
	}

	if ( (_queue_ && _high_) && (_queue_->entries() <= _low_) )
	{
		_put_event_.trigger();
	}

	return status;
}

int txNativeEventQueue::get (txQueue* queue, int num)
{
	void* msg;
	int status = -1;

	if (!_queue_)
	{
		return status;
	}

	if (!num)
	{
		num = _queue_->entries();
	}

	for (int i = 0; i < num; i++)
	{
		while ((msg = _queue_->get()) == 0)
		{
			if (_high_ && _queue_->entries() <= _low_)
			{
				_put_event_.trigger();
			}

			txNativeThread::yield(_get_event_);

			if (!_queue_)
			{
				return status;
			}
		}

		queue->append(msg);

		status = 1;
	}

	if ( (_queue_ && _high_) && (_queue_->entries() <= _low_) )
	{
		_put_event_.trigger();
	}

	return status;
}

int txNativeEventQueue::registerQueue (txQueue* queue)
{
	int status = -1;

	if (!_queue_)
	{
		_queue_ = queue;
		status = 1;
	}

	return status;
}

int txNativeEventQueue::unregisterQueue (txQueue*& queue)
{
	int status = 1;

	queue = _queue_;

	if (!_queue_)
	{
		status = -1;
	}
	else
	{
		_queue_ = 0;
	}

	_put_event_.trigger();
	_get_event_.trigger();

	return status;
}

int txNativeEventQueue::entries (void)
{
	int entries = 0;

	if (!_queue_)
	{
		return entries;
	}

	entries = _queue_->entries();

	return entries; 
}

void txNativeEventQueue::flush (void)
{
	if (!_queue_)
	{
		return;
	}

	_queue_->clear();

	_put_event_.trigger();
	_get_event_.trigger();
}

#endif // NATIVE_SUPPORT


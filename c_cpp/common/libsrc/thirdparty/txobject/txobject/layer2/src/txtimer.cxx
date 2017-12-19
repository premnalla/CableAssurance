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

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/time.h>

	static struct timeval TX_CURRENT_TIME;
#endif

#if defined TX_WIN
	#include <sys/timeb.h>

	static struct timeb TX_CURRENT_TIME;
#endif

#include "txlist.h"
#include "txtimer.h"

static txList THE_TIMER_LIST;

static void setHighestMatchingIndex (double time, txTimer* timer)
{
	int lo = 0;
	txTimer* timer_ptr;
	int hi = THE_TIMER_LIST.entries();

	if (!hi || (time <= ((txTimer*) THE_TIMER_LIST.first())->expiryTime()))
	{
		THE_TIMER_LIST.insertAt(0, (txObject*) timer);
		return;
	}

	txListIterator iter(THE_TIMER_LIST);

	for (; timer_ptr = (txTimer*) iter.next(); lo++)
	{
		if (time <= timer_ptr->expiryTime())
		{
			break;
		}	
	}

	THE_TIMER_LIST.insertAt(lo, (txObject*) timer);
}

txTimer::txTimer (void) :
	_interval_(0), _function_(0), _context_(0), _xprtime_(0.0),
	_status_(txTimerEnum::STOPPED)
{
}
	
txTimer::txTimer (TX_TIMER_FUNC function, void* context,
	unsigned long interval) :
	_interval_(interval),
	_function_(function),
	_context_(context),
	_xprtime_(0.0)
{
	_setupTimer_();
}
	
txTimer::~txTimer (void)
{
	clear();
}

void txTimer::_setupTimer_ (void)
{
	_status_ = txTimerEnum::RUNNING;

	_xprtime_ = currentTime() + _interval_;

	setHighestMatchingIndex(_xprtime_, this);
}

void txTimer::_trigger_ (double now)
{
	if (_status_ == txTimerEnum::RUNNING)
	{
		_status_ = txTimerEnum::STOPPED;

		if (_function_(_context_) == txTimerEnum::CONTINUE)
		{
			_status_ = txTimerEnum::RUNNING;

			if (_interval_ == 0)
			{
				_xprtime_ = now + 1;
			}
			else
			{
				while ((_xprtime_ += _interval_) < now);
			}

			setHighestMatchingIndex(_xprtime_, this);
		}
	}
}

void txTimer::trigger (double now)
{
	THE_TIMER_LIST.removeReference((txObject*) this);

	_trigger_(now);
}

void txTimer::reset (TX_TIMER_FUNC function, void* context,
	unsigned long interval)
{
	if (interval)
	{
		clear();

		_interval_ = interval;

		if (function)
		{
			_function_ = function;
		}

		if (context)
		{
			_context_ = context;
		}

		_setupTimer_();
	}
}

void txTimer::clear (void)
{
	if (_status_ == txTimerEnum::RUNNING)
	{
		_status_ = txTimerEnum::STOPPED;
	}

	THE_TIMER_LIST.removeReference((txObject*) this);
}

void txTimer::_syncTimers_ (double btotal)
{
	txTimer* timer;

	unsigned long asecs, amsecs;

	txTimer::currentTime(asecs, amsecs);

	double delta, atotal = (asecs * 1000) + amsecs;  

	if (delta = (double) (btotal - atotal))
	{
		txListIterator iter(THE_TIMER_LIST);

		while (timer = (txTimer*) iter.next())
		{
			timer->_xprtime_ = timer->_xprtime_ - delta;
		}
	}
}

long txTimer::getMinWaitTime (void)
{
	txTimer* t;
	double now;
	long min_time = -1;

	if (THE_TIMER_LIST.entries())
	{
		t = (txTimer*) THE_TIMER_LIST.first();

		now = currentTime();

		if (now > t->expiryTime())
		{
			min_time = 0;
		}
		else
		{
			min_time = (long) (t->expiryTime() - now);

			if ((min_time<0) || (min_time>((signed)t->interval())))
			{
				min_time = 1;

				txListIterator iter(THE_TIMER_LIST);

				while (t = (txTimer*) iter.next())
				{
					t->_xprtime_ = t->interval() + now;
				}
			}
		}
	}
	
	return min_time;
}

long txTimer::processAndGetMinWait (void)
{
	processExpiredTimers();

	return (long) getMinWaitTime();
}

void txTimer::processExpiredTimers (void)
{
	double now = currentTime();

	while (THE_TIMER_LIST.entries() &&
		((txTimer*) THE_TIMER_LIST.first())->expiryTime() <= now)
	{
		((txTimer*) THE_TIMER_LIST.get())->_trigger_(now);
	}
}

int txTimer::currentTimeZone (void)
{
	int current_zone = 0;

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	struct timezone tz_var;

	gettimeofday((struct timeval*) 0, &tz_var);

	current_zone = tz_var.tz_minuteswest;
#endif

#if defined TX_WIN
#endif

	return current_zone;
}

void txTimer::currentTimeZone (int& mw, int& dst)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	struct timezone tz_var;

	gettimeofday((struct timeval*) 0, &tz_var);

	dst = tz_var.tz_dsttime;
	mw = tz_var.tz_minuteswest;
#endif

#if defined TX_WIN
	dst = 0; mw = 0;
#endif
}

double txTimer::currentTime (void)
{
	double current_time;

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	gettimeofday(&TX_CURRENT_TIME, (struct timezone*) 0);

	current_time = TX_CURRENT_TIME.tv_sec * 1000;
	current_time += TX_CURRENT_TIME.tv_usec / 1000;
#endif

#if defined TX_WIN
	ftime(&TX_CURRENT_TIME);

	current_time = TX_CURRENT_TIME.time * 1000;
	current_time += TX_CURRENT_TIME.millitm;
#endif

	return current_time;
}

void txTimer::currentTime (unsigned long &sec, unsigned long &msec)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	gettimeofday(&TX_CURRENT_TIME, (struct timezone*) 0);

	sec = TX_CURRENT_TIME.tv_sec;
	msec = (unsigned long) TX_CURRENT_TIME.tv_usec / 1000;
#endif

#if defined TX_WIN
	ftime(&TX_CURRENT_TIME);

	sec = TX_CURRENT_TIME.time;
	msec = TX_CURRENT_TIME.millitm;
#endif
}

void txTimer::setTimeZone (int mw, int dst)
{
	unsigned long bsecs, bmsecs;

	txTimer::currentTime(bsecs, bmsecs);

	double btotal = (bsecs * 1000) + bmsecs;  

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	struct timezone tz_var;
	tz_var.tz_dsttime = dst;
	tz_var.tz_minuteswest = mw;

	settimeofday((struct timeval*) 0, &tz_var);
#endif

#if defined TX_WIN
#endif

	txTimer::_syncTimers_(btotal);
}

void txTimer::setTimeOfDay (unsigned long secs, unsigned long msecs)
{
	unsigned long bsecs, bmsecs;

	txTimer::currentTime(bsecs, bmsecs);

	double btotal = (bsecs * 1000) + bmsecs;  

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	struct timeval tv_var;

	tv_var.tv_sec = secs;
	tv_var.tv_usec = msecs * 1000;

	settimeofday(&tv_var, (struct timezone*) 0);
#endif

#if defined TX_WIN
#endif

	txTimer::_syncTimers_(btotal);
}

txExtendedTimer::txExtendedTimer (void) :
	_func_(0), _context_(0), _remainder_(0), _count_down_(0)
{
}

txExtendedTimer::txExtendedTimer (TX_TIMER_FUNC func, void* context,
	unsigned long interval) :

	_func_(0), _context_(0), _remainder_(0), _count_down_(0)
{
	reset(func, context, interval);
}

txExtendedTimer::~txExtendedTimer (void)
{
	_timer_.clear();
}

txTimerEnum::RETURN_STATUS txExtendedTimer::_expiredTimerCb_ (void* context)
{
	txExtendedTimer* self = (txExtendedTimer*) context;

	self->_count_down_--;

	if (!self->_count_down_ && self->_remainder_)
	{
		self->_timer_.reset(
			self->_func_, self->_context_, self->_remainder_);

		return txTimerEnum::STOP;
	}
	else if (!self->_count_down_ && !self->_remainder_)
	{
		self->_func_(self->_context_);

		return txTimerEnum::STOP;
	}

	return txTimerEnum::CONTINUE;
}

void txExtendedTimer::reset (TX_TIMER_FUNC func, void* context,
	unsigned long interval)
{
	_count_down_ = 0;

	if (func)
	{
		_func_ = func;
	}

	if (context)
	{
		_context_ = context;
	}

	if ((interval*1000) > 0xF7314000) // 48 days in seconds
	{
		unsigned long value = (interval * 1000) / 0xF7314000;
		_count_down_ = value; value = value * 0xF7314000;
		_remainder_ = (interval * 1000) - value;

		_timer_.reset(_expiredTimerCb_, this, 0xF7314000);
	}
	else
	{
		_timer_.reset(_func_, _context_, interval * 1000);
	}
}


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

#if !defined ( __TXTIMER_H__ )
#define __TXTIMER_H__

class txTimerEnum
{
	public:
		enum STATUS
		{
			STOPPED = 0, RUNNING = 1
		};

		enum RETURN_STATUS
		{
			STOP = 0, CONTINUE = 1
		};
};

typedef txTimerEnum::RETURN_STATUS (*TX_TIMER_FUNC) (void*);

class txTimer // defined by milli-second timer
{
	private:
		double _xprtime_;
		unsigned long _interval_;
		txTimerEnum::STATUS _status_;

		void* _context_;
		TX_TIMER_FUNC _function_;

		inline void _setupTimer_ (void);
		inline void _trigger_ (double now);

		txTimer (const txTimer& obj);
		txTimer& operator= (const txTimer& obj);

	public:
		txTimer (void);

		txTimer (
			TX_TIMER_FUNC func,
			void* context,
			unsigned long interval);

		~txTimer (void);

		double expiryTime (void) const
		{
			return _xprtime_;
		}

		txTimerEnum::STATUS status (void) const
		{
			return _status_;
		}

		void* context (void) const
		{
			return _context_;
		}

		unsigned long interval (void) const
		{
			return _interval_;
		}

		void trigger (double now);

		void clear (void);

		void reset (
			TX_TIMER_FUNC func = 0,
			void* context = 0,
			unsigned long interval = 0);

	private:
		static void _syncTimers_ (double);

	public:
		static long getMinWaitTime (void);

		static long processAndGetMinWait (void);

		static void processExpiredTimers (void);

		static int currentTimeZone (void);

		static double currentTime (void);

		static void currentTimeZone (int& mw, int& dst);

		static void currentTime (unsigned long& s, unsigned long& ms);

		static void setTimeZone (int mw, int dst = 0);

		static void setTimeOfDay (unsigned long sec, unsigned long ms);
};

class txExtendedTimer // defined by second timer
{
	private:
		txTimer _timer_;
		void* _context_;
		TX_TIMER_FUNC _func_;
		unsigned long _remainder_;
		unsigned long _count_down_;

	private:
		static txTimerEnum::RETURN_STATUS _expiredTimerCb_ (void*);

	public:
		txExtendedTimer (void);

		txExtendedTimer (TX_TIMER_FUNC, void*, unsigned long);

		~txExtendedTimer (void);

		const txTimer* timer (void) const
		{
			return &_timer_;
		}

		void* context (void) const
		{
			return _context_;
		}

		unsigned long remainder (void) const
		{
			return _remainder_;
		}

		unsigned long timersToExpire (void) const
		{
			return _count_down_;
		}

		void reset (TX_TIMER_FUNC f=0, void* c=0, unsigned long i=0);
};

#endif // __TXTIMER_H__

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
 
#if defined TX_OOT_STAT
#if !defined ( __THRD_STATS_H__ )
#define __THRD_STATS_H__

class ThreadStats
{
	public: 
		ThreadStats (void);

		long getRunCPU (void);

		double getRunTime (void);

		double getWaitTime (void);

		double getAliveTime (void);

		double getActiveTime (void);

		unsigned long getNumYields (void);

	private:
		double _runTime_;
		double _waitTime_;
		double _activeTime_;
		double _aliveTimeStart_;
		double _intervalTimeStart_;

		long _runCPU_;
		long _intervalCPUStart_;
		unsigned long _numYields_;

	private:
		double _getIntervalTime_ (void);
		void _startIntervalTime_ (void);
		void _startIntervalCPU_ (void);

		long _getIntervalCPU_ (void);

		double _dtime_ (void);

	friend class Thread;
};

#endif // __THRD_STATS_H__
#endif // TX_OOT_STAT

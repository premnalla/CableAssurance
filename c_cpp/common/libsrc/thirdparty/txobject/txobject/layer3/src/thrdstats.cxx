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
#include <time.h>
#include <sys/times.h>
#include <sys/param.h>
#include <sys/types.h>

#include "thrdstats.h"

extern "C" {int gettimeofday(struct timeval*, void*);}

long ThreadStats::getRunCPU (void)
{
	return _getIntervalCPU_() + _runCPU_;
}

double ThreadStats::getRunTime (void)
{
	return _getIntervalTime_() + _runTime_;
}

double ThreadStats::getActiveTime (void)
{
	return _activeTime_;
}

double ThreadStats::getAliveTime (void)
{
	return _dtime_() - _aliveTimeStart_;
}

double ThreadStats::getWaitTime (void)
{
	return _waitTime_;
}

unsigned long ThreadStats::getNumYields (void)
{
	return _numYields_;
}

void ThreadStats::_startIntervalTime_ (void)
{
	_intervalTimeStart_ = _dtime_();
}

double ThreadStats::_getIntervalTime_ (void)
{
	return _dtime_() - _intervalTimeStart_;
}

void ThreadStats::_startIntervalCPU_ (void)
{ 
	tms crtCPU;

	times(&crtCPU);

	_intervalCPUStart_ = crtCPU.tms_utime + crtCPU.tms_stime;
}

long ThreadStats::_getIntervalCPU_ (void)
{
	tms crtCPU;

	times( &crtCPU );

	return crtCPU.tms_utime + crtCPU.tms_stime - _intervalCPUStart_;
}

double ThreadStats::_dtime_ (void)
{
	struct timeval tv;

	gettimeofday(&tv, 0);

	return double(tv.tv_sec) + double(tv.tv_usec) / 1000000;
}

ThreadStats::ThreadStats (void) : 
	_runTime_(0.0), _activeTime_(0.0), _waitTime_(0.0),
	_runCPU_(0), _numYields_(0), _intervalCPUStart_(0)
{
	_intervalTimeStart_ = _aliveTimeStart_ = _dtime_();
}

#endif // TX_OOT_STAT

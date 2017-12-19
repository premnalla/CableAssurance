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

#if !defined ( __TXTHEARD_H__ )
#define __TXTHEARD_H__

class txEvent;

typedef void (*TX_THREAD_FUNC) (void*);

class txThread
{
	public:
		static const int DEFAULT_PRIORITY;
		static const int DEFAULT_STACK_SIZE;

	public:
		static void start (
			TX_THREAD_FUNC func,
			void* context = 0,
			const char* name = "default",
			int priority = DEFAULT_PRIORITY,
			int stack_size = DEFAULT_STACK_SIZE);

		static int priority (void);

		static int priority (int pri);

		static unsigned long id (void);

		static const char* name (void);

		static const char* name (const char* name);

		static void yield (void);

		static void yield (txEvent& event);

		static void yield (unsigned long time);

		static int yield (txEvent& event, unsigned long time);

	public:
		// for experience users
		static void schedule (void);
};

#if defined NATIVE_SUPPORT

class txNativeEvent;

class txNativeThread
{
	public:
		static const int DEFAULT_PRIORITY;
		static const int DEFAULT_STACK_SIZE;

	public:
		static void start (
			TX_THREAD_FUNC func,
			void* context = 0,
			const char* name = "default",
			int priority = DEFAULT_PRIORITY,
			int stack_size = DEFAULT_STACK_SIZE);

		static int priority (void);

		static int priority (int pri);

		static unsigned long id (void);

		static const char* name (void);

		static const char* name (const char* name);

		static void yield (void);

		static void yield (txNativeEvent& event);

		static void yield (unsigned long time);

		static int yield (txNativeEvent& event, unsigned long time);
};

#endif // NATIVE_SUPPORT

#endif // __TXTHREAD_H__

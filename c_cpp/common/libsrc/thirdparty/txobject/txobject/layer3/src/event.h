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
 
#if !defined ( __EVENT_H__ )
#define __EVENT_H__

#include "txlist.h"
#include "txobject.h"

class Event : public txObject
{
	private:
		const char* _name_;
		txList _threads_waiting_;

	public:
		Event (const char* name = 0);
		~Event (void);

		const char* name (void) const { return _name_; };
		void trigger (void);
		void wait (void);

	friend class Thread;
	friend class ThrdMgr;
};

#endif // __EVENT_H__

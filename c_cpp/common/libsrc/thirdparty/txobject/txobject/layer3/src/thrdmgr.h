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
 
#if !defined ( __THRDMGR_H__ )
#define __THRDMGR_H__

#if defined TX_OOT_DEBUG
class Thread;
class txHashTable;

class ThrdMgr
{
	private:
		static txHashTable* _threads_;

	private:
		static void _startup_ (void);
		static void _shutdown_ (void);

	public:
		static void add (Thread* t);
		static void remove (Thread* t);

		static void log (void);
};
#endif

#endif // __THRDMGR_H__

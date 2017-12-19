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
 
#if !defined ( __TXSTATECTMTX_H__ )
#define __TXSTATECTMTX_H__

#include "sys/txstatemtx.h"

class txAbsPeer;
 
class txStateCtMtx : public txStateMatrix
{
	public:
		enum STATE
		{
			STATIC = -1,
			UNKNOWN = 0,
			INITIAL = 1,
			ESTABLISHED = 2,
			LOST = 3,
			MERGE = 4
		};

	public:
		txStateCtMtx (void)
		{
		}

		txStateCtMtx (const txAbsPeer& peer);

		~txStateCtMtx (void)
		{
		}

		int determinate (void) const;

		void set (const txAbsPeer& r, const txAbsPeer& c, STATE state);

		STATE get (const txAbsPeer& r, const txAbsPeer& c) const;
};

#endif // __TXSTATECTMTX_H__

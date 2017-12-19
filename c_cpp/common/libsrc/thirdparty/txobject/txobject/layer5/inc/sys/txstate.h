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
 
#if !defined ( __TXSTATE_H__ )
#define __TXSTATE_H__

#include "txstream.h"

class txAbsPeer;
class txPeerList;

class txState : public txStream
{
	TX_DECLARE_STREAM_TYPE(txState)

	private:
		int *_refcnt_;

	protected:
		int incref (void) { (*_refcnt_)++ ; return *_refcnt_; }
		int decref (void) { (*_refcnt_)-- ; return *_refcnt_; }
		int refcount (void) { return *_refcnt_; }

	public:
		txState (void);

		txState (const txState& obj);

		txState& operator= (const txState& obj);

		~txState (void);

		virtual signed short int state (const txAbsPeer*) const = 0;

		virtual signed short int state (const txPeerList*) const = 0;

		virtual txState* clone (void) = 0;
};

#endif // __TXSTATE_H__

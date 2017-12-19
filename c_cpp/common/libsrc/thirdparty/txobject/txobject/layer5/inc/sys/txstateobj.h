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
 
#if !defined ( __TXSTATEOBJ_H__ )
#define __TXSTATEOBJ_H__

#include "sys/txstate.h"

class txAbsPeer;
class txPeerList;

class txStateObject : public txState
{
	TX_DECLARE_STREAM_TYPE(txStateObject)

	private:
		signed short int _state_;

	public:
		txStateObject (signed short int state = 0);
		txStateObject& operator= (const txStateObject&);

		~txStateObject (void);

		txStateObject (const txStateObject& obj ) : txState(obj)
		{
			_state_ = obj._state_;
		}

		txState* clone (void)
		{
			return new txStateObject(*this);
		}

		void setState (signed short int state)
		{
			_state_ = state;
		}

		signed short int state (const txAbsPeer* peer = 0) const
		{
			return _state_;
		}

		signed short int state (const txPeerList* list) const
		{
			return _state_;
		}

		void storeInners (txOut& out) const;

		void restoreInners (txIn& in);
};

#endif // __TXSTATEOBJ_H__

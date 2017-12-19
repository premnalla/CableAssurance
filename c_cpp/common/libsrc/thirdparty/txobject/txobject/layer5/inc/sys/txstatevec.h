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
 
#if !defined ( __TXSTATEVEC_H__ )
#define __TXSTATEVEC_H__

#include "sys/txstate.h"

class txHashMap;
class txPeerList;

class txStateVector : public txState
{
	TX_DECLARE_STREAM_TYPE(txStateVector)

	private:
		txHashMap* _sv_;

	public:
		txStateVector (int);
		txStateVector (void);
		txStateVector (const txStateVector&);
		txStateVector& operator= (const txStateVector&);

		~txStateVector (void);

		txState* clone (void);

		void set (const txAbsPeer& key, const txState& value);

		void del (const txAbsPeer& key);

		const txState* get (const txAbsPeer& key ) const;

		signed short int state (const txAbsPeer* ex_key = 0) const;

		signed short int state (const txPeerList* ex_keys) const;

		int entries (void) const;

		const txHashMap* getTable (void) const { return _sv_; }

		void storeInners (txOut& out) const;

		void restoreInners (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txStateVector)

#endif // __TXSTATEVEC_H__

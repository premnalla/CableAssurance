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
 
#if !defined ( __TXSTATEMTX_H__ )
#define __TXSTATEMTX_H__

#include "sys/txstate.h"
#include "sys/txstatevec.h"

class txAbsPeer;
class txPeerList;

class txStateMatrix : public txState
{
	private:
		txAbsPeer* _primary_key_;
		txStateVector* _matrix_rows_;

	public:
		txStateMatrix (void);
		txStateMatrix (const txStateMatrix&);
		txStateMatrix& operator= (const txStateMatrix&);

		~txStateMatrix (void);

		txState* clone (void);

		void set (const txAbsPeer&, const txAbsPeer&, const txState&);

		const txState* get (const txAbsPeer&, const txAbsPeer&) const;

		void setRow (const txAbsPeer&, const txStateVector&);

		const txState* getRow (void) const;

		const txState* getRow (const txAbsPeer& row) const;

		const txState* getCol (void) const;

		const txState* getCol (const txAbsPeer& col) const;

		signed short int state (const txAbsPeer* ex_key = 0) const;

		signed short int state (const txPeerList* ex_keys) const;

		signed short int rowState (const txAbsPeer& r,
			const txAbsPeer&) const;

		signed short int rowState (const txAbsPeer& r,
			const txPeerList&) const;

		signed short int colState (const txAbsPeer& c,
			const txAbsPeer&) const;

		signed short int colState (const txAbsPeer& c,
			const txPeerList&) const;

		void setPrimaryKey (const txAbsPeer&);

		const txAbsPeer* getPrimaryKey (void) const;

		void del (const txAbsPeer&) const;

		txStateVector* ensureSquare (const txAbsPeer&);
};

#endif // __TXSTATEMTX_H__

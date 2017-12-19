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

#if !defined ( __TXFASTCOUNT_H__ )
#define __TXFASTCOUNT_H__

#include "txcount.h"

class txFastCount : public txCount
{
	TX_DECLARE_DOBJECT(txFastCount)

	private:
		txUInt32* _FastCount_;

		static txObject* _incFastCount_ (txFastCount* self);

		txFastCount (void);

	public:

		txFastCount (txUInt32* count, txUInt32* max);

		~txFastCount (void);

		void incrementFastCount (void)
		{
			_incFastCount_(this);
		}

		void createNotify (void);

		void destroyNotify (void);

		void attrWriteNotify (
			const char*, const txObject*, const txObject*);
};

#endif // __TXFASTCOUNT_H__

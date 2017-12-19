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

#if !defined ( __TXCOUNT_H__ )
#define __TXCOUNT_H__

#include "txuint32.h"
#include "txdobject.h"

class txCount : public txDObject
{
	TX_DECLARE_DOBJECT(txCount)

	protected:
		txUInt32* _Count_;
		txUInt32* _MaxCount_;

		static txObject* _getCount_ (txCount* self);
		static txObject* _incrementCount_ (txCount* self);
		static txObject* _setCount_ (txCount* self, txIn& in);
		static txObject* _setMaxCount_ (txCount* self, txIn& in);

		txCount (void);

	public:
		txCount (txUInt32* count, txUInt32* max);

		~txCount (void);

		txObject* getCount (void)
		{
			return _getCount_(this);
		}

		void incrementCount (void)
		{
			_incrementCount_(this);
		}

		void createNotify (void);

		void destroyNotify (void);

		void changeOwnerNotify (
			const txAbsPeer&, const txAbsPeer&);

		void attrWriteNotify (
			const char*, const txObject*, const txObject*);
};

#endif // __TXCOUNT_H__

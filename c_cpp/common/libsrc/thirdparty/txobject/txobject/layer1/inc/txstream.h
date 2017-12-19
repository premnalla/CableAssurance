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

#if !defined ( __TXSTREAM_H__ )
#define __TXSTREAM_H__

#include "txobject.h"
#include "txfactory.h"

class txStream : public txObject 
{ 
	TX_DECLARE_STREAM_TYPE(txStream)

	public: 
		txStream (void);
		~txStream (void);

		virtual void stream (txOut& out) const;

		virtual void destream (txIn& in);

		virtual void storeInners (txOut& out) const;

		virtual void restoreInners (txIn& in);
};

TX_DECLARE_STREAM_TYPE_OPER(txStream)

#endif // __TXSTREAM_H__

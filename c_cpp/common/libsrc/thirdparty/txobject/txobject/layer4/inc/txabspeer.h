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

#if !defined ( __TXABSPEER_H__ )
#define __TXABSPEER_H__

#include "txstream.h"

class txAbsPeer : public txStream
{
	TX_DECLARE_STREAM_TYPE(txAbsPeer)

	public:
		enum TYPE
		{
			NONE = 0,
			TCP = 1,
			UDP = 2
		};

	private:
		txAbsPeer (const txAbsPeer&);
		txAbsPeer& operator= (const txAbsPeer&);

	public:
		txAbsPeer (void) {}
		virtual ~txAbsPeer (void) {}

		virtual void setType (TYPE type) = 0;

		virtual TYPE getType (void) const = 0;

		virtual int refCount (void) const = 0;

		virtual void activate (void) = 0;

		virtual void deactivate (void) = 0;

		virtual int isActive (void) const = 0;

		virtual unsigned hash (void) const = 0; 

		virtual const char* getId (void) const = 0;

		virtual txAbsPeer* clone (void) const = 0;

		virtual int equals (const txObject*) const = 0;
};

TX_DECLARE_STREAM_TYPE_OPER(txAbsPeer)

#endif // __TXABSPEER_H__

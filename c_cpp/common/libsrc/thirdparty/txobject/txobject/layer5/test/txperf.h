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

#if !defined ( __TXPERF_H__ )
#define __TXPERF_H__

#include "txuint32.h"
#include "txstring.h"
#include "txdobject.h"

class txPerf : public txDObject
{
	TX_DECLARE_DOBJECT(txPerf)

	private:
		txUInt32* _int_;
		txString* _string_;

		txPerf (void);

	public:
		txPerf (txUInt32*, txString*);
		~txPerf (void);
};

#endif // __TXPERF_H__

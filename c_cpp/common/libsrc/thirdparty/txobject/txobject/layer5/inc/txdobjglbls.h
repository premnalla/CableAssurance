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
 
#if !defined ( __TXDOBJGLBLS_H__ )
#define __TXDOBJGLBLS_H__

#include "txobject.h"
#include "txhashmap.h"

class txDObjGlobals : public txObject
{
	public:
		static const int PEER_TIMEOUT_INTERVAL;
		static const int BASE_THREAD_PRIORITY;
		static const int STATIC_PEERS_FLAG;
		static const char* SYSTEM_SPACE;
		static txHashMap txDObjMgrs;
};

class txDObjError : public txObject
{
	public:
		enum ERROR_CODE
		{
			MERGE,
			INCOMPLETE_SYNC,
			UNRESOLVED_PEER
		};

	private:
		ERROR_CODE _code_;
		txObject* _error_;

	public:

		txDObjError (ERROR_CODE c, txObject* error = 0) :
			_code_(c),
			_error_(error)
		{
		}

		~txDObjError (void)
		{
			delete _error_; _error_ = 0;
		}

		ERROR_CODE code (void)
		{
			return _code_;
		}

		txObject* error (void)
		{
			return _error_;
		}
};

#endif // __TXDOBJGLBLS_H__

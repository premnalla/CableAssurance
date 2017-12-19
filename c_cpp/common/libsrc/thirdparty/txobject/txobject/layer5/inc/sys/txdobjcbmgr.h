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
 
#if !defined ( __TXDOBJCBMGR_H__ )
#define __TXDOBJCBMGR_H__

#include "txglist.h"
#include "txobject.h"

typedef void (*TX_CBK_FUNC) (void*, void*);

class txDObjCB
{
	public:
		TX_CBK_FUNC func;
		void* context;
};
 
TX_DECLARE_GENERIC_LIST(txDObjCB,txDObjCBList)
 
class txDObjCBMgr : public txObject
{
	private:
		txDObjCBList _client_list_;

	private:
		txDObjCBMgr (const txDObjCBMgr&);
		txDObjCBMgr operator= (const txDObjCBMgr&);
		txDObjCB* _findFunction_ (const TX_CBK_FUNC, void*);

	public:
		txDObjCBMgr (void)
		{
		}

		~txDObjCBMgr (void)
		{
			_client_list_.clearAndDestroy();
		}

		void registerCb (const TX_CBK_FUNC func, void* context = 0);

		void unregisterCb (const TX_CBK_FUNC func, void* context = 0);

		void trigger (void* args = 0) const;
};

#endif // __TXDOBJCBMGR_H__

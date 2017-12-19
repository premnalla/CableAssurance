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

#include "sys/txdobjcbmgr.h"

txDObjCB* txDObjCBMgr::_findFunction_ (const TX_CBK_FUNC func, void*)
{
	txDObjCB* retval = 0;
	txDObjCB* callback = 0;
	txDObjCBListIterator iter(_client_list_);

	while (callback = (txDObjCB*) iter.next())
	{
		if (callback->func == func)
		{
			retval = callback;
			break;
		}
	}

	return retval;
}

void txDObjCBMgr::registerCb (const TX_CBK_FUNC func, void* context)
{
	txDObjCB* callback;

	if (!(callback = _findFunction_(func, context)))
	{
		callback = new txDObjCB;
		callback->func = func;
		callback->context = context;

		_client_list_.append(callback);
	}
}

void txDObjCBMgr::unregisterCb (const TX_CBK_FUNC func, void* context)
{
	txDObjCB* callback;

	if (callback = _findFunction_(func, context))
	{
		_client_list_.removeReference(callback);
		delete callback; callback = 0;
	}
}

void txDObjCBMgr::trigger (void* args) const
{
	txDObjCB* callback;
	txDObjCBListIterator iter((txDObjCBList&) _client_list_);

	while (callback = (txDObjCB*) iter.next())
	{
		callback->func(callback->context, args);
	}
}


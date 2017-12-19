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

#include "txdobjglbls.h"

const int txDObjGlobals::PEER_TIMEOUT_INTERVAL = 30000;
const int txDObjGlobals::BASE_THREAD_PRIORITY = 1;
const int txDObjGlobals::STATIC_PEERS_FLAG = 0;
const char* txDObjGlobals::SYSTEM_SPACE = "SYSTEM";

txHashMap txDObjGlobals::txDObjMgrs(10, TX_AUTODEL_ON);


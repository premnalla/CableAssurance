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

#if !defined ( __TXSYNC_H__ )
#define __TXSYNC_H__

typedef void (*TX_IO_FUNC) (void* context, int fd, unsigned long id);

class txSync
{
	public:
		enum MODE
		{
			IONone, IORead, IOWrite, IOExcept
		};

	public:
		static unsigned long registerIO (
			TX_IO_FUNC func,
			void* context,
			int fd,
			MODE mode);

		static void unregisterIO (unsigned long id);

		static void IoAndTime (long timeout);
};

#endif // __TXSYNC_H__

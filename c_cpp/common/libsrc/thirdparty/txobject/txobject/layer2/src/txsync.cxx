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

#if defined TX_SGI
	#define _ABI_SOURCE = 1
#endif

#include <string.h>

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/time.h>
	#include <unistd.h>
#endif

#if defined TX_SOL || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/select.h>
#endif

#if defined TX_WIN
	#include <winsock.h>

	#define close closesocket
#endif

#include "txlist.h"
#include "txsync.h"

struct FdStruct
{
	TX_IO_FUNC func;
	void* context;
	int fd;
};

static fd_set THE_ifdset;
static fd_set THE_ofdset;
static fd_set THE_efdset;

static int THE_FD_SIZE = 0;
static txList THE_READ_LIST;
static txList THE_WRITE_LIST;
static txList THE_EXCEPT_LIST;
static txList THE_EXECUTE_LIST;

static void deallocateFileDescriptors (txList& list)
{
	FdStruct* fds;

	while (fds = (FdStruct*) list.get())
	{
		close(fds->fd);
		delete fds; fds = 0;
	}
}

static void initializeFDMemory (void)
{
	FD_ZERO(&THE_ifdset);
	FD_ZERO(&THE_ofdset);
	FD_ZERO(&THE_efdset);

#if defined TX_WIN
	WSADATA wsaData;

	WSAStartup(MAKEWORD(2, 2), &wsaData);

	int fd = socket(AF_INET, SOCK_DGRAM, 0);

	txSync::registerIO(0, 0, fd, txSync::IORead);
#endif
}

static void cleanUpFDMemory (void)
{
	deallocateFileDescriptors(THE_READ_LIST);
	deallocateFileDescriptors(THE_WRITE_LIST);
	deallocateFileDescriptors(THE_EXCEPT_LIST);

#if defined TX_WIN
	WSACleanup();
#endif
}

TX_STATIC_ALLOC_NOTIFY(initializeFDMemory,(),initializeFDMemory)
TX_STATIC_DEALLOC_NOTIFY(cleanUpFDMemory,(),cleanUpFDMemory)

static void setHighestFd (txList* list)
{
	FdStruct* fds;
	txListIterator iter(*list);

	while (fds = (FdStruct*) iter.next())
	{
		if (fds->fd > THE_FD_SIZE)
		{
			THE_FD_SIZE = fds->fd;
		}
	}
}

static int unregisterFileDescriptor (unsigned long id, txList* list,
	fd_set* set)
{
	FdStruct* fds;
	txListIterator iter(*list);

	while (fds = (FdStruct*) iter.next())
	{
		if (id == (unsigned long) fds)
		{
			iter.remove();

			#if defined TX_WIN
				FD_CLR((unsigned) fds->fd, set);
			#else
				FD_CLR(fds->fd, set);
			#endif

			setHighestFd(list);

			delete fds; fds = 0;

			return 1;
		}
	}

	return 0;
}

inline void checkFileDescriptorList (txList& list, fd_set* set)
{
	// The THE_EXECUTE_LIST is used to guard against a call back
	// removing an item from the file descriptor lists.

	FdStruct* fds;
	txListIterator iter(list);

	while (fds = (FdStruct*) iter.next())
	{
		if (FD_ISSET(fds->fd, set))
		{
			THE_EXECUTE_LIST.append((txObject*) fds);
		}
	}

	while (fds = (FdStruct*) THE_EXECUTE_LIST.get())
	{
		fds->func(fds->context, fds->fd, (unsigned long) fds);
	}
}

unsigned long txSync::registerIO (TX_IO_FUNC func, void* context, int fd,
	MODE mode)
{
	FdStruct* fds = 0;

	if (fd < 0)
	{
		return 0;
	}

	if (fd >= THE_FD_SIZE)
	{
		THE_FD_SIZE = fd;
	}

	switch (mode)
	{
		case IORead:
		{
			fds = new FdStruct();
			FD_SET(fd, &THE_ifdset);
			THE_READ_LIST.append((txObject*) fds);
			break;
		}
		case IOWrite:
		{
			fds = new FdStruct();
			FD_SET(fd, &THE_ofdset);
			THE_WRITE_LIST.append((txObject*) fds);
			break;
		}
		case IOExcept:
		{
			fds = new FdStruct();
			FD_SET(fd, &THE_efdset);
			THE_EXCEPT_LIST.append((txObject*) fds);
			break;
		}
		case IONone:
		{
			break;
		}
	}

	if (fds)
	{
		fds->fd = fd;
		fds->func = func;
		fds->context = context;
	}

	return (unsigned long) fds;
}

void txSync::unregisterIO (unsigned long id)
{
	if (unregisterFileDescriptor(id, &THE_READ_LIST, &THE_ifdset))
	{
		return;
	}

	if (unregisterFileDescriptor(id, &THE_WRITE_LIST, &THE_ofdset))
	{
		return;
	}

	if (unregisterFileDescriptor(id, &THE_EXCEPT_LIST, &THE_efdset))
	{
		return;
	}
}

void txSync::IoAndTime (long min_timeout)
{
	static int fd;
	static fd_set t_ifdset;
	static fd_set t_ofdset;
	static fd_set t_efdset;
	static struct timeval xper = {0,0};

	memcpy(&t_ifdset, &THE_ifdset, sizeof(fd_set));
	memcpy(&t_ofdset, &THE_ofdset, sizeof(fd_set));
	memcpy(&t_efdset, &THE_efdset, sizeof(fd_set));

	if (min_timeout != -1)
	{
		xper.tv_sec = (long) (min_timeout * 0.001);
		xper.tv_usec = (min_timeout % 1000) * 1000;

		fd = select((THE_FD_SIZE + 1),
			&t_ifdset, &t_ofdset, &t_efdset, &xper);
	}
	else
	{
		fd = select((THE_FD_SIZE + 1),
			&t_ifdset, &t_ofdset, &t_efdset, 0);
	}

	if (fd > 0)
	{
		checkFileDescriptorList(THE_READ_LIST, &t_ifdset);
		checkFileDescriptorList(THE_WRITE_LIST, &t_ofdset);
		checkFileDescriptorList(THE_EXCEPT_LIST, &t_efdset);
	}
}


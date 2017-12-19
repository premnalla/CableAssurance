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

#include <string.h>
 
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/types.h>
	#include <netinet/in.h>
#else
	#include <winsock.h>
#endif

#include "txabspacker.h"

inline int depacketize (txNetworkMsgs& packets, txNetworkMsg& incoming_packet)
{
	unsigned header;
	int offset = 0;
	int amount = 0;
	txNetworkMsg* packet = 0;
	unsigned packet_length = 0;
	unsigned long index = sizeof(int);
	unsigned long length_of_residual = 0;

	if (incoming_packet.length() < sizeof(int))
	{
		return amount;
	}

	unsigned long work_length = incoming_packet.length();
	char* work_data = (char*) incoming_packet.data();
	packet_length = ntohl(*(unsigned long*) work_data);

	while ( ((work_length - index) > sizeof(int)) &&
		(packet_length <= (work_length - index)) )
	{
		packet = new txNetworkMsg(work_data + index, packet_length);
		packets.append(packet);

		index+= packet_length + sizeof(int);
		amount++;

		if (index < work_length)
		{
			memcpy(
				&header,
				work_data+(index-sizeof(int)),
				sizeof(int));

			packet_length = ntohl(*(unsigned long*) &header);
		}
		else
		{
			break;
		}
	}

	incoming_packet.clear();

	offset = index - sizeof(int);

	if ((length_of_residual = (work_length - offset)) > 0)
	{
		incoming_packet.append(work_data+offset, length_of_residual);
	}

	return amount;
}

txAbsPacker::txAbsPacker (void) :
	_residual(0, 0)
{
}

txAbsPacker::~txAbsPacker (void)
{
}

void txAbsPacker::packetize (txNetworkMsg& packet)
{
	unsigned long length = htonl(packet.length());
	packet.prepend((char*) &length, sizeof(int));
}

int txAbsPacker::depacketize (txNetworkMsg& packet, txNetworkMsgs& packets)
{
	_residual.append(packet.data(), packet.length());

	return ::depacketize(packets, _residual);
}

txAbsPacker* txAbsPacker::clone (void)
{
	return new txAbsPacker();	
}

void txAbsPacker::flush (void)
{
	_residual.clear();
}


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

#include <errno.h> 
#include <stdio.h>
#include <string.h>
#include <signal.h>

#if defined TX_SGI
	#define _ABI_SOURCE = 1
#endif

#if defined TX_SOL || TX_MAC
	#include <sys/socket.h>
	#include <sys/sockio.h>
#endif

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <netdb.h>
	#include <fcntl.h> 
	#include <net/if.h>
	#include <unistd.h> 
	#include <sys/un.h>
	#include <sys/time.h>
	#include <sys/ioctl.h> 
	#include <netinet/tcp.h>
	#include <netinet/in.h>
#endif

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	#include <sys/types.h>
        #include <sys/socket.h>
#endif

#if defined TX_WIN
#ifdef WIN2K_UDP_FIX
	//
	// FIX Window 2000 UDP socket BUG
	//
        #include <winsock2.h>

	#define SIO_UDP_CONNRESET _WSAIOW(IOC_VENDOR,12)
#endif
#ifndef WIN2K_UDP_FIX
        #include <winsock.h>
#endif
#endif

#include "txsync.h"
#include "txstring.h"
#include "txsocket.h"
#include "txthread.h"
#include "txippeer.h"
#include "txabspacker.h"
#include "txhashtable.h"

#if defined TX_WIN || TX_MAC
	typedef int txSizeOfType;
#endif

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX
	typedef unsigned int txSizeOfType;
#endif

#if defined GNU_SUPPORT
	#define txSizeOfType int
#endif

struct txSocketTokens
{
	txSocket* client;
	txSocket* self;
};

class txSockAddr
{
	private:
		struct sockaddr_in _addr_;

	public:
		txSockAddr (unsigned long netaddr, int port)
		{
			memset(&_addr_, 0, sizeof(_addr_));
			_addr_.sin_addr.s_addr = htonl(netaddr);
			_addr_.sin_port = htons(port);
			_addr_.sin_family = AF_INET;
		}

		struct sockaddr_in* get (void)
		{
			return &_addr_;
		}
};

static int displayWait (int wait_location)
{
	char a = 8;   /* BS */
	char b = 124; /* |  */
	char c = 47;  /* /  */
	char d = 45;  /* -  */
	char e = 92;  /* \  */

	for (int i = 0; i < 400; i++)
	{
		fprintf(stdout, "%c%c", a, a); fflush(stdout);

		switch (wait_location)
		{
			case 0:
			{
				fprintf(stdout, "%c", b); fflush(stdout);
				break;
			}
			case 1:
			{
				fprintf(stdout, "%c", c); fflush(stdout);
				break;
			}
			case 2:
			{
				fprintf(stdout, "%c", d); fflush(stdout);
				break;
			}
			case 3:
			{
				fprintf(stdout, "%c", e); fflush(stdout);
				break;
			}
		}
	}

	if (wait_location == 3)
	{
		wait_location = -1;
	}

	return wait_location + 1;
}

static const int THE_MAX_RECV_DEPTH = 100;
static const int THE_MAX_SEND_DEPTH = 100;

const int txSocket::SOCKET_THRD_PRIORITY = 4;

txSocket::txSocket (int type, int priority) :
	_send_event_("Send Event"), _receive_event_("Receive Event"),
	_in_q_(THE_MAX_RECV_DEPTH), _out_q_(THE_MAX_SEND_DEPTH),
	_contact_lost_args_(0), _contact_estab_args_(0),
	_contact_lost_func_(0), _contact_estab_func_(0),
	_snd_inet_id_(-1), _rcv_inet_id_(-1),
	_packer_(new txAbsPacker()),
	_status_(SOCKET_INITIAL),
	_type_(type), _port_(-1),
	_priority_(priority),
	_fd_INET_(-1),
	_netaddr_(0),
	_snd_(0,0),
	_rcv_(0,0)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	(void) signal(SIGPIPE, SIG_IGN);
#endif
}

txSocket::txSocket (int type, txAbsPacker* packer_from_user, int priority) :
	_send_event_("Send Event"), _receive_event_("Receive Event"),
	_in_q_(THE_MAX_RECV_DEPTH), _out_q_(THE_MAX_SEND_DEPTH),
	_contact_lost_args_(0), _contact_estab_args_(0),
	_contact_lost_func_(0), _contact_estab_func_(0),
	_snd_inet_id_(-1), _rcv_inet_id_(-1),
	_packer_(packer_from_user),
	_status_(SOCKET_INITIAL),
	_type_(type), _port_(-1),
	_priority_(priority),
	_fd_INET_(-1),
	_netaddr_(0),
	_snd_(0,0),
	_rcv_(0,0)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	(void) signal(SIGPIPE, SIG_IGN);
#endif
}

txSocket::txSocket (int Inet, int type, int port, int priority) :
	_send_event_("Send Event"), _receive_event_("Receive Event"),
	_in_q_(THE_MAX_RECV_DEPTH), _out_q_(THE_MAX_SEND_DEPTH),
	_contact_lost_args_(0), _contact_estab_args_(0),
	_contact_lost_func_(0), _contact_estab_func_(0),
	_snd_inet_id_(-1), _rcv_inet_id_(-1),
	_type_(type), _port_(port),
	_status_(SOCKET_INITIAL),
	_priority_(priority),
	_fd_INET_(Inet),
	_netaddr_(0),
	_packer_(0),
	_snd_(0,0),
	_rcv_(0,0)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	(void) signal(SIGPIPE, SIG_IGN);
#endif
}

txSocket::~txSocket (void)
{
	txSocket::close();

	_snd_.clear();
	_rcv_.clear();

	txThread::yield();

	delete _packer_; _packer_ = 0;
}

void txSocket::_initialize_ (void)
{
	if (_type_ == SOCK_STREAM)
	{
		txThread::start(
			(TX_THREAD_FUNC) txSocket::_sendThreadFunc_, this,
			"txSocket Sender", _priority_);

		txThread::start(
			(TX_THREAD_FUNC) txSocket::_receiveThreadFunc_, this,
			"txSocket Receiver", _priority_);
	}
}

void txSocket::_uninitialize_ (void)
{
	txQueue* queue;
	txNetworkMsg* packet;

	_out_q_.unregisterQueue(queue);

	if (queue)
	{
		while (packet = (txNetworkMsg*) queue->get())
		{
			delete packet; packet = 0;
		}

		delete queue; queue = 0;
	}

	_in_q_.unregisterQueue(queue);

	if (queue)
	{
		while (packet = (txNetworkMsg*) queue->get())
		{
			delete packet; packet = 0;
		}

		delete queue; queue = 0;
	}
}

void txSocket::_setNonBlocking_ (int fd)
{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	int delay = fcntl(fd, F_GETFL, 0);

	delay |= O_NDELAY;

	fcntl(fd, F_SETFL, delay);
#else
	unsigned long opt = 1;

	ioctlsocket(fd, FIONBIO, &opt);
#endif
}

void txSocket::_socketError_ (const char* function)
{
	if ((errno == EPIPE) ||
		(errno == EBADF) ||
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		(errno == ETIMEDOUT) ||
		(errno == ECONNRESET) ||
		(errno == ECONNREFUSED))
#else
		(errno == WSAETIMEDOUT) ||
		(errno == WSAECONNRESET) ||
		(errno == WSAECONNREFUSED))
#endif
	{
		txSocket::close();
	}
	else if ((errno != 0) &&
		(errno != EINTR) &&
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		(errno != EWOULDBLOCK))
#else
		(errno != 2))
#endif
	{
		fprintf(stderr, "\007");
		perror(function);
		fflush(stderr);
	}
}

int txSocket::_send_ (int fd, const char* stream, int streamlength)
{
	int sent = 0;
	int total = 0;

	while ((_status_ != SOCKET_CLOSED) && (total < streamlength))
	{
		sent = ::send(fd, stream + total, streamlength - total, 0);

		if (sent < 0)
		{
			_socketError_("write");

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			if (errno == EWOULDBLOCK)
#else
			if ((errno == 0) || (errno == 2))
#endif
			{
				_registerSendCallBack_();

				txThread::yield(_send_event_);

				_unregisterSendCallBack_();
			}
			else
			{
#if defined TX_WIN
				txSocket::close();
				total = -1;
				break;
#endif
			}
		}
#if defined TX_WIN
		else if (!sent)
		{
			txSocket::close();
			total = -1;
			break;
		}
#endif
		else
		{
			total+= sent;
		}
	}

	return total;
}

void txSocket::_sendThreadFunc_ (txSocket* self)
{
	txNetworkMsg* p;

	int fd = self->fd(); self->_setNonBlocking_(fd);

	while ((self->_status_ != SOCKET_CLOSED) &&
		(self->_out_q_.get((void*&) p) != -1))
	{
		self->_snd_.append(p->data(), p->length());
		delete p; p = 0;

		while (self->_out_q_.entries()&&(self->_snd_.length() < 4096))
		{
			self->_out_q_.get((void*&) p);
			self->_snd_.append(p->data(), p->length());
			delete p; p = 0;
		}

		self->_send_(fd, self->_snd_.data(), self->_snd_.length());
		self->_snd_.clear();
	}
}

void txSocket::_receiveThreadFunc_ (txSocket* self)
{
	int length;
	char buf[4096];
	txNetworkMsg* p;
	txNetworkMsgs packets;

	int fd = self->fd(); self->_setNonBlocking_(fd);

	while (self->_status_ != SOCKET_CLOSED)
	{
		txThread::yield(self->_receive_event_);

		if (self->_status_ == SOCKET_CLOSED)
		{
			break;
		}
		else if ((length = ::recv(fd, buf, 4096, 0)) > 0)
		{
			self->_rcv_.clear();
			self->_rcv_.append((char*) &buf, length);
			self->_packer_->depacketize(self->_rcv_, packets);

			while (p = packets.get())
			{
				if (self->_in_q_.put(p) == -1)
				{
					delete p; p = 0;
				}
			}
		}
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		else if (length < 0)
		{
			self->_socketError_("read");
		}
#endif
		else
		{
			self->close();
		}
	}
}

void txSocket::_sendDataCbFunc_ (txSocket* self, int)
{
	if (self->_status_ == SOCKET_CLOSED)
	{
		return;
	}

	self->_send_event_.trigger();
}

void txSocket::_receiveDataCbFunc_ (txSocket* self, int fd)
{
	if (self->_status_ == SOCKET_CLOSED)
	{
		return;
	}

	if ((self->_status_ == SOCKET_LISTENING) && self->_contact_estab_func_)
	{
		self->_accept_(fd);
	}
	else
	{
		self->_receive_event_.trigger();
	}
}

void txSocket::_registerSendCallBack_ (void)
{
	if ((_snd_inet_id_ == -1) && (_fd_INET_ != -1))
	{
		_snd_inet_id_ = (long)
			txSync::registerIO((TX_IO_FUNC)
				txSocket::_sendDataCbFunc_,
				this, _fd_INET_, txSync::IOWrite);
	}
}

void txSocket::_registerReceiveCallBack_ (void)
{
	if ((_rcv_inet_id_ == -1) && (_fd_INET_ != -1))
	{
		_rcv_inet_id_ = (long)
			txSync::registerIO((TX_IO_FUNC)
				txSocket::_receiveDataCbFunc_,
				this, _fd_INET_, txSync::IORead);
	}
}

void txSocket::_unregisterSendCallBack_ (void)
{
	if (_snd_inet_id_ != -1)
	{
		txSync::unregisterIO((unsigned long) _snd_inet_id_);

		_snd_inet_id_ = -1;
	}
}

void txSocket::_unregisterReceiveCallBack_ (void)
{
	if (_rcv_inet_id_ != -1)
	{
		txSync::unregisterIO((unsigned long) _rcv_inet_id_);

		_rcv_inet_id_ = -1;
	}
}

void txSocket::_unregisterFileDescriptors_ (void)
{
	_unregisterSendCallBack_();
	_unregisterReceiveCallBack_();
}

void txSocket::_contactLost_ (void* obj)
{
	txSocket* self = (txSocket*) obj;
	TX_SOCKET_FUNC func;

	if (self->_contact_lost_func_)
	{
		func = self->_contact_lost_func_;

		self->_contact_lost_func_ = 0;

		func(self->_contact_lost_args_, self);
	}
}

void txSocket::_contactEstablished_ (void* obj)
{
	txSocketTokens* t = (txSocketTokens*) obj;
	txSocket* self = t->self;
	txSocket* client = t->client;

	if (self->_contact_estab_func_)
	{
		self->_contact_estab_func_(self->_contact_estab_args_, client);
	}
	else
	{
		delete client; client = 0;
	}

	delete t; t = 0;
}

unsigned long txSocket::netaddr (void)
{
	if (!_netaddr_)
	{
		_netaddr_ = txSocket::generateLocalNetAddr(OUT_OF_BAND);
	}

	return _netaddr_;
}

int txSocket::fd (void)
{
	return _fd_INET_;
}

int txSocket::port (void)
{
	return _port_;
}

int txSocket::close (void)
{
	int status = 1;

	_unregisterFileDescriptors_();

	if (_status_ != SOCKET_CLOSED)
	{
		_status_ = SOCKET_CLOSED;

#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		if ((_fd_INET_ != -1) && ::close(_fd_INET_) < 0)
#else
		if ((_fd_INET_ != -1) && ::closesocket(_fd_INET_) < 0)
#endif
		{
			status = -1;
			_socketError_("close");
		}

		_fd_INET_ = -1;

		_uninitialize_();

		txThread::start(
			txSocket::_contactLost_, this,
			"txSocket::_lost_", _priority_ + 2);
	}

	return status;
}

int txSocket::bind (int port)
{
	return bind(netaddr(), port);
}

int txSocket::bind (unsigned long ntddr, int port)
{
	char dummy = 1;
	int position = 0;
	struct sockaddr_in a_inet;
	txSizeOfType len = sizeof(a_inet);

	_netaddr_ = ntddr;
	_fd_INET_ = socket(AF_INET, _type_, 0);

	setsockopt(_fd_INET_, SOL_SOCKET, SO_REUSEADDR, &dummy, sizeof(int));

#if defined WIN2K_UDP_FIX
	if (_type_ == SOCK_DGRAM)
	{
		BOOL bNewBehavior = FALSE;
		DWORD dwBytesReturned = 0;

		WSAIoctl(_fd_INET_, SIO_UDP_CONNRESET,
			&bNewBehavior, sizeof(BOOL),
			NULL, 0, &dwBytesReturned,
			NULL, NULL);
	}
#endif

	memset(&a_inet, 0, len);
	a_inet.sin_family = AF_INET;
	a_inet.sin_port = htons(port);
	a_inet.sin_addr.s_addr = htonl(ntddr);

	while (::bind(_fd_INET_, (sockaddr*) &a_inet, len) < 0)
	{
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		if (errno != EADDRINUSE)
#else
		if (errno != WSAEADDRINUSE)
#endif
		{
			_socketError_("bind");
			return -1;
		}
		else
		{
			position = displayWait(position);	
		}
	}

	getsockname(_fd_INET_, (sockaddr*) &a_inet, &len);
	_port_ = ntohs(a_inet.sin_port);
	_status_ = SOCKET_BOUND;

	_registerReceiveCallBack_();

	return _port_;
}

int txSocket::listen (int connections)
{
	_status_ = SOCKET_LISTENING;

	int status = 1;

	if (::listen(_fd_INET_, connections) < 0)
	{
		status = -1;
		_socketError_("listen");
	}

	return status;
}

int txSocket::connect (unsigned long ntddr, int port)
{
	char buf[256];
	char dummy = 1;
	struct sockaddr_in a_inet;

	_fd_INET_ = socket(AF_INET, _type_, 0);

	setsockopt(_fd_INET_, IPPROTO_TCP, TCP_NODELAY, &dummy, sizeof(int));

	_setNonBlocking_(_fd_INET_);

	txSizeOfType len = sizeof(a_inet);
	memset(&a_inet, 0, len);
	a_inet.sin_family = AF_INET;
	a_inet.sin_port = htons(port);
	a_inet.sin_addr.s_addr = htonl(ntddr);

	int status = -1;

	for (int i = 0; (status == -1) && (i < 25); i++)
	{
		::connect(_fd_INET_, (sockaddr*) &a_inet, len);

		status = getpeername(_fd_INET_, (struct sockaddr*) buf, &len);

		txThread::yield(_send_event_, 100);
	}

	if (status != -1)
	{
		if (_fd_INET_ != -1)
		{
			_netaddr_ = htonl(a_inet.sin_addr.s_addr);
		}

		_port_ = port;
		_initialize_();
		_registerReceiveCallBack_();
		_status_ = SOCKET_CONNECTED;
	}
	else
	{
		status = -1;
		close();
	}

	return status;
}

txSocket* txSocket::_accept_ (int self_fd)
{
	int fd;
	char dummy = 1;
	struct sockaddr_in address;
	txSizeOfType len = sizeof(address);

	if ((fd = ::accept(self_fd, (sockaddr*) &address, &len)) < 0)
	{
		_socketError_("accept");
		return 0;
	}

	setsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &dummy, sizeof(int));

	int port = (int) ntohs(address.sin_port);
	txSocket* socket = new txSocket(fd, _type_, port, _priority_);
	socket->_netaddr_ = htonl(address.sin_addr.s_addr);
	socket->_initialize_();
	socket->_status_ = SOCKET_READY;
	socket->_registerReceiveCallBack_();
	socket->_packer_ = _packer_->clone();

	txSocketTokens* t = new txSocketTokens();
	t->self = this; t->client = socket;

	txThread::start(
		txSocket::_contactEstablished_, t,
		"txSocket::_estab_", _priority_ + 1);

	return socket;
}

int txSocket::send (txNetworkMsg* p)
{
	int status = -1;

	if (_status_ != SOCKET_CLOSED)
	{
		status = _out_q_.put((void*) p);
	}

	return status;
}

int txSocket::send (const char* stream, int streamlength)
{
	int status = -1;
	txNetworkMsg* p;

	if (_status_ != SOCKET_CLOSED)
	{
		p = new txNetworkMsg(stream, streamlength);

		_packer_->packetize(*p);

		status = _out_q_.put((void*) p);
	}

	return status;
}

int txSocket::recv (txNetworkMsgs& packets, int amount, unsigned long time)
{
	int status = -1;
	txNetworkMsg* p;

	if (_status_ != SOCKET_CLOSED)
	{
		if (!amount && !(amount = _in_q_.entries()))
		{
			amount = 1;
		}

		for (int i = 0; i < amount; i++)
		{
			if (_in_q_.get((void*&) p, time) != -1)
			{
				packets.append(p);
			}
			else
			{
				return -1;
			}
		}

		status = 1;
	}

	return status;
}

int txSocket::sendTo (txNetworkMsg* p)
{
	int length = -1;
	struct sockaddr* address;
	txIpPeer* peer = (txIpPeer*) p->peer();
	txSockAddr sock_s(peer->getNetAddr(), peer->getPort());
	address = (sockaddr*) sock_s.get();

	if (_status_ == SOCKET_CLOSED)
	{
		return length;
	}

	if ((length = ::sendto(_fd_INET_, p->data(),
		p->length(), 0, address, sizeof(*address))) < 0)
	{
#if defined TX_LINUX /* redhat bug fix. */
		_unregisterFileDescriptors_(); ::close(_fd_INET_);
		txSocket::bind(_netaddr_, _port_);
		length = 0;
#else
		_socketError_("sendto");
#endif
	}
	else
	{
		delete p; p = 0;
	}

	return length;
}

int txSocket::sendTo (const char* data, int datalength, unsigned long ntddr,
	int port)
{
	int length = -1;
	struct sockaddr* address;
	txSockAddr sock_s(ntddr, port); address = (sockaddr*) sock_s.get();

	if (_status_ == SOCKET_CLOSED)
	{
		return length;
	}

	if ((length = ::sendto(_fd_INET_, data, datalength, 0,
		address, sizeof(*address))) < 0)
	{
#if defined TX_LINUX /* redhat bug fix. */
		_unregisterFileDescriptors_(); ::close(_fd_INET_);
		txSocket::bind(_netaddr_, _port_);
		length = 0;
#else
		_socketError_("sendto");
#endif
	}

	return length;
}

char* txSocket::recvFrom (int bufsize, int& datalength, unsigned long& ntddr,
	int& port)
{
	char* msg = 0;
	char buf[4096];
	struct sockaddr_in* address;
	txSockAddr sock_s(txSocket::netaddr(), _port_); address = sock_s.get();

	if (_status_ == SOCKET_CLOSED)
	{
		port = 0; ntddr = 0; datalength = -1; return msg;
	}

	txThread::yield(_receive_event_);

	if (_status_ == SOCKET_CLOSED)
	{
		port = 0; ntddr = 0; datalength = -1; return msg;
	}

	txSizeOfType len = sizeof(*address);

	if ((datalength = ::recvfrom(_fd_INET_, buf, bufsize, 0,
		(sockaddr*) address, &len)) < 0)
	{
#if defined TX_LINUX /* redhat bug fix. */
		_unregisterFileDescriptors_(); ::close(_fd_INET_);
		txSocket::bind(_netaddr_, _port_);
		datalength = 0; port = 0; ntddr = 0;
#else
		_socketError_("recvfrom");
#endif
	}
	else if (datalength > 0)
	{
		msg = new char[datalength];
		memcpy(msg, buf, datalength);
		port = (int) ntohs(address->sin_port);
		ntddr = htonl(address->sin_addr.s_addr);
	}
	else
	{
		port = 0; ntddr = 0; datalength = -1; txSocket::close();
	}

	return msg;
}

void txSocket::registerContactEstablished (const TX_SOCKET_FUNC func,
	void* args)
{
	_contact_estab_func_ = func;
	_contact_estab_args_ = args;
}

void txSocket::registerContactLost (const TX_SOCKET_FUNC func,
	void* args)
{
	_contact_lost_func_ = func;
	_contact_lost_args_ = args;
}

void txSocket::unregisterContactEstablished (void)
{
	_contact_estab_func_ = 0;
	_contact_estab_args_ = 0;
}

void txSocket::unregisterContactLost (void)
{
	_contact_lost_func_ = 0;
	_contact_lost_args_ = 0;
}

unsigned long txSocket::generateLocalNetAddr (txSocket::TYPE type)
{
	unsigned long ntddr = 0;

	if (type == txSocket::OUT_OF_BAND)
	{
		char node[256];

		if (gethostname(node, 256) < 0)
		{
			fprintf(stderr, "\007");
			perror("gethostname");
			fflush(stderr);
		}
		else
		{
			ntddr = txSocket::generateNetAddr(node);
		}
	}
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	else if (type == txSocket::IN_BAND)
	{
		struct ifreq ifr;
		struct sockaddr_in ha;
		int sock = socket(AF_INET, SOCK_DGRAM, 0);
		strcpy(ifr.ifr_name, "fa0");
		ioctl(sock, SIOCGIFADDR, (char*) &ifr);
		memcpy((char*) &ha,(char*) &ifr.ifr_addr, sizeof(sockaddr_in));
		::close(sock);

		ntddr = ha.sin_addr.s_addr;
	}
#endif

	return ntddr;
}

unsigned long txSocket::generateNetAddr (const char* machine)
{
	char ch;
	int d1, d2, d3, d4;
	unsigned long ntddr = 0;
	struct hostent* node = 0;

	if ((sscanf(machine,"%d.%d.%d.%d%c",&d1,&d2,&d3,&d4,&ch)==4) &&
		( (0 <= d1) && (d1 <= 255) && (0 <= d2) && (d2 <= 255) ) &&
		( (0 <= d3) && (d3 <= 255) && (0 <= d4) && (d4 <= 255) ))
	{
		ntddr = ((long) d1 << 24) | ((long) d2 << 16) |
			((long) d3 << 8) | ((long) d4 << 0);
	}
	else if ((node = gethostbyname(machine)) == 0)
	{
		fprintf(stderr, "\007");
		perror("gethostbyname");
		fflush(stderr);
	}

	if (!ntddr && node)
	{
		struct sockaddr_in address;
		memset(&address, 0, sizeof(&address));
		memcpy(&address.sin_addr, node->h_addr, node->h_length);
		ntddr = htonl(address.sin_addr.s_addr);
	}

	return ntddr;
}

int txSocket::generateIp (unsigned long ntddr, char* ip)
{
	sprintf(ip, "%d.%d.%d.%d",
		(int) (ntddr >> 24) & 0xff,
		(int) (ntddr >> 16) & 0xff,
		(int) (ntddr >>  8) & 0xff,
		(int) (ntddr >>  0) & 0xff);

	return 1;
}

int txSocket::generateIp (const char* machine, char* ip)
{
	unsigned long ntddr;

	if (ntddr = generateNetAddr(machine))
	{
#ifdef TX_BIG_ENDIAN
		return txSocket::generateIp(htonl(ntddr), ip);
#endif

#ifdef TX_LITTLE_ENDIAN
		return txSocket::generateIp(ntddr, ip);
#endif

#ifndef TX_BIG_ENDIAN
#ifndef TX_LITTLE_ENDIAN
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
		return txSocket::generateIp(htonl(ntddr), ip);
#endif

#if defined TX_WIN || TX_LINUX
		return txSocket::generateIp(ntddr, ip);
#endif
#endif // TX_LITTLE_ENDIAN
#endif // TX_BIG_ENDIAN
	}

	return 0;
}

int txSocket::generateLocalIp (txSocket::TYPE type, char* ip)
{
	if (type == txSocket::OUT_OF_BAND)
	{
		char node[256];

		if (gethostname(node, 256) < 0)
		{
			fprintf(stderr, "\007");
			perror("gethostname");
			fflush(stderr);
		}
		else
		{
			unsigned long net = txSocket::generateNetAddr(node);

#ifdef TX_BIG_ENDIAN
			return txSocket::generateIp(htonl(net), ip);
#endif

#ifdef TX_LITTLE_ENDIAN
			return txSocket::generateIp(net, ip);
#endif

#ifndef TX_BIG_ENDIAN
#ifndef TX_LITTLE_ENDIAN
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
			return txSocket::generateIp(htonl(net), ip);
#endif

#if defined TX_WIN || TX_LINUX
			return txSocket::generateIp(net, ip);
#endif
#endif // TX_LITTLE_ENDIAN
#endif // TX_BIG_ENDIAN
		}
	}
#if defined TX_SOL || TX_SUNOS || TX_SGI || TX_HP || TX_DEC || TX_LINUX || TX_MAC
	else if (type == txSocket::IN_BAND)
	{
		struct ifreq ifr;
		struct sockaddr_in ha;
		int sock = socket(AF_INET, SOCK_DGRAM, 0);
		strcpy(ifr.ifr_name, "fa0");
		ioctl(sock, SIOCGIFADDR, (char*) &ifr);
		memcpy((char*) &ha,(char*) &ifr.ifr_addr, sizeof(sockaddr_in));
		::close(sock);

		return txSocket::generateIp(ha.sin_addr.s_addr, ip);
	}
#endif

	return 0;
}


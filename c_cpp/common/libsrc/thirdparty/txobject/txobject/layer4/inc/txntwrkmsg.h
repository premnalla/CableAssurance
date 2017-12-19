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

#if !defined ( __TXNTWRKMSG_H__ )
#define __TXNTWRKMSG_H__

#include <stdlib.h>

#include "txsmrtbuf.h"
#include "txabspeer.h"

struct txNetworkMsg
{
	private:
		txAbsPeer* _peer_;
		txNetworkMsg* _next_;
		txSmartBuffer* _data_;
		unsigned long* _ref_count_;

	private:
		txNetworkMsg (void) :
			_peer_(0), _data_(0), _next_(0), _ref_count_(0)
		{
		}

	public:
		txNetworkMsg (const char* d, int l, const txAbsPeer* a = 0) :
			_peer_(0), _data_(0), _next_(0),
			_ref_count_(new unsigned long(1))
		{
			_data_ = new txSmartBuffer(d, l);

			if (a)
			{
				_peer_ = a->clone();
			}
		}

		~txNetworkMsg (void)
		{
			(*_ref_count_)--;

			if (!(*_ref_count_))
			{
				delete _ref_count_; _ref_count_ = 0;
				delete _data_; _data_ = 0;
			}

			delete _peer_; _peer_ = 0;
		}

		void* operator new (size_t size);
		void operator delete (void* p);

		void append (const char* data, unsigned long length)
		{
			_data_->append(data, length);
		}

		void prepend (const char* data, unsigned long length)
		{
			_data_->prepend(data, length);
		}

		const char* data (void) const
		{
			return _data_->data();
		}

		unsigned long length (void) const
		{
			return _data_->length();
		}

		void peer (txAbsPeer* peer)
		{
			_peer_ = peer;
		}

		txAbsPeer* peer (void)
		{
			return _peer_;
		}

		void clear (void)
		{
			_data_->clear();
		}
		
		txNetworkMsg* clone (txAbsPeer* peer = 0)
		{
			txNetworkMsg* msg = new txNetworkMsg();

			(*_ref_count_)++;

			msg->_ref_count_ = _ref_count_;
			msg->_data_ = _data_;

			if (peer)
			{
				msg->_peer_ = peer->clone();
			}
			else if (_peer_)
			{
				msg->_peer_ = _peer_->clone();
			}

			return msg;
		}

	friend struct txNetworkMsgs;
};

struct txNetworkMsgs
{
	private:
		txNetworkMsg* _head_;
		txNetworkMsg* _tail_;

	public:
		txNetworkMsgs (void) :
			_head_(0),
			_tail_(0)
		{
		}

		~txNetworkMsgs (void)
		{
			txNetworkMsg* packet;

			while (packet = get())
			{
				free((char*) packet);
			}
		}

		txNetworkMsg* get (void)
		{
			txNetworkMsg* packet = _head_;

			if (packet)
			{
				_head_ = _head_->_next_;
			}

			if (!_head_)
			{
				_tail_ = 0;
			}

			return packet;
		}

		void append (txNetworkMsg* packet)
		{
			if (packet)
			{
				if (!_head_)
				{
					_head_ = packet;
				}

				if (_tail_)
				{
					txNetworkMsg* temp = _tail_;
					temp->_next_ = packet;
				}
				packet->_next_ = 0;

				_tail_ = packet;
			}
		}
};

#endif // __TXNTWRKMSG_H__

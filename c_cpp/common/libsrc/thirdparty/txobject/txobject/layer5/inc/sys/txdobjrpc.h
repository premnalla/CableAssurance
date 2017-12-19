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
 
#if !defined ( __TXDOBJRPC_H__ )
#define __TXDOBJRPC_H__

#include "txevent.h"
#include "txuint32.h"
#include "txthread.h"
#include "txabspeer.h"
#include "sys/txdobjmsg.h"

class txRpcResult;

class txDObjRpc : public txDObjMsg
{
	TX_DECLARE_STREAM_TYPE(txDObjRpc)

	private:
		static int _registerFlag_;
		static txHashTable _rpc_entries_;

	private:
		static txObject* _rpcReqRcvd_ (
			txDObjRpc*, txIn&, const txAbsPeer&);
		static txObject* _rpcRespRcvd_ (
			txDObjRpc*, txIn&, const txAbsPeer&);

	public:
		txDObjRpc (txDObjMgr* d_mgr = 0);

		~txDObjRpc (void);

		void sendRpcMessage (
			txRpcResult&,
			txOut&,
			const txAbsPeer&,
			unsigned long time = 0);

		void flushAffectedRpcs (void);

		static void flushAffectedRpcs (const txAbsPeer&);
};

class txRpcResult : public txObject
{
	public:
		enum RESPONSE
		{
			INIT,
			FAIL,
			TIMEOUT,
			SUCCEEDED,
			DESTROYED
		}; 

	public:
		txRpcResult (void) :
			_result_(0),
			_status_(INIT)
		{
		}

		~txRpcResult (void)
		{
			delete _result_; _result_ = 0;
		}

		void result (txObject* result)
		{
			_result_ = result;
		}

		txObject* result (void)
		{
		 	txObject* retval = _result_;

			_result_ = 0;

			return retval;
		}

		void status (RESPONSE status)
		{
			_status_ = status;
		}

		RESPONSE status (void)
		{
			return _status_;
		}

	private:
		txObject* _result_;
		RESPONSE _status_;
};
       
class txRpcEntry : public txObject
{
	public:
		txRpcEntry (txRpcResult& r, const txAbsPeer& a, txDObjRpc* s) :
			_id_(++_rpc_count_),  _peer_(a.clone()), _result_(&r),
			_self_(s)
		{
		}

		~txRpcEntry (void)
		{
			delete _peer_; _peer_ = 0;
		}

		const txAbsPeer* peer (void) const
		{
			return _peer_;
		}

		unsigned long id (void) const
		{
			return _id_;
		}

		txRpcResult* result (void) const
		{
			return _result_;
		}	

		txDObjRpc* self (void) const
		{
			return _self_;
		}

		unsigned hash (void) const
		{
			return _id_;
		}

		int equals (const txObject* obj) const
		{
			return id() == ((txUInt32*) obj)->value();
		}

		void waitForResult (unsigned long timeout = 0);

		void setResponse (txRpcResult::RESPONSE, txObject* result);

	private:
		txEvent _event_;
		txAbsPeer* _peer_;
		txDObjRpc* _self_;
		unsigned long _id_;
		txRpcResult* _result_;
		static unsigned long _rpc_count_;
};

#endif // __TXDOBJRPC_H__

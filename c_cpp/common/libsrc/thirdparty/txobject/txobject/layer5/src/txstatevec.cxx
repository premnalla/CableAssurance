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

#include "txlist.h"
#include "txstring.h"
#include "txhashmap.h"
#include "txpeerlist.h"
#include "sys/txstatevec.h"

TX_DEFINE_STREAM_TYPE(txStateVector,txState)

txStateVector::txStateVector (int) : _sv_(new txHashMap())
{
}

txStateVector::txStateVector (void) : _sv_(0)
{
}

txStateVector::txStateVector (const txStateVector& obj) :
	txState(obj)
{
	_sv_ = obj._sv_;
}

txStateVector& txStateVector::operator= (const txStateVector& obj)
{
	if (this != &obj)
	{
		if (refcount() == 1)
		{
			_sv_->clearAndDestroy();
			delete _sv_; _sv_ = 0;
		}

		_sv_ = obj._sv_;

		txState::operator=(obj);
	}

	return *this;
}

txStateVector::~txStateVector (void)
{
	if (refcount() == 1)
	{
		if (_sv_)
		{
			_sv_->clearAndDestroy();
			delete _sv_; _sv_ = 0;
		}
	}
}

txState* txStateVector::clone (void)
{
	return new txStateVector(*this);
}

void txStateVector::set (const txAbsPeer& key, const txState& obj)
{
	txObject* old;
	txState* value = ((txState*) &obj)->clone();

	if (!_sv_->findValue(&key))
	{
		_sv_->insertKeyAndValue(key.clone(), value);
	}
	else
	{
		old = _sv_->findValue(&key, value);
		delete old; old = 0;
	}
}

void txStateVector::del (const txAbsPeer& key)
{
	_sv_->removeAndDestroy(&key);
}

const txState* txStateVector::get (const txAbsPeer& key) const
{
	return _sv_ ? (txState*) _sv_->findValue(&key) : 0;
}

signed short int txStateVector::state (const txAbsPeer* exclude_key) const
{
	txAbsPeer* key = 0;
	signed short int state_value = 0;

	txHashMapIterator iter(*_sv_);

	// Get The First Non Excluded State 
	while (key = (txAbsPeer*) iter.next())
	{
		if (exclude_key && (key->equals(exclude_key)))
		{
			// exclude This Keys State
		}	
		else
		{
			state_value = ((txState*)
				iter.value())->state(exclude_key);

			break;
		}
	}

	// Check All Other Non Exclude States Against The State
	while (key = (txAbsPeer*) iter.next())
	{
		if (exclude_key && (key->equals(exclude_key)))
		{
			// exclude This Keys State
		}	
		else if (state_value != ((txState*)
			iter.value())->state(exclude_key))
		{
			state_value = 0;
			break;
		}
	}

	return state_value;
}

signed short int txStateVector::state (const txPeerList* exclude_keys) const
{
	txHashMap dict;
	txAbsPeer* key = 0;
	txState* state_obj;
	signed short int state = 0;
	txHashMapIterator dict_iter(*_sv_);
	txPeerListIterator list_iter((txPeerList&) *exclude_keys);

	while (key = (txAbsPeer*) list_iter.next())
	{
		dict.insertKeyAndValue(key, key);
	}

	while (key = (txAbsPeer*) dict_iter.next())
	{
		if (!dict.find(key))
		{
			state_obj = (txState*) dict_iter.value();
			state = state_obj->state(exclude_keys);
			break;
		}
	}

	while (key = (txAbsPeer*) dict_iter.next())
	{
		if (!dict.find(key))
		{
			state_obj = (txState*) dict_iter.value();

			if (state != state_obj->state(exclude_keys))
			{
				state = 0;
				break;
			}
		}
	}

	return state;
}

void txStateVector::storeInners (txOut& out) const
{
	out << _sv_;
}

void txStateVector::restoreInners (txIn& in)
{
	in >> _sv_;
}


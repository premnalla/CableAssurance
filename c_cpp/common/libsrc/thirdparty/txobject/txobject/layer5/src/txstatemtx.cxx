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

#include "txstring.h"
#include "txhashmap.h"
#include "txabspeer.h"
#include "txpeerlist.h"
#include "sys/txstatemtx.h"
#include "sys/txstateobj.h"

//
// work around. see ::colState
//
#include "sys/txstatectmtx.h"

txStateMatrix::txStateMatrix (void) :
	_matrix_rows_(new txStateVector(1)),
	_primary_key_(0)
{
}

txStateMatrix::txStateMatrix (const txStateMatrix& sm_obj) :
	txState(sm_obj),
	_primary_key_(0)
{
	_matrix_rows_ = sm_obj._matrix_rows_;
}

txStateMatrix::~txStateMatrix (void)
{
	if (refcount() == 1)
	{
		delete _matrix_rows_; _matrix_rows_ = 0;
	}

	delete _primary_key_; _primary_key_ = 0;
}

void txStateMatrix::set(const txAbsPeer& row, const txAbsPeer& col,
	const txState& value)
{
	txStateVector* row_vector;

	ensureSquare(col);

	row_vector = ensureSquare(row);

	row_vector->set(col, value);
}

txStateVector* txStateMatrix::ensureSquare (const txAbsPeer& row)
{
	txStateVector* vector = (txStateVector*) _matrix_rows_->get(row);
	txStateVector* rowvector;
	const txAbsPeer* key = 0;

	if (!vector)
	{
		txStateObject defaultstate(0);
		vector = new txStateVector(1);

		txHashMapIterator it((txHashMap&) *_matrix_rows_->getTable());

		while (key = (txAbsPeer*) it.next())
		{
			rowvector = (txStateVector*) it.value();
			rowvector->set(row, defaultstate);
			vector->set(*key, defaultstate);
		}

		vector->set(row, defaultstate);
		_matrix_rows_->set(row, *vector);
		delete vector;

		vector = (txStateVector*) _matrix_rows_->get(row);
	}

	return vector;
}

const txState* txStateMatrix::get (const txAbsPeer& row,
	const txAbsPeer& col) const
{
	txStateVector* vec = (txStateVector*) _matrix_rows_->get(row);
	const txState* obj = 0;

	if (vec)
	{
		obj = vec->get(col);
	}

	return obj;
}

void txStateMatrix::setRow (const txAbsPeer& row,
	const txStateVector& row_vec)
{
	txHashMapIterator iter((txHashMap&) *row_vec.getTable());
	const txState* row_obj;
	const txAbsPeer* col;

	while (col = (txAbsPeer*) iter.next())
	{
		if (row_obj = row_vec.get(*col))
		{
			set(row, *col, *row_obj);
		}
	}
}

const txState* txStateMatrix::getRow (const txAbsPeer& row) const
{
	return _matrix_rows_->get(row);
}

const txState* txStateMatrix::getRow (void) const
{
	txHashMapIterator iter((txHashMap&) *(_matrix_rows_->getTable()));

	return _matrix_rows_->get((txAbsPeer&) *(iter.next()));
}

void txStateMatrix::del (const txAbsPeer& vector_key) const
{
	txStateVector* vec = (txStateVector*) _matrix_rows_->get(vector_key);
	const txAbsPeer* row;

	if (vec)
	{
		txHashMapIterator iter(
			(txHashMap&) *_matrix_rows_->getTable());

		while (row = (txAbsPeer*) iter.next())
		{
			vec = (txStateVector*) _matrix_rows_->get(*row);

			vec->del(vector_key);
		}

		_matrix_rows_->del(vector_key);
	}
}

const txState* txStateMatrix::getCol (const txAbsPeer& col) const
{
	txStateVector* new_vector = new txStateVector(1); 
	txHashMapIterator iter((txHashMap&) *_matrix_rows_->getTable());
	const txAbsPeer* row;
	txStateVector* vec;

	while (row = (txAbsPeer*) iter.next())
	{
		vec = (txStateVector*) _matrix_rows_->get(*row);
		new_vector->set(*row, *((txState*) vec->get(col)));
	}

	return new_vector;
}

txState* txStateMatrix::clone (void)
{
	return new txStateMatrix(*this);
}

txStateMatrix& txStateMatrix::operator= (const txStateMatrix& sm_obj)
{
	if (this != &sm_obj)
	{
		if (refcount() == 1)
		{
			delete _matrix_rows_; _matrix_rows_ = 0;
		}

		txState::operator=(sm_obj);
    	}

	return *this;
}

signed short int txStateMatrix::state (const txAbsPeer* exclude_key) const
{
	return _matrix_rows_->state(exclude_key);
}

signed short int txStateMatrix::state (const txPeerList* exclude_keys) const
{
	return _matrix_rows_->state(exclude_keys);
}

signed short int txStateMatrix::rowState (const txAbsPeer& row,
	const txAbsPeer& exclude_key) const
{
	signed short int state = 0;
	const txState* the_row = getRow(row);

	if (the_row)
	{
		state = the_row->state(&exclude_key);
	}

	return state;
}

signed short int txStateMatrix::rowState (const txAbsPeer& row,
	const txPeerList& exclude_keys) const
{
	signed short int state = 0 ;
	const txState* the_row = getRow(row);

	if (the_row)
	{
		state = the_row->state(&exclude_keys);
	}

	return state;
}

signed short int txStateMatrix::colState (const txAbsPeer& col,
	const txAbsPeer& exclude_key) const
{
	txHashMapIterator iter((txHashMap&) *_matrix_rows_->getTable());
	signed short int l_state = 0;
	signed short int r_state = 0;
	const txState* st_object;
	const txAbsPeer* row;
	txStateVector* vec;

	while (row = (txAbsPeer*) iter.next())
	{
		if (!row->equals(&exclude_key))
		{
			vec = (txStateVector*) _matrix_rows_->get(*row);
			st_object = vec->get(col);
			l_state = st_object->state(&exclude_key);

			break;
		}
	}

	while (row = (txAbsPeer*) iter.next())
	{
		if (!row->equals(&exclude_key))
		{
			vec = (txStateVector*) _matrix_rows_->get(*row);
			st_object = vec->get(col);

			if (l_state!=(r_state=st_object->state(&exclude_key)))
			{
				#if !defined TX_AGREE_UPON_AGREEING
				// this is a work around when peer1 can talk
				// with peer2, but peer3 cannot talk with peer2
				// ... etc.

				if ((l_state != txStateCtMtx::ESTABLISHED &&
					l_state != txStateCtMtx::LOST) ||
					(r_state != txStateCtMtx::ESTABLISHED &&
					r_state != txStateCtMtx::LOST))
				{
				#endif

					l_state = 0;
					break;

				#if !defined TX_AGREE_UPON_AGREEING
				}
				#endif
			}
		}
	}

	return l_state;
}

signed short int txStateMatrix::colState (const txAbsPeer& col,
	const txPeerList& exclude_keys) const
{
	txHashTable table;
	txStateVector* vec;
	const txAbsPeer* row;
	const txState* st_object;
	signed short int l_state = 0;
	signed short int r_state = 0;
	txPeerListIterator list_iter((txPeerList&) exclude_keys);
	txHashMapIterator map_iter((txHashMap&)*_matrix_rows_->getTable());

	while (row = list_iter.next()) // put in table to make lookups faster
	{
		table.insert((txAbsPeer*) row);
	}

	while (row = (txAbsPeer*) map_iter.next())
	{
		if (!table.find(row))
		{
			vec = (txStateVector*) _matrix_rows_->get(*row);
			st_object = vec->get(col);
			l_state = st_object->state(&exclude_keys);
			break;
		}
	}

	while (row = (txAbsPeer*) map_iter.next())
	{
		if (!table.find(row))
		{
			vec = (txStateVector*) _matrix_rows_->get(*row);
			st_object = vec->get(col);

			if (l_state!=(r_state=st_object->state(&exclude_keys)))
			{
				#if !defined TX_AGREE_UPON_AGREEING
				// this is a work around when peer1 can talk
				// with peer2, but peer3 cannot talk with peer2
				// ... etc.

				if ((l_state != txStateCtMtx::ESTABLISHED &&
					l_state != txStateCtMtx::LOST) ||
					(r_state != txStateCtMtx::ESTABLISHED &&
					r_state != txStateCtMtx::LOST))
				{
				#endif

					l_state = 0;
					break;

				#if !defined TX_AGREE_UPON_AGREEING
				}
				#endif
			}
		}
	}

	return l_state;
}

void txStateMatrix::setPrimaryKey (const txAbsPeer& key)
{
	delete _primary_key_; _primary_key_ = 0;

	_primary_key_ = key.clone();
}

const txAbsPeer* txStateMatrix::getPrimaryKey (void) const
{
	return _primary_key_;
}


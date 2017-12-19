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

#if !defined ( __TXIN_H__ )
#define __TXIN_H__

#include "txsmrtbuf.h"
#include "txtypecodes.h"

class txOut;
class txObject;
class txHashMap;

class txIn
{
	private:
		const char* _end_;
		const char* _cursor_;
		short _generic_type_;
		txHashMap* _map_context_;
		unsigned long _userflags_;
		unsigned long _currentLength_;
		unsigned long _currentHeader_;

		// if coping data, use this attribute
		txSmartBuffer* _datastream_;

		// if referencing data, use these attributes
		char* _init_data_;
		unsigned long _init_length_;

	private:
		inline void _reCalAsciiLength_ (void);

		inline unsigned long _get_ (unsigned long);

		inline int _readHeader_ (void);
		inline int _readHeaderThenTypeMatch_ (unsigned long);

		inline int _variableLengthOverlay_ (txObject&);
		inline txObject* _variableLengthDestream_ (int&);

	public:
		int _typeMatchCurrentHeader_ (unsigned long) const;

	public:
		txIn (const txOut& out);                        // copy data
		txIn (char* data, unsigned long length);        // ref. data
		txIn (const char* data, unsigned long length);  // copy data

		~txIn (void);

		int getNull (void);

		txIn& operator>> (float& o);			// 32 bits
		txIn& operator>> (double& o);			// 64 bits

		txIn& operator>> (char*& o);			// ASCII

		txIn& operator>> (signed char& o);		// 8 bits
		txIn& operator>> (unsigned char& o);		// 8 bits

		txIn& operator>> (signed short int& o);		// 16 bits
		txIn& operator>> (signed long int& o);		// 32 bits

		txIn& operator>> (unsigned short int& o);	// 16 bits
		txIn& operator>> (unsigned long int& o);	// 32 bits

		txObject* destream (void);
		void destream (txObject& o, int overlay_different_type = 0);

		unsigned long get (char*&, unsigned long);
		// ASCII needs len + 1 memory to set the null character
		unsigned long get (char*, unsigned long, unsigned long);

		const char* cursor (void) const;
		unsigned long index (void) const;
		void cursor (unsigned long index);
		unsigned long objectLength (void) const;

		void flush (void);
		const char* data (void) const;
		unsigned long length (void) const;

		// Generic Type Utilities //

		void genericTypingOn (void);
		void genericTypingOff (void);
		int genericTyping (void) const;

		// User Flag Utilities //

		void userFlags (unsigned long);
		unsigned long userFlags (void) const;
};

#endif // __TXIN_H__

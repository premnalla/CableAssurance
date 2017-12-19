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
 
#if !defined ( __TXGVECTOR_H__ )
#define __TXGVECTOR_H__

#include "txutil.h"
#include "txobject.h"

#if defined OSTORE_SUPPORT
#define TX_ALLOC_VECTOR(TYPE,self,n)                                          \
        if (objectstore::is_persistent(self))                                 \
        {                                                                     \
                self->_array = new(TX_GET_MEM_LOCATION(self),                 \
                        os_typespec::get_pointer(), n) TYPE[n];               \
        }                                                                     \
        else                                                                  \
        {                                                                     \
                self->_array = new TYPE[n];                                   \
        }
#else
#define TX_ALLOC_VECTOR(TYPE,self,n) self->_array = new TYPE[n];
#endif

#define TX_DECLARE_GENERIC_VECTOR(TYPE,NAME)                                  \
                                                                              \
class NAME : public txObject                                                  \
{                                                                             \
        TX_PERSIST                                                            \
                                                                              \
        protected:                                                            \
                int _items;                                                   \
                TYPE* _array;                                                 \
                                                                              \
        public:                                                               \
                inline NAME (void);                                           \
                                                                              \
                inline NAME (int n);                                          \
                                                                              \
                NAME (int n, TYPE x);                                         \
                                                                              \
                inline ~NAME (void);                                          \
                                                                              \
                NAME (const NAME &x);                                         \
                                                                              \
                NAME& operator= (const NAME &x);                              \
                                                                              \
                NAME& operator= (TYPE x);                                     \
                                                                              \
                inline TYPE& operator[] (int i);                              \
                                                                              \
                inline TYPE operator[] (int i) const;                         \
                                                                              \
                inline const TYPE* data (void) const;                         \
                                                                              \
                inline int length (void) const;                               \
                                                                              \
                void resize (int n, TYPE x);                                  \
                void resize (int n);                                          \
};                                                                            \
                                                                              \
inline NAME::NAME (void) :                                                    \
        _items(0),                                                            \
        _array(0)                                                             \
{                                                                             \
}                                                                             \
                                                                              \
inline NAME::NAME (int n) :                                                   \
        _items(n)                                                             \
{                                                                             \
        TX_ALLOC_VECTOR(TYPE,this,n)                                          \
}                                                                             \
                                                                              \
inline NAME::~NAME (void)                                                     \
{                                                                             \
        delete [] _array;                                                     \
}                                                                             \
                                                                              \
inline TYPE& NAME::operator[] (int i)                                         \
{                                                                             \
        return _array[i];                                                     \
}                                                                             \
                                                                              \
inline TYPE NAME::operator[] (int i) const                                    \
{                                                                             \
        return _array[i];                                                     \
}                                                                             \
                                                                              \
inline const TYPE* NAME::data (void) const                                    \
{                                                                             \
        return _array;                                                        \
}                                                                             \
                                                                              \
inline int NAME::length (void) const                                          \
{                                                                             \
        return _items;                                                        \
}

#define TX_DEFINE_GENERIC_VECTOR(TYPE,NAME)                                   \
                                                                              \
NAME::NAME (int n, TYPE x)                                                    \
{                                                                             \
        TX_ALLOC_VECTOR(TYPE,this,n)                                          \
        register int i = _items = n;                                          \
        register TYPE* y = _array;                                            \
                                                                              \
        while (i--)                                                           \
        {                                                                     \
                *y++ = x;                                                     \
        }                                                                     \
}                                                                             \
                                                                              \
NAME::NAME (const NAME &x)                                                    \
{                                                                             \
        TX_ALLOC_VECTOR(TYPE,this,x._items)                                   \
        register int i = _items = x._items;                                   \
        register TYPE* z = x._array;                                          \
        register TYPE* y = _array;                                            \
                                                                              \
        while (i--)                                                           \
        {                                                                     \
                *y++ = *z++;                                                  \
        }                                                                     \
}                                                                             \
                                                                              \
NAME& NAME::operator= (const NAME &x)                                         \
{                                                                             \
        if (this != &x)                                                       \
        {                                                                     \
                delete [] _array; _array = 0;                                 \
                                                                              \
                TX_ALLOC_VECTOR(TYPE,this,x._items)                           \
                register int i = _items = x._items;                           \
                register TYPE* z = x._array;                                  \
                register TYPE* y = _array;                                    \
                                                                              \
                while (i--)                                                   \
                {                                                             \
                        *y++ = *z++;                                          \
                }                                                             \
        }                                                                     \
                                                                              \
        return *this;                                                         \
}                                                                             \
                                                                              \
NAME& NAME::operator= (TYPE x)                                                \
{                                                                             \
        register int i = _items;                                              \
                                                                              \
        while (i--)                                                           \
        {                                                                     \
                  _array[i] = x;                                              \
        }                                                                     \
                                                                              \
        return *this;                                                         \
}                                                                             \
                                                                              \
void NAME::resize (int n, TYPE x)                                             \
{                                                                             \
        if (n == _items)                                                      \
        {                                                                     \
                return;                                                       \
        }                                                                     \
                                                                              \
        TYPE* array;                                                          \
        register TYPE* z = array = _array;                                    \
                                                                              \
        TX_ALLOC_VECTOR(TYPE,this,n)                                          \
                                                                              \
        register TYPE* y = _array;                                            \
        register int j = (n > _items) ? n : 0;                                \
        register int i = (n <= _items) ? n : _items;                          \
                                                                              \
        while (i--)                                                           \
        {                                                                     \
                *y++ = *z++;                                                  \
        }                                                                     \
                                                                              \
        while (j--)                                                           \
        {                                                                     \
                *y++ = x;                                                     \
        }                                                                     \
                                                                              \
        delete [] array;                                                      \
        _items = n;                                                           \
}                                                                             \
                                                                              \
void NAME::resize (int n)                                                     \
{                                                                             \
        if (n == _items)                                                      \
        {                                                                     \
                return;                                                       \
        }                                                                     \
                                                                              \
        TYPE* array;                                                          \
        register TYPE* z = array = _array;                                    \
                                                                              \
        TX_ALLOC_VECTOR(TYPE,this,n)                                          \
                                                                              \
        register TYPE* y = _array;                                            \
        register int i = (n <= _items) ? n : _items;                          \
                                                                              \
        while (i--)                                                           \
        {                                                                     \
                *y++ = *z++;                                                  \
        }                                                                     \
                                                                              \
        delete [] array;                                                      \
        _items = n;                                                           \
}
     
#endif // __TXGVECTOR_H__

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
 
#if !defined ( __TXGLIST_H__ )
#define __TXGLIST_H__

#include "txutil.h"
#include "sys/txsyslist.h"

#define TX_DECLARE_GENERIC_LIST(TYPE,NAME)                                    \
        TX_DECLARE_GENERIC_LIST_WITH_CLRDEL_TYPE(TYPE,NAME,TYPE)

#define TX_DECLARE_GENERIC_LIST_WITH_CLRDEL_TYPE(TYPE,NAME,CLRDEL)            \
                                                                              \
typedef int (*TX_TEST_##TYPE) (const TYPE*, const TYPE*);                     \
                                                                              \
class NAME : public txSysList                                                 \
{                                                                             \
    public:                                                                   \
        NAME (void)                                                           \
        {                                                                     \
        }                                                                     \
                                                                              \
        const TYPE* at (int i) const                                          \
        {                                                                     \
            return (TYPE*) txSysList::_at(i);                                 \
        }                                                                     \
                                                                              \
        const TYPE* append (TYPE* x)                                          \
        {                                                                     \
            return (TYPE*) txSysList::_append(x);                             \
        }                                                                     \
                                                                              \
        const TYPE* prepend (TYPE* x)                                         \
        {                                                                     \
            return (TYPE*) txSysList::_prepend(x);                            \
        }                                                                     \
                                                                              \
        int index (TYPE* x)                                                   \
        {                                                                     \
            return txSysList::_index(x);                                      \
        }                                                                     \
                                                                              \
        const TYPE* insertAfter (int i, TYPE* x)                              \
        {                                                                     \
            return (TYPE*) txSysList::_insertAfter(i, x);                     \
        }                                                                     \
                                                                              \
        const TYPE* insertAt (int i, TYPE* x)                                 \
        {                                                                     \
            return (TYPE*) txSysList::_insertAt(i, x);                        \
        }                                                                     \
                                                                              \
        const TYPE* findReference (TX_TEST_##TYPE f, const TYPE* x) const     \
        {                                                                     \
            return (TYPE*) txSysList::_findReference((TX_TEST_NODE) f, x);    \
        }                                                                     \
                                                                              \
        const TYPE* findReference (const TYPE* x) const                       \
        {                                                                     \
            return (TYPE*) txSysList::_findReference(x);                      \
        }                                                                     \
                                                                              \
        TYPE* removeReference (TX_TEST_##TYPE f, const TYPE* x)               \
        {                                                                     \
            return (TYPE*) txSysList::_removeReference((TX_TEST_NODE) f, x);  \
        }                                                                     \
                                                                              \
        TYPE* removeReference (const TYPE* x)                                 \
        {                                                                     \
            return (TYPE*) txSysList::_removeReference(x);                    \
        }                                                                     \
                                                                              \
        TYPE* get (void)                                                      \
        {                                                                     \
            return (TYPE*) txSysList::_get();                                 \
        }                                                                     \
                                                                              \
        const TYPE* first (void) const                                        \
        {                                                                     \
            return (TYPE*) txSysList::_first();                               \
        }                                                                     \
                                                                              \
        const TYPE* last (void) const                                         \
        {                                                                     \
            return (TYPE*) txSysList::_last();                                \
        }                                                                     \
                                                                              \
        int entries (void) const                                              \
        {                                                                     \
            return txSysList::_entries();                                     \
        }                                                                     \
                                                                              \
        void clear (void)                                                     \
        {                                                                     \
            txSysList::_clear();                                              \
        }                                                                     \
                                                                              \
        void clearAndDestroy (void)                                           \
        {                                                                     \
            CLRDEL* node;                                                     \
                                                                              \
            while (node = (CLRDEL*) get())                                    \
            {                                                                 \
                    delete node; node = 0;                                    \
            }                                                                 \
        }                                                                     \
};                                                                            \
                                                                              \
class NAME##Iterator : public txSysListIterator                               \
{                                                                             \
    public:                                                                   \
        NAME##Iterator (void)                                                 \
        {                                                                     \
        }                                                                     \
                                                                              \
        NAME##Iterator (NAME &list) :                                         \
            txSysListIterator(list)                                           \
        {                                                                     \
        }                                                                     \
                                                                              \
        const TYPE* key (void) const                                          \
        {                                                                     \
            return (TYPE*) txSysListIterator::_key();                         \
        }                                                                     \
                                                                              \
        const TYPE* next (void)                                               \
        {                                                                     \
            return (TYPE*) txSysListIterator::_next();                        \
        }                                                                     \
                                                                              \
        TYPE* remove (void)                                                   \
        {                                                                     \
            return (TYPE*) txSysListIterator::_remove();                      \
        }                                                                     \
                                                                              \
        void reset (void)                                                     \
        {                                                                     \
            txSysListIterator::_reset();                                      \
        }                                                                     \
                                                                              \
        void reset (NAME& list)                                               \
        {                                                                     \
            txSysListIterator::_reset((txSysList&) list);                     \
        }                                                                     \
};

#endif // __TXGLIST_H__

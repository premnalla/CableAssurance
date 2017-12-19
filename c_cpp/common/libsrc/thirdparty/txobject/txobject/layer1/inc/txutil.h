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

#if !defined ( __TXUTIL_H__ )
#define __TXUTIL_H__

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_STATIC_ALLOC_NOTIFY(f,p,n)                                         \
        class txStaticNotify##n                                               \
        {                                                                     \
                public:                                                       \
                       txStaticNotify##n (void) { f##p; }                     \
        };                                                                    \
                                                                              \
        static txStaticNotify##n txStaticNotifyValue##n;

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_STATIC_DEALLOC_NOTIFY(f,p,n)                                       \
        class txStaticNotify##n                                               \
        {                                                                     \
                public:                                                       \
                       ~txStaticNotify##n (void) { f##p; }                    \
        };                                                                    \
                                                                              \
        static txStaticNotify##n txStaticNotifyValue##n;

////////////////////////////////// USER MACRO /////////////////////////////////
#if defined OSTORE_SUPPORT
        #include <ostore/ostore.hh>

        #define TX_PERSIST                                                    \
                public:                                                       \
                static os_typespec* get_os_typespec();

        #define TX_GET_OBJECT_CLUSTER(ptr) os_segment::of(ptr)
        #define TX_IS_OBJECT_PERSISTENT(ptr) objectstore::is_persistent(ptr)
#else
        #define TX_PERSIST
#endif

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_CRASH (*(int*) 0x0 = 1);

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DECLARE_STREAM_TYPE(c)                                             \
        TX_DECLARE_STREAM_TYPE_WITH_ALIAS(c,c)

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DECLARE_STREAM_TYPE_WITH_ALIAS(c,alias)                            \
        public:                                                               \
        TX_DEFINE_TYPE(alias)                                                 \
        friend txStream* txCreate##c##Func (void);

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DEFINE_STREAM_TYPE(c,p)                                            \
        TX_DEFINE_STREAM_TYPE_WITH_ALIAS(c,p,c)

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DEFINE_STREAM_TYPE_WITH_ALIAS(c,p,alias)                           \
        TX_DEFINE_PARENT_TYPE_WITH_ALIAS(c,p,alias)                           \
                                                                              \
        txStream* txCreate##c##Func (void)                                    \
        {                                                                     \
                return new c();                                               \
        }                                                                     \
                                                                              \
        static txFactoryBinder txFactoryBinder##c(c##::Type,txCreate##c##Func);

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DECLARE_STREAM_TYPE_OPER(c)                                        \
        inline txIn& operator>> (txIn& in, c& self)                           \
        {                                                                     \
                in.destream(self);                                            \
                return in;                                                    \
        }                                                                     \
                                                                              \
        inline txIn& operator>> (txIn& in, c*& self)                          \
        {                                                                     \
                self = (c*) in.destream();                                    \
                return in;                                                    \
        }

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DEFINE_TYPE(c)                                                     \
        TX_PERSIST                                                            \
                                                                              \
        public:                                                               \
                                                                              \
        static const txString Type;                                           \
                                                                              \
        virtual const char* className (void) const                            \
        {                                                                     \
               return #c;                                                     \
        }                                                                     \
                                                                              \
        virtual const txString& classType (void) const                        \
        {                                                                     \
               return Type;                                                   \
        }

////////////////////////////////// USER MACRO /////////////////////////////////
#define TX_DEFINE_PARENT_TYPE(c,p)                                            \
        TX_DEFINE_PARENT_TYPE_WITH_ALIAS(c,p,c)

#define TX_DEFINE_PARENT_TYPE_WITH_ALIAS(c,p,alias)                           \
        const txString c##::Type(#alias);                                     \
                                                                              \
        class txTypeBinder##c##p                                              \
        {                                                                     \
                public:                                                       \
                        txTypeBinder##c##p (void)                             \
                        {                                                     \
                                txTypeCheckSS::addRelation(#alias, #p);       \
                        }                                                     \
        };                                                                    \
                                                                              \
        static txTypeBinder##c##p typeBinder##c##p;

#endif // __TXUTIL_H__

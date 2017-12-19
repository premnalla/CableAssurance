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
 
#if !defined ( __TXDOBJUTIL_H__ )
#define __TXDOBJUTIL_H__

#include <stdio.h> 

#include "txint16.h"
#include "txobject.h"
#include "txstring.h"
#include "txdobjptr.h"
#include "txdobjglbls.h"

class txAbsPeer;

typedef txObject* (*TX_METHOD_PTR)(void*, txIn&, const txAbsPeer&);

class txDObjMethod : public txObject
{
	private:
		TX_METHOD_PTR _method_ptr_;

	public:
		txDObjMethod (TX_METHOD_PTR method) :
			_method_ptr_(method)
		{
		}

		~txDObjMethod (void)
		{
		}

		TX_METHOD_PTR getMethod (void) const
		{
			return _method_ptr_;
		}

		txObject* apply (void* self, txIn& in, const txAbsPeer& peer)
		{
			return _method_ptr_(self, in, peer);
		}
};

class txOffSet : public txString
{
	private:
		int _offset_;

	public:
		txOffSet (void)
		{
		}

		txOffSet (const char* type, txObject** a, txObject* self) :
			txString(type), _offset_((int) ((int) a - (int) self))
		{
		}

		~txOffSet (void)
		{
		}

		txObject** get (txObject* self)
		{
			return (txObject**) ((int) self + _offset_);
		}

		int offset (void)
		{
			return _offset_;
		}
};

#define TX_INTERNAL_FETCH(A)                                                  \
  (txDObjMgr*) txDObjGlobals::txDObjMgrs.findValue(A)

#define TX_FETCH_DOBJMGR(A)                                                   \
  TX_INTERNAL_FETCH(A);

#define TX_REGISTER_DOBJECT(T,R,P,A)                                          \
  R=(T*)((txDObjMgr*) TX_INTERNAL_FETCH(A))->registerObject(R,P);

#define TX_REGISTER_NAMED_DOBJECT(T,R,G,A)                                    \
  R=(T*)((txDObjMgr*) TX_INTERNAL_FETCH(A))->registerGlobalObject(R,G);

#define TX_REGISTER_NAMED_DOBJECT_IN_SPACE(T,R,P,G,A)                         \
  R=(T*)((txDObjMgr*) TX_INTERNAL_FETCH(A))->registerGlobalObject(R,G,P);

#define TX_UNREGISTER_DOBJECT(T,R,A)                                          \
  R=(T*)((txDObjMgr*) TX_INTERNAL_FETCH(A))->unregisterObject(R->oid());


class txNull : public txStream
{
	TX_DECLARE_STREAM_TYPE(txNull)

	public:
		txNull (void) {}

		~txNull (void) {}

		void storeInners (txOut&) const {}

		void restoreInners (txIn&) {}
};

TX_DECLARE_STREAM_TYPE_OPER(txNull)

class txDObject;

class txDObjUpdate
{
	public:
	static void attribute (
		txDObject*, txInt16&, txString&, txObject**, txIn&);

	static void attrWrite (
		txDObject*, txObject**, txString&, txIn&);

	static void attrWriteIndex (
		txDObject*, txObject**, txString&, txIn&);

	static void attrDelete (
		txDObject*, txObject**, txString&);

	static void attrDeleteIndex (
		txDObject*, txObject**, txString&, txIn&);

	static void changeOwner (
		txDObject*, txObject**, txIn&);
};


#define TX_INITIALIZE_DOBJECT()                                               \
   if (!_TX_R_) { _TX_R_ = 1; registerInners(); }

#define TX_DECLARE_DOBJECT(TYPE)                                              \
TX_DECLARE_DOBJECT_WITH_ALIAS(TYPE,TYPE)

#define TX_DECLARE_DOBJECT_WITH_ALIAS(TYPE,ALIAS)                             \
 TX_DECLARE_STREAM_TYPE_WITH_ALIAS(TYPE,ALIAS)                                \
                                                                              \
 private:                                                                     \
   static int _TX_R_;                                                         \
   static txHashMap _TX_M_;                                                   \
   static txHashTable _TX_A_;                                                 \
                                                                              \
   void restoreComplete (void);                                               \
   void registerInners (void) const;                                          \
   void registerAttribute (const char*, const void*) const;                   \
   void registerTwoWayMethod (const char*, TX_METHOD_PTR) const;              \
                                                                              \
 protected:                                                                   \
   void storeInners (txOut& out) const;                                       \
   void restoreInners (txIn& in);                                             \
   virtual void _updateAttribute_ (txInt16&, txString&, txIn&);               \
   virtual txObject* _apply_ (const txString&, txIn&, const txAbsPeer&);      \
                                                                              \
 public:                                                                      \
   TYPE* operator-> (void);

#define TX_DEFINE_DOBJECT(TYPE,PARENT)                                        \
        TX_DEFINE_DOBJECT_WITH_ALIAS(TYPE,PARENT,TYPE)

#define TX_DEFINE_DOBJECT_WITH_ALIAS(TYPE,PARENT,ALIAS)                       \
 int TYPE::_TX_R_ = 0;                                                        \
 txHashMap TYPE::_TX_M_(0, TX_AUTODEL_ON);                                    \
 txHashTable TYPE::_TX_A_(0, TX_AUTODEL_ON);                                  \
                                                                              \
 TX_DEFINE_STREAM_TYPE_WITH_ALIAS(TYPE,PARENT,ALIAS)                          \
                                                                              \
 txObject* TYPE::_apply_ (const txString& n, txIn& in, const txAbsPeer& peer) \
 {                                                                            \
        txObject* v;                                                          \
        txDObjMethod* method;                                                 \
       	                                                                      \
        TX_INITIALIZE_DOBJECT()                                               \
       	                                                                      \
        if ((method = (txDObjMethod*) _TX_M_.findValue(&n)))                  \
        {                                                                     \
                v = method->apply(this, in, peer);                            \
        }                                                                     \
        else                                                                  \
        {                                                                     \
                v = PARENT::_apply_(n, in, peer);                             \
        }                                                                     \
       	                                                                      \
        return v;                                                             \
 }                                                                            \
                                                                              \
 void TYPE::_updateAttribute_ (txInt16& type, txString& attr, txIn& in)       \
 {                                                                            \
        txOffSet* offset;                                                     \
                                                                              \
        TX_INITIALIZE_DOBJECT()                                               \
                                                                              \
        if(!(offset = (txOffSet*) _TX_A_.find(&attr)))                        \
        {                                                                     \
                PARENT::_updateAttribute_(type, attr, in);                    \
                return;                                                       \
        }                                                                     \
                                                                              \
        txDObjUpdate::attribute(this, type, attr, offset->get(this), in);     \
 }                                                                            \
                                                                              \
 void TYPE::registerAttribute (const char* name, const void* attr) const      \
 {                                                                            \
        txString lu(name);                                                    \
                                                                              \
        if (!_TX_A_.find(&lu))                                                \
        {                                                                     \
         _TX_A_.insert(new txOffSet(name,(txObject**)attr,(txObject*) this)); \
        }                                                                     \
        else                                                                  \
        {                                                                     \
         fprintf(stderr, "TXOBJECT[error] : dup. registerAttribute\n");       \
         fflush(stderr); TX_CRASH;                                            \
        }                                                                     \
 }                                                                            \
                                                                              \
 void TYPE::registerTwoWayMethod (const char* name, TX_METHOD_PTR meth) const \
 {                                                                            \
        txString lu(name);                                                    \
                                                                              \
        if (!_TX_M_.find(&lu))                                                \
        {                                                                     \
         _TX_M_.insertKeyAndValue(new txString(name),new txDObjMethod(meth)); \
        }                                                                     \
        else                                                                  \
        {                                                                     \
         fprintf(stderr, "TXOBJECT[error] : dup. registerTwoWayMethod.\n");   \
         fflush(stderr); TX_CRASH;                                            \
        }                                                                     \
 }                                                                            \
                                                                              \
 void TYPE::storeInners (txOut& out) const                                    \
 {                                                                            \
        PARENT::storeInners(out);                                             \
                                                                              \
        txOffSet* offset;                                                     \
                                                                              \
        TX_INITIALIZE_DOBJECT()                                               \
                                                                              \
        out << (signed short int) TYPE::_TX_A_.entries();                     \
                                                                              \
        txHashTableIterator iter(TYPE::_TX_A_);                               \
                                                                              \
        while (offset = (txOffSet*) iter.next())                              \
        {                                                                     \
                out.put(offset);                                              \
                out.put(*offset->get((txObject*) this));                      \
        }                                                                     \
 }                                                                            \
                                                                              \
 void TYPE::restoreInners (txIn& in)                                          \
 {                                                                            \
        PARENT::restoreInners(in);                                            \
                                                                              \
        txObject* v;                                                          \
        txObject* o;                                                          \
        txOffSet offset;                                                      \
        txObject** attr;                                                      \
                                                                              \
        TX_INITIALIZE_DOBJECT()                                               \
                                                                              \
        signed short int entries; in >> entries;                              \
                                                                              \
        for (int i = 0; i < entries; i++)                                     \
        {                                                                     \
                in >> offset; in >> v;                                        \
                                                                              \
                if (o = (txObject*) _TX_A_.find(&offset))                     \
                {                                                             \
                        attr = ((txOffSet*) o)->get(this); *attr = v;         \
                }                                                             \
                else                                                          \
                {                                                             \
                        delete v; v = 0;                                      \
                }                                                             \
        }                                                                     \
                                                                              \
        TYPE::restoreComplete();                                              \
 }                                                                            \
                                                                              \
 TYPE* TYPE::operator-> (void)                                                \
 {                                                                            \
         TYPE* self = (TYPE*) ((txDObjPtr*) this)->_getObject_();             \
                                                                              \
         if (!self)                                                           \
         {                                                                    \
                 fprintf(stderr, "TXOBJECT[error] : invalid smart ptr ");     \
                 fprintf(stderr, "[%s]\n", #TYPE);                            \
                 fflush(stdout); TX_CRASH;                                    \
         }                                                                    \
                                                                              \
         return self;                                                         \
 }

#endif // __TXDOBJUTIL_H__

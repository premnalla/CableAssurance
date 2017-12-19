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
#include "txbtree.h"
#include "txdobject.h"
#include "txhashmap.h"
#include "txhashtable.h"
#include "sys/txdobjutil.h"

TX_DEFINE_STREAM_TYPE(txNull,txStream)

void txDObjUpdate::attribute (txDObject* self, txInt16& type, txString& name,
	txObject** attr, txIn& in)
{
	switch(type.value())
	{
		case txDObject::ATTR_WRITE:
		{
			txDObjUpdate::attrWrite(self, attr, name, in);
			break;
		}
		case txDObject::ATTR_WRITE_INDEX:
		{
			txDObjUpdate::attrWriteIndex(self, attr, name, in);
			break;
		}
		case txDObject::ATTR_DELETE:
		{
			txDObjUpdate::attrDelete(self, attr, name);
			break;
		}
		case txDObject::ATTR_DELETE_INDEX:
		{
			txDObjUpdate::attrDeleteIndex(self, attr, name, in);
			break;
		}
		case txDObject::CHANGE_OWNER:
		{
			txDObjUpdate::changeOwner(self, attr, in);
		}
	}
}

void txDObjUpdate::attrWrite (txDObject* self, txObject** attr,
	txString& name, txIn& in)
{
	txObject* o = *attr; in >> *attr;

	self->attrWriteNotify(name.data(), o, *attr);

	delete o; o = 0;
}

void txDObjUpdate::attrWriteIndex (txDObject* self, txObject** attr,
	txString& name, txIn& in)
{
	txObject* n;
	txObject* o = 0;

	if ((*attr)->isClass(txBTree::Type))
	{
		in >> n; txBTree* tree = (txBTree*) *attr;

		o = tree->remove(n); tree->insert(n);

		self->attrWriteIndexNotify(name.data(), n, o, n);

		in >> n; delete n; n = 0;
	}
	else if ((*attr)->isClass(txHashTable::Type))
	{
		in >> n; txHashTable* table = (txHashTable*) *attr;

		o = table->remove(n); table->insert(n);

		self->attrWriteIndexNotify(name.data(), n, o, n);

		in >> n; delete n; n = 0;
	}
	else if ((*attr)->isClass(txList::Type))
	{
		txInt16 index; txList* list = (txList*) *attr;

		in.destream(index, 1); // allow for overlay of different type

		in >> n;

		if (list->entries() <= index.value())
		{
			list->append(n);
		}
		else
		{
			o = (txObject*) list->at(index.value());

			list->removeReference(o);
			list->insertAt(index.value(), n);
		}

		self->attrWriteIndexNotify(name.data(), &index, o, n);
	}
	else if ((*attr)->isClass(txHashMap::Type))
	{
		txObject* k; int replaced = 0;
		txHashMap* dict = (txHashMap*) *attr; in >> k; in >> n;

		if (!dict->insertKeyAndValue(k, n))
		{
			o = dict->findValue(k, n);
			replaced = 1;
		}

		self->attrWriteIndexNotify(name.data(), k, o, n);

		if (replaced)
		{
			delete k; k = 0;
		}
	}

	delete o; o = 0;
}

void txDObjUpdate::attrDelete (txDObject* self, txObject** attr, txString& name)
{
	self->attrDeleteNotify(name.data(), *attr);

	delete *attr; *attr = 0;
}

void txDObjUpdate::attrDeleteIndex (txDObject* self, txObject** attr,
	txString& name, txIn& in)
{
	txObject* n;
	txObject* o = 0;

	if ((*attr)->isClass(txBTree::Type))
	{
		txBTree* tree = (txBTree*) *attr;

		in >> n; o = tree->remove(n);

		self->attrDeleteIndexNotify(name.data(), n, o);

		delete n; n = 0;
		delete o; o = 0;
	
	}
	else if ((*attr)->isClass(txHashTable::Type))
	{
		txHashTable* table = (txHashTable*) *attr;

		in >> n; o = table->remove(n);

		self->attrDeleteIndexNotify(name.data(), n, o);

		delete n; n = 0;
		delete o; o = 0;
	}
	else if ((*attr)->isClass(txList::Type))
	{
		txInt16 index; txList* list = (txList*) *attr;

		in.destream(index, 1); // allow for overlay of different type

		if (list->entries() > index.value())
		{
			o = (txObject*) list->at(index.value());
			list->remove(o);

			self->attrDeleteIndexNotify(name.data(), &index, o);

			delete o; o = 0;
		}
	}
	else if ((*attr)->isClass(txHashMap::Type))
	{
		txObject* k; txHashMap* dict = (txHashMap*) *attr; in >> n;

		if (k = dict->removeKeyAndValue(n, (txObject*&) o))
		{
			self->attrDeleteIndexNotify(name.data(), k, o);

			delete k; k = 0; delete o; o = 0;
		}

		delete n; n = 0;
	}
}

void txDObjUpdate::changeOwner (txDObject* self, txObject** attr, txIn& in)
{
	in >> *attr; txObject* o = *attr;

	self->changeOwnerNotify((txAbsPeer&) *o, (txAbsPeer&) **attr);

	delete o; o = 0;
}


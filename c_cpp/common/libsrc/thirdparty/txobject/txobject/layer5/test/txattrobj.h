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

#if !defined ( __TXATTROBJ_H__ )
#define __TXATTROBJ_H__

#include "txlist.h"
#include "txuint32.h"
#include "txdobject.h"
#include "txhashmap.h"

class txAttrObj : public txDObject
{
	TX_DECLARE_DOBJECT(txAttrObj)

	private:
		txList* _list_;
		txUInt32* _Count_;
		txHashMap* _dict_;

	public:	
		
		txAttrObj (void);
		~txAttrObj (void);

		void createNotify (void);

		void destroyNotify (void);

		void printList (void);
		void printDict (void);

		int getDictEntries (void);
		int getListEntries (void);

		void addDictEntry (txObject* index, txObject* value);
		void removeDictEntry (txObject* index);

		void addListEntry (txObject* index, txObject* value);
		void removeListEntry (txObject* index);

		void changeOwnerNotify (
			const txAbsPeer&, const txAbsPeer&);

		void attrWriteNotify (
			const char* attr_name,
			const txObject* old_object,
			const txObject* new_object);

		void attrWriteIndexNotify (
			const char* attr_name,
			const txObject* index,
			const txObject* old_object,
			const txObject* new_object);

		void attrDeleteIndexNotify (
			const char* attr_name,
			const txObject* index,
			const txObject* value);

};

#endif // __TXATTROBJ_H__

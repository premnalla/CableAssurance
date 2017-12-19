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
 
#if !defined ( __TXTYPECHECKSS_H__ )
#define __TXTYPECHECKSS_H__

class txList;
class txString;
class txHashMap;
class txHashTable;

class txTypeCheckSS
{
	private:
		static txHashMap* _subclassing_;
		static txHashMap* _superclassing_;
		static txHashTable* _class_type_table_;

	private:
		static void _connect_ (
			txHashMap* map,
			const char* src,
			const char* dest);

		static int _isConnected_ (
			txHashMap* map,
			const txString& src,
			const txString& dest);

		static int _findClassRelations_ (
			txHashMap* map,
			const txString& src,
			txList& user);

		static void _addTypeToList_ (
			txString& src,
			txList& user);

		static void _flushClassTypeTable_ (
			txHashTable* table);

		static void _flushClassDictionary_ (
			txHashMap* map);

	public:
		static void addRelation (const char* sub, const char* super)
		{
			startUp(); // to ensure memory init. before main.

			_connect_(_subclassing_, sub, super);
			_connect_(_superclassing_, super, sub);
		}

		static int isSubClass (const txString& sub,
			const txString& oftype)
		{
			return !isShutDown() && isClass(sub, oftype) || 
				_isConnected_(_subclassing_, sub, oftype);
		}

		static int isSuperClass (const txString& super,
			const txString& oftype)
		{
			return !isShutDown() && isClass(super, oftype) || 
				_isConnected_(_superclassing_, super, oftype);
		}

		static void subClasses (const txString& src, txList& l)
		{
			if (!isShutDown())
			{
				_addTypeToList_((txString&) src, l);
				_findClassRelations_(_subclassing_, src, l);
			}
		}

		static void superClasses (const txString& src, txList& l)
		{
			if (!isShutDown())
			{
				_addTypeToList_((txString&) src, l);
				_findClassRelations_(_superclassing_, src, l);
			}
		}

		static const txList* immediateSuperClasses (const txString&);

		static const txList* immediateSubClasses (const txString&);

		static int isClass (const txString&, const txString&);

		static const txString* findRTTIType (const char*);

		static int subClassEntries (void);

		static int superClassEntries (void);

		static void startUp (void);

		static void shutDown (void);

		static int isShutDown (void)
		{
			return _subclassing_ ? 0 : 1; // or _superclassing_
		}
};

//
// txHash : txObject id generator
//

unsigned txHash (const char* data, unsigned long length);

#endif // __TXTYPECHECKSS_H__

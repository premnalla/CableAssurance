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
 
#if !defined ( __TXBTREE_H__ )
#define __TXBTREE_H__

#include "txobject.h"
 
class txBtNode; // item
class txBtLNode; // leaf
class txBtBNode; // branch

class txBTree : public txObjectSequence
{
	TX_DEFINE_TYPE(txBTree)

	private:
		txBtNode* _root_;
		unsigned long _flags_;

		int _entries_;
		int _leaf_low_water_;
		int _leaf_max_index_;
		int _branch_low_water_;
		int _branch_max_index_;

	private:
		void _initialize_ (int i);
		void _notifyRootFull_ (void);
		void _notifyRootEmpty_ (void);

		void _incrementEntries_ (void) { _entries_++; }
		void _decrementEntries_ (void) { _entries_--; }

	public:
		txBTree (
			int order = 3,
			TX_AUTODEL_FLAG autodel = TX_AUTODEL_OFF,
			TX_COMPARE_FLAG compare = TX_COMPARE_V2K)
		{
			TX_SET_AUTODEL_FLAG(_flags_, autodel);
			TX_SET_COMPARE_FLAG(_flags_, compare);

			_initialize_(order);
		}

		~txBTree (void)
		{
			if (TX_GET_AUTODEL_FLAG(_flags_))
			{
				clearAndDestroy();
			}
			else
			{
				clear();
			}
		}

		virtual txObject* remove (const txObject* obj);
		virtual const txObject* insert (txObject* obj);
		virtual const txObject* find (const txObject* obj) const;
		virtual void removeAndDestroy (const txObject* obj);

		const txObject* findBest (const txObject* obj) const;

		const txObject* last (void) const;
		const txObject* first (void) const;
		const txObject* at (int index) const;

		int index (const txObject* obj) const;
		virtual int entries (void) const { return _entries_; }

		virtual void clearAndDestroy (void);
		virtual void clear (void);

		void stream (txOut& out) const;

		void destream (txIn& in);

	friend class txBTreeIterator;
	friend class txBtBNode;
	friend class txBtLNode;
	friend class txBtNode;
};

TX_DECLARE_STREAM_TYPE_OPER(txBTree)

class txBTreeIterator : public txObjectIterator
{
	TX_PERSIST

	private:
		int _cursor_;
		int _direction_;
		txBTree* _tree_;

	public:
		txBTreeIterator (const txBTree& t, int direction = 1); // up

		~txBTreeIterator (void);

		virtual txObject* remove (void);

		virtual const txObject* next (void);

		void reset (const txBTree& t, int direction = 1); // up

		virtual void reset (void);

	friend class txBTree;
};

class txBtNode : public txObject
{
	TX_PERSIST

	protected:
		txBtBNode* _parent_;
		txBTree* _tree_;
		int _is_leaf_;
		int _last_;

	public:
		txBtNode (int isleaf, txBtBNode* p, txBTree* t = 0);

		virtual ~txBtNode (void)
		{
		}

		virtual void insert (const txObject* obj, int index) = 0;
		virtual void remove (int index) = 0;

		virtual txObject* operator[] (int index) const = 0;

		virtual txObject* find (const txObject*, txBtNode**, int*) = 0;
		virtual txObject* findBest (const txObject*,txBtNode**,int*)=0;

		virtual int findIndex (const txObject* obj) const = 0;
		virtual int entries (void) const = 0;

		virtual txBtLNode* firstLeafNode (void) = 0;
		virtual txBtLNode* lastLeafNode (void) = 0;

		virtual void splitNode (void) = 0;

	friend class txBtBNode;
	friend class txBtLNode;
	friend class txBTree;
};

class txBtItem : public txObject
{
	TX_PERSIST

	private:
		txBtNode* _tree_;
		txObject* _key_;
		int _tree_entries_;

	public:
		txBtItem (void);
		txBtItem (txBtNode* n, txObject* o);
		txBtItem (txObject* o, txBtNode* n);

		~txBtItem (void);

	friend class txBtBNode;
};


class txBtBNode : public txBtNode
{
	TX_PERSIST

	private:
		txBtItem* _item_;

	public:
		txBtBNode (txBtBNode* p, txBTree* t = 0);
		txBtBNode (txBtBNode* p, txBTree* t, txBtNode* oldroot);

		~txBtBNode (void);

		void insert (const txObject* obj, int index);
		void insert (txBtItem &i, int index);
		void insert (int at, txObject* obj, txBtNode* n);
		void insertElement (txBtItem &itm, int at);
		void insertElement (int at, txObject* obj, txBtNode* n);

		void remove (int index);
		void removeItem (int index);

		txObject* operator[] (int index) const;
		txObject* find (const txObject*, txBtNode**, int*);
		txObject* findBest (const txObject*, txBtNode**, int*);

		int entries (int index) const;
		int entries (void) const;

		void setTree (int i, txBtNode* node)
		{
			_item_[i]._tree_ = node; node->_parent_ = this;
		}

		void setKey (int i, txObject* obj)
		{
			_item_[i]._key_ = obj;
		}

		void setItem (int i, txBtItem& itm)
		{
			_item_[i] = itm; itm._tree_->_parent_ = this;
		}

		void setItem (int i, txObject* obj, txBtNode* node)
		{
			setTree(i, node); setKey(i, obj);
		}

		void notifyParent (void);

		void incrementEntries (txBtNode* np);
		void decrementEntries (txBtNode* np);

		txBtLNode* lastLeafNode (void);
		txBtLNode* firstLeafNode (void);

		int getEntries (int i) const;
		void setEntries (int i, int r);

		int incrementEntries (int i, int n=1);
		int decrementEntries (int i, int n=1);

		int indexOf (const txBtNode* n) const;
		int findIndex (const txObject* obj) const;

		txBtItem& getItem (int i) const { return _item_[i]; }
		txBtNode* getTree (int i) const { return _item_[i]._tree_; }
		txObject* getKey (int i) const { return _item_[i]._key_; }

		void splitNode (void);
		void shiftLeft (int cnt);
		void splitNodeWith (txBtBNode* r, int index);

		void append (txBtItem &itm);
		void append (txObject* obj, txBtNode* n);
		void appendFrom (txBtBNode* src, int start, int stop);

		void mergeWithRight (txBtBNode* r, int index);

		void balanceWithLeft (txBtBNode* l, int index);
		void balanceWithRight (txBtBNode* r, int index);
		void balanceWith (txBtBNode* n, int index);

		void pushLeft (int cnt, txBtBNode* leftsib, int parentIdx);
		void pushRight (int cnt, txBtBNode* rightsib, int parentIdx);

		int vEntries (void) const;
		int pEntries (void) const { return _last_; }

		int maxIndex (void) const
		{
			return _tree_->_branch_max_index_;
		}

		int maxpEntries (void) const
		{
			return _tree_->_branch_max_index_;
		}

		void isFull (txBtNode* n);

		int isFull (void) const
		{
			return _last_ == maxIndex();
		}

		int isAlmtxtFull (void) const
		{
			return _last_ >= maxIndex()-1;
		}

		void isLow (txBtNode* n);

		int isLow (void) const
		{
			return _last_ < _tree_->_branch_low_water_;
		}
};


class txBtLNode : public txBtNode
{
	TX_PERSIST

	private:
		txObject** _item_;

	public:
		txBtLNode (txBtBNode*, const txObject* o=0, txBTree* t=0);

		~txBtLNode (void);

		void insert (const txObject* obj, int index);

		void remove (int index);
		void removeItem (int index) { remove(index); }

		txObject* operator[] (int index) const;
		txObject* find (const txObject*, txBtNode**, int*);
		txObject* findBest (const txObject*, txBtNode**, int*);

		int entries (void) const;
		int entries (int i) const;

		int indexOf (const txObject* obj) const;
		int findIndex (const txObject *obj) const;

		txObject* getKey (int index ) { return _item_[index]; }
		void setKey (int index, txObject* obj) { _item_[index] = obj; }

		txBtLNode* firstLeafNode (void);
		txBtLNode* lastLeafNode (void);

		void splitNode (void);
		void shiftLeft (int cnt);
		void splitNodeWith (txBtLNode* r, int index);

		void append (txObject* obj);
		void appendFrom (txBtLNode* src, int start, int stop);

		void mergeWithRight (txBtLNode* r, int index);

		void balanceWith (txBtLNode* n, int index);
		void balanceWithLeft (txBtLNode* l, int index);
		void balanceWithRight (txBtLNode* r, int index);

		void pushLeft (int cnt, txBtLNode* l, int parentIndex);
		void pushRight (int cnt, txBtLNode* r, int parentIndex);

		int vEntries (void) const;
		int pEntries (void) const { return _last_ + 1; }

		int maxIndex (void) const
		{
			return _tree_->_leaf_max_index_;
		}

		int maxpEntries (void) const
		{
			return _tree_->_leaf_max_index_+1;
		}

		int isFull (void) const
		{
			return _last_ == maxIndex();
		}

		int isAlmtxtFull (void) const
		{
			return _last_ >= maxIndex()-1;
		}

		int isLow (void) const
		{
			return _last_ < _tree_->_leaf_low_water_;
		}

	friend class txBtBNode;
};

inline const txObject* txBTree::at (int i) const
{
	return _root_ ? (*_root_)[i] : 0;
}

inline const txObject* txBTree::first (void) const
{
	return _root_ ? (*_root_)[0] : 0;
}

inline const txObject* txBTree::last (void) const
{
	return _root_ ? (*_root_)[_entries_ - 1] : 0;
}

inline int txBtBNode::getEntries (int i) const
{
	return _item_[i]._tree_entries_;
}

inline int txBtBNode::entries (int index) const
{
	return getEntries(index);
}

inline void txBtBNode::setEntries (int i, int r)
{
	_item_[i]._tree_entries_ = r;
}

inline int txBtBNode::incrementEntries (int i, int n)
{
	return (_item_[i]._tree_entries_ += n);
}

inline int txBtBNode::decrementEntries (int i, int n)
{
	return (_item_[i]._tree_entries_ -= n);
}

inline int txBtBNode::vEntries (void) const
{
	return pEntries()+1;
}

inline txObject* txBtLNode::operator[] (int i) const
{
	return _item_[i];
}

inline int txBtLNode::vEntries (void) const
{
	return pEntries()+1;
}

#endif // __TXBTREE_H__

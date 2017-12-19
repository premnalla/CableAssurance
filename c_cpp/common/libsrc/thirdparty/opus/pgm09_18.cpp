//
//   This file contains the C++ code from Program 9.18 of
//   "Data Structures and Algorithms
//    with Object-Oriented Design Patterns in C++"
//   by Bruno R. Preiss.
//
//   Copyright (c) 1998 by Bruno R. Preiss, P.Eng.  All rights reserved.
//
//   http://www.pads.uwaterloo.ca/Bruno.Preiss/books/opus4/programs/pgm09_18.cpp
//
void BinaryTree::Purge ()
{
    if (!IsEmpty ())
    {
	if (IsOwner ())
	    delete key;
	delete left;
	delete right;
	key = 0;
	left = 0;
	right = 0;
    }
}

BinaryTree::~BinaryTree ()
    { Purge (); }

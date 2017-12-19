//
//   This file contains the C++ code from Program 10.14 of
//   "Data Structures and Algorithms
//    with Object-Oriented Design Patterns in C++"
//   by Bruno R. Preiss.
//
//   Copyright (c) 1998 by Bruno R. Preiss, P.Eng.  All rights reserved.
//
//   http://www.pads.uwaterloo.ca/Bruno.Preiss/books/opus4/programs/pgm10_14.cpp
//
void MWayTree::DepthFirstTraversal (
    PrePostVisitor& visitor) const
{
    if (!IsEmpty ())
    {
	for (int i = 0; i <= numberOfKeys + 1; ++i)
	{
	    if (i >= 2)
		visitor.PostVisit (*key [i - 1]);
	    if (i >= 1 && i <= numberOfKeys)
		visitor.Visit (*key [i]);
	    if (i <= numberOfKeys - 1)
		visitor.PreVisit (*key [i + 1]);
	    if (i <= count)
		subtree [i]->DepthFirstTraversal (visitor);
	}
    }
}

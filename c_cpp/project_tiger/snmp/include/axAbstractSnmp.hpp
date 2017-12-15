
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _SAMPLE_HPP_
#define _SAMPLE_HPP_

//********************************************************************
// include files
//********************************************************************

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: sample.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class sample 
{
public:

  /// default constructor
  sample();

  /// destructor
  virtual ~sample();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */
  int foo(const int p1, int const * p2, int const * p3);

protected:

  // /// first protected data memeber
  // int m_initState;

private:

  // /// first private data memeber
  // int m_exitState;

  sample(const sample &);

};

#endif // _SAMPLE_HPP_

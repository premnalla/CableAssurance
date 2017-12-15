
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbBlade_hpp_
#define _axDbBlade_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractTopoContainer.hpp"

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
 * file/class: axDbBlade.hpp
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

class axDbBlade : public axDbAbstractTopoContainer
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Update;
  static AX_INT8 * Insert;

  /// default constructor
  axDbBlade();

  /// destructor
  virtual ~axDbBlade();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  //
  virtual bool getQuerySql(AX_INT8 *, size_t);

private:

  axDbBlade(const axDbBlade &);

};

#endif // _axDbBlade_hpp_

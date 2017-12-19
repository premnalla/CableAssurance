
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSqlResultSet_hpp_
#define _axSqlResultSet_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSqlOperReturn.hpp"

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
 * file/class: axSqlResultSet.hpp
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

class axSqlResultSet : public axAbstractSqlOperReturn
{
public:

  /// destructor
  virtual ~axSqlResultSet();

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

   // virtual bool getNext(cmts &);
   // virtual bool getNext(cm &);
   // virtual bool getNext(emta &);
   // ...

protected:

  /// data constructor
  axSqlResultSet(axAbstractConnection *);

private:

  /// default constructor
  axSqlResultSet();

  axSqlResultSet(const axSqlResultSet &);

};

#endif // _axSqlResultSet_hpp_

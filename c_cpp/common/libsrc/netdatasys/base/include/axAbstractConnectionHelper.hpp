
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractConnectionHelper_hpp_
#define _axAbstractConnectionHelper_hpp_

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
 * file/class: axAbstractConnectionHelper.hpp
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

class axAbstractConnectionHelper 
{
public:

  /// destructor
  virtual ~axAbstractConnectionHelper();

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

  // virtual axAbstractConnection * getConnection(void)=0;
  // virtual axAbstractConnection * releaseConnection(void)=0;
  // virtual axAbstractConnection * testConnection(void)=0;

  // virtual axAbstractSession * getSession(void)=0;
  // virtual axAbstractSession * releaseSession(void)=0;
  // virtual axAbstractSession * testSession(void)=0;

protected:

  /// default constructor
  axAbstractConnectionHelper();

private:

  axAbstractConnectionHelper(const axAbstractConnectionHelper &);

};

#endif // _axAbstractConnectionHelper_hpp_

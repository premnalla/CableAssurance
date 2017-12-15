
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractDbThreadInitializer_hpp_
#define _axAbstractDbThreadInitializer_hpp_

//********************************************************************
// include files
//********************************************************************
// #include "axObject.hpp"

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
 * file/class: axAbstractDbThreadInitializer.hpp
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

// class axAbstractDbThreadInitializer : public axObject
class axAbstractDbThreadInitializer
{
public:

  /// destructor
  virtual ~axAbstractDbThreadInitializer();

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

protected:

  /// default constructor
  axAbstractDbThreadInitializer();


private:

  axAbstractDbThreadInitializer(const axAbstractDbThreadInitializer &);

};

#endif // _axAbstractDbThreadInitializer_hpp_

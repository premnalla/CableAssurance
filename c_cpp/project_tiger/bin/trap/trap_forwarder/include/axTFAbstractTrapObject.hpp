
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFAbstractTrapObject_hpp_
#define _axTFAbstractTrapObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"

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
 * file/class: axTFAbstractTrapObject.hpp
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

class axTFAbstractTrapObject : public axObject
{
public:

  /// data constructor
  // axTFAbstractTrapObject(time_t);

  /// destructor
  virtual ~axTFAbstractTrapObject();

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

  /**
   *
   * IN:
   *   Char Buffer length
   * OUT:
   *   Char Buffer to hold XML
   *
   */
  virtual bool toXML(AX_INT8 *, AX_UINT32)=0;

protected:

  /// default constructor
  axTFAbstractTrapObject();

private:

  axTFAbstractTrapObject(const axTFAbstractTrapObject &);

};

#endif // _axTFAbstractTrapObject_hpp_

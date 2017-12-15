
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractDbObject_hpp_
#define _axAbstractDbObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axDbDataTypes.hpp"

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
 * file/class: axAbstractDbObject.hpp
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

class axAbstractDbObject : public axObject
{
public:

  /// destructor
  virtual ~axAbstractDbObject();

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

   // virtual bool insertObj(void);
   // virtual bool queryObj(void);
   // virtual bool deleteObj(void);
   // virtual bool updateObj(void);

   virtual AX_UINT8 getResourceType(void);

protected:

  /// default constructor
  axAbstractDbObject();


private:

  axAbstractDbObject(const axAbstractDbObject &);

};

#endif // _axAbstractDbObject_hpp_

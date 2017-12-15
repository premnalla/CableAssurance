
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractTopoIntObject_hpp_
#define _axAbstractTopoIntObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axTopoLookupIntDsTypes.hpp"

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
 * file/class: axAbstractTopoIntObject.hpp
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

class axAbstractTopoIntObject : public axObject
{
public:

  /// destructor
  virtual ~axAbstractTopoIntObject();

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

  virtual axIntKey_t      * getKey(void)=0;
  virtual axIntNonkey_t   * getNonKey(void)=0;
  virtual axAbstractLock  * getLock(void)=0;
  virtual bool              isAddable(void)=0;
  virtual bool              hasData(void)=0;

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void)=0;

  /**
   *
   */
  virtual AX_UINT16         getContainerId(void)=0;

protected:

  /// default constructor
  axAbstractTopoIntObject();

private:

  axAbstractTopoIntObject(const axAbstractTopoIntObject &);

};

#endif // _axAbstractTopoIntObject_hpp_

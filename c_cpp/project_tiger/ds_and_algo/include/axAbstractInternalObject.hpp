
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractInternalObject_hpp_
#define _axAbstractInternalObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axInternalDsTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// class axAbstractKeyPart;
// class axAbstractNonkeyPart;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axAbstractInternalObject.hpp
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

class axAbstractInternalObject : public axObject
{
public:

  /// destructor
  virtual ~axAbstractInternalObject();

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
  virtual bool isStatusOperational(void);

  /**
   *
   */
  virtual bool isAlarmable(void);

  /**
   *
   */
  virtual bool generateAlarm(AX_UINT8, INTDS_RESID_t, time_t);

protected:

  /// default constructor
  axAbstractInternalObject();

private:

  axAbstractInternalObject(const axAbstractInternalObject &);

  // axAbstractKeyPart    * m_key;
  // axAbstractNonkeyPart * m_nonkeyPart;

};

#endif // _axAbstractInternalObject_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalCm_hpp_
#define _axInternalCm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalDsTypes.hpp"
#include "axAbstractInternalObject.hpp"
#include "axDbCm.hpp"
#include "axDbCmCurrentStatus.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axSnmpDataTypes.hpp"

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
 * file/class: axInternalCm.hpp
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

class axInternalCm : public axAbstractInternalObject
{
public:

  /**
   * IN: Db layer CM object
   */
  axInternalCm(axDbCm &);

  /**
   * IN: Db layer CM object
   */
  axInternalCm(axDbCm &, axDbCmCurrentStatus &, axDbCurrentCmPerf &);

  /**
   * IN: SNMP result row from CMTS-CM-Status table poll
   */
  axInternalCm(axSnmpCmtsCmResultRow_s *);

  /**
   * IN: Key
   */
  axInternalCm(axIntCmKey_t &);

  /**
   * IN: CM mac
   */
  axInternalCm(AX_INT8 *);

  /// destructor
  virtual ~axInternalCm();

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

  /// AVL tree compare function
  // NOT USED ANYMORE. JUST here for no apparent good reason!!!
  static int CompareFunction(const void *, const void *, void *);

  ///
  virtual AX_INT32 keyCompare(axObject *);

  /// axObject overrides...
  virtual auto_ptr<string> hashString(void);

  /// equality check
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

  ///
  AX_INT8 * getMacAddress(void);

  ///
  virtual axIntKey_t      * getKey(void);
  virtual axIntNonkey_t   * getNonKey(void);
  virtual axAbstractLock  * getLock(void);
  virtual bool              isAddable(void);
  virtual bool              hasData(void);

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void);

  ///
  static bool isCmOnline(AX_UINT8);

  ///
  bool isEmta(void);
  bool isOnline(void);

  ///
  bool isCmEndUserDevice(void);

  /**
   * Overloaded from base class
   */
  virtual bool  isStatusOperational(void);

protected:

  /// default constructor
  axInternalCm();

private:

  axInternalCm(const axInternalCm &);

  void initAtInstantiation(void);

  void init(axDbCm &);

  axInternalCmData_t m_cmData;

};

#endif // _axInternalCm_hpp_

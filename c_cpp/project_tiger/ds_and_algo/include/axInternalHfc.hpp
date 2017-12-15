
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalHfc_hpp_
#define _axInternalHfc_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalDsTypes.hpp"
#include "axAbstractIterator.hpp"
#include "axAbstractInternalObject.hpp"
#include "axDbHfc.hpp"
#include "axDbAbstractCurrentCounts.hpp"
#include "axSnmpDataTypes.hpp"
#include "axDbHfcCurrentStatus.hpp"
#include "axDbHfcCurrentTca.hpp"

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
 * file/class: axInternalHfc.hpp
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

class axInternalHfc : public axAbstractInternalObject
{
public:

  /// data constructor
  axInternalHfc(axDbHfc &);

  /// data constructor
  axInternalHfc(axDbHfc &, axDbAbstractCurrentCounts &, 
    axDbHfcCurrentStatus &, axDbHfcCurrentTca &);

  /// data constructor
  axInternalHfc(AX_INT8 *);

  /// data constructor
  axInternalHfc(axIntHfcKey_t &);

  /// destructor
  virtual ~axInternalHfc();

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
  // static int CompareFunction(const void *, const void *, void *);
  virtual AX_INT32 keyCompare(axObject *);

  /// axObject overrides...
  virtual auto_ptr<string> hashString(void);

  /// equality check
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

  ///
  AX_INT8 * getHfcName(void);

  ///
  axObject * addCm(axObject *);
  axObject * addCmExtLock(axObject *);
  axObject * findCm(axObject *);
  axObject * findCmExtLock(axObject *);
  axObject * removeCm(axObject *);
  axObject * removeCmExtLock(axObject *);

  ///
  axObject * addMta(axObject *);
  axObject * addMtaExtLock(axObject *);
  axObject * findMta(axObject *);
  axObject * findMtaExtLock(axObject *);
  axObject * removeMta(axObject *);
  axObject * removeMtaExtLock(axObject *);

  ///
  axAbstractIterator * getCableModems(void);
  axAbstractIterator * getMTAs(void);

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

#if 0
  /**
   *
   */
  time_t getPowerAlarmDetectStartTime(void);

  /**
   *
   */
  time_t getPercentAlarmDetectStartTime(void);
#endif

protected:

  /// default constructor
  axInternalHfc();

private:

  axInternalHfc(const axInternalHfc &);

  void initAtInstantiation(void);

  void init(axDbHfc &);

  axInternalHfcData_t m_hfcData;

};

#endif // _axInternalHfc_hpp_

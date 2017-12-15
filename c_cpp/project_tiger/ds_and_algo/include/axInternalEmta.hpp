
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalEmta_hpp_
#define _axInternalEmta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalDsTypes.hpp"
#include "axInternalCmts.hpp"
#include "axInternalGenMta.hpp"
#include "axInternalCm.hpp"
#include "axDbEmta.hpp"
#include "axDbMtaCurrentAvailability.hpp"

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
 * file/class: axInternalEmta.hpp
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

class axInternalEmta : public axInternalGenMta
{
public:

  /**
   * Data constructor:
   */
  axInternalEmta(axDbEmta &, axInternalCmts *);

  /**
   * Data constructor:
   */
  axInternalEmta(axDbEmta &, axDbMtaCurrentAvailability &,
                                         axInternalCmts *);

  /**
   * IN: Key
   */
  axInternalEmta(axIntEmtaKey_t &);

  /**
   * IN: CM mac, MTA mac
   */
  axInternalEmta(AX_INT8 *, AX_INT8 *);

  /**
   * IN: MTA mac
   */
  axInternalEmta(AX_INT8 *);

  /// destructor
  virtual ~axInternalEmta();

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

  /// axObject overrides...
  virtual auto_ptr<string> hashString(void);

  /// equality check
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

  ///
  // virtual AX_INT32 keyCompare(axObject *);

  ///
  virtual AX_INT8 * getMtaMacAddress(void);

  axInternalCm * getCm(void);

  ///
  virtual bool compareAndUpdate(axObject *, INTDS_RESID_t);

  ///
  virtual axIntKey_t      * getKey(void);
  virtual axIntNonkey_t   * getNonKey(void);
  virtual axAbstractLock  * getLock(void);
  virtual bool              hasData(void);

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void);

#if 0
  ///
  virtual bool              updateDb(void);
#endif

  ///
  // virtual bool isAvailable(void);
  // virtual bool isAddable(void);

  ///
  virtual bool isEmta(void);

  /**
   * Overloaded from base class
   */
  virtual bool isStatusOperational(void);

  /**
   * Overloaded from base class
   */
  virtual bool isAlarmable(void);

  /**
   * Overloaded from base class
   */
  virtual bool generateAlarm(AX_UINT8, INTDS_RESID_t, time_t);

protected:

  /// default constructor
  axInternalEmta();

private:

  axInternalEmta(const axInternalEmta &);

  void initAtInstantiation(void);

  void init(axDbEmta &, axInternalCmts *);

  axInternalEmtaData_t  m_emtaData;

};

#endif // _axInternalEmta_hpp_

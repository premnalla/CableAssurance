
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalCmts_hpp_
#define _axInternalCmts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListBase.hpp"
#include "axInternalDsTypes.hpp"
#include "axAbstractInternalObject.hpp"
#include "axDbCmts.hpp"
#include "axDbAbstractCurrentCounts.hpp"
#include "axInternalHfc.hpp"
#include "axInternalChannel.hpp"
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// class axAbstractAvlTree;
// class axAbstractLock;


/** 
 * This class is used to ...
 * 
 * 
 * file/class: axInternalCmts.hpp
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

class axInternalCmts : public axAbstractInternalObject
{
public:

  /// data constructor
  axInternalCmts(axIntCmtsKey_t &);

  /// data constructor
  axInternalCmts(axDbCmts &);

  /// data constructor
  axInternalCmts(axDbCmts &, axDbAbstractCurrentCounts &);

  /// destructor
  virtual ~axInternalCmts();

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

  /// for AVL tree use only !!!
  // static int CompareFunction(const void *, const void *, void *);
  virtual AX_INT32 keyCompare(axObject *);

  /// 
  synchronizedTable_t * getChannelTable(void);
  synchronizedTable_t * getHfcTable(void);
  synchronizedTable_t * getCmTable(void);
  synchronizedTable_t * getMtaTable(void);

  /// axObject overrides...
  virtual AX_INT64  hashCode(void);
  virtual AX_UINT32 hashUInt32(void);

  /// equality check
  virtual bool isEqual(axObject *);

  ///
  virtual axIntKey_t      * getKey(void);
  virtual axIntNonkey_t   * getNonKey(void);
  virtual axAbstractLock  * getLock(void);
  virtual bool              isAddable(void);
  virtual bool              hasData(void);

  /**
   * IN: (axDbCmts *) o
   * ASSUMPTION: A write lock held by the caller
   */
  bool compareAndUpdate(axObject *);

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void);

  ///
  bool isDocsis20Capable(void);
  void setDocsisCapability(AX_INT32);

  ///
  bool isHfcAlarmDetectionInProgress(void);
  void setHfcAlarmDetectionInProgress(void);
  void clearHfcAlarmDetectionInProgress(void);

  /**
   * 
   */
  axInternalCm * findCmExtLock(AX_INT8 *);
  axInternalCm * findCm(AX_INT8 *);
  axObject * addCmExtLock(axObject *);
  axObject * addCm(axObject *);

  /**
   * 
   */
  axObject * findMtaExtLock(AX_INT8 *);
  axObject * findMta(AX_INT8 *);
  axObject * addMtaExtLock(axObject *);
  axObject * addMta(axObject *);

  /**
   *      
   */
  axObject * findHfcExtLock(axIntHfcKey_t &);
  axObject * findHfc(axIntHfcKey_t &);
  axObject * addHfcExtLock(axObject *);
  axObject * addHfc(axObject *);

  /**
   *
   */
  axObject * findChannelExtLock(axIntChannelKey_t &);
  axObject * findChannel(axIntChannelKey_t &);
  axObject * addChannelExtLock(axObject *);
  axObject * addChannel(axObject *);

  /**
   *
   */
  bool canDoCounts(void);

  /**
   *
   */
  bool isDeleted(void);
  void setDeleted(bool);

  /**
   *
   */
  bool isReconciled(void);
  void setReconciled(bool);

  /**
   * Add to FIFO-Q and Remove from FIFO-Q
   */
  axObject * addCountsMessage(axObject *);
  axObject * removeCountsMessage(void);

protected:

  /// default constructor
  axInternalCmts();

private:

  axInternalCmts(const axInternalCmts &);

  /// helper method
  void initAtInstantiation(void);
  void initPointers(void);
  void init(axDbCmts &);

  ///
  axInternalCmtsData_t  m_cmtsData;

  ///
  synchronizedTable_t m_channelTable;
  synchronizedTable_t m_hfcTable;
  synchronizedTable_t m_cmTable;
  synchronizedTable_t m_mtaTable;
  // ...etc...

  // AX_UINT8  m_docsisCapability;

  /**
   * Contains outstanding messages to do counts & alarm detection
   */
  axListBase  m_countsMessages;
};

#endif // _axInternalCmts_hpp_

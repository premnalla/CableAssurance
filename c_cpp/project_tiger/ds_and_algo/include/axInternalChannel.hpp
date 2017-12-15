
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalChannel_hpp_
#define _axInternalChannel_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalDsTypes.hpp"
#include "axAbstractIterator.hpp"
#include "axAbstractInternalObject.hpp"
#include "axDbChannel.hpp"
#include "axDbAbstractCurrentCounts.hpp"
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
 * file/class: axInternalChannel.hpp
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

class axInternalChannel : public axAbstractInternalObject
{
public:

  /// data constructor
  axInternalChannel(axDbChannel &);

  /// data constructor
  axInternalChannel(axDbChannel &, axDbAbstractCurrentCounts &);

  /// data constructor
  axInternalChannel(axSnmpCmtsChannelResultRow_s *);

  /// data constructor
  axInternalChannel(AX_INT8 *);

  /// data constructor
  axInternalChannel(axIntChannelKey_t &);

  /// destructor
  virtual ~axInternalChannel();

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
  AX_INT8 * getChannelName(void);

  ///
  axObject * addCmExtLock(axObject *);
  axObject * addCm(axObject *);
  axObject * findCmExtLock(axObject *);
  axObject * findCm(axObject *);
  axObject * removeCmExtLock(axObject *);
  axObject * removeCm(axObject *);

  ///
  axObject * addMtaExtLock(axObject *);
  axObject * addMta(axObject *);
  axObject * findMtaExtLock(axObject *);
  axObject * findMta(axObject *);
  axObject * removeMtaExtLock(axObject *);
  axObject * removeMta(axObject *);

  ///
  axAbstractIterator * getCableModems(void);
  axAbstractIterator * getMTAs(void);

  ///
  virtual bool isDownstreamChannel(void);
  virtual bool isUpstreamChannel(void);

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

protected:

  /// default constructor
  axInternalChannel();

private:

  axInternalChannel(const axInternalChannel &);

  void initAtInstantiation(void);

  void init(axDbChannel &);

  axInternalChannelData_t m_channelData;

};

#endif // _axInternalChannel_hpp_


//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractMultiStates_hpp_
#define _axAbstractMultiStates_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCommonDefs.hpp"

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
 * file/class: axAbstractMultiStates.hpp
 *
 * Design Document:
 *
 * Description:
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

class axAbstractMultiStates
{
public:

  /// destructor
  virtual ~axAbstractMultiStates();

protected:

  /// default constructor
  axAbstractMultiStates();

  ///
  void setInitState(bool);
  ///
  bool getInitState(void);

  ///
  void setState(bool);
  ///
  bool getState(void);

  ///
  void setInitializing(bool);
  ///
  bool getInitializing(void);

  ///
  void setReinitRequested(bool);
  ///
  bool getReinitRequested(void);

  ///
  void setReinitializing(bool);
  ///
  bool getReinitializing(void);

  ///
  virtual void lock(void);
  ///
  virtual void unlock(void);
  ///
  virtual void setStateUnlocked(bool);
  ///
  virtual bool getStateUnlocked(void);

private:

  ///
  axCommonDefs::syncBoolMultiStateS_t  m_states;

  /// copy not allowed
  axAbstractMultiStates(const axAbstractMultiStates &);

};

#endif // _axAbstractMultiStates_hpp_

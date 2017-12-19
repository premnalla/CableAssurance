
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractConnHandler_hpp_
#define _axAbstractConnHandler_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axStatus.h"
#include "axSingletonBase.hpp"
#include "axAbstractMultiStates.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axTraceHandler;

/**
 * This class is used to ...
 *
 *
 * file/class: axAbstractConnHandler.hpp
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

class axAbstractConnHandler : 
  public axSingletonBase, public axAbstractMultiStates
{
public:

  ///
  virtual axStatus_t connect(void);

  ///
  virtual axStatus_t disconnect(void);

  ///
  virtual axStatus_t reconnect(void);

  ///
  virtual void checkReturnCode(axStatus_t);

  ///
  virtual bool isConnected(void);

  ///
  virtual const char * getAppName(void) const;

  ///
  void setTraceHandler(axTraceHandler *);

protected:

  /// default constructor
  axAbstractConnHandler(const char *);

  /// destructor
  virtual ~axAbstractConnHandler();

  axTraceHandler * getTraceHandler(void);

private:

  ///
  string m_appName;

  ///
  axTraceHandler * m_traceHandler;

  /// default constructor
  axAbstractConnHandler();

  /// copy not allowed
  axAbstractConnHandler(const axAbstractConnHandler &);

};

#endif // _axAbstractConnHandler_hpp_

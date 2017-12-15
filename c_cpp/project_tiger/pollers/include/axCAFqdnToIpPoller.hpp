
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAFqdnToIpPoller_hpp_
#define _axCAFqdnToIpPoller_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axDbCmts;
class axDbEmta;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axCAFqdnToIpPoller.hpp
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

class axCAFqdnToIpPoller : public axAceWorker
{
public:

  /// default constructor
  axCAFqdnToIpPoller();

  /// destructor
  virtual ~axCAFqdnToIpPoller();

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

  virtual AX_INT32 run(void);

protected:

private:

  axCAFqdnToIpPoller(const axCAFqdnToIpPoller &);

  void convertFqdnToIp(axDbCmts *);
  bool ipAddressChanged(axDbEmta *, AX_INT8 *);
};

#endif // _axCAFqdnToIpPoller_hpp_

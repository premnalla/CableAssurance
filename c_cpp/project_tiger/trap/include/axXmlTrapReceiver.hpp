
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axXmlTrapReceiver_hpp_
#define _axXmlTrapReceiver_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"
#include "axSocketFailInterface.hpp"

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
 * file/class: axXmlTrapReceiver.hpp
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

class axXmlTrapReceiver : public axAceWorker,
                          public axSocketFailInterface
{
public:

  /// default constructor
  axXmlTrapReceiver();

  /// destructor
  virtual ~axXmlTrapReceiver();

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

  /**
   * 
   */
  virtual AX_INT32 run(void);

  /**
   * from interface axSocketFailInterface
   */
  virtual void handleReadFailure(AX_INT32);

  /**
   * from interface axSocketFailInterface
   */
  virtual void handleWriteFailure(AX_INT32);

protected:

private:

  axXmlTrapReceiver(const axXmlTrapReceiver &);

  AX_INT32          getConnection(void);
  AX_INT32          connectToServer(void);
  void              readFromFd(AX_INT32);

  AX_INT32          m_fd;
  // static XercesDOMParser * m_parser;
};

#endif // _axXmlTrapReceiver_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSoapServiceProcessor_hpp_
#define _axSoapServiceProcessor_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"
#include "cPeerServH.h"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axInt32;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSoapServiceProcessor.hpp
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

class axSoapServiceProcessor : public axAceWorker
{
public:

  /// data constructor
  axSoapServiceProcessor(struct soap *);

  /// destructor
  virtual ~axSoapServiceProcessor();

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

  /// default constructor
  axSoapServiceProcessor();

  /**
   *
   */
  void processRequest(axInt32 *);

private:

  axSoapServiceProcessor(const axSoapServiceProcessor &);

  struct soap * m_soap;
};

#endif // _axSoapServiceProcessor_hpp_

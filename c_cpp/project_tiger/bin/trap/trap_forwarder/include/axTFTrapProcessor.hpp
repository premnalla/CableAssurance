
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFTrapProcessor_hpp_
#define _axTFTrapProcessor_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"
#include "axTFAbstractTrapObject.hpp"
#include "axTopoLookupIntGenMta.hpp"
#include "axAbstractTopoContainerObj.hpp"
#include "axSocketFailInterface.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define axTFTrapProcessor_XML_BUFF_LEN                            4096

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axTFTrapProcessor.hpp
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

class axTFTrapProcessor : public axAceWorker
{
public:

  /// default constructor
  axTFTrapProcessor();

  /// destructor
  virtual ~axTFTrapProcessor();

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

  /**
   *
   */
  void setSockFailHandler(axSocketFailInterface *);

protected:

private:

  axTFTrapProcessor(const axTFTrapProcessor &);

  bool convertToXML(axTFAbstractTrapObject *);

  bool forwardTrap(axTopoLookupIntGenMta *);

  axAbstractTopoContainerObj * findClient(axTopoLookupIntGenMta *);

  void processTrap(axTopoLookupIntGenMta *, axTFAbstractTrapObject *);

  AX_INT8 xmlBuffer[axTFTrapProcessor_XML_BUFF_LEN];

  axSocketFailInterface * m_sockFailHandler;
};

#endif // _axTFTrapProcessor_hpp_

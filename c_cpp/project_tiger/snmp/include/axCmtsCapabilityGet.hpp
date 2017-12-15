/********************************************************************/
/*********************** OBSOLETED **********************************/
/********************************************************************/

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCapabilityGet_hpp_
#define _axCmtsCapabilityGet_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpSingleGetWork.hpp"

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
 * file/class: axCmtsCapabilityGet.hpp
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

class axCmtsCapabilityGet : public axAbstractSnmpSingleGetWork
{
public:

  /// data constructor
  axCmtsCapabilityGet(axSnmpSession *);

  /// destructor
  virtual ~axCmtsCapabilityGet();

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


protected:

  /// default constructor
  axCmtsCapabilityGet();

  virtual axSnmpValueBase_s * getDecodedResponse(void);
  virtual axSnmpOidsBase_s  * getInitialOids(void);
  virtual netsnmp_pdu       * createRequestPdu(void);
  virtual void                addOids(netsnmp_pdu *, axSnmpOidsBase_s *);
  virtual void                decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);
  virtual void                useResponse(void);

private:

  axCmtsCapabilityGet(const axCmtsCapabilityGet &);

};

#endif // _axCmtsCapabilityGet_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpGenericGetNext_hpp_
#define _axSnmpGenericGetNext_hpp_

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
 * file/class: axSnmpGenericGetNext.hpp
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

class axSnmpGenericGetNext : public axAbstractSnmpSingleGetWork
{
public:

  /// default constructor
  axSnmpGenericGetNext();

  /// destructor
  virtual ~axSnmpGenericGetNext();

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
  void setOid(AX_INT8 *);

  /**
   *
   */
  axSnmpValueBase_s * getDecodedResponse(void);

  /**
   *
   */
  virtual bool createAndSendNextRequests(axSnmpAsyncCbReqObj *);

protected:

  /**
   *
   */
  virtual bool createAndSendInitialRequests(void);

  /**
   *
   */
  virtual axSnmpOidsBase_s  * getInitialOids(void);

  /**
   *
   */
  virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);

  /**
   *
   */
  virtual void useResponse(axSnmpAsyncCbReqObj *);

private:

  axSnmpGenericGetNext(const axSnmpGenericGetNext &);

  /**
   *
   */
  void getValue(netsnmp_variable_list *);

  axSnmpGenericGetNextOid_s      m_oid;
  axSnmpGenericGetResultValue_s  m_decodedResponse;

};

#endif // _axSnmpGenericGetNext_hpp_

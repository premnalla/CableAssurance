
//********************************************************************
// !!! OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractMessageData_hpp_
#define _axAbstractMessageData_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axMessageDataTypes.hpp"

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
 * file/class: axAbstractMessageData.hpp
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

class axAbstractMessageData : public axObject
{
public:

  /// destructor
  virtual ~axAbstractMessageData();

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

  ///
  AX_UINT32  getMessageId(void);

protected:

  /// default constructor
  axAbstractMessageData();

  /**
   * IN: message id
   */
  axAbstractMessageData(AX_UINT32);

private:

  axAbstractMessageData(const axAbstractMessageData &);

  axMessageHeader_s  m_msgHeader;

};

#endif // _axAbstractMessageData_hpp_

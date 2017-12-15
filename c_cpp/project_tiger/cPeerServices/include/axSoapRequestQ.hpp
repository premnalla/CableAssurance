
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSoapRequestQ_hpp_
#define _axSoapRequestQ_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axConsumerSupplierQueue.hpp"

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
 * file/class: axSoapRequestQ.hpp
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

class axSoapRequestQ : public axConsumerSupplierQueue
{
public:

  /// destructor
  virtual ~axSoapRequestQ();

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
  static axSoapRequestQ * getInstance(void);

protected:

  /// default constructor
  axSoapRequestQ();


private:

  axSoapRequestQ(const axSoapRequestQ &);

  static axSoapRequestQ    * m_instance;
};

#endif // _axSoapRequestQ_hpp_

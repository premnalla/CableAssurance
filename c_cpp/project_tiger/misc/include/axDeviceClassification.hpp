
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDeviceClassification_hpp_
#define _axDeviceClassification_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axDcData.hpp"

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
 * file/class: axDeviceClassification.hpp
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

class axDeviceClassification 
{
public:

  /**
   * IN: CM Mac Address
   */
  axDeviceClassification(axDcData *);

  /// destructor
  virtual ~axDeviceClassification();

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
   * Classify device into CM/eMTA.
   * Create HFC entry and associate CM/eMTA to HFC
   * Create and associate Customer info with device
   * ...etc...
   */
  bool classifyDevice(void);


protected:


private:

  /// default constructor
  axDeviceClassification();

  ///
  axDeviceClassification(const axDeviceClassification &);

  ///
  axDcData * m_dcData;
};

#endif // _axDeviceClassification_hpp_

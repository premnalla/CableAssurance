
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFSystemConfig_hpp_
#define _axTFSystemConfig_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axLockableObject.hpp"

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
 * file/class: axTFSystemConfig.hpp
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

class axTFSystemConfig : public axLockableObject
{
public:

  /// destructor
  virtual ~axTFSystemConfig();

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
  static axTFSystemConfig * getInstance(void);

  AX_UINT32 getServerPortNumber(void);
  void      setServerPortNumber(const AX_INT8 *);

protected:

  /// default constructor
  axTFSystemConfig();

private:

  axTFSystemConfig(const axTFSystemConfig &);

  static axTFSystemConfig * m_instance;

  bool       m_initialized;

  AX_UINT16  m_serverPortNumber;

};

#endif // _axTFSystemConfig_hpp_

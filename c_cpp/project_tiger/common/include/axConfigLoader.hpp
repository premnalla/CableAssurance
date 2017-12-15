
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axConfigLoader_hpp_
#define _axConfigLoader_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axAll.h"
#include "ace/Configuration.h"
#include "ace/Configuration_Import_Export.h"

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
 * file/class: axConfigLoader.hpp
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

class axConfigLoader 
{
public:

  /**
   * IN: filename
   */
  axConfigLoader(AX_INT8 *);

  /// destructor
  virtual ~axConfigLoader();

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
  bool loadConfig(void);

  /**
   * IN: section-name, param-name
   * OUT: value-string
   */
  bool getValue(const AX_INT8 *, const AX_INT8 *, std::string &);

protected:

  /// default constructor
  axConfigLoader();

private:

  // not allowed
  axConfigLoader(const axConfigLoader &);

  std::string               m_fileName;
  bool                      m_loaded;
  ACE_Configuration_Heap  * m_config;
  ACE_Registry_ImpExp     * m_registryImpExp;
};

#endif // _axConfigLoader_hpp_

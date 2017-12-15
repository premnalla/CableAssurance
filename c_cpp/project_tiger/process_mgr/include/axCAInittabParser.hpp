
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAInittabParser_hpp_
#define _axCAInittabParser_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axAll.h"

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
 * file/class: axCAInittabParser.hpp
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

class axCAInittabParser 
{
public:

  /**
   * IN: filename
   */
  axCAInittabParser(const AX_INT8 *);

  /// destructor
  virtual ~axCAInittabParser();

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
  bool parseFile(void);

protected:

private:

  /// default constructor not allowed
  axCAInittabParser();

  /// not allowed
  axCAInittabParser(const axCAInittabParser &);

  ///
  void parseLine(AX_INT8 *, AX_UINT32 &);

  ///
  std::string    m_fileName;
  bool           m_fileParsed;
};

#endif // _axCAInittabParser_hpp_


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAInittabReader_hpp_
#define _axCAInittabReader_hpp_

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
 * file/class: axCAInittabReader.hpp
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

class axCAInittabReader 
{
public:

  /**
   * IN: filename
   */
  axCAInittabReader(const AX_INT8 *);

  /// destructor
  virtual ~axCAInittabReader();

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
  bool openFile(void);

  /**
   *
   * OUT: Buffer for line
   * IN: Buffer length
   * 
   */
  bool readLine(AX_INT8 *, AX_INT32);

protected:

private:

  /// default constructor not allowed
  axCAInittabReader();

  /// not allowed
  axCAInittabReader(const axCAInittabReader &);

  ///
  std::string    m_fileName;
  bool           m_fileOpened;
  FILE         * m_fp;
};

#endif // _axCAInittabReader_hpp_

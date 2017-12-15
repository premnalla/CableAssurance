
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractTopoContainerObj_hpp_
#define _axAbstractTopoContainerObj_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axObject.hpp"
#include "axDbAbstractTopoContainer.hpp"

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
 * file/class: axAbstractTopoContainerObj.hpp
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

class axAbstractTopoContainerObj : public axObject
{
public:

  /// data constructor
  // axAbstractTopoContainerObj(time_t);

  /// destructor
  virtual ~axAbstractTopoContainerObj();

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
  AX_INT32         getContainerFd(void);
  AX_UINT16        getContainerId(void);
  const AX_INT8 *  getContainerName(void);
  const AX_INT8 *  getContainerHost(void);

  void             setContainerFd(AX_INT32);
  void             clearContainerFd(void);

  /**
   * Exteded from axObject base class.
   *
   * @return
   * @see
   */
  virtual bool isKeyEqual(axObject *);

protected:

  /**
   * IN:
   *   Client FD      -
   *   Container ID   -
   *   Container Name -
   *   Container Host -
   */
  axAbstractTopoContainerObj(AX_INT32, AX_UINT16, AX_INT8 *, AX_INT8 *);

  /**
   * IN:
   *   Client FD      -
   *   DB Container   -
   */
  axAbstractTopoContainerObj(AX_INT32, axDbAbstractTopoContainer &);

  /**
   * IN:
   *   Container ID   -
   */
  axAbstractTopoContainerObj(AX_UINT16);


  axAbstractTopoContainerObj();

private:

  axAbstractTopoContainerObj(const axAbstractTopoContainerObj &);

  AX_INT32      m_containerFd;
  AX_UINT16     m_containerId;
  std::string   m_containerName;
  std::string   m_containerHost;

};

#endif // _axAbstractTopoContainerObj_hpp_

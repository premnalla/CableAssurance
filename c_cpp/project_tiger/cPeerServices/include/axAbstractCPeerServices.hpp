
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCPeerServices_hpp_
#define _axAbstractCPeerServices_hpp_

//********************************************************************
// include files
//********************************************************************
#include "cPeerServH.h"
// #include <string>

//********************************************************************
// definitions/macros
//********************************************************************
#define HTTP_PREFIX                                          "http://"
#define HTTP_POSTFIX       "/CableAssurance/caservices/CPeerServiceEP"

/* PING result strings */
#define MY_PING_PASS                                           "alive"
#define MY_PING_FAIL                                    "unreacheable"
#define MY_PING_UNK                                        "timed-out"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axAbstractCPeerServices.hpp
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

class axAbstractCPeerServices 
{
public:

  /// destructor
  virtual ~axAbstractCPeerServices();

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
  virtual int pingMta(ns1__TopoHierarchyKeyT *topologyKey,
                            std::string mtaResId, std::string &result);
  /**
   * 
   */
  virtual int getMtaData(ns1__TopoHierarchyKeyT *topologyKey, 
     std::string mtaResId, struct cpeer__getMtaDataResponse &_param_1);

  /**
   *
   */
  virtual int getCmData(ns1__TopoHierarchyKeyT *topologyKey, 
       std::string cmResId, struct cpeer__getCmDataResponse &_param_2);

  /**
   *
   */
  virtual int getCmtsCmData(ns1__TopoHierarchyKeyT *topologyKey, 
                            std::string cmtsResId, std::string cmResId, 
                        struct cpeer__getCmtsCmDataResponse &_param_3);

protected:

  /**
   * default constructor
   */
  axAbstractCPeerServices();


private:

  /// not allowed
  axAbstractCPeerServices(const axAbstractCPeerServices &);

};

#endif // _axAbstractCPeerServices_hpp_

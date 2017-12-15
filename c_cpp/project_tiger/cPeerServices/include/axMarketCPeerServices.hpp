
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMarketCPeerServices_hpp_
#define _axMarketCPeerServices_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCPeerServices.hpp"

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
 * file/class: axMarketCPeerServices.hpp
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

class axMarketCPeerServices : public axAbstractCPeerServices
{
public:

  /**
   * default constructor
   */
  axMarketCPeerServices();

  /// destructor
  virtual ~axMarketCPeerServices();

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


private:

  /// not allowed
  axMarketCPeerServices(const axMarketCPeerServices &);

};

#endif // _axMarketCPeerServices_hpp_

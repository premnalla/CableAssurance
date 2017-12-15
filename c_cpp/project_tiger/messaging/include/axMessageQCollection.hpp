
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMessageQCollection_hpp_
#define _axMessageQCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListBase.hpp"

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
 * file/class: axMessageQCollection.hpp
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

class axMessageQCollection : public axListBase
{
public:

  /**
   *
   * IN: Q Type (P2P | P/S), Creator Sub System ID
   *
   *
   */
  axMessageQCollection(AX_UINT8, AX_UINT8);

  /// destructor
  virtual ~axMessageQCollection();

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

  AX_UINT8 getQType(void);
  AX_UINT8 getCreatorId(void);

protected:

  /// default constructor
  axMessageQCollection();


private:

  axMessageQCollection(const axMessageQCollection &);

  AX_UINT8    m_qType;
  AX_UINT8    m_creatorSubSystemId;
};

#endif // _axMessageQCollection_hpp_

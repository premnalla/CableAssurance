
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCblAssureDataLoader_hpp_
#define _axCblAssureDataLoader_hpp_

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
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
 * file/class: axCblAssureDataLoader.hpp
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

class axCblAssureDataLoader 
{
public:

  /// destructor
  virtual ~axCblAssureDataLoader();

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

  static axCblAssureDataLoader * getInstance(void);

  bool loadData(void);
  bool createRunnableCollections(void);
  bool queueTimer(void);

protected:

  /// default constructor
  axCblAssureDataLoader();

private:

  axCblAssureDataLoader(const axCblAssureDataLoader &);

  void loadCmtses(void);
  void loadChannels(void);
  void loadHfcs(void);
  void loadCableModems(void);
  void loadMTAs(void);
  void createCmToChannelAssociation(void);
  void createCmToHfcAssociation(void);
  void updateCurrentAlarms(void);
  void timeTraversal(void);

  static axCblAssureDataLoader * m_instance;

  bool m_dataLoaded;
};

#endif // _axCblAssureDataLoader_hpp_

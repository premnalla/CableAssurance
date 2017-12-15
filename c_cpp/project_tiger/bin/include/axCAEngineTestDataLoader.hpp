
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAEngineTestDataLoader_hpp_
#define _axCAEngineTestDataLoader_hpp_

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
 * file/class: axCAEngineTestDataLoader.hpp
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

class axCAEngineTestDataLoader 
{
public:

  /// destructor
  virtual ~axCAEngineTestDataLoader();

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

  static axCAEngineTestDataLoader * getInstance(void);

  bool loadData(void);
  bool createRunnableCollections(void);
  bool queueTimer(void);

protected:

  /// default constructor
  axCAEngineTestDataLoader();

private:

  axCAEngineTestDataLoader(const axCAEngineTestDataLoader &);

  void loadCmtses(void);
  void loadChannels(void);
  void loadHfcs(void);
  void loadCableModems(void);
  void loadMTAs(void);
  void createCmToChannelAssociation(void);
  void createCmToHfcAssociation(void);
  void updateCurrentAlarms(void);
  void timeTraversal(void);

  static axCAEngineTestDataLoader * m_instance;

  bool m_dataLoaded;
};

#endif // _axCAEngineTestDataLoader_hpp_

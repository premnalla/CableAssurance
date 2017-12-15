
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCATaskCoordHelper_hpp_
#define _axCATaskCoordHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axLockableObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
struct axCATaskCoordHelperData {

  int cmtsPollCounter;
  int cmPollCounter;
  int mtaPingCounter;
  int mtaPollCounter;
  int countsCounter;
  int deviceClassifyCounter;
  int summaryCounter;

  axCATaskCoordHelperData() :
    cmtsPollCounter(0),
    cmPollCounter(0),
    mtaPingCounter(0),
    mtaPollCounter(0),
    countsCounter(0),
    deviceClassifyCounter(0),
    summaryCounter(0)
  {
  }

};


/** 
 * Used to help with task coordination, especially with polling,
 * and summarization coordination.
 * 
 * 
 * file/class: axCATaskCoordHelper.hpp
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

class axCATaskCoordHelper : public axLockableObject
{
public:

  /// destructor
  virtual ~axCATaskCoordHelper();

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
  static axCATaskCoordHelper * getInstance(void);

  /**
   *
   */
  bool canDoCmtsPoll(void);
  bool canDoCmPoll(void);
  bool canDoMtaPing(void);
  bool canDoMtaPoll(void);
  bool canDoCounts(void);
  bool canDoDeviceClassification(void);
  bool canDoSummary(void);

  /**
   *
   */
  void setCmtsPoll(void);
  void setCmPoll(void);
  void setMtaPing(void);
  void setMtaPoll(void);
  void setCounts(void);
  void setDeviceClassification(void);
  void setSummary(void);

  /**
   *
   */
  void clearCmtsPoll(void);
  void clearCmPoll(void);
  void clearMtaPing(void);
  void clearMtaPoll(void);
  void clearCounts(void);
  void clearDeviceClassification(void);
  void clearSummary(void);

protected:

  /**
   * default constructor
   */
  axCATaskCoordHelper();

private:

  /// not allowed
  axCATaskCoordHelper(const axCATaskCoordHelper &);

  ///
  static axCATaskCoordHelper * m_instance;

  ///
  axCATaskCoordHelperData      m_data;

};

#endif // _axCATaskCoordHelper_hpp_

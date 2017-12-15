
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlResult_hpp_
#define _axDbMySqlResult_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbResult.hpp"

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
 * file/class: axDbMySqlResult.hpp
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

class axDbMySqlResult : public axDbResult
{
public:

  /// data constructor
  axDbMySqlResult(axDbConnection *);

  /// destructor
  virtual ~axDbMySqlResult();

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

   // virtual bool isSuccess(void);
   virtual AX_UINT32 getNumAffectedRows(void);
   virtual bool getLastInsertId(AX_UINT32 &);
   virtual bool getLastInsertId(AX_UINT64 &);

protected:

  /// default constructor
  axDbMySqlResult();

private:

  axDbMySqlResult(const axDbMySqlResult &);

};

#endif // _axDbMySqlResult_hpp_

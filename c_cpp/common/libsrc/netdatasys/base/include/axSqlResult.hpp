
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSqlResult_hpp_
#define _axSqlResult_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSqlOperReturn.hpp"

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
 * file/class: axSqlResult.hpp
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

class axSqlResult : public axAbstractSqlOperReturn
{
public:

  /// destructor
  virtual ~axSqlResult();

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
   virtual AX_UINT32 getNumAffectedRows(void)=0;
   virtual bool getLastInsertId(AX_UINT32 &)=0;
   virtual bool getLastInsertId(AX_UINT64 &)=0;

protected:

  /// data constructor
  axSqlResult(axAbstractConnection *);

private:

  /// default constructor
  axSqlResult();

  axSqlResult(const axSqlResult &);

};

#endif // _axSqlResult_hpp_

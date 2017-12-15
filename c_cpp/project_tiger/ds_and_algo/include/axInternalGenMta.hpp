
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalGenMta_hpp_
#define _axInternalGenMta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractInternalObject.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axInternalGenMta.hpp
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

class axInternalGenMta : public axAbstractInternalObject
{
public:

  /// destructor
  virtual ~axInternalGenMta();

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

  /// AVL tree compare method
  // static int CompareFunction(const void *, const void *, void *);
  virtual AX_INT32 keyCompare(axObject *);

  ///
  virtual AX_INT8 * getMtaMacAddress(void)=0;

  ///
  virtual bool compareAndUpdate(axObject *, INTDS_RESID_t)=0;

#if 0
  ///
  virtual axIntKey_t      * getKey(void)=0;
  virtual axIntNonkey_t   * getNonKey(void)=0;
  virtual axAbstractLock  * getLock(void)=0;
  virtual bool              hasData(void)=0;
#endif

  ///
  bool                      isAddable(void);

#if 0
  ///
  virtual bool              updateDb(void)=0;
#endif

  ///
  bool isAvailable(void);

  ///
  virtual bool isEmta(void);

  ///
  static AX_UINT8 isProvStatePass(AX_UINT8);
  static AX_UINT8 isPingStateFailure(AX_UINT8);
  static AX_UINT8 isPingStatePass(AX_UINT8);

protected:

  /// default constructor not allowed
  axInternalGenMta();

private:

  axInternalGenMta(const axInternalGenMta &);

};

#endif // _axInternalGenMta_hpp_

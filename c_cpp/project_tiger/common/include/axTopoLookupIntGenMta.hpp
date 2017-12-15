
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTopoLookupIntGenMta_hpp_
#define _axTopoLookupIntGenMta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axTopoLookupIntDsTypes.hpp"
#include "axAbstractTopoIntObject.hpp"
#include "axDbTopoLookupGenMta.hpp"

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
 * file/class: axTopoLookupIntGenMta.hpp
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

class axTopoLookupIntGenMta : public axAbstractTopoIntObject
{
public:

  /**
   * IN: MTA mac
   */
  axTopoLookupIntGenMta(AX_INT8 *);

  /**
   * IN: MTA mac
   */
  axTopoLookupIntGenMta(axDbTopoLookupGenMta &);

  /// destructor
  virtual ~axTopoLookupIntGenMta();

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

  /// axObject overrides...
  virtual auto_ptr<string> hashString(void);

  /// equality check
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

  /// axObject overrides...
  virtual AX_INT32 keyCompare(axObject *);

  ///
  AX_INT8 * getMtaMacAddress(void);

  ///
  virtual axIntKey_t      * getKey(void);
  virtual axIntNonkey_t   * getNonKey(void);
  virtual axAbstractLock  * getLock(void);
  virtual bool              hasData(void);
  virtual bool              isAddable(void);

  /**
   *
   */
  virtual INTDS_RESID_t     getResId(void);

  /**
   *
   */
  virtual AX_UINT16         getContainerId(void);

protected:

  /// default constructor
  axTopoLookupIntGenMta();

private:

  axTopoLookupIntGenMta(const axTopoLookupIntGenMta &);

  axTopoIntGenMtaData_t  m_gMtaData;

};

#endif // _axTopoLookupIntGenMta_hpp_

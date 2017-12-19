
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axUInt32Key_hpp_
#define _axUInt32Key_hpp_

//********************************************************************
// include files
//********************************************************************

//********************************************************************
// definitions/macros
//********************************************************************
#include "axObject.hpp"
#include "axAbstractKeyPart.hpp"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axUInt32Key.hpp
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

class axUInt32Key : public axAbstractKeyPart, public axObject
{
public:

  /// data constructor
  axUInt32Key(AX_UINT32);

  /// destructor
  virtual ~axUInt32Key();

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

  AX_UINT32 getKey(void);

  /// hash code
  virtual const long hashCode(void) const;
  virtual const int hashInt(void) const;

  /// Equals check
  virtual bool isKeyEqual(const axObject *) const;

protected:

  /// default constructor
  axUInt32Key();

private:

  axUInt32Key(const axUInt32Key &);

  AX_UINT32 m_key;

};

#endif // _axUInt32Key_hpp_

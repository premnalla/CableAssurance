
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axObject_hpp_
#define _axObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include <memory>
#include "axAll.h"

using namespace std;

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axObjectEqualsCompare;

/**
 * Description ....
 *
 *
 * file/class: axObject.hpp
 *
 * Design Document:
 *
 * Description:
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

class axObject 
{
public:

  /// destructor
  virtual ~axObject();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string> toString(void);

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  virtual AX_INT64  hashCode(void);
  virtual AX_INT32  hashInt32(void);
  virtual AX_UINT32 hashUInt32(void);
  // virtual AX_UINT64 hashCode(void);
  // virtual AX_LONG   hashLong(void);
  // virtual AX_INT64  hashInt64(void);
  // virtual AX_UINT64 hashUInt64(void);

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  virtual auto_ptr<string> hashString(void);

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

  /**
   * Operator "<"...
   *
   * @return
   * @see
   */
  virtual bool operator< (const axObject *);

  /**
   * Key Compare method
   *
   * @return: (this->hashCode() - inObject->hashCode())
   *   "<0" if this object is less than in-object
   *   "==0" if this object is equal to in-object
   *   ">0" if this object is greater than in-object
   *
   * @see
   */
  virtual AX_INT32 keyCompare(axObject *);

  /**
   * CompareFunction used by AVL Tree etc
   *
   * @return: this->keyCompare(axObject * inObject)
   *   "<0" if this object is less than in-object
   *   "==0" if this object is equal to in-object
   *   ">0" if this object is greater than in-object
   *
   * @see
   */
  static int CompareFunction(const void *, const void *, void *);

protected:

  /// default constructor
  axObject();

private:

  axObject(const axObject &);

};


/**
 * Description ....
 *
 *
 * file/class: axObjectEqualsCompare.hpp
 *
 * Design Document:
 *
 * Description:
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

class axObjectEqualsCompare
{
public:

  /// default constructor
  axObjectEqualsCompare();

  /// destructor
  virtual ~axObjectEqualsCompare();

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  bool operator() (const AX_INT64 &, const AX_INT64 &);
  bool operator() (const AX_INT32 &, const AX_INT32 &);
  bool operator() (const AX_UINT32 &, const AX_UINT32 &);
  bool operator() (const AX_INT8 * , const AX_INT8 *);
  // bool operator() (const AX_LONG &, const AX_LONG &);

protected:

private:

};


#endif // _axObject_hpp_

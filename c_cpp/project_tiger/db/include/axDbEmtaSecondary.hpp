
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbEmtaSecondary_hpp_
#define _axDbEmtaSecondary_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"

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
 * file/class: axDbEmtaSecondary.hpp
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

class axDbEmtaSecondary : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /**
   * default constructor
   */
  axDbEmtaSecondary();

  /// destructor
  virtual ~axDbEmtaSecondary();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  AX_UINT32      m_id;
  AX_UINT32      m_emta_res_id;
  std::string    m_phone1;
  std::string    m_phone2;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  // virtual bool deleteRow(void);

protected:


private:

  /// not allowed
  axDbEmtaSecondary(const axDbEmtaSecondary &);

};

#endif // _axDbEmtaSecondary_hpp_

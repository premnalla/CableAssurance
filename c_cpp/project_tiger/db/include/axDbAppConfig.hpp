
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAppConfig_hpp_
#define _axDbAppConfig_hpp_

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
 * file/class: axDbAppConfig.hpp
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

class axDbAppConfig : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * QueryAll;

  /// default constructor
  axDbAppConfig();

  // copy constructor
  axDbAppConfig(const axDbAppConfig &);

  /// destructor
  virtual ~axDbAppConfig();

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
   * data members...
   */

  //
  std::string m_varName;
  std::string m_varValue;

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

  /**
   * Overloaded from base class axObject
   */
  virtual bool isKeyEqual(axObject *);

protected:

private:

};

#endif // _axDbAppConfig_hpp_

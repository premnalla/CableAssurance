
//********************************************************************
// Copyright (c) 2007 Prem Nallasivampillai
//********************************************************************

#ifndef _axXmlErrorHandler_hpp_
#define _axXmlErrorHandler_hpp_

//********************************************************************
// include files
//********************************************************************
#include <xercesc/util/XercesDefs.hpp>
#include <xercesc/sax/ErrorHandler.hpp>
#include <xercesc/sax/SAXParseException.hpp>

XERCES_CPP_NAMESPACE_USE

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
 * file/class: axXmlErrorHandler.hpp
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

class axXmlErrorHandler : public ErrorHandler
{
public:

  /// default constructor
  axXmlErrorHandler();

  /// destructor
  virtual ~axXmlErrorHandler();

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
  void warning(const SAXParseException& toCatch);

  void error(const SAXParseException& toCatch);

  void fatalError(const SAXParseException& toCatch);

  void resetErrors();

  ///
  bool isErrored(void);

protected:

  ///
  void setErrored(bool);

private:

  bool             m_errored;

  axXmlErrorHandler(const axXmlErrorHandler &);

};

#endif // _axXmlErrorHandler_hpp_

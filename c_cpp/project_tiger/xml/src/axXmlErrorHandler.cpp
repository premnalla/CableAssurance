
//********************************************************************
// Copyright (c) 2007 Prem Nallasivampillai
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <string>
#include "axXmlErrorHandler.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axXmlErrorHandler::axXmlErrorHandler() :
  m_errored(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axXmlErrorHandler::~axXmlErrorHandler()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axXmlErrorHandler::axXmlErrorHandler()
// {
// }


//********************************************************************
// method: error
//********************************************************************
void
axXmlErrorHandler::error(const SAXParseException & toCatch)
{
  setErrored(true);

  const XMLCh * msg = toCatch.getMessage();

  fprintf(stderr, "	Line   : %d\n", (int)toCatch.getLineNumber());
  fprintf(stderr, "	column : %d\n", (int)toCatch.getColumnNumber());
  fprintf(stderr, "	Failure: message : %s\n", (char *)XMLString::transcode(msg));
}


//********************************************************************
// method: fatalError
//********************************************************************
void
axXmlErrorHandler::fatalError(const SAXParseException & toCatch)
{
  setErrored(true);

  const XMLCh * msg = toCatch.getMessage();

  fprintf(stderr, " Line   : %d\n", (int)toCatch.getLineNumber());
  fprintf(stderr, " column : %d\n", (int)toCatch.getColumnNumber());
  fprintf(stderr, " Failure: message : %s\n", (char *)XMLString::transcode(msg));
}


//********************************************************************
// method: warning
//********************************************************************
void
axXmlErrorHandler::warning(const SAXParseException & toCatch)
{
  setErrored(true);

  const XMLCh * msg = toCatch.getMessage();

  fprintf(stderr, " Line   : %d\n", (int)toCatch.getLineNumber());
  fprintf(stderr, " column : %d\n", (int)toCatch.getColumnNumber());
  fprintf(stderr, " Failure: message : %s\n", (char *)XMLString::transcode(msg));
}


//********************************************************************
// method: resetErrors
//********************************************************************
void
axXmlErrorHandler::resetErrors(void)
{
  setErrored(false);
}


//********************************************************************
// method: isErrored
//********************************************************************
bool
axXmlErrorHandler::isErrored(void)
{
  return (m_errored);
}


//********************************************************************
// method: setErrored
//********************************************************************
void
axXmlErrorHandler::setErrored(bool b)
{
  m_errored = b;
}

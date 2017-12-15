
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axCALog.hpp"
#include "axSoapServiceProcessor.hpp"
#include "axSoapRequestQ.hpp"
#include "axInt32.hpp"
#include "cPeerServStub.h"

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
axSoapServiceProcessor::axSoapServiceProcessor() :
  m_soap(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSoapServiceProcessor::~axSoapServiceProcessor()
{
#if 0
  if (m_soap != null) {
    soap_done(m_soap);
    soap_free(m_soap);
    m_soap = null;
  }
#endif
}


//********************************************************************
// data constructor:
//********************************************************************
axSoapServiceProcessor::axSoapServiceProcessor(struct soap * sp) :
  m_soap(sp)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axSoapServiceProcessor::run(void)
{
  static const char * myName="axSoapReqProc::run:";

  ACE_DEBUG((LM_INFO, "%s starting\n", myName));

  AX_INT32 ret = 0;

  axSoapRequestQ * q = axSoapRequestQ::getInstance();

  axObject * o;

  /*
   *
   */
  while((o = q->remove()) != NULL) {

    ACE_DEBUG((LM_INFO, "%s got message \n", myName));

    axInt32 * msg = dynamic_cast<axInt32 *> (o);

    if (!msg) {
      delete o;
      continue;
    }

    processRequest(msg);

    // finally free the msg
    delete msg;
  }

  ACE_DEBUG((LM_INFO, "%s exitting\n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axSoapServiceProcessor::processRequest(axInt32 * msg)
{
  static const char * myName="axSoapSrvProc::procReq:";

  ACE_DEBUG((LM_INFO, "%s entry\n", myName));

  m_soap->socket = msg->getValue();
  if (!soap_valid_socket(m_soap->socket)) {
    return;
  }

  /*
   * Gotcha!!!! Yes, it is NOT soap_serve()
   */
  cPeerServ_serve(m_soap);

  soap_destroy(m_soap);
  soap_end(m_soap);

  ACE_DEBUG((LM_INFO, "%s exit\n", myName));
}




//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axCALog.hpp"
#include "axSoapServiceAcceptor.hpp"
#include "cPeerServH.h"
#include "axCASystemConfig.hpp"
#include "axSoapRequestQ.hpp"
#include "axInt32.hpp"
#include "axSoapServiceProcessor.hpp"
// #include "cPeerServ.nsmap"
#include "axMyGsoapConstants.hpp"

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
axSoapServiceAcceptor::axSoapServiceAcceptor()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSoapServiceAcceptor::~axSoapServiceAcceptor()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSoapServiceAcceptor::axSoapServiceAcceptor()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axSoapServiceAcceptor::run(void)
{
  static const char * myName="axSoapAccep::run:";

  ACE_DEBUG((LM_INFO, "%s starting\n", myName));

  AX_INT32 ret = 0;

  struct soap mySoap;

  SOAP_SOCKET masterSocket;
  SOAP_SOCKET slaveSocket;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  axSoapRequestQ * q = axSoapRequestQ::getInstance();

  soap_init(&mySoap);
  mySoap.namespaces = axAll_namespaces;

  ACE_DEBUG((LM_INFO, "%s port:%d\n", myName, 
                                   sysCfg->get_soap_services_port()));

  masterSocket = soap_bind(&mySoap, NULL, 
    sysCfg->get_soap_services_port(), sysCfg->get_soap_services_backlog());

  if (masterSocket < 0) {
    ACE_DEBUG((LM_CRITICAL, "%s soap_bind() failed\n", myName));
    goto EXIT_LABEL;
  }

  /*
   *
   */
  for (int i=0; i<sysCfg->get_soap_service_req_processors(); i++) {
    struct soap * scp = soap_copy(&mySoap);
    axSoapServiceProcessor * p = new axSoapServiceProcessor(scp);
    p->start();
  }

  /*
   *
   */
  while(1) {

    slaveSocket = soap_accept(&mySoap);
    if (slaveSocket < 0) {
      soap_print_fault(&mySoap, stderr);
      break;
    }

    ACE_DEBUG((LM_DEBUG, "%s got new request \n", myName));

    axInt32 * s = new axInt32(slaveSocket);
    q->add(s);

  }

  soap_destroy(&mySoap);
  soap_end(&mySoap);
  soap_done(&mySoap);

EXIT_LABEL:

  ACE_DEBUG((LM_INFO, "%s exitting\n", myName));

  return (ret);
}



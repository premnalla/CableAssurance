
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "AdminServH.h"
#include "axMyGsoapConstants.hpp"
#include "axConfigDownloader.hpp"
#include "axDbLocalSystem.hpp"

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
axConfigDownloader::axConfigDownloader()
{
}


//********************************************************************
// destructor:
//********************************************************************
axConfigDownloader::~axConfigDownloader()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axConfigDownloader::axConfigDownloader()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axConfigDownloader::downloadConfig(void)
{
  static const char * myName="configDownloader.downloadConfig:";

  static const char * svrPrefix="http://";
  static const char * svrPostfix="/CableAssurance/caservices/AdministrationEP";

  char url[128];
  sprintf(url, "%s%s%s", svrPrefix, "localhost:8080", svrPostfix);

  struct soap soap;
  soap_init(&soap);
  soap.namespaces = axAll_namespaces;

  axDbLocalSystem ls;
  if (ls.getRow() && ls.m_systemType == DB_LOCAL_SYSTEM_BLADE) {
    /*
     * Whare we are doing above is a clever way to update the Blade DB with config items
     * from the Market. We invoke the "getConfig" service provided
     * by the WebServer instead of reimplementing the DB update method in axDbAppConfig
     * which will be quite painful. Therefore, otherthan calling this service method,
     * we have nothing more to do. The current config is returned.
     * 
     * The WebService method below updates the DB along with getting it.
     */

    // call service
    struct adm__getConfigResponse resp;
    soap_call_adm__getConfig(&soap, url, NULL, resp);

    // check for errors
    if (soap.error) {
      soap_print_fault(&soap, stderr);
      goto CLEANUP;
    }
  }

  ACE_DEBUG((LM_INFO, "%s success", myName));

CLEANUP:
  // cleanup
  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);
}




//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axMarketCPeerServices.hpp"
#include "axDbBlade.hpp"
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
axMarketCPeerServices::axMarketCPeerServices()
{
}


//********************************************************************
// destructor:
//********************************************************************
axMarketCPeerServices::~axMarketCPeerServices()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axMarketCPeerServices::axMarketCPeerServices()
// {
// }


//********************************************************************
// method:
//********************************************************************
int
axMarketCPeerServices::pingMta(ns1__TopoHierarchyKeyT *topologyKey,
                            std::string mtaResId, std::string &result)
{
  static const char * myName="axMarketCPeerServices::pingMta:";

  int ret = MY_SOAP_ERROR;

  axCASystemConfig * gSysCfg = axCASystemConfig::getInstance();

  struct soap soap;
  axDbBlade bld;
  char ep[512];

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  {
    bld.m_id = atoi(topologyKey->bladeId.c_str());
    if (!bld.getRow()) {
      goto EXIT_LABEL;
    }

    soap_init(&soap);
    soap.namespaces = axAll_namespaces;
    sprintf(ep, "%s%s:%d%s", HTTP_PREFIX, bld.m_host.c_str(), 
                     gSysCfg->get_soap_services_port(), HTTP_POSTFIX);
    soap_call_cpeer__pingMta(&soap, ep, NULL, topologyKey, mtaResId, result);
    if (soap.error) {
      // soap_print_fault(&soap, stderr);
      ACE_DEBUG((LM_ERROR, "%s soap error", myName));
      goto SOAP_ERROR;
    } else {
      ACE_DEBUG((LM_DEBUG, "%s success", myName));
    }
  }

  ret = SOAP_OK;

SOAP_ERROR:

  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axMarketCPeerServices::getMtaData(ns1__TopoHierarchyKeyT *topologyKey,
     std::string mtaResId, struct cpeer__getMtaDataResponse &_param_1)
{
  static const char * myName="mkt.getMtaData:";

  int ret = MY_SOAP_ERROR;

  axCASystemConfig * gSysCfg = axCASystemConfig::getInstance();

  struct soap soap;
  axDbBlade bld;
  char ep[512];

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  {
    bld.m_id = atoi(topologyKey->bladeId.c_str());
    if (!bld.getRow()) {
      goto EXIT_LABEL;
    }

    soap_init(&soap);
    soap.namespaces = axAll_namespaces;
    sprintf(ep, "%s%s:%d%s", HTTP_PREFIX, bld.m_host.c_str(),
                     gSysCfg->get_soap_services_port(), HTTP_POSTFIX);
    soap_call_cpeer__getMtaData(&soap, ep, NULL, topologyKey, mtaResId, 
                                                            _param_1);
    if (soap.error) {
      // soap_print_fault(&soap, stderr);
      ACE_DEBUG((LM_ERROR, "%s soap error", myName));
      goto SOAP_ERROR;
    } else {
      ACE_DEBUG((LM_DEBUG, "%s success", myName));
    }
  }

  ret = SOAP_OK;

SOAP_ERROR:

  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axMarketCPeerServices::getCmData(
                                  ns1__TopoHierarchyKeyT *topologyKey,
       std::string cmResId, struct cpeer__getCmDataResponse &_param_2)
{
  static const char * myName="mkt.getCmData:";

  int ret = MY_SOAP_ERROR;

  axCASystemConfig * gSysCfg = axCASystemConfig::getInstance();

  struct soap soap;
  axDbBlade bld;
  char ep[512];

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  {
    bld.m_id = atoi(topologyKey->bladeId.c_str());
    if (!bld.getRow()) {
      goto EXIT_LABEL;
    }

    soap_init(&soap);
    soap.namespaces = axAll_namespaces;
    sprintf(ep, "%s%s:%d%s", HTTP_PREFIX, bld.m_host.c_str(),
                     gSysCfg->get_soap_services_port(), HTTP_POSTFIX);
    soap_call_cpeer__getCmData(&soap, ep, NULL, topologyKey, cmResId, 
                                                            _param_2);
    if (soap.error) {
      // soap_print_fault(&soap, stderr);
      ACE_DEBUG((LM_ERROR, "%s soap error", myName));
      goto SOAP_ERROR;
    } else {
      ACE_DEBUG((LM_DEBUG, "%s success", myName));
    }
  }

  ret = SOAP_OK;

SOAP_ERROR:

  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int 
axMarketCPeerServices::getCmtsCmData(
                                  ns1__TopoHierarchyKeyT *topologyKey,
                           std::string cmtsResId, std::string cmResId,
                        struct cpeer__getCmtsCmDataResponse &_param_3)
{
  static const char * myName="mkt.getCmtsCmData:";

  int ret = MY_SOAP_ERROR;

  axCASystemConfig * gSysCfg = axCASystemConfig::getInstance();

  struct soap soap;
  axDbBlade bld;
  char ep[512];

  if (!topologyKey) {
    goto EXIT_LABEL;
  }

  {
    bld.m_id = atoi(topologyKey->bladeId.c_str());
    if (!bld.getRow()) {
      goto EXIT_LABEL;
    }

    soap_init(&soap);
    soap.namespaces = axAll_namespaces;
    sprintf(ep, "%s%s:%d%s", HTTP_PREFIX, bld.m_host.c_str(),
                     gSysCfg->get_soap_services_port(), HTTP_POSTFIX);
    soap_call_cpeer__getCmtsCmData(&soap, ep, NULL, topologyKey, 
                                        cmtsResId, cmResId, _param_3);
    if (soap.error) {
      // soap_print_fault(&soap, stderr);
      ACE_DEBUG((LM_ERROR, "%s soap error", myName));
      goto SOAP_ERROR;
    } else {
      ACE_DEBUG((LM_DEBUG, "%s success", myName));
    }
  }

  ret = SOAP_OK;

SOAP_ERROR:

  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);

EXIT_LABEL:

  return (ret);
}



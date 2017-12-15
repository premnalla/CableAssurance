/* allCteSOAPBindingProxy.h
   Generated by gSOAP 2.7.9c from include/allServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/

#ifndef allCteSOAPBinding_H
#define allCteSOAPBinding_H
#include "allH.h"
extern SOAP_NMAC struct Namespace all_namespaces[];
class CteSOAPBinding
{   public:
	struct soap *soap;
	const char *endpoint;
	CteSOAPBinding() { soap = soap_new(); if (soap) soap->namespaces = all_namespaces; endpoint = "http://localhost:8080/CableAssurance/caservices/CteEP"; };
	virtual ~CteSOAPBinding() { if (soap) { soap_destroy(soap); soap_end(soap); soap_free(soap); } };
	virtual int cte__getCteData(ArrayOfCTEQueryInputT *queryInput, struct cte__getCteDataResponse &_param_21) { return soap ? soap_call_cte__getCteData(soap, endpoint, NULL, queryInput, _param_21) : SOAP_EOM; };
};
#endif
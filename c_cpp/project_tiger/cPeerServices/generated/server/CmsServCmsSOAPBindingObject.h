/* CmsServCmsSOAPBindingObject.h
   Generated by gSOAP 2.7.9c from include/CmsServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/

#ifndef CmsServCmsSOAPBinding_H
#define CmsServCmsSOAPBinding_H
#include "CmsServH.h"

/******************************************************************************\
 *                                                                            *
 * Service Object                                                             *
 *                                                                            *
\******************************************************************************/

extern SOAP_NMAC struct Namespace CmsServ_namespaces[];
class CmsSOAPBinding : public soap
{    public:
	CmsSOAPBinding()
	{ soap_init(this); this->namespaces = CmsServ_namespaces; };
	virtual ~CmsSOAPBinding() { soap_destroy(this); soap_end(this); soap_done(this); };
	virtual	int bind(const char *host, int port, int backlog) { return soap_bind(this, host, port, backlog); };
	virtual	int accept() { return soap_accept(this); };
	virtual	int serve() { return CmsServ_serve(this); };
};

/******************************************************************************\
 *                                                                            *
 * Service Operations (you should define these)                               *
 *                                                                            *
\******************************************************************************/


SOAP_FMAC5 int SOAP_FMAC6 cms__getLineStatus(struct soap*, ArrayOfCMSLineT *input, struct cms__getLineStatusResponse &_param_1);

#endif

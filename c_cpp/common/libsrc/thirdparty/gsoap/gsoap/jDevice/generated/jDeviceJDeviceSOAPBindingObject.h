/* jDeviceJDeviceSOAPBindingObject.h
   Generated by gSOAP 2.7.9c from include/jDevice.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/

#ifndef jDeviceJDeviceSOAPBinding_H
#define jDeviceJDeviceSOAPBinding_H
#include "jDeviceH.h"

/******************************************************************************\
 *                                                                            *
 * Service Object                                                             *
 *                                                                            *
\******************************************************************************/

extern SOAP_NMAC struct Namespace jDevice_namespaces[];
class JDeviceSOAPBinding : public soap
{    public:
	JDeviceSOAPBinding()
	{ soap_init(this); this->namespaces = jDevice_namespaces; };
	virtual ~JDeviceSOAPBinding() { soap_destroy(this); soap_end(this); soap_done(this); };
	virtual	int bind(const char *host, int port, int backlog) { return soap_bind(this, host, port, backlog); };
	virtual	int accept() { return soap_accept(this); };
	virtual	int serve() { return jDevice_serve(this); };
};

/******************************************************************************\
 *                                                                            *
 * Service Operations (you should define these)                               *
 *                                                                            *
\******************************************************************************/


SOAP_FMAC5 int SOAP_FMAC6 jDevice__getDeviceDetails(struct soap*, std::string macAddress, struct jDevice__getDeviceDetailsResponse &_param_1);

#endif

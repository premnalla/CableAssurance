/* CmsServServer.cpp
   Generated by gSOAP 2.7.9c from include/CmsServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/
#include "CmsServH.h"

SOAP_SOURCE_STAMP("@(#) CmsServServer.cpp ver 2.7.9c 2007-07-14 19:32:07 GMT")


SOAP_FMAC5 int SOAP_FMAC6 CmsServ_serve(struct soap *soap)
{
#ifndef WITH_FASTCGI
	unsigned int k = soap->max_keep_alive;
#endif

	do
	{
#ifdef WITH_FASTCGI
		if (FCGI_Accept() < 0)
		{
			soap->error = SOAP_EOF;
			return soap_send_fault(soap);
		}
#endif

		soap_begin(soap);

#ifndef WITH_FASTCGI
		if (soap->max_keep_alive > 0 && !--k)
			soap->keep_alive = 0;
#endif

		if (soap_begin_recv(soap))
		{	if (soap->error < SOAP_STOP)
			{
#ifdef WITH_FASTCGI
				soap_send_fault(soap);
#else 
				return soap_send_fault(soap);
#endif
			}
			soap_closesock(soap);

			continue;
		}

		if (soap_envelope_begin_in(soap)
		 || soap_recv_header(soap)
		 || soap_body_begin_in(soap)
		 || CmsServ_serve_request(soap)
		 || (soap->fserveloop && soap->fserveloop(soap)))
		{
#ifdef WITH_FASTCGI
			soap_send_fault(soap);
#else
			return soap_send_fault(soap);
#endif
		}

#ifdef WITH_FASTCGI
	} while (1);
#else
	} while (soap->keep_alive);
#endif
	return SOAP_OK;
}

#ifndef WITH_NOSERVEREQUEST
SOAP_FMAC5 int SOAP_FMAC6 CmsServ_serve_request(struct soap *soap)
{
	soap_peek_element(soap);
	if (!soap_match_tag(soap, soap->tag, "cms:getLineStatus"))
		return soap_serve_cms__getLineStatus(soap);
	return soap->error = SOAP_NO_METHOD;
}
#endif

SOAP_FMAC5 int SOAP_FMAC6 soap_serve_cms__getLineStatus(struct soap *soap)
{	struct cms__getLineStatus soap_tmp_cms__getLineStatus;
	struct cms__getLineStatusResponse _param_1;
	soap_default_cms__getLineStatusResponse(soap, &_param_1);
	soap_default_cms__getLineStatus(soap, &soap_tmp_cms__getLineStatus);
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	if (!soap_get_cms__getLineStatus(soap, &soap_tmp_cms__getLineStatus, "cms:getLineStatus", NULL))
		return soap->error;
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
	 || soap_end_recv(soap))
		return soap->error;
	soap->error = cms__getLineStatus(soap, soap_tmp_cms__getLineStatus.input, _param_1);
	if (soap->error)
		return soap->error;
	soap_serializeheader(soap);
	soap_serialize_cms__getLineStatusResponse(soap, &_param_1);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cms__getLineStatusResponse(soap, &_param_1, "cms:getLineStatusResponse", "")
		 || soap_body_end_out(soap)
		 || soap_envelope_end_out(soap))
			 return soap->error;
	};
	if (soap_end_count(soap)
	 || soap_response(soap, SOAP_OK)
	 || soap_envelope_begin_out(soap)
	 || soap_putheader(soap)
	 || soap_body_begin_out(soap)
	 || soap_put_cms__getLineStatusResponse(soap, &_param_1, "cms:getLineStatusResponse", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap->error;
	return soap_closesock(soap);
}

/* End of CmsServServer.cpp */
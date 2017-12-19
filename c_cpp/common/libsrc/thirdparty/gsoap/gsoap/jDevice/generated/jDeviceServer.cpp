/* jDeviceServer.cpp
   Generated by gSOAP 2.7.9c from include/jDevice.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/
#include "jDeviceH.h"

SOAP_SOURCE_STAMP("@(#) jDeviceServer.cpp ver 2.7.9c 2007-03-06 13:20:24 GMT")


SOAP_FMAC5 int SOAP_FMAC6 jDevice_serve(struct soap *soap)
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
		 || jDevice_serve_request(soap)
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
SOAP_FMAC5 int SOAP_FMAC6 jDevice_serve_request(struct soap *soap)
{
	soap_peek_element(soap);
	if (!soap_match_tag(soap, soap->tag, "jDevice:getDeviceDetails"))
		return soap_serve_jDevice__getDeviceDetails(soap);
	return soap->error = SOAP_NO_METHOD;
}
#endif

SOAP_FMAC5 int SOAP_FMAC6 soap_serve_jDevice__getDeviceDetails(struct soap *soap)
{	struct jDevice__getDeviceDetails soap_tmp_jDevice__getDeviceDetails;
	struct jDevice__getDeviceDetailsResponse _param_1;
	soap_default_jDevice__getDeviceDetailsResponse(soap, &_param_1);
	soap_default_jDevice__getDeviceDetails(soap, &soap_tmp_jDevice__getDeviceDetails);
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	if (!soap_get_jDevice__getDeviceDetails(soap, &soap_tmp_jDevice__getDeviceDetails, "jDevice:getDeviceDetails", NULL))
		return soap->error;
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
	 || soap_end_recv(soap))
		return soap->error;
	soap->error = jDevice__getDeviceDetails(soap, soap_tmp_jDevice__getDeviceDetails.macAddress, _param_1);
	if (soap->error)
		return soap->error;
	soap_serializeheader(soap);
	soap_serialize_jDevice__getDeviceDetailsResponse(soap, &_param_1);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_jDevice__getDeviceDetailsResponse(soap, &_param_1, "jDevice:getDeviceDetailsResponse", "")
		 || soap_body_end_out(soap)
		 || soap_envelope_end_out(soap))
			 return soap->error;
	};
	if (soap_end_count(soap)
	 || soap_response(soap, SOAP_OK)
	 || soap_envelope_begin_out(soap)
	 || soap_putheader(soap)
	 || soap_body_begin_out(soap)
	 || soap_put_jDevice__getDeviceDetailsResponse(soap, &_param_1, "jDevice:getDeviceDetailsResponse", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap->error;
	return soap_closesock(soap);
}

/* End of jDeviceServer.cpp */
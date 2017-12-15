/* CmsServClient.cpp
   Generated by gSOAP 2.7.9c from include/CmsServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/
#include "CmsServH.h"

SOAP_SOURCE_STAMP("@(#) CmsServClient.cpp ver 2.7.9c 2007-07-14 19:32:08 GMT")


SOAP_FMAC5 int SOAP_FMAC6 soap_call_cms__getLineStatus(struct soap *soap, const char *soap_endpoint, const char *soap_action, ArrayOfCMSLineT *input, struct cms__getLineStatusResponse &_param_1)
{	struct cms__getLineStatus soap_tmp_cms__getLineStatus;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:8080/CableAssurance/caservices/CmsEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cms__getLineStatus.input = input;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cms__getLineStatus(soap, &soap_tmp_cms__getLineStatus);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cms__getLineStatus(soap, &soap_tmp_cms__getLineStatus, "cms:getLineStatus", "")
		 || soap_body_end_out(soap)
		 || soap_envelope_end_out(soap))
			 return soap->error;
	}
	if (soap_end_count(soap))
		return soap->error;
	if (soap_connect(soap, soap_endpoint, soap_action)
	 || soap_envelope_begin_out(soap)
	 || soap_putheader(soap)
	 || soap_body_begin_out(soap)
	 || soap_put_cms__getLineStatus(soap, &soap_tmp_cms__getLineStatus, "cms:getLineStatus", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_cms__getLineStatusResponse(soap, &_param_1);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_get_cms__getLineStatusResponse(soap, &_param_1, "cms:getLineStatusResponse", "");
	if (soap->error)
	{	if (soap->error == SOAP_TAG_MISMATCH && soap->level == 2)
			return soap_recv_fault(soap);
		return soap_closesock(soap);
	}
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
	 || soap_end_recv(soap))
		return soap_closesock(soap);
	return soap_closesock(soap);
}

/* End of CmsServClient.cpp */
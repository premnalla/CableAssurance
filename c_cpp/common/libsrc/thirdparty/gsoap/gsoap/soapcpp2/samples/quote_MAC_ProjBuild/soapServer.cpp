/* soapServer.cpp
   Generated by gSOAP 2.3 from quote.h
   Copyright (C) 2001-2003 Genivia inc.
   All Rights Reserved.
*/
#include "soapH.h"

SOAP_SOURCE_STAMP("@(#) soapServer.cpp ver 2.3 2003-06-24 22:51:49 GMT")


SOAP_FMAC1 int SOAP_FMAC2 soap_serve(struct soap *soap)
{
	unsigned int n = SOAP_MAXKEEPALIVE;
	do
	{	soap_begin(soap);
		if (!--n)
			soap->keep_alive = 0;
		if (soap_begin_recv(soap))
		{	if (soap->error < SOAP_STOP)
				return soap_send_fault(soap);
			else
				continue;
		}
		if (soap_envelope_begin_in(soap) || soap_recv_header(soap) || soap_body_begin_in(soap))
			return soap_send_fault(soap);
		soap->error = soap_serve_ns__getQuote(soap);
		if (soap->error)
			return soap_send_fault(soap);
	} while (soap->keep_alive);
	return SOAP_OK;
}

SOAP_FMAC1 int SOAP_FMAC2 soap_serve_ns__getQuote(struct soap *soap)
{	struct ns__getQuote soap_tmp_ns__getQuote;
	struct ns__getQuoteResponse soap_tmp_ns__getQuoteResponse;
	float soap_tmp_float;
	soap_default_ns__getQuoteResponse(soap, &soap_tmp_ns__getQuoteResponse);
	soap_default_float(soap, &soap_tmp_float);
	soap_tmp_ns__getQuoteResponse.Result = &soap_tmp_float;
	soap_default_ns__getQuote(soap, &soap_tmp_ns__getQuote);
	soap_get_ns__getQuote(soap, &soap_tmp_ns__getQuote, "ns:getQuote", NULL);
	if (soap->error == SOAP_TAG_MISMATCH && soap->level == 2)
		soap->error = SOAP_NO_METHOD;
	if (soap->error)
		return soap->error;
	
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
#ifndef WITH_LEANER
	 || soap_getattachments(soap)
#endif
	 || soap_end_recv(soap))
		return soap->error;
	soap->error = ns__getQuote(soap, soap_tmp_ns__getQuote.symbol, &soap_tmp_float);
	if (soap->error)
		return soap->error;
	soap_serializeheader(soap);
	soap_serialize_ns__getQuoteResponse(soap, &soap_tmp_ns__getQuoteResponse);
	soap_begin_count(soap);
	if (soap->mode & SOAP_IO_LENGTH)
	{	soap_envelope_begin_out(soap);
		soap_putheader(soap);
		soap_body_begin_out(soap);
		soap_put_ns__getQuoteResponse(soap, &soap_tmp_ns__getQuoteResponse, "ns:getQuoteResponse", "");
		soap_body_end_out(soap);
		soap_envelope_end_out(soap);
	};
	if (soap_response(soap, SOAP_OK)
	 || soap_envelope_begin_out(soap)
	 || soap_putheader(soap)
	 || soap_body_begin_out(soap)
	 || soap_put_ns__getQuoteResponse(soap, &soap_tmp_ns__getQuoteResponse, "ns:getQuoteResponse", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
#ifndef WITH_LEANER
	 || soap_putattachments(soap)
#endif
	 || soap_end_send(soap))
		return soap->error;
	soap_closesock(soap);
	return SOAP_OK;
}

/* end of soapServer.cpp */

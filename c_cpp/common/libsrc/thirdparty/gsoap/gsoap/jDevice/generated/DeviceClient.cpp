/* DeviceClient.cpp
   Generated by gSOAP 2.7.9c from include/Device.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/
#include "DeviceH.h"

SOAP_SOURCE_STAMP("@(#) DeviceClient.cpp ver 2.7.9c 2007-03-06 13:20:24 GMT")


SOAP_FMAC5 int SOAP_FMAC6 soap_call_Device__pingDevice(struct soap *soap, const char *soap_endpoint, const char *soap_action, int resourceId, struct Device__pingDeviceResponse &_param_1)
{	struct Device__pingDevice soap_tmp_Device__pingDevice;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9081/CableAssurance/caservices/DeviceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_Device__pingDevice.resourceId = resourceId;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_Device__pingDevice(soap, &soap_tmp_Device__pingDevice);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_Device__pingDevice(soap, &soap_tmp_Device__pingDevice, "Device:pingDevice", "")
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
	 || soap_put_Device__pingDevice(soap, &soap_tmp_Device__pingDevice, "Device:pingDevice", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_Device__pingDeviceResponse(soap, &_param_1);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_get_Device__pingDeviceResponse(soap, &_param_1, "Device:pingDeviceResponse", "");
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

/* End of DeviceClient.cpp */

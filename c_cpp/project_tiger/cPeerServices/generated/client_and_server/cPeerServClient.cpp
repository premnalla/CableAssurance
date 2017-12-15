/* cPeerServClient.cpp
   Generated by gSOAP 2.7.9c from include/cPeerServices.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/
#include "cPeerServH.h"

SOAP_SOURCE_STAMP("@(#) cPeerServClient.cpp ver 2.7.9c 2007-07-14 19:32:06 GMT")


SOAP_FMAC5 int SOAP_FMAC6 soap_call_cpeer__pingMta(struct soap *soap, const char *soap_endpoint, const char *soap_action, ns1__TopoHierarchyKeyT *topologyKey, std::string mtaResId, std::string &result)
{	struct cpeer__pingMta soap_tmp_cpeer__pingMta;
	struct cpeer__pingMtaResponse *soap_tmp_cpeer__pingMtaResponse;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cpeer__pingMta.topologyKey = topologyKey;
	soap_tmp_cpeer__pingMta.mtaResId = mtaResId;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cpeer__pingMta(soap, &soap_tmp_cpeer__pingMta);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cpeer__pingMta(soap, &soap_tmp_cpeer__pingMta, "cpeer:pingMta", "")
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
	 || soap_put_cpeer__pingMta(soap, &soap_tmp_cpeer__pingMta, "cpeer:pingMta", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_std__string(soap, &result);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_tmp_cpeer__pingMtaResponse = soap_get_cpeer__pingMtaResponse(soap, NULL, "cpeer:pingMtaResponse", "");
	if (soap->error)
	{	if (soap->error == SOAP_TAG_MISMATCH && soap->level == 2)
			return soap_recv_fault(soap);
		return soap_closesock(soap);
	}
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
	 || soap_end_recv(soap))
		return soap_closesock(soap);
	result = soap_tmp_cpeer__pingMtaResponse->result;
	return soap_closesock(soap);
}

SOAP_FMAC5 int SOAP_FMAC6 soap_call_cpeer__getMtaData(struct soap *soap, const char *soap_endpoint, const char *soap_action, ns1__TopoHierarchyKeyT *topologyKey, std::string mtaResId, struct cpeer__getMtaDataResponse &_param_1)
{	struct cpeer__getMtaData soap_tmp_cpeer__getMtaData;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cpeer__getMtaData.topologyKey = topologyKey;
	soap_tmp_cpeer__getMtaData.mtaResId = mtaResId;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cpeer__getMtaData(soap, &soap_tmp_cpeer__getMtaData);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cpeer__getMtaData(soap, &soap_tmp_cpeer__getMtaData, "cpeer:getMtaData", "")
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
	 || soap_put_cpeer__getMtaData(soap, &soap_tmp_cpeer__getMtaData, "cpeer:getMtaData", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_cpeer__getMtaDataResponse(soap, &_param_1);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_get_cpeer__getMtaDataResponse(soap, &_param_1, "cpeer:getMtaDataResponse", "");
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

SOAP_FMAC5 int SOAP_FMAC6 soap_call_cpeer__getCmData(struct soap *soap, const char *soap_endpoint, const char *soap_action, ns1__TopoHierarchyKeyT *topologyKey, std::string cmResId, struct cpeer__getCmDataResponse &_param_2)
{	struct cpeer__getCmData soap_tmp_cpeer__getCmData;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cpeer__getCmData.topologyKey = topologyKey;
	soap_tmp_cpeer__getCmData.cmResId = cmResId;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cpeer__getCmData(soap, &soap_tmp_cpeer__getCmData);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cpeer__getCmData(soap, &soap_tmp_cpeer__getCmData, "cpeer:getCmData", "")
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
	 || soap_put_cpeer__getCmData(soap, &soap_tmp_cpeer__getCmData, "cpeer:getCmData", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_cpeer__getCmDataResponse(soap, &_param_2);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_get_cpeer__getCmDataResponse(soap, &_param_2, "cpeer:getCmDataResponse", "");
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

SOAP_FMAC5 int SOAP_FMAC6 soap_call_cpeer__getCmtsCmData(struct soap *soap, const char *soap_endpoint, const char *soap_action, ns1__TopoHierarchyKeyT *topologyKey, std::string cmtsResId, std::string cmResId, struct cpeer__getCmtsCmDataResponse &_param_3)
{	struct cpeer__getCmtsCmData soap_tmp_cpeer__getCmtsCmData;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cpeer__getCmtsCmData.topologyKey = topologyKey;
	soap_tmp_cpeer__getCmtsCmData.cmtsResId = cmtsResId;
	soap_tmp_cpeer__getCmtsCmData.cmResId = cmResId;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cpeer__getCmtsCmData(soap, &soap_tmp_cpeer__getCmtsCmData);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cpeer__getCmtsCmData(soap, &soap_tmp_cpeer__getCmtsCmData, "cpeer:getCmtsCmData", "")
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
	 || soap_put_cpeer__getCmtsCmData(soap, &soap_tmp_cpeer__getCmtsCmData, "cpeer:getCmtsCmData", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_cpeer__getCmtsCmDataResponse(soap, &_param_3);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_get_cpeer__getCmtsCmDataResponse(soap, &_param_3, "cpeer:getCmtsCmDataResponse", "");
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

SOAP_FMAC5 int SOAP_FMAC6 soap_call_cpeer__sendEvent(struct soap *soap, const char *soap_endpoint, const char *soap_action, ns1__EventMessageT *event, short &result)
{	struct cpeer__sendEvent soap_tmp_cpeer__sendEvent;
	struct cpeer__sendEventResponse *soap_tmp_cpeer__sendEventResponse;
	if (!soap_endpoint)
		soap_endpoint = "http://localhost:9091/CableAssurance/caservices/CPeerServiceEP";
	if (!soap_action)
		soap_action = "";
	soap->encodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
	soap_tmp_cpeer__sendEvent.event = event;
	soap_begin(soap);
	soap_serializeheader(soap);
	soap_serialize_cpeer__sendEvent(soap, &soap_tmp_cpeer__sendEvent);
	if (soap_begin_count(soap))
		return soap->error;
	if (soap->mode & SOAP_IO_LENGTH)
	{	if (soap_envelope_begin_out(soap)
		 || soap_putheader(soap)
		 || soap_body_begin_out(soap)
		 || soap_put_cpeer__sendEvent(soap, &soap_tmp_cpeer__sendEvent, "cpeer:sendEvent", "")
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
	 || soap_put_cpeer__sendEvent(soap, &soap_tmp_cpeer__sendEvent, "cpeer:sendEvent", "")
	 || soap_body_end_out(soap)
	 || soap_envelope_end_out(soap)
	 || soap_end_send(soap))
		return soap_closesock(soap);
	soap_default_short(soap, &result);
	if (soap_begin_recv(soap)
	 || soap_envelope_begin_in(soap)
	 || soap_recv_header(soap)
	 || soap_body_begin_in(soap))
		return soap_closesock(soap);
	soap_tmp_cpeer__sendEventResponse = soap_get_cpeer__sendEventResponse(soap, NULL, "cpeer:sendEventResponse", "");
	if (soap->error)
	{	if (soap->error == SOAP_TAG_MISMATCH && soap->level == 2)
			return soap_recv_fault(soap);
		return soap_closesock(soap);
	}
	if (soap_body_end_in(soap)
	 || soap_envelope_end_in(soap)
	 || soap_end_recv(soap))
		return soap_closesock(soap);
	result = soap_tmp_cpeer__sendEventResponse->result;
	return soap_closesock(soap);
}

/* End of cPeerServClient.cpp */

#include "cPeerServH.h"
// SOAP_NMAC struct Namespace cPeerServ_namespaces[] =
SOAP_NMAC struct Namespace axAll_namespaces[] =
{
  {"SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/", "http://www.w3.org/*/soap-envelope", NULL},
  {"SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/", "http://www.w3.org/*/soap-encoding", NULL},
  {"xsi", "http://www.w3.org/2001/XMLSchema-instance", "http://www.w3.org/*/XMLSchema-instance", NULL},
  {"xsd", "http://www.w3.org/2001/XMLSchema", "http://www.w3.org/*/XMLSchema", NULL},
  {"ns1", "http://www.palmyrasys.com/webservices/CableAssurance", NULL, NULL},
  {"adm", "http://www.palmyrasys.com/webservices/CableAssurance/Administration", NULL, NULL},
  {"cms", "http://www.palmyrasys.com/webservices/CableAssurance/Cms", NULL, NULL},
  {"cpeer", "http://www.palmyrasys.com/webservices/CableAssurance/CPeerService", NULL, NULL},
  {"cte", "http://www.palmyrasys.com/webservices/CableAssurance/Cte", NULL, NULL},
  {NULL, NULL, NULL, NULL}
};

#include "cPeerServH.h"
// #include "cPeerServ.nsmap"
#include "axMyGsoapConstants.hpp"

const char server[] = "http://192.168.1.104:9091/CableAssurance/caservices/CPeerServiceEP";
// const char server[] = "192.168.1.104:9091";

int main(int argc, char **argv)
{ 
  struct soap soap;
  soap_init(&soap);
  soap.namespaces = axAll_namespaces;
  std::string resp;
#if 0
  soap_call_cpeer__pingMta(&soap, server, NULL, "00:00:00:00:00:01", resp);
  if (soap.error)
  { 
    soap_print_fault(&soap, stderr);
    exit(1);
  } else {
    // printf("result = %g\n", result);
    printf("success: %s\n", resp.c_str());
  }
#endif
  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);
  return 0;
}

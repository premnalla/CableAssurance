#include "jDeviceH.h"
// #include "jDevice.nsmap"
#include "component.nsmap"

const char server[] = "http://192.168.1.108:9080/CableAssurance/caservices/JDeviceEP";

int main(int argc, char **argv)
{ 
  struct soap soap;
  soap_init(&soap);
  soap.namespaces = jDevice_namespaces;
  jDevice__getDeviceDetailsResponse resp;
  soap_call_jDevice__getDeviceDetails(&soap, server, NULL, "00:00:00:00:00:01", resp);
  if (soap.error)
  { 
    soap_print_fault(&soap, stderr);
    exit(1);
  } else {
    // printf("result = %g\n", result);
    printf("success\n");
  }
  soap_destroy(&soap);
  soap_end(&soap);
  soap_done(&soap);
  return 0;
}

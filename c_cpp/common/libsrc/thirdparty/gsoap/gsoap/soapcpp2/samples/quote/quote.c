#include "soapH.h"	/* include generated proxy and SOAP support */

int main(int argc, char **argv)
{ struct soap soap;
  float q;
  char *sym;
  if (argc > 1)
    sym = argv[1];
  else
  { fprintf(stderr, "Usage: quote <ticker>\n");
    exit(1);
  }
  soap_init(&soap);
  if (soap_call_ns__getQuote(&soap, "http://services.xmethods.net/soap", "", sym, &q) == 0)
    printf("\nCompany - %s    Quote - %f\n", sym, q);
  else
    soap_print_fault(&soap, stderr);
  soap_end(&soap);
  soap_done(&soap);
  return 0;
}

/* The namespace mapping table is required and associates namespace prefixes with namespace names: */
struct Namespace namespaces[] =
{
  {"SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/", "http://www.w3.org/*/soap-envelope", NULL}, /* MUST be the first */
  {"SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/", "http://www.w3.org/*/soap-encoding", NULL}, /* MUST be the second */
  {"xsi", "http://www.w3.org/1999/XMLSchema-instance", "http://www.w3.org/*/XMLSchema-instance", NULL}, /* MUST be the third */
  {"xsd", "http://www.w3.org/1999/XMLSchema", "http://www.w3.org/*/XMLSchema", NULL},
  {"ns", "urn:xmethods-delayed-quotes"},	/* Method namespace URI */
  {NULL, NULL, NULL, NULL}
};



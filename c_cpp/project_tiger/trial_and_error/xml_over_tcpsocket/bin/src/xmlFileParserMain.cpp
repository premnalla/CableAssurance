#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <xercesc/util/OutOfMemoryException.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMNodeList.hpp>
#include <xercesc/dom/DOMNamedNodeMap.hpp>
#include <xercesc/framework/LocalFileInputSource.hpp>
#include "axXmlErrorHandler.hpp"

XERCES_CPP_NAMESPACE_USE

static char*                    gXmlFile               = 0;
static bool                     gDoNamespaces          = false;
static bool                     gDoSchema              = false;
static bool                     gSchemaFullChecking    = false;
static bool                     gDoCreate              = false;
static char*                    goutputfile            = 0;
// options for DOMWriter's features
static XMLCh*                   gOutputEncoding        = 0;
static bool                     gSplitCdataSections    = true;
static bool                     gDiscardDefaultContent = true;
static bool                     gUseFilter             = false;
static bool                     gFormatPrettyPrint     = false;
static bool                     gWriteBOM              = false;
static XercesDOMParser::ValSchemes    gValScheme       = XercesDOMParser::Val_Auto;
static XercesDOMParser * parser = NULL;

int main(int argc, char * argv[])
{

  /* XML Parser init */
  try {
     XMLPlatformUtils::Initialize();
  } catch (const XMLException& toCatch) {
     return -1;
  }
  parser = new XercesDOMParser;
  parser->setValidationScheme(gValScheme);
  parser->setDoNamespaces(gDoNamespaces);
  parser->setDoSchema(gDoSchema);
  parser->setValidationSchemaFullChecking(gSchemaFullChecking);
  parser->setCreateEntityReferenceNodes(gDoCreate);
  axXmlErrorHandler *errReporter = new axXmlErrorHandler();
  parser->setErrorHandler(errReporter);

  try
  {
    LocalFileInputSource s(XMLString::transcode("./test.xml"));
    const unsigned long startMillis = XMLPlatformUtils::getCurrentMillis();
    // parser->parse("./test.xml");
    parser->parse(s);
    const unsigned long endMillis = XMLPlatformUtils::getCurrentMillis();
    long duration = endMillis - startMillis;
    int errorCount = parser->getErrorCount();

    DOMNode * nd = parser->getDocument();
    printf("Node Name = %s\n", XMLString::transcode(nd->getNodeName()));
    // parser->getDocument();

    DOMNodeList * children = nd->getChildNodes();
    for (int i=0; i<children->getLength(); i++) {
      DOMNode * child = children->item(i);
      printf("Node Name = %s\n", XMLString::transcode(child->getNodeName()));
      DOMNodeList * grandChildren = child->getChildNodes();
      for (int j=0; j<grandChildren->getLength(); j++) {
        DOMNode * grandChild = grandChildren->item(j);
        printf("Node Name = %s\n", XMLString::transcode(grandChild->getNodeName()));
      }
    }

  }
  catch (const OutOfMemoryException&)
  {
    printf("Out-of-memory exception in parse\n");
  }
  catch (const XMLException& e)
  {
    printf("XML exception in parse\n");
  }

  return (0);
}



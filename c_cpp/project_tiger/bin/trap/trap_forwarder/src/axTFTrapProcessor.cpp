
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axCALog.hpp"
#include "axTFTrapProcessor.hpp"
#include "axTFIncomingTrapQ.hpp"
#include "axTFAbstractTrapObject.hpp"
#include "axTopoLookupTables.hpp"
#include "axTopoLookupIntGenMta.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axDbTopoLookupGenMta.hpp"
#include "axAbstractTopoContainerObj.hpp"
#include "axWrappedOutputStream.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axTFTrapProcessor::axTFTrapProcessor() :
  m_sockFailHandler(NULL)
{
  xmlBuffer[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axTFTrapProcessor::~axTFTrapProcessor()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTFTrapProcessor::axTFTrapProcessor()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axTFTrapProcessor::run(void)
{
  AX_INT32 ret = 0;

  axTFIncomingTrapQ * trapQ = axTFIncomingTrapQ::getInstance();
  axTFAbstractTrapObject * o;
  
  axTopoLookupTables * t = axTopoLookupTables::getInstance();
  synchronizedTable_t * tt = t->getMtaTable();

  axTopoLookupIntGenMta * intMta;

  while ((o=(axTFAbstractTrapObject *)trapQ->remove())) {
    /* process object */

    // printf("axTFTrapProcessor::run(): got item ...\n");

    // o->timerCallback();

    // fill in actual value from trap object
    AX_INT8 * mtaMac = "";

    intMta = t->findMta(mtaMac);
    if (!intMta) {
      // look in db
      axDbTopoLookupGenMta dbMta(mtaMac);
      if (dbMta.getRow()) {
        intMta = new axTopoLookupIntGenMta(dbMta);
        if (intMta->isAddable()) {
          axWriteLockOperator wLock(tt->lock);
          if (!t->addMtaExtLock(intMta)) {
            delete intMta;
            intMta = NULL;
          }
        }
      }
    }

    if (intMta) {
      processTrap(intMta, o);
    }

    delete o;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axTFTrapProcessor::processTrap(axTopoLookupIntGenMta * intMta,
                                        axTFAbstractTrapObject * trap)
{
  /*
   * Convert trap to XML
   */
  bool rc = convertToXML(trap);

  /*
   * forward trap
   */
  if (rc) {
    forwardTrap(intMta);
  }

}


//********************************************************************
// method:
//********************************************************************
axAbstractTopoContainerObj *
axTFTrapProcessor::findClient(axTopoLookupIntGenMta * intMta)
{
  axAbstractTopoContainerObj * ret;

  axTopoLookupTables * t = axTopoLookupTables::getInstance();

  AX_UINT16 ci;

  {
    axReadLockOperator rL(intMta->getLock());
    ci = intMta->getContainerId();
  }

  ret = t->findContainer(ci);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axTFTrapProcessor::forwardTrap(axTopoLookupIntGenMta * intMta)
{
  bool ret = false;

  axAbstractTopoContainerObj * client = findClient(intMta);

  AX_INT32 fd;
  if (client && (fd=client->getContainerFd()) != 0) {
    axWrappedOutputStream out(fd, m_sockFailHandler);
    out.streamWrite(xmlBuffer, strlen(xmlBuffer));
    out.streamClose();
    ret = true;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axTFTrapProcessor::convertToXML(axTFAbstractTrapObject * trap)
{
  // initialize
  xmlBuffer[0] = '\0';

  bool ret = trap->toXML(xmlBuffer, sizeof(xmlBuffer));

  return (ret);
}

//********************************************************************
// method:
//********************************************************************
void 
axTFTrapProcessor::setSockFailHandler(axSocketFailInterface * hdl)
{
  m_sockFailHandler = hdl;
}




//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpMultipleGetWork.hpp"

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
axAbstractSnmpMultipleGetWork::axAbstractSnmpMultipleGetWork()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractSnmpMultipleGetWork::~axAbstractSnmpMultipleGetWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractSnmpMultipleGetWork::axAbstractSnmpMultipleGetWork(
//                                                axSnmpSession * s) :
//   axAbsSnmpSyncPollWork(s)
// {
// }


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axAbstractSnmpMultipleGetWork::run(void)
{
  bool ret = true;

  netsnmp_pdu * req;
  netsnmp_pdu * resp;

  startWork();

  axSnmpOidsBase_s * oids = getInitialOids();

  bool done = false;

  axSnmpRespDecodeRC_s decodeRc;

  while (!done) {

    req = createRequestPdu();
    if (!req) {
      done = true;
      continue;
    }

    addOids(req, oids);

    // send request and get response
    bool sendRc = sendRequestAndGetResponse(
                  getSnmpSession()->getInternalSession(), req, &resp);
    if (!sendRc) {
      done = true;
      continue;
    }

    // decode raw response
    decodeResponse(resp, decodeRc);

    // free response
    freeResponse(&resp);

    // update internal structures
    if (decodeRc.hasData) {
      useResponse();
    }

    // get OID's for next GET
    if (decodeRc.moreDataPossible) {
      oids = getNextOids();
    } else {
      done = true;
    }

  } // while

  endWork();

  return (ret);
}
#endif



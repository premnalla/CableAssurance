
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpSingleGetWork.hpp"

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
axAbstractSnmpSingleGetWork::axAbstractSnmpSingleGetWork()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractSnmpSingleGetWork::~axAbstractSnmpSingleGetWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractSnmpSingleGetWork::axAbstractSnmpSingleGetWork(
//                                                axSnmpSession * s) :
//   axAbsSnmpSyncPollWork(s)
// {
// }


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axAbstractSnmpSingleGetWork::run(void)
{
  bool ret = true;

  netsnmp_pdu * req;
  netsnmp_pdu * resp;

  startWork();

  axSnmpOidsBase_s * oids = getInitialOids();

  axSnmpRespDecodeRC_s decodeRc;

  req = createRequestPdu();
  if (!req) {
    goto EXIT_LABEL;
  }

  addOids(req, oids);

  bool sendRc = sendRequestAndGetResponse(
                    getSnmpSession()->getInternalSession(), req, &resp);
  if (!sendRc) {
    goto EXIT_LABEL;
  }

  // decode raw response
  decodeResponse(resp, decodeRc);

  // free response
  freeResponse(&resp);

  // update internal structures
  if (decodeRc.hasData) {
    useResponse();
  }

  endWork();

  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif



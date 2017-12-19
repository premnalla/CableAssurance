//
// snmpCGtoCGArchMsg.C
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpCGtoCGArchMsg.H"


snmpCGtoCGArchMsg::snmpCGtoCGArchMsg(PDU             *npdu,    // = NULL,
				     sendPduArgs     *nargs,   // = NULL,
				     snmpFIFOObj     *retFIFO, // = NULL,
				     snmpStatusInfo  *nstatus) // = NULL 
{
  DEBUGCREATE(camDebugObj, "CGtoCGArchMsg");
  DEBUG9(camDebugObj, "snmpCGtoCGArchMsg contructor, full params");

  pdu    = npdu;
  args   = nargs;
  status = nstatus;

  this->set_returnFIFO(retFIFO);
}

snmpCGtoCGArchMsg::snmpCGtoCGArchMsg(snmpCGtoCGArchMsg *ctcMsg)
{
  DEBUGCREATE(camDebugObj, "CGtoCGArchMsg");
  DEBUG9(camDebugObj, "snmpCGtoCGArchMsg(snmpCGtoCGArchMsg *) constructer");
  
  this->pdu    = 0;
  this->args   = 0;
  this->status = 0;

  if (ctcMsg->pdu)    { this->pdu    = ctcMsg->pdu->clone();    }
  if (ctcMsg->args)   { this->args   = ctcMsg->args->clone();   }
  if (ctcMsg->status) { this->status = ctcMsg->status->clone(); }
  this->set_returnFIFO(ctcMsg->get_returnFIFO());
}

snmpCGtoCGArchMsg::~snmpCGtoCGArchMsg()  
{
  DEBUG9(camDebugObj, "~snmpCGtoCGArchMsg destructor");
  DEBUGDESTROY(camDebugObj);

  if (pdu)    delete pdu;
  if (args)   delete args;
  if (status) delete status;
}

  

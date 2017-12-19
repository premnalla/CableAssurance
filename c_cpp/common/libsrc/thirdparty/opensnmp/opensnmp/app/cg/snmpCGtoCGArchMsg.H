#ifndef SNMPCGTOCGARCHMSG_H
#define SNMPCGTOCGARCHMSG_H

#include "asnDataTypes.H"
#include "snmpMessageObj.H"
#include "snmpStatusInfo.H"
#include "debug.H"
#include "sendPduArgs.H"


class snmpCGtoCGArchMsg : public snmpMessageObj  {

private:
  DEBUGDECLARE(camDebugObj);
  
public:
   
  PDU             *pdu;
  sendPduArgs     *args;
  snmpStatusInfo  *status;

  snmpCGtoCGArchMsg(PDU             *npdu     = NULL,
		    sendPduArgs     *nargs    = NULL,
		    snmpFIFOObj     *retFIFO  = NULL,
		    snmpStatusInfo  *nstatus  = NULL);

  snmpCGtoCGArchMsg(snmpCGtoCGArchMsg *ctcMsg);

  virtual ~snmpCGtoCGArchMsg();

  virtual snmpCGtoCGArchMsg *clone() {
    return new snmpCGtoCGArchMsg(this);
  }


  DEFINE_PTR_ACCESSOR(PDU,            pdu, );
  DEFINE_PTR_ACCESSOR(sendPduArgs,    args, );
  DEFINE_PTR_ACCESSOR(snmpStatusInfo, status, );

};


#endif /*  SNMPCGTOCGARCHMSG_H  */

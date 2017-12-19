#ifndef SNMPUSMDELUSERMSG_H
#define SNMPUSMDELUSERMSG_H

#include "usmUserMsg.H"

class usmDelUserMsg : public usmUserMsg {


public:
  usmDelUserMsg
  (OctetString      *engID,
   OctetString      *userName,
   snmpFIFOObj      *returnFIFO, 

   snmpMessageObj   *copyFromMsg = NULL,
   int               newID       = 0,
   snmpSynchDataObj *newData     = NULL );

  usmDelUserMsg(usmDelUserMsg *copyFrom);

  virtual ~usmDelUserMsg();
  virtual  usmDelUserMsg   *clone()  { return (new usmDelUserMsg(this)); };

};

#endif /* SNMPUSMDELUSERMSG_H */

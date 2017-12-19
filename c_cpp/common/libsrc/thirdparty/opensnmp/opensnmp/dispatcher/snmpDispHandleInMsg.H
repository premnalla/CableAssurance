//
// snmpDispHandleInMsg declaration
//
#ifndef SNMPDISPHANDLEINMSG_H
#define SNMPDISPHANDLEINMSG_H

#include "snmpMessageObj.H"
#include "asnDataTypes.H"
#include "snmpConstants.H"
#include "debug.H"
#include "BufferClass.H"

using SNMP_CONSTANTS::TransportDomain;

class snmpDispHandleInMsg : public snmpMessageObj {

private:
  TransportDomain  *transportDomain;       // domain from which msg was sent
  TransportAddress *transportAddress;      // addr from which msg was sent
  TransportAddress *localTransportAddress; // addr on which msg recvd
  BufferClass      *msgData;
  Integer32        *msgLength;
  DEBUGDECLARE(debugObj);

public:
   
  snmpDispHandleInMsg();
  snmpDispHandleInMsg(TransportDomain  *domain, 
		      TransportAddress *address, 
		      TransportAddress *localAddress, 
		      BufferClass      *data, 
		      Integer32        *length);

  virtual ~snmpDispHandleInMsg();

  virtual snmpDispHandleInMsg *clone() const;

  void set_localTransportAddress(TransportAddress  *transportAddress);
  TransportAddress *get_localTransportAddress(bool extract=false);
  void set_transportDomain(TransportDomain  *transportDomain);
  TransportDomain  *get_transportDomain(bool extract=false);
  void set_transportAddress(TransportAddress  *transportAddress);
  TransportAddress  *get_transportAddress(bool extract=false);
  void set_msgData(BufferClass  *msgData);
  BufferClass  *get_msgData(bool extract=false);
  void set_msgLength(Integer32  *msgLength);
  Integer32  *get_msgLength(bool extract=false);

  void set_testMsg(BufferClass  *testMsg);
  BufferClass  *get_testMsg(bool extract=false);

};


#endif /*  SNMPDISPHANDLEINMSG_H  */

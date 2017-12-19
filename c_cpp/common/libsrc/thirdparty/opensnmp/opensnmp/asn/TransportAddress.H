#ifndef TADDRESS_H
#define TADDRESS_H

#include "OctetString.H"
#include "snmpConstants.H"
#include <netinet/in.h>

class TransportAddress : public OctetString {
public:
  SNMP_CONSTANTS::TransportDomain TDomain;
  int socket;
  
  TransportAddress(short portnum = 161,
		   std::string peername = "0.0.0.0",
		   //		   std::string peername = "127.0.0.1",
		   SNMP_CONSTANTS::TransportDomain td = 
		   SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP);
  
  TransportAddress(const TransportAddress & tAdd);
  
  virtual ~TransportAddress(void) {};

  void set_address(string peername, short portnum = 161,
		   SNMP_CONSTANTS::TransportDomain td = 
		   SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP);
  
  virtual TransportAddress *clone(void);

  void set_socket(int sd);
  int get_socket(bool extract = false);
  
  void operator =(TransportAddress & tAdd);

  string toString();

};

#endif

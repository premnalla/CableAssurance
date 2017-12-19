#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#include "TransportAddress.H"
#include "opensnmpUtilities.H"

TransportAddress::TransportAddress(short  portnum,
				   string peername,
				   SNMP_CONSTANTS::TransportDomain td)
  : OctetString()
{
  set_address(peername, portnum, td);
  this->socket = 0;
} // TransportAddress


TransportAddress::TransportAddress(const TransportAddress &tAdd)
  : OctetString(tAdd)
{
  this->data_string = tAdd.data_string;
  this->TDomain     = tAdd.TDomain;
  this->socket      = 0;
} // TransportAddress

// clone

TransportAddress *
TransportAddress::clone(){
  return( new TransportAddress(*this) );
}


void
TransportAddress::set_address(string peername, short portnum,
			      SNMP_CONSTANTS::TransportDomain td) {

  if (td == SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP ||
      td == SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_TCP) {
    /* XXX: v4 udp/tcp specific */
    
    assign("",0);
    TDomain = td;
    
    int addr = inet_addr(peername.c_str()); /* a.b.c.d */
    if (addr == -1) {
      /* x.y.com */
      struct hostent *hp = gethostbyname(peername.c_str());
      if (hp == NULL) {
	throw snmpStringException("could not resolve address: '" + 
				  peername + "'");
      }
      append((char *) hp->h_addr, hp->h_length);
    }
    else {
      append((char *) &addr, sizeof(int));
    }
    portnum = htons(portnum);
    append((char*)&portnum,sizeof(short));
  } else {
    throw snmpStringException("unsupported transport domain");
  }
  
} // set_address


void 
TransportAddress::set_socket(int sd) {
  this->socket = sd;
} 
   
int 
TransportAddress::get_socket(bool extract) {
  int ret = this->socket;
  if (extract) this->socket = 0;
  return(ret);
}

void
TransportAddress::operator =(TransportAddress & tAdd) {
  OctetString::operator =(tAdd);
  this->TDomain = tAdd.TDomain;
  this->socket  = tAdd.socket;
}    


string
TransportAddress::toString() {
  string  retString;

  retString = "Address, '" + toHexString() + "' : ";

  if (SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_UDP == TDomain)  {
    retString = retString + " Domain UDP : ";
  }
  else if (SNMP_CONSTANTS::SNMP_TRANSPORT_DOMAIN_TCP == TDomain)  {
    retString = retString + " Domain TCP : ";
  }
  else {
    retString = retString + " Domain unknown : ";
  }

  retString = retString + " Socket, '" + 
    OPENSNMP_UTILITIES::intToString(socket) + "'";

  return retString;
}



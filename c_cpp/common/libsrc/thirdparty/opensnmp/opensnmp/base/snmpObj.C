//
// generic snmpObj implementation.
//

#include "config.h"
#include "snmpObj.H"
#include <typeinfo>

extern "C" 
{
    const char *opensnmp_version(void) 
    {
        return "0.4.0";
    }
 
}


snmpObj::snmpObj() {
}

snmpObj::~snmpObj() {
}

//  snmpObj
//  *snmpObj::clone() const {
//    return( new snmpObj(*this));
//  }

snmpObj::operator std::string() const
{
  return this->toString();
}

std::string
snmpObj::toString() const {
  return("snmpObj::toString() not implemented");
}

// string
// snmpObj::get_className() {
//   return(typeid(this).name());
// }

std::ostream&
operator<<( std::ostream& os, const snmpObj & obj )
{
  os << obj.operator std::string();
  return os;
}

// snmpException

#include "config.h"
#include "snmpException.H"

snmpException::snmpException() {
}

snmpException::snmpException(const snmpException & ref)
  : theErrorObj( ref.theErrorObj )
{

}

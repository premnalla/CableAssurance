//
// generic snmpArchObj
//

#include "config.h"
#include "snmpArchObj.H"
#include <stdio.h>
#include <iostream>
#include <string>


snmpArchObj::snmpArchObj()  {
}

snmpArchObj::~snmpArchObj()  {
}


snmpArchObj *
snmpArchObj::clone() const {
  std::cout << "\nERROR, not able to clone 'snmpArchObj'\n";
  throw;
}



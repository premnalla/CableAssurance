#ifndef OPEN_SNMP_UTILITIES_H
#define OPEN_SNMP_UTILITIES_H

#include <string>
#include <cstdlib>

namespace OPENSNMP_UTILITIES {

  bool    compareIgnoreCase(const std::string & s1, const std::string & s2 );
  bool    compareIgnoreCase(const std::string & s1, const char * s2 );

  bool    comparePrefix(const std::string & prefix, const std::string & s1);

  std::string intToString(int val);

};

#endif /* OPEN_SNMP_UTILITIES_H */

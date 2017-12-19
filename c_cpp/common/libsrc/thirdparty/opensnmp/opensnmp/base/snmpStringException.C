//
// snmpStringException:
//
//   A simple exception class to throw that contains a string as an
//   error message.

#include "config.h"
#include "snmpStringException.H"

snmpStringException::snmpStringException() {
    this->SSE_errorstr = "";
}

snmpStringException::snmpStringException(string Error_Str) : 
  SSE_errorstr(Error_Str) {
}

string
snmpStringException::what() {
  return this->SSE_errorstr;
}

string
snmpStringException::get_errorMessage() {
  return this->SSE_errorstr;
}

void
snmpStringException::set_errorMessage(string Error_Str) {
  this->SSE_errorstr = Error_Str;
}

  

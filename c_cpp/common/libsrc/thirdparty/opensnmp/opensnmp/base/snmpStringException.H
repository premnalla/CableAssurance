#ifndef STRINGEXCEPTION_H
#define STRINGEXCEPTION_H

#include <string>

#include "snmpException.H"

class snmpStringException : public snmpException {
 protected:
   string SSE_errorstr;
   
 public:
   snmpStringException();
   snmpStringException(string Error_Str);
   snmpStringException(const snmpStringException &ref) 
     : SSE_errorstr(ref.SSE_errorstr) {};
   virtual ~snmpStringException() throw() {};

   virtual string get_errorMessage();
   virtual void   set_errorMessage(string Error_Str);

   virtual string what(); // from <exception>
};

#endif /* STRINGEXCEPTION_H */

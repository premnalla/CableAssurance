#ifndef CONTROL_H
#define CONTROL_H
// control.H

// A basic "show how it works" implementation class.

#include "snmpScalarData.H"
#include <VarBindList.H>
#include "PersistentVarBind.H"
#include "MIBRegTree.H"

#if TIME_WITH_SYS_TIME
# include <sys/time.h>
# include <time.h>
#else
# if HAVE_SYS_TIME_H
#  include <sys/time.h>
# else
#  include <time.h>
# endif
#endif

enum control_columns 
{
    dump_COL = 1,
};


class controlMib : public snmpScalarData {
 private:
 public:
   MIBRegTree *mibtop;
   controlMib(MIBRegTree *top);
   virtual ~controlMib();

   virtual asnDataType  *get_column(unsigned int num) const;
   virtual snmpProtoErr *set_column(unsigned int num, asnDataType *data);
};
#endif /* CONTROL_H */

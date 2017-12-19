#ifndef SYSTEM_H
#define SYSTEM_H
// system.H

// A basic "show how it works" implementation class.

#include "asnDataTypes.H"
#include "snmpScalarSet.H"
#include <VarBindList.H>
#include "PersistentVarBind.H"

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

enum system_columns 
{
    sysDescr_COL = 1,
    sysObjectID_COL = 2,
    sysUpTime_COL = 3,
    sysContact_COL = 4,
    sysName_COL = 5,
    sysLocation_COL = 6,
    sysServices_COL = 7,
};


class systemMib : public snmpScalarSet {
 private:
   time_t      start_time; // read-only
 public:

   systemMib();
   virtual ~systemMib();

   virtual asnDataType *get_column(unsigned int num) const;
};
#endif /* SYSTEM_H */

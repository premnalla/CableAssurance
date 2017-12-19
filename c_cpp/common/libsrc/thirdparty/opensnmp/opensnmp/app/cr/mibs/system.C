// system.C: implementation for the system mib.

// Note, we're relocatable.  We assume we've been registered at OID
// and that OID.label.0 belongs to us.

#include <config.h>

#include "system.H"
#include "snmpProtoErr.H"

extern "C" 
{
#ifdef HAVE_SYS_UTSNAME_H
#include <sys/utsname.h>
#endif
}

static char*
get_sysname()
{
#ifdef HAVE_UNAME
    static struct utsname buf;
    uname(&buf);
    return buf.nodename;
#endif

    // no UNAME
    return "unknown";
}

systemMib::systemMib()
{
    time(&start_time);

    // set up default containers
    set_data(sysDescr_COL, new OctetString("dummy descr"));
    set_data(sysObjectID_COL, new OID(".1.3.6.1.4.1.2021.13.9988"));
    set_data(sysContact_COL,
             new PersistentVarBind(new OID(".1.3.6.1.2.1.1.4.0"),
                                   new OctetString("unknown contact")),
             true);
    set_data(sysName_COL, new OctetString(get_sysname()));
    set_data(sysLocation_COL, 
             new PersistentVarBind(new OID(".1.3.6.1.2.1.1.6.0"),
                                   new OctetString("unknown location")),
             true);
    set_data(sysServices_COL, new Integer32(72));
}

systemMib::~systemMib() {
}

asnDataType *
systemMib::get_column(unsigned int num) const {
    if (num == sysUpTime_COL) {
        return new TimeTicks((time(NULL) - start_time)*100);
    }
    return snmpScalarSet::get_column(num);
}

// XXX: actually set the real system name.


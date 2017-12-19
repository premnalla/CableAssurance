// control.C: implementation for the control mib.

// Note, we're relocatable.  We assume we've been registered at OID
// and that OID.label.0 belongs to us.

#include <config.h>

#include "control.H"
#include "snmpProtoErr.H"

controlMib::controlMib(MIBRegTree *top) : snmpScalarData(1)
{
    mibtop = top;
}

controlMib::~controlMib() {
    // These are not the things to delete that you're looking for.  Move along.
}

asnDataType *
controlMib::get_column(unsigned int num) const {
    switch(num) {
        case dump_COL:
            return new Integer32(0);  // XXX: um mem leak!

  }
  return 0;
}

snmpProtoErr *
controlMib::set_column(unsigned int num, asnDataType *data) {
    switch(num) {
        case dump_COL:
            mibtop->dumpTree("tree: ");
            break;
        default:
            return new snmpProtoErr(PROTOERR_NOTWRITABLE);
    }
    return NULL;
}


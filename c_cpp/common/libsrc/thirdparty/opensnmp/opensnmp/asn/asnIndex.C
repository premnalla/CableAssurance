#include "config.h"
#include "asnIndex.H"

asnIndex::asnIndex() {
}

asnIndex::asnIndex(berTag tag) : asnDataType(tag) {
}

asnIndex::~asnIndex() {
}

string
asnIndex::toString() const {
  return string("asnIndex::toString() not implemented");
}

OID
asnIndex::toOID(bool implied) 
{
    OID ret;
    
    appendOID(ret, implied);
    return ret;
}


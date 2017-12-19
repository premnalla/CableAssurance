// MIBRegistration.C

#include "config.h"
#include "MIBRegistration.H"

MIBRegistration::MIBRegistration(MIBImplementation *themib, OID *region,
                                 OctetString *context, 
                                 int range_subid, int upper_bound)
{
  this->mib = themib;
  this->region = region;
  this->context = context;
  this->range_subid = range_subid;
  this->upper_bound = upper_bound;
  search_ranges = new SearchRangeList();
}

MIBRegistration::MIBRegistration(const MIBRegistration&ref)
{
  this->mib = ref.mib;
  this->region = ref.region;
  this->context = ref.context;
  this->range_subid = ref.range_subid;
  this->upper_bound = ref.upper_bound;
  search_ranges = new SearchRangeList(*ref.search_ranges);
}

MIBRegistration::~MIBRegistration()
{
  if( search_ranges )
    delete search_ranges;
}

void
MIBRegistration::collectRanges(SearchRange *sr) {
  search_ranges->push_back(sr);
}

void
MIBRegistration::SearchRangeListReset() {
    if (search_ranges) {
        // XXX: nicley clean, watching for object deletion/extraction
        search_ranges->clear();
        delete search_ranges;
    }
    search_ranges = new SearchRangeList();
}

snmpProtoErr *
MIBRegistration::get() {
  return mib->snmp_get(this, search_ranges);
}

snmpProtoErr *
MIBRegistration::getNext() {
  return mib->snmp_getNext(this, search_ranges);
}

snmpProtoErr *
MIBRegistration::set(set_actions action) {
  if (action == AGENTX_TESTSET)
    return mib->snmp_set(this, action, search_ranges);
  else
    // XXX: AgentX doesn't include search ranges in the calls beyond TESTSET.
    // XXX: stay with that?
    return mib->snmp_set(this, action, search_ranges); 
}

OID *
MIBRegistration::get_reg_oid() {
  return region;
}

SearchRangeList *MIBRegistration::get_ranges(bool extract) {
  SearchRangeList *ret = search_ranges;
  if (extract)
    search_ranges = 0;
  return ret;
}

MIBImplementation *MIBRegistration::get_MIBImplementation(bool extract) {
    MIBImplementation *ret;
    ret = mib;
    
    if (extract)
        mib = 0;
    
    return ret;
}
    
void MIBRegistration::set_MIBImplementation(MIBImplementation *newone) {
    if (mib)
        delete mib;
    mib = newone;
}


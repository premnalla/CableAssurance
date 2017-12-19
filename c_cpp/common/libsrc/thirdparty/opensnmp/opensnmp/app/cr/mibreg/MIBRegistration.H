#ifndef MIBREGISTRATION_H
#define MIBREGISTRATION_H
// MIBRegistration.H

// most of the basis for this class was taken from the AgentX
// specifications to help test that transaction system as well.

// this needs to be above the MIBImplementation.H inclusion.
enum set_actions {
  AGENTX_TESTSET,
  AGENTX_COMMIT,
  AGENTX_UNDO,
  AGENTX_CLEANUP,
  WERE_FINISHED     // local state.
};

#include "snmpObj.H"
#include "OID.H"
#include "OctetString.H"
#include "snmpProtoErr.H"
#include "MIBImplementation.H"
#include "VarBind.H"
#include "VarBindList.H"
#include "SearchRange.H"

class MIBImplementation;

class MIBRegistration : public snmpObj {
 private:
   // The following specifies the mib region to be registered.
   OID *region;
   int  range_subid;
   int  upper_bound;

   // how it was registered
   int priority;
   OctetString *context;

   // The actual class that will do the hard work.
   MIBImplementation *mib;

   // A place to collect data for firing off in a single request to
   // the subagent.
   SearchRangeList *search_ranges;

   MIBRegistration(const MIBRegistration& );
 public:
   MIBRegistration(MIBImplementation *themib, OID *region,
                   OctetString *context = 0, 
                   int range_subid = 0, int upper_bound = 0);
   virtual ~MIBRegistration();
   virtual MIBRegistration *clone() const { return new MIBRegistration(*this); }
 
   // collectVarBind() must be called to request the varBind you
   // actually want the MIBImplementation to operate on with the
   // following operations.  This is required mainly for multi-pass
   // set_ processing, but is more convenient for some mib sections to
   // be dealt with in a single pass as well.
   virtual void collectRanges(SearchRange *sr);
   virtual void SearchRangeListReset();
   virtual SearchRangeList *get_ranges(bool extract=false);
   virtual snmpProtoErr *get();
   virtual snmpProtoErr *getNext();
   virtual snmpProtoErr *set(set_actions action);
   virtual OID *get_reg_oid();
   virtual MIBImplementation *get_MIBImplementation(bool extract = false);
   virtual void set_MIBImplementation(MIBImplementation *newone);
};
#endif /* MIBREGISTRATION_H */

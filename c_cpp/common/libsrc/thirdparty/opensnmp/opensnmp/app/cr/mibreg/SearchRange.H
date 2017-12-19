#ifndef SEARCHRANGE_H
#define SEARCHRANGE_H
// SearchRange.H

// most of the basis for this class was taken from the AgentX
// specifications, as they have thought endlessly on the subject of
// mib registrations and processing of snmp requests.

#include <list>

class SearchRange;
typedef std::list<SearchRange *> SearchRangeList;

#include "asnDataTypes.H"
#include "MIBRegTree.H"

class MIBRegTree;

class SearchRange : public snmpObj {
 private:
   // The following specifies the mib region to be registered.
   OID *start_oid;    // not necessarily == start_tree's beginning oid.

   MIBRegTree *start_tree;
   MIBRegTree *end_tree;

   VarBind *varbind;
   SearchRange(const SearchRange&);
   virtual SearchRange *clone() const { return new SearchRange(*this); }
 public:
   SearchRange(VarBind *varbind = 0, MIBRegTree *start_tree = 0, 
               MIBRegTree *end_tree = 0);
   virtual ~SearchRange();
   
   virtual OID *get_start_oid();

   virtual OID *get_end_oid();

   virtual MIBRegTree *get_start_tree();
   virtual void set_start_tree(MIBRegTree *start_tree);

   virtual MIBRegTree *get_end_tree();
   virtual void set_end_tree(MIBRegTree *);

   virtual VarBind *get_varbind();
   virtual void set_varbind(VarBind *varbind);
   
   // update the search range to the next section, based on the
   // current mib registration tree.  Note that this returns a pointer
   // to the same object for convenience, not a new one.
   virtual SearchRange *get_next();
};

#endif /* SEARCHRANGE_H */

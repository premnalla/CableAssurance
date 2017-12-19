#ifndef MIBREGTREE_H
#define MIBREGTREE_H
// MIBRegTree.H

#include "MIBRegistration.H"

class MIBRegTree;
class MIBRegistration;

class MIBRegTree : public snmpObj {
 private:
   MIBRegTree *children;
   MIBRegTree *parent;
   MIBRegTree *next, *prev;
   MIBRegistration *node;

 protected:
   virtual void addToChildren(MIBRegTree *addme);

 public:
   MIBRegTree(const MIBRegTree &);
   MIBRegTree(MIBRegistration *node);
   virtual ~MIBRegTree();
   virtual MIBRegTree *clone() const { return new MIBRegTree(*this); }
   virtual MIBRegTree *get_registered(OID *searchKey);
   virtual MIBRegistration *get_registration(bool extract = false);
   virtual void set_registration(MIBRegistration *node);
   virtual void addNew(MIBRegTree *addme);
   virtual MIBRegTree *get_children();
   virtual MIBRegTree *get_next();
   virtual MIBRegTree *get_parent();

   // debugging:
   void dumpTree(string prefix);
};
#endif /* MIBREGTREE_H */

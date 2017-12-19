#include "config.h"
#include "MIBRegTree.H"
#include "mibEmpty.H"
#include "debug.H"

MIBRegTree::MIBRegTree(const MIBRegTree &ref) {
  this->node = ref.node->clone();
  this->parent = ref.parent;
  this->children = ref.children->clone();
  this->next = ref.next;
}

MIBRegTree::MIBRegTree(MIBRegistration *node) {
  this->node = node;
  this->parent = 0;
  this->children = 0;
  this->next = 0;
}

MIBRegTree::~MIBRegTree() {
  if (node)
    delete node;
  if (children)
    delete children;

  // fix up next/prev/parent pointers
  if (prev)
    prev->next = next;
  else if (parent)
    parent->children = next;
  if (next)
    next->prev = prev;
}


// get_registered: returns a pointer to the oid registration object
// that best suits the given OID.
MIBRegTree *
MIBRegTree::get_registered(OID *searchKey) {
  // Do we match?
  MIBRegTree *ret;

  if ((node->get_reg_oid()->mincompare(*searchKey, 
                                       node->get_reg_oid()->length()))
      == EQUAL_TO) {
    // need to watch for something longer that matches as well though.
    if (children == 0)
      return this;

    ret = children->get_registered(searchKey);
    
    if (ret)       // if a child somewhere matches
      return ret;  // use it instead (longer OIDs have priority).
    
    return this;

  } else if (next != 0) {
    // we don't match, check our sibling.
    return next->get_registered(searchKey);
  }
  // nothing nowhere nohow matched.
  return 0;
}

void
MIBRegTree::addNew(MIBRegTree *addme) {
  OID *regOID = addme->get_registration()->get_reg_oid();
  MIBRegTree *ret = get_registered(regOID);
  DEBUGINIT(debugObj, "MIBRegTree::addNew");

  DEBUG9(debugObj, "adding " << (string) *regOID << " to: \n");
  dumpTree("tree: ");

  unsigned int position = ret->get_registration()->get_reg_oid()->size();

  if (position < addme->get_registration()->get_reg_oid()->size()) {
      // it's possible that the new node overlaps at some point further
      // downward with one of our children.  We need to split the tree at
      // that point instead of just adding it our children list.
      unsigned int nodenum = (*regOID)[position];

      for(MIBRegTree *child = ret->children; child; child = child->next) {
          OID *suboid = child->get_registration()->get_reg_oid();
            DEBUG9(debugObj,"checking " << (*suboid)[position] << " against " \
                   << nodenum << "\n");
          if ((*suboid)[position] == nodenum) {
              // this child matches lower in the oid tree.  Find the
              // exact point where the regoid and the current oid
              // differ.

                DEBUG9(debugObj, "subregistration found at #" << position 
                       << ": " << (string) *regOID << " under " \
                       << (string) *(ret->get_registration()->get_reg_oid()) \
                       << " lower under " << (string) *suboid << "\n");
              
              for(; position < child->get_registration()->get_reg_oid()->size() 
                      && position < suboid->size();
                  position++) {
                  if ((*suboid)[position] != (*regOID)[position]) {
                      // here the oid deviates.  Create a dummy node
                      // at this point, and register both the new node
                      // and the current child under the dummy node.

                      // erase ourselves from the chain, keeping our children.
                      if (child->prev) {
                          child->prev->next = child->next;
                      } else {
                          child->parent->children = child->next;
                      }
                      child->prev = NULL;
                      child->next = NULL;
                      child->parent = NULL;

                      OID *here = suboid->clone();
                      here->crop(1, position);

                      DEBUG9(debugObj,"splitting at #" << position << ": " \
                             << (string) *here << "\n");

                      MIBRegTree *newbogus =
                          new MIBRegTree(
                              new MIBRegistration(new mibEmpty(),
                                                  here,
                                                  new OctetString("none")));

                      // set up the tree
                      ret->addToChildren(newbogus);
                      newbogus->addToChildren(child);
                      newbogus->addToChildren(addme);
                      return;
                  }
              }

              // uh oh.
              // XXX: replace emptyMibs but simply discard other 
              //      registrations with a log message.
              DEBUG9(debugObj, "haven't handled duplications yet\n");
              throw;
          }
      }
      
  } else {
      // this should be the only possibility, but double checking in
      // case of logic fallacy:
      if (*(addme->get_registration()->get_reg_oid()) ==
          *(ret->get_registration()->get_reg_oid())) {

          // XXX: compare contexts, and allow duplicates of different.

          if (dynamic_cast<mibEmpty *>
              (ret->get_registration()->get_MIBImplementation()) != 0) {
              ret->set_registration(addme->get_registration(true));
              delete addme;
              return;
          } else {
              // XXX: drop the registration request and return an error?
              DEBUG9(debugObj, "haven't handled duplications yet\n");
              throw;
          }
      } else {
          DEBUG9(debugObj, "shouldn't have gotten here\n");
          throw;
      }
  }
      
  // ret contains the lowest match, so we merely add it to the
  // children list (sorted properly).
  ret->addToChildren(addme);
}

void
MIBRegTree::addToChildren(MIBRegTree *addme) {
  MIBRegTree *addnode = children, *prevnode = 0;
  OID *regOID = addme->get_registration()->get_reg_oid();
  
  while(addnode != 0 && 
        *regOID > *(addnode->get_registration()->get_reg_oid())) {
      prevnode = addnode;
      addnode = addnode->next;
  }

  addme->parent = this;
  if (addnode == 0) {
    // nothing beyond us.
    addme->next = 0;
    addme->prev = prevnode;
    if (prevnode)
      prevnode->next = addme;
    else
      this->children = addme;
  } else {
    addme->next = addnode;
    addme->prev = addnode->prev;
    addnode->prev = addme;
    if (prevnode)
      prevnode->next = addme;
  }

  if (prevnode == 0)
    children = addme;
}


MIBRegistration *
MIBRegTree::get_registration(bool extract) {
    MIBRegistration *ret = node;
    if (extract)
        node = 0;
    return ret;
}

void MIBRegTree::set_registration(MIBRegistration *node) {
    if (this->node)
        delete this->node;
    this->node = node;
}

MIBRegTree *MIBRegTree::get_children() {
  return children;
}

MIBRegTree *MIBRegTree::get_next() {
  return next;
}

MIBRegTree *MIBRegTree::get_parent() {
  return parent;
}

// debugging printout only
void MIBRegTree::dumpTree(string prefix) {
    MIBRegTree *mrptr;
    DEBUGCREATE_L(debugObj, "MIBRegTree::dumpTree");
    
    DEBUG9_L(debugObj, prefix << (string) *(node->get_reg_oid()) << "\n");
    for(mrptr = get_children(); mrptr; mrptr = mrptr->get_next()) {
        mrptr->dumpTree(prefix + "  ");
    }
}

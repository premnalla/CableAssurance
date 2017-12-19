/* SearchRange.C */

#include "config.h"
#include "SearchRange.H"

SearchRange::SearchRange(VarBind *varbind, MIBRegTree *start_tree, 
                         MIBRegTree *end_tree) {
  this->varbind = varbind;
  this->start_oid = varbind->get_OID()->clone();
  this->start_tree = start_tree;
  this->end_tree = end_tree;
}

SearchRange::SearchRange(const SearchRange& ref) {
  this->varbind = ref.varbind;
  this->start_oid = ref.start_oid->clone();
  this->start_tree = ref.start_tree;
  this->end_tree = ref.end_tree;
}

SearchRange::~SearchRange() {
  // don't delete anything else, as we don't own the information.
  if (start_oid)
    delete start_oid;
}

OID *SearchRange::get_start_oid() {
  return start_oid;
}

OID *SearchRange::get_end_oid() {
    if (end_tree)
        return end_tree->get_registration()->get_reg_oid();
    return NULL;
}

MIBRegTree *SearchRange::get_start_tree() {
  return start_tree;
}

void SearchRange::set_start_tree(MIBRegTree *start_tree) {
  this->start_tree = start_tree;
}

MIBRegTree *SearchRange::get_end_tree() {
  return end_tree;
}

void SearchRange::set_end_tree(MIBRegTree *end_tree) {
  this->end_tree = end_tree;
}

SearchRange *SearchRange::get_next() {
  if (start_tree == 0)
    return 0;
  if (end_tree != NULL) {
    start_tree = end_tree;
    end_tree = end_tree->get_children();
  } else {
    end_tree = start_tree->get_next();
    start_tree = start_tree->get_parent();
  }
  if (start_tree)
    return this;
  return 0;
}

VarBind *SearchRange::get_varbind() {
  return varbind;
}

void SearchRange::set_varbind(VarBind *varbind) {
  // don't delete the previous one, it is not ours.
  this->varbind = varbind;
}



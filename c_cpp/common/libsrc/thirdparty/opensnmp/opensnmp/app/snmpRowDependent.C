#include "config.h"
#include "snmpRowDependent.H"

snmpRowDependent::snmpRowDependent(snmpDataTable *table, //= NULL
                                   snmpRow *row) {       //= NULL
    this->table = table;
    this->row = row;
}

snmpRowDependent::snmpRowDependent(const snmpRowDependent &thecopy) {
    this->table = thecopy.table;
    this->row = thecopy.row;
}

snmpRowDependent::~snmpRowDependent() {
    // don't delete row or table
}
    
void
snmpRowDependent::set_table(snmpDataTable  *table) {
  this->table = table;
}

snmpDataTable  *
snmpRowDependent::get_table(bool extract) {
  snmpDataTable  *ret = table;
  if (extract)
    table = NULL;
  return ret;
}

void
snmpRowDependent::set_row(snmpRow  *row) {
  this->row = row;
}

snmpRow  *
snmpRowDependent::get_row(bool extract) {
  snmpRow  *ret = row;
  if (extract)
    row = NULL;
  return ret;
}


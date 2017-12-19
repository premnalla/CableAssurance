#include "config.h"
#include "snmpCRRegisterTable.H"

snmpCRRegisterTable::snmpCRRegisterTable(snmpDataTable *table, 
                                         OID *registerAt, 
                                         OctetString *context,
                                         unsigned int maxcolumn, 
                                         unsigned int mincolumn,
                                         snmpDataTable::dataStorageType dSType,
					 snmpRowManager *row_manager) 
    : snmpCRRegisterObj(registerAt, context)
{
    this->table = table;
    this->maxcolumn = maxcolumn;
    this->mincolumn = mincolumn;
    this->dSType = dSType;
    this->row_manager = row_manager;
}

snmpCRRegisterTable::~snmpCRRegisterTable() {
    if (this->table)
        delete this->table;
}

void
snmpCRRegisterTable::set_table(snmpDataTable  *table) {
  if (this->table)
    delete this->table;
  this->table = table;
}

snmpDataTable  *
snmpCRRegisterTable::get_table(bool extract) {
  snmpDataTable  *ret = table;
  if (extract)
    table = NULL;
  return ret;
}

void
snmpCRRegisterTable::set_rowManager(snmpRowManager  *manager) {
  if (this->row_manager)
    delete this->row_manager;
  this->row_manager = manager;
}

snmpRowManager  *
snmpCRRegisterTable::get_rowManager(bool extract) {
  snmpRowManager  *ret = row_manager;
  if (extract)
    row_manager = NULL;
  return ret;
}

unsigned int
snmpCRRegisterTable::get_maxcolumn() {
    return maxcolumn;
}

void
snmpCRRegisterTable::set_maxcolumn(unsigned int maxcolumn) {
    this->maxcolumn = maxcolumn;
}
    
unsigned int
snmpCRRegisterTable::get_mincolumn() {
    return mincolumn;
}

void
snmpCRRegisterTable::set_mincolumn(unsigned int mincolumn) {
    this->mincolumn = mincolumn;
}

snmpDataTable::dataStorageType
snmpCRRegisterTable::get_dSType() {
    return dSType;
}

void
snmpCRRegisterTable::set_dSType(snmpDataTable::dataStorageType dSType) {
    this->dSType = dSType;
}
    

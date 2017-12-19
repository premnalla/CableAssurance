#include "config.h"
#include "snmpRow.H"
#include <typeinfo>
#include <sstream>
#include "debug.H"
#include "snmpDatabaseObj.H"

snmpRow::snmpRow(const snmpRow &ref) {
    this->columnAllocators = ref.columnAllocators;
    map<int, asnDataType *>::const_iterator i;
    for(i = ref.data.begin(); i != ref.data.end(); i++) {
      set_column(i->first, *i->second);
    }
}

snmpRow::snmpRow(map<int, snmpColumnAllocator *> *columnAllocators,
                 map<int, asnDataType *> *defValues) {
    this->columnAllocators = columnAllocators;
    if (defValues)
      allocateMissingDefaults( *defValues );
}

snmpRow::~snmpRow() {
    // data contents are ours (we clone in set_column), so delete
    map<int, asnDataType *>::const_iterator i;
    for(i = data.begin(); i != data.end(); i++) {
      delete i->second;
    }
}

asnDataType *
snmpRow::get_column(int col, bool extract) {
    asnDataType *ret = data[col];
    if (extract)
        data[col] = NULL;
    return ret;
}

// pass in the asnDataType * of a value in this row, this returns its 
// column number. (So an object in a row can find what column it's in)
// If not found, get_column_number returns 0;
int   
snmpRow::get_column_number(asnDataType *val) {
  map<int, asnDataType *>::const_iterator  row_it;
  for(row_it = this->data.begin(); row_it != this->data.end(); row_it++) {
    if (val == row_it->second) return(row_it->first);
  }
  return(0);
}


void
snmpRow::set_column(int col, const asnDataType & thedata){
    asnDataType *olddata = data[col];
    
    // does the column exist?
    if (!olddata && columnAllocators && (*columnAllocators)[col] != NULL) {
        // no. ok, then can we create it using an allocator?
        data[col] = olddata = ((*columnAllocators)[col])->allocate(this);
    }
    
    // does the column exist yet?
    if (olddata) {
        // yes, tell it to change itself to the new value.
        olddata->change_value(thedata);
    } else {
        // no, copy the new value.
        data[col] = thedata.clone();
    }
}

void
snmpRow::create_column(int col, const asnDataType & thedata){

    // does the column exist yet?
    if (data[col]) {
        // yes, what are you doing calling create?
      throw; // xxx-rks : throw what??
    }
    
    // can we create it using an allocator?
    if (columnAllocators && (*columnAllocators)[col] != NULL) {
      // yes. do it.
      data[col] = ((*columnAllocators)[col])->allocate(this, thedata);
    } else {
      // no, copy the new value.
      data[col] = thedata.clone();
    }
}

// use default values to fill in any empty colums
void snmpRow::allocateMissingDefaults( const defaultValueList & defValues) {

  map<int, asnDataType *>::const_iterator i;
  for(i = defValues.begin(); i != defValues.end(); i++) {
    if( get_column( i->first ) == NULL )
      set_column(i->first, *i->second);
  }
}

// Not to be used by the general public if avoidable.  Use set_column instead.
void
snmpRow::set_columnForced(int col, asnDataType *thedata){
    if (data[col])
        delete data[col];
    data[col] = thedata;
}

string
snmpRow::toString() const {
  ostringstream buf;

  map<int, asnDataType *>::const_iterator i;
  for(i = data.begin(); i != data.end(); i++) {
      if (i->second) {
          buf << "col " << i->first << '=' << *i->second << '|';
      } else {
          buf << "col " << i->first << '=' << "NULL" << '|';
      }
  }
  buf << ends;

  return buf.str();
}


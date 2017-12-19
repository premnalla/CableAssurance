#include "config.h"
#include "snmpRowStatusCheckColsExist.H"

snmpRowStatusCheckColsExist::snmpRowStatusCheckColsExist
  (std::list <int> &initlist) : mustExistColumns(initlist)
{
}

snmpRowStatusCheckColsExist::snmpRowStatusCheckColsExist() {
}

snmpRowStatusCheckColsExist::snmpRowStatusCheckColsExist(const snmpRowStatusCheckColsExist & ref )
  : mustExistColumns( ref.mustExistColumns )
{
}

snmpRowStatusCheckColsExist::snmpRowStatusCheckColsExist(const int &startcol, 
                                                         const int &endcol) {
    for(int i = startcol; i <= endcol; i++)
        mustExistColumns.push_back(i);
}

void snmpRowStatusCheckColsExist::add(int &newcol) {
    mustExistColumns.push_back(newcol);
}

void snmpRowStatusCheckColsExist::add_range(int &startcol, int &endcol) {
    for(int i = startcol; i <= endcol; i++)
        add(i);
}

bool snmpRowStatusCheckColsExist::check_row(snmpRow *row) {
    std::list<int>::iterator col;
    for(col = mustExistColumns.begin(); col != mustExistColumns.end(); col++) {
        if (row->get_column(*col) == NULL)
            return false;
    }
    return true;
}

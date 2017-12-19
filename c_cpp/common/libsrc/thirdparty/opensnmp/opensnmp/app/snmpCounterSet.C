#include <config.h>

#include "snmpCounterSet.H"

snmpCounterSet::snmpCounterSet() {
}

snmpCounterSet::snmpCounterSet(unsigned int highcolnum, 
                               unsigned int lowcolnum) {
    for(unsigned int i = lowcolnum; i <= highcolnum; i++) {
        addCounterAt(i);
    }
}

void
snmpCounterSet::addCounterAt(unsigned int num) {
    if (!get_column(num)) // don't delete previously existing ones.
        set_data(num, new Counter32((unsigned int) 0));
}    

Counter32 *
snmpCounterSet::increment(unsigned int column, unsigned int value) {
    Counter32 *col = dynamic_cast<Counter32 *> (get_column(column));
    if (!col) {
        addCounterAt(column);
        col = dynamic_cast<Counter32 *> (get_column(column));    
        if (!col)
            return NULL; // XXX throw error or debug string at least?
    }
    *col += value;
    return col;
}

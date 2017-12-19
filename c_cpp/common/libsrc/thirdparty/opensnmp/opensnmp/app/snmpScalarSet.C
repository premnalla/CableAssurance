#include <config.h>

#include "snmpScalarSet.H"

snmpScalarSet::snmpScalarSet() : snmpScalarData(1,1) {
}

snmpScalarSet::~snmpScalarSet() {
    std::map <int, asnDataType *>::iterator iter;
    for(iter = dataset.begin();
        iter != dataset.end();
        iter++) {
        if (iter->second != NULL) {
            delete iter->second;
        }
    }
}

void
snmpScalarSet::set_data(unsigned int colnum, asnDataType *data,
                        bool iswritable) {  //= NULL
    if (dataset[colnum] != NULL)
        delete dataset[colnum];
    dataset[colnum] = data;
    if (colnum > get_max_col())
        set_max_col(colnum);
    if (colnum < get_min_col())
        set_min_col(colnum);
    if (iswritable)
        writableflags[colnum] = true;
}

asnDataType *
snmpScalarSet::get_data(unsigned int colnum, bool extract) {
    asnDataType *ret;
    ret = dataset[colnum];
    if (extract)
        dataset[colnum] = NULL;
    return ret;
}
   
asnDataType *
snmpScalarSet::get_column(unsigned int colnum) const {
    std::map <int, asnDataType *>::const_iterator it;
    it = dataset.find(colnum);
    if (it != dataset.end())
        return it->second;
    return NULL;
}

snmpProtoErr *
snmpScalarSet::set_column(unsigned int colnum, const asnDataType *data) {
    if (dataset[colnum]) {
        if (!writableflags[colnum])
            return new snmpProtoErr(PROTOERR_NOTWRITABLE);
        dataset[colnum]->change_value(*data);
    } else {
        return new snmpProtoErr(PROTOERR_NOSUCHNAME);
    }
    return NULL;
}

bool
snmpScalarSet::is_writable(unsigned int colnum) const {
    std::map <int, bool>::const_iterator it;
    it = writableflags.find(colnum);
    //    if (it != dataset.end())
    if (it != writableflags.end())
        return it->second;
    return false;
}

    

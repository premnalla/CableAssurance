#include "config.h"
#include <iostream>
#include <sstream>
#include <assert.h>

#include "snmpIndexes.H"
#include "debug.H"

using std::list;

snmpIndexes::snmpIndexes( int size, bool lastimplied ) {
    this->_size = size;
    this->values = NULL;
    this->lastimplied = lastimplied;
    allocateArray();
}

snmpIndexes::snmpIndexes(const list<berTag> &indexTypes, bool lastimplied) {
    this->indexTypes = indexTypes;
    this->_size = indexTypes.size();
    this->values = NULL;
    this->lastimplied = lastimplied;
    allocateArray();
}

snmpIndexes::snmpIndexes(const snmpIndexes &ref) {
    this->values = NULL;
    copy(ref);
}

void
snmpIndexes::allocateArray() {
    assert( this->values == NULL );
    this->values = new asnIndex *[this->_size];
    for(unsigned int i=0; i < this->_size; i++) {
        this->values[i] = 0;
    }
}

snmpIndexes &
snmpIndexes::copy(const snmpIndexes &ref) {
    // free old values
    deleteData();

    // copy new values
    _size = ref._size;
    indexTypes = ref.indexTypes;
    lastimplied = ref.lastimplied;
    assert( _size >= indexTypes.size() );
    allocateArray();
    if (indexTypes.size() > 0) {
        for(unsigned int i=0; i < indexTypes.size(); i++) {
	  if (ref.values[i])
	    values[i] = ref.values[i]->clone();
        }
    }
    return *this;
}

snmpIndexes::~snmpIndexes(){
    deleteData();
}

void
snmpIndexes::deleteData(){
    // don't try to index off NULL pointer
    if( values == NULL )
      return;

    // free each value
    for(unsigned int i=0; i < indexTypes.size(); i++) {
        if (values[i])
            delete values[i];
    }
    // free array
    delete [] values;
    values = NULL;
}

OID
snmpIndexes::get_OID(OID *prefix) const {
    if (prefix) {
        OID ret(*prefix);
        append_OID(ret);
        return ret;
    } else {
        OID ret;
        append_OID(ret);
        return ret;
    }
}

OID&
snmpIndexes::append_OID(OID &to) const {
    unsigned int j;

    for(j=0; j < indexTypes.size() && values[j] != NULL; j++) {
        if (lastimplied && (j == indexTypes.size()-1))
            values[j]->appendOID(to, true); // implied
        else
            values[j]->appendOID(to, false);
    }
    return to;
}

bool
snmpIndexes::operator < (const snmpIndexes &ref) const {
    OID a = get_OID();
    OID b = ref.get_OID();
    return (a < b);
}

bool
snmpIndexes::set_fromOID(const OID & theoid, const OID & prefix) {
    if (theoid.mincompare(theoid) != EQUAL_TO) {
        std::cerr << "snmpIndexes::set_fromOID: prefix not equal\n";
        // XXX: throw something?
    }
    return set_fromOID(theoid, prefix.length());
}

bool
snmpIndexes::set_fromOID(const OID & theoid, unsigned int prefixlen) {
    DEBUGINIT(debugObj, "snmpIndexes");
    list<berTag>::iterator ii, lasttest;
    OID::const_iterator oi, tmpi;
    unsigned int i, len, index, oidspot;
    unsigned char cbuf[128]; // XXX
    unsigned int ibuf[128]; // XXX
    
    // get the starting point
    if (prefixlen)
        for(oidspot=1, oi = theoid.begin(); 
            oi != theoid.end() && oidspot < prefixlen; oi++, oidspot++);
    else
        oi = theoid.begin();
    
    // map the types
    
    for(index=0, ii=indexTypes.begin(); 
        ii != indexTypes.end() && oi != theoid.end(); index++, ii++) {
        lasttest = ii;
        lasttest++;
        switch ((int) *ii) {
            case BER_TAG_INTEGER32:
                values[index] = new Integer32(*oi);
                oidspot++;
                oi++;
                break;
            
            case BER_TAG_OCTET_STRING:
                if (lastimplied && (lasttest == indexTypes.end())) {
                    len = theoid.length() - oidspot + 1;
                } else {
                    len = *oi;
                    oi++;
                }

                for(i=0; oi != theoid.end() && i < len; i++, oi++) {
                    if (*oi > 255)
                        return false; // illegal value.
                    cbuf[i] = (unsigned char) *oi;
                    oidspot++;
                }

                if (oi == theoid.end() && i < len)
                    return false; // too short. // XXX: memory leak.

                cbuf[len] = '\0';
                values[index] = new OctetString((char *) cbuf, len);
                break;

            case BER_TAG_OBJECT_ID:
                if (lastimplied && (lasttest == indexTypes.end())) {
                    len = theoid.length() - oidspot + 1;
                } else {
                    len = *oi;
                    oi++;
                }

                for(i=0; i < len && oi != theoid.end(); 
                    i++, oi++) {
                    ibuf[i] = (*oi);
                    oidspot++;
                }
                
                if (oi == theoid.end() && i < len)
                    return false; // too short. // XXX: memory leak.

                values[index] = new OID(ibuf, len);
                break;
                

          default:
            std::cerr << "snmpIndexes::set_fromOID: unknown type requested: " 
		      << ((int) *ii) << "\n";
            return false;
        }
    }
    if (oi != theoid.end()) {
        DEBUG9(debugObj, "snmpIndexes:set_fromOID (trail): ");
        for(; oi != theoid.end(); oi++) {
            DEBUG9(debugObj, *oi << ".");
        }
        DEBUG9(debugObj, "\n");
        return false;
    }
    
    if (oi == theoid.end() && ii == indexTypes.end())
        return true;
    return false;
}

asnIndex *
snmpIndexes::get_index_number(unsigned int num){
    // use num-1 so that they can reference it by the column number (typically).
    if (num && (--num < indexTypes.size()))
        return values[num];
    return NULL;
}

const asnIndex *
snmpIndexes::get_index_number(unsigned int num) const {
    // use num-1 so that they can reference it by the column number (typically).
    if ( num && (--num < indexTypes.size()))
        return values[num];
    return NULL;
}

void
snmpIndexes::set_index_number(unsigned int num, asnIndex *value){
    // use num-1 so that they can reference it by the column number (typically).
    if (num >= 1 && num <= indexTypes.size()) {
        list<berTag>::iterator bti;
        int i;
        for(bti = indexTypes.begin(), i = 1;
            bti != indexTypes.end() && i < (int) num; bti++, i++);
        if (value->get_tag() != *bti) {
            throw snmpIndexesWrongDataTypeError((int) num);
        }
        if (values[num-1])
            delete values[num-1];
	values[num-1] = value;
    } else {
        throw snmpIndexesWrongDataTypeError(-1);
    }
}

void
snmpIndexes::add_index( berTag type ) {
  assert( indexTypes.size() < _size ); // rks-xxx: throw bounds error?
  indexTypes.push_back( type );
}

snmpIndexes *
snmpIndexes::clone() const {
    return new snmpIndexes(*this);
}

snmpIndexes *
snmpIndexes::cloneEmpty() const {
    return new snmpIndexes(indexTypes);
}

void snmpIndexes::set_indexes(list<asnIndex *> &siref) {
    list<asnIndex *>::iterator lai = siref.begin();
    unsigned int i;
    for(lai = siref.begin(), i=1; lai != siref.end(); lai++, i++) {
        set_index_number(i, *lai);
    }
}

void snmpIndexes::set_indexes_cloned(list<asnIndex *> &siref) {
    list<asnIndex *>::iterator lai = siref.begin();
    unsigned int i;
    for(lai = siref.begin(), i=1; lai != siref.end(); lai++, i++) {
        set_index_number(i, (*lai)->clone());
    }
}

string snmpIndexes::toString() const {
    std::ostringstream buf;
    list<berTag>::const_iterator ii;
    int i;
    
    for(i=1, ii=indexTypes.begin(); ii != indexTypes.end(); i++, ii++) {
        const asnDataType *ind = get_index_number(i);
        buf << i << " (tag=" << (int) *ii << "): ";
        if (ind)
            buf << (string) *get_index_number(i);
        else
            buf << "NULLINDEX";
        buf << ", ";
    }
    buf << std::ends;
    return string(buf.str());
}

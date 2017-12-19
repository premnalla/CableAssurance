#include "snmpDatabaseRec.H"

//======================================================================
//
// snmpDatabaseRec
//
snmpDatabaseRec::~snmpDatabaseRec(void)
{
    if(k)
        delete []k;
    if(d)
        delete []d;
}

//------------------------------------------------------------
void
snmpDatabaseRec::set( const VarBind & vb, int flags )
{
    if( flags & key )
        setKey( * vb.get_OID() );
    if( flags & data )
        setData( * vb.get_value() );
}

void
snmpDatabaseRec::get( VarBind& vb, int flags )
{
    if( flags & key )
        getKey( vb );
    if( flags & data )
        getData( vb );
}


//------------------------------------------------------------
void
snmpDatabaseRec::setKey( void *key, size_t key_len )
{
    if(key_len > k_len) {
        if(k)
            delete [] k;
        k = new char[key_len];
    }
    memcpy(k,key,key_len);
    k_len = key_len;
}

void
snmpDatabaseRec::setKey( const OID& oid )
{
    char buf[1024], *start;
    int len;

    start = oid.asnEncode( &buf[sizeof(buf)-1] ) + 1;
    len = &buf[sizeof(buf)-1] - start + 1;
    setKey( start, len );
}

void
snmpDatabaseRec::getKey( VarBind &vb )
{
    asnDataType *pKey;
    asnDataType::asnDecodeUnknown( (char*)getKey(), &pKey,
                                   getKeyLen() );
    vb.set_OID( dynamic_cast<OID*>(pKey) );
}


//------------------------------------------------------------
void
snmpDatabaseRec::setData( void *data, size_t data_len )
{
    if(data_len > d_len) {
        if(d)
            delete [] d;
        d = new char[data_len];
    }
    memcpy(d,data,data_len);
    d_len = data_len;
}

void
snmpDatabaseRec::setData( const asnDataType& value )
{
    char buf[1024], *start;
    int len;

    start = value.asnEncode( &buf[sizeof(buf)-1] ) + 1;
    len = &buf[sizeof(buf)-1] - start + 1;
    setData( start, len );
}

void
snmpDatabaseRec::getData( VarBind &vb )
{
    asnDataType *pData;
    asnDataType::asnDecodeUnknown( (char*)getData(), &pData,
                                   getDataLen() );
    vb.set_value(pData);
}

//------------------------------------------------------------

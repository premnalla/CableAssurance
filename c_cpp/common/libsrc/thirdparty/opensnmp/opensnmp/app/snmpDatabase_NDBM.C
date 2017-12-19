#include "config.h"

#include <iostream>
#include <sstream>
#include <fstream>
#include <assert.h>

#include "snmpDatabase_NDBM.H"

extern "C" {
#include "db1/ndbm.h"
#include "fcntl.h"
};


//======================================================================
//
//
// snmpDatabase_NDBM methods
//
//
typedef struct dbm_ctx {
   DBM  *dbm;
   datum key;
} dbm_ctx;

snmpDatabaseContext
snmpDatabase_NDBM::open( const string & dbPathIn )
{
    DEBUGCREATE(this->pDebug, "snmpDatabase_NDBM");
    flow( "snmpDatabase_NDBM ctor( " + dbPathIn + " )" );
    
    string dbfile( dbPathIn );
    dbfile.append("persistentData");

    dbm_ctx *ctx = (dbm_ctx *)malloc(sizeof(dbm_ctx));
    ctx->dbm = ::dbm_open(dbfile.c_str(), O_RDWR | O_CREAT, 0660 );

    return static_cast<snmpDatabaseContext>(ctx);
}

void
snmpDatabase_NDBM::close( snmpDatabaseContext &context )
{
    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);
    dbm_close(ctx->dbm);
    free(context);
}

void
snmpDatabase_NDBM::_firstkey( snmpDatabaseContext & context,
                              snmpDatabaseRec & putHere )
{
    flow( "firstkey" );

    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);
    ctx->key = ::dbm_firstkey(ctx->dbm);
    if(NULL == ctx->key.dptr) {
        putHere.setError(snmpDatabaseRec::noSuchRec);
        return;
    }
    putHere.setKey( ctx->key.dptr, ctx->key.dsize );

    _fetch( context, putHere );
}

void
snmpDatabase_NDBM::_nextkey( snmpDatabaseContext & context,
                             snmpDatabaseRec & putHere )
{
    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);
    ctx->key = ::dbm_nextkey(ctx->dbm);
    if(NULL == ctx->key.dptr) {
        putHere.setError(snmpDatabaseRec::noSuchRec);
        return;
    }
    putHere.setKey( ctx->key.dptr, ctx->key.dsize );

    _fetch( context, putHere );
}

void
snmpDatabase_NDBM::_store( snmpDatabaseContext & context,
                           snmpDatabaseRec & fromHere, int flags )
{
    datum key, content;
    key.dptr = (char*)fromHere.getKey();
    key.dsize = fromHere.getKeyLen();
    content.dptr = (char*)fromHere.getData();
    content.dsize = fromHere.getDataLen();
    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);

    int ret;
    if( flags == snmpDatabaseObj::insert )
        ret = ::dbm_store(ctx->dbm, key, content, DBM_INSERT);
    else
        ret = ::dbm_store(ctx->dbm, key, content, DBM_REPLACE);
    if (0 == ret)
        fromHere.setError( snmpDatabaseRec::noError );
    else if (1 == ret)
        fromHere.setError( snmpDatabaseRec::duplicateRec );
}

void
snmpDatabase_NDBM::_fetch( snmpDatabaseContext &context,
                           snmpDatabaseRec & putHere )
{
    datum key, content;
    key.dptr = (char*)putHere.getKey();
    key.dsize = putHere.getKeyLen();
    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);

    content = ::dbm_fetch( ctx->dbm, key );
    if (0 != content.dptr) {
        putHere.setError( snmpDatabaseRec::noError );
        putHere.setData( content.dptr, content.dsize );
    }
    else
        putHere.setError( snmpDatabaseRec::noSuchRec );
}

void
snmpDatabase_NDBM::_deleterec( snmpDatabaseContext &context,
                               snmpDatabaseRec & recKey,
                               snmpDatabaseRec * recOut )
{
    if(recOut) {
        recOut->setKey( (void*)recKey.getKey(), recKey.getKeyLen() );
        _fetch( context, *recOut );
        // if we can't fetch it, we probably can't delete it
        if(recOut->getError() != snmpDatabaseRec::noError)
            return;
    }

    datum key;
    key.dptr = (char*)recKey.getKey();
    key.dsize = recKey.getKeyLen();
    dbm_ctx *ctx = static_cast<dbm_ctx*>(context);

    int ret = ::dbm_delete( ctx->dbm, key );
    if (0 == ret)
        recKey.setError( snmpDatabaseRec::noError );
    else
        recKey.setError( snmpDatabaseRec::noSuchRec );

    if(recOut)
        recOut->setError( recKey.getError() );
}

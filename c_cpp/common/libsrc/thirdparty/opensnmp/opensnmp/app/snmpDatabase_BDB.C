#include "config.h"

#include <iostream>
#include <sstream>
#include <fstream>
#include <assert.h>

#include "snmpDatabase_BDB.H"


//======================================================================
//
//
// snmpDatabase_BDB methods
//
//
struct bdb_ctx {
   DbEnv *dbEnv;
   Db    *db;
   Dbc   *dbcp;
};

snmpDatabaseContext
snmpDatabase_BDB::open( const string & dbPathIn )
{
    DEBUGCREATE(this->pDebug, "snmpDatabase_BDB");
    flow( "snmpDatabase_BDB ctor( " + dbPathIn + " )" );
    
    struct bdb_ctx * ctx = (struct bdb_ctx *)new char(sizeof(bdb_ctx));
    ctx->dbcp = NULL;

    // order is important here!! DbEnv must be before Db!!!
    ctx->dbEnv = new DbEnv(0);
    if(NULL == ctx->dbEnv) {
        delete (char*)ctx;
        throw; // xxx-rks: throw what?
    }
    ctx->dbEnv->set_error_stream( &cerr );
    ctx->dbEnv->set_errpfx( "snmpDatabase_BDB" );

    //
    // We have multiple processes reading/writing these files, so
    // we need concurrency control and a shared buffer pool, but
    // not logging or transactions.
    //
    int rc = ctx->dbEnv->open(dbPathIn.c_str(),
#ifdef STATIC_LIB
                              DB_THREAD |
#endif
                              DB_RECOVER | DB_CREATE | DB_INIT_LOCK | DB_INIT_MPOOL | DB_INIT_TXN,
                              0644);
    if( rc != 0 ) {
        delete ctx->dbEnv;
        delete (char*)ctx;
        throw; // xxx-rks: throw what?
    }

    ctx->db = new Db( ctx->dbEnv, 0);
    if(NULL == ctx->db){
        delete ctx->dbEnv;
        delete (char*)ctx;
        throw; // xxx-rks: throw what?
    }

#if (DB_VERSION_MAJOR == 4) && (DB_VERSION_MINOR > 0)
    ctx->db->open( NULL, "persistentData", NULL, DB_BTREE, DB_CREATE, 0 );
#else
    ctx->db->open( "persistentData", NULL, DB_BTREE, DB_CREATE, 0 );
#endif

    return static_cast<snmpDatabaseContext>(ctx);
}

void
snmpDatabase_BDB::close( snmpDatabaseContext &context )
{
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);

    try {
        if (ctx->dbcp)
            ctx->dbcp->close();

        if (ctx->db)
            ctx->db->close(0);

        if (ctx->dbEnv)
            ctx->dbEnv->close(0);
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
    }
}

void
snmpDatabase_BDB::_firstkey( snmpDatabaseContext & context,
                             snmpDatabaseRec & rec )
{
    flow( "firstkey" );
    
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);
    if(ctx->dbcp) {
        ctx->dbcp->close();
    }

    try {
        // Acquire a cursor for the table.
        ctx->db->cursor(NULL, &ctx->dbcp, 0);
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
        rec.setError( snmpDatabaseRec::unknownError );
    }

    return _nextkey( context, rec );
}

void
snmpDatabase_BDB::_nextkey( snmpDatabaseContext & context,
                            snmpDatabaseRec & rec )
{
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);

    try {
        Dbt key((void*)rec.getKey(), rec.getKeyLen());
        Dbt data;
        int ret = ctx->dbcp->get(&key, &data, DB_NEXT);
        if (ret == 0) {
            rec.setError( snmpDatabaseRec::noError );
            rec.setKey(key.get_data(),key.get_size());
            rec.setData(data.get_data(),data.get_size());
        }
        else if (ret == DB_NOTFOUND)
            rec.setError( snmpDatabaseRec::noSuchRec );
        else
            rec.setError( snmpDatabaseRec::unknownError );
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
        rec.setError( snmpDatabaseRec::unknownError );
    }
}

void
snmpDatabase_BDB::_store( snmpDatabaseContext & context,
                          snmpDatabaseRec & rec, int flags )
{
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);

    try {
        int ret;
        Dbt key((void*)rec.getKey(), rec.getKeyLen());
        Dbt data((void*)rec.getData(), rec.getDataLen());
        if( flags == snmpDatabaseObj::insert )
            ret = ctx->db->put(0, &key, &data, DB_NOOVERWRITE);
        else
            ret = ctx->db->put(0, &key, &data, 0);

        if (0 == ret)
            rec.setError( snmpDatabaseRec::noError );
        else if (ret == DB_KEYEXIST)
            rec.setError( snmpDatabaseRec::duplicateRec );
        else
            rec.setError( snmpDatabaseRec::unknownError );
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
        rec.setError( snmpDatabaseRec::unknownError );
    }
}

void
snmpDatabase_BDB::_fetch( snmpDatabaseContext &context,
                          snmpDatabaseRec & rec )
{
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);

    try {
        Dbt key((void*)rec.getKey(), rec.getKeyLen());
        Dbt data;
        int ret = ctx->db->get(0, &key, &data, 0);

        if (0 == ret) {
            rec.setError( snmpDatabaseRec::noError );
            rec.setData(data.get_data(),data.get_size());
        }
        else if (ret == DB_NOTFOUND)
            rec.setError( snmpDatabaseRec::noSuchRec );
        else
            rec.setError( snmpDatabaseRec::unknownError );
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
        rec.setError( snmpDatabaseRec::unknownError );
    }
}

void
snmpDatabase_BDB::_deleterec( snmpDatabaseContext &context,
                              snmpDatabaseRec & recKey,
                              snmpDatabaseRec * recOut )
{
    struct bdb_ctx* ctx = static_cast<struct bdb_ctx*>(context);

    if(recOut) {
        recOut->setKey( (void*)recKey.getKey(), recKey.getKeyLen() );
        _fetch( context, *recOut );
        // if we can't fetch it, we probably can't delete it
        if(recOut->getError() != snmpDatabaseRec::noError)
            return;
    }

    try {
        Dbt key((void*)recKey.getKey(), recKey.getKeyLen());
        int ret = ctx->db->del(0, &key, 0);

        if (0 == ret)
            recKey.setError( snmpDatabaseRec::noError );
        else if (ret == DB_NOTFOUND)
            recKey.setError( snmpDatabaseRec::noSuchRec );
        else
            recKey.setError( snmpDatabaseRec::unknownError );
    }
    catch (DbException &dbe) {
        err( "Database ERROR: " + string(dbe.what()) );
        recKey.setError( snmpDatabaseRec::unknownError );
    }

    if(recOut)
        recOut->setError( recKey.getError() );
}


//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axDbSampleQuery.hpp"
#include "axDbMySqlConnFactory.hpp"
#include "axDbMySqlConnection.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbMySqlQuery.hpp"
#include "axDbDataTypes.hpp"
#include "axDbMySqlResultSet.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
class foo {
public:
  foo() {};
  virtual ~foo() {};
  foo(const foo &) {};
};

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbSampleQuery::axDbSampleQuery()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbSampleQuery::~axDbSampleQuery()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbSampleQuery::axDbSampleQuery()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axDbSampleQuery::queryCmtses(void)
{
  bool ret = true;

  axDbMySqlConnFactory * cf = axDbMySqlConnFactory::getInstance();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * c = h.getConnection();
    assert(c!=NULL);
    axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *> (c);
    assert(mc!=NULL);
    std::string qStr = "select * from cmts";
    MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

    int qRc = mysql_real_query(dbHdl, qStr.c_str(), strlen(qStr.c_str()));
    if (qRc) {
      printf("mysql_real_query() : failed\n");
      ret = false;
      goto EXIT_LABEL;
    }

    MYSQL_RES * resultSet = mysql_store_result(dbHdl);

    int numFields = mysql_num_fields(resultSet);
    MYSQL_ROW row;
    while ((row=mysql_fetch_row(resultSet))) {
      unsigned long * lengths = mysql_fetch_lengths(resultSet);
      // printf("lengths[0]=%u\n", (AX_UINT32) lengths[0]);
      // printf("row[0]=%s,	row[4]=%s\n", (char *) row[0], row[4]);
    }

    mysql_free_result(resultSet);
    resultSet = NULL;
  }


EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSampleQuery::queryCmtses2(void)
{
  bool ret = true;

  axDbMySqlConnFactory * cf = axDbMySqlConnFactory::getInstance();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * c = h.getConnection();
    assert(c!=NULL);
    axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *> (c);
    assert(mc!=NULL);
    std::string qstr = "select * from cmts";
    MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

    axDbMySqlQuery query(mc);
    // DB_CMTS_FIELDS cmtsFields = {0};
    axDbCmts cmts;
    bool qrc = query.execute(qstr.c_str(), cmts);
    if (!qrc) {
      printf("Query failed: \n");
      goto EXIT_LABEL;
    }

    // std::auto_ptr<axSqlResultSet> rs = (axSqlResultSet *) query.getResultSet();
    // std::auto_ptr<axSqlResultSet *> rs = query.getResultSet();
    // std::auto_ptr<foo> fp = new foo();

    // query.storeResultSet();
    // axDbMySqlResultSet * rs = (axDbMySqlResultSet *) query.getResultSet();
    axDbMySqlResultSet * rs = dynamic_cast<axDbMySqlResultSet *> (query.getResultSet());

    int i = 1;
    while (rs->getNext(cmts)) {
      // printf("Got cmts: id-%u,	resId-%u\n", cmts.m_id, cmts.m_cmtsResId);
      i++;
    }

    // not needed
    // delete rs; rs = NULL;
  }


EXIT_LABEL:

  return (ret);
}



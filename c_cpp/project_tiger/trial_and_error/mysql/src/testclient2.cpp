
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <memory.h>
#include <NdbApi.hpp>
#include <iostream>

#define PRINT_ERROR(code,msg) \
  std::cout << "Error in " << __FILE__ << ", line: " << __LINE__ \
            << ", code: " << code \
            << ", msg: " << msg << "." << std::endl
#define APIERROR(error) { \
    PRINT_ERROR(error.code,error.message); \
  }

/*****************************************************************************
 SCHEMA:

CREATE TABLE IF NOT EXISTS int_pk_tbl (
  id int auto_increment primary key,
  name varchar(255) not null
) ENGINE=NDBCLUSTER;

insert into int_pk_tbl(name) values('foo1'),('foo2'),('foo3'),('foo4');

 *****************************************************************************/
 
int main(int argc, char** argv)
{
  const char * dbName = "test";
  const char * table = "int_pk_tbl";
  const char * connectHost = "192.168.1.107";

  ndb_init();

  Ndb_cluster_connection * dbConn = new Ndb_cluster_connection(connectHost);
  if (dbConn->connect(4, 5, 1)) {
    printf("Connection to Mgm Node failed\n");
    exit(-1);
  }
  printf("Connection to Mgm Node succeeded\n");

  if (dbConn->wait_until_ready(30, 0) < 0) {
    printf("Cluster not ready\n");
    exit(-1);
  }
  printf("Cluster ready\n");

  Ndb * myNdb = new Ndb(dbConn, dbName);
  if (myNdb->init()) {
    printf("DB selection failed\n");
    exit(-1);
  }

  printf("Database = %s\n", myNdb->getDatabaseName());
  printf("Schema = %s\n", myNdb->getSchemaName());

  NdbDictionary::Dictionary * myDict = myNdb->getDictionary();
  const NdbDictionary::Table * myTable = myDict->getTable(table);

  if (myTable == NULL) {
    APIERROR(myDict->getNdbError());
    exit(-1);
  }

  struct Ndb::Key_part_ptr key_part[2];
  // struct Key_part_ptr * key_part_ptr;
  memset(key_part, 0, sizeof(key_part));
  int pk = 3;
  int i = 0;
  key_part[i].ptr = &pk;
  key_part[i].len = sizeof(pk);

  NdbTransaction * myTrans = myNdb->startTransaction(myTable, key_part);

  if (myTrans == NULL) {
    APIERROR(myNdb->getNdbError());
    exit(-1);
  }

  NdbOperation * myOp = myTrans->getNdbOperation(myTable);

  if (myOp == NULL) {
    APIERROR(myTrans->getNdbError());
    exit(-1);
  }

  if (myOp->readTuple(NdbOperation::LM_CommittedRead) == -1) {
    APIERROR(myTrans->getNdbError());
    exit(-1);
  }

  myOp->equal("id", pk);

  NdbRecAttr * myCols[1];
  myCols[0] = myOp->getValue("name");

  if (myCols[0] == NULL) {
    APIERROR(myTrans->getNdbError());
    exit(-1);
  }

  if (myTrans->execute(NdbTransaction::NoCommit) != 0) {
    APIERROR(myTrans->getNdbError());
    exit(-1);
  }

  if (myTrans->getNdbError().classification == NdbError::NoDataFound) {
    printf("No data found\n");
  } else {
    printf("name = %s\n", myCols[0]->aRef());
  }

  myNdb->closeTransaction(myTrans);

  ndb_end(0);
  return 0;

}


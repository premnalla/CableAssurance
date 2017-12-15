
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

CREATE TABLE IF NOT EXISTS idb_sdr_index (
  sdr_key varchar(255) not null primary key,
  last_update_date TIMESTAMP NULL DEFAULT NULL,
  location_alg TINYINT ZEROFILL NOT NULL,
  sk1 VARBINARY(255) NULL UNIQUE,
  sk2 VARBINARY(255) NULL UNIQUE,
  sk3 VARBINARY(255) NULL UNIQUE,
  sk4 VARBINARY(255) NULL UNIQUE,
  sk5 VARBINARY(255) NULL UNIQUE,
  KEY last_update_date (last_update_date)
) ENGINE=NDBCLUSTER;

 *****************************************************************************/
 
int main(int argc, char** argv)
{
  const char * dbName = "test";
  const char * table = "idb_sdr_index";
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

  char primaryKey[64];
  strcpy(primaryKey, "cccccccccc");

  NdbTransaction * myTrans = myNdb->startTransaction(myTable, primaryKey, strlen(primaryKey));
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

  char equalsKey[64];
  char * bufLen = &equalsKey[0];
  *((unsigned char *) bufLen) = (unsigned char) strlen(primaryKey);
  strcpy(&equalsKey[1], primaryKey);
  myOp->equal("sdr_key", equalsKey);

  NdbRecAttr * myCols[1];
  myCols[0] = myOp->getValue("location_alg");

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
    printf("location_alg = %d\n", myCols[0]->short_value());
  }

  myNdb->closeTransaction(myTrans);

  ndb_end(0);
  return 0;

}


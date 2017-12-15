

#include <ndb_init.h>
#include <NdbApi.hpp>

#include <iostream>
#include <time.h>
#include <sys/time.h>
#include <fstream>

using namespace std;

#define OH_PK 20
#define PAGESIZE_DM 32768 
#define PAGESIZE_IM 8192
#define PAGESIZE_OH_DM 128 
#define PAGESIZE_OH_IM 0
#define OH_TUPLE_NOTNULL 36
#define OH_TUPLE_NULL 40
#define OH_TUPLE_DISK 40
#define OH_UNIQUE_INDEX 36
#define OH_ORDERED_INDEX 16
#define ALIGNMENT 4


/**
 * prototype for pk_read function
 */

#define uint16 short
#define uint2korr(A)	(uint16) (((uint16) ((unsigned char) (A)[0])) +\
				  ((uint16) ((unsigned char) (A)[1]) << 8))      

int calcVarAttrib(const NdbDictionary::Table * t,
		  const NdbDictionary::Index * ix,
		  int col,
		  Ndb* ndb,
		  bool longvar,
		  bool ftScan);


int calcBlobAttrib(const NdbDictionary::Table * t,
		   const NdbDictionary::Index * ix,
		   int col,
		   Ndb* ndb,
		   int & szRam,
		   int & szDisk,
		   bool ftScan);

int calculateDM(Ndb * ndb, 
		const NdbDictionary::Table * t,
		const NdbDictionary::Index * ix,
		int & bytesRam , 
		int & bytesDisk,
		bool ftScan,
		int & noOfBlobs);


int supersizeme(Ndb * ndb,char * db, char * tbl, bool ftScan);


int select_count(Ndb* pNdb, const NdbDictionary::Table* pTab,
		 int parallelism,
		 int* count_rows,
		 NdbOperation::LockMode lock);



int main(int argc, char ** argv)
{
  ndb_init();
  /**
   * define a connect string to the management server
   */
  if(argc < 4) 
    {
      cout << "usage: ndb_size <connstr> <db>" 
	   << " <table> <--A>" << endl
	   << "<connstr> -- connect string " << endl
	   << "<db> -- database name" << endl
	   << "<table> -- table name" << endl
	   << "Optional:" << endl  
	   << "--A, specifies that all records in the table" << endl
	   << " will be used to calculate average size of var*" << endl
	   << " attributes. By default this is calculated over" << endl
	   << " max 1000 rows in ACC order (random.)" << endl << endl
	   << "--A will also save the number of records in "
	   << " stored in each table to the log file." << endl 
	   << "ndb_record_size will append the following information"
	   << " to a log file called <db>.csv :" << endl << endl
	   << "<db>,<table>,<#ordered indexes>,<#unique indexes>,"
	   << "<record size in ram>,<record size on disk>" << endl
	   << "Please not that the output is appended to the file!"
	   << endl;
      return -1;
    }
  
  

  
  char * connectstring = argv[1];
  char * db  = argv[2];
  char * table = argv[3];
  bool ftScan = false;
  if(argc==5)
    {
      
      if(strcmp(argv[4],"--A")==0)
	 ftScan=true;
      else
	{
	  cout << "invalid flag specified" << endl;
	  return 0;
	}
    }



  /**
   * Create a Ndb_cluster_connection object using the connectstring
   */
  Ndb_cluster_connection * conn = new Ndb_cluster_connection(connectstring);


  /**
   * Connect to the management server
   * try 12 times, wait 5 seconds between each retry,
   * and be verbose (1), if connection attempt failes
   */
  if(conn->connect(12, 5, 1) != 0)
    {
      cout << "Unable to connect to management server." << endl;
      return -1;
    }

  /**
   * Join the cluster
   * wait for 30 seconds for first node to be alive, and 0 seconds
   * for the rest.
   */
  if (conn->wait_until_ready(30,0) <0)
    {
      cout << "Cluster nodes not ready in 30 seconds." << endl;
      return -1;
    }



  /**
   * The first thing we have to do is to instantiate an Ndb object.
   * The Ndb object represents a connection to a database.
   * It is important to note that the Ndb object is not thread safe!!
   * Thus, if it is a multi-threaded application, then typically each thread
   * uses its own Ndb object.
   *
   * Now we create an Ndb object that connects to the test database.
   */

  Ndb * ndb = new Ndb(conn, db);


  if (ndb->init() != 0)
    {
      /**
       * just exit if we can't initialize the Ndb object
       */
      return -1;
    }

  if (supersizeme(ndb, db,  table, ftScan) <0)
    return -1;


  return 0;

}



int waste(const NdbDictionary::Column * c)
{
  
  int rest=0;
  int wst=0;

  if((rest=(c->getSizeInBytes()) %  ALIGNMENT) > 0)
    {
      wst = (ALIGNMENT - rest);
      if(c->getType()==NdbDictionary::Column::Tinyunsigned)
	cout << "WARNING : wasting " << wst 
	     << " bytes due to alignment "
	     << endl;
    }
  return wst;
}



int calculateDM(Ndb * ndb, 
		const NdbDictionary::Table * t,
		const NdbDictionary::Index * ix,
		int & bytesRam , 
		int & bytesDisk,
		bool ftScan,
		int & noOfBlobs)

			    
{
  
  bool isNullable=false;
  int cols = 0;
  int noOfBits=0;
  int sizepk=0;
  bool pkFound=false;
  bytesRam=0;
  bytesDisk=0;
  if (ix==0)
    {
      cols= t->getNoOfColumns();
      for(int i=0; i < t->getNoOfColumns() ; i++)
	{
	  const NdbDictionary::Column *  c = t->getColumn(i);
	  if(c->getPrimaryKey())
	    {		  
	      pkFound=true;
	      //sizepk += (c->getSizeInBytes() + waste(c)); 
	      //cout << "pk = " <<  sizepk << endl << endl;
	    }
	  
	  
	}

    }
  else
    {
      cols= ix->getNoOfColumns();
      /*
       * calculate size of PK if is a unique index
       */

      for(int i=0; i < t->getNoOfColumns() ; i++)
	{
	  const NdbDictionary::Column *  c = t->getColumn(i);
	  if(c->getPrimaryKey())
	    {		  
	      sizepk += (c->getSizeInBytes() + waste(c)); 
	      pkFound=true;
	    }
	  
	}
    
    }
  for(int col=0; col < cols ; col++)
    {
      const NdbDictionary::Column * c=0;
      if( ix ==0)
	c = t->getColumn(col);
      else
	{
	  c = ix->getColumn(col);
	}
      int sz=0;      
      int disk_sz=0;
      const NdbDictionary::Column::Type type=c->getType();

      switch(type)
	{
	case NdbDictionary::Column::Tinyint:
	case NdbDictionary::Column::Tinyunsigned:
	case NdbDictionary::Column::Smallint:
	case NdbDictionary::Column::Smallunsigned:
	case NdbDictionary::Column::Mediumint:
	case NdbDictionary::Column::Mediumunsigned:	  
	  printf("---\tWARNING! tiny/small/mediumint found. Consider replace "
		     "to BIT(%d)!!\n",8*c->getSizeInBytes() );
	  if (c->getStorageType() == NdbDictionary::Column::StorageTypeDisk)
	    disk_sz=(c->getSizeInBytes() + waste(c));
	  else
	    sz = (c->getSizeInBytes() + waste(c));       
	  
	  break;
	case NdbDictionary::Column::Bit:
	  noOfBits+=c->getLength();	  
	  break;
	case NdbDictionary::Column::Varchar:
	case NdbDictionary::Column::Varbinary:
	  if (c->getStorageType() == NdbDictionary::Column::StorageTypeDisk)
	    disk_sz=c->getSizeInBytes();
	  else
	    {
	      sz=calcVarAttrib(t,ix,col,ndb, false,ftScan);
	    }
	  break;
	case NdbDictionary::Column::Longvarchar:
	case NdbDictionary::Column::Longvarbinary:
	  if (c->getStorageType() == NdbDictionary::Column::StorageTypeDisk)
	    disk_sz=c->getSizeInBytes();
	  else
	    sz=calcVarAttrib(t,ix,col,ndb, true, ftScan);
	  break;
	case NdbDictionary::Column::Blob:
	case NdbDictionary::Column::Text:
	  // hack for now..
	  calcBlobAttrib(t,ix,col,ndb, sz, disk_sz, ftScan);       
	  noOfBlobs++;
	  break;
	default:
	  if (c->getStorageType() == NdbDictionary::Column::StorageTypeDisk)
	    disk_sz=c->getSizeInBytes()+ waste(c);       
	  else
	    sz = (c->getSizeInBytes() + waste(c));       
	  break;
	  
	}
  
      bytesRam += sz;
      bytesDisk += disk_sz;
      
      if(c->getNullable())
	isNullable=true;
    }
  
  if(ix == 0)
    {
      if(isNullable)
	bytesRam = bytesRam + OH_TUPLE_NULL;
      else
	bytesRam = bytesRam + OH_TUPLE_NOTNULL;
    }
  else
    {
      bytesRam = bytesRam + sizepk + OH_UNIQUE_INDEX;
    }
  
  int words32=0;
  if(noOfBits>0)
    { 
      //      printf("no of bits = %d\n", noOfBits);
      int overlap=noOfBits - 32;
      if(overlap>0)
	{
	  int bit2bytes = overlap / 8;
	  int rest_bit = overlap % 8;
	  words32=bit2bytes/4;
	  rest_bit = rest_bit + 8* (bit2bytes % 4);      
	  if( rest_bit > 0 )
	    {
	      words32++;
	    }
	}
    }
  
  if(!pkFound)
    {
      cout << "---\tWARNING! No primary key explicitly defined!"
	   << " Hidden PK cost is 8B (BigInt) + 16B for ordered index!" <<endl;
    }
  
  
  bytesRam+=words32*4;
  if(bytesDisk>0)
    bytesDisk+=OH_TUPLE_DISK;
  return 1;
}

			    

int supersizeme(Ndb * ndb,char * db, char * tbl, bool ftScan)
{
  
  int dm_per_rec=0;
  int im_per_rec=0;
  int disk_per_rec=0;
  int noOfOrderedIndexes=0, noOfUniqueHashIndexes=0, noOfBlobs=0;
  int tmpDm=0,tmpIm=0, tmpDisk=0;
  NdbDictionary::Dictionary * dict  = ndb->getDictionary();
  const NdbDictionary::Table * table  = dict->getTable(tbl);
  if(table == 0)
    {
      cout << "table " << tbl << " not found." << endl;
      return -1;
    }

  bool isTable=false;
  
  cout << endl << "Calculating storage cost per record for table " 
       << table->getName() << ":" << endl << endl;
  
  calculateDM( ndb, table, NULL, tmpDm, tmpDisk, ftScan, noOfBlobs );
  tmpIm = OH_PK;
  
  dm_per_rec +=tmpDm;
  disk_per_rec +=tmpDisk;
  im_per_rec +=tmpIm;
  NdbDictionary::Dictionary::List list;
  dict->listIndexes(list, *table);   

  for (unsigned i = 0; i < list.count; i++) 
    {      
      NdbDictionary::Dictionary::List::Element& elt = list.elements[i];
      switch (elt.type) 
      {
      case NdbDictionary::Object::UniqueHashIndex:
	{
	  const NdbDictionary::Index * ix  = 
	    dict->getIndex(elt.name, table->getName());
	  calculateDM(ndb, table, ix, tmpDm, tmpDisk, ftScan, noOfBlobs);
	  tmpIm = OH_PK;
	  cout <<  "---\tWARNING! Unique Index found named (" << elt.name;

	  cout << "): Cost DataMemory per record = " << tmpDm 
	       << " and IndexMemory  = " << tmpIm << endl;
	  dm_per_rec += tmpDm;
	  disk_per_rec += tmpDisk;
	  im_per_rec += tmpIm;
	  isTable = true;
	  noOfUniqueHashIndexes++;
	}
      break;
      case NdbDictionary::Object::OrderedIndex:
	tmpDm = OH_ORDERED_INDEX;
	tmpIm = 0;
	cout <<  "---\tOrdered Index found named (" << elt.name;
	cout << "). Additional cost per record is = " 
	     << tmpDm << " bytes of DataMemory."  << endl;
	dm_per_rec += tmpDm;
	isTable = true;
	noOfOrderedIndexes++;
	break;
      default: 
	break;
      }
    }


  int rows = 0;
  if(ftScan)
    {
      if (select_count(ndb, table, 240, &rows, 
		       NdbOperation::LM_CommittedRead) != 0){
	cout << "counting rows failed" << endl;
	return 0;
      }
    }

  printf("\nRecord size (incl OH):"
	 "\n\t#Rows found=%d records (--A required) "  
	 "\n\t#OrderedIndexes=%d"  
	 "\n\t#UniqueHashIndexes=%d "  
	 "\n\t#blob/text=%d "  
	 "\n\tDataMemory=%d bytes "  
	 "\n\tIndexMemory=%d bytes" 
	 "\n\tDiskspace=%d bytes\n\n",
	 rows,
	 noOfOrderedIndexes,
	 noOfUniqueHashIndexes,
	 noOfBlobs,
	 dm_per_rec, 
	 im_per_rec,
	 disk_per_rec);

  printf("\n\nAppending the following to %s.csv \n",db);
  printf("%s,%s,%d,%d,%d,%d,%d,%d,%d\n\n",
	 db,
	 table->getName(), 
	 rows,
	 noOfOrderedIndexes,
	 noOfUniqueHashIndexes,
	 noOfBlobs,
	 im_per_rec,
	 dm_per_rec,
	 disk_per_rec);


  
  char filename[255];
  sprintf(filename,"%s.csv",db); 
  ofstream myfile (filename,ios::app);
  if (myfile.is_open())
  {
    myfile << db 
	   << "," << table->getName() 
	   << "," << rows 
	   << "," << noOfOrderedIndexes
	   << "," << noOfUniqueHashIndexes
	   << "," << noOfBlobs
	   << "," << im_per_rec 
	   << "," << dm_per_rec 
	   << "," << disk_per_rec 

	 << endl;
    myfile.close();
  }

  return 1;
}

int calcVarAttrib(const NdbDictionary::Table * t,
		  const NdbDictionary::Index * ix,
		  int col,
		  Ndb* ndb,
		  bool longvarchar,
		  bool ftScan)
{
  
  char buff[8052];
  memset(buff, 0, sizeof(buff));
  int sz=0;
  NdbTransaction * trans = ndb->startTransaction();
  NdbScanOperation * sop = trans->getNdbScanOperation(t);
  sop->readTuples();
  sop->getValue(col, buff);
  
  if(trans->execute(NdbTransaction::NoCommit, 
		    NdbOperation::AbortOnError, 1) == -1)
    {
      printf("No data found\n");
    }
  
  int rows=0;
  int check=0;
  while((check = sop->nextResult(true)) == 0)
    {
      
      rows++;
      if(longvarchar) // > 255B
	{	  
	  sz+=(int)uint2korr(buff) +2 ;  //+2 for length. SQL std
	  
	}
      else
	{
	  sz+=(int)buff[0] + 1 ; //+1 for length. SQL std
	}
      if(rows==1000 && !ftScan)
	break;
    }
  trans->close();
  if(rows==0)
    {
      sz = t->getColumn(col)->getSizeInBytes();
      printf("---\tWARNING! No reference data found for VAR*. Defaulting to max size..%d Bytes \n",sz);
      printf("\tConsider loading database with average data for exact"
	     " measurement\n");

      return sz;
    }

  
  printf("---\tVAR* attribute is %d bytes averaged over %d rows\n", 
	     sz/rows, rows);
  return (int)sz/rows;
}




int calcBlobAttrib(const NdbDictionary::Table * t,
		   const NdbDictionary::Index * ix,
		   int col,
		   Ndb* ndb,
		   int & szRam,
		   int & szDisk,
		   bool ftScan)
{

  NdbTransaction * trans = ndb->startTransaction();
  NdbScanOperation * sop = trans->getNdbScanOperation(t);

  sop->readTuples();
  int x=0;
  sop->getValue("a",(char*)&x );
  NdbBlob * blob = sop->getBlobHandle(col);  
  
  if(trans->execute(NdbTransaction::NoCommit, 
		    NdbOperation::AbortOnError, 1) == -1)
    {
      printf("No data found\n");
    }
  
  unsigned long long len=0;


  int rows=0;
  int check=0;
  while((check = sop->nextResult(true)) == 0)
    {
      
      rows++;
      blob->getLength(len);
      if(len<256)
	szRam=(int)len;
      else
	{
	  szRam=256;
	  szDisk=(int)len-256;
	}

      if(rows==1000 && !ftScan)
	break;
    }
  if(rows==0)
    {
      printf("---\tWARNING! No reference data found for BLOB/TEXT. "
	     "Defaulting to 256 bytes! \n");
      printf("\tConsider loading database with average data for exact"
	     " measurement. \n");
      szRam=256;
      szDisk=0;
      return 0;
      
    }
  trans->close();
  printf("---\tBLOB/TEXT attribute is %d bytes (RAM) and %d bytes (DISK)"
	 " averaged over %d rows\n", 	 
	 szRam/rows, 
	 szDisk/rows,
	 rows);

  szRam=szRam/rows; 
  szDisk=szDisk/rows;

  return 0;
}



int 
select_count(Ndb* pNdb, const NdbDictionary::Table* pTab,
	     int parallelism,
	     int* count_rows,
	     NdbOperation::LockMode lock){
  
  int                  retryAttempt = 0;
  const int            retryMax = 100;
  int                  check;
  NdbTransaction       *pTrans;
  NdbScanOperation	       *pOp;

  while (true){

    if (retryAttempt >= retryMax){
      cout << "ERROR: has retried this operation " << retryAttempt 
	   << " times, failing!" << endl;
      return -1;
    }

    pTrans = pNdb->startTransaction();
    if (pTrans == NULL) {
      const NdbError err = pNdb->getNdbError();

      if (err.status == NdbError::TemporaryError){
	usleep(50*1000);
	retryAttempt++;
	continue;
      }
      cout << "Error : " << err.message << endl;
      return -1;
    }
    pOp = pTrans->getNdbScanOperation(pTab->getName());	
    if (pOp == NULL) {
      NdbError err = pTrans->getNdbError();
      cout << "Error : " << err.message << endl;
      pNdb->closeTransaction(pTrans);
      return -1;
    }

    if( pOp->readTuples(NdbScanOperation::LM_Dirty) ) {
      NdbError err = pTrans->getNdbError();
      cout << "Error : " << err.message << endl;
      pNdb->closeTransaction(pTrans);
      return -1;
    }


    check = pOp->interpret_exit_last_row();
    if( check == -1 ) {
      NdbError err = pTrans->getNdbError();
      cout << "Error : " << err.message << endl;
      pNdb->closeTransaction(pTrans);
      return -1;
    }
  
    Uint64 tmp;
    Uint32 row_size;
    pOp->getValue(NdbDictionary::Column::ROW_COUNT, (char*)&tmp);
    pOp->getValue(NdbDictionary::Column::ROW_SIZE, (char*)&row_size);
    check = pTrans->execute(NdbTransaction::NoCommit);
    if( check == -1 ) {
      NdbError err = pTrans->getNdbError();
      cout << "Error : " << err.message << endl;
      pNdb->closeTransaction(pTrans);
      return -1;
    }
    
    Uint64 row_count = 0;
    int eof;
    while((eof = pOp->nextResult(true)) == 0){
      row_count += tmp;
    }
    
    if (eof == -1) {
      const NdbError err = pTrans->getNdbError();
      
      if (err.status == NdbError::TemporaryError){
	pNdb->closeTransaction(pTrans);
	usleep(1000*50);
	retryAttempt++;
	continue;
      }
      cout << "Error : " << err.message << endl;
      pNdb->closeTransaction(pTrans);
      return -1;
    }
    
    pNdb->closeTransaction(pTrans);
    
    if (count_rows != NULL){
      *count_rows = row_count;
    }
    
    return 0;
  }
  return -1;
}

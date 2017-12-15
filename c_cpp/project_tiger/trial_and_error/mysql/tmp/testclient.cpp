
#include <NdbApi.hpp>

#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <fstream.h> // for ifstream 
#include <pthread.h> // for pthread_xxx functions 
#include <time.h> // for localtime
#include <assert.h>
#include <string>
using std::string;

char * databaseName = "SLDB_INDEX";
char * table = "IDB_SLDB_INDEX";
char * index_SDR_KEY = "PRIMARY"; // xxx
char * index_SK1 = "SK1_INDEX";
char * index_SK2 = "SK2_INDEX";
char * index_SK3 = "SK3_INDEX";
char * index_SK4 = "SK4_INDEX";
char * index_SK5 = "SK5_INDEX";
char * index_ACT_DATE = "ACT_DATE_INDEX";
char * index_CRE_DATE = "CRE_DATE_INDEX";
char * index_LUP_DATE = "LUP_DATE_INDEX";




char * index_MIN = "idx_min";
char * index_SIP_URI = "idx_sip_uri";
char * index_EMAIL_ADDRESS = "idx_email_addr";
char * index_IM_HANDLE = "idx_im_handle";


/*
create INDEX ACT_DATE_INDEX on test.IDB_SLDB_INDEX(ACTIVATION_DATE);
create INDEX CRE_DATE_INDEX on test.IDB_SLDB_INDEX(CREATE_DATE);
create INDEX LUP_DATE_INDEX on test.IDB_SLDB_INDEX(LAST_UPDATE_DATE);
create INDEX SK1_INDEX on test.IDB_SLDB_INDEX(SK1);
create INDEX SK2_INDEX on test.IDB_SLDB_INDEX(SK2);
create INDEX SK3_INDEX on test.IDB_SLDB_INDEX(SK3);
create INDEX SK4_INDEX on test.IDB_SLDB_INDEX(SK4);
create INDEX SK5_INDEX on test.IDB_SLDB_INDEX(SK5);
PRIMAY INDEX on SDR_KEY
*/

//all keys are VARCHAR(255) SDR_KEY,SK1,SK2,SK3,SK4,SK5
#define KEY_SIZE 255


#define MDN_SIZE 15
#define MIN_SIZE 15
#define SIP_URI_SIZE 64
#define EMAIL_ADDRESS_SIZE 64
#define IM_HANDLE_SIZE 64
#define MAX_FIELD_SIZE 64

char SPACES[MAX_FIELD_SIZE + 1];
#define SLEE_CLUSTER_ID_DATA "AA"
#define SLEE_HOST_ID_DATA "AA"
#define SIMDB_CLUSTER_ID_DATA "AA"
#define SIMDB_HOST_ID_DATA "AA"

//one more than actual max number of threads
#define MAX_THREADS 101


// Object representing the cluster
Ndb_cluster_connection *cluster_connection[MAX_THREADS];
Ndb * myNdb[MAX_THREADS];
NdbDictionary::Dictionary * myDict[MAX_THREADS];
const NdbDictionary::Table * myTable[MAX_THREADS];
//new indexes
const NdbDictionary::Index * myIndex_SDR_KEY[MAX_THREADS];
const NdbDictionary::Index * myIndex_SK1[MAX_THREADS];
const NdbDictionary::Index * myIndex_SK2[MAX_THREADS];
const NdbDictionary::Index * myIndex_SK3[MAX_THREADS];
const NdbDictionary::Index * myIndex_SK4[MAX_THREADS];
const NdbDictionary::Index * myIndex_SK5[MAX_THREADS];
const NdbDictionary::Index * myIndex_ACT_DATE[MAX_THREADS];
const NdbDictionary::Index * myIndex_LUP_DATE[MAX_THREADS];
const NdbDictionary::Index * myIndex_CRE_DATE[MAX_THREADS];

//old indexes
const NdbDictionary::Index * myIndex_MIN[MAX_THREADS];
const NdbDictionary::Index * myIndex_SIP_URI[MAX_THREADS];
const NdbDictionary::Index * myIndex_EMAIL_ADDRESS[MAX_THREADS];
const NdbDictionary::Index * myIndex_IM_HANDLE[MAX_THREADS];


#define PRINT_ERROR(code,msg) \
  std::cout << "Error in " << __FILE__ << ", line: " << __LINE__ \
            << ", code: " << code \
            << ", msg: " << msg << "." << std::endl
#define APIERROR(error) { \
    PRINT_ERROR(error.code,error.message); \
  }


const int DEFAULT_TEST_DURATION = 1;
const int DEFAULT_THREAD_NUMBER = 1;

#define MAX_KEYS 500001
char keys[MAX_THREADS][MAX_KEYS][11];


bool done = false;
long duration = DEFAULT_TEST_DURATION;
int nodeSelection = -1;
int nthreads = DEFAULT_THREAD_NUMBER;
static pthread_mutex_t LOCK_thread_count = PTHREAD_MUTEX_INITIALIZER;
static pthread_mutex_t LOCK_thread_number = PTHREAD_MUTEX_INITIALIZER;
static pthread_mutex_t LOCK_results = PTHREAD_MUTEX_INITIALIZER;
static int thread_number = 0;

int selectOperation = -1; // 1 to 5 for 5 key columns
int writeOperation = -1; // 0 = all, 1 = insert, 2 = update, 3 = delete
int sleepTime = 0; // default sleepTime in microseconds is 0

// comma separated list of 5 percentage numbers ranging from 0 to 100
string percentagesStr;


// Variables for percentage selects for reading
int primaryKeyPercent = 0;
int nonPrimaryKeyPercent = 0;

// Min and max modulus numbers that can range from 0 to 99 or be -1
int primaryKeyMin = -1;
int primaryKeyMax = -1;
int nonPrimaryKeyMin = -1;
int nonPrimaryKeyMax = -1;


int recsPerCommit = 1; // default
bool doingReadsOnly; // false implies doing writes only
bool stopTestAfterOneKeyPass = false;

char error_data [] = "Garbage data";


// data for sip_uri
char sip_uri_data[10][50] =
{"sip.telcordia.com;protocol=tcp",
 "atlas4.atlas.vonage.net:5061",
 "factsplus.unimessaging.net",
 "udpsip.flintstones.org",
 "proxy01.sipphone.com",
 "biloxi.examples.com",
 "fwd.pulver.com",
 "sip.vonage.net",
 "packet8.net",
 "skype.org"
};

// data for email address
char email_address_data[10][50] =
{
"intel.com",
"netsol.com",
"sprint.com",
"verizon.com",
"telcordia.com",
"research.att.com",
"longdomainnames.com",
"searchenginewatch.com",
"domainnamehandbook.com",
"antidisestablishmentarianism.com"
};

// data for im_handle
char im_handle_data[10][50] =
{
"epop.wiredred.com",
"telcordia.im.com",
"im.research.att.com",
"sametime.ibm.com",
"longdomainnames-im.com",
"hotmail.com",
"searchenginewatch-im.com",
"jabber.org",
"instant-messaging-handle.com",
"yahoo.com"
};

//
// data buckets
// running sum in 1st bucket for all operations, then buckets for latency
// 1st subscript holds the time latency
// 2nd subscript holds running totals, 3rd subscript hold item cnts
// 1st bucket is running total for reads/writes


#if 1 // 1 for normal tests, 0 for full table scan tests
#define NUMBER_BUCKETS 10 // minimum number must be 3, else pgm will core

double readData[NUMBER_BUCKETS][3] =
{
  {0, 0, 0 },
  {500L, 0, 0 }, // 0.5 ms
  {1000L, 0, 0 }, // 1 ms
  {2000L, 0, 0 }, // 2 ms
  {5000L, 0, 0 }, // 5 ms
  {7500L, 0, 0 }, // 7.5 ms
  {10000L, 0, 0 }, // 10 ms
  {20000L, 0, 0 }, // 20 ms
  {50000L, 0, 0 }, // 50 ms
  {0, 0, 0 } // over previous bucket
};

double writeData[NUMBER_BUCKETS][3] =
{
  {0, 0, 0 },
  {500L, 0, 0 }, // 0.5 ms
  {1000L, 0, 0 }, // 1 ms
  {2000L, 0, 0 }, // 2 ms
  {5000L, 0, 0 }, // 5 ms
  {7500L, 0, 0 }, // 7.5 ms
  {10000L, 0, 0 }, // 10 ms
  {20000L, 0, 0 }, // 20 ms
  {50000L, 0, 0 }, // 50 ms
  {0, 0, 0 } // over previous bucket
};
#else // full table scan tests
#define NUMBER_BUCKETS 10 // minimum number must be 3, else pgm will core

double readData[NUMBER_BUCKETS][3] =
{
  {0, 0, 0 },
  {2000000L, 0, 0 }, // 2s
  {4000000L, 0, 0 }, // 4s
  {6000000L, 0, 0 }, // 6s 
  {8000000L, 0, 0 }, // 8s
  {10000000L, 0, 0 }, // 10s
  {12000000L, 0, 0 }, // 12s
  {14000000L, 0, 0 }, // 14s
  {16000000L, 0, 0 }, // 16s
  {0, 0, 0 } // over previous bucket
};

double writeData[NUMBER_BUCKETS][3] =
{
  {0, 0, 0 },
  {50000L, 0, 0 }, // 50 ms
  {100000L, 0, 0 }, // 100 ms
  {200000L, 0, 0 }, // 200 ms
  {500000L, 0, 0 }, // 500 ms
  {750000L, 0, 0 }, // 750 ms
  {1000000L, 0, 0 }, // 1000 ms
  {1500000L, 0, 0 }, // 1500 ms
  {2000000L, 0, 0 }, // 2000 ms
  {0, 0, 0 } // over previous bucket
};
#endif

#define NUM_STMTS 6

// Track number of each type of select done
int readTypes[NUM_STMTS] = { 0, 0, 0, 0, 0, 0};

// Track number of each type of write done
int writeTypes[3] = { 0, 0, 0};


// 1st subscrib holds max value, 2nd subscript holds num ops at that point
// 3rd subscript holds unix time when max hit
unsigned long maxRead[1][3] =
{
  {0, 0, 0}
} ;

unsigned long maxWrite[1][3] =
{
  {0, 0, 0}
} ;

// 1st subscript holds running totals of all fetches
// 2nd subscript holds max fetch value
// 3rd subscript holds the read number when max fetch hit
// NOTE we know number of reads so can compute
// "average number of recs returned" for this query
unsigned long fetchData[1][3] =
{
  {0, 0 ,0}
};

long numKeys;

extern char* optarg;
extern int optind, optopt;


inline void printUsage()
{
  cerr
    << "Usage: testclient -f <keyFile> {-r <A,B,C,D,E>" << endl
    << "-i <IP address or hostname>" << endl
    << "-w [I | D | U | A]} " << endl
    << "[-c <recs_per_commit> (default 1) - only applies if -w]" << endl
    << "[-t <test_duration_secs> (default 1)] " << endl
    << "[-n <num_threads> (default 1)]" << endl
    << "[-s <microseconds sleep time>]" << endl
    << "-r implies do only reads supplying percentages of reads to use" << endl
    << "   each of 2 key fields as follows:" << endl
    << "   A => percent of reads to use MDN key" << endl
    << "   B => percent of reads to use other keys" << endl
    << "   A + B must equal 100" << endl
    << "   If spaces are used in percent string must enclose entire -r arg" << endl
    << "   in double quotes (e.g., -r \"20, 30\")" << endl
    << "-w implies do only writes supplying what kind: " << endl
    << "   I => do only inserts; D => do only deletes; U => do only updates" << endl
    << "   A => do all 3, that is, inserts, updates, deletes" << endl
    << "   -t 0 is mandatory for -w I or -w D as can't repeat keys" << endl
    << "   Key file used for -w should be different from key file used" << endl
    << "   by any other processes doing reads" << endl
    << "-s only applies if -w supplied for writes - default is sleep 0" << endl
    << " -r and -w are mutually exclusive - only one may be supplied" << endl
    << "Note that -t 0 is special and implies stopping test when all keys used once" << endl
    << "[ -o <value for Ndb_cluster_connection::set_optimized_node_selection()]" << endl
    << "[ -p => create only one connection to the DB for entire process - default is 1 per thread]" << endl
    << "Examples:" << endl
    << " Do reads using MDN key 100% using key file called keysfile for 5 minutes with 16 threads: " << endl
    << "   SLDB_client -i 192.4.108.52 -f keysfile -r 100,0 -t300 -n 16" << endl
    << " Do reads using MDN 40%,  other keys 60% each for key file keys1:" << endl
    << "   SLDB_client -i 192.4.108.52 -f keys1 -r 40,60" << endl
    << endl;
  
}

long getTime()
{
  struct timeval tp;
  gettimeofday(&tp, (void*)NULL);
  long t=(long) ((tp.tv_sec)*1000000 + (tp.tv_usec)) ;
  return t;
}

int
getKeys(int threadNumber, const char * fileName)
{
  // Read fileName to gets keys. 
  ifstream file(fileName);
  const unsigned int SZ=128;
  char buff[SZ];

  if (!file) {
    cerr << "ERROR: Cannot open file<" << fileName << ">" << endl;
    return 1;
  }
    
  int numRead = 0;

  while( file.getline(buff,SZ-1)) {
    numRead++;
#ifdef DEBUG
    printf("%d threadnumber  buff (%s)\n",threadNumber,buff);
#endif //DEBUG
    strcpy(keys[threadNumber][numRead-1], buff);
    if(numRead == MAX_KEYS) {
      cerr << "WARNING: Maximum number of allowable keys reached"
           << "\n\tMaximumn number of keys allowed is <" << MAX_KEYS
           << ">" << endl;
      break;
    }
  }
  cout << "Thread " << threadNumber << " finished reading <" << numRead << "> keys from input file <"
       << fileName << ">" << endl;

#ifdef DEBUG
  for(int j=0; j < numRead; j++) {
    // cout << "keys[" << j << "]=<" << keys[j] << ">" << endl;
    // xxx:
    cout << "keys[" << threadNumber << "][" << j << "]=<" << keys[threadNumber][j] << ">" << endl;
  }
#endif // DEBUG
  
  numKeys = numRead;
  return 0;
}

// colType == -1 => mdn, min
// colType == 0 => sip_uri
// colType == 1 => email_address
// colType == 2 => im_handle
char * getData(char * buf, int data_size, char * keyValue, int colType)
{
  char *bufLen = &buf[0];
  // or char *bufLen = buf;
  if (colType == -1) {
    //MDN or MIN
    // snprintf(buf, data_size + 1, "%s%s", keyValue, SPACES);
    // xxx: Updated version for variable length fields:
    *(uchar*) bufLen = strlen(keyValue);
    strcpy(&buf[1], keyValue);
  } else {
    // Get the last 4 digits of keyValue and do a mod 10 on it
    // to get the index into the data array for the particular column
    string keyStr(keyValue);
    string suffixStr = keyStr.substr(6, 4);
    int suffix = atoi(suffixStr.c_str());
    int num = suffix%10;
    char * varD;
    switch(colType) {
    case 0: // sip_uri
      varD = &sip_uri_data[num][0];
      break;
    case 1: // email_address
      varD = &email_address_data[num][0];
      break;
    case 2: // im_handle
      varD = &im_handle_data[num][0];
      break;      
    default:
      cerr << "Illegal colType=" << colType << " received" << endl;
      return error_data;
    } // end switch
    // snprintf(buf, data_size + 1, "%s@%s%s", keyValue, varD, SPACES);
    // xxx: Updated version for variable length fields:
    int len = sprintf(&buf[1], "%s@%s", keyValue, varD);
    *(uchar*) bufLen = len;
  }
  return buf;
}

void
checkFetch(unsigned long fetchCnt)
{
  pthread_mutex_lock(&LOCK_results);

  fetchData[0][0] += fetchCnt;
  if(fetchCnt > fetchData[0][1]) {
    fetchData[0][1] = fetchCnt; // save max
    fetchData[0][2] = ((unsigned long) readData[0][2]) + 1; // save where max occured
  }
#ifdef DEBUG
  cout << "\nfetchCnt= " << fetchCnt << endl;
  cout << "fetchData[0][0](running fetch total) = <" << fetchData[0][0]
       << ">" << endl;
  cout << "fetchData[0][1](max fetch value) = <" << fetchData[0][1]
       << ">" << endl;
  cout << "fetchData[0][2](Num reads where max occurred) = <"
       << fetchData[0][2] << ">" << endl;
#endif
  pthread_mutex_unlock(&LOCK_results);

}


void
checkLatency( long transDuration, bool selectDone)
{
  int i, j;
  struct timeval tp;

  pthread_mutex_lock(&LOCK_results);
  
  if(selectDone) {
    readData[0][1] += transDuration;
    readData[0][2] += 1;

    bool found = false;
    for(i = 1 ; i < NUMBER_BUCKETS-1 ; i++) {
      if(transDuration <= readData[i][0] ) {
        readData[i][1] += transDuration;
        readData[i][2]++;
        found = true;
        break;
      }
    }
    if(!found) {
      // beyond last time bucket
      readData[NUMBER_BUCKETS-1][1] += transDuration;
      readData[NUMBER_BUCKETS-1][2]++;
    }
    if(transDuration > maxRead[0][0]) {
      maxRead[0][0] = transDuration;
      maxRead[0][1] = readData[0][2];
      // Get the time and save it
      gettimeofday(&tp, (void*)NULL);
      maxRead[0][2] = tp.tv_sec;
    }

#ifdef DEBUG
    cout << "\ntransDuration= " << transDuration << ", found="
	 << found << endl;

    for(i = 0; i < NUMBER_BUCKETS ; i++) {
      for ( j = 0; j < 3 ; j++) {
        cout << "readData[" << i << "][" << j << "] = <" << readData[i][j]
             << ">" << endl;
      }
    }
    cout << "maxRead[0][0] = <" << maxRead[0][0] << ">" << endl;
    cout << "maxRead[0][1] = <" << maxRead[0][1] << ">" << endl;
    cout << "maxRead[0][2] = <" << maxRead[0][2] << ">" << endl;
#endif
  }
  else {
    // Must have been write
    writeData[0][1] += transDuration;
    writeData[0][2] += 1;

    bool found = false;
    for(i = 1 ; i < NUMBER_BUCKETS-1 ; i++) {
      if(transDuration <= writeData[i][0] ) {
        writeData[i][1] += transDuration;
        writeData[i][2]++;
        found = true;
        break;
      }
    }
    if(!found) {
      // beyond last time bucket
      writeData[NUMBER_BUCKETS-1][1] += transDuration;
      writeData[NUMBER_BUCKETS-1][2]++;
    }
    if(transDuration > maxWrite[0][0]) {
      maxWrite[0][0] = transDuration;
      maxWrite[0][1] = writeData[0][2];
      // Get the time and save it
      gettimeofday(&tp, (void*)NULL);
      maxWrite[0][2] = tp.tv_sec;
    }
#ifdef DEBUG
    cout << "\ntransDuration= " << transDuration << endl;
    for( i = 0; i < NUMBER_BUCKETS ; i++) {
      for ( j = 0; j < 3; j++) {
        cout << "writeData[" << i << "][" << j << "] = <" << writeData[i][j]
             << ">" << endl;
      }
    }
    cout << "maxWrite[0][0] = <" << maxWrite[0][0] << ">" << endl;
    cout << "maxWrite[0][1] = <" << maxWrite[0][1] << ">" << endl;
    cout << "maxWrite[0][2] = <" << maxWrite[0][2] << ">" << endl;
#endif
  }
  pthread_mutex_unlock(&LOCK_results);

  return;
}


int doSelect(int selectOp, char * keyValue, int me)
{
  
  unsigned long cnt = 0L;

#ifdef DEBUG
  cout << "Doing select with " << selectOp << " and " << keyValue << " me is " << me << endl;
#endif

  NdbTransaction *myTransaction= myNdb[me] -> startTransaction();
  if (myTransaction == NULL) {
    APIERROR(myNdb[me] -> getNdbError());
    return 1;
  }

  char SDR_KEY_buf[KEY_SIZE + 1];
  char SK1_buf[KEY_SIZE + 1];
  char SK2_buf[KEY_SIZE + 1];
  char SK3_buf[KEY_SIZE + 1];
  char SK4_buf[KEY_SIZE + 1];
  char SK5_buf[KEY_SIZE + 1];

  char * keyData;
  char * keyName;
  NdbRecAttr *res1;
  NdbRecAttr *res2;
  NdbRecAttr *res3;
  NdbRecAttr *res4;
  NdbRecAttr *res5;
  NdbRecAttr *res6;
  NdbRecAttr *res7;
  NdbRecAttr *res8;
  NdbRecAttr *res9;
  const NdbDictionary::Index * indx;

  switch(selectOp) {
    case 1: // select using SDR_KEY
      keyData = getData(SDR_KEY_buf, KEY_SIZE, keyValue, -1);
      indx = myIndex_SDR_KEY[me];
      break;
    case 2: // select using SK1
      keyData = getData(SK1_buf, KEY_SIZE, keyValue, -1);
      keyName = "SK1";
      indx = myIndex_SK1[me];
      break;
    case 3: // select using SK2
      keyData = getData(SK2_buf, KEY_SIZE, keyValue, 0);
      keyName = "SK2";
      indx = myIndex_SK2[me];
      break;
    case 4: // select using SK3
      keyData = getData(SK3_buf, KEY_SIZE, keyValue, 1);
      keyName = "SK3";
      indx = myIndex_SK3[me];
      break;
    case 5: // select using SK4
      keyData = getData(SK4_buf, KEY_SIZE, keyValue, 2);
      keyName = "SK4";
      indx = myIndex_SK4[me];
      break;
    case 6: // select using SK5
      keyData = getData(SK5_buf, KEY_SIZE, keyValue, 2);
      keyName = "SK5";
      indx = myIndex_SK5[me];
      break;
    default:
      cerr << "select Operation<" << selectOp << "> is Unknown"
           << "\n\tNo Select done" << endl;
      return 1;
  } // end switch

  switch(selectOp) {
    case 1: // select using MDN
      {
	NdbOperation *myOperation= myTransaction->getNdbOperation(myTable[me]);
	if (myOperation == NULL) {
	  APIERROR(myTransaction->getNdbError());
	  return 1;
	}
	// if (myOperation->readTuple(NdbOperation::LM_Read) != 0)
        // xxxabc experiment?? Using LM_CommittedRead instead of LM_Read
        // provides 49% faster performance:
	if (myOperation->readTuple(NdbOperation::LM_CommittedRead) != 0)
        {
           // xxx
           APIERROR(myTransaction->getNdbError());
           return 1;
        }

	if (myOperation->equal("SDR_KEY", keyData) == -1)
        {
           APIERROR(myTransaction->getNdbError());
           return 1;
        }
	res1 = myOperation->getValue("SDR_KEY", NULL);
	if (res1 == NULL) APIERROR(myTransaction->getNdbError());
	res2 = myOperation->getValue("SK1", NULL);
	if (res2 == NULL) APIERROR(myTransaction->getNdbError());
	res3 = myOperation->getValue("SK2", NULL);
	if (res3 == NULL) APIERROR(myTransaction->getNdbError());
	res4 = myOperation->getValue("SK3", NULL);
	if (res4 == NULL) APIERROR(myTransaction->getNdbError());
	res5 = myOperation->getValue("SK4", NULL);
	if (res5 == NULL) APIERROR(myTransaction->getNdbError());
	res6 = myOperation->getValue("SK5", NULL);
	if (res6 == NULL) APIERROR(myTransaction->getNdbError());
	/* 
	res7 = myOperation->getValue("IM_HANDLE", NULL);
	if (res7 == NULL) APIERROR(myTransaction->getNdbError());
	res8 = myOperation->getValue("EMAIL_ADDRESS", NULL);
	if (res8 == NULL) APIERROR(myTransaction->getNdbError());
	res9 = myOperation->getValue("SIP_URI", NULL);
	if (res9 == NULL) APIERROR(myTransaction->getNdbError());
	*/
     
        // xxx - this is read only, so should intuitively be NoCommit. However,
        // when using NdbOperation::readTuple(LM_Read) and 
        // NdbTransaction::execute(NdbTransaction::NoCommit), performance 
        // went down 29% as compared to using NdbOperation::readTuple(LM_Read) 
        // and NdbTransaction::execute(NdbTransaction::Commit).
	if(myTransaction->execute(NdbTransaction::NoCommit) != 0){
	  APIERROR(myTransaction->getNdbError());
	}
        else
        {
           // xxx - Verify that the record was actually found.
           if (myTransaction->getNdbError().classification == NdbError::NoDataFound)
           {
              cerr << "ERROR. Thread " << me << " could not find record <" << keyValue << ">." << endl;
           }
           else
           {
#ifdef DEBUG
              /*
	      // display some columns
	      cout << "mdn=" << res1 -> aRef() << endl;
	      cout << "slee_cluster_id=" << res2 -> aRef() << endl;
	      cout << "slee_host_id=" << res3 -> aRef() << endl;
              */
              // xxx:
              int rc = res2->isNULL();
              char *valP = res2->aRef();
              if ( rc == -1)
              {
                 cout << "ERROR. Could not get SK1." << endl;
              }
              else if (rc == 1)
              {
                 cout << "SK1 is NULL. Value is: " << valP << endl;
              }
              else if (rc == 0)
              {
                 uchar len = *(uchar*) valP;
                 char val[256];
                 int i;
                 for (i = 0; i <= len; i++)
                 {
                    val[i] = valP[i+1];
                 }
                 val[i] = '\0';
                 cout << "Thread " << me << " found record. SK1 value is: <" << val << ">." << endl; 
              }
#endif // DEBUG
           }
        }
	cnt++;

      }
      break;
    case 2: // select using MIN
    case 3: // select using sip_uri
    case 4: // select using email_address
    case 5: // select using im_handle
      {
	NdbIndexScanOperation *myIOperation= myTransaction->getNdbIndexScanOperation(indx);
	if (myIOperation == NULL) {
	  APIERROR(myTransaction->getNdbError());
	  return 1;
	}
        // xxx
	myIOperation->readTuples(NdbOperation::LM_Read);
	myIOperation->setBound(keyName, NdbIndexScanOperation::BoundEQ, keyData);
	res1 = myIOperation->getValue("SDR_KEY", NULL);
	if (res1 == NULL) APIERROR(myTransaction->getNdbError());
	res2 = myIOperation->getValue("SK1", NULL);
	if (res2 == NULL) APIERROR(myTransaction->getNdbError());
	res3 = myIOperation->getValue("SK2", NULL);
	if (res3 == NULL) APIERROR(myTransaction->getNdbError());
	res4 = myIOperation->getValue("SK3", NULL);
	if (res4 == NULL) APIERROR(myTransaction->getNdbError());
	res5 = myIOperation->getValue("SK4", NULL);
	if (res5 == NULL) APIERROR(myTransaction->getNdbError());
	res6 = myIOperation->getValue("SK5", NULL);
	if (res6 == NULL) APIERROR(myTransaction->getNdbError());
	/*
	res7 = myIOperation->getValue("IM_HANDLE", NULL);
	if (res7 == NULL) APIERROR(myTransaction->getNdbError());
	res8 = myIOperation->getValue("EMAIL_ADDRESS", NULL);
	if (res8 == NULL) APIERROR(myTransaction->getNdbError());
	res9 = myIOperation->getValue("SIP_URI", NULL);
	if (res9 == NULL) APIERROR(myTransaction->getNdbError());
	*/
        // xxx
	if(myTransaction->execute(NdbTransaction::NoCommit) == -1){
	  APIERROR(myTransaction->getNdbError());
	}
	while (myIOperation -> nextResult() == 0) {
	  cnt++;
#ifdef DEBUG
	  // display some columns
	  cout << "mdn=" << res1 -> aRef() << endl;
	  cout << "slee_cluster_id=" << res2 -> aRef() << endl;
	  cout << "slee_host_id=" << res3 -> aRef() << endl;
#endif // DEBUG
	}

      }
      break;
    default:
      cerr << "select Operation<" << selectOp << "> is Unknown"
           << "\n\tNo Select done" << endl;
      return 1;
  } // end switch
/*  
#ifdef DEBUG
  cout << "Fetched <" << cnt << "> records for select done" << endl;
#endif // DEBUG      
*/

  if(selectOp != 1) {
    // non-primary key column...can have NULLs..save stats
    checkFetch(cnt);
  }

  myNdb[me] -> closeTransaction(myTransaction);

  return 0; //success
}

int doWrite(int writeOp, bool doCommit, char * keyValue, int me)
{


#ifdef DEBUG
  cout << "Write keyValue=<" << keyValue << ">, bool doCommit<"
       << doCommit << ">, writeOp=<" << writeOp << ">" << endl;
#endif // DEBUG

  NdbTransaction *myTransaction= myNdb[me] -> startTransaction();
  if (myTransaction == NULL) {
    APIERROR(myNdb[me] -> getNdbError());
    return 1;
  }


  if(keyValue == NULL) {
    // just do commit
#ifdef DEBUG
    cout << "Just doing commit after <" << writeData[0][2]
         << "> writes" << endl;
#endif // DEBUG   
    if(myTransaction->execute( NdbTransaction::Commit ) == -1){
      APIERROR(myTransaction->getNdbError());
      return 1;
    }
    return 0;
  } // end keyValue was NULL => only do a commit
  
  //  struct timeval tp;
  //  struct timezone tzp;
  //  struct tm *m1;


  char MDN_buf[MDN_SIZE + 1];
  char MIN_buf[MIN_SIZE + 1];
  char SIP_URI_buf[SIP_URI_SIZE + 1];
  char EMAIL_ADDRESS_buf[EMAIL_ADDRESS_SIZE + 1];
  char IM_HANDLE_buf[IM_HANDLE_SIZE + 1];


  NdbOperation *myOperation= myTransaction->getNdbOperation(myTable[me]);
  if (myOperation == NULL) {
    APIERROR(myTransaction->getNdbError());
    return 1;
  }



  switch (writeOp) {
    case 1: // insert
      myOperation -> insertTuple();
      myOperation -> equal("MDN", getData(MDN_buf, MDN_SIZE, keyValue, -1));
      myOperation -> setValue("MIN", getData(MIN_buf, MIN_SIZE, keyValue, -1));
      myOperation -> setValue("SIP_URI", getData(SIP_URI_buf, SIP_URI_SIZE, keyValue, 0));
      myOperation -> setValue("EMAIL_ADDRESS", getData(EMAIL_ADDRESS_buf, EMAIL_ADDRESS_SIZE, keyValue, 1));
      myOperation -> setValue("IM_HANDLE", getData(IM_HANDLE_buf, IM_HANDLE_SIZE, keyValue, 2));
      myOperation -> setValue("SLEE_CLUSTER_ID", SLEE_CLUSTER_ID_DATA);
      myOperation -> setValue("SLEE_HOST_ID", SLEE_HOST_ID_DATA);
      myOperation -> setValue("SIMDB_CLUSTER_ID", SIMDB_CLUSTER_ID_DATA);
      myOperation -> setValue("SIMDB_HOST_ID", SIMDB_HOST_ID_DATA);

      // load current date time into last_updt column
      //gettimeofday(&tp, &tzp);
      //m1 = localtime(&tp.tv_sec);
      //last_updt.year = 1900+m1->tm_year;
      //last_updt.month = m1->tm_mon+1;
      //last_updt.day = m1->tm_mday;
      //last_updt.hour = m1->tm_hour;
      //last_updt.minute = m1->tm_min;
      //last_updt.second = m1->tm_sec;
      //last_updt.fraction = tp.tv_usec * 1000; // this is nanoseconds

#ifdef DEBUG
      cout << "Doing Insert:" << endl;
      cout << "mdn=" << keyValue << endl;
#endif // DEBUG

      break; // end for case for insert
        
    case 2: // update
      myOperation -> updateTuple();
      myOperation -> equal("MDN", getData(MDN_buf, MDN_SIZE, keyValue, -1));
      myOperation -> setValue("SLEE_CLUSTER_ID", SLEE_CLUSTER_ID_DATA);
      myOperation -> setValue("SLEE_HOST_ID", SLEE_HOST_ID_DATA);
      myOperation -> setValue("SIMDB_CLUSTER_ID", SIMDB_CLUSTER_ID_DATA);
      myOperation -> setValue("SIMDB_HOST_ID", SIMDB_HOST_ID_DATA);

      // load current date time into last_updt column
      //gettimeofday(&tp, &tzp);
      //m1 = localtime(&tp.tv_sec);
      //last_updt.year = 1900+m1->tm_year;
      //last_updt.month = m1->tm_mon+1;
      //last_updt.day = m1->tm_mday;
      //last_updt.hour = m1->tm_hour;
      //last_updt.minute = m1->tm_min;
      //last_updt.second = m1->tm_sec;
      //last_updt.fraction = tp.tv_usec * 1000; // this is nanoseconds

#ifdef DEBUG
      cout << "Doing Update :" << endl;
      cout << "mdn=" << keyValue << endl;
#endif // DEBUG      

      break; // end for case for Update
        
    case 3:
      myOperation -> deleteTuple();
      myOperation -> equal("MDN", getData(MDN_buf, MDN_SIZE, keyValue, -1));

#ifdef DEBUG
      cout << "Doing Delete :" << endl;
      cout << "mdn=" << keyValue << endl;
#endif // DEBUG      

      break; // end for case for Delete
    default:
      cerr << "Got illegal writeOp=" << writeOp << " - quitting" << endl;
      return 1;
  } // end switch (writeOp)
    

#ifdef DEBUG
  cout << "Doing Execute for writeOp=" << writeOp << endl;
#endif // DEBUG
      
  // Execute 
  if(myTransaction->execute( doCommit ? NdbTransaction::Commit : NdbTransaction::NoCommit) == -1){
    APIERROR(myTransaction->getNdbError());
    return 1;
  }
  myNdb[me] -> closeTransaction(myTransaction);

  return 0; //success


} // end doWrite


void
displayResults()
{

  char buf[32];
  struct tm *m;
  
  double duration1 = duration;
  duration1 = duration1/1000.0; // convert from milliseconds to seconds
  
  cout << "Threads:  " << nthreads <<  ", TestDurationInSecs: "
       << duration1 << endl;

  if(!doingReadsOnly) {
    string str(writeOperation == 0 ? "Inserts, Updates, Deletes" :
               writeOperation == 1 ? "Inserts only" :
               writeOperation == 2 ? "Updates only" : "Deletes only");
    cout << "RecsPerCommit:  " << recsPerCommit << ", Did " << str.c_str()
         << endl;
    if(sleepTime > 0) {
      cout << "Sleeping " << sleepTime << " micro seconds between each write"
           << endl;
    }
    cout << "\tInserts=" << writeTypes[0] << endl
         << "\tUpdates=" << writeTypes[1] << endl
         << "\tDeletes=" << writeTypes[2] << endl
         << endl;
  }
  
  // cout << "Number of keys used:  " << numKeys << endl;
  // xxx
  cout << "Number of keys used per thread:   " << numKeys << endl;
  if(doingReadsOnly) {
    cout << "Percentage for primaryKey Selects=" << primaryKeyPercent << endl
         << "Percentage for nonPrimaryKey Selects=" << nonPrimaryKeyPercent
         << endl << endl;
    cout << "Overall Num Reads: " << readData[0][2] << endl;
    cout << "\tReads using column mdn=" << readTypes[0] << endl
         << "\tReads using column min=" << readTypes[1] << endl
         << "\tReads using column sip_uri=" << readTypes[2] << endl
         << "\tReads using column email_address=" << readTypes[3] << endl
         << "\tReads using column im_handle=" << readTypes[4] << endl
         << endl;
  }
  cout << "Times are in microseconds" << endl << endl;

  float numTrans;
  
  if(readData[0][2] > 0 ) {

    numTrans = readData[0][2];

    // Get time of max read and convert to user friendly format
    m = localtime((const long *)&maxRead[0][2]);    

    memset(buf,'\0', sizeof(buf));
    sprintf(buf,"%0.2d/%0.2d/%d - %0.2d:%0.2d:%0.2d\n",
            m->tm_mon+1,m->tm_mday,1900+m->tm_year,m->tm_hour,
            m->tm_min,m->tm_sec);
    
    cout << "\tmaxRead: " << maxRead[0][0] << "\n\toccurred at Num Reads: "
         << maxRead[0][1] << "\n\tat time: " << buf << endl;

    cout << "Avg Read Time: " << readData[0][1]/numTrans << endl;

    cout << "Total individual read times: " << readData[0][1] << endl;
    cout << "Total Read Rate/Sec not using duration: "
         << readData[0][2]/(readData[0][1]/1000000.0) << endl;
    cout << "Read Rate/Sec using duration: "
         << readData[0][2]/(duration1) << endl;

    // print fetch stats when appropriate - using indexes that can have NULLS
    switch(selectOperation) {
      case 2:
      case 3:
      case 4:
      case 5:
        cout << "\n\tTotal Records Fetched: " << fetchData[0][0] << endl;
        cout << "\tMax recs fetched for a single query: " << fetchData[0][1]
             << endl;
        cout << "\tMax fetch value occurred at Num Reads: " << fetchData[0][2]
             << endl;
        cout << "\tAverage number of records returned per query: "
             << fetchData[0][0]/numTrans << endl << endl;
    }

    for(int i = 1 ; i < NUMBER_BUCKETS-1 ; i++) {
      cout << "\tNumber reads <= " << readData[i][0] << " usec was:  "
           << readData[i][2] << " (" << (readData[i][2]/numTrans) * 100.0
           << "%)" << endl;
      if(readData[i][2] > 0)
        cout << "\t\tavg of them: " << readData[i][1]/readData[i][2] << endl;
    }

    cout << "\tNum Reads > " << readData[NUMBER_BUCKETS-2][0] << " usec was:  "
         << readData[NUMBER_BUCKETS-1][2] << " ("
         << (readData[NUMBER_BUCKETS-1][2]/numTrans) * 100.0
         << "%)" << endl;
    if(readData[NUMBER_BUCKETS-1][2] > 0)
      cout << "\t\tavg of them: "
           << readData[NUMBER_BUCKETS-1][1]/readData[NUMBER_BUCKETS-1][2]
           << endl;
  }
  
  cout << "\nOverall Num Writes: " << writeData[0][2] << endl;

  if(writeData[0][2] > 0 ) {

    numTrans = writeData[0][2];
    
    // Get time of max write and convert to user friendly format
    m = localtime((const long *)&maxWrite[0][2]);    

    memset(buf,'\0', sizeof(buf));
    sprintf(buf,"%0.2d/%0.2d/%d - %0.2d:%0.2d:%0.2d\n",
            m->tm_mon+1,m->tm_mday,1900+m->tm_year,m->tm_hour,
            m->tm_min,m->tm_sec);

    cout << "\tmaxWrite: " << maxWrite[0][0]
         << "\n\toccurred at Num Writes: "
         << maxWrite[0][1]  << "\n\tat time: " << buf << endl;

    cout << "Avg Write Time: " << writeData[0][1]/numTrans << endl;

    cout << "Total individual write times: " << writeData[0][1] << endl;
    cout << "Total Write Rate/Sec not using duration: "
         << writeData[0][2]/(writeData[0][1]/1000000.0) << endl;
    cout << "Write Rate/Sec using duration: "
         << writeData[0][2]/(duration1) << endl;
        
    for(int i = 1 ; i < NUMBER_BUCKETS-1 ; i++) {
      cout << "\tNumber writes <= " << writeData[i][0] << " usec was:  "
           << writeData[i][2] << " ("
           << (writeData[i][2]/numTrans) * 100.0
           << "%)" << endl;
      if(writeData[i][2] > 0)
        cout << "\t\tavg of them: "
             << writeData[i][1]/writeData[i][2] << endl;
    }

    cout << "\tNum Writes > "
         << writeData[NUMBER_BUCKETS-2][0] << " usec was:  "
         << writeData[NUMBER_BUCKETS-1][2] << " ("
         << (writeData[NUMBER_BUCKETS-1][2]/numTrans) * 100.0
         << "%)" << endl;
    if(writeData[NUMBER_BUCKETS-1][2] > 0)
      cout << "\t\tavg of them: "
           << writeData[NUMBER_BUCKETS-1][1]/writeData[NUMBER_BUCKETS-1][2]
           << endl;
  }

}


void* workThread(void* arg);
void* sigThread(void* arg);


int main(int argc, char** argv)
{
  int rc;
  char fileName[256];
  fileName[0] = '\0';
  char connectHost[32];
  connectHost[0] = '\0';
  int oneConnectionOnly = 0;
  
  memset(SPACES, ' ', MAX_FIELD_SIZE);
  SPACES[MAX_FIELD_SIZE] = '\0';

  int c;
  while((c = getopt(argc, argv, "hf:w:r:t:n:c:s:i:o:p")) != -1)  {
    switch(c)  {
      case 's': // sleep time in microseconds
        sleepTime = atoi(optarg);
        if(sleepTime < 0) {
          cerr << "For -s, " << sleepTime << " is illegal - value (microseconds) must be >= 0" << endl;
          return 1;
        }
        break;
      case 't':
        duration = atoi(optarg);
        if(duration == 0)
          stopTestAfterOneKeyPass = true;          
        break;
      case 'n':
        nthreads = atoi(optarg);
	if (nthreads >= MAX_THREADS) {
	  cerr << "Number of threads must be less than " << MAX_THREADS - 1 << endl;
	  return 1;
	}
        break;
      case 'r':
        // expect 2 comma separated numbers that add up to 100
        percentagesStr.assign(optarg);
        doingReadsOnly = true;
        break;
      case 'w':
        // expect a single upper case character (I or D or U or A)
        if(optarg[0] == 'I') 
          writeOperation = 1;
        else if (optarg[0] == 'U')
          writeOperation = 2;
        else if (optarg[0] == 'D')
          writeOperation = 3;
        else if (optarg[0] == 'A')
          writeOperation = 0;
        else {
          cerr << "For -w, " << optarg[0] << " is Illegal - must supply I, D, U, or A" << endl;
          return 1;
        }
        doingReadsOnly = false;
        break;        
      case 'c':
        recsPerCommit = atoi(optarg);
        if(recsPerCommit <= 0) {
          cerr << "-c value=" << recsPerCommit << " must be positive" << endl;
          return 1;
        }
        break;
      case 'f':
	cerr<<"filename given: " << optarg<<endl;
        strcpy(fileName, optarg);
        break;
      case 'i':
	cerr<<"Hostname  or IP given: " << optarg<<endl;
        strcpy(connectHost, optarg);
        break;
      case 'o':
        // xxx
	cerr<<"optimized node selection: " << optarg<<endl;
        nodeSelection = atoi(optarg);
        if (nodeSelection < -1)
        {
	   cerr<<"ERROR. Invalid value for -o: " << optarg<<endl;
           return 1;
        }
        cout << "nodeSelection (-o) value: " << nodeSelection << endl;
        break;
      case 'p':
        cerr <<"Only one database connection for entire process." << endl;
        oneConnectionOnly = 1;
        break;
      case 'h':
        printUsage();
        return 0;
      case ':':
        cerr << "Option -" << (char) optopt << " requires an argument" << endl;
        return 1;
      default:
        cerr << "Unknown option -" << (char) optopt << endl;
        return 1;
    } // end switch
  } // end while

  // Sanity checks 
  if(!percentagesStr.empty() && writeOperation != -1 ||
     (percentagesStr.empty() && writeOperation == -1)) {
    cerr << "Must provide only one of -r (Do Reads) or -w (Do writes) arguments!"
         << endl;
    return 1;
  }
  if(fileName[0] == '\0') {
    cerr << "Must provide -f argument with valid filename!" << endl;
    return 1;
  }

  if (connectHost[0] == '\0') {
    cerr << "ERROR. Must provide -i argument with a valid IP address or hostname!" 
         << endl;
    return 1;
  }

  // ndb_init must be called first
  ndb_init();
  for (int clusterConnI=0; clusterConnI < nthreads; clusterConnI++) {
    // printf("Attempting to create cluster Connection %d\n",clusterConnI);
    // Need to flush:
    cout << "Attempting to create cluster Connection " << clusterConnI << endl;
    cluster_connection[clusterConnI] = new Ndb_cluster_connection(connectHost);
    // printf("Created cluster Connection %d\n",clusterConnI);
    cout << "Created cluster Connection " << clusterConnI << endl;
    // xxx cluster_connection[clusterConnI] = new Ndb_cluster_connection(connectHost);
    if (nodeSelection != -1)
    {
       cluster_connection[clusterConnI]->set_optimized_node_selection(nodeSelection);
       // API above doesn't have a return value.
    }
    // connect to mysql cluster
    if (cluster_connection[clusterConnI]->connect(4 /* retries               */,
				  5 /* delay between retries */,
				  1 /* verbose               */))
      {
        cerr << "Cluster management server was not ready within 30 secs.\n";
        return 1;
      }
      // if (cluster_connection[clusterConnI]->wait_until_ready(30,0) < 0)
      // Change call for test with 4 ndbd's (2 per server). This didn't help peformance.
      int rc_connect;
      rc_connect = cluster_connection[clusterConnI]->wait_until_ready(30,5);
      if (rc_connect < 0)
      {
        cout << "ERROR. Cluster <" << clusterConnI << "> was not ready within 30 secs. No live nodes detected." << endl;
        exit(-1);
      }
      else if (rc_connect > 0)
      {
        cout << "ERROR. Cluster <" << clusterConnI << "> connected to at least one but not all nodes." << endl;
      }
      else
      {
        cout << "Cluster <" << clusterConnI << "> connected to all nodes." << endl;
      }
      if (oneConnectionOnly)
      {
         cout << "Will use only one database connection for process" << endl;
         break;
      }
   }
  // Object representing the database
    int cluster_connection_index;
    for (int jj = 1; jj <= nthreads; jj++) {
      if (oneConnectionOnly)
      {
         cluster_connection_index = 0;
      }
      else
      {
         cluster_connection_index = jj-1;
      }
   // myNdb[jj] = new Ndb ( cluster_connection[jj-1], databaseName );
      myNdb[jj] = new Ndb ( cluster_connection[cluster_connection_index], databaseName );
      if (myNdb[jj] -> init()) {
	APIERROR(myNdb[jj] -> getNdbError());
	return 1;
      }
      myDict[jj] = myNdb[jj] -> getDictionary();
      myTable[jj] = myDict[jj] -> getTable(table);
      if (myTable[jj] == NULL) {
	APIERROR(myDict[jj] -> getNdbError());
	return 1;
      }
      myIndex_SDR_KEY[jj] = myDict[jj]->getIndex(index_SDR_KEY, table);
      if (myIndex_SDR_KEY[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_SK1[jj] = myDict[jj]->getIndex(index_SK1, table);
      if (myIndex_SK1[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_SK2[jj] = myDict[jj]->getIndex(index_SK2, table);
      if (myIndex_SK2[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_SK3[jj] = myDict[jj]->getIndex(index_SK3, table);
      if (myIndex_SK3[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_SK4[jj] = myDict[jj]->getIndex(index_SK4, table);
      if (myIndex_SK4[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_SK5[jj] = myDict[jj]->getIndex(index_SK5, table);
      if (myIndex_SK5[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_ACT_DATE[jj] = myDict[jj]->getIndex(index_ACT_DATE, table);
      if (myIndex_ACT_DATE[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_CRE_DATE[jj] = myDict[jj]->getIndex(index_CRE_DATE, table);
      if (myIndex_CRE_DATE[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
      myIndex_LUP_DATE[jj] = myDict[jj]->getIndex(index_LUP_DATE, table);
      if (myIndex_LUP_DATE[jj] == NULL) {
	APIERROR(myDict[jj]->getNdbError());
	return 1;
      }
    }

  // Get read percentages if -r
  if(doingReadsOnly) {
    int start, end, percent, numChars, tempEnd;
    start = 0;
    string substrStr;
    for(int i=0; i < 2 ; i++) {
      end = percentagesStr.find_first_of(',', start);
      // get by any white space for starting position
      numChars = percentagesStr.find_first_not_of(" \t", start);
      start = numChars;
      if(end != string::npos) {
        // get rid of any white space after the percent number but
        // before the comma delimiter
        tempEnd = percentagesStr.find_last_not_of(" \t", end-1);
      }
      else {
        // get rid of any white space between the end of the line
        // and this last percent number.
        tempEnd = percentagesStr.find_last_not_of(" \t", end);
        // Make sure we got 2 values
        if(i < 1) {
          cerr << "Less than 2 percentage values specified - try again" << endl;
          return 1;
        }
      }
      numChars = tempEnd - start +1;

      substrStr = percentagesStr.substr(start, numChars);
      percent = atoi(substrStr.c_str());
      start = end+1; // set up for next percent number

      switch (i) {
        case 0:
          primaryKeyPercent = percent;
          break;
        case 1:
          nonPrimaryKeyPercent = percent;
          break;
      } // end switch
    } // end for loop for 2 percentages

    // Make sure they add up to 100
    if(primaryKeyPercent + nonPrimaryKeyPercent != 100) {
      cerr <<
        "Read percentages for different key columns do not add up to 100"
           << endl;
      return 1;
    }
#ifdef DEBUG
    cout << "primaryKeyPercent=" << primaryKeyPercent << endl
         << "nonPrimaryKeyPercent=" << nonPrimaryKeyPercent << endl;      
#endif // DEBUG      

    int currMax = -1;
    // Convert the percentages to min and max modulus numbers
    if(primaryKeyPercent > 0) {
      primaryKeyMin = 0;
      currMax = primaryKeyMax = primaryKeyPercent -1;
    }
    if(nonPrimaryKeyPercent > 0) {
      nonPrimaryKeyMin = currMax+1;
      currMax = nonPrimaryKeyMax = nonPrimaryKeyPercent + nonPrimaryKeyMin -1;
    }
            
#ifdef DEBUG
    cout << "primaryKeyMin=" << primaryKeyMin << ", primaryKeyMax="
         << primaryKeyMax << endl
         << "nonPrimaryKeyMin=" << nonPrimaryKeyMin << ", nonPrimaryKeyMax="
         << nonPrimaryKeyMax << endl      
         << endl;
#endif // DEBUG      


  } // end doingReadsOnly is true
  else {
    // doing writes only
    // Make sure test duration is 0 => no repeat of keys for delete or
    // insert only commands
    if((writeOperation == 1 || writeOperation == 3 ) && duration != 0) {
      cerr
        << "Test Duration (-t) must be 0 (implying keys used once then stop)"
        << endl;
      return 1;
    }
    
  } // end doing writes only

  // Get keys - load keys array 
  for (int keyI=0; keyI <nthreads; keyI++) {
    int tKeyI = keyI;
    std::string fileNameExt(fileName);
    char tCharStr [3];
    if (tKeyI > 9) {
      tKeyI=tKeyI % 10;
    }
    sprintf(tCharStr,"%d",tKeyI);
    fileNameExt.append(tCharStr);
    // printf("Attempting to read keys from %s\n",fileNameExt.c_str());
    cout << "Attempting to read keys from " << fileNameExt.c_str() << endl;
    rc = getKeys(keyI,fileNameExt.c_str());
    if(rc != 0) {
      cerr << "unable to get keys - terminating" << endl;
      return 1;
    }
  }
  
  int threadsDone;
  struct timeval tp;

  // We create one extra thread as a signal handler
  pthread_t* threads = new pthread_t[nthreads+1];

  // Get thread id of this thread
  int thread_id = pthread_self();
  
  // Create signal handling thread
  assert(pthread_create(&threads[nthreads], 0, sigThread,
                        (void *)&thread_id) == 0);

  for(int i = 0; i < nthreads; i++)  {
    // printf("Attempting to start thread <%d>\n",i);
    cout << "Attempting to start thread " << i << endl;
    assert(pthread_create(&threads[i], 0, workThread,
                          (void *)&threadsDone) == 0);
  }
  
  // Date time here
  struct timezone tzp;
  char buf[32];
  gettimeofday(&tp, &tzp);
  struct tm *m = localtime(&tp.tv_sec);
  sprintf(buf,"%0.2d/%0.2d/%d - %0.2d:%0.2d:%0.2d.%0.3d\n",
          m->tm_mon+1,m->tm_mday,1900+m->tm_year,m->tm_hour,
          m->tm_min,m->tm_sec,tp.tv_usec/1000);
  cout << "\nStarting time of test was "<< buf << endl;


  // Only use these if duration is 0
  long testStartTimeSecs, testEndTimeSecs, testStartTimeUsecs,
    testEndTimeUsecs;

  if(duration == 0) {
    testStartTimeSecs = tp.tv_sec;
    testStartTimeUsecs = tp.tv_usec;
    threadsDone = 0;

    while(true) {
      if(threadsDone == nthreads) {
        gettimeofday(&tp, (void*)NULL);
        testEndTimeSecs = tp.tv_sec;
        testEndTimeUsecs = tp.tv_usec;        
        duration = (testEndTimeSecs - testStartTimeSecs)*1000 +
          (testEndTimeUsecs - testStartTimeUsecs)/1000; // in ms
        if(duration < 1)
          duration = 1;
        break;
      }
    }
  }
  else {
    duration = duration*1000; // convert duration to milliseconds
    sleep(duration/1000);
    done = true;
  }
  
  sleep(1); // wait for thread to finish
  // print output
  displayResults();

  ndb_end(0);
  return 0;

}

void * sigThread( void* arg)
{
  sigset_t set;
  int sig;
  int * thread_id = (int *) arg;
  
  sigemptyset(&set);
  sigaddset(&set, SIGINT);
  sigaddset(&set, SIGQUIT);
  sigaddset(&set, SIGTERM);
  sigthreadmask(SIG_BLOCK, &set, NULL);

  while(1) {
    sigwait(&set, &sig);
    switch (sig) {
      case SIGINT:
      case SIGQUIT:
      case SIGTERM:
      default:

        cerr << "Received signal<" << sig << ">, terminating process cleanly"
             << endl;

        done = true; // set this so worker thread(s) will terminate cleanly
        sleep(2); // give some time for threads to cleanly quit
        // If duration was 0, then main thread should exit
        // when all worker threads are done.
        // If duration is not 0, then main thread could be sleeping
        // duration amount of secs.  In this case,
        // if we kill the makin thread we will not call displayResults()
        // to get any results.  So, before killing main thread, lets call
        // displayResults() just to be safe.  No harm in getting the O/P
        // twice. Kill main thread that may be sleeping..if already
        // exited, no harm
        displayResults();
        pthread_kill(*thread_id, 15);
        pthread_exit((void *)-1); // terminate this thread
    } // end switch
  } // end while(1)
}


void* workThread(void* arg)
{
  int me;
  static char Me[] = "workThread";
  
  
  int rc = 0;
  int numRecsWrittenSinceLastCommit = 0;
  int numTrans = 1;
  bool doCommit;
  int writeOp = 0;  // Init this so first op is 1 => insert
  int lastKeyUsedIndex;
  
  pthread_mutex_lock(&LOCK_thread_number);
  thread_number ++;
  me = thread_number;
  pthread_mutex_unlock(&LOCK_thread_number);

  // printf("starting workthread <%d>\n", me);
  cout << "starting workthread " << me << endl;
  if (me < 1 || me > nthreads) {
    cerr << "Thread has number outside or range" << endl;
    goto end;
  }
  
  while(!done)  {

    long startTime, endTime;
    int modNumber;
    bool selectDone;

    for (int i = 0; i < numKeys ; i++) {
      startTime = getTime();
      
      // Determine if we should do a select or write
      if(doingReadsOnly) {
        modNumber = numTrans%100;
        if(modNumber <= primaryKeyMax  && modNumber >= primaryKeyMin)
          selectOperation = 1;
        else if(modNumber <= nonPrimaryKeyMax && modNumber >=
                nonPrimaryKeyMin) {
          // Want to select using a non-primary key
          // Need to do a mod on the key suffix to determine which key
          // column will have data and use that column for select
          // string keyStr(keys[me][i]);
          // xxx:
          string keyStr(keys[me-1][i]);
          string suffixStr = keyStr.substr(6, 4);
          int suffix = atoi(suffixStr.c_str());
          modNumber = suffix%4;
          selectOperation = modNumber + 2;
        }
        else
          selectOperation = -1; // error

        // rc = doSelect(selectOperation, keys[me][i], me);
        // xxx:
        rc = doSelect(selectOperation, keys[me-1][i], me);
        selectDone = true;
      }
      else {
        // Must be doing writes only
        if(writeOperation == 0) {
          // Do the next operation among the Insert, Update, Delete
          writeOp = writeOp +1;
          if(writeOp > 3) {
            writeOp = 1;
          }
        }
        else
          // Only doing inserts or only updates or only deletes
          writeOp = writeOperation;
        
        numRecsWrittenSinceLastCommit++;
        if(numRecsWrittenSinceLastCommit >= recsPerCommit) {
          doCommit = true;
          numRecsWrittenSinceLastCommit = 0;
        }
        else
          doCommit = false;
          
        selectDone = false;

        // writeOp: 1 = insert, 2 = update, 3 = delete
        // rc = doWrite(writeOp, doCommit, keys[me][i], me);
        // xxx
        rc = doWrite(writeOp, doCommit, keys[me-1][i], me);
        // Want to use same key for insert, update and delete if
        // that is the operations we are doing
        if(writeOperation == 0) {
          // Save index of last key used so can do final delete if needed
          lastKeyUsedIndex = i;
          // If insert or update use same key - decrement index
          // If delete let key go to next - don't decrement index
          if(writeOp < 3)
            i--;
        }
        if(sleepTime > 0) {
#ifdef DEBUG
          cout << "sleeping for " << sleepTime << " microseconds" << endl;
#endif // DEBUG      
          usleep(sleepTime); // sleep in microseconds
        }
      } // end else are doing writes

      if( rc != 0  ) {
        cerr << "Error while doing transaction - terminating" << endl;

        displayResults();

        if(stopTestAfterOneKeyPass) {
	  goto end;
	}
      } // end if rc!= 0
      else {
        numTrans++;
      }
      
      endTime = getTime();

      long diff = endTime-startTime;

      checkLatency(diff, selectDone); // Put duration into proper bucket

      long numTrans; 
      // xxx
      float numTrans1;
      double duration;
      char type[32];
      if(selectDone) {
        numTrans = readData[0][2];
        duration = readData[0][1];
        strcpy(type, "Select");
        readTypes[selectOperation-1]++;
      }
      else {
        numTrans = writeData[0][2];
        duration = writeData[0][1];
        strcpy(type, "Write");
        writeTypes[writeOp-1]++;
      }
      
      if ( numTrans != 0 && (numTrans % 50000) == 0 )
      {
        numTrans1 = numTrans;
        cerr << "thread "<<me<< " # of " << type << " trans so far: "
             << numTrans
             << ". Trans per sec so far: "
             << numTrans1/(duration/1000000.0)
             <<  endl;
      }
      
      // See if time duration was reached
      if(done) {
        break; // get out of for loop
      }
    } // end for loop going through keys
    // will repeat using same keys again until test duration secs reached
    // unless duration is 0 in which case we now stop
    if(stopTestAfterOneKeyPass) {
      // yes..see if were doing any writes and if we should do 1 last commit
      if(!doingReadsOnly && numRecsWrittenSinceLastCommit > 0) {
        // will just do commit, no write
        rc = doWrite(writeOp, true, NULL, me);
        numRecsWrittenSinceLastCommit = 0;
      }

      goto end;
    }
  } // end while

  // If doing all 3 writes make sure we do a delete last
  if(!doingReadsOnly && writeOperation == 0 && writeOp != 3) {
    // Last operation was not delete - do it
    numRecsWrittenSinceLastCommit = 0;
    // Always do commit on this last delete
    // rc = doWrite(3, true, keys[me][lastKeyUsedIndex], me);
    // xxx
    rc = doWrite(3, true, keys[me-1][lastKeyUsedIndex], me);
    writeTypes[2]++;
  }
  if(!doingReadsOnly && numRecsWrittenSinceLastCommit > 0) {
    // will just do commit, no write
    rc = doWrite(writeOp, true, NULL, me);
  }

 end:
  pthread_mutex_lock(&LOCK_thread_count);
  int * threadsCompleted;
  threadsCompleted = (int *) arg;
  // bump threadsDone whose address was passed in
  *threadsCompleted = *threadsCompleted + 1;
  pthread_mutex_unlock(&LOCK_thread_count);
  pthread_exit(0);
  return 0;
}

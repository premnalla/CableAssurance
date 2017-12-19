// snmpEngine.C
//

#include "config.h"
#include <iostream>
#include <string>
#include <list>
#include <pthread.h>
#include "debug.H"
#include "snmpEngine.H"
#include "snmpUSMArchRegObj.H"
#include "snmpMPArchRegObj.H"
#include "snmpDispArchRegObj.H"
#include "mib.H"
#include "snmpCRRegisterScalarSet.H"


snmpEngine::snmpEngine()
  : psSnmpEngineId( new OID( ".1.3.6.1.6.3.10.2.1.1.0" ), 
     		    new OctetString(string(SNMP_DEFAULT_ENG_ID)) ),
    // 		    new OctetString("WaaaHooo") ),
    psSnmpEngineBoots( new OID( ".1.3.6.1.6.3.10.2.1.2.0" ), 
		       new Integer32( 0 ) ) // will be updated to 1 in init()
{
    DEBUGCREATE_L(debugObj, "snmpEngine");
    DEBUG9_L(debugObj, " Creating Engine");

  //   OctetString *engID = new OctetString();
  //    engID->fromString(SNMP_DEFAULT_PRE_ENG_ID);
  //    engID->append(SNMP_DEFAULT_ENG_ID);
  //  psSnmpEngineId = PersistentVarBind( new OID( ".1.3.6.1.6.3.10.2.1.1" ), 
  //					engID );

    this->engineInited = false; // we're not 'init' not called yet.
    this->archRegistry = new snmpRegObj();
    engineDefaultCRPort = -1; // don't open by default
    engineDefaultCGPort = -1; // don't open by default
    engineDefaultNRPort = -1; // don't open by default
    engineDefaultNGPort = -1; // don't open by default
    engineCounters = new snmpCounterSet();
    // define the counters we're going to support
    engineCounters->addCounterAt(snmpInPkts);
    engineCounters->addCounterAt(snmpInBadVersions);
    engineCounters->addCounterAt(snmpInASNParseErrs);
    engineCounters->addCounterAt(snmpSilentDrops);

    //    this->libsmiptr = 0;
    init_libsmi();
    DEBUG9_L(debugObj, " after init_libsmi");
    DEBUG9_L(debugObj, " and engine id is '" << psSnmpEngineId << "'");
}

snmpEngine::~snmpEngine() {
  SAFE_DELETE(this->archRegistry);
}

LIBSMI *snmpEngine::libsmiptr = NULL;

void
snmpEngine::init_libsmi() {
    DEBUGCREATE_L(debugObj, "snmpEngine::init_libsmi");
    string mibs="SNMPv2-MIB:SNMP-VIEW-BASED-ACM-MIB:SNMP-USER-BASED-SM-MIB:IPSEC-POLICY-MIB";
    string mibpiece = "";
    unsigned int sz; // XXX: which header is "size_type" defined in???

    if (libsmiptr)
        return;
    
    libsmiptr = new LIBSMI();

    while(1) {
        if ((sz = mibs.find(':')) != string::npos)
            mibpiece = mibs.substr(0,sz);
        else
            mibpiece = mibs;
        
        DEBUG9_L(debugObj, "LOADING MIB:" << mibpiece << "\n");
        if (libsmiptr->loadModule(mibpiece).size() == 0)
            DEBUG9_L(debugObj, " FAILED!\n");

        if ((sz = mibs.find(':')) == string::npos)
            break;

        mibs = mibs.substr(sz+1);
    }
    DEBUG9_L(debugObj, "done loading mibs\n");
} // init_libsmi

void
snmpEngine::init() {
  DEBUGCREATE_L(debugObj, "snmpEngine");
  DEBUG9_L(debugObj, "init");
  // don't allow more than one initialization of the engine
  if (this->engineInited)   return; // we're already inited
  else this->engineInited = true;

  snmpMPArchRegObj       *MPArchRegPtr;
  snmpUSMArchRegObj      *USMArchRegPtr;
  snmpDispArchRegObj     *DispArchRegPtr;
  snmpRegObj             *theReg;

  // set engine start time
  gettimeofday(&engineStartTime, NULL);
  // set engine boots
  const Integer32* oldValue = dynamic_cast<const Integer32*> (psSnmpEngineBoots.get_value());
  Integer32 newValue( int(*oldValue) + 1 );
  psSnmpEngineBoots.set_value( newValue );

  string dispName = SNMP_DISP_ARCH_NAME; // Dispatcher,
  string mpName   = SNMP_MP3_ARCH_NAME;  // Msg Processor, &
  string usmName  = SNMP_USM_ARCH_NAME;  // USM arch. in the registry.

  snmpEngine * tmpEng = this;

  DispArchRegPtr = new snmpDispArchRegObj(tmpEng); // Create the respective 
  MPArchRegPtr   = new snmpMPArchRegObj(tmpEng);   // dispatcher, MP, and USM 
  USMArchRegPtr  = new snmpUSMArchRegObj(tmpEng);  // registration objects.
  
  theReg = this->archRegistry;

  theReg->addNewArchInit( dispName, DispArchRegPtr); // Add arch's to the
  theReg->addNewArchInit( mpName,   MPArchRegPtr);   // registry.
  theReg->addNewArchInit( usmName, USMArchRegPtr);
} // init

void
snmpEngine::shutdown() {
  // This will shutdown any of the three threads that
  // may have been started by init's entry of these
  // three arch objects into the regsitry.
  using std::string;

  std::list<string>  exitList;
  exitList.push_back(string(SNMP_DISP_ARCH_NAME)); // Dispatcher
  exitList.push_back(string(SNMP_USM_ARCH_NAME)); // USM 
  exitList.push_back(string(SNMP_MP3_ARCH_NAME)); // Msg Processor

  snmpRegObj::exitArches(exitList);
} // shutdown

void
snmpEngine::shutdownAll() {
  // This will shutdown all threads 
  // listed in the registry
  snmpRegObj::exitAllArches();
}


void
snmpEngine::set_archRegistry(snmpRegObj *archRegistry) {
  if (this->archRegistry)
    delete this->archRegistry;
  this->archRegistry = archRegistry;
}

snmpRegObj *
snmpEngine::get_archRegistry(bool extract) {
  snmpRegObj *ret = this->archRegistry;
  if (extract)
    this->archRegistry = NULL;
  return ret;
}

void
snmpEngine::set_snmpEngineId(OctetString *snmpEngineId) {
  this->engMutex.lock("snmpEngine:set_snmpEngineId");
  this->psSnmpEngineId.set_value( *snmpEngineId );
  this->engMutex.unlock("snmpEngine:set_snmpEngineId");
  delete snmpEngineId;
}

// probably temporary but wanted for now...
void
snmpEngine::set_snmpEngineId_noUpdate(OctetString *snmpEngineId) {
  DEBUGCREATE_L(debugObj, "snmpEngine");
  this->engMutex.lock("snmpEngine:set_snmpEngineId");
  DEBUG9_L(debugObj, "snmpEngine: setting engine id to '" << string(*snmpEngineId) << "'\n");
  this->psSnmpEngineId.set_value_no_update( *snmpEngineId );
  this->engMutex.unlock("snmpEngine:set_snmpEngineId");
  delete snmpEngineId;
}

const OctetString *
snmpEngine::get_snmpEngineId(void) {
  return dynamic_cast<const OctetString*> (this->psSnmpEngineId.get_value());
}

OctetString 
snmpEngine::get_snmpEngineIdOS(void) {
  const OctetString* ret;
  ret = dynamic_cast<const OctetString*> (this->psSnmpEngineId.get_value());
  return *ret;
}

const Integer32 *
snmpEngine::get_snmpEngineBoots(void) {
  return dynamic_cast<const Integer32*> (this->psSnmpEngineBoots.get_value());
}

bool
snmpEngine::set_engineDefaultCRPort(int port) {
  this->engineDefaultCRPort = port;
  return(true);
}
bool
snmpEngine::set_engineDefaultCGPort(int port) {
  this->engineDefaultCGPort = port;
  return(true);
}
bool
snmpEngine::set_engineDefaultNRPort(int port) {
  this->engineDefaultNRPort = port;
  return(true);
}
bool
snmpEngine::set_engineDefaultNGPort(int port) {
  this->engineDefaultNGPort = port;
  return(true);
}
        
void
snmpEngine::registerMibs() {
    snmpCRRegisterScalarSet *regMsg;
    snmpFIFOObj     *crFIFO = NULL;
    snmpRegObj       theReg;
    DEBUGCREATE_L(debugObj, "snmpEngine");
    
    if ((crFIFO = theReg.findArchFIFO(SNMP_CR_ARCH_NAME)) == NULL) {
        DEBUG9_L(debugObj, "CR not found, not registering our counters.\n");
        return;
    }
    DEBUG9_L(debugObj, "registering our counters.\n");
    regMsg = new snmpCRRegisterScalarSet(engineCounters,
                                         new OID(".1.3.6.1.2.1.11"),
                                         new OctetString(""));
    crFIFO->push(regMsg);
}

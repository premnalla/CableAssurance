#ifndef SNMPENGINE_H
#define SNMPENGINE_H

#include "snmpObj.H"
#include "snmpRegObj.H"
#include "asnDataTypes.H"
#include "threadSafeArray.H"
#include "snmpMutexObj.H"
#include "snmpStats.H"        // SMNP_STATS namespace w/enum SNMP_STATS_LIST 
#include "PersistentVarBind.H"
#include "sys/time.h"
#include "mib.H"
#include "snmpCounterSet.H"

#define SNMP_DEFAULT_NR_PORT 162
#define SNMP_DEFAULT_NG_PORT 0
#define SNMP_DEFAULT_CR_PORT 161
#define SNMP_DEFAULT_CG_PORT 0

#define SNMP_DEFAULT_PRE_ENG_ID "0x800007E5"
#define SNMP_DEFAULT_ENG_ID "localEngineID"

class snmpEngine : public snmpObj {
private:
  bool               engineInited;
  PersistentVarBind  psSnmpEngineId;
  PersistentVarBind  psSnmpEngineBoots;
  snmpRegObj        *archRegistry;
  snmpMutexObj       engMutex;
  static LIBSMI     *libsmiptr;
  snmpCounterSet    *engineCounters;

  // set_snmpEngineId() assigns an OctetString pointer as the
  // value for the EngineId. Any previous pointer is deleted.
  // The snmpEngine class assumes responsibility for the pointer.
  void set_snmpEngineId(OctetString *snmpEngineId);

  int              engineDefaultCRPort;
  int              engineDefaultCGPort;
  int              engineDefaultNRPort;
  int              engineDefaultNGPort;
  
  timeval          engineStartTime;

  friend int main( int, char** );

public:
  // used for setting/retrieving enumerated list of snmp statistics.
  // use set(SNMP_STATS::enumation_name, new_int_value), int get(enum),
  // and increment(enum).
  thread_safe_array<int, SNMP_STATS::SNMPV3_STATS_LAST> stats;

  enum counter_enum {
      // only used/implemented ones are defined here.
      // obsolete ones aren't even listed commented out.
      snmpInPkts            = 1,
      snmpInBadVersions     = 3,
//      snmpInBadCommunityNames = 4,
//      snmpInBadCommunityUsers = 5
      snmpInASNParseErrs    = 6,
//      snmpEnableAuthenTraps = 30,
      snmpSilentDrops       = 31
//      snmpProxyDrops        = 32
  };

  snmpEngine();
  virtual ~snmpEngine();

  static void init_libsmi();
  virtual void init();
  virtual void shutdown();
  virtual void shutdownAll();

  const int     get_engineDefaultCRPort() { return engineDefaultCRPort; };
  bool          set_engineDefaultCRPort(int port);

  const int     get_engineDefaultCGPort() { return engineDefaultCGPort; };
  bool          set_engineDefaultCGPort(int port);

  const int     get_engineDefaultNRPort() { return engineDefaultNRPort; };
  bool          set_engineDefaultNRPort(int port);

  const int     get_engineDefaultNGPort() { return engineDefaultNGPort; };
  bool          set_engineDefaultNGPort(int port);

  const timeval get_engineStartTime()     { return engineStartTime; };

  // set_archRegistry() assigns a snmpRegObj pointer as the
  // value for the archRegistry. Any previous pointer is deleted.
  // The snmpEngine class assumes responsibility for the pointer.
  void set_archRegistry(snmpRegObj *archRegistry);
  snmpRegObj *get_archRegistry(bool extract=false);
  
  // get_snmpEngineId() returns the pointer to the OctetString
  // for this snmpEngine.
  const OctetString *get_snmpEngineId(void);
  OctetString get_snmpEngineIdOS(void);
  void set_snmpEngineId_noUpdate(OctetString *snmpEngineId);

  // get_snmpEngineBoots() returns the pointer to the Integer32
  // for this snmpEngine.
  const Integer32 *get_snmpEngineBoots(void);

  snmpCounterSet *get_engineCounters(void) {
      return engineCounters;
  }

  void
  increment_engineCounter(unsigned int at, unsigned int by = 1) {
      engineCounters->increment(at, by);
  }

  void registerMibs();

  snmpEngine *clone() const {
    cerr << "ERROR:snmpEngine::clone not implemented!" << endl;
    throw;
  }
};


#endif /* SNMPENGINE_H ifndef */

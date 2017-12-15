/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axInternalDsTypes.hpp      
 *
 *************************************************************************
 * Modification History:
 *			
 *************************************************************************
 * Description:
 *
 *************************************************************************
 * Modification History:
 *
 *    12/25/01    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axInternalDsTypes_hpp_
#define _axInternalDsTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include <sys/time.h>
#include <string.h>
#include <pthread.h>
#include "axAll.h"
#include "axCblAssureConstants.hpp"
#include "axReaderWriterLock.hpp"
#include "axMultipleReaderLock.hpp"
#include "axListBase.hpp"
#include "axInternalUtils.hpp"


// #ifdef __cplusplus
// extern "C"
// {
// #endif



/*************************************************************************
 * Forward Declerations
 *************************************************************************/
class axAbstractCollection;
class axAbstractLock;
class axInternalCm;

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/
#define INT_CMTS_NAME_SIZE     80
#define INT_FQDN_SIZE          80
#define INT_IP_ADDRESS_SIZE    40 // MUST be sufficient to capture IPv4 and IPv6 addresses
#define INT_SNMP_COMMUNIT_SIZE 32
#define INT_CHANNEL_NAME_SIZE  32
#define INT_HFC_NAME_SIZE      40


/*
 * IP Address Versions
 */
#define AX_INT_IPADDR_TYPE_NULL                                          0
#define AX_INT_IPADDR_TYPE_IPv4                       AX_IP_ADDR_TYPE_IPv4
#define AX_INT_IPADDR_TYPE_IPv6                       AX_IP_ADDR_TYPE_IPv6

/*
 * End-User Device Types
 */
#define AX_INT_EU_DEVIE_TYPE_UNKNOWN                                 (0x00)
#define AX_INT_EU_DEVIE_TYPE_CM                                      (0x01)
#define AX_INT_EU_DEVIE_TYPE_EMTA                                    (0x02)

/*
 * Channel Types
 */
#define AX_INT_CHANNEL_TYPE_UNKNOWN                                  (0x00)
#define AX_INT_CHANNEL_TYPE_UPSTREAM                                 (0x01)
#define AX_INT_CHANNEL_TYPE_DOWNSTREAM                               (0x02)


/*************************************************************************
 * Global Type definitions
 *************************************************************************/
typedef AX_UINT32 INTDS_RESID_t;
typedef AX_MAC_t INTDS_MAC_t;

struct synchronizedTable_t {
  axAbstractCollection * table;
  axAbstractLock       * lock;

  synchronizedTable_t() : table(NULL), lock(NULL) 
  {
  }

  synchronizedTable_t(axAbstractCollection * c, axAbstractLock *l) : 
    table(c), lock(l) 
  {
  }
};

/****************************** Common *************************************
 ****************************** Common *************************************/

struct axIntKey_t {
};

struct axIntNonkey_t {
};


struct axIntCmCounts_t {
  AX_UINT16  total;
  AX_UINT16  online;

  axIntCmCounts_t() : total(0), online(0) 
  {
  }
  bool operator== (const axIntCmCounts_t & in) const
  {
    if (total == in.total && online == in.online) {
      return (true);
    } else {
      return (false);
    }
  }
  bool operator!= (const axIntCmCounts_t & in) const
  {
    if (total != in.total || online != in.online) {
      return (true);
    } else {
      return (false);
    }
  }
};

struct axIntMtaCounts_t {
  AX_UINT16  total;
  AX_UINT16  available;

  axIntMtaCounts_t() : total(0), available(0)
  {
  }
  bool operator== (const axIntMtaCounts_t & in) const
  {
    if (total == in.total && available == in.available) {
      return (true);
    } else {
      return (false);
    }
  }
  bool operator!= (const axIntMtaCounts_t & in) const
  {
    if (total != in.total || available != in.available) {
      return (true);
    } else {
      return (false);
    }
  }
};

struct axIntCounts_t {
  axIntCmCounts_t cm;
  axIntMtaCounts_t mta;
  AX_UINT32 lastLogTime;

  axIntCounts_t() : lastLogTime(0) 
  {
  }

  bool operator== (const axIntCounts_t & in) const
  {
    if (cm == in.cm && mta == in.mta) {
      return (true);
    } else {
      return (false);
    }
  }

  bool operator!= (const axIntCounts_t & in) const
  {
    if (cm != in.cm || mta != in.mta) {
      return (true);
    } else {
      return (false);
    }
  }

};

/****************************** CMTS *************************************
 ****************************** CMTS *************************************/

struct axIntCmtsKey_t : public axIntKey_t {
  INTDS_RESID_t   resId;

  axIntCmtsKey_t () : resId(0)
  {
  }

  axIntCmtsKey_t (INTDS_RESID_t r) : resId(r)
  {
  }

  axIntCmtsKey_t (const axIntCmtsKey_t & k) : 
    resId(k.resId)
  {
  }

};

struct axIntCmtsNonkey_t : public axIntNonkey_t {
  AX_INT8           fqdn[INT_FQDN_SIZE];
  AX_INT8           ipAddress[INT_IP_ADDRESS_SIZE];
#if 0
  AX_INT8           cmtsReadCommunity[INT_SNMP_COMMUNIT_SIZE];
  AX_INT8           cmReadCommunity[INT_SNMP_COMMUNIT_SIZE];
  AX_INT8           cmWriteCommunity[INT_SNMP_COMMUNIT_SIZE];
  AX_INT8           mtaReadCommunity[INT_SNMP_COMMUNIT_SIZE];
  AX_INT8           mtaWriteCommunity[INT_SNMP_COMMUNIT_SIZE];
#endif
  axIntCounts_t     currentCounts;
  axIntCounts_t     previousCounts;

  AX_UINT8 docsisCapability:4;
  AX_UINT8 ipAddressType:4;
  AX_UINT8 snmpVersion:4;
  AX_UINT8 onlineState:2;
  AX_UINT8 hfcAlarmDetectionInProgress:1;
  AX_UINT8 cmStatusPoll:1;
  //
  AX_UINT8 cmPerfPoll:1;
  AX_UINT8 mtaPoll:1;
  AX_UINT8 mtaPing:1;
  AX_UINT8 isDeleted:1;
  AX_UINT8 isReconciled:1;
  AX_UINT8 unused1:3;
  AX_UINT8 unused2;

  axIntCmtsNonkey_t() : docsisCapability(0), ipAddressType(0), 
    snmpVersion(AX_SNMP_VERSION_2c), onlineState(0), 
    hfcAlarmDetectionInProgress(0), cmStatusPoll(0), cmPerfPoll(0),
    mtaPoll(0), mtaPing(0), isDeleted(0), isReconciled(0)
  {
    fqdn[0] = '\0';
    ipAddress[0] = '\0';
#if 0
    cmtsReadCommunity[0] = '\0';
    cmReadCommunity[0] = '\0';
    cmWriteCommunity[0] = '\0';
    mtaReadCommunity[0] = '\0';
    mtaWriteCommunity[0] = '\0';
#endif
  }
};

struct axInternalCmtsData_t {
  axReaderWriterLock    lock;
  axIntCmtsKey_t        keyPart;
  axIntCmtsNonkey_t   * nonkeyPart;

  axInternalCmtsData_t() : nonkeyPart(NULL)
  {
  }

  axInternalCmtsData_t(axIntCmtsKey_t & k) : 
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalCmtsData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }

};

/**************************** Channel ***********************************
 **************************** Channel ***********************************/
typedef AX_INT8 INTDS_CHNL_NAME_t[INT_CHANNEL_NAME_SIZE];

struct axIntChannelKey_t : public axIntKey_t {
  INTDS_CHNL_NAME_t name;

  axIntChannelKey_t ()
  {
    name[0] = '\0';
  }

  axIntChannelKey_t (INTDS_CHNL_NAME_t m)
  {
    copyIntChannelName(name, m);
  }

  axIntChannelKey_t (const axIntChannelKey_t & k)
  {
    copyIntChannelName(name, k.name);
  }

};

struct axIntChannelNonkey_t : public axIntNonkey_t {
  axListBase        cmList;
  axListBase        mtaList;
  time_t            nextPercentAlmStartTm;
  time_t            nextPowerAlmStartTm;
  INTDS_RESID_t     resId;
  AX_UINT32         channelIndex;
  AX_UINT8          channelType;
  axIntCounts_t     currentCounts;
  axIntCounts_t     previousCounts;

  axIntChannelNonkey_t() : nextPercentAlmStartTm(0), nextPowerAlmStartTm(0),
    resId(0), channelIndex(0), channelType(0)
  {
  }
};

struct axInternalChannelData_t {
  axReaderWriterLock         lock;
  axIntChannelKey_t          keyPart;
  axIntChannelNonkey_t     * nonkeyPart;

  axInternalChannelData_t() : nonkeyPart(NULL)
  {
  }

  axInternalChannelData_t(axIntChannelKey_t & k) : 
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalChannelData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }
};

/**************************** HFC Plant ***********************************
 **************************** HFC Plant ***********************************/
typedef AX_INT8 INTDS_HFC_NAME_t[INT_HFC_NAME_SIZE];

struct axIntHfcKey_t : public axIntKey_t {

  INTDS_HFC_NAME_t name;

  axIntHfcKey_t ()
  {
    name[0] = '\0';
  }

  axIntHfcKey_t (INTDS_HFC_NAME_t m)
  {
    copyIntHfcName(name, m);
  }

  axIntHfcKey_t (const axIntHfcKey_t & k)
  {
    copyIntHfcName(name, k.name);
  }

};

/**
 * NOTE: we don't have HFC status log. Therefore we don't need last-log-time
 *       fields.
 */
struct axIntHfcNonkey_t : public axIntNonkey_t {
  axListBase        cmList;
  axListBase        mtaList;
  // time_t            nextPercentAlmStartTm;
  // time_t            nextPowerAlmStartTm;
  //
  time_t            percentAlarmChangeTime;
  time_t            powerAlarmChangeTime;
  time_t            tcaChangeTime;
  //
  INTDS_RESID_t     resId;
  axIntCounts_t     currentCounts;
  axIntCounts_t     previousCounts;
  //
  AX_UINT32         lastAlarmLogTime; // percent-alarm
  AX_UINT32         lastTcaLogTime; // percent-alarm
  //
  AX_UINT8          percentAlarmChanges;
  AX_UINT8          powerAlarmChanges;
  AX_UINT8          tcaStateChanges;
  AX_UINT8          percentAlarm:1;
  AX_UINT8          powerAlarm:1;
  AX_UINT8          tca:1;
  AX_UINT8          unused1:5;

  axIntHfcNonkey_t() : 
    // nextPercentAlmStartTm((time_t)0), nextPowerAlmStartTm((time_t)0), 
    percentAlarmChangeTime(0), powerAlarmChangeTime(0), 
    tcaChangeTime(0), resId(0), lastAlarmLogTime(0), lastTcaLogTime(0),
    percentAlarmChanges(0), powerAlarmChanges(0), tcaStateChanges(0), 
    percentAlarm(0), powerAlarm(0), tca(0)
  {
  }

};

struct axInternalHfcData_t {
  axReaderWriterLock   lock;
  axIntHfcKey_t        keyPart;
  axIntHfcNonkey_t   * nonkeyPart;

  axInternalHfcData_t() : nonkeyPart(NULL)
  {
  }

  axInternalHfcData_t(axIntHfcKey_t & k) : 
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalHfcData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }
};


/******************************* CM **************************************
 ******************************* CM **************************************/
struct axIntCmKey_t : public axIntKey_t {
  INTDS_MAC_t     mac;

  axIntCmKey_t ()
  {
    mac[0] = '\0';
  }

  axIntCmKey_t (INTDS_MAC_t m)
  {
    copyIntMac(mac, m);
  }

  axIntCmKey_t (const axIntCmKey_t & k)
  {
    copyIntMac(mac, k.mac);
  }

};

struct axIntCmNonkey_t : public axIntNonkey_t {
  INTDS_RESID_t resId;
  time_t        onlineChangeTime;
  time_t        tcaChangeTime;
  AX_UINT32     lastStateLogTime;
  AX_UINT32     lastPerfLogTime;
  AX_UINT32     modemIndex;
  AX_UINT32     upstreamChannelIndex;
  AX_UINT32     downstreamChannelIndex;
  AX_INT8       ipAddress[INT_IP_ADDRESS_SIZE];

  AX_UINT8      docsisState:4;
  AX_UINT8      ipAddressType:4;
  AX_UINT8      euDeviceType:4;
  AX_UINT8      tca:1;
  AX_UINT8      unused1:3;
  AX_UINT16     downstreamSNR;

  AX_UINT16     downstreamPower;
  AX_UINT16     upstreamPower;

  // AX_UINT16     upstreamSNRatCmts;
  // AX_UINT16     upstreamPowerAtCmts;

  AX_UINT32     t1Timeouts;
  AX_UINT32     t2Timeouts;
  AX_UINT32     t3Timeouts;
  AX_UINT32     t4Timeouts;

  AX_UINT16     onlineStateChanges;
  AX_UINT16     tcaStateChanges;

  axIntCmNonkey_t() : 
    resId(0), onlineChangeTime((time_t)0), tcaChangeTime(0), 
    lastStateLogTime(0), lastPerfLogTime(0), modemIndex(0), 
    upstreamChannelIndex(0), downstreamChannelIndex(0), 
    docsisState(0), ipAddressType(0), 
    euDeviceType(AX_INT_EU_DEVIE_TYPE_CM), tca(1), unused1(0), 
    downstreamSNR(0), downstreamPower(0), upstreamPower(0),
    // upstreamSNRatCmts(0), upstreamPowerAtCmts(0),
    t1Timeouts(0), t2Timeouts(0), t3Timeouts(0), t4Timeouts(0),
    onlineStateChanges(0), tcaStateChanges(0)
  {
    ipAddress[0] = '\0';
  }
};

struct axInternalCmData_t {
  axReaderWriterLock    lock;
  axIntCmKey_t          keyPart;
  axIntCmNonkey_t     * nonkeyPart;

  axInternalCmData_t() : nonkeyPart(NULL)
  {
    keyPart.mac[0] = '\0';
  }

  axInternalCmData_t(axIntCmKey_t & k) : 
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalCmData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }
};


/******************************* (e)MTA **************************************
 ******************************* (e)MTA **************************************/
struct axIntGenMtaKey_t : public axIntKey_t {
  INTDS_MAC_t     mac;

  axIntGenMtaKey_t ()
  {
    mac[0] = '\0';
  }

  axIntGenMtaKey_t (INTDS_MAC_t m)
  {
    copyIntMac(mac, m);
  }

  axIntGenMtaKey_t (const axIntGenMtaKey_t & k)
  {
    copyIntMac(mac, k.mac);
  }

};


struct axIntEmtaKey_t : public axIntGenMtaKey_t {
  axIntEmtaKey_t ()
  {
  }

  axIntEmtaKey_t (INTDS_MAC_t m) : axIntGenMtaKey_t(m)
  {
  }

  axIntEmtaKey_t (const axIntEmtaKey_t & k) : axIntGenMtaKey_t(k)
  {
  }

};

struct axIntMtaKey_t : public axIntGenMtaKey_t {
  axIntMtaKey_t ()
  {
  }

  axIntMtaKey_t (INTDS_MAC_t m) : axIntGenMtaKey_t(m)
  {
  }

  axIntMtaKey_t (const axIntMtaKey_t & k) : axIntGenMtaKey_t(k)
  {
  }

};

struct axIntGenMtaNonkey_t : public axIntNonkey_t {
  INTDS_RESID_t  resId;
  time_t         availChangeTime;
  time_t         powerAlarmChangeTime;
  AX_UINT32      lastAvailLogTime;
  AX_INT8        ipAddress[INT_IP_ADDRESS_SIZE];
  AX_UINT8       ipAddressType:4;
  AX_UINT8       provState:4;
  AX_UINT8       pingState:2;
  AX_UINT8       available:1;
  AX_UINT8       justBecameUnavailable:1;
  AX_UINT8       onBatteryAlarm:1;
  AX_UINT8       unused:3;
  AX_UINT16      availChanges;

  axIntGenMtaNonkey_t() : resId(0), availChangeTime(0), 
    powerAlarmChangeTime(0), lastAvailLogTime(0), ipAddressType(0), 
    provState(0), pingState(0), available(0), justBecameUnavailable(0), 
    onBatteryAlarm(0), unused(0), availChanges(0)
  {
    ipAddress[0] = '\0';
  }
};

struct axIntEmtaNonkey_t : public axIntGenMtaNonkey_t {
  axInternalCm * cmPtr;

  axIntEmtaNonkey_t() : cmPtr(NULL)
  {
  }
};

struct axIntMtaNonkey_t : public axIntGenMtaNonkey_t {
};

struct axInternalEmtaData_t {
  axReaderWriterLock      lock;
  axIntEmtaKey_t          keyPart;
  axIntEmtaNonkey_t     * nonkeyPart;

  axInternalEmtaData_t() : nonkeyPart(NULL)
  {
  }

  axInternalEmtaData_t(axIntEmtaKey_t & k) :
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalEmtaData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }
};


struct axInternalMtaData_t {
  axReaderWriterLock      lock;
  axIntMtaKey_t           keyPart;
  axIntMtaNonkey_t      * nonkeyPart;

  axInternalMtaData_t() : nonkeyPart(NULL)
  {
  }

  axInternalMtaData_t(axIntMtaKey_t & k) :
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axInternalMtaData_t()
  {
    if (nonkeyPart) {
      delete nonkeyPart;
    }
  }

  bool hasData(void)
  {
    return (nonkeyPart ? true : false);
  }
};


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/




// #ifdef __cplusplus
// }
// #endif

#endif /* #ifndef _axInternalDsTypes_hpp_ */

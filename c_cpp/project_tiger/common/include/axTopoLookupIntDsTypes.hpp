/*********************************************************************
 * Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axTopoLookupIntDsTypes.hpp      
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

#ifndef _axTopoLookupIntDsTypes_hpp_
#define _axTopoLookupIntDsTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include "axInternalDsTypes.hpp"


/******************************* CM **************************************
 ******************************* CM **************************************/
struct axTopoIntCmKey_t : public axIntKey_t {
  INTDS_MAC_t     mac;

  axTopoIntCmKey_t ()
  {
    mac[0] = '\0';
  }

  axTopoIntCmKey_t (INTDS_MAC_t m)
  {
    copyIntMac(mac, m);
  }

  axTopoIntCmKey_t (const axTopoIntCmKey_t & k)
  {
    copyIntMac(mac, k.mac);
  }

};

struct axTopoIntCmNonkey_t : public axIntNonkey_t {
  INTDS_RESID_t resId;
  AX_UINT16     containerId;
  AX_UINT16     unused1;

  axTopoIntCmNonkey_t() : 
    resId(0), containerId(0), unused1(0)
  {
  }
};

struct axTopoIntCmData_t {
  axReaderWriterLock    lock;
  axTopoIntCmKey_t      keyPart;
  axTopoIntCmNonkey_t * nonkeyPart;

  axTopoIntCmData_t() : nonkeyPart(NULL)
  {
    keyPart.mac[0] = '\0';
  }

  axTopoIntCmData_t(axTopoIntCmKey_t & k) : 
    keyPart(k), nonkeyPart(NULL)
  {
  }

  virtual ~axTopoIntCmData_t()
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


/******************************* Generic MTA **************************************
 ******************************* Generic MTA **************************************/
struct axTopoIntGenMtaKey_t : public axIntKey_t {
  INTDS_MAC_t     mac;

  axTopoIntGenMtaKey_t ()
  {
    mac[0] = '\0';
  }

  axTopoIntGenMtaKey_t (INTDS_MAC_t m)
  {
    copyIntMac(mac, m);
  }

  axTopoIntGenMtaKey_t (const axTopoIntGenMtaKey_t & k)
  {
    copyIntMac(mac, k.mac);
  }

};


struct axTopoIntGenMtaNonkey_t : public axIntNonkey_t {
  INTDS_RESID_t  resId;
  AX_UINT16      containerId;
  AX_UINT16      unused1;

  axTopoIntGenMtaNonkey_t() : resId(0), containerId(0), unused1(0)
  {
  }
};

struct axTopoIntGenMtaData_t {
  axReaderWriterLock        lock;
  axTopoIntGenMtaKey_t      keyPart;
  axTopoIntGenMtaNonkey_t * nonkeyPart;

  axTopoIntGenMtaData_t() : nonkeyPart(NULL)
  {
  }

  axTopoIntGenMtaData_t(axTopoIntGenMtaKey_t & k) :
    keyPart(k), nonkeyPart(NULL)
  {
  }

  axTopoIntGenMtaData_t(INTDS_MAC_t mac) :
    keyPart(mac), nonkeyPart(NULL)
  {
  }

  virtual ~axTopoIntGenMtaData_t()
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


#endif /* #ifndef _axTopoLookupIntDsTypes_hpp_ */

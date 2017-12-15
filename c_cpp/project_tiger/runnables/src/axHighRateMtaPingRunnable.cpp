
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axList.hpp"
#include "axCALog.hpp"
#include "axHighRateMtaPingRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axAbstractIterator.hpp"
#include "axInternalGenMta.hpp"
#include "axPinger.hpp"
#include "axPingDataTypes.hpp"
#include "axIteratorHolder.hpp"
#include "axDbMtaPingStatusLog.hpp"
#include "axInternalUtils.hpp"
#include "axDbEmta.hpp"
#include "axDbMtaPingStatusLog.hpp"
#include "axSocketUtils.hpp"
#include "axIcmpSocketHolder.hpp"
#include "axIcmpTemplateSocket.hpp"
#include "axIcmpSocketFactory.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

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
axHighRateMtaPingRunnable::axHighRateMtaPingRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(HIGHRATE_MTA_PING_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axHighRateMtaPingRunnable::~axHighRateMtaPingRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHighRateMtaPingRunnable::axHighRateMtaPingRunnable(
    axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(HIGHRATE_MTA_PING_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHighRateMtaPingRunnable::run(void)
{
  static const char * myName="MtaPingRblRun:";

  AX_INT32    ret = 0;
  AX_INT8     ipAddress[INT_IP_ADDRESS_SIZE];
  AX_UINT32   addressFamily;
  axList      unknownList;
  pthread_t   tid;

  tid = pthread_self();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry\n",myName));

  axIcmpSocketFactory * sFact = axIcmpSocketFactory::getInstance();

  {
    axReadLockOperator rRunnableLock(getLock());

    axIteratorHolder iH(m_mtaList.createIterator());
    axAbstractIterator * mtaIter = iH.getIterator();

    axIcmpTemplateSocket sockTemplate;

    axInternalGenMta * gMta;
    axIcmpCASocket * sock;

    ACE_DEBUG((LM_MISC_DEBUG, "%s iter begin. tid=%d\n", myName, tid));

    for (gMta=dynamic_cast<axInternalGenMta *> (mtaIter->getFirst());
         gMta;
         gMta=dynamic_cast<axInternalGenMta *> (mtaIter->getNext())) {

      axIntGenMtaNonkey_t * nonkey;

      // get mta ip-addr & community
      {
        axReadLockOperator rMtaLock(gMta->getLock());

        if (!gMta->hasData()) {
          continue;
        }

        /*
         * Don't ping offline eMTA's
         */
        if (gMta->isEmta()) {
          axIntEmtaNonkey_t * eNk = (axIntEmtaNonkey_t *) 
                                                     gMta->getNonKey();
          axInternalCm * intCm = eNk->cmPtr;
          axReadLockOperator cmRlock(intCm->getLock());
          axIntCmNonkey_t * cmNk = (axIntCmNonkey_t *) 
                                                    intCm->getNonKey();
          if (!intCm->hasData() || 
              !axInternalCm::isCmOnline(cmNk->docsisState)) {
            continue;
          }
        }

        nonkey = (axIntGenMtaNonkey_t *) gMta->getNonKey();

        copyIntIpAddress(ipAddress, nonkey->ipAddress);
        /*
         * NOTE: the ipaddress type is obtained by gethostinfo/getaddrinfo
         * functions. It is not in SNMP ip address type form. Therefore,
         * it is not necessary to convert here for MTA's. For CM's it is 
         * a different case altogether. Since we get MIB ipaddrtype, we
         * need to convert.
         */
#if 0
        addressFamily = sockAddressTypeToAddressFamily(
                                                nonkey->ipAddressType);
        sockTemplate.setDomain(addressFamily);
#endif
        addressFamily = nonkey->ipAddressType;
        sockTemplate.setDomain(addressFamily);
      }

      /*
       * Ping MTA, get result, and process result
       *
       */
      {

        /*
         * OLD: Open RAW socket for ICMP echo-request/reply
         * NEW: Instead of opening raw socket here, get an open socket of
         *      the appropriate socket family.
         */

        /*
         * What we can do here is; have an externalizer, then pass the family
         * to it which will give back a generic socket and sock factory. We
         * can then release the socket once we are done with it.
         */

        axIcmpSocketHolder sH(sock=sFact->createSocket(&sockTemplate));
        if (!sock) {
          continue;
        }

        // sock->setFamily(AF_INET or AF_INET6);
        // if (!sock->openSocket()) {
        //   goto EXIT_LABEL;
        // }

        axPinger pinger(sock);
        AX_INT32 pingRc;

        if (!pinger.setIpAddress(addressFamily, ipAddress)) {
          continue;
        }

        pingRc = pinger.ping();

        {
          axWriteLockOperator rMtaLock(gMta->getLock());
          updatePingState(gMta, pingRc);
        }

      }

    } // iterate over all mta's

    ACE_DEBUG((LM_MISC_DEBUG, "%s iter end. tid=%d\n", myName, tid));

    /*
     * TODO
     */

    if (unknownList.size()) {
    }

  }


// EXIT_LABEL:

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit\n",myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateMtaPingRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axHighRateMtaPingRunnable::addSubject(axObject * o)
{
  m_mtaList.add(o);
}


//********************************************************************
// method:
//********************************************************************
bool
axHighRateMtaPingRunnable::updatePingState(axInternalGenMta * intMta,
                                                           int pingRc)
{
  bool ret = false;

  axIntGenMtaNonkey_t * nk;
  axDbEmta dbEmta;
  struct timeval tm;
  INTDS_RESID_t mtaResId;

  if (!intMta->hasData()) {
    goto EXIT_LABEL;
  }

  nk = (axIntGenMtaNonkey_t* ) intMta->getNonKey();

  if (nk->pingState == pingRc) {
    ret = true;
    goto EXIT_LABEL;
  }

  mtaResId = intMta->getResId();

  dbEmta.m_mtaResId = mtaResId;
  if (!dbEmta.getRow()) {
    goto EXIT_LABEL;
  }

  gettimeofday(&tm, NULL);

  /*
   * Keep it nice and simple. First update obvious
   */

  dbEmta.m_pingState = pingRc;
  dbEmta.m_isPingFailure = 
         axInternalGenMta::isPingStateFailure(dbEmta.m_pingState);
  if (dbEmta.updateRow()) {
    nk->pingState = pingRc;
  }

  {
    axDbMtaPingStatusLog pLog(mtaResId, tm.tv_sec, nk);
    pLog.insertRow();
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}



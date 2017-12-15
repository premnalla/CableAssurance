
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include "axCALog.hpp"
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include <net-snmp/library/fd_event_manager.h>
#include "axTFSnmpTrapReceiver.hpp"
#include "axTFMtaTrapObject.hpp"
#include "axTFIncomingTrapQ.hpp"

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
static netsnmp_session * snmptrapd_add_session(netsnmp_transport *);
static int trap_callback(int op, netsnmp_session *session, int reqid, 
                                       netsnmp_pdu *pdu, void *magic);


//********************************************************************
// default constructor:
//********************************************************************
axTFSnmpTrapReceiver::axTFSnmpTrapReceiver() :
  m_exit(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axTFSnmpTrapReceiver::~axTFSnmpTrapReceiver()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTFSnmpTrapReceiver::axTFSnmpTrapReceiver()
// {
// }


//********************************************************************
// method: main loop
//********************************************************************
AX_INT32
axTFSnmpTrapReceiver::run(void)
{
  static const char * myName="axTFSnmpTrapReceiver::run:";

  pthread_t thrId = getWorkerId();

  netsnmp_session * ss = NULL;

  char listenPort[32];

  if (thrId != pthread_self()) {
    ACE_ERROR((LM_ERROR, "ACE_thread_t != pthread_t\n"));
  }

  strcpy(listenPort, "udp:162");

#if 0
  /*
   * initialize the agent library
   */
  init_agent("CATrapForwarder");
#endif

  /*
   * Initialize the world.
   */
  init_snmp("CATrapForwarder");

  SOCK_STARTUP;

  netsnmp_transport * transport = 
                     netsnmp_tdomain_transport(listenPort, 1, "udp");
  if (!transport) {
    goto EXIT_LABEL;
  }

  ss = snmptrapd_add_session(transport);
  if (!ss) {
    goto EXIT_LABEL;
  }

  fd_set readfds;
  fd_set writefds;
  fd_set exceptfds;
  int block;
  int numfds;
  struct timeval timeout;
  struct timeval * tvp;
  int count;

  while(!m_exit) {

    numfds = 0;
    FD_ZERO(&readfds);
    FD_ZERO(&writefds);
    FD_ZERO(&exceptfds);
    block = 0;
    tvp = &timeout;
    timerclear(tvp);
    tvp->tv_sec = 5;
    snmp_select_info(&numfds, &readfds, tvp, &block);
    if (block == 1) {
        /* block without timeout */
        tvp = NULL;
    }
    netsnmp_external_event_info(&numfds, &readfds, &writefds, &exceptfds);
    count = select(numfds, &readfds, &writefds, &exceptfds, tvp);
    if (count > 0) {
        netsnmp_dispatch_external_events(&count, &readfds, &writefds,
                                             &exceptfds);
        /* If there are any more events after external events, then
         * try SNMP events. */
        if (count > 0) {
            snmp_read(&readfds);
        }
    } else {
        switch (count) {
        case 0:
            snmp_timeout();
            break;
        case -1:
            if (errno == EINTR) {
                continue;
            }
            ACE_DEBUG((LM_CRITICAL, "%s select returned %d\n", myName, count));
            // snmp_log_perror("select");
            m_exit = true;
            break;
        default:
            ACE_DEBUG((LM_CRITICAL, "%s select returned %d\n", myName, count));
            // fprintf(stderr, "select returned %d\n", count);
            m_exit = true;
        }
    }

    run_alarms();

  } // while

EXIT_LABEL:

  ACE_DEBUG((LM_CRITICAL, "%s thread %d exitting!", myName, (int)thrId));

  return (0);
}


static int
trap_callback(int op, netsnmp_session *session,
           int reqid, netsnmp_pdu *pdu, void *magic)
{
  int ret = 0;
  // Arris MTA Trap Base: 1.3.6.1.4.1.4491.2.2.3.5
  oid stdTrapOidRoot[] = { 1, 3, 6, 1, 6, 3, 1, 1, 5 };
  oid snmpTrapOid[] = { 1, 3, 6, 1, 6, 3, 1, 1, 4, 1, 0 };
  oid trapOid[MAX_OID_LEN + 2] = { 0 };
  int trapOidLen;
  netsnmp_variable_list * vars;
  // netsnmp_trapd_handler *traph;
  // netsnmp_transport *transport = (netsnmp_transport *) magic;
  // extern netsnmp_trapd_handler *netsnmp_auth_global_traphandlers;
  // extern netsnmp_trapd_handler *netsnmp_pre_global_traphandlers;
  // extern netsnmp_trapd_handler *netsnmp_post_global_traphandlers;

  axTFMtaTrapObject * to;
  axTFIncomingTrapQ * tq;

  switch (op) {

    case NETSNMP_CALLBACK_OP_RECEIVED_MESSAGE:

        /*
         * Drops packets with reception problems
         */
        if (session->s_snmp_errno) {
          /*
           * drop problem packets
           */
          return 1;
        }

        switch (pdu->command) {
          case SNMP_MSG_TRAP:
            break;

          case SNMP_MSG_TRAP2:
          case SNMP_MSG_INFORM:
            vars = pdu->variables;
            if (vars) {
              vars = vars->next_variable;
            }
            if (!vars || snmp_oid_compare (vars->name, vars->name_length,
                snmpTrapOid, OID_LENGTH (snmpTrapOid))) {
                /*
                 * Didn't find it!
                 * Let's look through the full list....
                 */
                for (vars = pdu->variables; vars; vars = vars->next_variable) {
                  if (!snmp_oid_compare(vars->name, vars->name_length,
                       snmpTrapOid, OID_LENGTH (snmpTrapOid))) {
                    break;
                  }
                }
                if (!vars) {
                  /*
                   * Still can't find it!  Give up.
                   */
                  // snmp_log (LOG_ERR, "Cannot find TrapOID in TRAP2 PDU\n");
                  return 1;
                }
            }
            memcpy (trapOid, vars->val.objid, vars->val_len);
            trapOidLen = vars->val_len / sizeof (oid);
            to = new axTFMtaTrapObject();
#if 0
            to.setEventIndex()
            to.setEventLevel()
            to.setEventEnterprise()
            to.setEventId()
            to.setEventText()
            to.setEventMac()
            to.setEventEndpoint()
#endif
            tq = axTFIncomingTrapQ::getInstance();
            tq->add(to);
            break;

          default:
            // LOG error
            break;
        }
      break;

    default:
      // LOG error
      break;
  }

  return (0);
}


static netsnmp_session *
snmptrapd_add_session(netsnmp_transport * t)
{
    netsnmp_session sess, *session = &sess, *rc = NULL;

    snmp_sess_init(session);
    session->peername = SNMP_DEFAULT_PEERNAME;
    session->version = SNMP_DEFAULT_VERSION;
    session->community_len = SNMP_DEFAULT_COMMUNITY_LEN;
    session->retries = SNMP_DEFAULT_RETRIES;
    session->timeout = SNMP_DEFAULT_TIMEOUT;
    session->callback = trap_callback;
    session->callback_magic = (void *) t;
    session->authenticator = NULL;
    sess.isAuthoritative = SNMP_SESS_UNKNOWNAUTH;

    rc = snmp_add(session, t, NULL, NULL);
    if (rc == NULL) {
        // snmp_sess_perror("snmptrapd", session);
        // LOG error
    }
    return rc;
}



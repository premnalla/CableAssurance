#ifndef SNMPCONSTANTS_H
#define SNMPCONSTANTS_H

#include <sys/types.h>  // needed for u_char...
#include "asnDataType.H"

// This file contains definitions for general snmp functions. Both a
//  
// Defined Namespaces:
//
// SNMP_CONSTANTS 
//

/*
 * Definitions for the Simple Network Management Protocol (RFC 1067).
 *
 *
 */
/***********************************************************
	Copyright 1988, 1989 by Carnegie Mellon University

                      All Rights Reserved

Permission to use, copy, modify, and distribute this software and its 
documentation for any purpose and without fee is hereby granted, 
provided that the above copyright notice appear in all copies and that
both that copyright notice and this permission notice appear in 
supporting documentation, and that the name of CMU not be
used in advertising or publicity pertaining to distribution of the
software without specific, written prior permission.  

CMU DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE, INCLUDING
ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO EVENT SHALL
CMU BE LIABLE FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL DAMAGES OR
ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION,
ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS
SOFTWARE.
******************************************************************/


namespace SNMP_CONSTANTS {

  enum TransportDomain
  {
    SNMP_TRANSPORT_DOMAIN_UDP,
    SNMP_TRANSPORT_DOMAIN_TCP
  };

  const string ENG_ID_DISCOVERY_SEC_NAME = "";
  const string ENG_ID_DISCOVERY_ENG_ID   = "";

  /* application for SNMPv3 modular arch. */
  enum SnmpAppType 
  {
    UNKNOWN=0, 
    SNMP_APP_CR, 
    SNMP_APP_CG, 
    SNMP_APP_NR, 
    SNMP_APP_NG, 
    SNMP_APP_PROXY
  };

  /* SNMP versions */
  /* There currently exists the following SNMP versions.
   * (Note that only SNMPv1 is in widespread usage, and this code supports
   *  only SNMPv1, SNMPv2c, and SNMPv3.
   *
   *  SNMPv1 - (full) the original version, defined by RFC 1157
   *  SNMPsec - (historic) the first attempt to add strong security
   *             to SNMPv1, defined by RFCs 1351, 1352, and 1353.
   *  SNMPv2p - (historic) party-based SNMP, which was another
   *             attempt to add strong security to SNMP, defined
   *             by RFCs 1441, 1445, 1446, 1448, and 1449.
   *  SNMPv2c - (experimental) community string-based SNMPv2,
   *             which was an attempt to combine the protocol
   *             operations of SNMPv2 with the security of
   *             SNMPv1, defined by RFCs 1901, 1905, and 1906.
   *  SNMPv2u - (experimental) user-based SNMPv2, which provided
   *             security based on user names and protocol
   *             operations of SNMPv2, defined by RFCs 1905,
   *             1909, and 1910.
   *  SNMPv2* (or SNMPv2star) - (experimental) an attempt to add the
   *             best features of SNMPv2p and SNMPv2u, defined
   *             by unpublished documents found at WEB site
   *             owned by SNMP Research (a leading SNMP vendor)
   *  SNMPv3 - the current attempt by the IETF working group to merge
   *             the SNMPv2u and SNMPv2* proposals into a more widly
   *             accepted SNMPv3.  It is defined by not yet published
   *             documents of the IETF SNMPv3 WG.
   *
   * SNMPv1, SNMPv2c, SNMPv2u, and SNMPv3 messages have a common
   * form, which is an ASN.1 sequence containing a message version 
   * field, followed by version dependent fields.
   * SNMPsec, SNMPv2p, and SNMPv2* messages have a common form,
   * which is a tagged ASN.1 context specific sequence containing
   * message dependent fields.
   *
   * In the #defines for the message versions below, the value
   * for SNMPv1, SNMPv2c, SNMPv2u, and SNMPv3 messages is the
   * value of the message version field. Since SNMPsec, SNMPv2p,
   * and SNMPv2* messages do not have a message version field,
   * the value in the defines for them is choosen to be a large
   * arbitrary number.
   *
   * Note that many of the version ID's are defined below purely for
   * documentational purposes.  At this point the only protocol planned
   * for future implementations is SNMP3, as the other v2 protocols will
   * not be supported by the IETF (ie, v2u, v2sec, v2star) or used by
   * the snmp community at large (at the time of this writing).  */

  /* versions based on version field */
  const unsigned char SNMP_VERSION_1  = 0;
  const unsigned char SNMP_VERSION_2c = 1;
  const unsigned char SNMP_VERSION_2u = 2;
  const unsigned char SNMP_VERSION_3  = 3;

  /* versions not based on a version field */
  const unsigned char SNMP_VERSION_sec   = 128;
  const unsigned char SNMP_VERSION_2p    = 129;
  const unsigned char SNMP_VERSION_2star = 130;


  const unsigned char ASN_CONTEXT     = 0x80;
  const unsigned char ASN_CONSTRUCTOR = 0x20;
  
  // PDU types in SNMPv1, SNMPsec, SNMPv2p, SNMPv2c, SNMPv2u, 
  // SNMPv2*, and SNMPv3
  const unsigned char SNMP_MSG_GET      = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x0);
  const unsigned char SNMP_MSG_GETNEXT  = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x1);
  const unsigned char SNMP_MSG_RESPONSE = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x2);
  const unsigned char SNMP_MSG_SET      = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x3);
  
  /* PDU types in SNMPv1 and SNMPsec */
  const unsigned char SNMP_MSG_TRAP     = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x4);
  
  /* PDU types in SNMPv2p, SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 */
  const unsigned char SNMP_MSG_GETBULK  = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x5);
  const unsigned char SNMP_MSG_INFORM   = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x6);
  const unsigned char SNMP_MSG_TRAP2    = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x7);
  
  /* PDU types in SNMPv2u, SNMPv2*, and SNMPv3 */
  const unsigned char SNMP_MSG_REPORT   = (ASN_CONTEXT | ASN_CONSTRUCTOR | 0x8);
  
  /* Exception values for SNMPv2p, SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 */
  const unsigned char ASN_PRIMITIVE       = (unsigned char)0x00;
  const unsigned char SNMP_NOSUCHOBJECT   = (ASN_CONTEXT | ASN_PRIMITIVE | 0x0);
  const unsigned char SNMP_NOSUCHINSTANCE = (ASN_CONTEXT | ASN_PRIMITIVE | 0x1);
  const unsigned char SNMP_ENDOFMIBVIEW   = (ASN_CONTEXT | ASN_PRIMITIVE | 0x2);
  
  /* Error codes (the value of the field error-status in PDUs) */
  
  /* in SNMPv1, SNMPsec, SNMPv2p, SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 PDUs */
  const unsigned char SNMP_ERR_NOERROR    = 0; /* XXX  Used only for PDUs? */
  const unsigned char SNMP_ERR_TOOBIG     = 1;
  const unsigned char SNMP_ERR_NOSUCHNAME = 2;
  const unsigned char SNMP_ERR_BADVALUE   = 3;
  const unsigned char SNMP_ERR_READONLY   = 4;
  const unsigned char SNMP_ERR_GENERR     = 5;

  /* in SNMPv2p, SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 PDUs */
  const unsigned char SNMP_ERR_NOACCESS            = 6;
  const unsigned char SNMP_ERR_WRONGTYPE           = 7;
  const unsigned char SNMP_ERR_WRONGLENGTH         = 8;
  const unsigned char SNMP_ERR_WRONGENCODING       = 9;
  const unsigned char SNMP_ERR_WRONGVALUE          = 10;
  const unsigned char SNMP_ERR_NOCREATION          = 11;
  const unsigned char SNMP_ERR_INCONSISTENTVALUE   = 12;
  const unsigned char SNMP_ERR_RESOURCEUNAVAILABLE = 13;
  const unsigned char SNMP_ERR_COMMITFAILED        = 14;
  const unsigned char SNMP_ERR_UNDOFAILED          = 15;
  const unsigned char SNMP_ERR_AUTHORIZATIONERROR  = 16;
  const unsigned char SNMP_ERR_NOTWRITABLE         = 17;

  /* in SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 PDUs */
  const unsigned char SNMP_ERR_INCONSISTENTNAME    = 18;
  
  const string SNMP_ERR_STRING_ARRAY[19] = 
    {"No Error", 
     "Too Big",
     "No Such Name",
     "Bad Value",
     "Read Only",
     "General Error",
     /* in SNMPv2p, SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 PDUs */
     "No Access",
     "Wrong Type",
     "Wrong Length",
     "Wrong Encoding",
     "Wrong Value",
     "No Creation",
     "Inconsistent Value",
     "Resource Unavailable",
     "Commit Failed",
     "Undo Failed",
     "Authorization Error",
     "Not Writable",
     /* in SNMPv2c, SNMPv2u, SNMPv2*, and SNMPv3 PDUs */
     "INCONSISTENTNAME"};
  
  const unsigned char MAX_SNMP_ERR                 = 18;
  
  /* values of the generic-trap field in trap PDUs */
  const unsigned char SNMP_TRAP_COLDSTART          = 0;
  const unsigned char SNMP_TRAP_WARMSTART          = 1;
  const unsigned char SNMP_TRAP_LINKDOWN           = 2;
  const unsigned char SNMP_TRAP_LINKUP             = 3;
  const unsigned char SNMP_TRAP_AUTHFAIL           = 4;
  const unsigned char SNMP_TRAP_EGPNEIGHBORLOSS    = 5;
  const unsigned char SNMP_TRAP_ENTERPRISESPECIFIC = 6;
  
  /* row status values */
  const unsigned char SNMP_ROW_NONEXISTENT   = 0;
  const unsigned char SNMP_ROW_ACTIVE        = 1;
  const unsigned char SNMP_ROW_NOTINSERVICE  = 2;
  const unsigned char SNMP_ROW_NOTREADY      = 3;
  const unsigned char SNMP_ROW_CREATEANDGO   = 4;
  const unsigned char SNMP_ROW_CREATEANDWAIT = 5;
  const unsigned char SNMP_ROW_DESTROY       = 6;

  /* row storage values */
  const unsigned char SNMP_STORAGE_OTHER       = 1;
  const unsigned char SNMP_STORAGE_VOLATILE    = 2;
  const unsigned char SNMP_STORAGE_NONVOLATILE = 3;
  const unsigned char SNMP_STORAGE_PERMANENT   = 4;
  const unsigned char SNMP_STORAGE_READONLY    = 5;
  
  /* message processing models */
  const unsigned char SNMP_MP_MODEL_SNMPv1  = 0;
  const unsigned char SNMP_MP_MODEL_SNMPv2c = 1;
  const unsigned char SNMP_MP_MODEL_SNMPv2u = 2;
  const unsigned char SNMP_MP_MODEL_SNMPv3  = 3;
  const int    SNMP_MP_MODEL_SNMPv2p = 256;
  
  /* security values */
  const unsigned char SNMP_SEC_MODEL_ANY     = 0;
  const unsigned char SNMP_SEC_MODEL_SNMPv1  = 1;
  const unsigned char SNMP_SEC_MODEL_SNMPv2c = 2;
  const unsigned char SNMP_SEC_MODEL_USM     = 3;
  const int    SNMP_SEC_MODEL_SNMPv2p = 256;

  const unsigned char SNMP_SEC_LEVEL_NOAUTH     = 0;
  const unsigned char SNMP_SEC_LEVEL_AUTHNOPRIV = 1;
  const unsigned char SNMP_SEC_LEVEL_AUTHPRIV   = 3;
  
  const unsigned char SNMP_MSG_FLAG_AUTH_BIT = 0x01;
  const unsigned char SNMP_MSG_FLAG_PRIV_BIT = 0x02;
  const unsigned char SNMP_MSG_FLAG_RPRT_BIT = 0x04;
  
  /* control PDU handling characteristics */
  const int    UCD_MSG_FLAG_RESPONSE_PDU    = 0x100;
  const int    UCD_MSG_FLAG_EXPECT_RESPONSE = 0x200;
  const int    UCD_MSG_FLAG_FORCE_PDU_COPY  = 0x400;
  const int    UCD_MSG_FLAG_ALWAYS_IN_VIEW  = 0x800;
  
  /* view status */
  const unsigned char SNMP_VIEW_INCLUDED = 1;
  const unsigned char SNMP_VIEW_EXCLUDED = 2;
  
  /* lengths as defined by TCs */
  const unsigned char SNMPADMINLENGTH = 255;
  
  // Procedures! *********************************

  bool is_read_class(berTag pdu_type);
  bool is_write_class(berTag pdu_type);
  bool is_response_class(berTag pdu_type);
  bool is_notification_class(berTag pdu_type);
  bool is_internal_class(berTag pdu_type);
  bool is_confirmed_class(berTag pdu_type);
  bool is_unconfirmed_class(berTag pdu_type);


};

#endif /* SNMPCONSTANTS_H */




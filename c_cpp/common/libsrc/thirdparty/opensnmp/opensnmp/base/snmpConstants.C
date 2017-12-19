//
// snmpConstants.C
//
// this defines the is_confirmed procedure...

#include "config.h"
#include "snmpConstants.H"

// from rfc 2571:


bool
SNMP_CONSTANTS::is_read_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_GETBULK || pdu_type == BER_TAG_PDU_GETNEXT || 
      pdu_type == BER_TAG_PDU_GET) {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_write_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_SET) {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_response_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_REPORT  || pdu_type == BER_TAG_PDU_RESPONSE) {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_notification_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_INFORM  || pdu_type == BER_TAG_PDU_TRAP2)  {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_internal_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_REPORT) {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_confirmed_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_INFORM  || pdu_type == BER_TAG_PDU_GETBULK ||
      pdu_type == BER_TAG_PDU_GETNEXT || pdu_type == BER_TAG_PDU_GET ||
      pdu_type == BER_TAG_PDU_SET) {
    return(true);
  }
  else {
    return(false);
  }
}

bool
SNMP_CONSTANTS::is_unconfirmed_class(berTag pdu_type) {
  if (pdu_type == BER_TAG_PDU_REPORT  || pdu_type == BER_TAG_PDU_TRAP2 ||
      pdu_type == BER_TAG_PDU_RESPONSE) {
    return(true);
  }
  else {
    return(false);
  }
}


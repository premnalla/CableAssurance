#include "config.h"
#include "snmpRowStatus.H"
#include "debug.H"

snmpRowStatus::snmpRowStatus() {
    this->check = NULL;
}

snmpRowStatus::snmpRowStatus(const asnDataType &thecopy)
    : Integer32((dynamic_cast<const Integer32 &>(thecopy))),
      snmpRowDependent(table, row)
{
    this->check = NULL;
}

snmpRowStatus::snmpRowStatus(const Integer32 &thecopy) 
    : Integer32(thecopy),
      snmpRowDependent(table, row)
{
    this->check = NULL;
}

snmpRowStatus::snmpRowStatus(const snmpRowStatus &thecopy) 
    : Integer32(thecopy),
      snmpRowDependent(thecopy) {
    this->check = thecopy.check ? thecopy.check->clone() : NULL;
}

snmpRowStatus::snmpRowStatus(int init, snmpDataTable *table, //= NULL
                             snmpRow *row,                   //= NULL 
                             snmpRowStatusCheck *check)      //= NULL
    : Integer32(init),
      snmpRowDependent(table, row)
{
    this->check = check;
}

snmpRowStatus::snmpRowStatus(char *init, snmpDataTable *table, //= NULL 
                             snmpRow *row,                     //= NULL 
                             snmpRowStatusCheck *check)        //= NULL
    : Integer32(init),
      snmpRowDependent(table, row)
{
    this->check = check;
}

snmpRowStatus::~snmpRowStatus() {
    if (check)
        delete check;
}

snmpRowStatus &
snmpRowStatus::change_value(const asnDataType &fromNum) {
    int newValue = (dynamic_cast<const Integer32 &>(fromNum));
    int oldValue = Integer32::get_value();
    DEBUGINIT(debugObj, "snmpRowStatus");
    DEBUG9(debugObj, "Changing row status to: " << newValue << "\n");

    // see if the new value is a legal change or not.

// From the SNMPv2-TC MIB:
//                                          STATE
//               +--------------+-----------+-------------+-------------
//               |      A       |     B     |      C      |      D
//               |              |status col.|status column|
//               |status column |    is     |      is     |status column
//     ACTION    |does not exist|  notReady | notInService|  is active
// --------------+--------------+-----------+-------------+-------------
// set status    |noError    ->D|inconsist- |inconsistent-|inconsistent-
// column to     |       or     |   entValue|        Value|        Value
// createAndGo   |inconsistent- |           |             |
//               |         Value|           |             |
// --------------+--------------+-----------+-------------+-------------
// set status    |noError  see 1|inconsist- |inconsistent-|inconsistent-
// column to     |       or     |   entValue|        Value|        Value
// createAndWait |wrongValue    |           |             |
// --------------+--------------+-----------+-------------+-------------
// set status    |inconsistent- |inconsist- |noError      |noError
// column to     |         Value|   entValue|             |
// active        |              |           |             |
//               |              |     or    |             |
//               |              |           |             |
//               |              |see 2   ->D|see 8     ->D|          ->D
// --------------+--------------+-----------+-------------+-------------
// set status    |inconsistent- |inconsist- |noError      |noError   ->C
// column to     |         Value|   entValue|             |
// notInService  |              |           |             |
//               |              |     or    |             |      or
//               |              |           |             |
//               |              |see 3   ->C|          ->C|see 6
// --------------+--------------+-----------+-------------+-------------
// set status    |noError       |noError    |noError      |noError   ->A
// column to     |              |           |             |      or
// destroy       |           ->A|        ->A|          ->A|see 7
// --------------+--------------+-----------+-------------+-------------
// set any other |see 4         |noError    |noError      |see 5
// column to some|              |           |             |
// value         |              |      see 1|          ->C|          ->D
// --------------+--------------+-----------+-------------+-------------

//             (1) goto B or C, depending on information available to the
//             agent.

//             (2) if other variable bindings included in the same PDU,
//             provide values for all columns which are missing but
//             required, and all columns have acceptable values, then
//             return noError and goto D.

//             (3) if other variable bindings included in the same PDU,
//             provide legal values for all columns which are missing but
//             required, then return noError and goto C.

//             (4) at the discretion of the agent, the return value may be
//             either:

//                  inconsistentName:  because the agent does not choose to
//                  create such an instance when the corresponding
//                  RowStatus instance does not exist, or

//                  inconsistentValue:  if the supplied value is
//                  inconsistent with the state of some other MIB object's
//                  value, or

//                  noError: because the agent chooses to create the
//                  instance.

//             If noError is returned, then the instance of the status
//             column must also be created, and the new state is B or C,
//             depending on the information available to the agent.  If
//             inconsistentName or inconsistentValue is returned, the row
//             remains in state A.

//             (5) depending on the MIB definition for the column/table,
//             either noError or inconsistentValue may be returned.

//             (6) the return value can indicate one of the following
//             errors:

//                  wrongValue: because the agent does not support
//                  notInService (e.g., an agent which does not support
//                  createAndWait), or

//                  inconsistentValue: because the agent is unable to take
//                  the row out of service at this time, perhaps because it
//                  is in use and cannot be de-activated.

//             (7) the return value can indicate the following error:

//                  inconsistentValue: because the agent is unable to
//                  remove the row at this time, perhaps because it is in
//                  use and cannot be de-activated.

//             (8) the transition to D can fail, e.g., if the values of the
//             conceptual row are inconsistent, then the error code would
//             be inconsistentValue.

//             NOTE: Other processing of (this and other varbinds of) the
//             set request may result in a response other than noError
//             being returned, e.g., wrongValue, noCreation, etc.
    
    switch (newValue) {
        // these two end up being equivelent as far as checking the
        // status goes, although the final states are based on the
        // newValue.
        case SNMP_CONSTANTS::SNMP_ROW_ACTIVE:
        case SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE:
            if (oldValue == SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE || 
                oldValue == SNMP_CONSTANTS::SNMP_ROW_ACTIVE ||
                (oldValue == SNMP_CONSTANTS::SNMP_ROW_NOTREADY &&
                 check_row()))
                set_value(newValue);
            else
                throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            break;

        case SNMP_CONSTANTS::SNMP_ROW_NOTREADY:
            // Illegal set value.
            throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            break;

        case SNMP_CONSTANTS::SNMP_ROW_CREATEANDGO:
            if (oldValue != SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT)
                // impossible, we already exist.
                throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            set_value(SNMP_CONSTANTS::SNMP_ROW_ACTIVE);
            break;

        case SNMP_CONSTANTS::SNMP_ROW_CREATEANDWAIT:
            if (oldValue != SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT)
                // impossible, we already exist.
                throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            set_value(SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE);
            break;

        case SNMP_CONSTANTS::SNMP_ROW_DESTROY:
            if (!remove_self())
                throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            break;
            
        default:
            throw snmpProtoErr(PROTOERR_INCONSISTENTVALUE);
            break;
    }
    return *this;
}

snmpRowStatus &
snmpRowStatus::override_value(const asnDataType &fromNum) {
    int newValue = (dynamic_cast<const Integer32 &>(fromNum));

    switch (newValue)
      {
      case SNMP_CONSTANTS::SNMP_ROW_ACTIVE:
      case SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE:
	break;

      case SNMP_CONSTANTS::SNMP_ROW_NOTREADY:
      case SNMP_CONSTANTS::SNMP_ROW_CREATEANDWAIT:
	newValue = SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE;
	break;

      case SNMP_CONSTANTS::SNMP_ROW_CREATEANDGO:
	newValue = SNMP_CONSTANTS::SNMP_ROW_ACTIVE;
	break;

      case SNMP_CONSTANTS::SNMP_ROW_NONEXISTENT:
      case SNMP_CONSTANTS::SNMP_ROW_DESTROY:
      default:
	throw; // xxx-rks throw what?
	break;
      }

    set_value( newValue );
    return *this;
}

int
snmpRowStatus::get_value() const {
    DEBUGINIT(debugObj, "snmpRowStatus");
    switch(Integer32::get_value()) {
        /* these are the only legal values we should be at */
        case SNMP_CONSTANTS::SNMP_ROW_ACTIVE:
        case SNMP_CONSTANTS::SNMP_ROW_NOTINSERVICE:
            if (check_row())
                return Integer32::get_value();
            return SNMP_CONSTANTS::SNMP_ROW_NOTREADY;

        default:
            DEBUG1(debugObj, "ack ack ack, rowStatus value is wacked wacked wacked\n");
            return 0;
    }
}

bool
snmpRowStatus::check_row() const {
    if (row && check)
        return check->check_row(row);
    return true;
}

bool
snmpRowStatus::remove_self() {
    if (table && row) {
        return table->delete_row(row, true);
    }
    return false;
}

void
snmpRowStatus::set_check(snmpRowStatusCheck  *check) {
  if (this->check)
    delete this->check;
  this->check = check;
}

snmpRowStatusCheck  *
snmpRowStatus::get_check(bool extract) {
  snmpRowStatusCheck  *ret = check;
  if (extract)
    check = NULL;
  return ret;
}


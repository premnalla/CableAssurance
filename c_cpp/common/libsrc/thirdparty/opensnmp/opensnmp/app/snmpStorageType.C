#include "config.h"
#include "snmpStorageType.H"
#include "debug.H"

snmpStorageType::snmpStorageType() {
}

snmpStorageType::snmpStorageType(const Integer32 &thecopy) : Integer32(thecopy) {
}

snmpStorageType::snmpStorageType(const snmpStorageType &thecopy) 
    : Integer32(thecopy) {
}

snmpStorageType::snmpStorageType(int init) 
    : Integer32(init) {
}

snmpStorageType::snmpStorageType(char *init) 
    : Integer32(init) {
}

snmpStorageType::~snmpStorageType() {
}

snmpStorageType &
snmpStorageType::change_value(const asnDataType &fromNum) {
    int newValue = (dynamic_cast<const Integer32 &>(fromNum));
    int oldValue = Integer32::get_value();
    DEBUGINIT(debugObj, "snmpStorageType");
    DEBUG9(debugObj, "Changing storage type to: " << newValue << "\n");

// From the SNMPv2-TC MIB:

//             "Describes the memory realization of a conceptual row.  A
//             row which is volatile(2) is lost upon reboot.  A row which
//             is either nonVolatile(3), permanent(4) or readOnly(5), is
//             backed up by stable storage.  A row which is permanent(4)
//             can be changed but not deleted.  A row which is readOnly(5)
//             cannot be changed nor deleted.

//             If the value of an object with this syntax is either
//             permanent(4) or readOnly(5), it cannot be written.
//             Conversely, if the value is either other(1), volatile(2) or
//             nonVolatile(3), it cannot be modified to be permanent(4) or
//             readOnly(5).  (All illegal modifications result in a
//             'wrongValue' error.)

//             Every usage of this textual convention is required to
//             specify the columnar objects which a permanent(4) row must
//             at a minimum allow to be writable."

    switch (oldValue) {
        case SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT:
        case SNMP_CONSTANTS::SNMP_STORAGE_READONLY:
            throw snmpProtoErr(PROTOERR_WRONGVALUE);

        case SNMP_CONSTANTS::SNMP_STORAGE_OTHER:
        case SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE:
        case SNMP_CONSTANTS::SNMP_STORAGE_NONVOLATILE:
            if (newValue == SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT ||
                newValue == SNMP_CONSTANTS::SNMP_STORAGE_READONLY)
                throw snmpProtoErr(PROTOERR_WRONGVALUE);
    }
    set_value( newValue );

    return *this;
}

snmpStorageType &
snmpStorageType::override_value(const asnDataType &fromNum) {
    int newValue = (dynamic_cast<const Integer32 &>(fromNum));
    set_value( newValue );
    return *this;
}

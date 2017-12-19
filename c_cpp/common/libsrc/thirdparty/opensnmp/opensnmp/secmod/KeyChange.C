
#include "config.h"
#include "KeyChange.H"
#include "snmpUSMData.H"
#include "asnBadAssignment.H"

KeyChange::KeyChange(snmpDataTable *table, snmpRow *row) 
    : snmpRowDependent(table, row)
{
}

KeyChange::KeyChange(KeyChange const &thecopy) 
    : OctetString(thecopy), snmpRowDependent(thecopy) 
{
}

KeyChange::~KeyChange() 
{
}

// KeyChange &KeyChange::change_value(const asnDataType &newValue) {
// Inheritance problems....
// RawDataWrap<BER_TAG_OCTET_STRING>
KeyChange &
KeyChange::change_value(const asnDataType &newValue) {
    string keych = (dynamic_cast<const RawData &>(newValue)).get_value();
    snmpRow *row = this->get_row(false);
    OctetString *keyOStr;
    OID         *hashProto;
    string       newKey;
    //    cout << "KeyChange::change_value\n";

    if ( NULL == (hashProto = row->get_column_OID
		  (int(snmpUSMData::usmUserAuthProtocolCol))) ) {
      throw asnBadAssignment
	("KeyChange::change_value: unable to get hash protocol from row");
    }
    // which key are we changing?
    int col = row->get_column_number(this);

    if (col == snmpUSMData::usmUserKeyChangeCol) {
      if ( NULL == (keyOStr = row->get_column_OctetString
		    (int(snmpUSMData::usmUserAuthKeyCol))) ) {
	throw asnBadAssignment
	  ("KeyChange::change_value: failed to get key/hash value from row\n");
      }
    }
    else if (col == snmpUSMData::usmUserPrivKeyChangeCol) {
      if ( NULL == (keyOStr = row->get_column_OctetString
		    (int(snmpUSMData::usmUserPrivKeyCol))) ) {
	throw asnBadAssignment
	  ("KeyChange::change_value: failed to get key value from row\n");
      }
    }
    else {
      throw asnBadAssignment
	("Keychange:change_value: unsupported key change column");
    }

    string tmp_keyOStr(*keyOStr);
    if (!this->crypto.decode_keychange(*hashProto,
				       tmp_keyOStr,
				       keych,
				       &newKey)) {
      throw snmpStringException("Keychange:change_value: crypto error");
    }

    *keyOStr = newKey;
    // clean up
    newKey = "";

    return *this;
}

// const string &KeyChange::get_value() const {
//     return string();
// }


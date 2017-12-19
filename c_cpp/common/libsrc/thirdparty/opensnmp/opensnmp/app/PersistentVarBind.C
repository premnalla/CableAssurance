#include "config.h"
#include "PersistentVarBind.H"
#include "snmpDatabaseObj.H"

PersistentVarBind::PersistentVarBind(OID *oid, asnDataType *data)
  : VarBind( oid, data )
{
  snmpDatabaseObj *theDatabase = snmpDatabaseObj::getSnmpDatabaseObj();

  if (theDatabase)
      theDatabase->getInitialValue( *this ); // create default persistent value
}

PersistentVarBind::~PersistentVarBind(void)
{
  // xxx-rks: write code!
}

void
PersistentVarBind::EraseData(void)
{
  // xxx-rks: write code!
}

void
PersistentVarBind::set_value_no_update( const asnDataType & new_value )
{
  *VarBind::get_value() = new_value; 
}

void
PersistentVarBind::set_value( const asnDataType & new_value )
{
  set_value_no_update( new_value ); 
  update();
}

void
PersistentVarBind::update() {
  snmpDatabaseObj *theDatabase = snmpDatabaseObj::getSnmpDatabaseObj();
  if (theDatabase)
      theDatabase->store( *this ); // create default persistent value
}

void
PersistentVarBind::DecodeData(char *in, int len) {
    VarBind::get_value()->DecodeData(in, len);
    update();
}

char *
PersistentVarBind::EncodeData(char *in) const {
    return (VarBind::get_value())->EncodeData(in);
}

// since we're really just a wrapper around a data type, only
// encode it as the datatype and not like a varbind:
PersistentVarBind &
PersistentVarBind::operator =( const asnDataType & from ) { 
    VarBind::get_value()->change_value( from );
    update();
    return *this;
};

PersistentVarBind &
PersistentVarBind::change_value(const asnDataType &from) {
    VarBind::get_value()->change_value(from);
    update();
    return *this;
}

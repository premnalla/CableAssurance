/* VarBind.C */
//
// VarBind
//
// varbind is a variable bindings, which is a sequence of an OID and
// an associated VALUE.
//
// Encoding:
// TAG LEN <OID ENCODING> <VALUE ENCODING>
//
// TAG=0x30 (sequence tag)

#include "config.h"
#include <stdio.h>
#include "VarBind.H"

//
// VarBind
//

VarBind::VarBind() : asnDataType(BER_TAG_SEQUENCE)
{
  oid = 0;
  value = 0;
}

VarBind::~VarBind()
{
  EraseData();
}

VarBind::VarBind(const VarBind &ref) : asnDataType(ref.get_tag())
{
  // EraseData(); no need to erase on newly constructed vb ??
  this->oid = ref.oid->clone();
  if (ref.value)
    this->value = ref.value->clone();
  else
    this->value = NULL;
}

//
// Add a variable to the binding list.
//
VarBind::VarBind(OID *oid, asnDataType *value) : asnDataType(BER_TAG_SEQUENCE)
{
  // EraseData(); no need to erase on newly constructed vb ??
  this->oid = oid;
  this->value = value;
}

void
VarBind::EraseData()
{
  if (oid)
    delete oid;
  oid = 0;
  if (value)
    delete value;
  value = 0;
}


//
// Encode the data into a buffer, but in reverse.
//
char *
VarBind::EncodeData(char *cp) const
{
  if (value)
      cp = value->asnEncode(cp);
  else {
      Null n;
      cp = n.asnEncode(cp);
  }
  cp = oid->asnEncode(cp);
  return cp;
}

//
// Decode the data from a char * buffer.
//
void
VarBind::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();
  
  oid = new OID();
  tcp = oid->asnDecode(tcp, length);
  
  tcp = asnDataType::asnDecodeUnknown(tcp, &value, (length - (tcp - cp)));
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string VarBind::toString() const
{
  string s = "Varbind: oid=";
  if (oid == 0)
    s = s + "NULL";
  else
    s = s + string(*oid);
  s = s + ", val=";
  if (value == 0)
    s = s + "NULL\n";
  else
    s = s + string(*value) + "\n";
  return s;
}

VarBind::operator string() const
{
  string s = "Varbind: oid=";
  if (oid == 0)
    s = s + "NULL";
  else
    s = s + string(*oid);
  s = s + ", val=";
  if (value == 0)
    s = s + "NULL";
  else
    s = s + string(*value);
  return s;
}

OID *
VarBind::get_OID(bool extract) {
  OID *ret = oid;
  if (extract)
    oid = 0;
  return ret;
}

void
VarBind::set_OID(OID *newval) {
  if (oid)
    delete oid;
  oid = newval;
}

asnDataType *
VarBind::get_value(bool extract) {
  asnDataType *ret = value;
  if (extract)
    value = 0;
  return ret;
}

void VarBind::set_value(asnDataType *newvalue) {
  if (value)
    delete value;
  value = newvalue;
}

VarBind &
VarBind::change_value(const asnDataType &right) {
    const VarBind &ref = dynamic_cast<const VarBind &>(right);

    if (this->oid && ref.oid)
        *(this->oid) = *(ref.oid);
    else {
        if (this->oid)
            delete this->oid;
        if (ref.oid)
            this->oid = ref.oid->clone();
        else
            this->oid = 0;
    }
    
    if (this->value)
        delete this->value;
    if (ref.value)
        this->value = ref.value->clone();
    else
        this->value = 0;

    return *this;
}

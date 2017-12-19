/* VarBindList.C */
//
// VarBindList
//
// varbindlists is a sequence of variable bindings, which are in turn
// sequences of OID and VALUE.
//

#include "config.h"
#include <stdio.h>
#include "VarBindList.H"

//
// VarBindList
//

VarBindList::VarBindList() : asnDataType(BER_TAG_SEQUENCE)
{
}

VarBindList::VarBindList(VarBind* newVB) : asnDataType(BER_TAG_SEQUENCE)
{
  this->varbinds.push_back(newVB);
}

VarBindList::VarBindList(const VarBindList &ref) : asnDataType(BER_TAG_SEQUENCE)
{
  EraseData();
  VarBindList::const_iterator i;
  for(i = ref.begin(); i != ref.end(); i++) {
    this->varbinds.push_back((*i)->clone());
  }
}

VarBindList::~VarBindList()
{
  EraseData();
}

  
void
VarBindList::EraseData()
{
  std::list<VarBind *>::iterator i;
  for(i = varbinds.begin(); i != varbinds.end();i++) {
    if (NULL != *i)  delete *i;
  }
  varbinds.erase(varbinds.begin(), varbinds.end());
}

//
// Add a variable to the binding list.
//
void
VarBindList::Add(VarBind *vb)
{
  varbinds.push_back(vb);
}

void
VarBindList::push_back(VarBind *vb)
{
  varbinds.push_back(vb);
}

void
VarBindList::push_front(VarBind *vb)
{
  varbinds.push_front(vb);
}

void
VarBindList::insert(std::list<VarBind *>::iterator before, VarBind *vb)
{
  varbinds.insert(before, vb);
}

//
// Encode the data into a buffer, but in reverse.
//
char *
VarBindList::EncodeData(char *cp) const
{
  VarBindList::const_reverse_iterator i;
  for(i = varbinds.rbegin(); i != varbinds.rend(); i++) {
    cp = (*i)->asnEncode(cp);         // encode each varbind
  }
  return cp;
}

//
// Decode the data from a char * buffer.
//
void
VarBindList::DecodeData(char *cp, int length)
{
  char *cp2 = cp;
  int lenLeft = length;
  VarBind *vbp;

  EraseData();
  
  while((lenLeft = length - (cp - cp2)) > 0) {
    vbp = new VarBind();
    cp = vbp->asnDecode(cp, lenLeft);
    varbinds.push_back(vbp);
  }
}


//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string VarBindList::toString() const
{
  string s;
  VarBindList::const_iterator i = varbinds.begin();

  s = "VarBindList: \n";
  for(i = varbinds.begin(); i != varbinds.end(); i++) {
    s = s + (*i)->toString();
  }
  return s;
}

VarBindList::operator string() const
{
  string s;
  VarBindList::const_iterator i = varbinds.begin();

  s = "VarBindList: ";
  for(i = varbinds.begin(); i != varbinds.end(); i++) {
    s = s + string(**i);
  }
  return s;
}

VarBindList::iterator
VarBindList::begin()
{
  return varbinds.begin();
}

VarBindList::iterator
VarBindList::end()
{
  return varbinds.end();
}

VarBindList::const_iterator
VarBindList::begin() const
{
  return varbinds.begin();
}

VarBindList::const_iterator
VarBindList::end() const
{
  return varbinds.end();
}

VarBindList::reverse_iterator
VarBindList::rbegin()
{
  return varbinds.rbegin();
}

VarBindList::reverse_iterator
VarBindList::rend()
{
  return varbinds.rend();
}

VarBindList::const_reverse_iterator
VarBindList::rbegin() const
{
  return varbinds.rbegin();
}

VarBindList::const_reverse_iterator
VarBindList::rend() const
{
  return varbinds.rend();
}


string VarBindList::toPrettyString(string prefix)
{

  string s;
  VarBindList::iterator i;
  asnDataType *ad = 0;


  for(i = varbinds.begin(); i != varbinds.end(); i++) {
    s = s + prefix;
    s = s + (*i)->get_OID()->toString();
    s = s + " = ";
    ad = (*i)->get_value();
    s = s + ad->toString() + "\n";
  }
  return s;
}

int VarBindList::size() {
    return varbinds.size();
}


VarBind *
VarBindList::get_varBindNum(int index, bool extract) {
  iterator indexItr = varbinds.begin();
  VarBind * retValue;

  if (index < 1) return NULL;

  for(int count=1; (indexItr != varbinds.end() && count < index); 
      count++, indexItr++);

  if (indexItr != varbinds.end())  {
    retValue = *indexItr;
    if (extract) varbinds.erase(indexItr);
    return retValue;
  }
  else return NULL;
}


VarBindList &
VarBindList::change_value(const asnDataType &right) {
    
    // we are unable to use an assignment operator on each varbind
    // here, since there may be a mis-matching number of varbinds in
    // the list.  just clone it all.
    // 
    const VarBindList &ref = dynamic_cast<const VarBindList &>(right);
    EraseData();
    VarBindList::const_iterator i;
    for(i = ref.begin(); i != ref.end(); i++) {
        this->varbinds.push_back((*i)->clone());
    }
    return *this;
}

void
VarBindList::null_values() {
    // set all varbind values to Null()
    VarBindList::const_iterator i;
    for(i = varbinds.begin(); i != varbinds.end(); i++) {
        (*i)->set_value(new Null());
    }
}


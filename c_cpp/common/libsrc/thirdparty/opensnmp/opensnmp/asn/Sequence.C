/* Sequence.C */
//
// Sequence
//
// Sequences is a sequence of variable bindings, which are in turn
// sequences of OID and VALUE.
//

#include "config.h"
#include <stdio.h>
#include "Sequence.H"

//
// Sequence
//

Sequence::Sequence() : asnDataType(BER_TAG_SEQUENCE)
{
}

Sequence::Sequence(asnDataType* newDT) : asnDataType(BER_TAG_SEQUENCE)
{
  this->asnDataTypes.push_back(newDT);
}

Sequence::Sequence(const Sequence &ref) : asnDataType(BER_TAG_SEQUENCE)
{
  EraseData();
  Sequence::const_iterator i;
  for(i = ref.begin(); i != ref.end(); i++) {
    this->asnDataTypes.push_back((*i)->clone());
  }
}

Sequence::~Sequence()
{
  EraseData();
}

  
void
Sequence::EraseData()
{
  asnDataTypes.erase(asnDataTypes.begin(), asnDataTypes.end());
}

//
// Add a variable to the binding list.
//
void
Sequence::Add(asnDataType *vb)
{
  asnDataTypes.push_back(vb);
}

void
Sequence::push_back(asnDataType *vb)
{
  asnDataTypes.push_back(vb);
}

void
Sequence::push_front(asnDataType *vb)
{
  asnDataTypes.push_front(vb);
}

//
// Encode the data into a buffer, but in reverse.
//
char *
Sequence::EncodeData(char *cp) const
{
  Sequence::const_reverse_iterator i;
  for(i = asnDataTypes.rbegin(); i != asnDataTypes.rend(); i++) {
    cp = (*i)->asnEncode(cp);         // encode each asnDataType
  }
  return cp;
}

//
// Decode the data from a char * buffer.
//
void
Sequence::DecodeData(char *cp, int length)
{
  char *cp2 = cp;
  int lenLeft = length;
  asnDataType *dtp;

  EraseData();
  
  while((lenLeft = (length - (cp - cp2))) > 0) {
    cp = asnDataType::asnDecodeUnknown( cp, &dtp, lenLeft );
    asnDataTypes.push_back(dtp);
  }
}


//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string Sequence::toString() const
{
  string s;
  Sequence::const_iterator i = asnDataTypes.begin();

  s = "Sequence: \n";
  for(i = asnDataTypes.begin(); i != asnDataTypes.end(); i++) {
    s = s + (*i)->toString() + ';';
  }
  return s;
}

Sequence::operator string() const
{
  string s;
  Sequence::const_iterator i = asnDataTypes.begin();

  s = "Sequence: ";
  for(i = asnDataTypes.begin(); i != asnDataTypes.end(); i++) {
    s = s + string(**i) + ';';
  }
  return s;
}

Sequence::iterator
Sequence::begin()
{
  return asnDataTypes.begin();
}

Sequence::iterator
Sequence::end()
{
  return asnDataTypes.end();
}

Sequence::const_iterator
Sequence::begin() const
{
  return asnDataTypes.begin();
}

Sequence::const_iterator
Sequence::end() const
{
  return asnDataTypes.end();
}

Sequence::reverse_iterator
Sequence::rbegin()
{
  return asnDataTypes.rbegin();
}

Sequence::reverse_iterator
Sequence::rend()
{
  return asnDataTypes.rend();
}

Sequence::const_reverse_iterator
Sequence::rbegin() const
{
  return asnDataTypes.rbegin();
}

Sequence::const_reverse_iterator
Sequence::rend() const
{
  return asnDataTypes.rend();
}


int Sequence::size() {
    return asnDataTypes.size();
}

Sequence &
Sequence::change_value(const asnDataType &right) {
    
    // we are unable to use an assignment operator on each asnDataType
    // here, since there may be a mis-matching number of asnDataTypes in
    // the list.  just clone it all.
    // 
    const Sequence &ref = dynamic_cast<const Sequence &>(right);
    EraseData();
    Sequence::const_iterator i;
    for(i = ref.begin(); i != ref.end(); i++) {
        this->asnDataTypes.push_back((*i)->clone());
    }
    return *this;
}

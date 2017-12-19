/* Null.C */
//
// Null
//
//   NULL is simply a tag of 05, followed by a length of 00.  There is
//   no data associated with a NULL.
//

#include "config.h"
#include <stdlib.h>
#include <stdio.h>
#include "Null.H"
#include "asnParseError.H"

Null::Null() : asnDataType(BER_TAG_NULL)
{
}

Null::Null(berTag ber_tag) : asnDataType(ber_tag)
{
}


Null::Null(const Null &thecopy) : asnDataType(thecopy.get_tag())
{
}

Null::~Null()
{
}

//
// Encode the data into a buffer, but in reverse.
//
char *
Null::EncodeData(char *cp) const
{
  return cp;
}

void
Null::DecodeData(char *cp, int len)
{
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string Null::toString() const
{
  return ("Null");
}

Null &
Null::change_value(const asnDataType &right) {
    // cast it so it throws a bad_cast if its a wrong rval.
    const Null &ref = dynamic_cast<const Null &>(right);
    (void) ref.get_tag(); // do something to stop 'unused var' warning
    return *this;
}

Null::operator string() const
{
  return("NULL");
}


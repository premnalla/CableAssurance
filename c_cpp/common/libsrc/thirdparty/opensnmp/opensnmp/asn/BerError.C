/* BerError.C */
#include "config.h"
#include "BerError.H"

BerError::BerError(berTag ber_tag) 
  : Null(ber_tag)
{
}

BerError::BerError(const BerError &thecopy)
  : Null(thecopy)
{
}

BerError::~BerError() {
}

string BerError::toString() const
{
  switch (get_tag()) {
    case BER_TAG_NOSUCHOBJECT:
      return "No Such Object";

    case BER_TAG_NOSUCHINSTANCE:
      return "No Such Instance";

    case BER_TAG_ENDOFMIBVIEW:
      return "End of MIB View";

    default:
      return "Unknown BerError";
  }
}

BerError::operator string() const
{
  return this->toString();
}

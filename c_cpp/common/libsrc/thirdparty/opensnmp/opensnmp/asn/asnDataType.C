/* asnDataType.C */
//
// generic asn (BER) parent functions.
//

#include "config.h"
#include <stdio.h>

#include "asnDataTypes.H"

asnDataType::asnDataType()
{
}

asnDataType::~asnDataType()
{
}

asnDataType::asnDataType(berTag t)
{
  tag = t;
}

void
asnDataType::EraseData()
{
  // no-op
}

///
/// reverse encodings...  the cp moves backwards not forwards.
///
///   Note:  child classes must implement: EncodeData(char *cp)
///

//
// encode the length in reverse from the end of the packet forward
//
char *
asnDataType::EncodeLength(int len, char *buf)
{
  if (len <= 0x7f) {
    *buf-- = len;
  } else {
    char *cp = buf;
    
    while(len > 0xff) {
      *buf-- = len & 0xff;
      len >>= 8;
    }
    *buf-- = len & 0xff;
    *buf-- = (cp - buf) | 0x80;
  }
  return buf;
}

//
// encode the header (tag + len) in reverse.
//
char *
asnDataType::EncodeHeader(char *cp, int len, const berTag & asnTag)
{
  cp = EncodeLength(len, cp);
  *cp-- = asnTag;
  return cp;
}

//
// encode the object in reverse into a character buffer: data, header
//
char *
asnDataType::asnEncode(char *cp) const
{
  char *cp2 = cp;
  
  cp = EncodeData(cp);

  return EncodeHeader(cp, cp2 - cp, get_tag());
}

//
// Decoding functions.
//
//   asnDecode(char *):
//     Should be called to decode a given object from an asn char *
//     buffer.  It uses DecodeHeader and DecodeData to do this.
//     Children should implement DecodeData.
//
//     Returns a pointer to the point in the stream beyond the object parsed.
//
//   asnDecodeUnkown(char *):
//     A function to create and return an asn object given a ASN stream buffer.
char *
asnDataType::DecodeLength(char *cp, int *thelength)
{
  int asnLength = 0, lengthLen = 0;

  if (*cp & 0x80) { // long length ASN BER Format.
    lengthLen = *cp++ & 0x7f;

    // sanity check for length of length value versus buffer length.
    if ((2 + lengthLen) > *thelength)
      throw asnParseError("ASN length of length value is too large: greater than buffer/message size");
    
    while (lengthLen > 4) {
      if (*cp++) 
	throw asnParseError("ASN length of length value is too large: apparently greater than 4 bytes.");
      else
	lengthLen--;
    }
    
    while(lengthLen--) {
      asnLength = (asnLength << 8) | ((unsigned char) *cp++);
    }
  } 
  else { // short length ASN BER Format, soo much easier.
    // *cp is the exact length
    asnLength = *cp++;
  }

  // Sanity check for length versus buffer length.
  if((2 + lengthLen + asnLength) > *thelength)
    throw asnParseError("Size of ASN data decodes as larger than the buffer/message size!");

  *thelength = asnLength;
  return cp;
}

char *
asnDataType::DecodeHeader(char *cp, int *thelength)
{
  if ((get_tag() != *cp++) || (*thelength < 2))
    throw asnUsageError("Asked to decode something we're not able too: Unknown BER tag / too short BER encoding");

  return DecodeLength(cp,thelength);
}

char *
asnDataType::asnDecode(char *cp, int length)
{
  int asnlength = length;
  
  cp = DecodeHeader(cp, &asnlength);
  
  DecodeData(cp, asnlength);
  return cp+asnlength;
}

char *
asnDataType::asnDecodeUnknown(char *cp, asnDataType **ppadt, int length)
{

  if (ppadt == 0)
    return NULL;
  
  switch(*((unsigned char *) cp)) {
    case BER_TAG_SEQUENCE:
      *ppadt = (asnDataType *) new Sequence();
      break;

    case BER_TAG_INTEGER32:
      *ppadt = (asnDataType *) new Integer32();
      break;

    case BER_TAG_OCTET_STRING:
      *ppadt = (asnDataType *) new OctetString();
      break;

    case BER_TAG_OPAQUE:
      *ppadt = (asnDataType *) new Opaque();
      break;

    case BER_TAG_IPADDRESS:
      *ppadt = (asnDataType *) new IpAddress();
      break;

    case BER_TAG_NULL:
      *ppadt = (asnDataType *) new Null();
      break;

    case BER_TAG_BIT_STRING:
      *ppadt = (asnDataType *) new BitString();
      break;

    case BER_TAG_OBJECT_ID:
      *ppadt = (asnDataType *) new OID();
      break;

    case BER_TAG_UNSIGNED32:
      *ppadt = (asnDataType *) new Unsigned32();
      break;

    case BER_TAG_COUNTER:
      *ppadt = (asnDataType *) new Counter32();
      break;

    case BER_TAG_TIMETICKS:
      *ppadt = (asnDataType *) new TimeTicks();
      break;

    case BER_TAG_COUNTER64:
      *ppadt = (asnDataType *) new Counter64();
      break;

    case BER_TAG_NOSUCHOBJECT:
      *ppadt = (asnDataType *) new BerError(BER_TAG_NOSUCHOBJECT);
      break;

    case BER_TAG_NOSUCHINSTANCE:
      *ppadt = (asnDataType *) new BerError(BER_TAG_NOSUCHINSTANCE);
      break;

    case BER_TAG_ENDOFMIBVIEW:
      *ppadt = (asnDataType *) new BerError(BER_TAG_ENDOFMIBVIEW);
      break;

    default:
      throw asnUsageError("asnDecodeUnknown asked to handle a type it can't.");
      break;
  }
  
  return (*ppadt)->asnDecode(cp, length);
}


// base class virtual functions
int
asnDataType::asnHeaderLength(char *cp) {
  int headerLength=0;

  cp++; // skip the tag

  if (*cp & 0x80) {
    int lengthLen = *cp++ & 0x7f;
    
    if (lengthLen > 4)
      throw asnParseError("length of length field too long");

    // tag size + lengthLen size + size of the length value
    headerLength = 1 + 1 + lengthLen; 
    
  } else {
    // tag size + size of the length value
    headerLength = 1 + 1;
  }

  return(headerLength);
}


// base class virtual functions
int
asnDataType::asnLengthWithHeader(char *cp) {
  int headerLength=0, theLength=0;

  cp++; // skip the tag

  if (*cp & 0x80) {
    int lengthLen = *cp++ & 0x7f;
    
    if (lengthLen > 4)
      throw asnParseError("length of length field too long");

    // tag size + lengthLen size + size of the length value
    headerLength = 1 + 1 + lengthLen; 
    
    while(lengthLen--) {
      theLength = (theLength << 8) | ((unsigned char) *cp++);
    }

  } else {
    // *cp is the exact length
    theLength = *cp++;
    // tag size + size of the length value
    headerLength = 1 + 1;
  }

  return(headerLength + theLength);
}


string
asnDataType::toString() const
{
  return("asnDataType::toString() not implemented");
}

berTag
asnDataType::get_tag() const
{
  return tag;
}

void
asnDataType::set_tag(berTag t)
{
  tag = t;
}

std::ostream&
operator << ( std::ostream& os, const asnDataType & ref )
{
  os << ref.operator string();
  return os;
}

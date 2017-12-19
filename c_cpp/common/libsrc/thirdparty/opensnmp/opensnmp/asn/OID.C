/* OID.C */
//
// OID encodings
//
//   oids are encoded as TAG LEN DATA, where DATA is a series of
//   octets to encode each data segment.  Continuation of each segment
//   into the next octet is indicated by the MSBit of the current
//   byte.  In this fashion, each octet contains 7 bits of real data.
//
//   The first two segments (X.Y...) are limited in scope (by
//   definition) and are encoded as X*40+Y.
//
//   Example:  .1.3.6.1.127.128
//   Encoding: 06 06 2b 06 01 7f 81 00 
//             ^^ ^^ ^^^^^^^^^^^^^^^^^
//       tag --/   |        |
//    length -----/         |
//      data ---------------/
//

#include "config.h"
#include <string>
#include <iostream>
#include <sstream>

#include <stdlib.h>
#include <stdio.h>

#define NODEBUGGING
#include "OID.H"
#include "asnParseError.H"
#include "opensnmpUtilities.H"
using namespace std;


#define EXTREME_DETAIL( x ) DEBUGN( 10, this->dbg, x  )

oid_display_types OID::display_as = NUMERIC;

//
// OID
//

OID::OID() : asnIndex(BER_TAG_OBJECT_ID)
{
  DEBUGCREATE( OID::dbg, "OID" );
}

OID::OID(unsigned int *init, int initlen) : asnIndex(BER_TAG_OBJECT_ID)
{
#ifndef NODEBUGGING
  if( OID::dbg == NULL )
    DEBUGCREATE( OID::dbg, "OID" );
#endif
  this->set(init, initlen);
}

OID::OID(list<unsigned int> fromlist) : asnIndex(BER_TAG_OBJECT_ID)
{
#ifndef NODEBUGGING
  if( OID::dbg == NULL )
    DEBUGCREATE( OID::dbg, "OID" );
#endif
  values = fromlist;
}

OID::OID(const char *in) : asnIndex(BER_TAG_OBJECT_ID)
{
#ifndef NODEBUGGING
  if( OID::dbg == NULL )
    DEBUGCREATE( OID::dbg, "OID" );
#endif
  this->set(in);
}

OID::OID(const string & in) : asnIndex(BER_TAG_OBJECT_ID)
{
#ifndef NODEBUGGING
  if( OID::dbg == NULL )
    DEBUGCREATE( OID::dbg, "OID" );
#endif
  this->set(in);
}

OID::OID(SmiNode *sn) : asnIndex(BER_TAG_OBJECT_ID) {
#ifndef NODEBUGGING
  if( OID::dbg == NULL )
    DEBUGCREATE( OID::dbg, "OID" );
#endif
  this->set(sn);
}


void
OID::set(const string & from) {
    flow( "OID::set()" );

    // allow '-' for negative integers?
    string   numeric_chars = "-0123456789";
    SmiNode *sn		   = NULL;
    //    LIBSMI   ls;
    unsigned int oid_array[MAX_OID_LEN];

    values.erase(values.begin(), values.end());

    detail( "from=" + from + ", size: " + 
	    OPENSNMP_UTILITIES::intToString(from.size()) );

    string nextpos = from;
    if (nextpos.substr(0,1) == ".")
        nextpos = nextpos.substr(1);

    EXTREME_DETAIL( "next=" << nextpos << ", size: " << nextpos.size() );

    while(nextpos.length() != 0) {
        const string &element = nextpos.substr(0, nextpos.find('.',0));
	detail( "segment: " + element );
        if (nextpos.find('.',1) != string::npos) {
	    EXTREME_DETAIL( "find: " << nextpos.find('.',2) );
            nextpos = nextpos.substr(nextpos.find('.',1)+1);
        } else {
            nextpos = "";
        }
	EXTREME_DETAIL( "next=" << nextpos << ", size: " << nextpos.length() );
        if ( element.find_first_not_of(numeric_chars) != string::npos ) {
            // attempt to get the current node.

            if (size() != 0) {
	      EXTREME_DETAIL( "getting SMI node for " << (string) *this 
		      << " = " << size() );

                sn = smiGetNodeByOID(size(), array(oid_array));

                if (sn == NULL || (unsigned int) sn->oidlen != size()) {
		  err( "no SMI info for " + (string) *this
		       + ", so can't get next node for " + element );
                    throw illegalOID("no SMI info for " + (string) *this + ", so can't get next node for " + element);
                }

                // search all children for correct node with the right name.
                for(sn = smiGetFirstChildNode(sn);
                    sn != NULL;
                    sn = smiGetNextChildNode(sn)) {
                    if (sn->name == element) {
                        break;
                    }
                }

                if (sn == NULL) {
		  err( "illegalOID at segment: " + element );
                    throw illegalOID("at segment: " + element);
                }
            } else {
                sn = smiGetNode(NULL, element.c_str());

                // smiNode wasn't found?  Then its an illegal OID.
                if (sn == NULL) { // XXX: or oid length is wrong
		  err( "illegalOID starting segment: " + element );
                    throw illegalOID("starting segment: " + element);
                }
            }

            if (size()) {
                values.push_back(sn->oid[sn->oidlen-1]);
            } else {
                set(sn);
            }

	    EXTREME_DETAIL( "string: " << element << " = " 
		    << sn->oid[sn->oidlen-1] );
        } else {
            EXTREME_DETAIL( "numeric: " << element );
            values.push_back(atoi(element.c_str()));
        }
    }
}

void
OID::set(SmiNode *sn) {
    if (sn)
        for(unsigned int x = 0; x < sn->oidlen; x++) {
            values.push_back(sn->oid[x]);
        }
}

OID::OID(const OID &thecopy, char *append) : asnIndex(thecopy.get_tag())
{
  values = thecopy.values;
  
  while(append != 0 && *append != 0) {
    if (*append == '.')
      append++;
    values.push_back(atoi(append));
    while(*append != 0 && *append != '.') 
      append++;
  }
}

OID::~OID()
{
}

OID*
OID::clone() const { 
  return new OID(*this); 
};

void
OID::EraseData()
{
  values.erase(values.begin(),values.end());
}

int
OID::length() const {
  return values.size();
}


//
// Encode the data into a buffer.  (in reverse)
//
char *
OID::EncodeData(char *cp) const
{

  if (values.size() <= 1) {
    // illegally short oids are encoded as 0.0 (the NULL oid)
    *cp-- = 0;
    *cp-- = 0;
    return cp;
  }

  list<unsigned int>::const_reverse_iterator i;
  list<unsigned int>::const_reverse_iterator third = values.rend();
  third--;  // skip the first two
  third--;  // skip the first two

  for(i = values.rbegin(); i != third; i++) {
    int tmpi = *i;

    *cp-- = tmpi & 0x7f;
    tmpi >>= 7;

    while(tmpi > 0) {
      *cp-- = ((tmpi & 0x7f) | 0x80);
      tmpi >>= 7;
    }
  }

  // first two oid values are encoded toget_her
  *cp = *i++;
  *cp-- += (*i * 40);
  return cp;
}

void  
OID::DecodeData(char *cp, int len)
{
  char *cp2 = cp;
  EraseData();

  if (len < 1) {
    // illegal!
    err( "asnParseError: Zero length oid is illegal." );
    throw asnParseError("Zero length oid is illegal.");
  }
  
  values.push_back(*cp / 40);
  values.push_back(*cp % 40);
  cp++;
  
  while ((cp - cp2) < len) {
    if (values.size() >= MAX_OID_LEN) {
      err( "asnParseError: encoded oid size is larger than the maximum allowed" );
      throw asnParseError("encoded oid size is larger than the maximum allowed");
    }

    int tmpint = 0;
    while ((*cp & 0x80) == 0x80) {
      tmpint = tmpint << 7 | (*cp++ & 0x7f);
    }
    values.push_back(tmpint << 7 | *cp++);
  }
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//

string OID::toDisplayString(oid_display_types type) const {
  list<unsigned int>::const_iterator i;
  string ret_string;
  ostringstream buf;
  SmiNode *sn		   = NULL,
          *rootn           = NULL;
  string tmp_str;
  unsigned int remainder = 0;
  unsigned int count, tmpoid;
  unsigned int oid_array[MAX_OID_LEN];

  if (type == NUMERIC) {
      // do first for speed.
      for(i = values.begin(); i != values.end(); i++) {
          buf << '.' << *i;
      }
      // no ends, as it ends up as an extra character in the string
      return string(buf.str());
  }

  sn = smiGetNodeByOID(size(), array(oid_array));
  if (sn) {
      remainder = sn->oidlen;
      switch(type) {
          case SINGLE_NODE:
              buf << sn->name;
              break;

          case MODULE_AND_NODE:
              buf << smiGetNodeModule(sn)->name << (string) "::" << sn->name;
              break;
          
          default:
              // == FULLY_QUALIFIED:
              tmpoid = 1;
              rootn = smiGetNodeByOID(1, &tmpoid);
              for(; sn && sn != rootn; sn = smiGetParentNode(sn)) {
                  tmp_str = (string) "." + (string) sn->name + tmp_str;
              }
              if (sn)
                  tmp_str = (string) "." + (string) sn->name + tmp_str;
              buf << tmp_str;
              break;
      }
  }

//  cout << "remainder: " << remainder << "\n";
  for(count = 0, i = values.begin(); i != values.end() && count < size(); 
      i++, count++) {
      if (count >= remainder)
          buf << '.' << *i;
  }
  //  buf << ends;
  ret_string = buf.str();
  return ret_string;
}


string OID::toString() const
{
  ostringstream buf;

  buf << "oid: " << (string) *this << ends;

  return string(buf.str());
}

OID::operator string() const {
    return toDisplayString();
}

void
OID::set(unsigned int *init, int initlen)
{
  EraseData();
  
  for(int i=0; i < initlen; i++) {
    values.push_back(init[i]);
  }
}
 

// compare: compares 2 oids for legigraphical ordering.  Specifically,
// when two oids are compared each segment get_s compared until a
// difference is found.  The OID with the lower segment number is
// considered less than the other.  If the two oids are exact up to
// the smallest length between the two of them then the shorter oid is
// considered less than the longer one.

// returns -1 (LESS_THAN)    if this  < other
// returns  0 (EQUAL_TO)     if this == other
// returns  1 (GREATER_THAN) if this  > other
oid_compare_result
OID::compare(const OID &other) const
{
  list<unsigned int>::const_iterator o1, o2;
  
  for(o1 = values.begin(), o2 = other.values.begin(); 
      o1 != values.end() && o2 != other.values.end(); 
      o1++, o2++) {
    if (*o1 < *o2)
      return LESS_THAN;
    else if (*o1 > *o2)
      return GREATER_THAN;
  }
  
  if (o2 == other.values.end() && o1 == values.end())
    return EQUAL_TO;

  if (o1 == values.end())
    return LESS_THAN;

  return GREATER_THAN;
}

oid_compare_result
OID::mincompare(const OID &other, int complength) const {
  list<unsigned int>::const_iterator o1, o2;
  int num;
  
  for(o1 = values.begin(), o2 = other.values.begin(), num = 0; 
      o1 != values.end() && o2 != other.values.end(); 
      o1++, o2++, num++) {
    if (*o1 < *o2)
      return LESS_THAN;
    else if (*o1 > *o2)
      return GREATER_THAN;
  }
  if (complength == 0 || complength <= num)
    return EQUAL_TO;
  else if (o1 == values.end())
    return LESS_THAN;
  else
    return GREATER_THAN;
}

oid_compare_result
OID::mincompare(const OID &other, const unsigned char *mask) const {
  // Currently this is pretty much only used by the VACM, but could be
  // used by more in the future.
  list<unsigned int>::const_iterator o1, o2;
  int num;
  
  for(o1 = values.begin(), o2 = other.values.begin(), num = 0; 
      o1 != values.end() && o2 != other.values.end(); 
      o1++, o2++, num++) {
      if (mask[num/8] & (0x01 << num%8)) {
          if (*o1 < *o2)
              return LESS_THAN;
          else if (*o1 > *o2)
              return GREATER_THAN;
      }
  }
  return EQUAL_TO;
}

bool
OID::operator < (const OID &other) const
{
  if (compare(other) == LESS_THAN)
    return true;
  return false;
}

bool
OID::operator > (const OID &other) const
{
  if (compare(other) == GREATER_THAN)
    return true;
  return false;
}

bool
OID::operator == (const OID &other) const
{
  if (compare(other) == EQUAL_TO)
    return true;
  return false;
}

OID
OID::operator + (const OID &other)
{
    OID x(*this);
    x.values.insert(x.values.end(), other.values.begin(), other.values.end());
    return x;
}

OID&
OID::operator += (const OID &other)
{
    values.insert(values.end(), other.values.begin(), other.values.end());
    return *this;
}

unsigned int &
OID::operator[] (unsigned int arg) {
    list<unsigned int>::iterator i;

    if (arg >= values.size())
        throw asnUsageError("OID[] array index argument too large");

    for(i = values.begin(); i != values.end() && arg > 0; i++, arg--);
    return *i;
}

OID &
OID::change_value(const asnDataType &right) {
    values = (dynamic_cast<const OID &>(right)).values;
    return *this;
}

void
OID::append(const unsigned int val) {
    values.push_back(val);
}

void
OID::append(const OID &val) {
    values.insert(values.end(), val.begin(), val.end());
}

OID &
OID::crop(unsigned int begin, unsigned int end) {
    list<unsigned int>::iterator li;
    list<unsigned int>::iterator begini = values.begin(), endi = values.end();
    unsigned int i;
    for(li = values.begin(), i = 1;
        li != values.end(); 
        li++, i++) {
        if (i == begin)
            begini = li;
        if (end != 0 && i-1 == end)
            endi = li;
    }
    crop(begini, endi);
    return *this;
}

OID &
OID::crop(OID::iterator &begin, OID::iterator &end) {
    if (begin != values.begin())
        values.erase(values.begin(), begin);
    if (end != values.end())
        values.erase(end, values.end());
    return *this;
}

OID&
OID::appendOID(OID &to, bool implied) {
    if (!implied)
        to.append(length());
    to += *this;
    return to;
}

unsigned int *
OID::array() {
    static unsigned int ret[MAX_OID_LEN];

    return array(ret);
}

unsigned int *
OID::array(unsigned int *ret) const {
    OID::const_iterator li;
    int i;

    for(li = values.begin(), i = 0; li != values.end(); li++, i++) {
        ret[i] = *li;
    }
    return ret;
}


unsigned int
OID::size() const {
    return values.size();
}

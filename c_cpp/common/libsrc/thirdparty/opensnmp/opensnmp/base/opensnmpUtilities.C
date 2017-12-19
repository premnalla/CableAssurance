#include "config.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <map>
#include <stdio.h>

#include "opensnmpUtilities.H"

using namespace std;

bool
OPENSNMP_UTILITIES::compareIgnoreCase(const string& s1, const string& s2) {

    string::const_iterator si1=s1.begin();
    string::const_iterator si2=s2.begin();

    while ( si1 != s1.end() && si2 != s2.end() )  {
      if ( toupper(*si1) != toupper(*si2) )  return false;
      ++si1;
      ++si2;
    }

    if (si1==s1.end()) return (si2 == s2.end());

    return false;
}

bool
OPENSNMP_UTILITIES::compareIgnoreCase(const string& s1, const char * s2) {

    string::const_iterator si1=s1.begin();
    int  s2end = strlen(s2), si2 = 0;

    while ( (si1 != s1.end()) && (si2 < s2end) ) {
      if ( toupper(*si1) != toupper(s2[si2]) )  return false; 
      ++si1;
      ++si2;
    }

    if (si1==s1.end()) return ( si2 == s2end );

    return false;
}

bool
OPENSNMP_UTILITIES::comparePrefix(const string& prefix, const string& s1) {
    string::const_iterator PI=prefix.begin();
    string::const_iterator SI=s1.begin();

    while ( (PI != prefix.end()) && 
	    (SI != s1.end()) && 
	    (*PI == *SI) )  {
	++PI;
	++SI;
    }

    if (PI == prefix.end()) {
	return true;
    } else { 
	return false;
    }
}

string
OPENSNMP_UTILITIES::intToString(const int val) {
  char buf[64];
  sprintf(buf, "%d", val);
  string s = buf;
  return s;
}

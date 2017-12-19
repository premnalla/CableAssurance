#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include <cstdlib>
#include <map>
#include <fstream>
#include <syslog.h>
#include <sstream>
#include <sys/time.h>
#include "debug.H"

using namespace std;

bool   opensnmpdebug::initialized   = false;
// string opensnmpdebug::debugTags[MAXTAGS];
bool   opensnmpdebug::debugAll      = false;
int    opensnmpdebug::debugAllLevel = 0;
bool   opensnmpdebug::debugOff      = false;
bool   opensnmpdebug::timestampOn   = false;
bool   opensnmpdebug::utimestampOn  = false;
multimap< string, opensnmpdebug::debug_params, 
	  less_prefix_nocase > opensnmpdebug::tagMap;
//	  opensnmpdebug::less_prefix_nocase > opensnmpdebug::tagMap;
typedef multimap< string, opensnmpdebug::debug_params, 
		  less_prefix_nocase >::const_iterator MI;
snmpMutexObj    opensnmpdebug::debugPrintMutex;


// using method for debugAllDest's static access and following
string *opensnmpdebug::hiddenDebugAllDest;
// string opensnmpdebug::debugAllDest  = string("stdout");


// does a case-insensitive, prefix match, 
// (AKA case-insensitiv partial prefix less than)
// e.g., string "abc" matches tag "ABCDEF"
//       "abcc" does not match tag "ABCDEF"
bool
less_prefix_nocase::operator()(const string& t1,const string& t2) const {
      
  string::const_iterator TI1=t1.begin();
  string::const_iterator TI2=t2.begin();
      
  while (TI1 != t1.end() && TI2 != t2.end()) {
    if (toupper(*TI1)==toupper(*TI2)) {
      ++TI1;
      ++TI2;
    }
    else if (toupper(*TI1) < toupper(*TI2)) {
      return true;
    }
    else { // toupper(*TI1) > toupper(*TI2)
      return false;
    }
  } 
  // this would be true for non-prefix only matching !!
  return false;
} // less_prefix_nocase


opensnmpdebug::opensnmpdebug() {
  // yes, perhaps this is ironic or just weird, but it may work.
  if (!opensnmpdebug::initialized) {
    opensnmpdebug::debugPrintMutex.lock("opensnmpdebug constructor");
    if (!opensnmpdebug::initialized) {
      opensnmpdebug::initialized   = true;
      //      opensnmpdebug::debugAll      = false;
      //      opensnmpdebug::debugAllLevel = 0;
      //      opensnmpdebug::debugAllDest  = string("stdout");
      opensnmpdebug::debugOff      = false;
      opensnmpdebug::timestampOn   = false;
      opensnmpdebug::utimestampOn  = false;
    }
    opensnmpdebug::debugPrintMutex.unlock("opensnpmdebug constructor");
  }
    
  tag = string("");
  int dummy = tagstack.size();
}


// create a debug object with tag 'tag_in'
opensnmpdebug::opensnmpdebug(const string &tag_in) {
    this->tag = tag_in;

}


opensnmpdebug::~opensnmpdebug() {
}


// This mechanism for accessing a static string is used used
// because/ of the multi-threaded static initialization problems.
// I.E., a segmentation fault caused by accessing a non-atomic (not
// a int, bool, etc... but a string or other user defined class)
// static value before it is initialized.  I can't decide if it's
// hacky or not, but it appears, unfortunately, to be neccessary.

string &
opensnmpdebug::debugAllDest() {
  // ACTUAL GLOBAL STORAGE
  static string *debugAllDestSString = new string("stdout");

  opensnmpdebug::hiddenDebugAllDest = debugAllDestSString;

  return *opensnmpdebug::hiddenDebugAllDest;
}

// sets and returns debugAllDest via hiddenDebugAllDest, sigh
string &
opensnmpdebug::debugAllDest(const string &newVal) {
  // make sure it is initialized.
  opensnmpdebug::debugAllDest();
  // set
  *opensnmpdebug::hiddenDebugAllDest = newVal;

  return *opensnmpdebug::hiddenDebugAllDest;
}


// turn off/on debuging output programmatically
void
opensnmpdebug::quiet(bool isquiet) {
  opensnmpdebug::debugPrintMutex.lock("quiet:");
  opensnmpdebug::debugOff = isquiet;
  opensnmpdebug::debugPrintMutex.unlock("quiet:");
}


// if a debug statement would print for the current settings, return
// true.  For example, this method could be used to set off a loop
// that prints debugging output so that the loop processing could be
// ignored if debugging was not turned on for this object and level.
bool
opensnmpdebug::doIf(int level) {
  bool retValue = false;
 
  // lock for static object access
  this->debugPrintMutex.lock("doIf:");

  // if this is an error message (i.e. level == 0) 
  // or if debugAll is on (at the appropriate level) return true
  if ( (level == 0) ||
       ( (opensnmpdebug::debugAll) &&
	 (opensnmpdebug::debugAllLevel >= level) ) )  {
    retValue = true;
  } else {
    // check for any other matches
    for (MI tag_tmp = opensnmpdebug::tagMap.lower_bound(tag); 
	 tag_tmp != opensnmpdebug::tagMap.upper_bound(tag); tag_tmp++) {
      // check level
      if (tag_tmp->second.level >= level)  {
	retValue = true;
	break;
      }
    }
  }
  
  this->debugPrintMutex.unlock("doIf:");
  return retValue;
}  // doIf


void
opensnmpdebug::doTimestamp() {
  opensnmpdebug::debugPrintMutex.lock("doTimestamp:");
  opensnmpdebug::timestampOn = true;
  opensnmpdebug::debugPrintMutex.unlock("doTimestamp:");
}


void
opensnmpdebug::doUTimestamp() {
  opensnmpdebug::debugPrintMutex.lock("doUTimestamp:");
  opensnmpdebug::utimestampOn = true;
  opensnmpdebug::debugPrintMutex.unlock("doUTimestamp:");
}


// set_tagLevelDest sets the a tag level (or the global all tag
// level) and destination.  The level is set to 'l' and the
// destination to '&lf' for a given tag '&t' (or the global tag for
// a 't' of "all" or "ALL").
void
opensnmpdebug::set_tagLevelDest(const string &t, int l, const string &lf) {
  opensnmpdebug::debugPrintMutex.lock("set_tagLevelDest:");
  if( (t == "all") || (t == "ALL") ){
    opensnmpdebug::debugAll = true;
    opensnmpdebug::debugAllLevel = l;
    opensnmpdebug::debugAllDest(lf);
  }
  else {
    opensnmpdebug::tagMap.insert(pair<string,debug_params>
				 (t,debug_params(l, lf)));
  }
  opensnmpdebug::debugPrintMutex.unlock("set_tagLevelDest:");
}  // set_tagLevelDest


// Return the currently set destination for a given tag,level
// comibination.
string
opensnmpdebug::get_Dest(const string &tag, int level) {
  // only used for debugging purposes at the moment
  string retValue = string("");
  opensnmpdebug::debugPrintMutex.lock("get_Dest:");

  for (MI tag_tmp = opensnmpdebug::tagMap.lower_bound(tag); 
       tag_tmp != opensnmpdebug::tagMap.upper_bound(tag); ++tag_tmp) {
    retValue = tag_tmp->second.destination;
  }

  opensnmpdebug::debugPrintMutex.unlock("get_Dest:");
  return retValue;
}  // get_Dest


// print is usually called via Macros in debug.H.  The macro's used
// by the procedures and classes that use the debug class.
//
// NOTE: The Macro definition also include a mutex lock section for
// this method (and thus also the method do_print used by this
// method).  A mutex lock is basically required for access to the
// static objects in this class (particularly string objects)

void opensnmpdebug::print(int level, ostringstream & osmsg) {
  bool   printed      = false;
  string printed_dest = string("");
  string msg = osmsg.str();

  // if this is an error message (i.e. level == 0) always print
  // to stderr.
  if (level == 0) {
    do_print(msg,"stderr");
  }

  // if debug all is set.
  if (opensnmpdebug::debugAll) {
    // if it's an error message (level = 0) and the command line didn't
    // explicitly direct error messages, ignore.
    if ( (level == 0) && (opensnmpdebug::debugAllLevel != 0) ) {
    }
    else if ( opensnmpdebug::debugAllLevel >= level) {
      do_print(msg, opensnmpdebug::debugAllDest());
      printed      = true;
      printed_dest = opensnmpdebug::debugAllDest();
    }
  }

  
  // check for any other matches
  // Note: you can get double prints with debugAll set to one level
  // and another tag that exists. But you can also print all values
  // less than some number, and some specific tag values less than
  // a different (higher) number. Go wild.
  multimap< string, opensnmpdebug::debug_params, 
    less_prefix_nocase >::const_iterator tag_tmp;

  if (0 < opensnmpdebug::tagMap.size()) {
    for (tag_tmp = opensnmpdebug::tagMap.lower_bound(tag); 
	 tag_tmp != opensnmpdebug::tagMap.upper_bound(tag); ++tag_tmp) {
      // check level
      // if it's an error message (level = 0) and the command line didn't
      // explicitly direct error messages, ignore.
      if ( (level == 0) && (tag_tmp->second.level != 0) ) {
      }
      else if ( tag_tmp->second.level >= level) {
	if ( (!printed) || (printed_dest != tag_tmp->second.destination) ) {
	  do_print(msg, tag_tmp->second.destination);
	  printed = true;
	  printed_dest = tag_tmp->second.destination;
	}
      }
    }
  }

} // opensnmpdebug::print


// do_print is called by the print method in this class.  
//
// NOTE: The Macro definition that calls the method before this
// (i.e. the opensnmpdebug::print) include a mutex lock section for
// 'print' and thus, this method as well.  A mutex lock is basically
// required for access to the static objects in this class
// (particularly string objects)

void opensnmpdebug::do_print(string & msg, 
			     const string & logStringName) {
// print msg to logStringName and stdout
  
    char *timestamp = NULL;
    if (opensnmpdebug::timestampOn) {
         time_t Now;
         time(&Now);
	 timestamp = ctime(&Now);
    }
    long lsecs = 0, lusecs = 0;
    if (opensnmpdebug::utimestampOn) {
         timeval  tmNow;
         gettimeofday(&tmNow, NULL);
	 lsecs  = tmNow.tv_sec;
	 lusecs = tmNow.tv_usec;
    }

    //    debugPrintMutex.lock("opensnmp::do_print");

    if (logStringName == "stdout") {
	  if (opensnmpdebug::timestampOn)
	    cout << timestamp;
	  if (opensnmpdebug::utimestampOn)
	    cout << "secs:" << lsecs << " usecs:" << lusecs << endl;
	  cout << this->tag << ": " << msg << endl;
    } else if (logStringName == "stderr") {
	  if (opensnmpdebug::timestampOn)
	    cerr << timestamp;
	  if (opensnmpdebug::utimestampOn)
	    cerr << "secs:" << lsecs << " usecs:" << lusecs << endl;
	  cerr << this->tag << ": " << msg << endl;
    } else if (logStringName == "syslog") {
	  // print to syslog and stdout
	  string logstr = this->tag + ": " + msg;
	  cout << logstr << endl;
	  openlog("snmpV3", LOG_CONS|LOG_PID, LOG_DAEMON);
	  syslog(LOG_ERR,logstr.c_str());
	  closelog();
    } else { // must be a filename
	  // print to file and stdout
	  if (opensnmpdebug::timestampOn)
	    cout << timestamp;
	  if (opensnmpdebug::utimestampOn)
	    cout << "secs:" << lsecs << " usecs:" << lusecs << endl;
	  cout << this->tag << ": " << msg << endl;
	  
	  const char *logName = logStringName.c_str();
	  ofstream logfile(logName,ios::app);
	  if (!logfile) {
		cerr << "opensnmpdebug::do_print: cannot open output file " \
		     << logName << endl;
	  } else {
	        logfile << timestamp;
	        if (opensnmpdebug::utimestampOn)
		  logfile << "secs:" << lsecs << " usecs:" << lusecs << endl;
		logfile << this->tag << ": " << msg << endl;
		logfile.flush();
		logfile.close();
	  }
    }

    //    debugPrintMutex.unlock("opensnmp::do_print");

}  // do_print


bool
opensnmpdebug::matchTags (const string & t1) {
// does a case-insensitive, possibly partial match
// e.g., this->tag = abcdef matches a tag "abc" from the tagMap

    const string &t2 = this->tag;
    string::const_iterator TI1=t1.begin();
    string::const_iterator TI2=t2.begin();

    while (TI1 != t1.end() && 
	   TI2 != t2.end() && 
	   toupper(*TI1)==toupper(*TI2)) {
	++TI1;
	++TI2;
    }

    if (TI1==t1.end()) {
	return true;
    } else { 
	return false;
    }

}  // matchTags


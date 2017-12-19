#ifndef SNMPDEBUG_H
#define SNMPDEBUG_H

#include <iostream>
#include <string>
#include <cstdlib>
#include <fstream>
#include <sstream>
#include <map>
#include <stack>
#include "snmpMutexObj.H"

#define MAXTAGS 5
#define MAXTAGLENGTH 10

#ifndef NODEBUGGING
#define DEBUGDECLARE(OBJNAME) opensnmpdebug *OBJNAME
#define DEBUGCREATE(OBJNAME, TAG) OBJNAME = new opensnmpdebug(TAG)
#define DEBUGDESTROY(OBJNAME) delete OBJNAME
#define DEBUGNEWTAG(OBJ, TAG) OBJ->tag = TAG

/*
 * old macros:
 */

/*

  #define DEBUGPUSHTAG(OBJ, TAG) do {\
      OBJ->tagstack.push(OBJ->tag); \
      OBJ->tag = TAG; \
      } \
      while(0)

  #define DEBUGPOPTAG(OBJ) \
      if (OBJ->tagstack.size() > 0) { \
         OBJ->tag = OBJ->tagstack.top(); \
         OBJ->tagstack.pop(); \
       }
     } 
     while(0)

*/


#define DEBUGINIT(OBJNAME, TAG) opensnmpdebug *OBJNAME = new opensnmpdebug(TAG)

// local scope debug obj
#define DEBUGDECLARE_L(OBJNAME) opensnmpdebug  OBJNAME
#define DEBUGNEWTAG_L(OBJ, TAG) OBJ.tag = TAG
#define DEBUGCREATE_L(OBJNAME, TAG) opensnmpdebug OBJNAME(TAG)

#define DEBUGN(LEV,OBJ,data) \
    if ((LEV == 0) || (!OBJ->debugOff)) { \
      OBJ->debugPrintMutex.lock("Misc debug obj"); \
      std::ostringstream tmp_debug_buf_192837465_zseqsc; \
      tmp_debug_buf_192837465_zseqsc << data << std::ends; \
      OBJ->print(LEV,tmp_debug_buf_192837465_zseqsc); \
      OBJ->debugPrintMutex.unlock("Misc debug obj"); \
    }
//    while(0)

#define DEBUGN_L(LEV,OBJ,data) \
    if ((LEV == 0) || (!OBJ.debugOff)) { \
      OBJ.debugPrintMutex.lock("Misc debug local obj"); \
      std::ostringstream tmp_debug_buf_192837465_zseqsc; \
      tmp_debug_buf_192837465_zseqsc << data << std::ends; \
      OBJ.print(LEV,tmp_debug_buf_192837465_zseqsc); \
      OBJ.debugPrintMutex.unlock("Misc debug local obj"); \
    }
//    while(0)

#define DEBUG0(OBJ,data) DEBUGN(0,OBJ,data)
#define DEBUG1(OBJ,data) DEBUGN(1,OBJ,data)
#define DEBUG5(OBJ,data) DEBUGN(5,OBJ,data)
#define DEBUG9(OBJ,data) DEBUGN(9,OBJ,data)
#define DEBUGIF(OBJ,LEV) OBJ->doIf(LEV)

#define DEBUG0_L(OBJ,data) DEBUGN_L(0,OBJ,data)
#define DEBUG1_L(OBJ,data) DEBUGN_L(1,OBJ,data)
#define DEBUG5_L(OBJ,data) DEBUGN_L(5,OBJ,data)
#define DEBUG9_L(OBJ,data) DEBUGN_L(9,OBJ,data)
#define DEBUGIF_L(OBJ,LEV) OBJ.doIf(LEV)

#else /* NODEBUGGING */

#define DEBUGDECLARE(OBJNAME)
#define DEBUGCREATE(OBJNAME, TAG)
#define DEBUGDESTROY(OBJNAME)
#define DEBUGNEWTAG(OBJ, TAG)
#define DEBUGINIT(OBJNAME, TAG)
#define DEBUGPUSHTAG(OBJ, TAG)
#define DEBUGPOPTAG(OBJ)

// local scope debug obj
#define DEBUGDECLARE_L(OBJNAME)
#define DEBUGNEWTAG_L(OBJ, TAG)
#define DEBUGCREATE_L(OBJNAME, TAG)

#define DEBUGN(LEV,OBJ,data)
#define DEBUGN_L(LEV,OBJ,data)
#define DEBUG0(OBJ,data)
#define DEBUG1(OBJ,data)
#define DEBUG5(OBJ,data)
#define DEBUG9(OBJ,data)
#define DEBUGIF(OBJ,LEV) 0

#define DEBUG0_L(OBJ,data)
#define DEBUG1_L(OBJ,data)
#define DEBUG5_L(OBJ,data)
#define DEBUG9_L(OBJ,data)
#define DEBUGIF_L(OBJ,LEV) 0

#endif

#define DEBUG_ERR_WARN_FLOW_DETAIL(OBJ) \
  void err(string st_DEWFD_E)    { DEBUG0(OBJ, st_DEWFD_E); } \
  void warn(string st_DEWFD_W)   { DEBUG1(OBJ, st_DEWFD_W); } \
  void flow(string st_DEWFD_F)   { DEBUG5(OBJ, st_DEWFD_F); } \
  void detail(string st_DEWFD_D) { DEBUG9(OBJ, st_DEWFD_D); } 

/* old definitions:

   void err(std::ostringstream os_DEWFD_E)    { DEBUG0(OBJ, os_DEWFD_E); } \
   void warn(std::ostringstream os_DEWFD_W)   { DEBUG1(OBJ, os_DEWFD_W); } \
   void flow(std::ostringstream os_DEWFD_F)   { DEBUG5(OBJ, os_DEWFD_F); } \
   void detail(std::ostringstream os_DEWFD_D) { DEBUG9(OBJ, os_DEWFD_D); } \

*/


using std::string;

class less_prefix_nocase {
public:
  bool operator()(const string& t1,const string& t2) const;
  //     bool
  //     less_prefix_nocase::operator()(const string & t1,const string & t2)  {
  //       // does a case-insensitive, partial prefix, less than
  //       // e.g., string "abc" is not less than tag "ABCDEF" (i.e. their equal)
      
  //       string::const_iterator TI1=t1.begin();
  //       string::const_iterator TI2=t2.begin();
      
  //       while (TI1 != t1.end() && TI2 != t2.end()) {
  // 	if (toupper(*TI1)==toupper(*TI2)) {
  // 	  ++TI1;
  // 	  ++TI2;
  // 	}
  // 	else if (toupper(*TI1) < toupper(*TI2)) {
  // 	  return true;
  // 	}
  // 	else { // toupper(*TI1) > toupper(*TI2)
  // 	  return false;
  // 	}
  //       } 
  //       // this would be true for non-prefix only matching !!
  //       return false;
  //     } // less_prefix_nocase
};

class opensnmpdebug {

private:
  void do_print(std::string &, const string &);
  static string *hiddenDebugAllDest;
  
public:
  static snmpMutexObj  debugPrintMutex;

  class debug_params {
  public:
    int    level;
    string destination;

    debug_params(int lev, string theDest) : 
      level(lev), destination(theDest) {
    };
  };

public:
  
  opensnmpdebug();
  opensnmpdebug(const string &);
  ~opensnmpdebug();
  
  string tag;
  std::stack<string> tagstack;

  // methods
  void print(int, std::ostringstream&);
  static void quiet(bool isquiet = true);
  bool doIf(int);
  bool matchTags(const string&);
  static void doTimestamp();
  static void doUTimestamp();
  static void set_tagLevelDest(const string &, int,
			       const string &lf = "stdout");
  static string get_Dest(const string&,int);

  // master tags and level to compare incomings to
  static bool   initialized;
  //  static string debugTags[];
  static bool   debugAll;
  static int    debugAllLevel;
  //  static string debugAllDest; // now using following
  // returns debugAllDest, sets if newVal is not empty string
  static  string       &debugAllDest();
  static  string       &debugAllDest(const string &newVal);

  static bool   debugOff;
  static bool   timestampOn;
  static bool   utimestampOn;
  static std::multimap< string, debug_params, less_prefix_nocase > tagMap;

};

#endif /* SNMPDEBUG_H ifndef */

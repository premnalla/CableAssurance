#include "config.h"
#include "debug.H"
#include "cfgDebug.H"

DebugOptions::DebugOptions( void )
{
}

DebugOptions::~DebugOptions( void )
{
}

int
DebugOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "-D", (void*)'D', 2, 3 );
  mgr.register_option( this, "--debug", (void*)'d', 2, 3 );
  mgr.register_option( this, "--debug-timestamp", (void*)'t', 0, 0 );
  mgr.register_option( this, "--debug-utimestamp", (void*)'T', 0, 0 );
  mgr.register_option( this, "--debug-quiet", (void*)'q', 0, 0 );

  return 0; // success
}

int
DebugOptions::option_callback( const string & token, void* data,
			       std::deque<string> & parms )
{
  if( data == (void*)'t' ) {
    opensnmpdebug::doTimestamp();
    return 0; // success
  }
  else if( data == (void*)'T' ) {
    opensnmpdebug::doUTimestamp();
    return 0; // success
  }
  else if( data == (void*)'q' ) {
    opensnmpdebug::quiet();
    return 0;
  }

  int tagcount, debugLevel;
  char *debugTags, *tag, *strtok_info;
  string destination, tagslist[MAXTAGS];
  tagcount = 0;

  std::deque<string>::iterator it = parms.begin();

  debugLevel = atoi( (*it++).c_str() );
  debugTags = strdup( (*it++).c_str() );
    
  // parse debugTags, it may be comma-separated
  // list of tags or a single tag
  
  if (NULL != (tag = strtok_r(debugTags,",",&strtok_info))) {
    tagslist[tagcount++]=tag;
    while ((NULL != (tag = strtok_r(NULL,",",&strtok_info))) && 
	   tagcount<MAXTAGS) {
      tagslist[tagcount++] = tag;
    }
  }
    
  // check for destination filename, if there is one

  if ( it == parms.end() ) {
    destination = "stdout";
  } else {
    destination = (*it).c_str();
  }
  
  // if any tags are found, turn debug output on.
  if (tagcount > 0) opensnmpdebug::quiet(false);

  // for each tag in tagslist, set level and destination  
  while (tagcount > 0) {
    tagcount--;
    opensnmpdebug::set_tagLevelDest(tagslist[tagcount],debugLevel,
				    destination);
  }  

  free( debugTags );

  return 0; // success
}

void
DebugOptions::option_help( const string &, void* data, std::ostream& os )
{
  if( data == (void*)'t' ) {
    os << "    : add timestamp to debug messages" << std::endl;
  }
  else if( data == (void*)'q' ) {
    os << ": debug quiet mode" << std::endl;
  }
  else
  os <<
    " level (tag+|ALL) [outfile]\n" <<
    "                    : level: 0-99 integer. 99 shows the most output.\n" <<
    "                    : tag+: comma delimited list of one+ tag strings\n" <<
    "                      you wish to view. They include: USM,MP3,DISP,\n" <<
    "                      NET,CG,CR,persist,...\n" <<
    "                    : outfile: file to send debug info to, it\n" <<
    "                      defaults to standard out.\n";
}

#include "config.h"
#include <assert.h>

#include "mib.H"
#include "mibManager.H"

LIBSMI * MibManager::libsmiptr = new LIBSMI();


MibManager::MibManager( void )
{
  DEBUGCREATE(debugObj, "MibManager");
  flow( "MibManager::MibManager()" );
}

MibManager::~MibManager( void )
{
#ifndef NODEBUGGING
  if( debugObj != NULL )
    delete debugObj;
#endif
}

MibManager&
MibManager::instance( void )
{
  static MibManager theMibManager;
  assert( libsmiptr != NULL );
  return theMibManager;
}

int
MibManager::load_modules( const string & modules )
{
  flow( "MibManager::load_modules()" );

  string remaining_modules = modules;
  string module;
  unsigned int sz, errs = 0;

  while(1) {
    if ((sz = remaining_modules.find(':')) != string::npos)
      module = remaining_modules.substr(0,sz);
    else
      module = remaining_modules;
    
    detail( "LOADING MIB:" + module );
    if (libsmiptr->loadModule(module).size() == 0) {
      err( "FAILED to load module " + module );
      ++errs;
    }
    
    if ((sz = remaining_modules.find(':')) == string::npos)
      break;
    
    remaining_modules = remaining_modules.substr(sz+1);
  }

  return errs;
}

void
MibManager::add_path( const string & path )
{
  libsmiptr->path( path );
}

bool
MibManager::is_loaded( const string & module )
{
  return libsmiptr->isLoaded( module );
}

string
MibManager::get_paths( void )
{
  return libsmiptr->path();
}

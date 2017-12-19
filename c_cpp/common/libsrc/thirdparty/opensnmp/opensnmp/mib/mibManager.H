#ifndef MIB_MANAGER_H
#define MIB_MANAGER_H

#include "LIBSMI.H"
#include "debug.H"

//
// the MibManager class is a singleton that is responsible for
// loading MIBs using the libsmi library.
//
class MibManager {

public:

  ~MibManager( void );

  // instance:
  //   returns a reference to the instance of the singleton
  static MibManager& instance( void );

  // is_loaded:
  //  returns true if the specified module has been loaded,
  //  otherwise returns false;
  bool is_loaded( const string & module );

  // load_modules:
  //  attempts to load the specified modules. module names should
  //  be seperated by a colon (':') character. The number of
  //  modules which could not be loaded is returned.
  int load_modules( const string & modules );

  // get_paths:
  //  returns the colon (':') seperated list of paths that will
  //  be used to search for modules.
  string get_paths( void );

  // add_paths:
  //  adds the specified paths to the search path for mib modules.
  //  multiple paths should be seperate by a colon (':') character.
  //  if the first character is a '+', the paths will be appended
  //  to the current path. Otherwise the new path will replace the
  //  old path.
  void add_path( const string & path );


protected:

  MibManager( void );

private:

  MibManager& operator=( const MibManager& );
  MibManager( const MibManager& );

  static LIBSMI *libsmiptr;

  DEBUGDECLARE(debugObj);
  DEBUG_ERR_WARN_FLOW_DETAIL(debugObj);


};

#endif // MIB_MANAGER_H

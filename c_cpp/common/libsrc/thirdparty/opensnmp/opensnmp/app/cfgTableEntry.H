#ifndef TABLE_ENTRY_OPTIONS_H
#define TABLE_ENTRY_OPTIONS_H


#include "configOption.H"


class TableEntryOptions : public ConfigOptions {

public:

  TableEntryOptions( void );
  ~TableEntryOptions( void );

  virtual int registration_callback( ConfigMgr & );

  virtual int option_callback( const string & token, void * userData,
			       std::deque<string> & parms );
  virtual void option_help( const string & token, void * userData,
			    std::ostream& os );

private:

  TableEntryOptions( const TableEntryOptions & ); // not implemented
  TableEntryOptions & operator=( const TableEntryOptions & ); // not implemented
};


#endif // TABLE_ENTRY_OPTIONS_H

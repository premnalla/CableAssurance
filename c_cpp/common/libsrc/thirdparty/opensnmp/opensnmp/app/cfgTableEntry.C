#include "config.h"
#include <iostream>

#include "db_cxx.h"

#include "cfgTableEntry.H"

#include "snmpDatabaseObj.H"
#include "VarBindList.H"
#include "Sequence.H"
#include "OID.H"
#include "OctetString.H"
#include "Integer32.H"

TableEntryOptions::TableEntryOptions( void )
{
}

TableEntryOptions::~TableEntryOptions( void )
{
}

int
TableEntryOptions::registration_callback( ConfigMgr & mgr )
{
  mgr.register_option( this, "Table_Entry", (void*)1 );

  return 0; // success
}

// # Table_Entry { <table-oid> <table-index> { <column> <type> <value> } }

int
TableEntryOptions::option_callback( const string & /*token*/, void* /*data*/,
				    std::deque<string> & parms )
{
  if (parms.size() < 8) {
    cerr << "Table_Entry: not enough tokens" << endl;
    cerr << "    Table_Entry { <table-oid> <table-index> { <column> <type> <value> } }" << endl;
    return 1;
  }

  unsigned int index = 0;

  // start with table name and index
  OID table_oid( parms[ index++ ] );
  OID table_index( parms[ index++ ] );

  // build key
  OID * key = new OID( table_oid );
  key->append( 1 ); // add entry
  key->append( 0 ); // add dummy column
  key->append( table_index ); // add index
  
  Sequence * seq = new Sequence();

  //
  // add columns while we can
  while( (index+5) <= parms.size() ) {

    // need '{'
    if( ( parms[ index ].size() != 1 ) || ( parms[ index ][0] != '{' ) ) {
      cerr << "Table_Entry: no opening '{' for column data" << endl;
      return 2;
    }
    ++index;

    // if column is specified as entire OID, make sure it has the same
    // prefix as table
    int col;
    OID column_oid( parms[ index++ ] );
    if( column_oid.length() > 1 ) {
      if( column_oid.mincompare( table_oid, table_oid.length() ) != 0 ) {
	cerr << "Table_Entry: column is not part of table" << endl;
	return 3;
      }
      col = column_oid[ table_oid.length() + 1 ];
    }
    else
      col = column_oid[ 0 ];
    seq->Add( new Integer32( col ) );

    // check type
    if (parms[ index ].length() != 1 ) {
      cerr << "Table_Entry: <type> must be one of <s|i|o|x|c>" << endl;
      return 4;
    }

    switch( parms[ index++ ][0]) {

    case 's': {
      string tmp = parms[ index++ ];
      for( ; index < parms.size(); ++index ) {
	if( ( parms[ index ].length() != 1 ) ||
	    ( parms[ index ][0] != '}' ) ) {
	  tmp += ' ';
	  tmp += parms[ index ];
	}
	else
	  break;
      }
      if( ( tmp.size() == 2 ) &&
	  ( tmp[0] == '"' ) && ( tmp[1] == '"' ) )
	tmp.erase();
      seq->Add( new OctetString( tmp ) );
    }
    break;
    
    case 'i':
      seq->Add( new Integer32( atoi( parms[ index++ ].c_str() ) ) );
      break;
      
    case 'o':
      seq->Add( new OID( parms[ index++ ] ) );
      break;
      
    case 'x': {
      OctetString * tmp = new OctetString();
      tmp->fromHexString( parms[ index++ ] );
      seq->Add( tmp );
    }
    break;
  
    case 'c' :
      seq->Add( new Integer32( atoi( parms[ index++ ].c_str() ) ) );
      break;
      
    default:
      cerr << "Table_Entry: unknown flag '" << parms[ index-1 ] << "'"
	   << endl;
      return 5;
    }
    ++index; // skip over column end brace
  }

  // did we use all the tokens?
  if( index != parms.size() ) {
    cerr << "Table_Entry: tokens left over" << endl;
    return 6;
  }

  VarBind vb( key, seq );
  
  try {
    snmpDatabaseObj*  db = snmpDatabaseObj::getSnmpDatabaseObj();
    
    db->update( vb );
  }
  catch ( DbException & dbe ) {
    cerr << "Database ERROR: " << dbe.what() << "\n";
    return 7;
  }
  catch (...) {
    cerr << "Table_Entry: caught unknown exception." << endl;
    return 8;
  }
  
  return 0; // success
}

void
TableEntryOptions::option_help( const string & /*token*/, void* /*data*/,
				std::ostream& os )
{
  os <<
    "    Table_Entry { <table-oid> <table-index> " <<
    "{ <column> <type> <value> } }"
    "    Table_Entry { <oid> <index> { <oid> <type> <value> } }" << endl <<
    "                    : <type> is one of [s|i|o|x|c]" << endl;
}

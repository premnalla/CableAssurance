#include "config.h"
#include "snmpRowManager.H"

using std::map;

snmpRowManager::snmpRowManager( map <int, bool> *setable_cols, //= NULL
				snmpRowAllocator *allocator)   //= NULL
{
  this->setable_cols = setable_cols;
  this->allocator = allocator;
}

snmpRowManager::~snmpRowManager( void )
{
  delete setable_cols;
  delete allocator;
}


//bool row_testset( const snmpRow & row, int colnum, VarBind * vb )
//    { if( vb->get_value(false) == NULL ||
//  	(row.get_column( colnum, false ) != NULL &&
//  	 row.get_column( colnum, false )->get_tag() !=
//  	 vb->get_value(false)->get_tag()) )
//      return false;
//    return true;
//    }


void snmpRowManager::row_changed( OID *regoid,
				  const snmpRowKey & rowKey,
				  map<int,VarBind*> *cols )
{
  return;
}

#ifndef SNMPROWMANAGER_H
#define SNMPROWMANAGER_H
// snmpRowManager.H

#include "snmpRowAllocator.H"

class snmpRowManager {

public:

  snmpRowManager( std::map <int, bool> *setable_cols = NULL,
		  snmpRowAllocator *allocator = NULL );
  virtual ~snmpRowManager( void );

  std::map <int, bool> *setable_cols;
  snmpRowAllocator *allocator;

  // xxx-rks: why not allow actions on all cases?
//  virtual bool row_testset( const snmpRow & row, int colnum, VarBind * vb );
//  virtual bool row_commit( const snmpRow & row, int colnum, VarBind * vb );
//  virtual bool row_undo( const snmpRow & row, int colnum, VarBind * vb );
//  virtual bool row_cleanup( const snmpRow & row, int colnum, VarBind * vb );

  // row_changed()
  //   reg    : MIBRegistration ptr (useful to get table OID)
  //   changed: the row that had one or more columns changed
  //   cols   : map of the incoming varbind (the new value)
  //
  //   - xxx-rks: if the set already happened, maybe the old data
  //              (undoInfo) might be more useful? Though the varbind
  //              would mean more in earlier AGENTX_* phases.
  virtual void row_changed( OID *regoid,
			    const snmpRowKey & rowKey,
			    std::map<int,VarBind*> *cols );

protected:

  // provide access to snmpRow protected stuff for derived classes
  std::map<int, asnDataType*> & get_row_data( snmpRow* row ) {
    return row->data;
  }
  
private:

  // default ctor, copy ctor and assignment not implemented
  snmpRowManager( void );
  snmpRowManager( const snmpRowManager & ref );
  snmpRowManager & operator =(const snmpRowManager & );
};

#endif // SNMPROWMANAGER_H

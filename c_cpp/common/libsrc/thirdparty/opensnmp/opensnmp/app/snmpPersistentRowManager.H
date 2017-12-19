#ifndef SNMPPERSISTENTROWMANAGER_H
#define SNMPPERSISTENTROWMANAGER_H

#include "snmpRowManager.H"

typedef std::map<int, int> columnStorageMap;

class snmpPersistentRowManager : public snmpRowManager {

public:

  snmpPersistentRowManager( int storageTypeCol,
			    std::map <int, int> *columnStorage,
			    std::map <int, bool> *setable_cols = NULL,
			    snmpRowAllocator *allocator = NULL);

  virtual ~snmpPersistentRowManager( void );

  // row_changed()
  //   override of snmpRowManger::row_changed() to save data
  virtual void row_changed( OID *regoid,
			    const snmpRowKey & rowKey,
			    std::map<int,VarBind*> *cols );

protected:

  int storageTypeCol; // column number for storage type var
  columnStorageMap *columnStorage;

private:
  
  // default ctor, copy ctor and assignment not implemented
  snmpPersistentRowManager( void );
  snmpPersistentRowManager( const snmpPersistentRowManager & ref );
  snmpPersistentRowManager & operator =(const snmpPersistentRowManager & );
};

#endif // SNMPPERSISTENTROWMANAGER_H

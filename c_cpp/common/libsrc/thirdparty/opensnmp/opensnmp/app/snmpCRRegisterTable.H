// snmpCRRegisterTable.H

#ifndef SNMPCRREGISTERTABLE_H
#define SNMPCRREGISTERTABLE_H

#include "snmpCRRegisterObj.H"
#include "snmpDataTable.H"
#include "snmpRowManager.H"

#define SNMP_CR_ARCH_NAME "CR"

class snmpCRRegisterTable : public snmpCRRegisterObj {
  
 private:
   snmpDataTable *table;
   unsigned int maxcolumn;
   unsigned int mincolumn;
   snmpDataTable::dataStorageType dSType;
   snmpRowManager *row_manager;

 public:
   snmpCRRegisterTable(snmpDataTable *table,
                       OID *registerAt,
                       OctetString *context,
                       unsigned int maxcolumn, 
                       unsigned int mincolumn = 1, 
                       snmpDataTable::dataStorageType dSType = 
                       snmpDataTable::READ_ONLY,
                       snmpRowManager *manager = NULL);
      
   virtual ~snmpCRRegisterTable();

   void set_table(snmpDataTable  *table);
   snmpDataTable  *get_table(bool extract=false);
   void set_rowManager(snmpRowManager  *rowManager);
   snmpRowManager  *get_rowManager(bool extract=false);
   void set_maxcolumn(unsigned int maxcolumn);
   unsigned int get_maxcolumn();
   void set_mincolumn(unsigned int mincolumn);
   unsigned int get_mincolumn();
   void set_dSType(snmpDataTable::dataStorageType dSType);
   snmpDataTable::dataStorageType get_dSType();
   

};

#endif /*  SNMPCRREGISTERTABLE_H  */

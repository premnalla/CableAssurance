#ifndef SNMPROWSTATUSCHECKCOLSEXIST_H
#define SNMPROWSTATUSCHECKCOLSEXIST_H

#include "snmpRowStatusCheck.H"

class snmpRowStatusCheckColsExist : public snmpRowStatusCheck {
   std::list<int> mustExistColumns;
 public:
   snmpRowStatusCheckColsExist(std::list <int> &initlist);
   snmpRowStatusCheckColsExist();
   snmpRowStatusCheckColsExist(const snmpRowStatusCheckColsExist & );
   snmpRowStatusCheckColsExist(const int &startcol, const int &endcol);
   virtual void add(int &newcol);
   virtual void add_range(int &startcol, int &endcol);
   virtual bool check_row(snmpRow *row);
   virtual snmpRowStatusCheckColsExist *clone() const {
     return new snmpRowStatusCheckColsExist( *this ); 
   };
};

#endif /* SNMPROWSTATUSCHECKCOLSEXIST_H */

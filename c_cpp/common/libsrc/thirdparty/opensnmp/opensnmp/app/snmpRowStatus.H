#ifndef SNMPROWSTATUS_H
#define SNMPROWSTATUS_H

#include "Integer32.H"
#include "snmpConstants.H"
#include "snmpProtoErr.H"
#include "snmpRow.H"
#include "snmpRowStatusCheck.H"
#include "snmpDataTable.H"
#include "snmpRowDependent.H"

class snmpRowStatus : public Integer32, public snmpRowDependent {
 private:
   snmpRowStatusCheck *check;
   
 public:
   snmpRowStatus();
   snmpRowStatus(const asnDataType &thecopy);
   snmpRowStatus(const Integer32 &thecopy);
   snmpRowStatus(const snmpRowStatus &thecopy);
   snmpRowStatus(int, snmpDataTable *table = NULL, snmpRow *row = NULL, 
                 snmpRowStatusCheck *check = NULL);
   snmpRowStatus(char *, snmpDataTable *table = NULL, snmpRow *row = NULL, 
                 snmpRowStatusCheck *check = NULL);
   virtual ~snmpRowStatus();

   virtual snmpRowStatus &change_value(const asnDataType &fromNum);

   // the change_value method is overridden to allow a new value
   // to be set that would not normally be allowed by change_value.
   virtual snmpRowStatus &override_value(const asnDataType &from);

   virtual snmpRowStatus *clone() const { return new snmpRowStatus(*this); };
   virtual int get_value() const;
   virtual bool check_row() const;
   virtual bool remove_self();

   virtual void set_check(snmpRowStatusCheck  *check);
   virtual snmpRowStatusCheck  *get_check(bool extract=false);
};

#endif /* SNMPROWSTATUS_H */

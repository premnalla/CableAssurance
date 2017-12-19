#ifndef SNMPSCALARDATA_H
#define SNMPSCALARDATA_H

#include "asnDataType.H"
#include "snmpProtoErr.H"

class snmpScalarData {
 protected:
   unsigned int max_col;
   unsigned int min_col;
   bool default_is_writable;
   
 public:
   snmpScalarData(unsigned int highcolnum, unsigned int lowcolnum = 1,
                  bool default_is_writable = false);

   // accessors that must be implemented by child class
   virtual asnDataType  *get_column(unsigned int num) const = 0;

   // default behaviour is to check for existence and writability and
   // to call oldval->check_new_value(newval).
   virtual snmpProtoErr *test_set(unsigned int num, const asnDataType *data) const;

   // defaults to returning a "not writable" or "no such name" error:
   // test_set() should be called first to make sure value is legal.
   virtual snmpProtoErr *set_column(unsigned int num, const asnDataType *data);

   // defaults to false
   virtual bool is_writable(unsigned int) const {
       return default_is_writable;
   }
   
   // accessors that may be implemented by the child class for better
   // efficiency, otherwise it does a linear search repeatedly calling
   // get_column().
   virtual asnDataType *get_nextColumn(unsigned int num, 
                                      unsigned int *newColumn) const;

   // local accessors
   virtual unsigned int get_max_col() const { return max_col; }
   virtual unsigned int get_min_col() const { return min_col; }
   virtual void set_max_col(unsigned int max_col) { 
       this->max_col = max_col;
   }
   
   virtual void set_min_col(unsigned int min_col) { 
       this->min_col = min_col; 
   }
};

#endif /* SNMPSCALARDATA_H */

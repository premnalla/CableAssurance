#ifndef ROWOBJECTCOLUMNALLOCATOR_H
#define ROWOBJECTCOLUMNALLOCATOR_H

#include "snmpRow.H"
#include "snmpColumnAllocator.H"

// snmpColumnAllocator for snmpRowDependent objects

template<class OBJTYPE>
class snmpRowObjectAllocator : public snmpColumnAllocator {
 private:
   OBJTYPE *data;
   
 public:

   snmpRowObjectAllocator( const snmpRowObjectAllocator& ref ) {
     this->data = ref.data->clone();
   }

   snmpRowObjectAllocator(OBJTYPE *data) {
       this->data = data;
   }

   virtual ~snmpRowObjectAllocator() {
       if (data)
           delete data;
   }
   
   virtual OBJTYPE *allocate(snmpRow *row) {
       if (data) {
           OBJTYPE *ret = data->clone();
           ret->set_row(row);
           return ret;
       } else
           return NULL;
   }

  virtual OBJTYPE *allocate(snmpRow *row,
			    const asnDataType & initialValue ) {
    OBJTYPE *ret = data->clone();
    ret->override_value( initialValue );
    ret->set_row(row);
    return ret;
  }

  virtual snmpRowObjectAllocator *clone() const {
    return new snmpRowObjectAllocator(*this);
  }
};

#endif /* ROWOBJECTCOLUMNALLOCATOR_H */

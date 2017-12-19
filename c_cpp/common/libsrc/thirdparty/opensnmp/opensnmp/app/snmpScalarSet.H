#ifndef SNMPSCALARSET_H
#define SNMPSCALARSET_H

#include "snmpScalarData.H"

class snmpScalarSet : public snmpScalarData  {
 protected:
   std::map <int, asnDataType *> dataset;
   std::map <int, bool> writableflags;

 public:
   snmpScalarSet();
   virtual ~snmpScalarSet();

   void set_data(unsigned int colnum, asnDataType *data,
                 bool iswritable = false);
   asnDataType *get_data(unsigned int colnum, bool extract = false);
   
   virtual asnDataType *get_column(unsigned int num) const;
   virtual snmpProtoErr *set_column(unsigned int num, const asnDataType *data);
   virtual bool is_writable(unsigned int colnum) const;
};

#endif /* SNMPSCALARSET_H */

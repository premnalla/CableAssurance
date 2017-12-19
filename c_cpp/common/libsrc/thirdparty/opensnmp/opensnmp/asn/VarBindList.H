#ifndef VARBINDLIST_H
#define VARBINDLIST_H

#include <list>
#include "asnDataType.H"
#include "OID.H"
#include "VarBind.H"

class VarBindList      : public asnDataType {
 private:
   std::list<VarBind *> varbinds;
   
 public:
   VarBindList();
   VarBindList(VarBind *); // create a list of one object.
   VarBindList(const VarBindList &ref);
   virtual ~VarBindList();
   void EraseData();

   void Add(VarBind *);
   void push_back(VarBind *);
   void push_front(VarBind *);
   void null_values();
   void insert(std::list<VarBind *>::iterator before, VarBind *);
   int size();
   // returns the varbind number 'Index' or null if Index > size
   virtual VarBind * get_varBindNum(int index, bool extract = false );

   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int length);

   //   print it!
   virtual std::string toString() const;
   virtual operator string() const;
   virtual std::string toPrettyString(string prefix = "");

   virtual VarBindList *clone() const { return new VarBindList(*this); };
   virtual VarBindList &change_value(const asnDataType &right);

   // iterators
   typedef std::list<VarBind *>::iterator iterator;
   typedef std::list<VarBind *>::const_iterator const_iterator;
   typedef std::list<VarBind *>::reverse_iterator reverse_iterator;
   typedef std::list<VarBind *>::const_reverse_iterator const_reverse_iterator;
   std::list<VarBind *>::iterator begin();
   std::list<VarBind *>::iterator end();
   std::list<VarBind *>::const_iterator begin() const;
   std::list<VarBind *>::const_iterator end() const;
   std::list<VarBind *>::reverse_iterator rbegin();
   std::list<VarBind *>::reverse_iterator rend();
   std::list<VarBind *>::const_reverse_iterator rbegin() const;
   std::list<VarBind *>::const_reverse_iterator rend() const;
};

#endif /* VARBINDLIST_H */

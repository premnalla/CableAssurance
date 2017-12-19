#ifndef SEQUENCE_H
#define SEQUENCE_H

#include <list>
#include "asnDataType.H"

class Sequence      : public asnDataType {
 private:
   std::list<asnDataType *> asnDataTypes;
   
 public:
   Sequence();
   Sequence(asnDataType *); // create a list of one object.
   Sequence(const Sequence &ref);
   virtual ~Sequence();
   void EraseData();

   void Add(asnDataType *);
   void push_back(asnDataType *);
   void push_front(asnDataType *);
   int size();
   
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int length);

   //   print it!
   virtual std::string toString() const;
   virtual operator string() const;

   virtual Sequence *clone() const { return new Sequence(*this); };
   virtual Sequence &change_value(const asnDataType &right);

   // iterators
   typedef std::list<asnDataType *>::iterator iterator;
   typedef std::list<asnDataType *>::const_iterator const_iterator;
   typedef std::list<asnDataType *>::reverse_iterator reverse_iterator;
   typedef std::list<asnDataType *>::const_reverse_iterator 
                                       const_reverse_iterator;
   std::list<asnDataType *>::iterator begin();
   std::list<asnDataType *>::iterator end();
   std::list<asnDataType *>::const_iterator begin() const;
   std::list<asnDataType *>::const_iterator end() const;
   std::list<asnDataType *>::reverse_iterator rbegin();
   std::list<asnDataType *>::reverse_iterator rend();
   std::list<asnDataType *>::const_reverse_iterator rbegin() const;
   std::list<asnDataType *>::const_reverse_iterator rend() const;
};

#endif /* SEQUENCE_H */

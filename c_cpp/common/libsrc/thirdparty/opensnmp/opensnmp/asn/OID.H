#ifndef OID_H
#define OID_H

#include "mib.H"
#include <list>
#include <string>
#include "asnIndex.H"
#include "illegalOID.H"
#include "debug.H"

class asnIndex;

#define MAX_OID_LEN 128

enum oid_compare_result
{
  LESS_THAN    = -1,
  EQUAL_TO     = 0,
  GREATER_THAN = 1
};

enum oid_display_types 
{
    NOT_SPECIFIED,
    NUMERIC,
    SINGLE_NODE,
    MODULE_AND_NODE,
    FULLY_QUALIFIED
};

class OID : public asnIndex {
 private:
   std::list<unsigned int> values;
   static oid_display_types display_as;
  DEBUGDECLARE( dbg );
  DEBUG_ERR_WARN_FLOW_DETAIL( dbg );
 
 public:
   OID();
   OID(unsigned int *init, int initlen);
   OID(const char *in);
   OID(const std::string & in);
   OID(const OID &thecopy, char *append = 0);
   OID(std::list<unsigned int> fromlist);
   OID(SmiNode *sn);
   virtual ~OID();

   virtual void  EraseData();
   virtual char *EncodeData(char *cp) const;
   virtual void  DecodeData(char *cp, int len);

   // iteratation
   typedef std::list<unsigned int>::iterator iterator;
   typedef std::list<unsigned int>::const_iterator const_iterator;
   virtual OID::iterator begin() { return values.begin(); }
   virtual OID::iterator end() { return values.end(); }
   virtual OID::const_iterator begin() const { return values.begin(); }
   virtual OID::const_iterator end() const { return values.end(); }

   virtual void  set(unsigned int *init, int initlen);
   virtual void  set(const std::string & from);
   virtual void  set(SmiNode *sn);
   virtual void  append(const unsigned int val);
   virtual void  append(const OID &val);
   virtual OID & crop(unsigned int begin, unsigned int end = 0);
   virtual OID & crop(OID::iterator &begin, OID::iterator &end);
   virtual int   length() const;
   virtual unsigned int &operator[] (unsigned int arg);

   //  virtual functions from base classes

   // make into a to std::string of various forms:
   static void set_displayType(oid_display_types type) {
       display_as = type;
   }
   static oid_display_types get_displayType() {
       return display_as;
   }

   virtual std::string toDisplayString(oid_display_types type = display_as) const;
   virtual std::string get_fullyQualifiedStr() const {
     return toDisplayString(FULLY_QUALIFIED);}
   virtual std::string get_numericStr() const {
     return toDisplayString(NUMERIC);}
   virtual std::string get_singleNodeStr() const {
     return toDisplayString(SINGLE_NODE);}
   virtual std::string get_moduleAndNodeStr() const {
     return toDisplayString(MODULE_AND_NODE);}
       
   // cast to string
   virtual operator string() const;
   virtual std::string toString() const;
   virtual OID *clone() const;
   virtual OID& appendOID(OID &to, bool implied = false);

   // OID specific functions.

   // returns -1 if other < this, 0 ==, 1 >
   virtual oid_compare_result compare(const OID &other) const;

   // returns -1 if other < this, 0 ==, 1 >, but only against the min
   // length of the two oids.
   virtual oid_compare_result mincompare(const OID &other, 
                                         int complength = 0) const;
   virtual oid_compare_result mincompare(const OID &other, 
                                         const unsigned char *mask) const;

  // pass through
  void removeLast() { values.pop_back(); }

   // operators
   virtual bool operator < (const OID &other) const;
   virtual bool operator > (const OID &other) const;
   virtual bool operator == (const OID &other) const;
   virtual bool operator != (const OID &other) const
     { return( ! (this->operator==(other)) ); };

   virtual OID &change_value(const asnDataType &right);

   virtual OID operator + (const OID &other);
   virtual OID& operator += (const OID &other);
   virtual unsigned int *array();
   virtual unsigned int *array(unsigned int *ret) const;
   virtual unsigned int size() const;
};

#endif /* OID_H */

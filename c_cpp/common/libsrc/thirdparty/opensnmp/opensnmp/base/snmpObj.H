#ifndef SNMPOBJ_H
#define SNMPOBJ_H

#include <string>
#include <list>


#define SAFE_DELETE(x) do { if (x) {delete x; x = NULL;} } while (0);

// makes a get_NAME/set_NAME accessor function pair out variable TYPE,
// a variable NAME and optionally extra stuff to do when the add is
// done.
#define DEFINE_PTR_ACCESSOR(TYPE, NAME, MOREADDSTUFF)   \
   virtual TYPE *get_##NAME(bool extract = false) {     \
      TYPE *ret = NAME;                                 \
      if (extract)                                      \
         NAME = NULL;                                   \
      return ret;                                       \
   }                                                    \
   virtual void set_##NAME(TYPE* NAME) {                \
     if (this->NAME)                                    \
         delete this->NAME;                             \
     this->NAME = NAME;                                 \
     MOREADDSTUFF                                       \
   }                                                    

// same as above, but not a pointer 
#define DEFINE_ACCESSOR(TYPE, NAME, MOREADDSTUFF)       \
   virtual TYPE get_##NAME() {                          \
      return NAME;                                      \
   }                                                    \
   virtual void set_##NAME(const TYPE &NAME) {          \
     this->NAME = NAME;                                 \
     MOREADDSTUFF                                       \
   }                                                    

// same as above, but pass onward to a contained OBJ member instead.
#define DEFINE_PTR_PASS_PTR_ACCESSOR(OBJ, TYPE, NAME)   \
   virtual TYPE *get_##NAME() {                         \
      return OBJ->get_##NAME();                         \
   }                                                    \
   virtual void set_##NAME(const TYPE *NAME) {          \
     OBJ->set_##NAME(NAME);                             \
   }                                                    

// same as above, but pass onward to a contained OBJ member instead.
#define DEFINE_PASS_PTR_ACCESSOR(OBJ, TYPE, NAME)       \
   virtual TYPE *get_##NAME() {                         \
      return OBJ.get_##NAME();                          \
   }                                                    \
   virtual void set_##NAME(TYPE *NAME) {                \
     OBJ.set_##NAME(NAME);                              \
   }                                                    

extern "C" 
{
    const char *opensnmp_version(void);
}

using std::string;

// snmpObj Class:
//   *all* objects will inherit from this.  This object provides basic
//   parent functionality to provide manipulation routines for any
//   object to generically manipulate other objects, etc.
class snmpObj {

public:
  
  // Default constructors.  This class in particular is abstract,
  // though.  All children need it though.
  snmpObj();
  virtual ~snmpObj();

  // clone():
  //  implements a generic clone function.  All objects should implement
  //  this.  It should always return a new object which is a direct
  //  copy of itself, newly created and filled with the appropriate
  //  data.
  virtual snmpObj *clone() const = 0;
  
  // toString():
  //   mostly a debugging function to turn a given class into a
  //   string showing all its parameters.  Probably prints any
  //   generic stuff in a string, and then calls toString on all the
   //   owned objects.  Not really needed unless debugging.  May not
  //   be wanted here.
  virtual string toString() const;        // do we want this?
  virtual operator string() const;
  
  // get_className():
  //   simply returns the name of the class.  Only for debugging mostly. 
  //  virtual string get_className();    // do we want this?

  friend std::ostream& operator<<( std::ostream& os, const snmpObj & obj );

};  /* class snmpObj */


#endif /* SNMPOBJ_H */

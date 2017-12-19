#ifndef THREADSAFEARRAY_H
#define THREADSAFEARRAY_H

#include "snmpObj.H"
#include "snmpMutexObj.H"
// #include "threadSafeArray.C"

// This class is designed to provide thread safe access to an array of values.
// <type or class to store, length of array>
//
// restrictions:
//  -  there is no range checking, the user needs to do proper range checking
//     before accessing.

template <class storage_type, size_t length_of_array>
class thread_safe_array {

private:
  storage_type vals[length_of_array];
  snmpMutexObj statsMutex;

public:
  thread_safe_array() {};
  virtual ~thread_safe_array() {};

  virtual void set(size_t location, storage_type newVal) {
    if (this->statsMutex.lock("thread_safe_array::set")) {
      this->vals[location] = newVal;
      this->statsMutex.unlock("thread_safe_array::set");
    }
  };

  virtual storage_type  get(size_t location) {
    return(this->vals[location]);
  };

  // increment assumes a numeric storage_type... any other type will
  // need to implement it's own increment operator ++
  virtual storage_type increment(size_t location) {
    if (this->statsMutex.lock("thread_safe_array::increment")) {
      (this->vals[location])++;
      this->statsMutex.unlock("thread_safe_array::increment");
    }
    return(this->vals[location]);
  };

  virtual size_t length() { return length_of_array; };

};

#endif // THREADSAFEARRAY_H

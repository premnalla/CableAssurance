#ifndef SNMPSTATUSINFO_H
#define SNMPSTATUSINFO_H

#include <queue>
#include "snmpObj.H"
#include "snmpErrorObj.H"

class snmpStatusInfo : public snmpObj {
private:
  
  bool                        success;
  std::queue<snmpErrorObj *>  error_q;
  //  unsigned long               handle;

public:
  snmpStatusInfo(bool is_success = true);
  //  snmpStatusInfo(bool is_success = true, unsigned long handle = 0);
  snmpStatusInfo(bool is_success, snmpErrorObj *errObj);

  virtual ~snmpStatusInfo();
  
  virtual snmpStatusInfo *clone() const;
  
  DEFINE_ACCESSOR(bool, success, );
  //  DEFINE_ACCESSOR(unsigned long, handle, );

  // returns null if empty.
  virtual snmpErrorObj *popErrorObj();
  // returns pointer to front object without removing from queue!
  virtual snmpErrorObj *frontErrorObj();
  // add error object set this->success to false.
  virtual void pushErrorObj(snmpErrorObj *newError);

  // returns number of error objects currently in the queue
  virtual int num_of_errors();

  virtual string toString();
};


#endif  // SNMPSTATUSINFO_H

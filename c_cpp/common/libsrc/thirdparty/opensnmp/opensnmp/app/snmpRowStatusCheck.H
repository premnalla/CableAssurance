#ifndef SNMPROWSTATUSCHECK_H
#define SNMPROWSTATUSCHECK_H

#include "snmpObj.H"
#include "snmpRow.H"

/*
 * class snmpRowStatusCheck:
 *
 *   Public status check object that will be called to check a row in a table
 *   to determine if it can or can't be set to "Active(1)" as far as the row's
 *   rowStatus is concerned.
 */

class snmpRowStatusCheck : public snmpObj {
 public:
   virtual bool check_row(snmpRow *row) = 0;
   virtual snmpRowStatusCheck *clone() const = 0;

};

#endif /* SNMPROWSTATUSCHECK_H */

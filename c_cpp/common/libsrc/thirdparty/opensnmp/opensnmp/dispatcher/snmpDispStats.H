#ifndef SNMPDISPSTATS_H
#define SNMPDISPSTATS_H

#include "snmpObj.H"
#include "snmpRegObj.H"
#include "asnDataTypes.H"

class snmpDispStats : public snmpObj {
private:
  Integer32 snmpInPkts;
  Integer32 snmpInBadVersions;
  Integer32 snmpUnknownPDUHandlers;

  snmpDispStats(const snmpDispStats &);

protected:

public:

  snmpDispStats();
  virtual ~snmpDispStats();

  virtual void init();

  virtual snmpDispStats *clone() const { return new snmpDispStats(*this); };

  Integer32 get_snmpInPkts();
  Integer32 incr_snmpInPkts();
  Integer32 get_snmpInBadVersions();
  Integer32 incr_snmpInBadVersions();
  Integer32 get_snmpUnknownPDUHandlers();
  Integer32 incr_snmpUnknownPDUHandlers();

};


#endif /* SNMPDISPSTATS_H ifndef */

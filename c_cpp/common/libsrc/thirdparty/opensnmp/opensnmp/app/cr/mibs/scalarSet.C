// scalarSet.C: implementation for a simple set of scalars.

// Note, we're relocatable.  We assume we've been registered at OID
// and that OID.label.0 belongs to us.

#include "config.h"
#include "scalarSet.H"
#include "snmpProtoErr.H"
#include "debug.H"

scalarSet::scalarSet(snmpScalarData *data) {
    DEBUGCREATE(debugObj, "scalarSet");
    this->data = data;
}

scalarSet::~scalarSet() {
  DEBUGDESTROY(debugObj);
}

snmpProtoErr *
scalarSet::snmp_get(MIBRegistration *reg, SearchRangeList *thevars) {
  OID *regoid = reg->get_reg_oid();
  
  SearchRangeList::iterator i;
  for(i = thevars->begin(); i != thevars->end(); i++) {
    SearchRange *sr = *i;
    OID *retrievethis = sr->get_start_oid();
    // someone has compared up to the registration already, so check
    // to see if the suffix is correct.
    if ((regoid->length() + 2 == (retrievethis->length())) &&
        ((*retrievethis)[retrievethis->length()-1] == 0)) {

      asnDataType *ret = 
          data->get_column((*retrievethis)[retrievethis->length()-2]);
      if (ret != 0)
        sr->get_varbind()->set_value(ret->clone());
      // XXX: else error?
    } else {
      // XXX: error
    }
    DEBUG9(debugObj, "        scalarSet::get " << sr->get_varbind()->toString() << "\n");
  }
  return 0;
}

snmpProtoErr *
scalarSet::snmp_getNext(MIBRegistration *reg, SearchRangeList *thevars) {
  OID *regoid = reg->get_reg_oid();
  asnDataType *ret = 0;
  unsigned int newColumn;
  
  SearchRangeList::iterator i;
  for(i = thevars->begin(); i != thevars->end(); i++) {
    SearchRange *sr = *i;
    OID *retrievethis = sr->get_start_oid();
    if (retrievethis->mincompare(*regoid, regoid->length()) == LESS_THAN) {
        ret = data->get_nextColumn(-1, &newColumn);
    } else if (regoid->mincompare(*retrievethis, regoid->length()) == 0) {
        // they are identical up to the length of the oid we're
        // registered under.
        if (retrievethis->length() >= regoid->length() + 2) {
            // The length is >= than a scalar length for our data, so
            // return the next scalar if there is one.
            ret = data->get_nextColumn((*retrievethis)[regoid->length()], 
                                       &newColumn);
        } else if (regoid->length() == retrievethis->length()) {
            // the length is equal, so return the first scalar
            ret = data->get_nextColumn(-1, &newColumn);
        } else {
            // the length is short of a real scalar, so return that
            // scalar or the first after it, if it doesn't exist.
            ret = data->get_nextColumn((*retrievethis)[retrievethis->length()-1]-1, 
                                       &newColumn);
        }
    }
    if (ret) {
        OID *retoid = new OID(*regoid);
        retoid->append(newColumn);
        retoid->append(0);
        sr->get_varbind()->set_OID(retoid);
        sr->get_varbind()->set_value(ret->clone());
    }
    DEBUG9(debugObj, "        scalarSet::get_next " << sr->get_varbind()->toString() << "\n");
  }
  return 0;
}

snmpProtoErr *
scalarSet::snmp_set(MIBRegistration *reg, set_actions action, 
               SearchRangeList *thevars) {
  OID *regoid = reg->get_reg_oid();
  snmpProtoErr *err;
  
  SearchRangeList::iterator i;
  for(i = thevars->begin(); i != thevars->end(); i++) {
      SearchRange *sr = *i;
      OID *retrievethis = sr->get_start_oid();
      // someone has compared up to the registration already, so check
      // to see if the suffix is correct.
      if ((regoid->length() + 2 == (retrievethis->length())) &&
          ((*retrievethis)[retrievethis->length()-1] == 0)) {

          switch (action) {
              case AGENTX_TESTSET:
                  if ((unsigned int) action > data->get_max_col())
                      return new snmpProtoErr(PROTOERR_NOSUCHNAME,
                                              sr->get_varbind());
                  err = data->test_set((*retrievethis)[retrievethis->length()-2],
                                       sr->get_varbind()->get_value());
                  if (err) {
                      // bail out, and return the error immediately.
                      // first, though, set the varbind that caused the problem.
                      err->set_cause(sr->get_varbind());
                      return err;
                  }
                  break;
                  
              case AGENTX_COMMIT:
                  undoInfo[(*retrievethis)[retrievethis->length()-2]] =
                      data->get_column((*retrievethis)[retrievethis->length()-2])->clone();
                  err = data->set_column((*retrievethis)[retrievethis->length()-2],
                                         sr->get_varbind()->get_value());
                  if (err) {
                      // bail out, and return the error immediately.
                      // first, though, set the varbind that caused the problem.
                      err->set_cause(sr->get_varbind());
                      return err;
                  }
                  break;

              case AGENTX_CLEANUP:
                  if (undoInfo[(*retrievethis)[retrievethis->length()-2]])
                      delete undoInfo[(*retrievethis)[retrievethis->length()-2]];
                  undoInfo.erase((*retrievethis)[retrievethis->length()-2]);
                  break;

              case AGENTX_UNDO:
                  data->set_column((*retrievethis)[retrievethis->length()-2],
                                   undoInfo[(*retrievethis)[retrievethis->length()-2]]);
                  undoInfo.erase((*retrievethis)[retrievethis->length()-2]);
                  break;

              default:
                  break;
          }
      } else {
          return new snmpProtoErr(PROTOERR_NOSUCHNAME);
      }
    DEBUG9(debugObj, "        scalarSet::set " << sr->get_varbind()->toString() << "\n");
  }
  return 0;
}

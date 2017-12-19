// a special instance of a table that only has one row.  Essentially a
// scalar set can be treated as special table with only one row.

#include <config.h>
#include "snmpScalarData.H"

snmpScalarData::snmpScalarData(unsigned int highval, unsigned int lowval,
                               bool default_is_writable) {
    max_col = highval;
    min_col = lowval;
    this->default_is_writable = default_is_writable;
}

asnDataType *
snmpScalarData::get_nextColumn(unsigned int num, unsigned int *newColumn) const {
  asnDataType *ret = 0;

  while (ret == 0 && ++num <= get_max_col())
      ret = get_column(num);

  if (ret)
      *newColumn = num;
  return ret;
}

snmpProtoErr *
snmpScalarData::set_column(unsigned int num, const asnDataType *data) {
    asnDataType *old = get_column(num);
    if (old == NULL)
        return new snmpProtoErr(PROTOERR_NOSUCHNAME);

    if (is_writable(num)) {
        old->change_value(*data);
    } else {
        return new snmpProtoErr(PROTOERR_NOTWRITABLE);
    }
    return NULL;
}
 
snmpProtoErr *
snmpScalarData::test_set(unsigned int num, const asnDataType *data) const {
    asnDataType *old = get_column(num);

    if (old == NULL)
        return new snmpProtoErr(PROTOERR_NOSUCHNAME); // XXX: correct?

    if (!is_writable(num))
        return new snmpProtoErr(PROTOERR_NOTWRITABLE);

    asnDataType::valueErrors ret = old->check_new_value(*data);
    if (ret)
        return new snmpProtoErr(ret);
    return NULL;
}

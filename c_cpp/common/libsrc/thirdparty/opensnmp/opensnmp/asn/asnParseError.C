/* asnParseError.C */
#include "config.h"
#include "asnParseError.H"

asnParseError::asnParseError()
{
}

asnParseError::asnParseError(char *str) : snmpStringException(str)
{
}

asnUsageError::asnUsageError()
{
}

asnUsageError::asnUsageError(char *str) : snmpStringException(str)
{
}




//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <string.h>
#include "axCommonStringUtils.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCommonStringUtils::axCommonStringUtils()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCommonStringUtils::~axCommonStringUtils()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCommonStringUtils::axCommonStringUtils()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axCommonStringUtils::CopyString(AX_INT8 * dest, AX_INT8 * src, 
                                    AX_INT32 destLen, AX_INT32 srcLen)
{
  memcpy(dest, src, destLen--);
  dest[destLen] = '\0';

#if 0
  if (srcLen < destLen) {
    if (srcLen) {
      strcpy(dest, src);
    } else {
      dest[0] = '\0';
    }
  } else {
    memcpy(dest, src, destLen);
    dest[destLen-1] = '\0';
  }
#endif
}


//********************************************************************
// method:
//********************************************************************
void
axCommonStringUtils::CopyString(AX_INT8 * dest, AX_INT8 * src, 
                                                      AX_INT32 destLen)
{
  memcpy(dest, src, destLen--);
  dest[destLen] = '\0';
}


//********************************************************************
// method:
//********************************************************************
void
axCommonStringUtils::CopyString(AX_INT8 * dest, AX_INT8 * src)
{
  strcpy(dest, src);
}


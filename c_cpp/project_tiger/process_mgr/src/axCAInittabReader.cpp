
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCAInittabReader.hpp"

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
axCAInittabReader::axCAInittabReader() :
  m_fileOpened(false),
  m_fp(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCAInittabReader::~axCAInittabReader()
{
  if (m_fp) {
    fclose(m_fp);
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axCAInittabReader::axCAInittabReader(const AX_INT8 * fn) :
  m_fileName(fn),
  m_fileOpened(false),
  m_fp(NULL)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCAInittabReader::openFile(void)
{
  bool ret = false;

  if (m_fileOpened) {
    ret = true;
    goto EXIT_LABEL;
  }

  printf("file=%s\n", m_fileName.c_str());

  m_fp = fopen(m_fileName.c_str(), "r");

  if (m_fp) {
    m_fileOpened = true;
    ret = true;
  } else {
    printf("open error\n");
  }

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCAInittabReader::readLine(AX_INT8 * outB, AX_INT32 bLen)
{
  bool ret = false;

  char * s;
  int    actLen;

  if (!m_fileOpened) {
    goto EXIT_LABEL;
  }

  s = fgets(outB, bLen, m_fp);
  if (s) {
    actLen = strlen(outB);
    /* get rid of newline */
    outB[actLen-1] = '\0';
    ret = true;
  }

EXIT_LABEL:

  return (ret);
}



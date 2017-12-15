
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCAInittabParser.hpp"
#include "axCAInittabReader.hpp"
#include "axPmData.hpp"

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
axCAInittabParser::axCAInittabParser() :
  m_fileParsed(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCAInittabParser::~axCAInittabParser()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCAInittabParser::axCAInittabParser(const AX_INT8 * fn) :
  m_fileName(fn),
  m_fileParsed(false)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCAInittabParser::parseFile(void)
{
  bool ret = false;

  const AX_INT32 bL = 1024;
  AX_INT8        buf[bL];
  AX_UINT32      row;

  axPmData * pmData = axPmData::getInstance();
  axPmInittabEntries_s * tabEntries = pmData->getInittabEntries();

  axCAInittabReader fR(m_fileName.c_str());

  if (m_fileParsed) {
    goto EXIT_LABEL;
  }

  if (!fR.openFile()) {
    goto EXIT_LABEL;
  }

  row = 0;
  while (fR.readLine(buf, bL)) {
    parseLine(buf, row);
  }

  tabEntries->numEntries = row;

  ret = true;
  m_fileParsed = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCAInittabParser::parseLine(AX_INT8 * s, AX_UINT32 & row)
{
  const int numTokens = 10;
  char * tokens[numTokens];
  char * ss;
  char * token;
  char * previousToken;
  AX_UINT32  tokenNum = 0;
  AX_UINT32  i;
  char       command[512];
  char       path[512];
  static const char * colonDelim = ":";
  static const char * dirDelim = "/";
  static const char * spaceDelim = " ";

  axPmData * pmData = axPmData::getInstance();
  axPmInittabEntries_s * tabEntries = pmData->getInittabEntries();

  if (s[0]=='#' || s[0]=='\t' || s[0]==' ' || s[0]=='\n') {
    goto EXIT_LABEL;
  }

  memset(tokens, 0, sizeof(tokens));

  token = strtok_r(s, colonDelim, &ss);
  while (token) {
    tokens[tokenNum++] = token;

    // next call
    token = strtok_r(NULL, colonDelim, &ss);
  }

  if ((tokenNum != tabEntries->numColumns) ||
      (row >= tabEntries->maxEntries)) {
    goto EXIT_LABEL;
  }

  i = 0;
  copyString(tabEntries->entries[row].id, tokens[i++], 
                                                  AX_CAPM_MAX_ID_LEN);
  copyString(tabEntries->entries[row].runLevel, tokens[i++], 
                                                  AX_CAPM_MAX_RL_LEN);
  copyString(tabEntries->entries[row].action, tokens[i++], 
                                                 AX_CAPM_MAX_ACT_LEN);
  /* command => e.g. "/bin/foo -f foo.conf" */
  strcpy(command, tokens[i++]);

  /*
   * Breakup command parameter in file to obtain:
   *  1. Command name
   *  2. Arguments for command
   */

  i = 0;
  token = strtok_r(command, spaceDelim, &ss);
  if (!token) {

    /* case where command => "/bin/foo" */
    copyString(tabEntries->entries[row].path, command,
                                                AX_CAPM_MAX_PATH_LEN);
  } else {

    while (token) {

      if (i) {
        tabEntries->entries[row].argv[i] = strdup(token);
      } else {
        /* copy: "/bin/foo" part to path */
        copyString(tabEntries->entries[row].path, token,
                                                AX_CAPM_MAX_PATH_LEN);
      }

      // next call
      token = strtok_r(NULL, spaceDelim, &ss);
      i++;
    }

  }

  strcpy(path, tabEntries->entries[row].path);
  previousToken = token = strtok_r(path, dirDelim, &ss);
  if (!token) {

    /* case where path => "foo" */
    tabEntries->entries[row].argv[0] = strdup(path);

  } else {

    while (token) {
      previousToken = token;
      // next call
      token = strtok_r(NULL, dirDelim, &ss);
    }

    tabEntries->entries[row].argv[0] = strdup(previousToken);
  }

  ++row;

EXIT_LABEL:
  return;
}




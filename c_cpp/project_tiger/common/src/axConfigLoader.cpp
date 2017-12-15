
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "ace/String_Base.h"
#include "axConfigLoader.hpp"

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
axConfigLoader::axConfigLoader() :
  m_loaded(false),
  m_config(NULL),
  m_registryImpExp(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axConfigLoader::~axConfigLoader()
{
  if (m_config) {
    delete m_config;
  }

  if (m_registryImpExp) {
    delete m_registryImpExp;
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axConfigLoader::axConfigLoader(AX_INT8 * fname) :
  m_fileName(fname),
  m_loaded(false),
  m_config(NULL),
  m_registryImpExp(NULL)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axConfigLoader::loadConfig(void)
{
  bool ret = false;

  if (!m_loaded) {

    if (m_config) {
      delete m_config;
    }
    m_config = new ACE_Configuration_Heap();
    if (m_config->open() == -1) {
      goto EXIT_LABEL;
    }

    if (m_registryImpExp) {
      delete m_registryImpExp;
    }
    m_registryImpExp = new ACE_Registry_ImpExp(*m_config);
    if ( m_registryImpExp->import_config(const_cast<AX_INT8 *> 
                                      (m_fileName.c_str())) == -1 ) {
      goto EXIT_LABEL;
    }

    // finally
    m_loaded = true;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
// out: pV - parameter value
//********************************************************************
bool
axConfigLoader::getValue(const AX_INT8 * sectName, 
                          const AX_INT8 * paramName, std::string & pV)
{
  bool ret = false;

  ACE_TString v;

  ACE_Configuration_Section_Key sect;
  if (m_config->open_section(m_config->root_section(), 
                                           sectName, 0, sect) == -1) {
    goto EXIT_LABEL;
  }

  if (m_config->get_string_value(sect, paramName, v) == -1) {
    goto EXIT_LABEL;
  }

  pV = v.c_str();

  ret =true;

EXIT_LABEL:

  return (ret);
}



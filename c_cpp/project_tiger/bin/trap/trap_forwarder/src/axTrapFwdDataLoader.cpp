
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axCALog.hpp"
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbMySqlResult.hpp"
#include "axDbMySqlResultSet.hpp"
#include "axDbReadConnHelper.hpp"
#include "axAvlTree.hpp"
#include "axTrapFwdDataLoader.hpp"
#include "axTopoLookupTables.hpp"
#include "axTopoLookupIntGenMta.hpp"
#include "axDbTopoLookupGenMta.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axTrapFwdDataLoader * axTrapFwdDataLoader::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axTrapFwdDataLoader::axTrapFwdDataLoader() :
  m_dataLoaded(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axTrapFwdDataLoader::~axTrapFwdDataLoader()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTrapFwdDataLoader::axTrapFwdDataLoader()
// {
// }


//********************************************************************
// method:
//********************************************************************
axTrapFwdDataLoader *
axTrapFwdDataLoader::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axTrapFwdDataLoader();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool
axTrapFwdDataLoader::loadData(void)
{
  bool ret = true;

  if (!m_dataLoaded) {

    loadMTAs();
    ACE_DEBUG((LM_MISC_DEBUG,"loaded MTA's\n"));

    // finally ...
    m_dataLoaded = true;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axTrapFwdDataLoader::loadMTAs(void)
{
  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

  axTopoLookupTables * gD = axTopoLookupTables::getInstance();
  synchronizedTable_t * syncMtaTbl = gD->getMtaTable();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    // axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * mtaTable = dynamic_cast<axAvlTree *> 
                                                  (syncMtaTbl->table);

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    axDbTopoLookupGenMta dbEmta;
    bool qrc = query->execute(axDbTopoLookupGenMta::Query,
                                   (axDbAbstractTopoLookup &) dbEmta);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    {
      axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());

      while (rs->getNext((axDbAbstractTopoLookup &) dbEmta)) {
        axTopoLookupIntGenMta * intMta  =  
                                    new axTopoLookupIntGenMta(dbEmta);
        if (!intMta->isAddable() || !mtaTable->add(intMta)) {
          ACE_DEBUG((LM_ERROR, "Unable to add MTA (mac=%s)\n",
                                         intMta->getMtaMacAddress()));
          delete intMta;
        }
      }

      ACE_DEBUG((LM_MISC_DEBUG, "Total MTA's loaded = %d\n",
                                                   mtaTable->size()));
    }

  }

EXIT_LABEL:

  return;
}



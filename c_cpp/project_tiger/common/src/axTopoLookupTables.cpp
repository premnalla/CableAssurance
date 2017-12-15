
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axTopoLookupTables.hpp"
#include "axAvlTree.hpp"
#include "axMultipleReaderLock.hpp"
// #include "axTopoIntCm.hpp"
#include "axTopoLookupIntGenMta.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalUtils.hpp"
#include "axTopoBladeContainer.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axTopoLookupTables * axTopoLookupTables::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axTopoLookupTables::axTopoLookupTables() :
  m_mtaTable(new axAvlTree(), new axMultipleReaderLock()),
  m_containerList(new axMultipleReaderLock())
{
  // m_cmTable.table = new axAvlTree();
  // m_cmTable.lock = new axMultipleReaderLock();

  // m_mtaTable.table = new axAvlTree();
  // m_mtaTable.lock = new axMultipleReaderLock();
}


//********************************************************************
// destructor:
//********************************************************************
axTopoLookupTables::~axTopoLookupTables()
{
  if (m_mtaTable.table) {
    delete m_mtaTable.table;
    m_mtaTable.table = NULL;
  }

  if (m_mtaTable.lock) {
    delete m_mtaTable.lock;
    m_mtaTable.lock = NULL;
  }
}


//********************************************************************
// method :
//********************************************************************
axTopoLookupTables*
axTopoLookupTables::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axTopoLookupTables();
  }
  return (m_instance);
}


//********************************************************************
// method : 
//********************************************************************
synchronizedTable_t *
axTopoLookupTables::getMtaTable(void)
{
  return (&m_mtaTable);
}


//********************************************************************
// method : 
//********************************************************************
axListExtLock *
axTopoLookupTables::getContainerList(void)
{
  return (&m_containerList);
}


//********************************************************************
// method :
//********************************************************************
axTopoLookupIntGenMta *
axTopoLookupTables::findMtaExtLock(AX_INT8 * mtaMac)
{
  axTopoLookupIntGenMta * ret;

  synchronizedTable_t * syncMtaTable = getMtaTable();
  axAvlTree * mtaTbl = dynamic_cast<axAvlTree *>
                                               (syncMtaTable->table);
  axTopoLookupIntGenMta tmpIntMta(mtaMac);
  ret = (axTopoLookupIntGenMta *) mtaTbl->find(&tmpIntMta);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axTopoLookupIntGenMta *
axTopoLookupTables::findMta(AX_INT8 * mtaMac)
{
  axTopoLookupIntGenMta * ret;

  synchronizedTable_t * syncMtaTable = getMtaTable();
  axReadLockOperator rLock(syncMtaTable->lock);
  ret = this->findMtaExtLock(mtaMac);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axTopoLookupIntGenMta *
axTopoLookupTables::addMtaExtLock(axTopoLookupIntGenMta * o)
{
  synchronizedTable_t * syncMtaTable = getMtaTable();
  axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> (syncMtaTable->table);
  return((axTopoLookupIntGenMta *) mtaTbl->add(o));
}


//********************************************************************
// method :
//********************************************************************
axTopoLookupIntGenMta *
axTopoLookupTables::addMta(axTopoLookupIntGenMta * o)
{
  synchronizedTable_t * syncMtaTable = getMtaTable();
  axWriteLockOperator wLock(syncMtaTable->lock);
  return(this->addMtaExtLock(o));
}


//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::findContainerExtLock(AX_UINT16 containerId)
{
  axAbstractTopoContainerObj * ret;
  axTopoBladeContainer bld(containerId);
  ret = (axAbstractTopoContainerObj *) m_containerList.find(&bld);
  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axTopoLookupTables::setFdSetExtLock(fd_set * fdset)
{
  axListBase::listType_t::iterator item = m_containerList.begin();
  axListBase::listType_t::iterator end = m_containerList.end();
  for(; item != end; item++) {
    axAbstractTopoContainerObj * oI = (axAbstractTopoContainerObj *) (*item);
    int fd;
    if ( (fd = oI->getContainerFd()) ) {
      FD_SET(fd, fdset);
    }
  }
}


//********************************************************************
// method :
//********************************************************************
void
axTopoLookupTables::setFdSet(fd_set * fdset)
{
  axReadLockOperator rL(getContainerList()->getLock());
  setFdSetExtLock(fdset);
}


//********************************************************************
// method :
//********************************************************************
void
axTopoLookupTables::getFdSetExtLock(AX_INT32 * fdList, size_t len, fd_set * fdset)
{
  memset(fdList, 0, len);
  int numElem = len/sizeof(*fdList);
  axListBase::listType_t::iterator item = m_containerList.begin();
  axListBase::listType_t::iterator end = m_containerList.end();
  for(int i=0; item != end && i<numElem; item++) {
    axAbstractTopoContainerObj * oI = (axAbstractTopoContainerObj *) (*item);
    int fd;
    if ( (fd = oI->getContainerFd()) && FD_ISSET(fd, fdset)) {
      fdList[i++] = fd;
    }
  }
}


//********************************************************************
// method :
//********************************************************************
void
axTopoLookupTables::getFdSet(AX_INT32 * fdList, size_t len, fd_set * fdset)
{
  axReadLockOperator rL(getContainerList()->getLock());
  getFdSetExtLock(fdList, len, fdset);
}

//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::findContainerExtLock(AX_INT32 containerFd)
{
  axAbstractTopoContainerObj * ret = NULL;

  axListBase::listType_t::iterator item = m_containerList.begin();
  axListBase::listType_t::iterator end = m_containerList.end();
  for(; item != end; item++) {
    axAbstractTopoContainerObj * oI = (axAbstractTopoContainerObj *) (*item);
    if (oI->getContainerFd() == containerFd) {
      ret = oI;
      break;
    }
  }

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::findContainer(AX_UINT16 containerId)
{
  axReadLockOperator rL(m_containerList.getLock());
  axAbstractTopoContainerObj * ret = findContainerExtLock(containerId);
  return (ret);
}


//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::findContainer(AX_INT32 containerFd)
{
  axReadLockOperator rL(m_containerList.getLock());
  axAbstractTopoContainerObj * ret = findContainerExtLock(containerFd);
  return (ret);
}

//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::addContainerExtLock(axAbstractTopoContainerObj * o)
{
  return ((axAbstractTopoContainerObj *) m_containerList.add(o));
}


//********************************************************************
// method :
//********************************************************************
axAbstractTopoContainerObj *
axTopoLookupTables::addContainer(axAbstractTopoContainerObj * o)
{
  axWriteLockOperator wL(m_containerList.getLock());
  return (addContainerExtLock(o));
}



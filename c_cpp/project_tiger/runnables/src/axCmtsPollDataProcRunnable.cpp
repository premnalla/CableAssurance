
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCmtsPollDataProcRunnable.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCm.hpp"
#include "axList.hpp"

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
axCmtsPollDataProcRunnable::axCmtsPollDataProcRunnable() :
  m_intCmts(NULL)
{
  setDeletable(true);
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsPollDataProcRunnable::~axCmtsPollDataProcRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsPollDataProcRunnable::axCmtsPollDataProcRunnable(
  axSnmpCmtsCmResultValues_s * s, axInternalCmts * intCmts) :
  m_intCmts(intCmts)
{
  setDeletable(true);
  m_pollData = *s;
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsPollDataProcRunnable::preRun(void)
{
  return (0);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsPollDataProcRunnable::run(void)
{
  AX_INT32 ret = -1;

  synchronizedTable_t * cmTblStruct = m_intCmts->getCmTable();

  axList newCmList;

  {
    axReadLockOperator cmTblLock(cmTblStruct->lock);

    axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (cmTblStruct->table);

    axInternalCm * actualIntCm;

    for (int i=0; i<m_pollData.numValues; i++) {

      axSnmpCmtsCmResultRow_s * r = &m_pollData.values[i];
      axInternalCm tmpIntCm(r->mac);
      actualIntCm = dynamic_cast<axInternalCm *> (cmTbl->find(&tmpIntCm));

      if (!actualIntCm) {

        // handle new cm
        axInternalCm * newCm = new axInternalCm(r);
        newCmList.add(newCm);

      } else {

        // do a diff/cmp of attr values and take necessary actions
        axInternalCm cm(r);
        actualIntCm->compareAndUpdate(&cm, m_intCmts->getResId());

      }

    } // for()

  }


  /*
   * add cm to internal tree, to db, etc...
   */
  if (newCmList.size()) {

    axWriteLockOperator cmTblLock(cmTblStruct->lock);

    axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (cmTblStruct->table);

    axInternalCm * cm;

    for (cm = (axInternalCm *) newCmList.remove(); cm; 
         cm = (axInternalCm *) newCmList.remove()) {

      if (cm->isAddable()) {

        axDbCm dbCm(cm, m_intCmts->getResId());
        if (dbCm.insertRow()) {
          cmTbl->add(cm);
        } else {
          delete cm;
        }

      } else {
        delete cm;
      }

    } // for

  }

  ret = 0;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsPollDataProcRunnable::postRun(void)
{
  return (0);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsPollDataProcRunnable::nextAction(void)
{
}



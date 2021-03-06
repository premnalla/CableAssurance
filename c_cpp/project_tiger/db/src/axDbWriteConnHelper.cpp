
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbWriteConnHelper.hpp"

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
axDbWriteConnHelper::axDbWriteConnHelper()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbWriteConnHelper::~axDbWriteConnHelper()
{
  axDbConnectionFactory * cf = getConnectionFactory();
  // axDbConnection * c = getConnection();
  // assert (c != NULL);
  cf->releaseWriteConnection(getConnection());
}


//********************************************************************
// data constructor:
//********************************************************************
axDbWriteConnHelper::axDbWriteConnHelper(axDbConnectionFactory * cf)
{
  axDbConnection * c = cf->getWriteConnection();

  while (c == NULL) {
    sleep(1);
    c = cf->getWriteConnection();
  }

  setConnection(c);
  setConnectionFactory(cf);
}


//********************************************************************
// method:
//********************************************************************


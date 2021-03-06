
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <unistd.h> // sleep()
#include "axDbReadConnHelper.hpp"

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
axDbReadConnHelper::axDbReadConnHelper()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbReadConnHelper::~axDbReadConnHelper()
{
  axDbConnectionFactory * cf = getConnectionFactory();
  // axDbConnection * c = getConnection();
  // assert (c != NULL);
  cf->releaseReadOnlyConnection(getConnection());
}


//********************************************************************
// data constructor:
//********************************************************************
axDbReadConnHelper::axDbReadConnHelper(axDbConnectionFactory * cf)
{
  axDbConnection * c = cf->getReadOnlyConnection();

  while (c == NULL) {
    sleep(1);
    c = cf->getReadOnlyConnection();
  }

  setConnection(c);
  setConnectionFactory(cf);
}


//********************************************************************
// method:
//********************************************************************


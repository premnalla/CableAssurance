/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axRunnableDataTypes.hpp      
 *
 *************************************************************************
 * Modification History:
 *			
 *************************************************************************
 * Description:
 *
 *************************************************************************
 * Modification History:
 *
 *    10/6/06     Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axRunnableDataTypes_hpp_
#define _axRunnableDataTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include "axAll.h"


/*************************************************************************
 * Forward Declerations
 *************************************************************************/

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/

struct axRunnableMetaDataBase_s {
  axRunnableMetaDataBase_s()
  {
  }
};


struct axRunnableCountsMetaData_s : public axRunnableMetaDataBase_s {
  AX_UINT8 countsType;
  axRunnableCountsMetaData_s() : countsType(0)
  {
  }
};

/*************************************************************************
 * Global Type definitions
 *************************************************************************/


#endif /* #ifndef _axRunnableDataTypes_hpp_ */

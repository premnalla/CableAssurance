/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axMessageDataTypes.hpp      
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
 *    08/7/06     Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axMessageDataTypes_hpp_
#define _axMessageDataTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include "axAll.h"
#include "axAbstractCAAlarm.hpp"


/*************************************************************************
 * Forward Declerations
 *************************************************************************/
// class axAbstractCAAlarm;

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/

/************************************************************************/
/******************************** Queue Types ***************************/
/************************************************************************/
#define AX_MSGQ_TYPE_NULL                                            0x00
#define AX_MSGQ_TYPE_P2P                                             0x01
#define AX_MSGQ_TYPE_PUB_SUB                                         0x02

/************************************************************************/
/************************* Message Topic Types **************************/
/************************************************************************/
#define AX_MSGQ_TOPIC_NULL                                           0x00
#define AX_MSGQ_TOPIC_COUNTS_ALARMS                                  0x01
#define AX_MSGQ_TOPIC_ALARM_PROCESSING                               0x02
/*                                  ...                                 */
#define AX_MSGQ_LAST                                                 0x02

/*************************************************************************
 * Global Type definitions
 *************************************************************************/

/*************************************************************************/
/************************ Message Header *********************************/
/*************************************************************************/
typedef AX_UINT32 AX_MSG_MSGID_TYPE;

struct axMessageHeaderBase_s {
  AX_MSG_MSGID_TYPE  msgId;    /* a unique correlator */
  AX_UINT8           msgVersion;
  AX_UINT8           msgTopic;
  AX_UINT8           msgSourceSubSystemId;
  AX_UINT8           unused1;

  axMessageHeaderBase_s() : msgId(0), msgVersion(0), msgTopic(0),
    msgSourceSubSystemId(0), unused1(0)
  {
  }

};


struct axMessageHeader_s : public axMessageHeaderBase_s {

  axMessageHeader_s()
  { 
  }

};

/*************************************************************************/
/************************ Overall Message Payload ************************/
/*************************************************************************/
struct axSpecificMsgPayloadBase_s {
};

struct axMessagePayloadBase_s {
  axSpecificMsgPayloadBase_s  * specificPayloadData;

  axMessagePayloadBase_s() : specificPayloadData(NULL)
  {
  }

  virtual ~axMessagePayloadBase_s()
  {
    if (specificPayloadData) {
      delete specificPayloadData;
    }
  }
};


struct axMessagePayload_s : public axMessagePayloadBase_s {
};


/*************************************************************************/
/**************************** Message ************************************/
/*************************************************************************/

struct axMessageBase_s {
  axMessageHeader_s    msgHeader;
  axMessagePayload_s * msgPayload;

  axMessageBase_s() : msgPayload(NULL)
  {
  }
  virtual ~axMessageBase_s()
  {
    if (msgPayload) {
      delete msgPayload;
    }
  }
};

struct axMessage_s : public axMessageBase_s {
};


/*************************************************************************/
/************************ Specific Message Payloads **********************/
/************************ Begin from here-on        **********************/
/*************************************************************************/
struct axCountsAndAlarmsPayload_s : public axSpecificMsgPayloadBase_s {
  AX_UINT32 cmtsResId;
  AX_UINT8  countsType;

  axCountsAndAlarmsPayload_s() : cmtsResId(0), countsType(0)
  {
  }
};

struct axAlarmProcessingPayload_s : public axSpecificMsgPayloadBase_s {
  axAbstractCAAlarm * alarm;

  axAlarmProcessingPayload_s() : alarm(NULL)
  {
  }
};


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axMessageDataTypes_hpp_ */

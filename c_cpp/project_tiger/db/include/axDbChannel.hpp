
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbChannel_hpp_
#define _axDbChannel_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"
#include "axListBase.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axInternalChannel;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbChannel.hpp
 * 
 * Design Document:
 * 
 * System:
 *  
 * Sub-system:
 * 
 * History:
 * 
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 * 
 */

class axDbChannel : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicChannelQuery;
  static AX_INT8 * CmtsChannelQuery;
  static AX_INT8 * CmtsChannelQueryStartup;
  static AX_INT8 * BasicChannelUpdate;
  static AX_INT8 * BasicChannelInsert;

  /// default constructor
  axDbChannel();

  /**
   * IN: Channel Res ID
   */
  axDbChannel(DB_RESID_t);

  /**
   * IN: Channel Index, CMTS Res ID
   */
  axDbChannel(AX_UINT32, DB_RESID_t);

  /**
   * IN: Channel Name, CMTS Res ID
   */
  axDbChannel(AX_INT8 *, DB_RESID_t);

  /**
   * IN: Internal Channel, CMTS Res ID
   */
  axDbChannel(axInternalChannel *, DB_RESID_t);

  /**
   * Copy constructor:
   */
  axDbChannel(const axDbChannel &);

  /// destructor
  virtual ~axDbChannel();

  // ***********************************
  // data members begin ...
  // ***********************************

  //
  DB_OBJ_AUTO_INC_ID_t m_id;

  //
  DB_RESID_t m_channelResId;

  //
  DB_RESID_t m_cmtsResId;

  //
  AX_UINT32  m_channelIndex;

  //
  DB_UPDATE_TIME_t   m_lastUpdated;

  //
  DB_CHANNEL_NAME_t  m_channelName;

  //
  AX_UINT8  m_channelType;

  //
  AX_UINT8  m_alertLevel;

  //
  AX_UINT8  m_deleted;

  //
  AX_UINT16  m_downstreamPower;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual AX_UINT8 getResourceType(void);

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

private:

  void instantiationInit(void);

};

#endif // _axDbChannel_hpp_

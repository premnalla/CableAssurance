/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public final class GlobalQueries {

	/* Channel */
	
	public static final String QRY_ST_OFFLINE_CM_IN_CHANNEL =
		"SELECT cm.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state in (3,7) and c.channel_res_id=" ;
	
	public static final String QRY_ST_ONLINE_CM_IN_CHANNEL =
		"SELECT cm.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state=6 and c.channel_res_id=" ;
	
	public static final String QRY_ST_OTHER_CM_IN_CHANNEL =
		"SELECT cm.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state not in (6,3,7) and c.channel_res_id=" ;
	
	public static final String QRY_OOS_CM_IN_CHANNEL = 
		"SELECT distinct cm.* from channel c join cable_modem cm using (channel_res_id) "
		+ "left outer join emta mta using (cm_res_id) "
		+ "join service_outage_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and c.channel_res_id="; 
	
	public static final String QRY_INEXCEP_CM_IN_CHANNEL =
		"SELECT distinct cm.* from channel c join cable_modem cm using (channel_res_id) "
			+ "left outer join emta mta using (cm_res_id) "
			+ "join exception_basic a on (cm.cm_res_id=a.resource_res_id) where "
			+ "mta.cm_res_id IS NULL and c.channel_res_id="; 
	
	public static final String QRY_INALARM_CM_IN_CHANNEL = 
		"SELECT distinct cm.* from channel c join cable_modem cm using (channel_res_id) "
		+ "left outer join emta mta using (cm_res_id) "
		+ "join alarm_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and c.channel_res_id=";
	
	public static final String QRY_CM_IN_UP_CHANNEL =
		"SELECT cm.* FROM cable_modem cm left outer join emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and cm.upstream_chnl_res_id=" ;
	
	public static final String QRY_CM_AND_MTACM_IN_CHANNEL =
		"SELECT cm.* FROM channel c join cable_modem cm using (channel_res_id) where c.channel_res_id=" ;
	
	public static final String QRY_INALARM_MTA_IN_CHANNEL =
		"SELECT distinct mta.* from channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) join alarm_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where c.channel_res_id=" ;

	public static final String QRY_INEXCEP_MTA_IN_CHANNEL =
		"SELECT distinct mta.* from channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) join exception_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where c.channel_res_id=" ;
	
	public static final String QRY_OOS_MTA_IN_CHANNEL =
		"SELECT distinct mta.* from channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) join service_outage_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where c.channel_res_id=" ;
	
	public static final String QRY_MTA_IN_UP_CHANNEL = 
		"SELECT mta.* FROM emta mta join cable_modem cm using (cm_res_id) "
		+ "where cm.upstream_chnl_res_id=" ;
	
	public static final String QRY_AVAIL_MTA_IN_CHANNEL =
		"SELECT mta.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) where mta.available=1 and c.channel_res_id=" ;
	
	public static final String QRY_UNAVAIL_MTA_IN_CHANNEL =
		"SELECT mta.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) where mta.available=0 and c.channel_res_id=" ;
	
	public static final String QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_CHANNEL =
		"SELECT mta.* FROM channel c join cable_modem cm using (channel_res_id) "
		+ "join emta mta using (cm_res_id) where (mta.available is null or mta.available not in (0,1)) "
		+ "and c.channel_res_id=" ;
	
	/* HFC */
	
	public static final String QRY_ST_OFFLINE_CM_IN_HFC = 
		"SELECT cm.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state in (3,7) and h.hfc_res_id=" ;
	
	public static final String QRY_ST_ONLINE_CM_IN_HFC = 
		"SELECT cm.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state=6 and h.hfc_res_id=" ;
	
	public static final String QRY_ST_OTHER_CM_IN_HFC = 
		"SELECT cm.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and "
		+ "cm.docsis_state not in (6,3,7) and h.hfc_res_id=" ;

	public static final String QRY_CM_RESID_IN_HFC = 
		"SELECT cm.cm_res_id FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL and h.hfc_res_id=" ;
	
	public static final String QRY_CM_AND_MTA_CM_IN_HFC = 
		"SELECT cm.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) where h.hfc_res_id=" ;
	
	public static final String QRY_INALARM_CM_IN_HFC = 
		"SELECT distinct cm.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) "
		+ "join alarm_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and h.hfc_res_id=" ;
	
	public static final String QRY_INEXCEP_CM_IN_HFC = 
		"SELECT distinct cm.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) "
		+ "join exception_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and h.hfc_res_id=" ;

	public static final String QRY_OOS_CM_IN_HFC = 
		"SELECT distinct cm.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "left outer join emta mta using (cm_res_id) "
		+ "join service_outage_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and h.hfc_res_id=" ;

	public static final String QRY_MTA_RESID_IN_HFC =
		"SELECT mta.emta_res_id FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) where h.hfc_res_id=" ;

	public static final String QRY_INALARM_MTA_IN_HFC =
		"SELECT distinct mta.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) join alarm_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where h.hfc_res_id=" ;

	public static final String QRY_INEXCEP_MTA_IN_HFC =
		"SELECT distinct mta.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) join exception_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where h.hfc_res_id=" ;

	public static final String QRY_OOS_MTA_IN_HFC = 
		"SELECT distinct mta.* from hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) join service_outage_basic a on (mta.emta_res_id=a.resource_res_id) "
		+ "where h.hfc_res_id=" ;

	public static final String QRY_AVAIL_MTA_IN_HFC =
		"SELECT mta.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) where mta.available=1 and h.hfc_res_id=" ;

	public static final String QRY_UNAVAIL_MTA_IN_HFC =
		"SELECT mta.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) where mta.available=0 and h.hfc_res_id=" ;

	public static final String QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_HFC =
		"SELECT mta.* FROM hfc_plant h join cable_modem cm using (hfc_res_id) "
		+ "join emta mta using (cm_res_id) where (mta.available is null or mta.available not in (0,1)) "
		+ "and h.hfc_res_id=" ;

	/* CMTS */
	
	public static final String QRY_INALARM_HFC_IN_CMTS =
		"SELECT distinct h.* from cmts c join hfc_plant h using (cmts_res_id) "
		+ "join alarm_basic a on (h.hfc_res_id=a.resource_res_id) where c.cmts_res_id=" ;

	public static final String QRY_INALARM_CHANNEL_IN_CMTS = 
		"SELECT distinct ch.* from cmts c join channel ch using (cmts_res_id) "
		+ "join alarm_basic a on (ch.channel_res_id=a.resource_res_id) where c.cmts_res_id=" ;

	public static final String QRY_CM_AND_MTA_CM_IN_CMTS = 
		"SELECT cm.* FROM cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) where c.cmts_res_id=" ;

	public static final String QRY_ST_OFFLINE_CM_IN_CMTS = 
		"SELECT cm.* FROM cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and cm.docsis_state in (3,7) and c.cmts_res_id=" ;

	public static final String QRY_ST_ONLINE_CM_IN_CMTS = 
		"SELECT cm.* FROM cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and cm.docsis_state=6 and c.cmts_res_id=" ;

	public static final String QRY_ST_OTHER_CM_IN_CMTS = 
		"SELECT cm.* FROM cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and cm.docsis_state not in (6,3,7) and c.cmts_res_id=" ;

	public static final String QRY_INALARM_CM_IN_CMTS =
		"SELECT distinct cm.* from cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "join alarm_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and c.cmts_res_id=" ;

	public static final String QRY_INEXCEP_CM_IN_CMTS =
		"SELECT distinct cm.* from cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
		+ "join exception_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and c.cmts_res_id=" ;

	public static final String QRY_OOS_CM_IN_CMTS =
		"SELECT distinct cm.* from cmts c join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "join service_outage_basic a on (cm.cm_res_id=a.resource_res_id) where "
		+ "mta.cm_res_id IS NULL and c.cmts_res_id=" ;

	public static final String QRY_INALARM_MTA_IN_CMTS =
		"SELECT distinct mta.* from cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "alarm_basic a on (mta.emta_res_id=a.resource_res_id) where c.cmts_res_id=" ;

	public static final String QRY_INEXCEP_MTA_IN_CMTS =
		"SELECT distinct mta.* from cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "exception_basic a on (mta.emta_res_id=a.resource_res_id) where c.cmts_res_id=" ;

	public static final String QRY_OOS_MTA_IN_CMTS =
		"SELECT distinct mta.* from cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "service_outage_basic a on (mta.emta_res_id=a.resource_res_id) where c.cmts_res_id=" ;

	public static final String QRY_AVAIL_MTA_IN_CMTS = 
		"SELECT mta.* FROM cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
		+ "mta.available=1 and c.cmts_res_id=" ;

	public static final String QRY_UNAVAIL_MTA_IN_CMTS = 
		"SELECT mta.* FROM cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
		+ "mta.available=0 and c.cmts_res_id=" ;

	public static final String QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_CMTS = 
		"SELECT mta.* FROM cmts c join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
		+ "(mta.available is null or mta.available not in (0,1)) and c.cmts_res_id=" ;
	
	public static final String QRY_OOS_HFC_IN_CMTS = 
		"SELECT distinct h.* from hfc_plant h join cmts c using (cmts_res_id) "
		+ "join service_outage_basic s on (h.hfc_res_id=s.resource_res_id) where "
		+ "c.cmts_res_id="; 
	
	public static final String QRY_INEXCEP_HFC_IN_CMTS =
		"SELECT distinct h.* from hfc_plant h join cmts c using (cmts_res_id) "
		+ "join exception_basic e on (h.hfc_res_id=e.resource_res_id) where "
		+ "c.cmts_res_id="; 

	public static final String QRY_OOS_CHANNEL_IN_CMTS = 
		"SELECT distinct h.* from channel h join cmts c using (cmts_res_id) "
		+ "join service_outage_basic s on (h.channel_res_id=s.resource_res_id) where "
		+ "c.cmts_res_id="; 
	
	public static final String QRY_INEXCEP_CHANNEL_IN_CMTS =
		"SELECT distinct h.* from channel h join cmts c using (cmts_res_id) "
		+ "join exception_basic e on (h.channel_res_id=e.resource_res_id) where "
		+ "c.cmts_res_id="; 

	/* Market */
	
	public static final String QRY_CMTS_IN_MARKET =
		"SELECT c.* FROM market m join blade b using (market_res_id) "
        + "join cmts c using (blade_res_id) where m.market_res_id=" ;

	public static final String QRY_ST_ONLINE_CMTS_IN_MARKET =
		"SELECT c.* FROM market m join blade b using (market_res_id) "
        + "join cmts c using (blade_res_id) where c.online_state=1 and m.market_res_id=" ;

	public static final String QRY_ST_NOT_ONLINE_CMTS_IN_MARKET =
		"SELECT c.* FROM market m join blade b using (market_res_id) "
        + "join cmts c using (blade_res_id) where c.online_state is not 1 and m.market_res_id=" ;

	public static final String QRY_INALARM_CMTS_IN_MARKET =
		"SELECT distinct c.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join alarm_basic a on (c.cmts_res_id=a.resource_res_id) "
		+ "where m.market_res_id=";

	public static final String QRY_HFC_IN_MARKET =
		"SELECT h.* FROM market m join blade b using (market_res_id) "
  	  + "join cmts c using (blade_res_id) join hfc_plant h using (cmts_res_id) "
  	  + "where m.market_res_id=" ;

	public static final String QRY_INALARM_HFC_IN_MARKET =
		"SELECT distinct h.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join hfc_plant h using (cmts_res_id) "
		+ "join alarm_basic a on (h.hfc_res_id=a.resource_res_id) where m.market_res_id=" ;

	public static final String QRY_CHANNEL_IN_MARKET =
		"SELECT ch.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
    	+ "where m.market_res_id=" ;

	public static final String QRY_INALARM_CHANNEL_IN_MARKET =
		"SELECT distinct ch.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
		+ "join alarm_basic a on (ch.channel_res_id=a.resource_res_id) where m.market_res_id=" ;

	public static final String QRY_CM_IN_MARKET = 
		"SELECT cm.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
    	+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
    	+ "where mta.cm_res_id is NULL and m.market_res_id=" ;

	public static final String QRY_ST_OFFLINE_CM_IN_MARKET = 
		"SELECT cm.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
    	+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
    	+ "where mta.cm_res_id is NULL and cm.docsis_state in (3,7) and m.market_res_id=" ;

	public static final String QRY_ST_ONLINE_CM_IN_MARKET = 
		"SELECT cm.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
    	+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
    	+ "where mta.cm_res_id is NULL and cm.docsis_state=6 and m.market_res_id=" ;

	public static final String QRY_ST_OTHER_CM_IN_MARKET = 
		"SELECT cm.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
    	+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
    	+ "where mta.cm_res_id is NULL and cm.docsis_state not in (6,3,7) and m.market_res_id=" ;

	public static final String QRY_INALARM_CM_IN_MARKET =
		"SELECT distinct cm.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
		+ "join alarm_basic a on (cm.cm_res_id=a.resource_res_id) "
		+ "where mta.cm_res_id IS NULL and m.market_res_id=" ;

	public static final String QRY_INEXCEP_CM_IN_MARKET = 
		"SELECT distinct cm.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id) "
		+ "join exception_basic e on (cm.cm_res_id=e.resource_res_id) "
		+ "where mta.cm_res_id IS NULL and m.market_res_id=" ;

	public static final String QRY_OOS_CM_IN_MARKET =
		"SELECT distinct cm.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) "
		+ "join cable_modem cm using (channel_res_id) left outer join emta mta using (cm_res_id)"
		+ "join service_outage_basic e on (cm.cm_res_id=a.resource_res_id) "
		+ "where mta.cm_res_id IS NULL and m.market_res_id=" ;

	public static final String QRY_MTA_IN_MARKET = 
		"SELECT mta.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
    	+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where m.market_res_id=" ;

	public static final String QRY_INALARM_MTA_IN_MARKET =
		"SELECT distinct mta.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "alarm_basic a on (mta.emta_res_id=a.resource_res_id) where m.market_res_id=" ;

	public static final String QRY_INEXCEP_MTA_IN_MARKET = 
		"SELECT distinct mta.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "exception_basic e on (mta.emta_res_id=e.resource_res_id) where m.market_res_id=" ;

	public static final String QRY_OOS_MTA_IN_MARKET =
		"SELECT distinct mta.* from market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
		+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join "
		+ "service_outage_basic e on (mta.emta_res_id=e.resource_res_id) where m.market_res_id=" ;

	public static final String QRY_CMTS_RESID_IN_MARKET =
		"SELECT c.cmts_res_id FROM market m join blade b using (market_res_id) "
		+ "join cmts c using (blade_res_id) WHERE market_res_id=" ;

	public static final String QRY_AVAIL_MTA_IN_MARKET = 
		"SELECT mta.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
    	+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
    	+ "mta.available=1 and m.market_res_id=" ;

	public static final String QRY_UNAVAIL_MTA_IN_MARKET = 
		"SELECT mta.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
    	+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
    	+ "mta.available=0 and m.market_res_id=" ;

	public static final String QRY_NOTAVAIL_NOTUNAVAIL_MTA_IN_MARKET = 
		"SELECT mta.* FROM market m join blade b using (market_res_id) "
    	+ "join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join "
    	+ "cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) where "
    	+ "(mta.available is null or mta.available not in (0,1)) and m.market_res_id=" ;

	public static final String QRY_OOS_HFC_IN_MARKET = 
		"SELECT distinct h.* from hfc_plant h join cmts c using (cmts_res_id) "
		+ "join market m using (market_res_id) "
		+ "join service_outage_basic s on (h.hfc_res_id=s.resource_res_id) where "
		+ "m.market_res_id="; 
	
	public static final String QRY_INEXCEP_HFC_IN_MARKET =
		"SELECT distinct h.* from hfc_plant h join cmts c using (cmts_res_id) "
		+ "join market m using (market_res_id) "
		+ "join exception_basic e on (h.hfc_res_id=e.resource_res_id) where "
		+ "m.market_res_id="; 

	public static final String QRY_OOS_CHANNEL_IN_MARKET = 
		"SELECT distinct h.* from channel h join cmts c using (cmts_res_id) "
		+ "join market m using (market_res_id) "
		+ "join service_outage_basic s on (h.channel_res_id=s.resource_res_id) where "
		+ "m.market_res_id="; 
	
	public static final String QRY_INEXCEP_CHANNEL_IN_MARKET =
		"SELECT distinct h.* from channel h join cmts c using (cmts_res_id) "
		+ "join market m using (market_res_id) "
		+ "join exception_basic e on (h.channel_res_id=e.resource_res_id) where "
		+ "m.market_res_id="; 

	/* Enterprise */
	public static final String QRY_CMTS_IN_ENTERPRISE = "select * from cmts";
	public static final String QRY_HFC_IN_ENTERPRISE = "select * from hfc_plant";
	public static final String QRY_CHANNEL_IN_ENTERPRISE = "select * from channel";
	public static final String QRY_CM_IN_ENTERPRISE = "select * from cable_modem";
	public static final String QRY_MTA_IN_ENTERPRISE = "select * from emta";
	
	/* Alarms */
	
	public static final String QRY_CURR_ALARMS = 
		"select ra.*, ca.*, aty.*, ast.* from caalarm.current_alarm ca join caalarm.root_alarm ra using(root_alarm_id) "
		+ "left outer join caalarm.alarm_types aty on (ra.alarm_type=aty.type and ra.alarm_sub_type=aty.sub_type) "
		+ "left outer join caalarm.alarm_states ast on (ca.alarm_state=ast.state) ";
	
	public static final String QRY_CURR_ALARM_POSTFIX = " order by ca.id desc";
	
	public static final String QRY_CURR_ALARM = 
		"select ra.*, ca.*, aty.*, ast.* from caalarm.current_alarm ca join caalarm.root_alarm ra using(root_alarm_id) "
		+ "left outer join caalarm.alarm_types aty on (ra.alarm_type=aty.type and ra.alarm_sub_type=aty.sub_type) "
		+ "left outer join caalarm.alarm_states ast on (ca.alarm_state=ast.state) where ca.root_alarm_id=";
	
	public static final String QRY_HIST_ALARMS = 
		"select ra.*, ha.*, aty.*, ast.* from caalarm.historical_alarm ha join caalarm.root_alarm ra using(root_alarm_id) "
		+ "left outer join caalarm.alarm_types aty on (ra.alarm_type=aty.type and ra.alarm_sub_type=aty.sub_type) "
		+ "left outer join caalarm.alarm_states ast on (ha.alarm_state=ast.state) ";
	
	public static final String QRY_HIST_ALARM_POSTFIX = " order by ha.id desc";
	
	public static final String QRY_HIST_ALARM = 
		"select ra.*, ha.*, aty.*, ast.* from caalarm.historical_alarm ha join caalarm.root_alarm ra using(root_alarm_id) "
		+ "left outer join caalarm.alarm_types aty on (ra.alarm_type=aty.type and ra.alarm_sub_type=aty.sub_type) "
		+ "left outer join caalarm.alarm_states ast on (ha.alarm_state=ast.state) where ha.root_alarm_id=";
	
	public static final String QRY_ALARM_DETAILS =
		"select * from caalarm.alarm_details where root_alarm_id=";
		
	public static final String QRY_ALARM_TYPES =
		"select * from caalarm.alarm_types ";
	
	/* Topology */
	
	public static final String UPD_LOCAL_SYSTEM = 
		"update canet.local_system set ";

	public static final String INS_MARKET_IN_LOCAL_SYSTEM = 
		"insert into canet.market(name,ip_address) values";

	public static final String DEL_MARKET_IN_LOCAL_SYSTEM = 
		"update canet.market set is_deleted=1 where market_id=";

	public static final String UPD_MARKET_IN_LOCAL_SYSTEM = 
		"update canet.market set ";

	public static final String INS_BLADE_IN_LOCAL_SYSTEM = 
		"insert into canet.blade(name,ip_address) values";

	public static final String DEL_BLADE_IN_LOCAL_SYSTEM = 
		"update canet.blade set is_deleted=1 where blade_id=";

	public static final String UPD_BLADE_IN_LOCAL_SYSTEM = 
		"update canet.blade set ";

	public static final String QRY_CMTS_IN_LOCAL_SYSTEM = 
		"select c.*, cc.* from canet.cmts c left outer join "
		+ "caperf.cmts_current_counts cc using(cmts_res_id) ";
	
	public static final String UPD_CMTS_IN_LOCAL_SYSTEM = 
		"update canet.cmts set ";

	public static final String INS_CMTS_IN_LOCAL_SYSTEM_1 = 
		"insert into canet.cmts(cmts_res_id,name,ip_address) values";

	public static final String INS_CMTS_IN_LOCAL_SYSTEM_2 = 
		"insert into caperf.cmts_current_counts(cmts_res_id,last_log_tm) values";

	public static final String INS_CMTS_IN_LOCAL_SYSTEM_3 = 
		"insert into canet.cmts_mta_v2c_attributes (cmts_res_id) values(";

	public static final String INS_CMTS_IN_LOCAL_SYSTEM_4 = 
		"insert into canet.cmts_cm_v2c_attributes (cmts_res_id) values(";

	public static final String INS_CMTS_IN_LOCAL_SYSTEM_5 = 
		"insert into canet.cmts_v2c_attributes (cmts_res_id) values(";

	public static final String DEL_CMTS_IN_LOCAL_SYSTEM = 
		"update canet.cmts set is_deleted=1 where cmts_res_id=";

	public static final String QRY_CMTS_SNMP_V2C_ATTR = 
		"select * from canet.cmts_v2c_attributes where cmts_res_id=";

	public static final String QRY_CM_SNMP_V2C_ATTR = 
		"select * from canet.cmts_cm_v2c_attributes where cmts_res_id=";

	public static final String QRY_MTA_SNMP_V2C_ATTR = 
		"select * from canet.cmts_mta_v2c_attributes where cmts_res_id=";

		public static final String QRY_SNMP_V2C_ATTRS_FOR_CMTS = 
		"select cmts.*,cm.*,mta.* from canet.cmts c left outer join "
		+ "canet.cmts_v2c_attributes cmts using (cmts_res_id) left outer join "
		+ "canet.cmts_cm_v2c_attributes cm using (cmts_res_id) left outer join "
		+ "canet.cmts_mta_v2c_attributes mta using (cmts_res_id) where c.cmts_res_id=";
	
	public static final String QRY_CMS_IN_LOCAL_SYSTEM = 
		"select c.* from canet.cms c ";
	
	public static final String UPD_CMS_IN_LOCAL_SYSTEM = 
		"update canet.cms set ";

	public static final String INS_CMS_IN_LOCAL_SYSTEM = 
		"insert into canet.cms(cms_res_id,name,ip_address) values";

	public static final String DEL_CMS_IN_LOCAL_SYSTEM = 
		"update canet.cms set is_deleted=1 where cms_res_id=";

	public static final String QRY_CHANNEL =
		"SELECT ch.*,cc.* FROM canet.channel ch left outer join "
		+ "caperf.channel_current_counts cc using (channel_res_id) " ;

	public static final String QRY_HFC = 
		"SELECT h.*,cc.* FROM canet.hfc_plant h left outer join "
		+ "caperf.hfc_current_counts cc using (hfc_res_id) " ;

	public static final String QRY_CM_STATUS_LOG = 
		"SELECT tm_sec,docsis_state FROM caperf.cm_status_log WHERE cm_res_id=";
	
	public static final String QRY_CM_PERF_LOG = 
		"select tm_sec,downstream_snr,downstream_power,upstream_power,t1_timeouts, "
		+ "t2_timeouts,t3_timeouts,t4_timeouts from caperf.cm_perf_log where cm_res_id=";
	
	public static final String QRY_CMTS_COUNTS_LOG = 
		"select tm_sec,cm_total,cm_online,mta_total,mta_available from "
		+ "caperf.cmts_counts_log where cmts_res_id=";
		
	public static final String QRY_CHANNEL_COUNTS_LOG = 
		"select tm_sec,cm_total,cm_online,mta_total,mta_available from "
		+ "caperf.channel_counts_log where channel_res_id=";
		
	public static final String QRY_HFC_COUNTS_LOG = 
		"select tm_sec,cm_total,cm_online,mta_total,mta_available from "
		+ "caperf.hfc_counts_log where hfc_res_id=";
		
	public static final String QRY_MTA_PROV_STATUS_LOG = 
		"select tm_sec,prov_state from caperf.mta_prov_status_log where mta_res_id=";
	
	public static final String QRY_MTA_PING_STATUS_LOG = 
		"select tm_sec,ping_state from caperf.mta_ping_status_log where mta_res_id=";
	
	public static final String QRY_MTA_AVAILABILITY_LOG = 
		"select tm_sec,availability from caperf.mta_availability_log where mta_res_id=";
	
	/* NEW 01-31-07 */
	
	public static final String QRY_MTA_IN_CMTS = 
		"SELECT mta.* FROM canet.cmts c join canet.cable_modem cm using (cmts_res_id) "
		+ "join canet.emta mta using (cm_res_id) where c.cmts_res_id=" ;

	public static final String QRY_CM_IN_CMTS = 
		"SELECT cm.* FROM canet.cmts c join canet.cable_modem cm using (cmts_res_id) "
		+ "left outer join canet.emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and c.cmts_res_id=" ;

	public static final String QRY_CM_IN_HFC = 
		"SELECT cm.* FROM canet.cable_modem cm left outer join canet.emta mta using (cm_res_id) "
		+ "where mta.cm_res_id IS NULL and cm.hfc_res_id=" ;
	
	public static final String QRY_MTA_IN_HFC =
		"SELECT mta.* FROM emta mta join cable_modem cm using (cm_res_id) where cm.hfc_res_id=" ;

	public static final String QRY_CURRENT_CM_STATUS =
		"SELECT c.* FROM caperf.cm_current_status c where c.cm_res_id=" ;

	public static final String QRY_CURRENT_HFC_STATUS =
		"SELECT h.* FROM caperf.hfc_current_alarm h where h.hfc_res_id=" ;

	public static final String QRY_CURRENT_MTA_AVAILABILITY =
		"SELECT m.* FROM caperf.mta_current_avail m where m.mta_res_id=" ;

	/* User authentication, authorization and access privileges */
	
	public static final String QRY_USER_ACCESS_TYPES =
		"SELECT at.* FROM caappuser.sys_access at ";

	public static final String QRY_USER_ACCESS_TYPES_BY_NAME =
		"SELECT at.* FROM caappuser.sys_access at where at.sys_access_name=";

	public static final String INS_USER_ACCESS_TYPE =
		"insert into caappuser.sys_access(sys_access_name) values(";

	public static final String QRY_APP_DOMAIN_TYPES =
		"SELECT adt.* FROM caappuser.sys_object adt ";

	public static final String QRY_APP_DOMAIN_TYPE_BY_NAME =
		"SELECT adt.* FROM caappuser.sys_object adt where adt.sys_object_name=";

	public static final String INS_APP_DOMAIN =
		"INSERT into caappuser.sys_object(sys_object_name) values(";

	public static final String QRY_ROLES =
		"SELECT ur.* FROM caappuser.user_role ur ";
//		"SELECT ur.*, so.*, sa.* FROM caappuser.role_oa_map rm "
//		+ "left join caappuser.user_role ur using (role_id) "
//	    + "left join caappuser.sys_object so using (sys_object_id) "
//	    + "left join caappuser.sys_access sa using (sys_access_id) ";

	public static final String QRY_ROLE_BY_NAME =
		"SELECT ur.* FROM caappuser.user_role ur where ur.role_name=";

	public static final String QRY_ROLE_BY_ID =
		"SELECT ur.* FROM caappuser.user_role ur where ur.role_id=";

	public static final String INS_ROLE =
		"INSERT into caappuser.user_role(role_name) values(";

	public static final String QRY_USERS =
		"SELECT u.*, r.role_name FROM caappuser.app_user u left outer join "
		+ "caappuser.user_role r using (role_id) " ;
	
	public static final String QRY_ROLE_OA_PAIRS =
		"SELECT r.role_name, o.sys_object_name, a.sys_access_name from "
		+ "caappuser.role_oa_map rm left join caappuser.user_role r using (role_id) "
		+ "left join caappuser.sys_object o on (o.sys_object_id=rm.sys_object_id) "
		+ "left join caappuser.sys_access a on (a.sys_access_id=rm.sys_access_id) "
		+ "where rm.role_id!=0";

	public static final String QRY_ROLE_OA =
		"SELECT r.*, o.*, a.* from "
		+ "caappuser.user_role r left outer join caappuser.role_oa_map rm using (role_id) "
		+ "left outer join caappuser.sys_object o using (sys_object_id) "
		+ "left outer join caappuser.sys_access a using (sys_access_id) ";
	
	public static final String QRY_ROLE_OA_POSTFIX = " order by r.role_id";

	public static final String QRY_ROLE_OA_PAIRS_BY_VALUES =
		"SELECT r.role_name, o.sys_object_name, a.sys_access_name from "
		+ "caappuser.role_oa_map rm left join caappuser.user_role r using (role_id) "
		+ "left join caappuser.sys_object o on (o.sys_object_id=rm.sys_object_id) "
		+ "left join caappuser.sys_access a on (a.sys_access_id=rm.sys_access_id) "
		+ "where ";

	public static final String INS_ROLE_OA_PAIR =
		"insert into caappuser.role_oa_map(role_id,sys_object_id,sys_access_id) values(";

	public static final String INS_ROLE_OA =
		"insert into caappuser.role_oa_map(role_id,sys_object_id,sys_access_id) values(";

	public static final String DEL_ROLE_OA =
		"delete from caappuser.role_oa_map where ";

//	public static final String QRY_USER_OA_PAIRS =
//		"SELECT u.user_id, o.sys_object_name, a.sys_access_name from "
//		+ "caappuser.user_oa_map um left join caappuser.app_user u using (app_user_id) "
//		+ "left join caappuser.sys_object o on (o.sys_object_id=um.sys_object_id) "
//		+ "left join caappuser.sys_access a on (a.sys_access_id=um.sys_access_id) "
//		+ "where um.app_user_id!=0";

//	public static final String QRY_USER_OA_PAIRS_BY_VALUES =
//		"SELECT u.user_id, o.sys_object_name, a.sys_access_name from "
//		+ "caappuser.user_oa_map um left join caappuser.app_user u using (app_user_id) "
//		+ "left join caappuser.sys_object o on (o.sys_object_id=um.sys_object_id) "
//		+ "left join caappuser.sys_access a on (a.sys_access_id=um.sys_access_id) "
//		+ "where ";

//	public static final String INS_USER_OA_PAIR =
//		"insert into caappuser.user_oa_map(app_user_id,sys_object_id,sys_access_id) values(";

	public static final String QRY_USER_ROLES =
		"SELECT u.user_id, r.role_name from "
		+ "caappuser.app_user u left join caappuser.user_role r using (role_id) "
		+ "where u.app_user_id!=0";

	public static final String QRY_USER_ROLES_BY_VALUES =
		"SELECT u.user_id, r.role_name from "
		+ "caappuser.app_user u left join caappuser.user_role r using (role_id) "
		+ "where ";

//	public static final String INS_USER_ROLES =
//		"insert into caappuser.user_role_map(app_user_id,role_id) values(";

	public static final String QRY_USER =
		"SELECT u.*, ur.role_name FROM caappuser.app_user u left outer join "
		+ "caappuser.user_role ur using (role_id) where u.app_user_id=" ;
	
	public static final String QRY_USER_BY_USERID =
		"SELECT u.*, ur.role_name FROM caappuser.app_user u left outer join "
		+ "caappuser.user_role ur using (role_id) where u.user_id=" ;
	
//	public static final String INS_USER =
//		"insert into caappuser.app_user(first_name,last_name,middle_initial,user_id,user_pw,user_location) values(" ;
	
	public static final String QRY_GET_USER_BY_USER_PW =
		"SELECT u.*, ur.role_name FROM caappuser.app_user u left outer join "
		+ "caappuser.user_role ur using (role_id) where u.user_id='%a%' and u.user_pw=PASSWORD('%b%')" ;
	
	public static final String QRY_GET_USER_ROLE_BY_USERID =
		"SELECT ur.role_name from caappuser.app_user u "
		+ "left outer join caappuser.user_role ur using(role_id) "
		+ "where u.user_id=";
	
	public static final String QRY_GET_OAPAIRS_BY_ROLE =
		"SELECT so.sys_object_name, sa.sys_access_name from caappuser.user_role ur "
		+ "left join caappuser.role_oa_map rm using(role_id) "
		+ "left join caappuser.sys_object so using (sys_object_id) "
		+ "left join caappuser.sys_access sa using (sys_access_id) "
		+ "where ur.role_name=";
	
//	public static final String QRY_GET_OAPAIRS_BY_USERID =
//		"SELECT so.sys_object_name, sa.sys_access_name from caappuser.app_user au "
//		+ "left join caappuser.user_oa_map uoam using(app_user_id) "
//		+ "left join caappuser.sys_object so using (sys_object_id) "
//		+ "left join caappuser.sys_access sa using (sys_access_id) "
//		+ "where au.user_id=";
	
	/*
	 * Summary Tables
	 */
	
	public static final String QRY_MTA_STATUS_SUMMARY = 
		"select m.mta_res_id,sum(m.sum_avail_tm) as sum_avail_tm,"
		+ "sum(m.sum_unavail_tm) as sum_unavail_tm,sum(m.state_changes) as sum_state_changes,"
		+ "em.mac_address,hfc.hfc_res_id,hfc.name,cmts.cmts_res_id,cmts.name from "
		+ "casumm.mta_status_summary m "
		+ "left outer join canet.emta em on (m.mta_res_id=em.emta_res_id) "
		+ "left outer join canet.cable_modem cm using (cm_res_id) "
		+ "left outer join canet.hfc_plant hfc using (hfc_res_id) "
		+ "left outer join canet.cmts cmts on (cm.cmts_res_id=cmts.cmts_res_id) "
		+ "where ";
	
	public static final String QRY_MTA_STATUS_SUMM_GRPBY = " group by m.mta_res_id ";
	public static final String QRY_MTA_STATUS_SUMM_ORDERBY_1 = " order by sum_unavail_tm desc ";
	public static final String QRY_MTA_STATUS_SUMM_ORDERBY_2 = " order by sum_state_changes desc ";
	
	public static final String QRY_MTA_STATUS_SUMMARY_FOR_CMTS = 
		QRY_MTA_STATUS_SUMMARY;
	
	public static final String QRY_MTA_STATUS_SUMMARY_FOR_HFC = 
		QRY_MTA_STATUS_SUMMARY;
	
	public static final String QRY_CM_TCA_SUMMARY = 
		"select m.cm_res_id,sum(m.sum_tcafree_tm) as sum_tcafree_tm,"
		+ "sum(m.sum_tca_tm) as sum_tca_tm,sum(m.state_changes) as sum_state_changes,"
		+ "cm.mac_address,hfc.hfc_res_id,hfc.name,cmts.cmts_res_id,cmts.name from "
		+ "casumm.cm_perf_thresh_summ m "
		+ "left outer join canet.cable_modem cm using (cm_res_id) "
		+ "left outer join canet.hfc_plant hfc using (hfc_res_id) "
		+ "left outer join canet.cmts cmts on (cm.cmts_res_id=cmts.cmts_res_id) "
		+ "where ";
	
	public static final String QRY_CM_TCA_SUMM_GRPBY = " group by m.cm_res_id ";
	public static final String QRY_CM_TCA_SUMM_ORDERBY_1 = " order by sum_tca_tm desc ";
	public static final String QRY_CM_TCA_SUMM_ORDERBY_2 = " order by sum_state_changes desc ";
	
	public static final String QRY_CM_TCA_SUMMARY_FOR_CMTS = 
		QRY_CM_TCA_SUMMARY;
	
	public static final String QRY_CM_TCA_SUMMARY_FOR_HFC = 
		QRY_CM_TCA_SUMMARY;
		
	
	public static final String QRY_CM_STATUS_SUMMARY = 
		"select m.cm_res_id,sum(m.sum_online_tm) as sum_online_tm,"
		+ "sum(m.sum_offline_tm) as sum_offline_tm,sum(m.state_changes) as sum_state_changes,"
		+ "cm.mac_address,hfc.hfc_res_id,hfc.name,cmts.cmts_res_id,cmts.name from "
		+ "casumm.cm_status_summary m "
		+ "left outer join canet.cable_modem cm using (cm_res_id) "
		+ "left outer join canet.hfc_plant hfc using (hfc_res_id) "
		+ "left outer join canet.cmts cmts on (cm.cmts_res_id=cmts.cmts_res_id) "
		+ "where ";
	
	public static final String QRY_CM_STATUS_SUMM_GRPBY = " group by m.cm_res_id ";
	public static final String QRY_CM_STATUS_SUMM_ORDERBY_1 = " order by sum_offline_tm desc ";
	public static final String QRY_CM_STATUS_SUMM_ORDERBY_2 = " order by sum_state_changes desc ";
	
	public static final String QRY_CM_STATUS_SUMMARY_FOR_CMTS = 
		QRY_CM_STATUS_SUMMARY;
	
	public static final String QRY_CM_STATUS_SUMMARY_FOR_HFC = 
		QRY_CM_STATUS_SUMMARY;
	
	
	public static final String QRY_HFC_STATUS_SUMMARY = 
		"select m.hfc_res_id,sum(m.sum_alarmfree_tm) as sum_alarmfree_tm,"
		+ "sum(m.sum_alarm_tm) as sum_alarm_tm,sum(m.state_changes) as sum_state_changes,"
		+ "hfc.hfc_res_id,hfc.name,cmts.cmts_res_id,cmts.name from "
		+ "casumm.hfc_alarm_summary m "
		+ "left outer join canet.hfc_plant hfc using (hfc_res_id) "
		+ "left outer join canet.cmts cmts using (cmts_res_id) "
		+ "where ";
	
	public static final String QRY_HFC_STATUS_SUMM_GRPBY = " group by m.hfc_res_id ";
	public static final String QRY_HFC_STATUS_SUMM_ORDERBY_1 = " order by sum_alarm_tm desc ";
	public static final String QRY_HFC_STATUS_SUMM_ORDERBY_2 = " order by sum_state_changes desc ";
		
	public static final String QRY_HFC_STATUS_SUMMARY_FOR_CMTS = 
		QRY_HFC_STATUS_SUMMARY;
	
	public static final String QRY_GET_CUSTOMER_FOR_RES = 
		"SELECT cacust.*, addr.* FROM canet.customer_resource map left join canet.customer_name cacust "
		+ "using (customer_id) left outer join canet.customer_address addr using (address_id) "
		+ "where map.res_id=";
	
	public static final String QRY_ALARM_STATE_HISTORY = 
		"select ash.*, asmd.*, alms.* from caalarm.alarm_state_history ash left outer join " +
		"caalarm.alarm_state_metadata asmd using (ash_id) left outer join " +
		"caalarm.alarm_states alms on (ash.alarm_state=alms.state) where root_alarm_id=";
		
}

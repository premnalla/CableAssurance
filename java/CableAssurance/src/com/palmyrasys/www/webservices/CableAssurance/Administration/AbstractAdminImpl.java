/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Administration;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.AggregateCmOfflineTresholdT;
import com.palmyrasys.www.webservices.CableAssurance.AggregateMtaTresholdT;
import com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT;
import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainT;
// import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT;
import com.palmyrasys.www.webservices.CableAssurance.ApplicationDomainTypeT;
import com.palmyrasys.www.webservices.CableAssurance.BladeT;
import com.palmyrasys.www.webservices.CableAssurance.ChannelStatusThresholdT;
import com.palmyrasys.www.webservices.CableAssurance.CmPerformanceConfigT;
import com.palmyrasys.www.webservices.CableAssurance.CmsAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.CmsStatusThresholdT;
import com.palmyrasys.www.webservices.CableAssurance.CmsT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsStatusThresholdT;
import com.palmyrasys.www.webservices.CableAssurance.CmtsT;
import com.palmyrasys.www.webservices.CableAssurance.ConfigDownloadT;
import com.palmyrasys.www.webservices.CableAssurance.EventCategoryT;
import com.palmyrasys.www.webservices.CableAssurance.EventMessageT;
import com.palmyrasys.www.webservices.CableAssurance.EventTypeT;
import com.palmyrasys.www.webservices.CableAssurance.HfcAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.HfcPowerTresholdT;
import com.palmyrasys.www.webservices.CableAssurance.HfcStatusThresholdT;
import com.palmyrasys.www.webservices.CableAssurance.LocalSystemT;
import com.palmyrasys.www.webservices.CableAssurance.MarketT;
import com.palmyrasys.www.webservices.CableAssurance.MtaAlarmConfigT;
import com.palmyrasys.www.webservices.CableAssurance.MtaStatusThresholdT;
import com.palmyrasys.www.webservices.CableAssurance.PollingIntervalsT;
import com.palmyrasys.www.webservices.CableAssurance.RegionT;
import com.palmyrasys.www.webservices.CableAssurance.ResourceTypeT;
import com.palmyrasys.www.webservices.CableAssurance.UserAccessT;
import com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT;
// import com.palmyrasys.www.webservices.CableAssurance.RoleOAPairT;
import com.palmyrasys.www.webservices.CableAssurance.RoleT;
import com.palmyrasys.www.webservices.CableAssurance.SnmpV2CAttributesT;
import com.palmyrasys.www.webservices.CableAssurance.SoakWindowT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
// import com.palmyrasys.www.webservices.CableAssurance.UserAccessTypeT;
// import com.palmyrasys.www.webservices.CableAssurance.UserOAPairT;
// import com.palmyrasys.www.webservices.CableAssurance.UserRoleT;
import com.palmyrasys.www.webservices.CableAssurance.UserT;
import com.palmyrasys.www.webservices.CableAssurance.Common.AbstractCommonServiceImpl;
import com.palmyrasys.www.webservices.CableAssurance.LocalSystem.LocalSystemSingleton;
import com.palmyrasyscorp.db.tables.AppConfig;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.CmSnmpV2CAttributes;
import com.palmyrasyscorp.db.tables.Cms;
import com.palmyrasyscorp.db.tables.Cmts;
import com.palmyrasyscorp.db.tables.CmtsSnmpV2CAttributes;
import com.palmyrasyscorp.db.tables.LocalSystem;
import com.palmyrasyscorp.db.tables.MtaSnmpV2CAttributes;
import com.palmyrasyscorp.db.tables.OAAccess;
import com.palmyrasyscorp.db.tables.OAObject;
import com.palmyrasyscorp.db.tables.Role;
import com.palmyrasyscorp.db.tables.RoleObjectAccess;
// import com.palmyrasyscorp.db.tables.RoleOAPair;
import com.palmyrasyscorp.db.tables.User;
// import com.palmyrasyscorp.db.tables.UserOAPair;
// import com.palmyrasyscorp.db.tables.UserRole;
import com.palmyrasyscorp.www.jsp.helper.MtaAlarmConfigHelper;
import com.palmyrasyscorp.www.webservices.helper.AdminHelper;
import com.palmyrasyscorp.www.webservices.helper.CPeerHelper;
import com.palmyrasyscorp.www.webservices.helper.LocalSystemHelper;

/**
 * @author Prem
 * 
 */
public abstract class AbstractAdminImpl extends AbstractCommonServiceImpl
		implements AdministrationEP {

	private static Log log = LogFactory.getLog(AbstractAdminImpl.class);


	/**
	 * 
	 */
	public short addCms(CmsT cms) {
		short ret = 0;

		try {
			Cms dbCms = new Cms();
			dbCms.setCmsName(cms.getCmsName());
			dbCms.setHost(cms.getCmsHost());
			// dbCms.setCmsDescription(cms.ge)
			dbCms.setCmsLoginPw(cms.getLoginPassword());
			dbCms.setCmsLoginUserId(cms.getLoginName());
			dbCms.setCmsType(cms.getCmsType());
			dbCms.setCmsSubType(cms.getCmsSubType());
			// dbCms.setVendor(cms.get)
			// dbCms.setCmsSoapUrl(arg0)

			if (!dbCms.insertRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Add,
						EventCategoryT.Resource, ResourceTypeT._CMS, dbCms
								.getCmsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}

			dbCms = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short addCmts(CmtsT cmts) {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			/*
			 * Add CMTS to myself (which is Market)
			 */
			Cmts dbCmts = new Cmts();
			dbCmts.setName(cmts.getCmtsName());
			dbCmts.setHose(cmts.getCmtsHost());
			// System.out.println("CMTS name: " + dbCmts.getName());
			if (!dbCmts.insertRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Add,
						EventCategoryT.Resource, ResourceTypeT._CMTS, dbCmts
								.getCmtsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short addCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId, SnmpV2CAttributesT[] attributes) {
		short ret = 0;

		try {
			/*
			 * Add SNMP v2c attributes to myself (which is Market)
			 */
			if (attributes.length != 3) {
				ret = -1;
				return ret;
			}

			SnmpV2CAttributesT attr;
			int i = 0;
			attr = attributes[i++];
			CmtsSnmpV2CAttributes cmts = new CmtsSnmpV2CAttributes();
			cmts.setCmtsResId(cmtsResId.longValue());
			cmts.setReadCommunity(attr.getReadCommnunity());
			cmts.setWriteCommunity(attr.getWriteCommnunity());
			cmts.insertRow();

			attr = attributes[i++];
			CmSnmpV2CAttributes cm = new CmSnmpV2CAttributes();
			cm.setCmtsResId(cmtsResId.longValue());
			cm.setReadCommunity(attr.getReadCommnunity());
			cm.setWriteCommunity(attr.getWriteCommnunity());
			cm.insertRow();

			attr = attributes[i++];
			MtaSnmpV2CAttributes mta = new MtaSnmpV2CAttributes();
			mta.setCmtsResId(cmtsResId.longValue());
			mta.setReadCommunity(attr.getReadCommnunity());
			mta.setWriteCommunity(attr.getWriteCommnunity());
			mta.insertRow();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short deleteCms(CmsT cms) {
		short ret = 0;

		try {
			Cms dbCms = new Cms(cms.getCmsResId().longValue());
			if (!dbCms.deleteRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Delete,
						EventCategoryT.Resource, ResourceTypeT._CMS, dbCms
								.getCmsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short deleteCmts(CmtsT cmts) {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			/*
			 * Add CMTS to myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmts.getCmtsResId().longValue());
			// System.out.println("CMTS name: " + dbCmts.getName());
			if (!dbCmts.deleteRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Delete,
						EventCategoryT.Resource, ResourceTypeT._CMTS, dbCmts
								.getCmtsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmtsAllSnmpV2CAttributes(TopoHierarchyKeyT topologyKey,
			BigInteger cmtsResId, SnmpV2CAttributesT[] attributes) {
		short ret = 0;

		try {
			/*
			 * Add SNMP v2c attributes to myself (which is Market)
			 */
			if (attributes.length != 3) {
				ret = -1;
				return ret;
			}

			// System.out.println("updateCmtsAllSnmpV2CAttributes: ");

			SnmpV2CAttributesT attr;
			int i = 0;
			attr = attributes[i++];
			CmtsSnmpV2CAttributes cmts = new CmtsSnmpV2CAttributes(cmtsResId
					.longValue());
			cmts.setReadCommunity(attr.getReadCommnunity());
			cmts.setWriteCommunity(attr.getWriteCommnunity());
			cmts.updateRow();

			attr = attributes[i++];
			CmSnmpV2CAttributes cm = new CmSnmpV2CAttributes(cmtsResId.longValue());
			cm.setReadCommunity(attr.getReadCommnunity());
			cm.setWriteCommunity(attr.getWriteCommnunity());
			cm.updateRow();

			attr = attributes[i++];
			MtaSnmpV2CAttributes mta = new MtaSnmpV2CAttributes(cmtsResId
					.longValue());
			mta.setReadCommunity(attr.getReadCommnunity());
			mta.setWriteCommunity(attr.getWriteCommnunity());
			mta.updateRow();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmtsAlarmConfig(CmtsAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			SoakWindowT s;

			AppConfig ac = new AppConfig();

			AlarmTypeConfigT atc = alm.getCmtsDown();

			s = atc.getSoakWindow();

			ac.setValue(AppConfig.cmts_comms_fail_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.cmts_comms_fail_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.cmts_comms_fail_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.cmts_comms_fail_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "CMTS_ALARM", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmtsStatusThreshold(CmtsStatusThresholdT pollintInterval)
			throws RemoteException {
		short ret = 0;

		try {
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmsAlarmConfig(CmsAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			SoakWindowT s;

			AppConfig ac = new AppConfig();

			AlarmTypeConfigT atc = alm.getCmsLossOfComm();

			s = atc.getSoakWindow();

			ac.setValue(AppConfig.cms_loc_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.cms_loc_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.cms_loc_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.cms_loc_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "CMS_ALARM", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateCmsStatusThreshold(CmsStatusThresholdT pollintInterval)
			throws RemoteException {
		short ret = 0;

		try {
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateHfcAlarmConfig(HfcAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			SoakWindowT s;

			AppConfig ac = new AppConfig();

			AggregateCmOfflineTresholdT cm = alm.getCmThresold();
			AggregateMtaTresholdT mta = alm.getMtaThresold();
			HfcPowerTresholdT power = alm.getPowerThresold();

			s = alm.getCmSoakWindow();

			ac.setValue(AppConfig.hfc_cm_offline_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.hfc_cm_offline_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.hfc_cm_offline_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.hfc_cm_offline_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			ac.setValue(AppConfig.hfc_cm_offline_alarm_detection_window, cm
					.getDetectionWindow());
			ac.setValue(AppConfig.hfc_cm_offline_alarm_threshold_1_cm, cm
					.getMaxCm_1());
			ac.setValue(AppConfig.hfc_cm_offline_alarm_threshold_2_cm, cm
					.getMaxCm_2());
			ac.setValue(AppConfig.hfc_cm_offline_alarm_threshold_1, cm
					.getPercentCmOffline_1());
			ac.setValue(AppConfig.hfc_cm_offline_alarm_threshold_2, cm
					.getPercentCmOffline_2());

			s = alm.getMtaSoakWindow();

			ac.setValue(AppConfig.hfc_mta_unavail_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.hfc_mta_unavail_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.hfc_mta_unavail_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.hfc_mta_unavail_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			ac.setValue(AppConfig.hfc_mta_unavail_alarm_detection_window, mta
					.getDetectionWindow());
			ac.setValue(AppConfig.hfc_mta_unavail_alarm_threshold_1, mta
					.getMtaThresholdCount());
			// System.out.println(ret.getMtaThresold().getMtaThresholdCount());
			// System.out.println(ret.getMtaThresold().getDetectionWindow());

			s = alm.getPowerSoakWindow();

			ac.setValue(AppConfig.hfc_power_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.hfc_power_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.hfc_power_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.hfc_power_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			ac.setValue(AppConfig.hfc_power_alarm_detection_window, power
					.getDetectionWindow());
			ac.setValue(AppConfig.hfc_power_alarm_threshold_1, power
					.getThresholdCount());

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "HFC_ALARM", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateHfcStatusThreshold(HfcStatusThresholdT pollintInterval)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	public short updateMtaAlarmConfig(MtaAlarmConfigT alm)
			throws RemoteException {
		short ret = 0;

		try {
			SoakWindowT s;

			AppConfig ac = new AppConfig();

			AlarmTypeConfigT atc;

			/*
			 * MTA unavailable
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_unavail_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_unavail_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_unavail_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_unavail_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_unavail_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA CMS LOC
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_cmsloc_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_cmsloc_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_cmsloc_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_cmsloc_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_cmsloc_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA Battery Missing
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_battmiss_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_battmiss_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_battmiss_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_battmiss_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_battmiss_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA On-Battery
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_onbatt_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_onbatt_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_onbatt_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_onbatt_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_onbatt_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA Replace Battery
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_replbatt_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_replbatt_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_replbatt_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_replbatt_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_replbatt_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA Hardware Failure
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_hwfail_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_hwfail_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_hwfail_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_hwfail_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_hwfail_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			/*
			 * MTA Line-Card Failure
			 */
			atc = MtaAlarmConfigHelper.getAlarmTypeConfig(
					AppConfig.mta_lcfail_alarm_nm, alm);
			s = atc.getSoakWindow();
			ac.setValue(AppConfig.mta_lcfail_soak_win_1_duration, s
					.getSoakWindow_1_Duration());
			ac.setValue(AppConfig.mta_lcfail_soak_win_1_start_tm, s
					.getSoakWindow_1_StartTime());
			ac.setValue(AppConfig.mta_lcfail_soak_win_2_duration, s
					.getSoakWindow_2_Duration());
			ac.setValue(AppConfig.mta_lcfail_soak_win_2_start_tm, s
					.getSoakWindow_2_StartTime());

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "MTA_ALARM", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateMtaStatusThreshold(MtaStatusThresholdT pollintInterval)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	public short updatePollingIntervals(PollingIntervalsT pollintInterval)
			throws RemoteException {
		short ret = -1;
		
		try {
			// PollingIntervalListBox lb = PollingIntervalListBox.getInstance();

			AppConfig ac = new AppConfig();

			// System.out.println(pollintInterval.getCmtsPollInterval());
			// System.out.println(pollintInterval.getCmPollInterval());
			// System.out.println(pollintInterval.getMtaPollInterval());
			// System.out.println(pollintInterval.getMtaPingInterval());

			ac.setValue(AppConfig.cmts_poll_interval, (pollintInterval
					.getCmtsPollInterval()));
			ac.setValue(AppConfig.cm_poll_interval, (pollintInterval
					.getCmPollInterval()));
			ac.setValue(AppConfig.mta_poll_interval, (pollintInterval
					.getMtaPollInterval()));
			ac.setValue(AppConfig.mta_ping_interval, (pollintInterval
					.getMtaPingInterval()));

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "GENERAL", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;

			ret = 0;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public short updateCms(CmsT cms) throws RemoteException {
		short ret = 0;

		try {
			Cms dbCms = new Cms(cms.getCmsResId().longValue());
			dbCms.setCmsName(cms.getCmsName());
			dbCms.setHost(cms.getCmsHost());
			// dbCms.setCmsDescription(cms.ge)
			dbCms.setCmsLoginPw(cms.getLoginPassword());
			dbCms.setCmsLoginUserId(cms.getLoginName());
			dbCms.setCmsType(cms.getCmsType());
			dbCms.setCmsSubType(cms.getCmsSubType());
			// dbCms.setVendor(cms.get)
			// dbCms.setCmsSoapUrl(arg0)

			if (!dbCms.updateRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Update,
						EventCategoryT.Resource, ResourceTypeT._CMS, dbCms
								.getCmsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}

			dbCms = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public short updateCmts(CmtsT cmts) throws RemoteException {
		short ret = 0;

		try {
			// System.out.println("CMTS resid:" + cmts.getCmtsResId());

			/*
			 * Get CMTS from myself (which is Market)
			 */
			Cmts dbCmts = new Cmts(cmts.getCmtsResId().longValue());
			dbCmts.setName(cmts.getCmtsName());
			dbCmts.setHose(cmts.getCmtsHost());
			// System.out.println("CMTS name: " + dbCmts.getName());
			if (!dbCmts.updateRow()) {
				ret = -1;
			} else {
				// send event to CAEngine via cPeerService
				EventMessageT e = new EventMessageT(EventTypeT.Update,
						EventCategoryT.Resource, ResourceTypeT._CMTS, dbCmts
								.getCmtsResId().toString());
				CPeerHelper ch = new CPeerHelper();
				ch.sendEvent(e);
			}

			dbCmts = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	public short updateLocalSystem(LocalSystemT ls) throws RemoteException {
		short ret = 0;

		try {
			LocalSystem dbLs = new LocalSystem();
			dbLs.setSystemName(ls.getSystemName());
			dbLs.setParentHost(ls.getParentHost());

			/*
			 * Set system type
			 */
			LocalSystemHelper.SetSystemType(dbLs, ls);

			if (!dbLs.updateRow()) {
				ret = -1;
			}

			dbLs = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	public short updateRegion(RegionT rgn) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short addRegion(RegionT rgn) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short updateMarket(MarketT mkt) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short addMarket(MarketT mkt) throws RemoteException {
		short ret = -1;

		return ret;
	}

	public short updateBlade(BladeT bld) throws RemoteException {
		short ret = 0;

		try {
			Blade dbBld = new Blade(bld.getBladeId().intValue());
			dbBld.setHost(bld.getHost());
			dbBld.setName(bld.getName());

			if (!dbBld.updateRow()) {
				ret = -1;
			}

			dbBld = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public short addBlade(BladeT bld) throws RemoteException {
		short ret = 0;

		try {
			Blade dbBld = new Blade();
			dbBld.setHost(bld.getHost());
			dbBld.setName(bld.getName());

			if (!dbBld.insertRow()) {
				ret = -1;
			}

			dbBld = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short deleteBlade(BladeT bld) throws java.rmi.RemoteException {
		short ret = 0;

		try {
			Blade dbBld = new Blade(bld.getBladeId().intValue());

			if (!dbBld.deleteRow()) {
				ret = -1;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmPerformanceConfigT getCmPerfConfig()
			throws java.rmi.RemoteException {
		CmPerformanceConfigT ret = null;

		try {
			AppConfig ac = new AppConfig();
			ret = new CmPerformanceConfigT(ac);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateChannelStatusThreshold(
			ChannelStatusThresholdT pollintInterval) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	public ChannelStatusThresholdT getChannelStatusThreshold()
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public CmsAlarmConfigT getCmsAlarmConfig() throws RemoteException {
		CmsAlarmConfigT ret = null;

		try {
			AppConfig ac = new AppConfig();
			ret = new CmsAlarmConfigT();

			AlarmTypeConfigT atc;
			SoakWindowT sw;

			/*
			 * LOC alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.cms_loc_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.cms_loc_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.cms_loc_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.cms_loc_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.cms_loc_soak_win_2_start_tm));
			atc.setSoakWindow(sw);

			ret.setCmsLossOfComm(atc);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmsStatusThresholdT getCmsStatusThreshold() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public CmtsAlarmConfigT getCmtsAlarmConfig() throws RemoteException {
		CmtsAlarmConfigT ret = null;

		try {
			AppConfig ac = new AppConfig();
			ret = new CmtsAlarmConfigT();

			AlarmTypeConfigT atc;
			SoakWindowT sw;

			/*
			 * Comms failure alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.cmts_comms_fail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.cmts_comms_fail_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.cmts_comms_fail_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.cmts_comms_fail_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.cmts_comms_fail_soak_win_2_start_tm));
			atc.setSoakWindow(sw);

			ret.setCmtsDown(atc);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public CmtsStatusThresholdT getCmtsStatusThreshold() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public HfcAlarmConfigT getHfcAlarmConfig() throws RemoteException {
		HfcAlarmConfigT ret = null;

		try {
			AppConfig ac = new AppConfig();
			ret = new HfcAlarmConfigT();

			ret.setCmSoakWindow(new SoakWindowT());
			ret.getCmSoakWindow().setSoakWindow_1_Duration(
					ac.getValue(AppConfig.hfc_cm_offline_soak_win_1_duration));
			ret.getCmSoakWindow().setSoakWindow_1_StartTime(
					ac.getValue(AppConfig.hfc_cm_offline_soak_win_1_start_tm));
			ret.getCmSoakWindow().setSoakWindow_2_Duration(
					ac.getValue(AppConfig.hfc_cm_offline_soak_win_2_duration));
			ret.getCmSoakWindow().setSoakWindow_2_StartTime(
					ac.getValue(AppConfig.hfc_cm_offline_soak_win_2_start_tm));

			ret.setCmThresold(new AggregateCmOfflineTresholdT());
			ret.getCmThresold().setDetectionWindow(
					ac.getValue(AppConfig.hfc_cm_offline_alarm_detection_window));
			ret.getCmThresold().setMaxCm_1(
					ac.getValue(AppConfig.hfc_cm_offline_alarm_threshold_1_cm));
			ret.getCmThresold().setMaxCm_2(
					ac.getValue(AppConfig.hfc_cm_offline_alarm_threshold_2_cm));
			ret.getCmThresold().setPercentCmOffline_1(
					ac.getValue(AppConfig.hfc_cm_offline_alarm_threshold_1));
			// System.out.println(ret.getCmThresold().getPercentCmOffline_1());
			ret.getCmThresold().setPercentCmOffline_2(
					ac.getValue(AppConfig.hfc_cm_offline_alarm_threshold_2));

			ret.setMtaSoakWindow(new SoakWindowT());
			ret.getMtaSoakWindow().setSoakWindow_1_Duration(
					ac.getValue(AppConfig.hfc_mta_unavail_soak_win_1_duration));
			ret.getMtaSoakWindow().setSoakWindow_1_StartTime(
					ac.getValue(AppConfig.hfc_mta_unavail_soak_win_1_start_tm));
			ret.getMtaSoakWindow().setSoakWindow_2_Duration(
					ac.getValue(AppConfig.hfc_mta_unavail_soak_win_2_duration));
			ret.getMtaSoakWindow().setSoakWindow_2_StartTime(
					ac.getValue(AppConfig.hfc_mta_unavail_soak_win_2_start_tm));

			ret.setMtaThresold(new AggregateMtaTresholdT());
			ret.getMtaThresold().setDetectionWindow(
					ac.getValue(AppConfig.hfc_mta_unavail_alarm_detection_window));
			ret.getMtaThresold().setMtaThresholdCount(
					ac.getValue(AppConfig.hfc_mta_unavail_alarm_threshold_1));
			// System.out.println(ret.getMtaThresold().getMtaThresholdCount());
			// System.out.println(ret.getMtaThresold().getDetectionWindow());

			ret.setPowerSoakWindow(new SoakWindowT());
			ret.getPowerSoakWindow().setSoakWindow_1_Duration(
					ac.getValue(AppConfig.hfc_power_soak_win_1_duration));
			ret.getPowerSoakWindow().setSoakWindow_1_StartTime(
					ac.getValue(AppConfig.hfc_power_soak_win_1_start_tm));
			ret.getPowerSoakWindow().setSoakWindow_2_Duration(
					ac.getValue(AppConfig.hfc_power_soak_win_2_duration));
			ret.getPowerSoakWindow().setSoakWindow_2_StartTime(
					ac.getValue(AppConfig.hfc_power_soak_win_2_start_tm));

			ret.setPowerThresold(new HfcPowerTresholdT());
			ret.getPowerThresold().setDetectionWindow(
					ac.getValue(AppConfig.hfc_power_alarm_detection_window));
			ret.getPowerThresold().setThresholdCount(
					ac.getValue(AppConfig.hfc_power_alarm_threshold_1));

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public HfcStatusThresholdT getHfcStatusThreshold() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public MtaAlarmConfigT getMtaAlarmConfig() throws RemoteException {
		MtaAlarmConfigT ret = null;

		try {
			ArrayList al = new ArrayList();

			AppConfig ac = new AppConfig();
			ret = new MtaAlarmConfigT();

			AlarmTypeConfigT atc;
			SoakWindowT sw;

			/*
			 * Unavailable alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_unavail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_unavail_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_unavail_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_unavail_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_unavail_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * CMS LOC alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_cmsloc_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_cmsloc_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_cmsloc_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_cmsloc_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_cmsloc_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * Battery Missing
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_battmiss_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_battmiss_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_battmiss_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_battmiss_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_battmiss_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * On-Battery
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_onbatt_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_onbatt_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_onbatt_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_onbatt_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_onbatt_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * Replace Battery
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_replbatt_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_replbatt_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_replbatt_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_replbatt_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_replbatt_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * Hardware Failure
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_hwfail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_hwfail_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_hwfail_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_hwfail_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_hwfail_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * Line Card Failure
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_lcfail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(ac
					.getValue(AppConfig.mta_lcfail_soak_win_1_duration));
			sw.setSoakWindow_1_StartTime(ac
					.getValue(AppConfig.mta_lcfail_soak_win_1_start_tm));
			sw.setSoakWindow_2_Duration(ac
					.getValue(AppConfig.mta_lcfail_soak_win_2_duration));
			sw.setSoakWindow_2_StartTime(ac
					.getValue(AppConfig.mta_lcfail_soak_win_2_start_tm));
			atc.setSoakWindow(sw);
			al.add(atc);

			/*
			 * Finally
			 */
			if (al.size() > 0) {
				AlarmTypeConfigT[] tmp = new AlarmTypeConfigT[al.size()];
				ret.setAlarmTypes((AlarmTypeConfigT[]) al.toArray(tmp));
			}

			ac = null;
			al.clear();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public MtaStatusThresholdT getMtaStatusThreshold() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public PollingIntervalsT getPollingIntervals() throws RemoteException {
		PollingIntervalsT ret = null;

		try {
			// PollingIntervalListBox lb = PollingIntervalListBox.getInstance();
			AppConfig ac = new AppConfig();
			ret = new PollingIntervalsT();
			ret.setCmtsPollInterval((ac.getValue(AppConfig.cmts_poll_interval)));
			ret.setCmPollInterval((ac.getValue(AppConfig.cm_poll_interval)));
			ret.setMtaPollInterval((ac.getValue(AppConfig.mta_poll_interval)));
			ret.setMtaPingInterval((ac.getValue(AppConfig.mta_ping_interval)));
			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return ret;
	}

	/**
	 * 
	 */
	public short updateCmPerfConfig(CmPerformanceConfigT cmPerf)
			throws java.rmi.RemoteException {
		short ret = 0;

		try {
			AppConfig ac = new AppConfig();
			ac.setValue(AppConfig.cm_perf_downstream_snr_lower, cmPerf
					.getDownstreamSnrLower());
			ac.setValue(AppConfig.cm_perf_downstream_power_lower, cmPerf
					.getDownstreamPowerLower());
			ac.setValue(AppConfig.cm_perf_downstream_power_upper, cmPerf
					.getDownstreamPowerUpper());
			ac.setValue(AppConfig.cm_perf_upstream_power_lower, cmPerf
					.getUpstreamPowerLower());
			ac.setValue(AppConfig.cm_perf_upstream_power_upper, cmPerf
					.getUpstreamPowerUpper());

			// send event to CAEngine via cPeerService
			EventMessageT e = new EventMessageT(EventTypeT.Update,
					EventCategoryT.Configuration, "CM_PERF", null);
			CPeerHelper ch = new CPeerHelper();
			ch.sendEvent(e);

			ac = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short addUser(UserT user) throws RemoteException {
		short ret = 0;

		try {
			User dbUser = new User();
			dbUser.setFirstName(user.getFirstName());
			dbUser.setMiddleInitial(user.getMiddleInitial());
			dbUser.setLastName(user.getLastName());
			dbUser.setUserId(user.getLoginName());
			dbUser.setUserPw(user.getLoginPassword());
			dbUser.setUserLocation(user.getLocation());
			dbUser.setIsUserActive(user.getIsActive().shortValue());
			dbUser.setRoleName(user.getRoleName());

			/*
			 * Finally update DB
			 */
			if (!dbUser.insertRow()) {
				ret = -1;
			}

			dbUser = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateUser(UserT user) throws RemoteException {
		short ret = 0;

		try {
			User dbUser = new User();
			dbUser.setFirstName(user.getFirstName());
			dbUser.setMiddleInitial(user.getMiddleInitial());
			dbUser.setLastName(user.getLastName());
			dbUser.setUserId(user.getLoginName());
			dbUser.setUserPw(user.getLoginPassword());
			dbUser.setUserLocation(user.getLocation());
			dbUser.setIsUserActive(user.getIsActive().shortValue());
			dbUser.setRoleName(user.getRoleName());

			/*
			 * Finally update DB
			 */
			if (!dbUser.updateRow()) {
				ret = -1;
			}

			dbUser = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateUserPassword(String loginName, String newPassword)
			throws RemoteException {
		short ret = 0;

		try {
			User dbUser = new User(loginName);
			dbUser.setUserPw(newPassword);

			/*
			 * Finally update DB
			 */
			if (!dbUser.updateRow()) {
				ret = -1;
			}

			dbUser = null;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	// /**
	// *
	// */
	// public short updateAccessPrivileges(String loginName, UserRoleT
	// privileges)
	// throws RemoteException {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// /**
	// *
	// */
	// public UserRoleT getAccessPrivileges(String loginName)
	// throws RemoteException {
	// // TODO Auto-generated method stub
	// return null;
	// }

	/**
	 * 
	 */
	public UserT getUser(String loginName) throws RemoteException {
		UserT ret = null;

		try {
			// System.out.println("getUser():" + loginName);
	
			User dbUser = new User(loginName);
	
			// System.out.println("Last name= " + dbUser.getLastName());
	
			ret = new UserT(dbUser);
	
			// System.out.println("Last name= " + ret.getLastName());	
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public UserT[] getUsers() throws RemoteException {
		UserT[] ret = null;

		try {
			// System.out.println("getUsers: service");

			ArrayList al = new ArrayList();

			List l = User.GetUsers();

			// System.out.println("User list size=" + l.size());

			for (int i = 0; i < l.size(); i++) {
				UserT su = new UserT((User) l.get(i));
				al.add(su);
			}
			l.clear();

			/*
			 * Finally
			 */
			if (al.size() > 0) {
				UserT[] tmp = new UserT[al.size()];
				ret = (UserT[]) al.toArray(tmp);
			}
			al.clear();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public ApplicationDomainT[] getApplicationDomains() throws RemoteException {
		ApplicationDomainT[] ret = null;
		
		try {
			List l = OAObject.GetList();
			ret = new ApplicationDomainT[l.size()];
			for (int i=0; i<l.size(); i++) {
				OAObject o = (OAObject) l.get(i);
				ret[i] = new ApplicationDomainT(o);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public UserAccessT[] getAccessRights() throws RemoteException {
		UserAccessT[] ret = null;
		
		try {
			List l = OAAccess.GetList();
			ret = new UserAccessT[l.size()];
			for (int i=0; i<l.size(); i++) {
				OAAccess a = (OAAccess) l.get(i);
				ret[i] = new UserAccessT(a);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 */
	public RoleT[] getRoles() throws java.rmi.RemoteException {
		RoleT[] ret = null;

		try {
			HashMap map = RoleObjectAccess.Get(null);
			ret = new RoleT[map.size()];
			Iterator iter = map.values().iterator();
			for (int i = 0; i < map.size() && iter.hasNext(); i++) {
				Role r = (Role) iter.next();
				ret[i] = new RoleT(r);
			}
			System.out.println("arraySize=" + ret.length);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public RoleT getRole(String roleName) throws java.rmi.RemoteException {
		RoleT ret = null;

		try {
			System.out.println("getRole(): " + roleName);
			
			Role dbR = new Role(roleName);
			if (!dbR.get()) {
				return (ret);
			}

			HashMap map = RoleObjectAccess.Get(dbR.getId());
			ret = new RoleT((Role) map.get(roleName));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short addRole(RoleT role) throws java.rmi.RemoteException {
		short ret = -1;

		try {
			// System.out.println("addRole():");
			
			String roleName = role.getRoleName();
			Role dbR = new Role(roleName);
			if (!dbR.insert()) {
				return (ret);
			}
			ret = updateRole(role);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public short updateRole(RoleT role) throws java.rmi.RemoteException {
		short ret = -1;

		try {
			// System.out.println("updateRole():");

			String roleName = role.getRoleName();
			Role dbR = new Role(roleName);
			if (!dbR.get()) {
				return (ret);
			}

			/*
			 * 
			 */
			HashMap map = RoleObjectAccess.Get(dbR.getId());
			Role tmpR = (Role) map.get(roleName);
			if (tmpR != null) {
				dbR = tmpR;
			} else {
				map.put(roleName, dbR);
			}
			
			HashMap dMap = dbR.getObjectMap();

			ApplicationDomainT[] domains = role.getDomains();
			System.out.println("Num domains" + domains.length);
			for (int i = 0; i < domains.length; i++) {
				ApplicationDomainTypeT dType = domains[i].getType();
				OAObject guiDomain = new OAObject(dType.getValue());
				if (!guiDomain.get()) {
					continue;
				}
				UserAccessT[] accessRights = domains[i].getAccessRights();
				System.out.println("Num accessRights" + accessRights.length);
				for (int j = 0; j < accessRights.length; j++) {
					UserAccessTypeT aType = accessRights[j].getType();
					OAAccess guiAccess = new OAAccess(aType.getValue());
					if (!guiAccess.get()) {
						continue;
					}

					if (!reconcileRole(dMap, guiDomain, guiAccess)) {
						// add entry
						RoleObjectAccess roa = new RoleObjectAccess(
								dbR.getId(), guiDomain.getId(), guiAccess.getId());
						roa.insert();
					}
				}

				/*
				 * Iterate thru and delete un-reconciled entries
				 */
				deleteUnreconciledRole(dMap, dbR, guiDomain);
			}

			// finally
			ret = 0;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 * @param domainMap
	 * @param dbDomain
	 * @param dbAccess
	 * @return
	 */
	private boolean reconcileRole(HashMap dbDomaiMap, OAObject guiDomain,
			OAAccess guiAccess) {
		boolean ret = false;

		try {
			OAObject d = (OAObject) dbDomaiMap.get(guiDomain.getAppDomain());
			HashMap dbAccessMap = d.getAccessMap();
			OAAccess access = (OAAccess) dbAccessMap.get(guiAccess.getAccess());
			if (access != null) {
				ret = true;
				access.setReconciled();
			}
		} catch (Exception e) {
			// don't print stacktrace
		}

		return (ret);
	}

	private void deleteUnreconciledRole(HashMap dbDomaiMap, Role dbRole,
			OAObject guiDomain) {

		try {
			OAObject d = (OAObject) dbDomaiMap.get(guiDomain.getAppDomain());
			HashMap dbAccessMap = d.getAccessMap();
			Iterator iter = dbAccessMap.values().iterator();
			for (int i = 0; iter.hasNext(); i++) {
				OAAccess access = (OAAccess) iter.next();
				if (!access.isReconciled()) {
					RoleObjectAccess roa = new RoleObjectAccess(dbRole.getId(),
							guiDomain.getId(), access.getId());
					roa.delete();
				}
			}

		} catch (Exception e) {
			// don't print stacktrace
		}

	}

	/**
	 * This method is called from two places. 1. From the WebPage. 2. By
	 * CAEngine's axConfigDownloader class
	 */
	public short downloadConfigFromParent() throws java.rmi.RemoteException {
		short ret = -1;

		try {
			LocalSystemSingleton lss = LocalSystemSingleton.getInstance();

			/*
			 * For now, only Blades are allowed to configure download conig from
			 * Market.
			 */
			if (!lss.isBlade()) {
				return (ret);
			}

			// LocalSystemT ls = new LocalSystemT();
			LocalSystem dbLs = new LocalSystem();
			AdminHelper mktAh = new AdminHelper(dbLs.getParentHost());
			// ah.getAccessPrivileges(loginName);
			CmPerformanceConfigT cmPerfCfg = mktAh.getCmPerfConfig();
			CmsAlarmConfigT cmsAlmCfg = mktAh.getCmsAlarmConfig();
			CmtsAlarmConfigT cmtsAlmCfg = mktAh.getCmtsAlarmConfig();
			HfcAlarmConfigT hfcAlmCfg = mktAh.getHfcAlarmConfig();
			MtaAlarmConfigT mtaAlmCfg = mktAh.getMtaAlarmConfig();
			PollingIntervalsT pollIntCfg = mktAh.getPollingIntervals();
			UserT[] users = mktAh.getUsers();
			// mktAh.g

			// AdminHelper localAh = new AdminHelper();
			if (cmPerfCfg != null) {
				updateCmPerfConfig(cmPerfCfg);
			}
			if (cmsAlmCfg != null) {
				updateCmsAlarmConfig(cmsAlmCfg);
			}
			if (cmtsAlmCfg != null) {
				updateCmtsAlarmConfig(cmtsAlmCfg);
			}
			if (hfcAlmCfg != null) {
				updateHfcAlarmConfig(hfcAlmCfg);
			}
			if (mtaAlmCfg != null) {
				updateMtaAlarmConfig(mtaAlmCfg);
			}
			if (pollIntCfg != null) {
				updatePollingIntervals(pollIntCfg);
			}
			if (users != null) {
				for (int i = 0; i < users.length; i++) {
					if (updateUser(users[i]) != 0) {
						addUser(users[i]);
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

	/**
	 * 
	 */
	public ConfigDownloadT getConfig() throws java.rmi.RemoteException {
		ConfigDownloadT ret = null;

		try {
			/*
			 * get data from Market server
			 */
			LocalSystem ls = new LocalSystem();
			AdminHelper ah = new AdminHelper(ls.getParentHost());
			ret = ah.getConfig();
			if (ret == null) {
				return (ret);
			}
			CmPerformanceConfigT cmPerfCfg = ret.getCmPerfCfg();
			CmsAlarmConfigT cmsAlmCfg = ret.getCmsAlarmCfg();
			CmtsAlarmConfigT cmtsAlmCfg = ret.getCmtsAlarmCfg();
			HfcAlarmConfigT hfcAlmCfg = ret.getHfcAlarmCfg();
			MtaAlarmConfigT mtaAlmCfg = ret.getMtaAlarmCfg();
			PollingIntervalsT pollIntCfg = ret.getPollingIntervals();
			ApplicationDomainT[] appDomains = ret.getApplicationDomains();
			UserAccessT[] userAccessRights = ret.getUserAccessRights();
			RoleT[] roles = ret.getRoles();
			UserT[] users = ret.getUsers();
			if (cmPerfCfg != null) {
				updateCmPerfConfig(cmPerfCfg);
			}
			if (cmsAlmCfg != null) {
				updateCmsAlarmConfig(cmsAlmCfg);
			}
			if (cmtsAlmCfg != null) {
				updateCmtsAlarmConfig(cmtsAlmCfg);
			}
			if (hfcAlmCfg != null) {
				updateHfcAlarmConfig(hfcAlmCfg);
			}
			if (mtaAlmCfg != null) {
				updateMtaAlarmConfig(mtaAlmCfg);
			}
			if (pollIntCfg != null) {
				updatePollingIntervals(pollIntCfg);
			}
			if (userAccessRights != null) {
				for (int i = 0; i < userAccessRights.length; i++) {
					UserAccessT sa = userAccessRights[i];
					OAAccess o = new OAAccess(sa.getType().getValue());
					o.insert();
				}
			}
			if (appDomains != null) {
				for (int i = 0; i < appDomains.length; i++) {
					ApplicationDomainT sa = appDomains[i];
					OAObject o = new OAObject(sa.getType().getValue());
					o.insert();
				}
			}
			if (roles != null) {
				for (int i = 0; i < roles.length; i++) {
					RoleT sr = roles[i];
					Role r = new Role(sr.getRoleName());
					r.insert();
				}
			}
			if (users != null) {
				for (int i = 0; i < users.length; i++) {
					if (updateUser(users[i]) != 0) {
						addUser(users[i]);
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}

		return (ret);
	}

}

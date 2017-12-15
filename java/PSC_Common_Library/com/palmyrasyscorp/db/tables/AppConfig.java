/**
 * 
 */
package com.palmyrasyscorp.db.tables;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 * 
 */
public class AppConfig extends AbstractDbObject {

	private static Log log = LogFactory.getLog(AppConfig.class);

	public static final String cmts_poll_interval = "cmts_poll_interval";

	public static final String mta_poll_interval = "mta_poll_interval";

	public static final String mta_ping_interval = "mta_ping_interval";

	public static final String cm_poll_interval = "cm_poll_interval";

	public static final String hfc_cm_offline_alarm_threshold_1_cm = "hfc_cm_offline_alarm_threshold_1_cm";

	public static final String hfc_cm_offline_alarm_threshold_1 = "hfc_cm_offline_alarm_threshold_1";

	public static final String hfc_cm_offline_alarm_threshold_2_cm = "hfc_cm_offline_alarm_threshold_2_cm";

	public static final String hfc_cm_offline_alarm_threshold_2 = "hfc_cm_offline_alarm_threshold_2";

	// public static final String cm_offline_alarm_threshold_3 =
	// "cm_offline_alarm_threshold_3";
	public static final String hfc_cm_offline_alarm_detection_window = "hfc_cm_offline_alarm_detection_window";

	// public static final String cm_offline_alarm_soak_window_1 =
	// "cm_offline_alarm_soak_window_1";
	public static final String hfc_cm_offline_soak_win_1_start_tm = "hfc_cm_offline_soak_win_1_start_tm";

	public static final String hfc_cm_offline_soak_win_2_start_tm = "hfc_cm_offline_soak_win_2_start_tm";

	public static final String hfc_cm_offline_soak_win_1_duration = "hfc_cm_offline_soak_win_1_duration";

	public static final String hfc_cm_offline_soak_win_2_duration = "hfc_cm_offline_soak_win_2_duration";

	public static final String hfc_mta_unavail_alarm_threshold_1 = "hfc_mta_unavail_alarm_threshold_1";

	public static final String hfc_mta_unavail_alarm_detection_window = "hfc_mta_unavail_alarm_detection_window";

	// public static final String hfc_mta_unavailable_alarm_soak_window_1 =
	// "mta_unavailable_alarm_soak_window_1";
	public static final String hfc_mta_unavail_soak_win_1_start_tm = "hfc_mta_unavail_soak_win_1_start_tm";

	public static final String hfc_mta_unavail_soak_win_2_start_tm = "hfc_mta_unavail_soak_win_2_start_tm";

	public static final String hfc_mta_unavail_soak_win_1_duration = "hfc_mta_unavail_soak_win_1_duration";

	public static final String hfc_mta_unavail_soak_win_2_duration = "hfc_mta_unavail_soak_win_2_duration";

	public static final String hfc_power_alarm_threshold_1 = "hfc_power_alarm_threshold_1";

	public static final String hfc_power_alarm_detection_window = "hfc_power_alarm_detection_window";

	// public static final String hfc_power_alarm_soak_window_1 =
	// "hfc_power_alarm_soak_window_1";
	public static final String hfc_power_soak_win_1_start_tm = "hfc_power_soak_win_1_start_tm";

	public static final String hfc_power_soak_win_2_start_tm = "hfc_power_soak_win_2_start_tm";

	public static final String hfc_power_soak_win_1_duration = "hfc_power_soak_win_1_duration";

	public static final String hfc_power_soak_win_2_duration = "hfc_power_soak_win_2_duration";

	// public static final String mta_battery_missing_alarm_soak_window_1 =
	// "mta_battery_missing_alarm_soak_window_1";
	// public static final String mta_on_battery_alarm_soak_window_1 =
	// "mta_on_battery_alarm_soak_window_1";

	/*
	 * MTA Alarm Config items
	 */
	public static final String mta_unavail_alarm_nm = "MTA Unavailable alarm";

	public static final String mta_unavail_soak_win_1_start_tm = "mta_unavail_soak_win_1_start_tm";

	public static final String mta_unavail_soak_win_2_start_tm = "mta_unavail_soak_win_2_start_tm";

	public static final String mta_unavail_soak_win_1_duration = "mta_unavail_soak_win_1_duration";

	public static final String mta_unavail_soak_win_2_duration = "mta_unavail_soak_win_2_duration";

	// /
	public static final String mta_onbatt_alarm_nm = "MTA on-battery alarm";

	public static final String mta_onbatt_soak_win_1_start_tm = "mta_onbatt_soak_win_1_start_tm";

	public static final String mta_onbatt_soak_win_2_start_tm = "mta_onbatt_soak_win_2_start_tm";

	public static final String mta_onbatt_soak_win_1_duration = "mta_onbatt_soak_win_1_duration";

	public static final String mta_onbatt_soak_win_2_duration = "mta_onbatt_soak_win_2_duration";

	// /
	public static final String mta_battmiss_alarm_nm = "MTA battery missing alarm";

	public static final String mta_battmiss_soak_win_1_start_tm = "mta_battmiss_soak_win_1_start_tm";

	public static final String mta_battmiss_soak_win_2_start_tm = "mta_battmiss_soak_win_2_start_tm";

	public static final String mta_battmiss_soak_win_1_duration = "mta_battmiss_soak_win_1_duration";

	public static final String mta_battmiss_soak_win_2_duration = "mta_battmiss_soak_win_2_duration";

	// /
	public static final String mta_replbatt_alarm_nm = "MTA replace battery alarm";

	public static final String mta_replbatt_soak_win_1_start_tm = "mta_replbatt_soak_win_1_start_tm";

	public static final String mta_replbatt_soak_win_2_start_tm = "mta_replbatt_soak_win_2_start_tm";

	public static final String mta_replbatt_soak_win_1_duration = "mta_replbatt_soak_win_1_duration";

	public static final String mta_replbatt_soak_win_2_duration = "mta_replbatt_soak_win_2_duration";

	// /
	public static final String mta_cmsloc_alarm_nm = "MTA CMS LOC alarm";

	public static final String mta_cmsloc_soak_win_1_start_tm = "mta_cmsloc_soak_win_1_start_tm";

	public static final String mta_cmsloc_soak_win_2_start_tm = "mta_cmsloc_soak_win_2_start_tm";

	public static final String mta_cmsloc_soak_win_1_duration = "mta_cmsloc_soak_win_1_duration";

	public static final String mta_cmsloc_soak_win_2_duration = "mta_cmsloc_soak_win_2_duration";

	// /
	public static final String mta_hwfail_alarm_nm = "MTA hardware failure alarm";

	public static final String mta_hwfail_soak_win_1_start_tm = "mta_hwfail_soak_win_1_start_tm";

	public static final String mta_hwfail_soak_win_2_start_tm = "mta_hwfail_soak_win_2_start_tm";

	public static final String mta_hwfail_soak_win_1_duration = "mta_hwfail_soak_win_1_duration";

	public static final String mta_hwfail_soak_win_2_duration = "mta_hwfail_soak_win_2_duration";

	// /
	public static final String mta_lcfail_alarm_nm = "MTA line card failure alarm";

	public static final String mta_lcfail_soak_win_1_start_tm = "mta_lcfail_soak_win_1_start_tm";

	public static final String mta_lcfail_soak_win_2_start_tm = "mta_lcfail_soak_win_2_start_tm";

	public static final String mta_lcfail_soak_win_1_duration = "mta_lcfail_soak_win_1_duration";

	public static final String mta_lcfail_soak_win_2_duration = "mta_lcfail_soak_win_2_duration";

	/*
	 * CMTS Alarm Config items
	 */
	public static final String cmts_comms_fail_alarm_nm = "CMTS Comms failure alarm";

	public static final String cmts_comms_fail_soak_win_1_start_tm = "cmts_comms_fail_soak_win_1_start_tm";

	public static final String cmts_comms_fail_soak_win_2_start_tm = "cmts_comms_fail_soak_win_2_start_tm";

	public static final String cmts_comms_fail_soak_win_1_duration = "cmts_comms_fail_soak_win_1_duration";

	public static final String cmts_comms_fail_soak_win_2_duration = "cmts_comms_fail_soak_win_2_duration";

	/*
	 * CMS Alarm Config items
	 */
	public static final String cms_loc_alarm_nm = "CMS LOC alarm";

	public static final String cms_loc_soak_win_1_start_tm = "cms_loc_soak_win_1_start_tm";

	public static final String cms_loc_soak_win_2_start_tm = "cms_loc_soak_win_2_start_tm";

	public static final String cms_loc_soak_win_1_duration = "cms_loc_soak_win_1_duration";

	public static final String cms_loc_soak_win_2_duration = "cms_loc_soak_win_2_duration";

	/*
	 * CM Performance thresholds
	 */
	public static final String cm_perf_downstream_snr_lower = "cm_perf_downstream_snr_lower";

	public static final String cm_perf_downstream_power_upper = "cm_perf_downstream_power_upper";

	public static final String cm_perf_downstream_power_lower = "cm_perf_downstream_power_lower";

	public static final String cm_perf_upstream_power_upper = "cm_perf_upstream_power_upper";

	public static final String cm_perf_upstream_power_lower = "cm_perf_upstream_power_lower";

	/**
	 * 
	 * 
	 */
	public AppConfig() {

	}

	/**
	 * 
	 * @param colName
	 * @return
	 */
	public String getValue(String colName) {
		String ret = null;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					"select var_value from canet.app_config where var_name=\"");
			qryStr.append(colName).append("\"");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				ret = rs.getString(i++);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (rs != null) {
				rs.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 */
	public boolean setValue(String colName, String colValue) {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					"update canet.app_config set var_value=\"");
			qryStr.append(colValue).append("\" where var_name=\"").append(
					colName).append("\"");

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

			ret = true;

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 */
	public boolean setValue(String colName, int colValue) {
		StringBuffer s = new StringBuffer(colValue);
		return (setValue(colName, s.toString()));
	}
}

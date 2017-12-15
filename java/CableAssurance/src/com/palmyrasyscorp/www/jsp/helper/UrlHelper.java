/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import com.palmyrasys.www.webservices.CableAssurance.*;

/**
 * @author Prem
 *
 */
public class UrlHelper {

	/*
	 * URL Parameter names
	 */
	static public final String REGION_ID = "rgnid";
	static public final String MARKET_ID = "mktid";
	static public final String BLADE_ID = "bldid";
	static public final String RESOURCE_ID = "resid";
	static public final String CMTS_RES_ID = "cmtsresid";
	static public final String UP_CHANNEL_RES_ID = "upchnlresid";
	static public final String DOWN_CHANNEL_RES_ID = "dnchnlresid";
	static public final String CHANNEL_RES_ID = "chnlresid";
	static public final String HFC_RES_ID = "hfcresid";
	static public final String CM_RES_ID = "cmresid";
	static public final String MTA_RES_ID = "emtaresid";
	static public final String ROOT_ALARM_ID = "ralmid";
	static public final String ALARM_TYPE = "almtype";
	static public final String LOGIN_ID = "loginid";
	static public final String ID = "id";
	static public final String NAME = "nm";
	
	/*
	 * String Keys
	 */
	static public final String ENTERPRISE = "Enterprise";
	static public final String MARKET = "Market";

	/*
	 * Button and other input field names
	 */
	static public final String SELECT_ALL_NM = "selectAll";
	static public final String UNSELECT_ALL_NM = "unselectAll";
	static public final String CLEAR_ALARM_NM = "clearAlarm";
	static public final String CLEAR_NM = "clear";
	static public final String FILTER_NM = "filter";
	
	/* */
	static public final String YES = "Yes";
	static public final String NO = "No";
	
	/* */
	static public final String CHECK_BOX_ON = "on";
	
	/* */
	public static final String FROM_COUNT = "fm"; 
	public static final String TO_COUNT = "to"; 
	
	public static final String TYPE = "type";
	
	public static final String MAC = "MAC";
	public static final String MTA = "MTA";
	public static final String CM = "CM";
	public static final String HFC = "HFC";
	public static final String CMTS = "CMTS";
	public static final String CMS = "CMS";
	
	public static final String UNKNOWN_FILLER = "---";
	
	/*
	 * Window / Frame Definitions 
	 */
	
	public static final String SELF = "_self";
	public static final String EMPTY_LOGO_FRAME = "EMPTY_LOGO_FRAME";
	public static final String EMPTY_DISPLAY_FRAME = "EMPTY_DISPLAY_FRAME";
	public static final String MAIN_FRAME = "MAIN_FRAME";
	public static final String ALARMS_VIEW_FRAME = "ALARMS_VIEW_FRAME";
	public static final String REPORTS_VIEW_FRAME = "REPORTS_VIEW_FRAME";
	public static final String ADMIN_FRAME = "ADMIN_FRAME";
	public static final String NET_OBJ_DATA = "NET_OBJ_DATA";
	public static final String TOP_DATA = "TOP_DATA";
	public static final String LOGO_AND_MENUS_FRAME = "LOGO_AND_MENUS_FRAME";
	public static final String DATA_FRAME = "DATA_FRAME";
	public static final String COPYRIGHT_FRAME = "COPYRIGHT_FRAME";
	public static final String MULTI_CM_CMP_LOGO_HDR_FRAME = "MULTI_CM_CMP_LOGO_HDR_FRAME";
	public static final String MULTI_EUD_CMP_HFC_FRAME = "MULTI_EUD_CMP_HFC_FRAME";
	public static final String MULTI_CM_CMP_PLOT_FRAME = "MULTI_CM_CMP_PLOT_FRAME";
	public static final String MULTI_CM_CMP = "MULTI_CM_CMP";
	// public static final String MULTI_MTA_CMP = "MULTI_MTA_CMP";
	public static final String NAV_TREE_DATA = "NAV_TREE_DATA";
	public static final String MAIN_ALARMS = "MAIN_ALARMS";
	public static final String NET_TOPO_CM_DETAILS = "NET_TOPO_CM_DETAILS";
	public static final String CM_DRILL_DOWN_LOGO_FRAME = "CM_DRILL_DOWN_LOGO_FRAME";
	public static final String CM_DRILL_DOWN_DETAILS_FRAME = "CM_DRILL_DOWN_DETAILS_FRAME";
	public static final String CM_DRILL_DOWN_PLOTS_FRAME = "CM_DRILL_DOWN_PLOTS_FRAME";
	public static final String VIEW_ALARMS_LOGO_HDR_FRAME = "VIEW_ALARMS_LOGO_HDR_FRAME";
	public static final String VIEW_ALARMS_FRAME = "VIEW_ALARMS_FRAME";
	public static final String NET_TOPO_DETAILS = "NET_TOPO_DETAILS";
	public static final String NET_TOPO_DETAILS_LOGO = "NET_TOPO_DETAILS_LOGO";
	public static final String NET_TOPO_DETAILS_DATA = "NET_TOPO_DETAILS_DATA";
	public static final String CSR_MAIN_FRAME = "CSR_MAIN_FRAME";
	public static final String CSR_LOGO_FRAME = "CSR_LOGO_FRAME";
	public static final String CSR_DATA_FRAME = "CSR_DATA_FRAME";
	public static final String CSR_DATA_ROW_1_FRAME = "CSR_DATA_ROW_1_FRAME";
	public static final String CSR_DATA_ROW_2_FRAME = "CSR_DATA_ROW_2_FRAME";
	public static final String CSR_DATA_CUSTDB_FRAME = "CSR_DATA_CUSTDB_FRAME";
	public static final String CSR_DATA_CMS_FRAME = "CSR_DATA_CMS_FRAME";
	public static final String CSR_DATA_MTA_FRAME = "CSR_DATA_MTA_FRAME";
	public static final String CSR_DATA_CMTS_FRAME = "CSR_DATA_CMTS_FRAME";
	public static final String CSR_DATA_CM_FRAME = "CSR_DATA_CM_FRAME";
	public static final String CSR_DATA_CADB_FRAME = "CSR_DATA_CADB_FRAME";
	public static final String ADMIN_LOGO_FRAME = "ADMIN_LOGO_FRAME";
	public static final String ADMIN_DATA_FRAME = "ADMIN_DATA_FRAME";
	public static final String ADMIN_TAB_MENU_FRAME = "ADMIN_TAB_MENU_FRAME";
	public static final String ADMIN_TAB_DATA_FRAME = "ADMIN_TAB_DATA_FRAME";
	public static final String ADMIN_ALARM_TAB_MENU_FRAME = "ADMIN_ALARM_TAB_MENU_FRAME";
	public static final String ADMIN_ALARM_TAB_DATA_FRAME = "ADMIN_ALARM_TAB_DATA_FRAME";
	public static final String ADMIN_TOPO_TAB_MENU_FRAME = "ADMIN_TOPO_TAB_MENU_FRAME";
	public static final String ADMIN_TOPO_TAB_DATA_FRAME = "ADMIN_TOPO_TAB_DATA_FRAME";
	public static final String ADMIN_PERF_TAB_MENU_FRAME = "ADMIN_PERF_TAB_MENU_FRAME";
	public static final String ADMIN_PERF_TAB_DATA_FRAME = "ADMIN_PERF_TAB_DATA_FRAME";
	public static final String ADMIN_USER_TAB_MENU_FRAME = "ADMIN_USER_TAB_MENU_FRAME";
	public static final String ADMIN_USER_TAB_DATA_FRAME = "ADMIN_USER_TAB_DATA_FRAME";
	
	/*
	 * CMTS Edit/Delete form button name prefix
	 */
	public static final String ID_SEPERATOR = ":";
	public static final String CMTS_EDIT_PREFIX = "edit_";
	public static final String CMTS_DELETE_PREFIX = "del_";
	public static final String CMS_EDIT_PREFIX = "edit_";
	public static final String CMS_DELETE_PREFIX = "del_";
	public static final String RESET_PASSWORD_PREFIX = "resetpw_";
	public static final String ALARM_PREFIX = "alm_";
	
	
	/*
	 * Report Types
	 */
	public static final String REPORT_TYPE_ST_DURATION = "1";
	public static final String REPORT_TYPE_ST_FREQUENCY = "2";
	
	
	/**
	 * 
	 * @param url
	 * @param tK
	 */
	static public void AppendTopoHierarchyIDs(StringBuffer url, TopoHierarchyKeyT tK) {
		url.append("?rgnid=").append(tK.getRegionId());
		url.append("&mktid=").append(tK.getMarketId());
		url.append("&bldid=").append(tK.getBladeId());
	}
	
	/**
	 * 
	 * @param url
	 * @param tK
	 */
	static public void AppendTopoKeysToName(StringBuffer name, TopoHierarchyKeyT tK) {
		name.append(tK.getRegionId()).append("_");
		name.append(tK.getMarketId()).append("_");
		name.append(tK.getBladeId()).append("_");
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	static public long ParseLong(String s){
		long ret;
		try {
			ret = Long.parseLong(s);
		} catch (Exception e) {
			ret = 0;
		}
		return (ret);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static final int StringToInt(String s) {
		int ret;
		try {
			ret = Integer.parseInt(s);
		} catch (Exception e) {
			ret = -1;
		}
		return (ret);
	}

	/**
	 * 
	 */
	private static final int BATCH_ARRAY_SIZE = 4;
	
	/**
	 * 
	 * @return
	 */
	public static final int[] DetermineBatchIndices(
			String fmIndex, String toIndex, int batchSize) {
		int[] ret = new int[BATCH_ARRAY_SIZE];

		int fmI = -1;
		int toI = -1;
		
		for (int i=0; i<BATCH_ARRAY_SIZE; i++) {
			ret[i] = -1;
		}
		
		if (fmIndex == null || toIndex == null) {
			ret[2] = batchSize;
			ret[3] = (2 * batchSize) - 1;
			return (ret);
		}
		
		fmI = StringToInt(fmIndex);
		toI = StringToInt(toIndex);			
		
		/* prev batch */
		ret[0] = fmI - batchSize;
		ret[1] = toI - batchSize;
		
		/* next batch */
		ret[2] = fmI + batchSize;
		ret[3] = toI + batchSize;
		
		return (ret);
	}
	
	/**
	 * 
	 * @param req
	 * @param fn
	 * @return
	 */
	public static final String getChartUrl(String fn) {
		StringBuffer s = new StringBuffer("../caservlet/DisplayChart?filename=");
		s.append(fn);
		return s.toString();
	}
		
	
	/**
	 * 
	 */
	public static final String BitToYesNo(short bit) {
		String ret;
		
		if (bit == 0) {
			ret = NO;
		} else {
			ret = YES;
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param batchSize
	 * @return
	 */
	public static final String getBatchMinusOne(String batchSize) {
		String ret;
		try {
			int i = Integer.parseInt(batchSize);
			--i;
			ret = String.valueOf(i);
		} catch (Exception e) {
			ret = batchSize;
		}
		return (ret);
	}
}

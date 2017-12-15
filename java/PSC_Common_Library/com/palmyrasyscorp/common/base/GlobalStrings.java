/**
 * 
 */
package com.palmyrasyscorp.common.base;

/**
 * @author Prem
 *
 */
public class GlobalStrings {

	//
	static public final String GUI_NO_VALUE_STRING = "--";

	//
	static public final String GUI_ONLINE_STATUS_STRING = "Online";
	static public final String GUI_NOT_ONLINE_STATUS_STRING = "Not online";
	static public final String GUI_OFFLINE_STATUS_STRING = "Online";
	static public final String GUI_OTHER_STATUS_STRING = "Other";

	// Packet Cable Prov Status
	static public final String GUI_PROV_STATUS_PASS_STRING = "Pass";
	static public final String GUI_PROV_STATUS_FAIL_STRING = "Fail";

	// Packet Cable Prov Status
	static public final String GUI_MTA_PING_STATUS_ALIVE = "Alive";
	static public final String GUI_MTA_PING_STATUS_UNREACHABLE = "Unreachable";
	static public final String GUI_MTA_PING_STATUS_UNKNOWN = "Unknown";

	//
	static public final String GUI_MTA_STATUS_AVAIL = "Available";
	static public final String GUI_MTA_STATUS_UNAVAIL = "Unavailable";
	
	// MTA Prov status
	static public final String GUI_PKTCBL_PROV_STATUS_1 = "pass";
	static public final String GUI_PKTCBL_PROV_STATUS_2 = "Inprogress";
	static public final String GUI_PKTCBL_PROV_STATUS_3 = "failConfigFileError";
	static public final String GUI_PKTCBL_PROV_STATUS_4 = "passWithWarnings";
	static public final String GUI_PKTCBL_PROV_STATUS_5 = "passWithIncompleteParsing";
	static public final String GUI_PKTCBL_PROV_STATUS_6 = "failureInternalError";
	static public final String GUI_PKTCBL_PROV_STATUS_7 = "failOtherReason";
	
	// CM DOCSIS status
	static public final String GUI_CM_DOCSIS_STATUS_1 = "other";
	static public final String GUI_CM_DOCSIS_STATUS_2 = "ranging";
	static public final String GUI_CM_DOCSIS_STATUS_3 = "rangingAborted";
	static public final String GUI_CM_DOCSIS_STATUS_4 = "rangingComplete";
	static public final String GUI_CM_DOCSIS_STATUS_5 = "ipComplete";
	static public final String GUI_CM_DOCSIS_STATUS_6 = "registrationComplete";
	static public final String GUI_CM_DOCSIS_STATUS_7 = "accessDenied";
	static public final String GUI_CM_DOCSIS_STATUS_8 = "operational";
	static public final String GUI_CM_DOCSIS_STATUS_9 = "registeredBPIInitializing";
	
	// Common Alarm Base URLs
	static public final String BASE_URL_CMTS_LIST_ALARMS = "alarm_list.faces";
	static public final String BASE_URL_HFC_LIST_ALARMS = "hfc_alarm_list.faces";
	static public final String BASE_URL_CHANNEL_LIST_ALARMS = "channel_alarm_list.faces";
	static public final String BASE_URL_MTA_LIST_ALARMS = "alarm_list.faces";
	static public final String BASE_URL_CM_LIST_ALARMS = "alarm_list.faces";

	// Common Service Outage Base URLs
	static public final String BASE_URL_CMTS_LIST_OUTAGES = "service_outage_list.faces";
	static public final String BASE_URL_HFC_LIST_OUTAGES = "hfc_service_outage_list.faces";
	static public final String BASE_URL_CHANNEL_LIST_OUTAGES = "channel_service_outage_list.faces";
	static public final String BASE_URL_MTA_LIST_OUTAGES = "service_outage_list.faces";
	static public final String BASE_URL_CM_LIST_OUTAGES = "service_outage_list.faces";

	// Performance Exceptions - Threshold Crossing Alerts (TCA's)
	static public final String BASE_URL_CHANNEL_LIST_TCA = "tca_list.faces";
	static public final String BASE_URL_HFC_LIST_TCA = "tca_list.faces";
	static public final String BASE_URL_CM_LIST_TCA = "tca_list.faces";
	static public final String BASE_URL_MTA_LIST_TCA = "tca_list.faces";
	
	// Base URL for Plots
	static public final String BASE_URL_CMTS_PLOTS_1 = "plot_cmts_counts.jsp";
	static public final String BASE_URL_HFC_PLOTS_1 = "plot_hfc_counts.jsp";
	static public final String BASE_URL_CHANNEL_PLOTS_1 = "plot_channel_counts.jsp";
	static public final String BASE_URL_CM_PLOTS_1 = "plot_cm_status.jsp";
	static public final String BASE_URL_MTA_PLOTS_1 = "plot_mta_status.jsp";

	// Customer details URL
	static public final String BASE_URL_CUSTOMER_DETAILS_1 = "customer_details.faces";
	static public final String CUSTOMER_DETAILS_IMG_1 = "images/person.jpg";
	static public final String CUSTOMER_DETAILS_IMG_ALT_1 = "Customer Details";
	
	// Table Cell colours
	static public final String TABLE_CELL_TAG_YELLOW = "<td style=\"{background-color: #FFFF66;}\">";
	static public final String TABLE_CELL_TAG_RED = "<td style=\"{background-color: #FF0066;}\">";
	static public final String TABLE_CELL_TAG_GRAY = "<td style=\"{background-color: #DDDDDD;}\">";
	
	// General Yes/No string for tables
	static public final String TABLE_YES_STRING = "Yes";
	static public final String TABLE_NO_STRING = "No";
	static public final String TABLE_DASH_STRING = "--";
	
	// RT Portal
	static public final String BASE_URL_RT_PORTAL_1 = "auto_rt_query.faces";
	static public final String CM_RES_TYPE_PARAM = "&restype=8";
	static public final String MTA_RES_TYPE_PARAM = "&restype=9";

	// Column names
	static public final String NAME_COL_NAME = "Name";
	static public final String PLOTS_COL_NAME = "Plots";
	static public final String MAC_ADDR_COL_NAME = "Mac Address";
	static public final String MTA_MAC_ADDR_COL_NAME = "MTA Mac Address";
	static public final String STATUS_COL_NAME = "Status";
	static public final String DOCSIS_STATUS_COL_NAME = "DOCSIS CM Status";
	static public final String DOCSIS_STATUS_COL_NAME_SHORT = "DOCSIS";
	static public final String CUSTOMER_COL_NAME = "Customer";
	static public final String MTA_PING_STATUS_COL_NAME = "Ping Status";
	static public final String MTA_PING_STATUS_COL_NAME_SHORT = "Ping";
	static public final String MTA_PROV_STATUS_COL_NAME = "Provision Status";
	static public final String MTA_PROV_STATUS_COL_NAME_SHORT = "Provision";
	static public final String MTA_UNAVAILABLE_STRING = "Unavailable";
	static public final String TCA_COL_NAME = "In-Exception";
	static public final String TCA_COL_NAME_SING = "In-Exception";
	static public final String SERVICE_OUTAGE_COL_NAME = "Service<br/>Outage";
	static public final String SERVICE_OUTAGE_COL_NAME_SING = "Service<br/>Outage";
	static public final String ALARM_COL_NAME_SING = "Alarm";
	static public final String ALARM_COL_NAME = "In-Alarm";
	static public final String RT_PORTAL_COL_NAME = "Status<br/>Portal";
	
	// Status names
	static public final String MTA_AVAILABLE_COL_NAME = "Available";
	static public final String MTA_UNAVAILABLE_COL_NAME = "Unavailable";
	static public final String MTA_AVAILABLE_STRING = "Available";
	
}

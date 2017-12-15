/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 * 
 */
public class AdminHfcAlarmFormBean extends AbstractBean {

	/*
	 * CM Detection Window
	 */
	private String m_cm_threshold_detection_window;
	public String getCmDetectionWindow() {
		return m_cm_threshold_detection_window;
	}
	public void setCmDetectionWindow(String s) {
		m_cm_threshold_detection_window = s;
	}
	
	/*
	 * CM Soak Window
	 */
	private String m_cm_soak_window_1_start_time;
	public String getCmSoakWindow1StartTime() {
		return m_cm_soak_window_1_start_time;
	}
	public void setCmSoakWindow1StartTime(String s) {
		m_cm_soak_window_1_start_time = s;
	}

	private String m_cm_window_1_soak_duration;
	public String getCmWindow1SoakDuration() {
		return m_cm_window_1_soak_duration;
	}
	public void setCmWindow1SoakDuration(String s) {
		m_cm_window_1_soak_duration = s;
	}

	private String m_cm_soak_window_2_start_time;
	public String getCmSoakWindow2StartTime() {
		return m_cm_soak_window_2_start_time;
	}
	public void setCmSoakWindow2StartTime(String s) {
		m_cm_soak_window_2_start_time = s;
	}
	
	private String m_cm_window_2_soak_duration;
	public String getCmWindow2SoakDuration() {
		return m_cm_window_2_soak_duration;
	}
	public void setCmWindow2SoakDuration(String s) {
		m_cm_window_2_soak_duration = s;
	}

	/*
	 * CM Threshold 1
	 */
	private String m_cm_threshold_1;
	public String getCmThreshold1() {
		return m_cm_threshold_1;
	}
	public void setCmThreshold1(String s) {
		m_cm_threshold_1 = s;
	}

	private String m_cm_threshold_1_cm;
	public String getCmThreshold1Cm() {
		return m_cm_threshold_1_cm;
	}
	public void setCmThreshold1Cm(String s) {
		m_cm_threshold_1_cm = s;
	}

	/*
	 * CM Threshold 2
	 */
	private String m_cm_threshold_2;
	public String getCmThreshold2() {
		return m_cm_threshold_2;
	}
	public void setCmThreshold2(String s) {
		m_cm_threshold_2 = s;
	}

	private String m_cm_threshold_2_cm;
	public String getCmThreshold2Cm() {
		return m_cm_threshold_2_cm;
	}
	public void setCmThreshold2Cm(String s) {
		m_cm_threshold_2_cm = s;
	}

	/*
	 * MTA Threshold 1
	 */
	private String m_mta_threshold_1;
	public String getMtaThreshold1() {
		return m_mta_threshold_1;
	}
	public void setMtaThreshold1(String s) {
		m_mta_threshold_1 = s;
	}

	private String m_mta_threshold_detection_window;
	public String getMtaDetectionWindow() {
		return m_mta_threshold_detection_window;
	}
	public void setMtaDetectionWindow(String s) {
		m_mta_threshold_detection_window = s;
	}
	
	private String m_mta_soak_window_1_start_time;
	public String getMtaSoakWindow1StartTime() {
		return m_mta_soak_window_1_start_time;
	}
	public void setMtaSoakWindow1StartTime(String s) {
		m_mta_soak_window_1_start_time = s;
	}

	private String m_mta_soak_window_2_start_time;
	public String getMtaSoakWindow2StartTime() {
		return m_mta_soak_window_2_start_time;
	}
	public void setMtaSoakWindow2StartTime(String s) {
		m_mta_soak_window_2_start_time = s;
	}

	private String m_mta_window_1_soak_duration;
	public String getMtaWindow1SoakDuration() {
		return m_mta_window_1_soak_duration;
	}
	public void setMtaWindow1SoakDuration(String s) {
		m_mta_window_1_soak_duration = s;
	}
	
	private String m_mta_window_2_soak_duration;
	public String getMtaWindow2SoakDuration() {
		return m_mta_window_2_soak_duration;
	}
	public void setMtaWindow2SoakDuration(String s) {
		m_mta_window_2_soak_duration = s;
	}
	
	/*
	 * MTA Power Threshold 1
	 */
	private String m_mta_pwr_threshold_1;
	public String getMtaPowerThreshold1() {
		return m_mta_pwr_threshold_1;
	}
	public void setMtaPowerThreshold1(String s) {
		m_mta_pwr_threshold_1 = s;
	}

	private String m_mta_power_detection_window;
	public String getMtaPowerDetectionWindow() {
		return m_mta_power_detection_window;
	}
	public void setMtaPowerDetectionWindow(String s) {
		m_mta_power_detection_window = s;
	}
	
	private String m_mta_pwr_soak_window_1_start_time;
	public String getMtaPowerSoakWindow1StartTime() {
		return m_mta_pwr_soak_window_1_start_time;
	}
	public void setMtaPowerSoakWindow1StartTime(String s) {
		m_mta_pwr_soak_window_1_start_time = s;
	}

	private String m_mta_pwr_soak_window_2_start_time;
	public String getMtaPowerSoakWindow2StartTime() {
		return m_mta_pwr_soak_window_2_start_time;
	}
	public void setMtaPowerSoakWindow2StartTime(String s) {
		m_mta_pwr_soak_window_2_start_time = s;
	}

	private String m_mta_pwr_window_1_soak_duration;
	public String getMtaPowerWindow1SoakDuration() {
		return m_mta_pwr_window_1_soak_duration;
	}
	public void setMtaPowerWindow1SoakDuration(String s) {
		m_mta_pwr_window_1_soak_duration = s;
	}
	
	private String m_mta_pwr_window_2_soak_duration;
	public String getMtaPowerWindow2SoakDuration() {
		return m_mta_pwr_window_2_soak_duration;
	}
	public void setMtaPowerWindow2SoakDuration(String s) {
		m_mta_pwr_window_2_soak_duration = s;
	}
	

	/**
	 * 
	 * 
	 */
	public AdminHfcAlarmFormBean() {
		super();
	}
}

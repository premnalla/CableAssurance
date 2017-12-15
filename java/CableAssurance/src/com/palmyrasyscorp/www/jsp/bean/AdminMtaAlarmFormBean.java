/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class AdminMtaAlarmFormBean extends AbstractBean {
	
	/*
	 * MTA Unavailable alarm 
	 */
	private String m_mta_unavail_win_1_soak_st_tm;
	public String getMtaUnavailWin1SoakStartTime() {
		return m_mta_unavail_win_1_soak_st_tm;
	}
	public void setMtaUnavailWin1SoakStartTime(String s) {
		m_mta_unavail_win_1_soak_st_tm = s;
	}

	private String m_mta_unavail_win_2_soak_st_tm;
	public String getMtaUnavailWin2SoakStartTime() {
		return m_mta_unavail_win_2_soak_st_tm;
	}
	public void setMtaUnavailWin2SoakStartTime(String s) {
		m_mta_unavail_win_2_soak_st_tm = s;
	}

	private String m_mta_unavail_win_1_soak_dur;
	public String getMtaUnavailWin1SoakDuration() {
		return m_mta_unavail_win_1_soak_dur;
	}
	public void setMtaUnavailWin1SoakDuration(String s) {
		m_mta_unavail_win_1_soak_dur = s;
	}
	
	private String m_mta_unavail_win_2_soak_dur;
	public String getMtaUnavailWin2SoakDuration() {
		return m_mta_unavail_win_2_soak_dur;
	}
	public void setMtaUnavailWin2SoakDuration(String s) {
		m_mta_unavail_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA CMS LOC alarm 
	 */
	private String m_mta_cmsloc_win_1_soak_st_tm;
	public String getMtaCmsLocWin1SoakStartTime() {
		return m_mta_cmsloc_win_1_soak_st_tm;
	}
	public void setMtaCmsLocWin1SoakStartTime(String s) {
		m_mta_cmsloc_win_1_soak_st_tm = s;
	}

	private String m_mta_cmsloc_win_2_soak_st_tm;
	public String getMtaCmsLocWin2SoakStartTime() {
		return m_mta_cmsloc_win_2_soak_st_tm;
	}
	public void setMtaCmsLocWin2SoakStartTime(String s) {
		m_mta_cmsloc_win_2_soak_st_tm = s;
	}

	private String m_mta_cmsloc_win_1_soak_dur;
	public String getMtaCmsLocWin1SoakDuration() {
		return m_mta_cmsloc_win_1_soak_dur;
	}
	public void setMtaCmsLocWin1SoakDuration(String s) {
		m_mta_cmsloc_win_1_soak_dur = s;
	}
	
	private String m_mta_cmsloc_win_2_soak_dur;
	public String getMtaCmsLocWin2SoakDuration() {
		return m_mta_cmsloc_win_2_soak_dur;
	}
	public void setMtaCmsLocWin2SoakDuration(String s) {
		m_mta_cmsloc_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA Battery Missing alarm 
	 */
	private String m_mta_battmiss_win_1_soak_st_tm;
	public String getMtaBattMissWin1SoakStartTime() {
		return m_mta_battmiss_win_1_soak_st_tm;
	}
	public void setMtaBattMissWin1SoakStartTime(String s) {
		m_mta_battmiss_win_1_soak_st_tm = s;
	}

	private String m_mta_battmiss_win_2_soak_st_tm;
	public String getMtaBattMissWin2SoakStartTime() {
		return m_mta_battmiss_win_2_soak_st_tm;
	}
	public void setMtaBattMissWin2SoakStartTime(String s) {
		m_mta_battmiss_win_2_soak_st_tm = s;
	}

	private String m_mta_battmiss_win_1_soak_dur;
	public String getMtaBattMissWin1SoakDuration() {
		return m_mta_battmiss_win_1_soak_dur;
	}
	public void setMtaBattMissWin1SoakDuration(String s) {
		m_mta_battmiss_win_1_soak_dur = s;
	}
	
	private String m_mta_battmiss_win_2_soak_dur;
	public String getMtaBattMissWin2SoakDuration() {
		return m_mta_battmiss_win_2_soak_dur;
	}
	public void setMtaBattMissWin2SoakDuration(String s) {
		m_mta_battmiss_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA On-Battery alarm 
	 */
	private String m_mta_onbatt_win_1_soak_st_tm;
	public String getMtaOnBattWin1SoakStartTime() {
		return m_mta_onbatt_win_1_soak_st_tm;
	}
	public void setMtaOnBattWin1SoakStartTime(String s) {
		m_mta_onbatt_win_1_soak_st_tm = s;
	}

	private String m_mta_onbatt_win_2_soak_st_tm;
	public String getMtaOnBattWin2SoakStartTime() {
		return m_mta_onbatt_win_2_soak_st_tm;
	}
	public void setMtaOnBattWin2SoakStartTime(String s) {
		m_mta_onbatt_win_2_soak_st_tm = s;
	}

	private String m_mta_onbatt_win_1_soak_dur;
	public String getMtaOnBattWin1SoakDuration() {
		return m_mta_onbatt_win_1_soak_dur;
	}
	public void setMtaOnBattWin1SoakDuration(String s) {
		m_mta_onbatt_win_1_soak_dur = s;
	}
	
	private String m_mta_onbatt_win_2_soak_dur;
	public String getMtaOnBattWin2SoakDuration() {
		return m_mta_onbatt_win_2_soak_dur;
	}
	public void setMtaOnBattWin2SoakDuration(String s) {
		m_mta_onbatt_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA Replace Battery alarm 
	 */
	private String m_mta_replbatt_win_1_soak_st_tm;
	public String getMtaReplBattWin1SoakStartTime() {
		return m_mta_replbatt_win_1_soak_st_tm;
	}
	public void setMtaReplBattWin1SoakStartTime(String s) {
		m_mta_replbatt_win_1_soak_st_tm = s;
	}

	private String m_mta_replbatt_win_2_soak_st_tm;
	public String getMtaReplBattWin2SoakStartTime() {
		return m_mta_replbatt_win_2_soak_st_tm;
	}
	public void setMtaReplBattWin2SoakStartTime(String s) {
		m_mta_replbatt_win_2_soak_st_tm = s;
	}

	private String m_mta_replbatt_win_1_soak_dur;
	public String getMtaReplBattWin1SoakDuration() {
		return m_mta_replbatt_win_1_soak_dur;
	}
	public void setMtaReplBattWin1SoakDuration(String s) {
		m_mta_replbatt_win_1_soak_dur = s;
	}
	
	private String m_mta_replbatt_win_2_soak_dur;
	public String getMtaReplBattWin2SoakDuration() {
		return m_mta_replbatt_win_2_soak_dur;
	}
	public void setMtaReplBattWin2SoakDuration(String s) {
		m_mta_replbatt_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA Hardware Failure alarm 
	 */
	private String m_mta_hwfail_win_1_soak_st_tm;
	public String getMtaHwFailWin1SoakStartTime() {
		return m_mta_hwfail_win_1_soak_st_tm;
	}
	public void setMtaHwFailWin1SoakStartTime(String s) {
		m_mta_hwfail_win_1_soak_st_tm = s;
	}

	private String m_mta_hwfail_win_2_soak_st_tm;
	public String getMtaHwFailWin2SoakStartTime() {
		return m_mta_hwfail_win_2_soak_st_tm;
	}
	public void setMtaHwFailWin2SoakStartTime(String s) {
		m_mta_hwfail_win_2_soak_st_tm = s;
	}

	private String m_mta_hwfail_win_1_soak_dur;
	public String getMtaHwFailWin1SoakDuration() {
		return m_mta_hwfail_win_1_soak_dur;
	}
	public void setMtaHwFailWin1SoakDuration(String s) {
		m_mta_hwfail_win_1_soak_dur = s;
	}
	
	private String m_mta_hwfail_win_2_soak_dur;
	public String getMtaHwFailWin2SoakDuration() {
		return m_mta_hwfail_win_2_soak_dur;
	}
	public void setMtaHwFailWin2SoakDuration(String s) {
		m_mta_hwfail_win_2_soak_dur = s;
	}	
	
	/*
	 * MTA Line-Card Failure alarm 
	 */
	private String m_mta_lcfail_win_1_soak_st_tm;
	public String getMtaLcFailWin1SoakStartTime() {
		return m_mta_lcfail_win_1_soak_st_tm;
	}
	public void setMtaLcFailWin1SoakStartTime(String s) {
		m_mta_lcfail_win_1_soak_st_tm = s;
	}

	private String m_mta_lcfail_win_2_soak_st_tm;
	public String getMtaLcFailWin2SoakStartTime() {
		return m_mta_lcfail_win_2_soak_st_tm;
	}
	public void setMtaLcFailWin2SoakStartTime(String s) {
		m_mta_lcfail_win_2_soak_st_tm = s;
	}

	private String m_mta_lcfail_win_1_soak_dur;
	public String getMtaLcFailWin1SoakDuration() {
		return m_mta_lcfail_win_1_soak_dur;
	}
	public void setMtaLcFailWin1SoakDuration(String s) {
		m_mta_lcfail_win_1_soak_dur = s;
	}
	
	private String m_mta_lcfail_win_2_soak_dur;
	public String getMtaLcFailWin2SoakDuration() {
		return m_mta_lcfail_win_2_soak_dur;
	}
	public void setMtaLcFailWin2SoakDuration(String s) {
		m_mta_lcfail_win_2_soak_dur = s;
	}	
	
	/**
	 * 
	 *
	 */
	public AdminMtaAlarmFormBean() {
		super();
	}
	
}

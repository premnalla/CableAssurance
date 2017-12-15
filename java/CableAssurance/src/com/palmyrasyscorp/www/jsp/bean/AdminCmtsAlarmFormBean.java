/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class AdminCmtsAlarmFormBean extends AbstractBean {

	/*
	 * CMTS comms failure alarm 
	 */
	private String m_cmts_comms_fail_win_1_soak_st_tm;
	public String getCmtsCommsFailWin1SoakStartTime() {
		return m_cmts_comms_fail_win_1_soak_st_tm;
	}
	public void setCmtsCommsFailWin1SoakStartTime(String s) {
		m_cmts_comms_fail_win_1_soak_st_tm = s;
	}

	private String m_cmts_comms_fail_win_2_soak_st_tm;
	public String getCmtsCommsFailWin2SoakStartTime() {
		return m_cmts_comms_fail_win_2_soak_st_tm;
	}
	public void setCmtsCommsFailWin2SoakStartTime(String s) {
		m_cmts_comms_fail_win_2_soak_st_tm = s;
	}

	private String m_cmts_comms_fail_win_1_soak_dur;
	public String getCmtsCommsFailWin1SoakDuration() {
		return m_cmts_comms_fail_win_1_soak_dur;
	}
	public void setCmtsCommsFailWin1SoakDuration(String s) {
		m_cmts_comms_fail_win_1_soak_dur = s;
	}
	
	private String m_cmts_comms_fail_win_2_soak_dur;
	public String getCmtsCommsFailWin2SoakDuration() {
		return m_cmts_comms_fail_win_2_soak_dur;
	}
	public void setCmtsCommsFailWin2SoakDuration(String s) {
		m_cmts_comms_fail_win_2_soak_dur = s;
	}	
	
	/**
	 * 
	 *
	 */
	public AdminCmtsAlarmFormBean() {
		super();
	}
	
}

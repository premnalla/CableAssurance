/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class AdminCmsAlarmFormBean extends AbstractBean {

	/*
	 * CMS LOC alarm 
	 */
	private String m_cms_loc_win_1_soak_st_tm;
	public String getCmsLocWin1SoakStartTime() {
		return m_cms_loc_win_1_soak_st_tm;
	}
	public void setCmsLocWin1SoakStartTime(String s) {
		m_cms_loc_win_1_soak_st_tm = s;
	}

	private String m_cms_loc_win_2_soak_st_tm;
	public String getCmsLocWin2SoakStartTime() {
		return m_cms_loc_win_2_soak_st_tm;
	}
	public void setCmsLocWin2SoakStartTime(String s) {
		m_cms_loc_win_2_soak_st_tm = s;
	}

	private String m_cms_loc_win_1_soak_dur;
	public String getCmsLocWin1SoakDuration() {
		return m_cms_loc_win_1_soak_dur;
	}
	public void setCmsLocWin1SoakDuration(String s) {
		m_cms_loc_win_1_soak_dur = s;
	}
	
	private String m_cms_loc_win_2_soak_dur;
	public String getCmsLocWin2SoakDuration() {
		return m_cms_loc_win_2_soak_dur;
	}
	public void setCmsLocWin2SoakDuration(String s) {
		m_cms_loc_win_2_soak_dur = s;
	}	
	
	/**
	 * 
	 *
	 */
	public AdminCmsAlarmFormBean() {
		super();
	}
	
}

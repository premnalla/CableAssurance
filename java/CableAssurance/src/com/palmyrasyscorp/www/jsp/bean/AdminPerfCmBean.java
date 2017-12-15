/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

/**
 * @author Prem
 *
 */
public class AdminPerfCmBean extends AbstractBean {

	private String m_save;
	public String getSave() { return m_save; }
	public void setSave(String s) { m_save = s; }
	
	/*
	 * NOTE: "Up" - Upper limit; "Low" - lower limit
	 */
	
	private String m_downstreamSnrMin;
	public String getLowDwnStrmSnr() { return m_downstreamSnrMin; }
	public void setLowDwnStrmSnr(String s) { m_downstreamSnrMin = s; }
	
//	private String m_downstreamSnrMax;
//	public String getUpDwnStrmSnr() { return m_downstreamSnrMax; }
//	public void setUpDwnStrmSnr(String s) { m_downstreamSnrMax = s; }
	
	private String m_downstreamPowerMax;
	public String getUpDwnStrmPower() { return m_downstreamPowerMax; }
	public void setUpDwnStrmPower(String s) { m_downstreamPowerMax = s; }
	
	private String m_downstreamPowerMin;
	public String getLowDwnStrmPower() { return m_downstreamPowerMin; }
	public void setLowDwnStrmPower(String s) { m_downstreamPowerMin = s; }
	
	private String m_upstreamPowerMax;
	public String getUpUpStrmPower() { return m_upstreamPowerMax; }
	public void setUpUpStrmPower(String s) { m_upstreamPowerMax = s; }	
	
	private String m_upstreamPowerMin;
	public String getLowUpStrmPower() { return m_upstreamPowerMin; }
	public void setLowUpStrmPower(String s) { m_upstreamPowerMin = s; }	
	
	public AdminPerfCmBean() {
		super();
	}
}

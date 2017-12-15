/**
 * 
 */
package com.palmyrasyscorp.www.jsp.bean;

import javax.servlet.http.HttpServletRequest;

import com.palmyrasyscorp.www.jsp.helper.UrlHelper;
import com.palmyrasys.www.webservices.CableAssurance.InputTimeT;

/**
 * @author Prem
 *
 */
public abstract class AbstractTimeBasedFilterBean extends AbstractBean {

	private String m_fmMonth = "";
	public String getFmMonth() {
		return m_fmMonth;
	}
	public void setFmMonth(String in) {
		if (in != null) {
			m_fmMonth = in;
		}
	}
	
	private String m_fmDay = "";
	public String getFmDay() {
		return m_fmDay;
	}
	public void setFmDay(String in) {
		if (in != null) {
			m_fmDay = in;
		}
	}
	
	private String m_fmYear = "";
	public String getFmYear() {
		return m_fmYear;
	}
	public void setFmYear(String in) {
		if (in != null) {
			m_fmYear = in;
		}
	}
	
	private String m_fmHour = "";
	public String getFmHour() {
		return m_fmHour;
	}
	public void setFmHour(String in) {
		if (in != null) {
			m_fmHour = in;
		}
	}
	
	private String m_fmMinute = "";
	public String getFmMinute() {
		return m_fmMinute;
	}
	public void setFmMinute(String in) {
		if (in != null) {
			m_fmMinute = in;
		}
	}
		
	private String m_toMonth = "";
	public String getToMonth() {
		return m_toMonth;
	}
	public void setToMonth(String in) {
		if (in != null) {
			m_toMonth = in;
		}
	}
	
	private String m_toDay = "";
	public String getToDay() {
		return m_toDay;
	}
	public void setToDay(String in) {
		if (in != null) {
			m_toDay = in;
		}
	}
	
	private String m_toYear = "";
	public String getToYear() {
		return m_toYear;
	}
	public void setToYear(String in) {
		if (in != null) {
			m_toYear = in;
		}
	}
	
	private String m_toHour = "";
	public String getToHour() {
		return m_toHour;
	}
	public void setToHour(String in) {
		if (in != null) {
			m_toHour = in;
		}
	}
	
	private String m_toMinute = "";
	public String getToMinute() {
		return m_toMinute;
	}
	public void setToMinute(String in) {
		if (in != null) {
			m_toMinute = in;
		}
	}
	
	private int m_iBatch = 100;
	private String m_defaultBatchSize = "100";
	protected void setDefaultBatchSize(String in) {
		m_defaultBatchSize = in;
		m_iBatch = UrlHelper.StringToInt(in);
	}
	protected String getDefaultBatchSize() {
		return m_defaultBatchSize;
	}
	private String m_batchSize = m_defaultBatchSize;
	public String getBatch() {
		// System.out.println("getBatch:"+m_batchSize);
		return m_batchSize;
	}
	public void setBatch(String in) {
		// System.out.println("setBatch:"+in);
		if (in != null) {
			if (in.length()==0) {
				m_batchSize = m_defaultBatchSize;
			} else {
				m_batchSize = in;
			}
		}
	}
	public int retBatch() {
		int ret;
		try {
			ret = Integer.parseInt(m_batchSize);
		} catch (Exception e) {
			ret = m_iBatch;
		}
		return (ret);
	}
	
	/**
	 * 
	 *
	 */
	protected AbstractTimeBasedFilterBean() {
		
	}
	

	/**
	 * 
	 * @param request
	 */
	public void setAttributes(HttpServletRequest request) {
		setFmMonth(request.getParameter(BeanConstants.FROM_MONTH));
		setFmDay(request.getParameter(BeanConstants.FROM_DAY));
		setFmYear(request.getParameter(BeanConstants.FROM_YEAR));
		setFmHour(request.getParameter(BeanConstants.FROM_HOUR));
		setFmMinute(request.getParameter(BeanConstants.FROM_MINUTE));
		
		setToMonth(request.getParameter(BeanConstants.TO_MONTH));
		setToDay(request.getParameter(BeanConstants.TO_DAY));
		setToYear(request.getParameter(BeanConstants.TO_YEAR));
		setToHour(request.getParameter(BeanConstants.TO_HOUR));
		setToMinute(request.getParameter(BeanConstants.TO_MINUTE));
		
		setBatch(request.getParameter(BeanConstants.BATCH));
	}
	
	/**
	 * 
	 * @param fmTime
	 * @param toTime
	 */
	public void setInputTime(InputTimeT fmTime, InputTimeT toTime) {
		fmTime.setDayOfMonth(getDay(getFmDay()));
		fmTime.setMonthOfYear(getMonth(getFmMonth()));
		fmTime.setYear(getYear(getFmYear()));

		toTime.setDayOfMonth(getDay(getToDay()));
		toTime.setMonthOfYear(getMonth(getToMonth()));
		toTime.setYear(getYear(getToYear()));
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	protected short getDay(String day) {
		short ret;
		
		try {
			ret = Short.parseShort(day);
		} catch (Exception e) {
			ret = -1;
		}

		return ret;
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	protected short getMonth(String month) {
		short ret;
		
		try {
			ret = Short.parseShort(month);
			--ret;
		} catch (Exception e) {
			ret = -1;
		}

		return ret;
	}
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	protected short getYear(String yr) {
		short ret;
		
		try {
			ret = Short.parseShort(yr);
		} catch (Exception e) {
			ret = -1;
		}

		return ret;
	}
	
}

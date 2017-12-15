/**
 * OBSOLETED
 */
package com.palmyrasyscorp.db.tables;

import java.sql.Timestamp;
import java.math.*;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.common.base.*;

/**
 * ********* OBSOLETED ********* OBSOLETED **********
 * 
 * @author Prem
 *
 */
public class CmPerfEntry extends AbstractPerfEntry {

	private BigInteger m_id;
	private Long m_cm_res_id;
	private Timestamp m_timestamp;
	
	private Float m_upstream_snr;
	public Float getUpstreamSnr() { return m_upstream_snr; }
	
	private Float m_downstream_snr;
	public Float getDownstreamSnr() { return m_downstream_snr; }
	
	private Float m_upstream_power;
	public Float getUpstreamPower() { return m_upstream_power; }
	
	private Float m_downstream_power;
	public Float getDownstreamPower() { return m_downstream_power; }

	/**
	 * 
	 */
	protected CmPerfEntry() {
		super();
	}

	public CmPerfEntry(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (BigInteger) rs.getObject(i++);
			m_cm_res_id = (Long) rs.getObject(i++);
			m_timestamp = (Timestamp) rs.getObject(i++);
			m_downstream_snr = DbUtils.ConvertBigDecimalToFloat((BigDecimal)rs.getObject(i++));
			m_downstream_power = DbUtils.ConvertBigDecimalToFloat((BigDecimal)rs.getObject(i++));
			m_upstream_power = DbUtils.ConvertBigDecimalToFloat((BigDecimal)rs.getObject(i++));
			m_upstream_snr = DbUtils.ConvertBigDecimalToFloat((BigDecimal)rs.getObject(i++));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUpstreamSnrHtmlTd() {
		float snr = m_upstream_snr.floatValue();
		return (getSnrHtmlTd(snr));
	}
	
	public String getDownstreamSnrHtmlTd() {
		float snr = m_downstream_snr.floatValue();
		return (getSnrHtmlTd(snr));
	}
	
	private String getSnrHtmlTd(float snr) {
		String ret;
		
		if (snr >= 30.5f) {
			ret = "<td>";
		} else if (snr <30.5f && snr >= 25.0f) {
			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
		} else {
			ret = GlobalStrings.TABLE_CELL_TAG_RED;
		}
		
		return ret; 
	}

	public String getDownstreamPowerHtmlTd() {
		String ret;
		float pow = m_downstream_power.floatValue();
		if ((pow >= -2.5f) && (pow <= 2.5)) {
			ret = "<td>";
		} else if ((pow >= -10.0f) && (pow <= 10.0)) {
			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
		} else {
			ret = GlobalStrings.TABLE_CELL_TAG_RED;
		}
		
		return ret; 
	}

	public String getUpstreamPowerHtmlTd() {
		String ret;
		float pow = m_upstream_power.floatValue();
		if ((pow >= 40.0) && (pow < 55.0)) {
			ret = "<td>";
		} else if ((pow >= 20.0) && (pow < 40.0)) {
			ret = GlobalStrings.TABLE_CELL_TAG_YELLOW;
		} else {
			ret = GlobalStrings.TABLE_CELL_TAG_RED;
		}
		
		return ret; 
	}

}

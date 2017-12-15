/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.ProxyDbResultSet;

/**
 * @author Prem
 *
 */
public class SnmpV2CAttributes extends AbstractDbObject {

	private static Log log = LogFactory.getLog(SnmpV2CAttributes.class);

	private Long m_cmts_res_id;
	public Long getCmtsResId() {
		return m_cmts_res_id;
	}
	public void setCmtsResId(long ri) {
		m_cmts_res_id = new Long(ri);
	}
	
	protected String m_read_community;
	public String getReadCommunity() {
		return m_read_community;
	}
	public void setReadCommunity(String s) {
		m_read_community = s;
	}
	
	protected String m_write_community;
	public String getWriteCommunity() {
		return m_write_community;
	}
	public void setWriteCommunity(String s) {
		m_write_community = s;
	}
	
	/**
	 * 
	 *
	 */
	protected SnmpV2CAttributes() {
		super();
	}
	
	/**
	 * 
	 *
	 */
	protected SnmpV2CAttributes(long cmtsResId) {
		super();
		m_cmts_res_id = new Long(cmtsResId);
	}
	
	/**
	 * 
	 * @param rs
	 */
	public SnmpV2CAttributes(ProxyDbResultSet rs) {
		super();

		int i=1;
		try {			
			i++; // skip id
			m_cmts_res_id = (Long) rs.getObject(i++);
			m_read_community = rs.getString(i++);
			m_write_community = rs.getString(i++);
		}
		catch (Exception e)
		{
			log.error(null, e);	
		}
	}
	
	/**
	 * 
	 * @param rs
	 * @param ih
	 */
	public SnmpV2CAttributes(ProxyDbResultSet rs, IntegerHolder ih) {
		super();

		int i=ih.value;
		try {			
			i++; // skip id
			m_cmts_res_id = (Long) rs.getObject(i++);
			m_read_community = rs.getString(i++);
			m_write_community = rs.getString(i++);
		}
		catch (Exception e)
		{
			log.error(null, e);	
		}
		ih.value = i;
	}

	/**
	 * 
	 * @param in
	 */
	public SnmpV2CAttributes(SnmpV2CAttributes in) {
		this.m_cmts_res_id = in.m_cmts_res_id;
		this.m_read_community = in.m_read_community;
		this.m_write_community = in.m_write_community;
	}
	
}

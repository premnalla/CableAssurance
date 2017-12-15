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
public abstract class AbstractTopoLookup extends AbstractDbObject {

	private static Log log = LogFactory.getLog(AbstractTopoLookup.class);

	private Long m_id;
	public Long getId() {
		return (m_id);
	}
	public void setId(Long in) {
		m_id = in;
	}
	
	private Long m_resId;
	public Long getResId() {
		return (m_resId);
	}
	public void setResId(Long resId){
		m_resId = resId;
	}
	
	private String m_mac;
	public String getMac() {
		return (m_mac);
	}
	public void setMac(String mac) {
		m_mac = mac;
	}
	
	private Integer m_containerId;
	public Integer getContainerId() {
		return (m_containerId);
	}
	public void setContainerId(Integer cId) {
		m_containerId = cId;
	}
	
	/**
	 * 
	 *
	 */
	protected AbstractTopoLookup() {
		
	}
	
	/**
	 * 
	 * @param rs
	 */
	public AbstractTopoLookup(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Long) rs.getObject(i++);
			m_resId = (Long) rs.getObject(i++);
			m_mac = rs.getString(i++);
			m_containerId = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}		
	}
	
}

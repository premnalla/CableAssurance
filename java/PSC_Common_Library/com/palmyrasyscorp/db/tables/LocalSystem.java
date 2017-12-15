/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class LocalSystem extends AbstractDbObject {

	private static Log log = LogFactory.getLog(LocalSystem.class);

	private Integer m_systemType;
	public Integer getSystemType() { return (m_systemType); }
	public void setSystemType(int s) { m_systemType = s; }
	
	private String m_systemName;	
	public String getSystemName() { return (m_systemName); }
	public void setSystemName(String s) { m_systemName = s; }
	
	private String m_parentIpAddress;
	public String getParentHost() { return (m_parentIpAddress); }
	public void setParentHost(String s) { m_parentIpAddress = s; }
	
	private Integer m_parentIpType;
	public Integer getParentIpType() { return m_parentIpType; }
	public void setParentIpType(Integer i) { m_parentIpType = i; }
	
	public static final short LSTYPE_ENTERPRISE = 1;
	public static final short LSTYPE_REGION = 2;
	public static final short LSTYPE_MARKET = 3;
	public static final short LSTYPE_BLADE = 4;
	
	public LocalSystem()
	{
		querySelf();
	}
	
	public LocalSystem(ProxyDbResultSet rs)
	{
		int i=1;
		try {
			m_systemType = (Integer) rs.getObject(i++);
			m_systemName = rs.getString(i++);
			m_parentIpAddress = rs.getString(i++);
			m_parentIpType = (Integer) rs.getObject(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
	
	private void querySelf()
	{
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			String qryStr = "SELECT * FROM canet.local_system";

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i=1;
				m_systemType = (Integer) rs.getObject(i++);
				m_systemName = rs.getString(i++);
				m_parentIpAddress = rs.getString(i++);
				m_parentIpType = (Integer) rs.getObject(i++);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (rs != null) {
				rs.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean updateRow() {
		boolean ret = false;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer updStr = new StringBuffer(GlobalQueries.UPD_LOCAL_SYSTEM);
			updStr.append("system_name=\"").append(m_systemName).append("\", ")
				.append("system_type=").append(m_systemType);
			updStr.append(",parent_ipaddr=\"").append(m_parentIpAddress).append("\",")
				.append("parent_ip_type=").append(m_parentIpType);

			// System.out.println(updStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(updStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}
	

}

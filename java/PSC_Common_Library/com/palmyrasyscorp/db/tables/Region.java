package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.common.base.Host;

public class Region extends AbstractDbObject implements Host {

	private static Log log = LogFactory.getLog(Region.class);
	
	private Integer m_regionId;
	private Boolean m_isDeleted;
	private String m_name;

	public short getAddressType() { return 0; }
	public String getHost() { return ""; }

	public Region() {}
	
	public Region(int id)
	{
		m_regionId = id;
		querySelf();
	}
	
	public Region(ProxyDbResultSet rs)
	{
		int i=1;
		try {
			m_regionId = (Integer) rs.getObject(i++);
			i++;
			m_name = rs.getString(i++);
			m_isDeleted = (Boolean) rs.getObject(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
	
	public Integer getId()
	{
		return (m_regionId);
	}
	
	public String getName() { return m_name; }
	
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
			String qryStr = "SELECT * FROM canet.region WHERE region_id=" + getId();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				i++;
				i++;
				m_name = rs.getString(i++);
				m_isDeleted = (Boolean) rs.getObject(i++);
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
	
}

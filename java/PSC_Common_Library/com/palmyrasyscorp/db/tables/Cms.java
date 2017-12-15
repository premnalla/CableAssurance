/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 *
 */
public class Cms extends AbstractDbObject implements AlertLevel {

	private static Log log = LogFactory.getLog(Cms.class);

	private Integer m_id;
	
	private Long m_cms_res_id;
	public Long getCmsResId() { return m_cms_res_id; }
	
	private String m_name;
	public String getCmsName() { return m_name; }
	public void setCmsName(String s) { m_name = s; }
	
	private String m_vendor;
	public String getVendor() { return m_vendor; }
	public void setVendor(String s) { m_vendor = s; }
	
	private String m_type;
	public String getCmsType() { return m_type; }
	public void setCmsType(String s) { m_type = s; }
	
	private String m_subType;
	public String getCmsSubType() { return m_subType; }
	public void setCmsSubType(String s) { m_subType = s; }

	private String m_description;
	public String getCmsDescription() { return m_description; }
	public void setCmsDescription(String s) { m_description = s; }

	private String m_url;
	public String getCmsSoapUrl() { return m_url; }
	public void setCmsSoapUrl(String s) { m_url = s; }

	private String m_loginUserId;
	public String getCmsLoginUserId() { return m_loginUserId; }
	public void setCmsLoginUserId(String s) { m_loginUserId = s; }

	private String m_loginPw;
	public String getCmsLoginPw() { return m_loginPw; }
	public void setCmsLoginPw(String s) { m_loginPw = s; }

	private String m_host;
	public String getHost() { return m_host; }
	public void setHost(String s) { m_host = s; }
	
	private Integer m_ip_address_type;
	public Integer getIpAddressType() { return m_ip_address_type; }

	private Integer m_alert_level;

	private Boolean m_is_deleted;
	public Boolean getIsDeleted() { return m_is_deleted; }

	public Cms() {}
	
	public Cms(long res_id)
	{
		m_cms_res_id = new Long(res_id);
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public Cms(ProxyDbResultSet rs)
	{
		int i=1;
		try {
			
			m_id = (Integer) rs.getObject(i++);
			m_cms_res_id = (Long) rs.getObject(i++);
			i++;
			m_name = rs.getString(i++);
			m_vendor = rs.getString(i++);
			m_type = rs.getString(i++);
			m_subType = rs.getString(i++);
			m_description = rs.getString(i++);
			m_url = rs.getString(i++);
			m_loginUserId = rs.getString(i++);
			m_loginPw = rs.getString(i++);
			m_host = rs.getString(i++);		
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_alert_level = (Integer) rs.getObject(i++);
			m_is_deleted = (Boolean) rs.getObject(i++);
			
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
	}
	
	/**
	 * 
	 *
	 */
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CMS_IN_LOCAL_SYSTEM);
			qryStr.append(" where c.cms_res_id=").append(getCmsResId());

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
				m_id = (Integer) rs.getObject(i++);
				
				// m_cms_res_id = mrid;
				i++;
				
				// last updated
				i++;
				
				m_name = rs.getString(i++);
				m_vendor = rs.getString(i++);
				m_type = rs.getString(i++);
				m_subType = rs.getString(i++);
				m_description = rs.getString(i++);
				m_url = rs.getString(i++);
				m_loginUserId = rs.getString(i++);
				m_loginPw = rs.getString(i++);
				m_host = rs.getString(i++);		
				m_ip_address_type = (Integer) rs.getObject(i++);
				m_alert_level = (Integer) rs.getObject(i++);
				m_is_deleted = (Boolean) rs.getObject(i++);

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
	 * TODO Fill in remaining update fields
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
			StringBuffer updStr = new StringBuffer(GlobalQueries.UPD_CMS_IN_LOCAL_SYSTEM);
			updStr.append("name=\"").append(m_name).append("\", ")
			.append("ip_address=\"").append(m_host).append("\" ")
			.append("is_deleted=").append(m_is_deleted?1:0)
			.append(" where cms_res_id=").append(m_cms_res_id);

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

	
	/**
	 * TODO Fill in remaining insert fields
	 * 
	 * @return
	 */
	public boolean insertRow() {
		boolean ret = false;

		Cms cms = GetCmsByName(m_name);
		if (cms != null) {
			if (cms.m_is_deleted) {
				cms.m_is_deleted = false;
				cms.m_host = m_host;
				ret = cms.updateRow();
				return (ret);
			}
		}
		
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create resource
			 */
			Resource rx = new Resource(ResourceTypes.RES_TYPE_CMS);
			rx.setConnection(c);
			Long resId = rx.createRow();

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * Create sql
			 */
			StringBuffer insStr = new StringBuffer(GlobalQueries.INS_CMS_IN_LOCAL_SYSTEM);
			insStr.append("(").append(resId).append(", \"")
			.append(m_name).append("\", \"")
			.append(m_host).append("\")");
			
			stmt.execute(insStr.toString());

			// Finally
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
	
	
	/**
	 * @return
	 */
	public boolean deleteRow() {
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
			StringBuffer updStr = new StringBuffer(GlobalQueries.DEL_CMS_IN_LOCAL_SYSTEM);
			updStr.append(m_cms_res_id);

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


	/**
	 * 
	 */
	public int getAlertLevel() {
		return m_alert_level.intValue();
	}
	
	/**
	 * Get the CMS whose name matches the input irrespective of whether
	 * the CMS is in deleted state or not.
	 * @param cn
	 * @return
	 */
	static public Cms GetCmsByName(String cn) {
		Cms ret = null;

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
			StringBuffer qryStr = new StringBuffer(
					"SELECT * FROM canet.cms WHERE name=\"");
			qryStr.append(cn).append("\"");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new Cms(rs);
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

		return (ret);
	}


}

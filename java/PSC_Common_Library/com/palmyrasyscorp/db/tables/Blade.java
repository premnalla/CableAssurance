/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.List;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.common.base.Host;

/**
 * @author Prem
 *
 */
public class Blade extends AbstractDbObject implements Host {
	
	private static Log log = LogFactory.getLog(Blade.class);

	private Integer m_bladeId;
	public Integer getId() { return m_bladeId;	}
	public void setId(int id) { m_bladeId = id; }

	private String m_name;
	public String getName() { return m_name; }
	public void setName(String nm) { m_name = nm; }

	private String m_host;
	public void setHost(String s) { m_host = s; }
	public String getHost() { return (m_host); }
	public String getHostForWS() { return (m_host + ":8080"); }
	public String getHostForWS(String port) {
		String ret;
		if (port != null) {
			ret = m_host + ":" + port;
		} else {
			ret = m_host;
		}
		return (ret); 
	}
	
	private Integer m_ip_address_type;
	public Integer getIpAddressType() { return m_ip_address_type; }
	public short getAddressType() { return m_ip_address_type.shortValue(); }

	private Boolean m_isDeleted;
	public Boolean getIsDeleted() { return m_isDeleted; }
	public void getIsDeleted(boolean id) { m_isDeleted = id; }

	public Blade() {
	}

	public Blade(int id) {
		m_bladeId = id;
		querySelf();
	}

	public Blade(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_bladeId = (Integer) rs.getObject(i++);
			i++;
			m_name = rs.getString(i++);
			m_host = rs.getString(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_isDeleted = (Boolean) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	private void querySelf() {
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
			String qryStr = "SELECT * FROM canet.blade WHERE blade_id=" + getId();

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
				m_host = rs.getString(i++);
				m_ip_address_type = (Integer) rs.getObject(i++);
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

	/**
	 * 
	 * @return
	 */
	public boolean insertRow() {
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
			StringBuffer insStr = new StringBuffer(GlobalQueries.INS_BLADE_IN_LOCAL_SYSTEM);
			insStr.append("(\"").append(m_name).append("\", \"")
				.append(m_host).append("\")");

			// System.out.println(insStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(insStr.toString());

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
			StringBuffer updStr = new StringBuffer(GlobalQueries.DEL_BLADE_IN_LOCAL_SYSTEM);
			updStr.append(m_bladeId);

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
	 * @return
	 */
	public boolean updateRow() {
		boolean ret = true;

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
			StringBuffer updStr = new StringBuffer(GlobalQueries.UPD_BLADE_IN_LOCAL_SYSTEM);
			updStr.append("name=\"").append(m_name).append("\", ")
			.append("ip_address=\"").append(m_host).append("\" ")
			.append("where blade_id=").append(m_bladeId);

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
	 * @param bn
	 * @return
	 */
	static public Blade GetBladeByName(String bn) {
		Blade ret = null;
		
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
			StringBuffer qryStr = new StringBuffer("SELECT * FROM canet.blade WHERE name=\"");
			qryStr.append(bn).append("\" and is_deleted=0");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new Blade(rs);
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
	
	/**
	 * 
	 * @return
	 */
	public static List<Blade> QueryBlades() {
		List<Blade> ret = new LinkedList<Blade>();

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
			StringBuffer qryStr = new StringBuffer("SELECT * FROM canet.blade ");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			while (rs.next()) {
				ret.add(new Blade(rs));
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

	/**
	 * 
	 * @return
	 */
	public List<Cmts> getCmtses()
	{
		List<Cmts> ret = new LinkedList<Cmts>();
		
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
			String qryStr = GlobalQueries.QRY_CMTS_IN_LOCAL_SYSTEM;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			IntegerHolder ih = new IntegerHolder();
			while (rs.next()) {
				ih.value = 0;
				Cmts cmts = new Cmts(rs, ih);
				CurrentCounts cc = new CurrentCounts(rs, ih);
				// System.out.println(cmts.getName());
				cmts.setCurrentCounts(cc);
				ret.add(cmts);
			}
			ih = null;
			
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

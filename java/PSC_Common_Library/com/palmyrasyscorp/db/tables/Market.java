package com.palmyrasyscorp.db.tables;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.common.base.Host;

public class Market extends AbstractDbObject implements Host {

	private static Log log = LogFactory.getLog(Market.class);

	private Integer m_marketId;
	public Integer getId() { return m_marketId;	}
	public void setId(int id) { m_marketId = id; }

	private String m_market_name;
	public String getName() { return m_market_name; }
	public void setName(String nm) { m_market_name = nm; }
	
	private String m_host;
	public String getHost() { return m_host; }
	public void setHost(String s) { m_host = s; }
	
	private Integer m_ip_address_type;
	public Integer getIpAddressType() { return m_ip_address_type; }
	public short getAddressType() { return m_ip_address_type.shortValue(); }

	private Boolean m_isDeleted;
	public Boolean getIsDeleted() { return m_isDeleted; }
	public void getIsDeleted(boolean id) { m_isDeleted = id; }

	public Market() {}
	
	public Market(int id)
	{
		m_marketId = id;
		querySelf();
	}
	
	public Market(ProxyDbResultSet rs)
	{
		int i=1;
		try {
			m_marketId = (Integer) rs.getObject(i++);
			i++;
			m_market_name = rs.getString(i++);
			m_host = rs.getString(i++);
			m_ip_address_type = (Integer) rs.getObject(i++);
			m_isDeleted = (Boolean) rs.getObject(i++);
		}
		catch (Exception e)
		{
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
			String qryStr = "SELECT * FROM canet.market WHERE market_id=" + getId();

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
				i++;
				i++;
				m_market_name = rs.getString(i++);
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
			StringBuffer insStr = new StringBuffer(GlobalQueries.INS_MARKET_IN_LOCAL_SYSTEM);
			insStr.append("(\"").append(m_market_name).append("\", \"")
				.append(m_host).append("\")");

			// System.out.println(qryStr);

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
			StringBuffer updStr = new StringBuffer(GlobalQueries.DEL_MARKET_IN_LOCAL_SYSTEM);
			updStr.append(m_marketId);

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
			StringBuffer updStr = new StringBuffer(GlobalQueries.UPD_MARKET_IN_LOCAL_SYSTEM);
			updStr.append("name=\"").append(m_market_name).append("\", ")
			.append("ip_address=\"").append(m_host).append("\" ")
			.append("where market_id=").append(m_marketId);

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
	
	public List<Blade> getBlades()
	{
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
			String qryStr = "SELECT * FROM canet.blade";

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				Blade b = new Blade(rs);
				ret.add(b);
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
				// System.out.println(c.getName());
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

	public List<Cms> getCmses()
	{
		List<Cms> ret = new LinkedList<Cms>();
		
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
			String qryStr = GlobalQueries.QRY_CMS_IN_LOCAL_SYSTEM;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				Cms cms = new Cms(rs);
				ret.add(cms);
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


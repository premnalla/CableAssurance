/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.LinkedList;
import java.util.List;

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
public class OAAccess extends AbstractDbObject {

	private static Log log = LogFactory.getLog(OAAccess.class);
	
	private Integer m_id;

	public Integer getId() {
		return m_id;
	}

	private String m_accessType;

	public String getAccess() {
		return m_accessType;
	}

	/**
	 * 
	 *
	 */
	private boolean m_isReconciled = false;
	public boolean isReconciled() { return m_isReconciled; }
	public void setReconciled() { m_isReconciled = true; }
	
	public OAAccess() {
		super();
	}

	public OAAccess(String at) {
		super();
		m_accessType = at;
	}

	public OAAccess(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_accessType = rs.getString(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @param rs
	 * @param ih
	 */
	public OAAccess(ProxyDbResultSet rs, IntegerHolder ih) {
		int i = ih.value;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_accessType = rs.getString(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}
	
	public static List<OAAccess> GetList() {
		List<OAAccess> ret = new LinkedList<OAAccess>();

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
			String qryStr = GlobalQueries.QRY_USER_ACCESS_TYPES;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				OAAccess u = new OAAccess(rs);
				ret.add(u);
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
	public boolean insert() {
		boolean ret = get();

		if (ret) {
			return (ret);
		}

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
			StringBuffer qryStr = new StringBuffer(
					GlobalQueries.INS_USER_ACCESS_TYPE);
			qryStr.append("\"").append(m_accessType).append("\")");

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

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
	public boolean get() {
		boolean ret = false;

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
					GlobalQueries.QRY_USER_ACCESS_TYPES_BY_NAME);
			qryStr.append("\"").append(m_accessType).append("\"");

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				m_id = (Integer) rs.getObject(1);
				ret = true;
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

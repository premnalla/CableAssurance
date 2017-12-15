/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.HashMap;
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
public class OAObject extends AbstractDbObject {

	private static Log log = LogFactory.getLog(OAObject.class);
	
	private Integer m_id;
	public Integer getId() {
		return m_id;
	}
	
	private String m_appDomain;
	public String getAppDomain() {
		return m_appDomain;
	}
	
	private HashMap<String, OAAccess> m_aMap = new HashMap<String, OAAccess>();
	public HashMap<String, OAAccess> getAccessMap() { return m_aMap; }
	
	
	public OAObject() {
		super();
	}
	
	public OAObject(String ad) {
		super();
		m_appDomain = ad;
	}
	
	public OAObject(ProxyDbResultSet rs) {
		int i=1;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_appDomain = rs.getString(i++);
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
	public OAObject(ProxyDbResultSet rs, IntegerHolder ih) {
		int i = ih.value;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_appDomain = rs.getString(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}
	
	public static List<OAObject> GetList() {
		List<OAObject> ret = new LinkedList<OAObject>();
		
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
			String qryStr = GlobalQueries.QRY_APP_DOMAIN_TYPES;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				OAObject u = new OAObject(rs);
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.INS_APP_DOMAIN);
			qryStr.append("\"").append(m_appDomain).append("\")");

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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_APP_DOMAIN_TYPE_BY_NAME);
			qryStr.append("\"").append(m_appDomain).append("\"");

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

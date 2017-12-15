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
public class Role extends AbstractDbObject {

	private static Log log = LogFactory.getLog(Role.class);
	
	private Integer m_id;
	public Integer getId() {
		return m_id;
	}
	
	private String m_roleName;
	public String getRoleName() {
		return m_roleName;
	}
	public void setRoleName(String rn) 
	{
		m_roleName = rn; 
	}
	
	private HashMap<String, OAObject> m_oMap = new HashMap<String, OAObject>();
	public HashMap<String, OAObject> getObjectMap() { return m_oMap; }
	
	/**
	 * 
	 *
	 */
	public Role() {
		super();
	}
	
	/**
	 * 
	 * @param rn
	 */
	public Role(String rn) {
		super();
		m_roleName = rn;
		// querySelf();
	}
	
	/**
	 * 
	 * @param roleId
	 */
	public Role(Integer roleId) {
		super();
		m_id = roleId;
		// querySelf();
	}
	
	public Role(ProxyDbResultSet rs) {
		super();
		int i=1;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_roleName = rs.getString(i++);
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
	public Role(ProxyDbResultSet rs, IntegerHolder ih) {
		super();
		int i = ih.value;
		try {
			m_id = (Integer) rs.getObject(i++);
			m_roleName = rs.getString(i++);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error(null, e);	
		}
		ih.value = i;
	}
	
	public static List<Role> GetList() {
		List<Role> ret = new LinkedList<Role>();
		
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
			String qryStr = GlobalQueries.QRY_ROLES;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			
			while (rs.next()) {
				Role u = new Role(rs);
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.INS_ROLE);
			qryStr.append("\"").append(m_roleName).append("\")");

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
			StringBuffer qryStr;
			if (m_roleName != null) {
				qryStr = new StringBuffer(GlobalQueries.QRY_ROLE_BY_NAME);
				qryStr.append("\"").append(m_roleName).append("\"");
			} else if (m_id != null) {
				qryStr = new StringBuffer(GlobalQueries.QRY_ROLE_BY_ID).append(m_id);				
			} else {
				return (ret);
			}

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
				int i=1;
				m_id = (Integer) rs.getObject(i++);
				m_roleName = rs.getString(i++);
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

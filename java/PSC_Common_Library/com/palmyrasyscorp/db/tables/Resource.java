/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;

/**
 * Usage: ... Resource r = new Resource(ResourceTypes.XXX);
 * r.setConnection(...); Long resId = r.createRow(); if (resId == null) { //
 * error } ...
 * 
 * @author Prem
 * 
 */
public class Resource extends AbstractDbObject {

	private static Log log = LogFactory.getLog(Resource.class);

	private Integer m_resType;

	public Integer getResType() {
		return m_resType;
	}

	private Long m_resId;

	public Long getResId() {
		return m_resId;
	}

	/**
	 * 
	 */
	protected Resource() {
		super();
	}

	/**
	 * @param conn
	 */
	public Resource(short resType) {
		super();
		m_resType = new Integer(resType);
	}

	/**
	 * 
	 * @param resId
	 */
	public Resource(long resId) {
		m_resId = new Long(resId);
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public Resource(ProxyDbResultSet rs) {
		super();
		int i = 1;
		try {
			m_resId = (Long) rs.getObject(i++);
			m_resType = (Integer) rs.getObject(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
	}

	public Long createRow() {
		Long ret = null;

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {

			/*
			 * Get connection
			 */
			if ((c = getConnection()) == null) {
				c = CADbConnectionPool.getInstance().getConnection();
			}

			/*
			 * Create statement
			 */
			stmt = c.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(
					"insert into xresource (res_type) values(");
			qryStr.append(m_resType).append(")");

			// System.out.println(qryStr);

			stmt.executeUpdate(qryStr.toString(),
					Statement.RETURN_GENERATED_KEYS);

			rs = stmt.getGeneratedKeys();

			int i = 1;
			if (rs.next()) {
				ret = (Long) rs.getObject(i++);
			}

			m_resId = ret;

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
	 * 
	 */
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
			StringBuffer qryStr = new StringBuffer(
					"select * from canet.xresource where res_id=");
			qryStr.append(m_resId);

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
				int i = 1;
				try {
					i++; // m_resId = (Long) rs.getObject(i++);
					m_resType = (Integer) rs.getObject(i++);
					
					// System.out.println("Got Resource");
				} catch (Exception e) {
					// e.printStackTrace();
					log.error(null, e);
				}
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

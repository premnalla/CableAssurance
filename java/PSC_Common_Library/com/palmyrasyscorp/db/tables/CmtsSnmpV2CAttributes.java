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
public class CmtsSnmpV2CAttributes extends SnmpV2CAttributes {

	private static Log log = LogFactory.getLog(CmtsSnmpV2CAttributes.class);

	/**
	 * 
	 * 
	 */
	public CmtsSnmpV2CAttributes() {
		super();
	}

	/**
	 * 
	 * 
	 */
	public CmtsSnmpV2CAttributes(long cmtsResId) {
		super(cmtsResId);
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 * @param ih
	 */
	public CmtsSnmpV2CAttributes(ProxyDbResultSet rs, IntegerHolder ih) {
		super(rs, ih);
	}

	/**
	 * 
	 * @param rs
	 */
	public CmtsSnmpV2CAttributes(ProxyDbResultSet rs) {
		super(rs);
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
			StringBuffer updStr = new StringBuffer(
					"update canet.cmts_v2c_attributes set ");
			updStr.append("read_community=\"").append(getReadCommunity())
					.append("\", ").append("write_community=\"").append(
							getWriteCommunity()).append("\" ").append(
							"where cmts_res_id=").append(getCmtsResId());

			// System.out.println(qryStr);

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
			StringBuffer updStr = new StringBuffer(
					"insert into canet.cmts_v2c_attributes(cmts_res_id,read_community,write_community) values(");
			updStr.append(getCmtsResId()).append("\"").append(
					getReadCommunity()).append("\",\"").append(
					getWriteCommunity()).append("\")");

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
					GlobalQueries.QRY_CMTS_SNMP_V2C_ATTR);
			qryStr.append(getCmtsResId());

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
				i++; // skip id
				i++; // skip cmts res id
				m_read_community = rs.getString(i++);
				m_write_community = rs.getString(i++);
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

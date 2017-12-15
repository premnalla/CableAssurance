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
public class TopoMtaLookup extends AbstractTopoLookup {

	private static Log log = LogFactory.getLog(TopoMtaLookup.class);
	
	/**
	 * 
	 * 
	 */
	public TopoMtaLookup() {
		super();
	}

	public TopoMtaLookup(String mac) {
		super();
		querySelf();
	}

	/**
	 * 
	 * @param rs
	 */
	public TopoMtaLookup(ProxyDbResultSet rs) {
		super(rs);
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
			String qryStr = "SELECT * FROM canet.topo_lookup_mta lc WHERE lc.mac_address="
				+ getMac();

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
				setId((Long) rs.getObject(i++));
				setResId((Long) rs.getObject(i++));
				i++; // setMac(rs.getString(i++));
				setContainerId((Integer) rs.getObject(i++));
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

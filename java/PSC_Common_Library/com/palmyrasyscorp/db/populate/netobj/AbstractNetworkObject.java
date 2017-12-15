/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.sql.ResultSet;
import java.sql.Statement;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public abstract class AbstractNetworkObject {

	private DbConnection m_conn = null;
	
	/**
	 * 
	 */
	protected AbstractNetworkObject() {
		super();
	}

	/**
	 * 
	 * @param conn
	 */
	protected AbstractNetworkObject(DbConnection conn) {
		super();
		m_conn = conn;
	}

	/**
	 * 
	 * @return
	 */
	protected DbConnection getConnection() {
		return (m_conn);
	}

	/**
	 * 
	 * @param resType
	 * @return
	 */
	protected Long createResource(short resType) {
		Long ret = null;

		Resource res = new Resource(m_conn);
		ret = res.createResource(resType);

		res = null;
		
		return (ret);
	}
	
}

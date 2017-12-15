/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.*;
import com.palmyrasyscorp.common.base.*;
// import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbConnection;

/**
 * @author Prem
 *
 */
public abstract class AbstractDbObject extends AbstractBase {

	/**
	 * 
	 */
	private LinkedList m_children = new LinkedList();

	/**
	 * 
	 */
	private ProxyDbConnection m_dbConn;
	
	/**
	 * 
	 * @param o
	 */
	public void addChild(Object o) {
		m_children.add(o);
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList getListOfChildren() {
		return (m_children);
	}
	
	/**
	 * 
	 * @param c
	 */
	protected void setConnection(ProxyDbConnection c) {
		m_dbConn = c;
	}
	
	/**
	 * 
	 * @return
	 */
	protected ProxyDbConnection getConnection() {
		return (m_dbConn);
	}
	
}

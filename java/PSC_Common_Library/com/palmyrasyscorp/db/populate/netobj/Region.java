/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class Region extends AbstractNetworkObject {

	private String m_regionName = null;
	private LinkedList m_marketNames = null;
	
	/**
	 * 
	 */
	protected Region() {
		super();
	}

	public Region(DbConnection conn, String regionName, LinkedList marketNames) {
		super(conn);
		m_regionName = regionName;
		m_marketNames = marketNames;
	}
	
	public void create() {
		createChildren();
	}
	
	private Long createSelf() {
		
		Long resId = createResource(Resource.RES_TYPE_NULL);
		
		if (resId == null) {
			// System.out.println("Unable to create resource")
			return (resId);
		}

		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into region (name) values(")
			.append("'").append(m_regionName).append("')");
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
			qryStr = null;
		}

		return (resId);
	}
	
	private void createChildren() {
		
		for (int i=0; i<m_marketNames.size(); i++) {
			Market mkt = new Market(getConnection(), (String)m_marketNames.get(i));
			mkt.create();
			mkt = null;
		}
		
	}

}


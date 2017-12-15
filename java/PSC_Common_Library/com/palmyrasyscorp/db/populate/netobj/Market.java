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
public class Market extends AbstractNetworkObject {

	private String m_marketName = null;

	/**
	 * 
	 */
	protected Market() {
		super();
	}

	/**
	 * @param conn
	 */
	public Market(DbConnection conn, String marketName) {
		super(conn);
		m_marketName = marketName;
	}

	public void create() {
		createSelf();
		createChildren();
	}
	
	private void createSelf() {
		
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer qryStr = null;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			qryStr = new StringBuffer("insert into canet.local_system (system_type,system_name) values(3,")
			.append("'").append(m_marketName).append("')");
			
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

	}
	
	private void createChildren() {
//		for (int i=1; i<=GlobalConstants.CMTS_PER_BLADE; i++) {
//			Cmts c = new Cmts(getConnection(), "CMTS-" + i);
//			c.create();
//			c = null;
//		}
	}

}

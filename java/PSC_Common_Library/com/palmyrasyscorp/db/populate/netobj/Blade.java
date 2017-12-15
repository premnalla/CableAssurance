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
public class Blade extends AbstractNetworkObject {

	private String m_bladeName = null;
	
	/**
	 * 
	 */
	protected Blade() {
		super();
	}

	/**
	 * @param conn
	 */
	public Blade(DbConnection conn, String bladeName) {
		super(conn);
		m_bladeName = bladeName;
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
			
			qryStr = new StringBuffer("insert into canet.local_system (system_type,system_name) values(4,")
			.append("'").append(m_bladeName).append("')");
			
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
	
	
	/**
	 * Create CMTS and it's children
	 *
	 */
	private void createChildren() {
		
		ProgramProperties props = ProgramProperties.getInstance();
		String cmtsNamePrefix = props.getValue("cmts.name.previx");

		for (int i=1; i<=GlobalConstants.CMTS_PER_BLADE; i++) {
			StringBuffer cmtsN = new StringBuffer(cmtsNamePrefix);
			cmtsN.append("-").append(i);
			Cmts c = new Cmts(getConnection(), cmtsN.toString());
			c.create();
			c = null;
		}
		
	}

}

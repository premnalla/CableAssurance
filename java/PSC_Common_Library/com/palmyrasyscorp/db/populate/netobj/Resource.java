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
public class Resource extends AbstractNetworkObject {

	static public final short RES_TYPE_NULL = 0;
//	static public final short RES_TYPE_ENTERPRISE = 1;
//	static public final short RES_TYPE_REGION = 2;
//	static public final short RES_TYPE_MARKET = 3;
//	static public final short RES_TYPE_BLADE = 4;
	static public final short RES_TYPE_CMTS = 1;
	static public final short RES_TYPE_CHANNEL = 2;
	static public final short RES_TYPE_HFC = 3;
	static public final short RES_TYPE_CABLE_MODEM = 4;
	static public final short RES_TYPE_EMTA = 5;
	static public final short RES_TYPE_CMS = 6;
	
	/**
	 * 
	 */
	public Resource() {
		super();
	}

	/**
	 * @param conn
	 */
	public Resource(DbConnection conn) {
		super(conn);
	}

	public Long createResource(short resType) {
		Long ret = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = getConnection().getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			String qryStr = "insert into xresource (res_type) values(" + resType + ")";
			
			System.out.println(qryStr);
			
			stmt.executeUpdate(qryStr, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.getGeneratedKeys();
			
			int i = 1;
			if (rs.next()) {
				ret = (Long) rs.getObject(i++);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}
}

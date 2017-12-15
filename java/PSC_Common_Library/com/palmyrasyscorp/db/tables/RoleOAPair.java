/**
 * OBSOLETED
 */
package com.palmyrasyscorp.db.tables;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.LinkedList;
//import java.util.List;
//
//import com.palmyrasyscorp.db.common.DbConnectionPool;
//import com.palmyrasyscorp.db.common.DbUtils;

/**
 * @author Prem
 *
 */
public class RoleOAPair extends AbstractDbObject {

//	private String m_roleName;
//	public String getRoleName() {
//		return m_roleName;
//	}
//	
//	private String m_appDomain;
//	public String getAppDomain() {
//		return m_appDomain;
//	}
//	
//	private String m_accessType;
//	public String getAccess() {
//		return m_accessType;
//	}
//	
//	public RoleOAPair() {
//		super();
//	}
//	
//	public RoleOAPair(String role, String obj, String access) {
//		super();
//		m_roleName = role;
//		m_appDomain = obj;
//		m_accessType = access;
//	}
//	
//	public RoleOAPair(ResultSet rs) {
//		int i=1;
//		try {
//			m_roleName = rs.getString(i++);
//			m_appDomain = rs.getString(i++);
//			m_accessType = rs.getString(i++);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}
//	
//	public static List<RoleOAPair> GetList() {
//		List<RoleOAPair> ret = new LinkedList<RoleOAPair>();
//		
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_ROLE_OA_PAIRS;
//			rs = stmt.executeQuery(qryStr);
//			
//			while (rs.next()) {
//				RoleOAPair u = new RoleOAPair(rs);
//				ret.add(u);
//			}
//			
//		} catch (Exception e) {	
//			e.printStackTrace();
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}  		
//		
//		return (ret);
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public boolean insert() {
//		boolean ret = get();
//
//		if (ret) {
//			return (ret);
//		}
//
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			Role r = new Role(m_roleName);
//			if (!(ret=r.get())) {
//				return (ret);
//			}
//			OAObject o = new OAObject(m_appDomain);
//			if(!(ret=o.get())) {
//				return (ret);
//			}
//			OAAccess a = new OAAccess(m_accessType);
//			if (!(ret=a.get())) {
//				return (ret);
//			}
//			stmt = conn.createStatement();
//			StringBuffer qryStr = new StringBuffer(
//					GlobalQueries.INS_ROLE_OA_PAIR);
//			qryStr.append(r.getId()).append(",").append(o.getId()).append(",")
//			.append(a.getId()).append(")");
//			ret = stmt.execute(qryStr.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public boolean get() {
//		boolean ret = false;
//
//		Connection conn = DbConnectionPool.getInstance().getConnection()
//				.getConnection();
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			StringBuffer qryStr = new StringBuffer(
//					GlobalQueries.QRY_ROLE_OA_PAIRS_BY_VALUES);
//			qryStr.append("r.role_name=\"").append(m_roleName).append("\" and ")
//			.append("o.sys_object_name=\"").append(m_appDomain).append("\" and ")
//			.append("a.sys_access_name=\"").append(m_accessType).append("\"");
//			rs = stmt.executeQuery(qryStr.toString());
//
//			if (rs.next()) {
//				ret = true;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}
//
//		return (ret);
//	}

}

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
public class UserRole extends AbstractDbObject {

//	private String m_userId;
//	public String getUserId() {
//		return m_userId;
//	}
//	
//	private String m_roleName;
//	public String getRoleName() {
//		return m_roleName;
//	}
//	
//	public UserRole() {
//		super();
//	}
//	
//	public UserRole(String ui, String rn) {
//		super();
//		m_userId = ui;
//		m_roleName = rn;
//	}
//	
//	public UserRole(ResultSet rs) {
//		int i=1;
//		try {
//			m_userId = rs.getString(i++);
//			m_roleName = rs.getString(i++);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}
//	
//	public static List<UserRole> GetList() {
//		List<UserRole> ret = new LinkedList<UserRole>();
//		
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_USER_ROLES;
//			rs = stmt.executeQuery(qryStr);
//			
//			while (rs.next()) {
//				UserRole u = new UserRole(rs);
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
//			User u = new User(m_userId);
//			if (u.getAppUserId()==null) {
//				return (ret);
//			}
//			Role r = new Role(m_roleName);
//			if (!(ret=r.get())) {
//				return (ret);
//			}
//			stmt = conn.createStatement();
//			StringBuffer qryStr = new StringBuffer(
//					// GlobalQueries.INS_USER_ROLES);
//					"foo");
//			qryStr.append(u.getAppUserId()).append(",").append(r.getId()).append(")");
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
//					GlobalQueries.QRY_USER_ROLES_BY_VALUES);
//			qryStr.append("u.user_id=\"").append(m_userId).append("\" and ")
//			.append("r.role_name=\"").append(m_roleName).append("\"");
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

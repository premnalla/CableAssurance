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
public class UserOAPair extends AbstractDbObject {

//	private String m_userId;
//	public String getUserId() {
//		return m_userId;
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
//	public UserOAPair() {
//		super();
//	}
//	
//	public UserOAPair(String ui, String ad, String at) {
//		super();
//		m_userId = ui;
//		m_appDomain = ad;
//		m_accessType = at;
//	}
//	
//	public UserOAPair(ResultSet rs) {
//		int i=1;
//		try {
//			m_userId = rs.getString(i++);
//			m_appDomain = rs.getString(i++);
//			m_accessType = rs.getString(i++);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}
//	
//	public static List<UserOAPair> GetList() {
//		List<UserOAPair> ret = new LinkedList<UserOAPair>();
//		
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			// String qryStr = GlobalQueries.QRY_USER_OA_PAIRS;
//			String qryStr = "foo";
//			rs = stmt.executeQuery(qryStr);
//			
//			while (rs.next()) {
//				UserOAPair u = new UserOAPair(rs);
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
//					// GlobalQueries.INS_USER_OA_PAIR);
//					"foo");
//			qryStr.append(u.getAppUserId()).append(",").append(o.getId()).append(",")
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
//					// GlobalQueries.QRY_USER_OA_PAIRS_BY_VALUES);
//					"foo");
//			qryStr.append("u.user_id=\"").append(m_userId).append("\" and ")
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

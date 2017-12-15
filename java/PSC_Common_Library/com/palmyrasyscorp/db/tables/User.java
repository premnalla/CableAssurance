/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class User extends AbstractDbObject {
	
	/* data members */
	
	private static Log log = LogFactory.getLog(User.class);
	
	private Long   m_appUserId;
	public Long getAppUserId() { return m_appUserId; }
	
	private String m_firstName;
	public String getFirstName() { return m_firstName; }
	public void setFirstName(String s) { m_firstName = s; }
	
	private String m_lastName;
	public String getLastName() { return m_lastName; }
	public void setLastName(String s) { m_lastName = s; }
	
	private String m_middleInitial;
	public String getMiddleInitial() { return m_middleInitial; }
	public void setMiddleInitial(String s) { m_middleInitial = s; }
	
	private String m_userId;
	public String getUserId() { return m_userId; }
	public void setUserId(String s) { m_userId = s; }
	
	private String m_userPw;
	public String getUserPw() { return m_userPw; }
	public void setUserPw(String s) { m_userPw = s; }
	
	private String m_userLocation;
	public String getUserLocation() { return m_userLocation; }
	public void setUserLocation(String s) { m_userLocation = s; }
	
	private Integer m_roleId;
	public Integer getRoleId() { return m_roleId; }
	public void setRoleId(Integer ri) { m_roleId = ri; }
	
	private Boolean m_userActive;
	public Boolean getIsUserActive() { return m_userActive; }
	public void setIsUserActive(short s) {
		if (s == 0) {
			m_userActive = false;			
		} else {
			m_userActive = true;
		}
	}
	
	private String m_roleName;
	public String getRoleName() { return m_roleName; }
	public void setRoleName(String rn) { m_roleName = rn; }
	
	/* list of AcessPrivilege's */
	private LinkedList m_accessPriveleges = new LinkedList();
//	private LinkedList m_oaPairs = new LinkedList();
	
	/**
	 * 
	 *
	 */
	public User() {
		super();
		// querySelf();
	}
	
	/**
	 * 
	 *
	 */
	public User(String id) {
		super();
		m_userId = id;
		querySelf();
	}
	
	/**
	 * 
	 *
	 */
	public User(Long id) {
		super();
		m_appUserId = id;
		querySelf();
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return (m_userId.hashCode());
	}
	
	/**
	 * 
	 */
	public boolean equals(Object o) {
		boolean ret = false;
		
		try {
			User ou = (User) o;
			ret = m_userId.equals(ou.getUserId());
		} catch (Exception e) {
			log.error(null, e);	
		}
		
		return (ret);
	}

	/**
	 * 
	 * @param rs
	 */
	public User(ProxyDbResultSet rs) {
		int i=1;
		try {
			/*
			 * From caappuser.app_user table
			 */
			m_appUserId = (Long) rs.getObject(i++);
			m_firstName = rs.getString(i++);
			m_lastName = rs.getString(i++);
			m_middleInitial = rs.getString(i++);
			m_userId = rs.getString(i++);
			m_userPw = rs.getString(i++);
			m_userLocation = rs.getString(i++);
			m_roleId = (Integer) rs.getObject(i++);
			m_userActive = (Boolean) rs.getObject(i++);
			
			/*
			 * From caappuser.user_role table
			 */
			m_roleName = rs.getString(i++);
		} catch (Exception e) {
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @return
	 */
	public static List GetUsers() {
		LinkedList<User> ret = new LinkedList<User>();
		
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
			String qryStr = GlobalQueries.QRY_USERS;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr);

			while (rs.next()) {
				User u = new User(rs);
				ret.add(u);
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

		return (ret);
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
			StringBuffer qryStr;
			if (m_userId != null) {
				qryStr = new StringBuffer(GlobalQueries.QRY_USER_BY_USERID);
				qryStr.append("\"").append(m_userId).append("\"");
			} else if (m_appUserId != null) {
				qryStr = new StringBuffer(GlobalQueries.QRY_USER);
				qryStr.append(m_appUserId);
			} else {
				return;
			}

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i=1;
				/*
				 * From caappuser.app_user table
				 */
				m_appUserId = (Long) rs.getObject(i++);
				m_firstName = rs.getString(i++);
				m_lastName = rs.getString(i++);
				m_middleInitial = rs.getString(i++);
				m_userId = rs.getString(i++);
				m_userPw = rs.getString(i++);
				m_userLocation = rs.getString(i++);
				m_roleId = (Integer) rs.getObject(i++);
				m_userActive = (Boolean) rs.getObject(i++);
				
				/*
				 * From caappuser.user_role table
				 */
				m_roleName = rs.getString(i++);
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
	
	/**
	 * 
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public static User GetUserByUserPw(String userId, String userPw) {
		User ret = null;
		
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
			String qryStr = new String(GlobalQueries.QRY_GET_USER_BY_USER_PW);
			qryStr = qryStr.replace("%a%", userId);
			qryStr = qryStr.replace("%b%", userPw);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new User(rs);
//				ret.fillAccessPrivileges();
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

		return (ret);
	}
	
//	/**
//	 * 
//	 *
//	 */
//	private void fillAccessPrivileges() {
//		fillOAPairsByRole();
//		createAccessPrivileges();
//	}
	
//	/**
//	 * 
//	 *
//	 */
//	private void createAccessPrivileges() {
//		// private LinkedList m_accessPriveleges = new LinkedList();
//		// private LinkedList m_oaPairs = new LinkedList();
//		for (int i=0; i<m_oaPairs.size();) {
//			boolean c = false;
//			OAPair oap = (OAPair) m_oaPairs.get(i);
//			// look ahead
//			int next = i + 1;
//			if (next < m_oaPairs.size()) {
//				OAPair noap = (OAPair) m_oaPairs.get(next);
//				if (noap.getObject().equals(oap.getObject())) {
//					c = true;
//					ReadWriteAccess rw = new ReadWriteAccess(oap.getObject());
//					m_accessPriveleges.add(rw);
//				}
//			}
//			if (!c) {
//				ReadOnlyAccess ro = new ReadOnlyAccess(oap.getObject());
//				m_accessPriveleges.add(ro);
//				i++;
//			} else {
//				i += 2;
//			}
//		}
//	}
	
//	/**
//	 * 
//	 * @return
//	 */
//	private String getUserRole() {
//		String ret = null;
//		
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_GET_USER_ROLE_BY_USERID + "'" + m_userId + "'";
//			rs = stmt.executeQuery(qryStr);
//			
//			if (rs.next()) {
//				ret = rs.getString(1);
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
		
//	/**
//	 * 
//	 *
//	 */
//	private boolean fillOAPairsByRole() {
//		boolean ret = false;
//		
//		String role = getUserRole();
//		if (role == null) {
//			return (ret);
//		}
//		
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_GET_OAPAIRS_BY_ROLE + "'" + role + "'";
//			rs = stmt.executeQuery(qryStr);
//			
//			while (rs.next()) {
//				int i = 1;
//				String oN = rs.getString(i++);
//				String aN = rs.getString(i++);
//				// OAPair oa = new OAPair(rs);
//				if (oN != null && aN != null) {
//					OAPair oa = new OAPair(rs);
//					m_oaPairs.add(oa);
//				}
//			}
//		} catch (Exception e) {	
//			e.printStackTrace();
//		} finally {
//			DbUtils.ReleaseResultSet(rs);
//			DbUtils.ReleaseStatement(stmt);
//			rs = null;
//			stmt = null;
//		}  		
//		return (ret);
//	}
	
//	/**
//	 * 
//	 * @return
//	 */
//	private boolean fillOAPairsByUser() {
//		boolean ret = false;
//
//		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
//		
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = conn.createStatement();
//			String qryStr = GlobalQueries.QRY_GET_OAPAIRS_BY_USERID + "'" + m_userId + "'";
//			rs = stmt.executeQuery(qryStr);
//			
//			while (rs.next()) {
//				int i = 1;
//				String oN = rs.getString(i++);
//				String aN = rs.getString(i++);
//				// OAPair oa = new OAPair(rs);
//				if (oN != null && aN != null) {
//					OAPair oa = new OAPair(rs);
//					m_oaPairs.add(oa);
//				}
//			}
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
	
	
	/**
	 * 
	 * @param objectName
	 * @return
	 */
	public AccessPrivilegeBase getAccessPrivilege(String objectName) {
		AccessPrivilegeBase ret = null;
		
		ReadOnlyAccess ro = new ReadOnlyAccess(objectName);
		int i = m_accessPriveleges.indexOf(ro);
		if (i >= 0) {
			ret = (AccessPrivilegeBase) m_accessPriveleges.get(i);
		}

		return (ret);
	}
	
	/**
	 * 
	 *
	 */
	public boolean insertRow() {
		boolean ret = false;
		
		User u = GetUserByUserId(m_userId);
		if (u != null) {
			// set fields
			u.m_firstName = m_firstName;
			u.m_middleInitial = m_middleInitial;
			u.m_lastName = m_lastName;
			u.m_userLocation = m_userLocation;
			u.m_userActive = m_userActive;
			u.m_userPw = m_userPw;
			u.m_roleId = m_roleId;
			// u.m_roleName = m_roleName;
			ret = u.updateRow();
			return (ret);
		}
		
		Role r = new Role(m_roleName);
		if (!r.get()) {
			return (ret);
		}
		
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer insStr = new StringBuffer("insert into caappuser.app_user");
			insStr.append("(first_name,last_name,middle_initial,user_id,user_pw,user_location,role_id,is_active) values(\"");
			insStr.append(m_firstName).append("\",\"").append(m_lastName).append("\",\"")
			.append(m_middleInitial).append("\",\"").append(m_userId).append("\",PASSWORD(\"").append(m_userPw)
			.append("\"),\"").append(m_userLocation).append("\",").append(r.getId()).append(",");
			
			if (m_userActive != null) {
				if (m_userActive.booleanValue()) {
					insStr.append("1");
				} else {
					insStr.append("0");
				}
			} else {
				insStr.append("0");				
			}
						
			insStr.append(")");
			// System.out.println(insStr);
			
			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(insStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}
	
	/**
	 * 
	 *
	 */
	public boolean updateRow() {
		boolean ret = false;
		
		/*
		 * Get the record from DB
		 */
		User u = new User(m_userId);
		
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer insStr = new StringBuffer("update caappuser.app_user set ");
			insStr.append("first_name=\"").append(m_firstName).append("\",")
			.append("last_name=\"").append(m_lastName).append("\",")
			.append("user_location=\"").append(m_userLocation).append("\"");
			
			if (m_userPw != null && m_userPw.length() > 0 && 
					!u.getUserPw().equals(m_userPw)) {
				insStr.append(",user_pw=PASSWORD(\"").append(m_userPw).append("\")");
			}
			
			if (m_middleInitial != null && !u.getMiddleInitial().equals(m_middleInitial)) {
				insStr.append(",middle_initial=\"").append(m_middleInitial).append("\"");
			}
			
			if (m_roleName != null) {
				Role r = new Role(m_roleName);
				if (r.get()) {
					Integer ri = u.getRoleId();
					if (ri == null || !ri.equals(r.getId())) {
						insStr.append(",role_id=").append(r.getId());
					}
				}				
			}
			
			// System.out.println("m_userActive="+m_userActive+", db.m_userActive="+u.getIsUserActive());
			
			if (m_userActive != null && !u.getIsUserActive().equals(m_userActive)) {
				insStr.append(",is_active=").append(m_userActive.booleanValue()==true?1:0);
			}
			
			insStr.append(" where user_id=\"").append(m_userId).append("\";");
			// System.out.println(insStr);
			
			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(insStr.toString());

			ret = true;
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

		return (ret);
	}
	

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public static User GetUserByUserId(String userId) {
		User ret = null;
		
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_USER_BY_USERID);
			qryStr.append("\"").append(userId).append("\"");

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = new User(rs);
//				ret.fillAccessPrivileges();
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

		return (ret);
	}
}

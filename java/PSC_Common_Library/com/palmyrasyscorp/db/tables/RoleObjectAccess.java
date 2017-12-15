/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 * 
 */
public class RoleObjectAccess extends AbstractDbObject {

	private static Log log = LogFactory.getLog(RoleObjectAccess.class);

	private Integer m_roleId;

	public Integer getRoleId() {
		return m_roleId;
	}

	private String m_roleName;

	public String getRoleName() {
		return m_roleName;
	}

	private Integer m_appDomainId;

	public Integer getAppDomainId() {
		return m_appDomainId;
	}

	private String m_appDomain;

	public String getAppDomain() {
		return m_appDomain;
	}

	private Integer m_accessId;

	public Integer getAccessId() {
		return m_accessId;
	}

	private String m_accessType;

	public String getAccess() {
		return m_accessType;
	}

	/**
	 * 
	 * 
	 */
	public RoleObjectAccess() {
		super();
	}

	/**
	 * 
	 * @param role
	 * @param obj
	 * @param access
	 */
	public RoleObjectAccess(String role, String obj, String access) {
		super();
		m_roleName = role;
		m_appDomain = obj;
		m_accessType = access;
	}

	/**
	 * 
	 * @param roleId
	 * @param objId
	 * @param accessId
	 */
	public RoleObjectAccess(Integer roleId, Integer domainId, Integer accessId) {
		super();
		m_roleId = roleId;
		m_appDomainId = domainId;
		m_accessId = accessId;
	}

	public RoleObjectAccess(ProxyDbResultSet rs) {
		int i = 1;
		try {
			m_roleId = (Integer) rs.getObject(i++);
			m_roleName = rs.getString(i++);
			m_appDomainId = (Integer) rs.getObject(i++);
			m_appDomain = rs.getString(i++);
			m_accessId = (Integer) rs.getObject(i++);
			m_accessType = rs.getString(i++);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	public static HashMap<String, Role> Get(Integer roleId) {
		HashMap<String, Role> map = new HashMap<String, Role>();

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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_ROLE_OA);
			if (roleId != null) {
				qryStr.append("where rm.role_id=").append(roleId);
			}
			qryStr.append(GlobalQueries.QRY_ROLE_OA_POSTFIX);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());
			IntegerHolder ih = new IntegerHolder();
			while (rs.next()) {
				ih.value = 1;
				try {
					Role r = new Role(rs, ih);
					OAObject o = new OAObject(rs, ih);
					OAAccess a = new OAAccess(rs, ih);

					if (!map.containsKey(r.getRoleName())) {
						map.put(r.getRoleName(), r);
					} else {
						r = map.get(r.getRoleName());
					}

					if (o.getAppDomain() == null || a.getAccess() == null) {
						continue;
					}

					HashMap<String, OAObject> oMap = r.getObjectMap();
					if (!oMap.containsKey(o.getAppDomain())) {
						oMap.put(o.getAppDomain(), o);
					} else {
						o = oMap.get(o.getAppDomain());
					}

					HashMap<String, OAAccess> aMap = o.getAccessMap();
					if (!aMap.containsKey(a.getAccess())) {
						aMap.put(a.getAccess(), a);
					} else {
						a = aMap.get(a.getAccess());
					}

				} catch (Exception e) {
					// e.printStackTrace();
					log.error(null, e);	
				}

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

		return (map);
	}

	/**
	 * 
	 * @return
	 */
	public boolean insert() {
		boolean ret = get();

		if (ret) {
			return (ret);
		}

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;

		try {
			Integer roleId = null;
			Integer domId = null;
			Integer accessId = null;

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(GlobalQueries.INS_ROLE_OA);
			if (m_roleName != null && m_appDomain != null
					&& m_accessType != null) {
				Role r = new Role(m_roleName);
				if (!(ret = r.get())) {
					return (ret);
				}
				roleId = r.getId();

				OAObject o = new OAObject(m_appDomain);
				if (!(ret = o.get())) {
					return (ret);
				}
				domId = o.getId();

				OAAccess a = new OAAccess(m_accessType);
				if (!(ret = a.get())) {
					return (ret);
				}
				accessId = a.getId();
			} else if (m_roleId != null && m_appDomainId != null
					&& m_accessId != null) {
				roleId = m_roleId;
				domId = m_appDomainId;
				accessId = m_accessId;
			} else {
				// System.out.println(m_roleName + "," + m_appDomain + ","
				// + m_accessType);
				// System.out.println(m_roleId + "," + m_appDomainId + ","
				// + m_accessId);
				return (false);
			}

			qryStr.append(roleId).append(",").append(domId).append(",").append(
					accessId).append(")");

			// System.out.println(qryStr);
			
			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

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
	 * @return
	 */
	public boolean delete() {
		boolean ret = get();

		if (ret) {
			return (ret);
		}

		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			Integer roleId = null;
			Integer domId = null;
			Integer accessId = null;

			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			StringBuffer qryStr = new StringBuffer(GlobalQueries.DEL_ROLE_OA);
			if (m_roleName != null && m_appDomain != null
					&& m_accessType != null) {
				Role r = new Role(m_roleName);
				if (!(ret = r.get())) {
					return (ret);
				}
				roleId = r.getId();

				OAObject o = new OAObject(m_appDomain);
				if (!(ret = o.get())) {
					return (ret);
				}
				domId = o.getId();

				OAAccess a = new OAAccess(m_accessType);
				if (!(ret = a.get())) {
					return (ret);
				}
				accessId = a.getId();
			} else if (m_roleId != null && m_appDomainId != null
					&& m_accessId != null) {
				roleId = m_roleId;
				domId = m_appDomainId;
				accessId = m_accessId;
			} else {
				return (false);
			}

			qryStr.append("role_id=").append(roleId).append(
					" and sys_object_id=").append(domId).append(
					" and sys_access_id=").append(accessId);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			stmt.execute(qryStr.toString());

			ret = true;
			
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
	 * @return
	 */
	public boolean get() {
		boolean ret = false;

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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_ROLE_OA);
			if (m_roleName != null && m_appDomain != null
					&& m_accessType != null) {
				qryStr.append("where r.role_name=\"").append(m_roleName)
						.append("\" and ").append("o.sys_object_name=\"")
						.append(m_appDomain).append("\" and ").append(
								"a.sys_access_name=\"").append(m_accessType)
						.append("\"");
			} else if (m_roleId != null && m_appDomainId != null
					&& m_accessId != null) {
				qryStr.append("where r.role_id=").append(m_roleId).append(
						" and o.sys_object_id=").append(m_appDomainId).append(
						" and a.sys_access_id=").append(m_accessId);
			} else {
				return (false);
			}

			// System.out.println(qryStr);

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				ret = true;
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

/**
 * 
 */
package com.palmyrasyscorp.db.tables;

/**
 * @author Prem
 *
 */
public abstract class AccessPrivilegeBase implements AccessPrivilege {

	private String m_objectName;
	protected void setObjectName(String in) {
		m_objectName = in;
	}
	public String getObjectName() {
		return (m_objectName);
	}
	public int hashCode() {
		return m_objectName.hashCode();
	}
	public boolean equals(Object o) {
		return (m_objectName.equals(o));
	}
}

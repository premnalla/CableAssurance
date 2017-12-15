/**
 * 
 */
package com.palmyrasyscorp.www.auth;

/**
 * @author Prem
 *
 */
public class CAUser extends AbstractCAUser {

	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	
	private String m_userName = null;
	private String m_password = null;
	
	public CAUser (String user, String pw) {
		m_userName = user;
		m_password = pw;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getPassword() {
		return m_password;
	}
}

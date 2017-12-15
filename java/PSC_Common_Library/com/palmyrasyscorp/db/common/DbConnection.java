/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.*;
import com.palmyrasyscorp.common.base.*;

/**
 * @author Prem
 *
 */
public class DbConnection extends AbstractConnection {
	private Connection m_connection;
	
	protected DbConnection() {}
	
	public DbConnection(Connection c)
	{
		m_connection = c;
	}
	
	public Connection getConnection()
	{
		return (m_connection);
	}
	
	public void closeConnection()
	{
		try {
			m_connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

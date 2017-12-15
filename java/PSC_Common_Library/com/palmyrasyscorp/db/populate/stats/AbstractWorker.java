package com.palmyrasyscorp.db.populate.stats;

import java.sql.*;

import com.palmyrasyscorp.db.common.*;

public abstract class AbstractWorker extends Thread {
	
	// protected final static int MAX_ROWS = 10;
	// protected final static int POLL_INTERVAL = 10;
	protected final static int MAX_ROWS = 5;
	// protected final static int MAX_ROWS = 10;
	protected final static float POLL_INTERVAL = 2;
	// protected final static int POLL_INTERVAL = 5;
	
	private boolean m_doneWork=false;
	
	protected AbstractWorker() {}
	
	/*
	abstract protected String getTableName();
	abstract protected LinkedList getObjectList();
	abstract protected long getResId(Object o);
	abstract protected String getResIdColumnName();
	abstract protected String getTotalColumnName();
	abstract protected String getOnlineColumnName();
	abstract protected int getTotalCount();
	abstract protected int getOnlineCount();
	*/
	
	public boolean isWorkDone()
	{
		return (m_doneWork);
	}
	
	protected void setDoneWork()
	{
		m_doneWork = true;
	}
	
	protected boolean closeConnection(DbConnection conn)
	{
		boolean ret = true;
		
		try {
			conn.getConnection().close();
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
			// ret = false;
		}

		return (ret);
	}

	/**
	 * 
	 * @return
	 */
	protected DbConnection createDbConnection()
	{
		DbConnection ret = null;
		
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
		}
		
		try {
			// Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root&password=manager");
			// Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/canet?user=root");
			ret = new DbConnection(conn);
			System.out.println("Got connection");
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param connectString
	 * @return
	 */
	protected DbConnection createDbConnection(String connectString)
	{
		DbConnection ret = null;
		
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
			ex.printStackTrace();
		}
		
		try {
			// Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root&password=manager");
			// Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root");
			// Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/canet?user=root");
			Connection conn = DriverManager.getConnection(connectString);
			ret = new DbConnection(conn);
			System.out.println("Got connection");
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return (ret);
	}

}

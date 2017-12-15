package com.palmyrasyscorp.db.common;

import java.util.*;
import sun.awt.*;
import java.sql.*;
import com.palmyrasyscorp.common.base.*;

public class DbConnectionPool extends AbstractPool {
  public  static final int MAX_READ_CONNECTIONS = 1;
  public  static final int MAX_WRITE_CONNECTIONS = 10;
  private static DbConnectionPool m_instance;

  private LinkedList m_freeReadConnectionList = new LinkedList();
  private Mutex m_freeReadLock = new Mutex();
  private LinkedList m_usedReadConnectionList = new LinkedList();
  private Mutex m_usedReadLock = new Mutex();
  
  private LinkedList m_freeWriteConnectionList = new LinkedList();
  private Mutex m_freeWriteLock = new Mutex();
  private LinkedList m_usedWriteConnectionList = new LinkedList();
  private Mutex m_usedWriteLock = new Mutex();
  
  protected DbConnectionPool()
  {
	  // create free list
	  // initialize used-list

	  try {
          // The newInstance() call is a work around for some
          // broken Java implementations

          Class.forName("com.mysql.jdbc.Driver").newInstance();
      } catch (Exception ex) {
          // handle the error
      }

	  for(int i=0; i<MAX_READ_CONNECTIONS; i++) {
		  try {
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://68.184.36.243/canet?user=root");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/canet?user=root&password=manager");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.107/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.110/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.103/canet?user=root&password=manager");
	            m_freeReadConnectionList.add(new DbConnection(conn));
	            System.out.println("Got connection");

	        } catch (SQLException ex) {
	            // handle any errors
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	        }
	  }
	  
	  for(int i=0; i<MAX_WRITE_CONNECTIONS; i++) {
		  try {
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/neto?user=root");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://68.184.36.243/canet?user=root");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.104/canet?user=root&password=manager");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.107/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.110/canet?user=root&password=manager");
	            // Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.103/canet?user=root&password=manager");
	            m_freeWriteConnectionList.add(new DbConnection(conn));
	            System.out.println("Got connection");

	        } catch (SQLException ex) {
	            // handle any errors
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	        }
	  }
  }
  
  /**
   * 
   * @return
   */
  public static DbConnectionPool getInstance()
  {
	  if (m_instance == null) {
		  m_instance = new DbConnectionPool();
	  }
	  
	  return (m_instance);
  }
  
  /**
   * 
   * @return
   */
  public DbConnection getConnection()
  {
	  DbConnection ret = null;

	  synchronized (m_freeReadLock) {
		  if (m_freeReadConnectionList.size() > 0) {
			  ret = (DbConnection) m_freeReadConnectionList.getFirst();
		  }
	  }
	  
	  return (ret);
  }
  
  /**
   * 
   * @return
   */
  public DbConnection getWriteConnection()
  {
	  DbConnection ret = null;

	  synchronized (m_freeWriteLock) {
		  if (m_freeWriteConnectionList.size() > 0) {
			  ret = (DbConnection) m_freeReadConnectionList.removeFirst();
		  }
	  }
	  
	  if (ret != null) {
		  synchronized (m_usedWriteLock) {
			  m_usedWriteConnectionList.add(ret);		  
		  }
	  }
	  
	  return (ret);
  }

  /**
   * 
   * @param c
   */
  public void releaseWriteConnection(DbConnection c)
  {
	  if (c != null) {
		  synchronized (m_usedWriteLock) {
			  m_usedWriteConnectionList.remove(c);		  
		  }
		  synchronized (m_freeWriteLock) {
			  m_freeWriteConnectionList.add(c);
		  }
	  }
  }
  
  /**
   * 
   * @param args
   */
  public static void main(String args[])
  {
	  System.out.println("Starting....");
	  DbConnectionPool.getInstance();
	  System.out.println("Finished....");
  }
  
}
